package com.jingjiang.gb28181.media.api;

import com.jingjiang.gb28181.configuration.properties.MediaServerProperties;
import com.jingjiang.gb28181.media.api.domain.IsRecording;
import com.jingjiang.gb28181.media.api.domain.OpenRtpServer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class MediaApi {

    private final MediaServerProperties mediaServerProperties;

    public MediaApi(MediaServerProperties mediaServerProperties) {
        this.mediaServerProperties = mediaServerProperties;
    }

    /**
     * 创建GB28181 RTP接收端口
     *
     * @param streamId 流ID
     * @return OpenRtpServer响应对象
     */
    public OpenRtpServer openRtpServer(String streamId) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("secret", mediaServerProperties.getSecret());
        map.add("port", 0);
        map.add("enable_tcp", 1);
        map.add("stream_id", streamId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<OpenRtpServer> response;
        response = restTemplate.postForEntity(mediaServerProperties.getHost() + "/index/api/openRtpServer", httpEntity, OpenRtpServer.class);
        return response.getBody();
    }

    public Boolean isRecording(String streamId) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("secret", mediaServerProperties.getSecret());
        map.add("type", 1);
        map.add("vhost", "__defaultVhost__");
        map.add("app", "rtp");
        map.add("stream", streamId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<IsRecording> response = restTemplate.postForEntity(mediaServerProperties.getHost() + "/index/api/isRecording", httpEntity, IsRecording.class);
        return Objects.requireNonNull(response.getBody()).getStatus();
    }

    public void closeRtpServer(String streamId) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("secret", mediaServerProperties.getSecret());
        map.add("stream_id", streamId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        restTemplate.postForEntity(mediaServerProperties.getHost() + "/index/api/closeRtpServer", httpEntity, Object.class);
    }

}
