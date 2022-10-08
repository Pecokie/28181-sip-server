package com.jingjiang.gb28181;

import com.jingjiang.gb28181.media.cache.MediaCacheHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

@RestController
@SpringBootApplication
public class GB28181Application {

    private final GB28181ApplicationService gb28181ApplicationService;

    public GB28181Application(GB28181ApplicationService gb28181ApplicationService) {
        this.gb28181ApplicationService = gb28181ApplicationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GB28181Application.class, args);
    }

    @GetMapping("/getStream")
    public Map<String, Object> getStream(String host, String channelId) throws InvalidArgumentException, ParseException, SipException, InterruptedException {

        Optional<Map.Entry<String, String>> optional = MediaCacheHolder.get(host + "@" + channelId);

        Map<String, String> map = new HashMap<>();

        if (optional.isPresent()) {
            String stream = optional.get().getKey();
            map.put("http-fmp4", "http://stream.sc.remoc.asia:8080/rtp/" + stream + ".live.mp4");
            map.put("ws-fmp4", "ws://stream.sc.remoc.asia:8080/rtp/" + stream + ".live.mp4");
            map.put("webrtc", "http://stream.sc.remoc.asia:8080/index/api/webrtc?app=rtp&stream=" + stream + "&type=play");
        } else {
            play(host, channelId, 10000);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("msg", "success");
        data.put("data", map);

        return data;
    }

    @GetMapping("/getAllStream")
    public ConcurrentMap<String, String> getAllStream() throws InvalidArgumentException, ParseException, SipException {
        return MediaCacheHolder.getAll();
    }

    @GetMapping("/play")
    public void play(String host, String channelId, Integer port) throws InvalidArgumentException, ParseException, SipException {
        gb28181ApplicationService.play(host, channelId, port);
    }

    @GetMapping("/playBackDownload")
    public void playBackDownload(String host, String channelId, Integer port,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        gb28181ApplicationService.playBackDownload(host, channelId, port, startTime, endTime);
    }

    @GetMapping("/PTZControl")
    public void PTZControl(String host, String channelId, String cmd, int speed) throws InvalidArgumentException, ParseException, SipException {
        gb28181ApplicationService.PTZControl(host, channelId, cmd, speed);
    }

}
