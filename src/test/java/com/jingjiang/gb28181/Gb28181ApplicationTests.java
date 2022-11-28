package com.jingjiang.gb28181;

import com.jingjiang.gb28181.configuration.SipRequestHeaderProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;

@SpringBootTest
class Gb28181ApplicationTests {
    @Autowired
    SipRequestHeaderProvider sipRequestHeaderProvider;
    @Test
    void contextLoads() throws InvalidArgumentException, ParseException, SipException {

    }

}
