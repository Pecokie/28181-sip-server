package com.jingjiang.gb28181.application;

import com.jingjiang.gb28181.domain.Device;
import com.jingjiang.gb28181.configuration.SipRequestHeaderProvider;
import com.jingjiang.gb28181.configuration.SipDeviceHolder;
import com.jingjiang.gb28181.configuration.SipProviderProxy;
import com.jingjiang.gb28181.media.cache.MediaCacheHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.SipProvider;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Request;
import java.text.ParseException;
import java.util.Objects;


/**
 * 视频业务类
 */
@Service
public class SipAppService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SipAppService.class);

    private final SipInfoContext sipInfoContext;
    private final SipProviderProxy sipProviderProxy;
    private final SipRequestHeaderProvider sipRequestHeaderProvider;

    public SipAppService(SipInfoContext sipInfoContext, SipProviderProxy sipProviderProxy, SipRequestHeaderProvider sipRequestHeaderProvider) {
        this.sipInfoContext = sipInfoContext;
        this.sipProviderProxy = sipProviderProxy;
        this.sipRequestHeaderProvider = sipRequestHeaderProvider;
    }


    /**
     * 云台控制
     *
     * @param host      地址
     * @param channelId 通道号
     * @param cmd       指令
     * @param speed     速度
     * @throws SipException             Sip异常
     * @throws InvalidArgumentException 无效参数异常
     * @throws ParseException           解析异常
     */
    public void PTZControl(String host, String channelId, String cmd, int speed) throws SipException, InvalidArgumentException, ParseException {
        int cmdCode = 0;
        if (Objects.equals(cmd, "left")) {
            cmdCode |= 0x02;
        } else if (Objects.equals(cmd, "right")) {
            cmdCode |= 0x01;
        } else if (Objects.equals(cmd, "up")) {
            cmdCode |= 0x08;
        } else if (Objects.equals(cmd, "down")) {
            cmdCode |= 0x04;
        } else if (Objects.equals(cmd, "zoomIn")) {
            cmdCode |= 0x10;
        } else if (Objects.equals(cmd, "zoomOut")) {
            cmdCode |= 0x20;
        }

        StringBuilder builder = new StringBuilder("A50F01");
        String strTmp;
        strTmp = String.format("%02X", cmdCode);
        builder.append(strTmp, 0, 2);

        strTmp = String.format("%02X", speed);
        builder.append(strTmp, 0, 2);
        builder.append(strTmp, 0, 2);

        strTmp = String.format("%X", speed);
        builder.append(strTmp, 0, 1).append("0");
        //计算校验码
        int checkCode = (0XA5 + 0X0F + 0X01 + cmdCode + speed + speed + (speed /*<< 4*/ & 0XF0)) % 0X100;
        strTmp = String.format("%02X", checkCode);
        builder.append(strTmp, 0, 2);
        String s = builder.toString();

        String context = sipInfoContext.PTZControlContext(channelId, s);

        Device device = SipDeviceHolder.sipDeviceMap.get(host);
        SipProvider sipProvider = sipProviderProxy.getSipProvider(device.getProtocol());
        CallIdHeader callId = sipProvider.getNewCallId();
        String fromTag = String.valueOf(System.currentTimeMillis());
        String viaTag = "z9hG4bK" + System.currentTimeMillis();

        Request request = sipRequestHeaderProvider.createMessageRequest(device.getAddress(), device.getPort(), channelId, fromTag, viaTag, context, device.getProtocol(), callId);
        sipProvider.getNewClientTransaction(request).sendRequest();
    }

    /**
     * 播放实时视频, 同步代码块 简单处理并发问题
     *
     * @param host      地址
     * @param channelId 通道id
     * @throws InvalidArgumentException 无效参数异常
     * @throws SipException             Sip异常
     * @throws ParseException           解析异常
     */
    public synchronized void play(String host, String channelId, Integer port) throws InvalidArgumentException, SipException, ParseException {
        if (MediaCacheHolder.isExist(host + "@" + channelId)) {
            LOGGER.info("{}@{} 流已存在", host, channelId);
        } else {
            int ssrc = (int) ((Math.random() * 9 + 1) * 100000000);
            String context = sipInfoContext.playContext(ssrc, channelId, port);
            Device device = SipDeviceHolder.sipDeviceMap.get(host);
            SipProvider sipProvider = sipProviderProxy.getSipProvider(device.getProtocol());
            CallIdHeader callId = sipProvider.getNewCallId();
            String fromTag = String.valueOf(System.currentTimeMillis());
            String viaTag = "z9hG4bK" + System.currentTimeMillis();
            device.setCallIdHeader(callId);
            device.setFromHeader(fromTag);
            device.setViaTag(viaTag);
            Request request = sipRequestHeaderProvider.createInviteRequest(device.getAddress(), device.getPort(), channelId, fromTag, viaTag, context, device.getProtocol(), callId);
            sipProvider.getNewClientTransaction(request).sendRequest();

            // 缓存中添加此流
            MediaCacheHolder.put(Integer.toHexString(ssrc).toUpperCase(), host + "@" + channelId);
        }
    }

}
