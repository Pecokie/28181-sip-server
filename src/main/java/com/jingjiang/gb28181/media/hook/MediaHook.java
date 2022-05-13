package com.jingjiang.gb28181.media.hook;

import com.jingjiang.gb28181.GB28181ApplicationService;
import com.jingjiang.gb28181.media.api.MediaApi;
import com.jingjiang.gb28181.media.api.domain.OpenRtpServer;
import com.jingjiang.gb28181.media.hook.domain.RecordMp4;
import com.jingjiang.gb28181.media.hook.domain.StreamNoneReader;
import com.jingjiang.gb28181.media.hook.domain.StreamNotFound;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/index/hook")
public class MediaHook {

    private final MediaApi mediaApi;
    private final GB28181ApplicationService gb28181ApplicationService;

    public MediaHook(MediaApi mediaApi, GB28181ApplicationService gb28181ApplicationService) {
        this.mediaApi = mediaApi;
        this.gb28181ApplicationService = gb28181ApplicationService;
    }

    /**
     * 流量统计事件
     */
    public void on_flow_report() {

    }

    /**
     * 访问http文件服务器上hls之外的文件时触发。
     */
    public void on_http_access() {

    }

    /**
     * 播放器鉴权事件
     */
    public void on_play() {

    }

    /**
     * rtsp/rtmp/rtp推流鉴权事件。
     */
    public void on_publish() {

    }

    /**
     * 录制mp4完成后通知事件
     */
    @PostMapping("/on_record_mp4")
    public void on_record_mp4(@RequestBody RecordMp4 recordMp4) {
        System.out.println(recordMp4);
        mediaApi.closeRtpServer(recordMp4.getStream());
    }

    /**
     * 该rtsp流是否开启rtsp专用方式的鉴权事件，开启后才会触发on_rtsp_auth事件。
     */
    public void on_rtsp_realm() {

    }

    /**
     * rtsp专用的鉴权事件，先触发on_rtsp_realm事件然后才会触发on_rtsp_auth事件。
     */
    public void on_rtsp_auth() {

    }

    /**
     * shell登录鉴权，ZLMediaKit提供简单的telnet调试方式
     */
    public void on_shell_login() {

    }

    /**
     * rtsp/rtmp流注册或注销时触发此事件
     */
    public void on_stream_changed() {

    }

    /**
     * 流无人观看时事件，用户可以通过此事件选择是否关闭无人看的流。
     */
    @PostMapping("/on_stream_none_reader")
    public Map<String, Object> on_stream_none_reader(@RequestBody StreamNoneReader streamNoneReader) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        Boolean recording = mediaApi.isRecording(streamNoneReader.getStream());
        if (recording) {
            map.put("close", false);
        } else {
            map.put("close", true);
        }
        return map;
    }

    /**
     * 流未找到事件
     *
     * @param streamNotFound 回调参数
     */
    @PostMapping("/on_stream_not_found")
    public void on_stream_not_found(@RequestBody StreamNotFound streamNotFound) throws InvalidArgumentException, ParseException, SipException {
        System.out.println(streamNotFound.toString());
        OpenRtpServer openRtpServer = mediaApi.openRtpServer(streamNotFound.getStream());
        String stream = streamNotFound.getStream();
        String[] split = stream.split("@");
        String host = split[0];
        String channel = split[1];
        gb28181ApplicationService.play(host, channel, openRtpServer.getPort());
    }

    /**
     * 服务器启动事件，可以用于监听服务器崩溃重启；此事件对回复不敏感。
     */
    public void on_server_started() {

    }

    /**
     * 服务器定时上报时间，上报间隔可配置，默认10s上报一次
     */
    public void on_server_keepalive() {

    }



}
