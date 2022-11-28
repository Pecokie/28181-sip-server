package com.jingjiang.gb28181.listener.strategy.request;

import com.jingjiang.gb28181.configuration.SipDeviceHolder;
import com.jingjiang.gb28181.domain.Device;
import com.jingjiang.gb28181.listener.strategy.RequestProcessStrategy;
import com.jingjiang.gb28181.configuration.SipResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.address.SipURI;
import javax.sip.header.FromHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;

@Component
public class MessageRequestProcess implements RequestProcessStrategy {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageRequestProcess.class);


    private final SipResponseUtils sipResponseUtils;

    public MessageRequestProcess(SipResponseUtils sipResponseUtils) {
        this.sipResponseUtils = sipResponseUtils;
    }


    @Override
    public String getMethod() {
        return Request.MESSAGE;
    }

    @Override
    public void process(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();

        FromHeader fromHeader = (FromHeader) request.getHeader(FromHeader.NAME);
        LOGGER.debug("收到来自 {} 的MESSAGE请求", fromHeader.getAddress());
        ViaHeader reqViaHeader = (ViaHeader) request.getHeader(ViaHeader.NAME);
        SipURI uri = (SipURI) fromHeader.getAddress().getURI();
        SipDeviceHolder.sipDeviceMap.put(uri.getUser(), new Device(reqViaHeader.getReceived(), reqViaHeader.getRPort(), reqViaHeader.getTransport()));
        sipResponseUtils.ok(requestEvent);
    }

}
