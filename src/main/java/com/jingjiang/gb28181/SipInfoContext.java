package com.jingjiang.gb28181;

import com.jingjiang.gb28181.configuration.properties.MediaServerProperties;
import com.jingjiang.gb28181.configuration.properties.SipServerProperties;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class SipInfoContext {

    private final SipServerProperties sipServerProperties;
    private final MediaServerProperties mediaServerProperties;

    public SipInfoContext(SipServerProperties sipServerProperties, MediaServerProperties mediaServerProperties) {
        this.sipServerProperties = sipServerProperties;
        this.mediaServerProperties = mediaServerProperties;
    }

    public String playContext(Integer ssrc, String channelId, Integer port) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        writer.println("v=0");
        writer.println("o=" + channelId + " 0 0 IN IP4 " + mediaServerProperties.getAddress());
        writer.println("s=Play");
        writer.println("c=IN IP4 " + mediaServerProperties.getAddress());
        writer.println("t=0 0");
        writer.println("m=video " + port + " RTP/AVP 96 98 97");
        writer.println("a=recvonly");
        writer.println("a=rtpmap:96 PS/90000");
        writer.println("a=rtpmap:97 MPEG4/90000");
        writer.println("a=rtpmap:98 H264/90000");
        writer.println("y=0" + ssrc);
        return stringWriter.toString();
    }

    public String playBackDownloadContext(Integer ssrc, String channelId, Integer port, LocalDateTime startTime, LocalDateTime lastTime) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        writer.println("v=0");
        writer.println("o=" + sipServerProperties.getId() + " 0 0 IN IP4 " + mediaServerProperties.getAddress());
        writer.println("s=Download");
        writer.println("u=" + channelId + ":0");
        writer.println("c=IN IP4 " + mediaServerProperties.getAddress());
        writer.println("t=" + startTime.atZone(ZoneId.systemDefault()).toEpochSecond() + " " + lastTime.atZone(ZoneId.systemDefault()).toEpochSecond());
        writer.println("m=video " + port + " RTP/AVP 96 98 97");
        writer.println("a=recvonly");
        writer.println("a=downloadspeed:1");
        writer.println("a=rtpmap:96 PS/90000");
        writer.println("a=rtpmap:97 MPEG4/90000");
        writer.println("a=rtpmap:98 H264/90000");
        writer.println("y=1" + ssrc);
        writer.println("f=");
        return stringWriter.toString();
    }

    public String PTZControlContext(String channelId, String cmdStr) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        writer.println("<?xml version=\"1.0\" ?>\r\n");
        writer.println("<Control>\r\n");
        writer.println("<CmdType>DeviceControl</CmdType>\r\n");
        writer.println("<SN>" + (int) ((Math.random() * 9 + 1) * 100000) + "</SN>\r\n");
        writer.println("<DeviceID>" + channelId + "</DeviceID>\r\n");
        writer.println("<PTZCmd>" + cmdStr + "</PTZCmd>\r\n");
        writer.println("<Info>\r\n");
        writer.println("</Info>\r\n");
        writer.println("</Control>\r\n");
        return stringWriter.toString();
    }

}
