package com.jingjiang.gb28181;

import com.jingjiang.gb28181.application.SipAppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;

@RestController
@SpringBootApplication
public class GB28181Application {

    private final SipAppService gb28181ApplicationService;

    public GB28181Application(SipAppService gb28181ApplicationService) {
        this.gb28181ApplicationService = gb28181ApplicationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GB28181Application.class, args);
    }


    @GetMapping("/play")
    public void play(String host, String channelId, Integer port) throws InvalidArgumentException, ParseException, SipException {
        gb28181ApplicationService.play(host, channelId, port);
    }

    @GetMapping("/PTZControl")
    public void PTZControl(String host, String channelId, String cmd, int speed) throws InvalidArgumentException, ParseException, SipException {
        gb28181ApplicationService.PTZControl(host, channelId, cmd, speed);
    }

}
