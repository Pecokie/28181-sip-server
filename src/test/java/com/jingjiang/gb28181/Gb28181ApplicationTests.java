package com.jingjiang.gb28181;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.header.HeaderFactory;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

@SpringBootTest
class Gb28181ApplicationTests {
    @Autowired
    SipRequestHeaderProvider sipRequestHeaderProvider;
    @Test
    void contextLoads() throws InvalidArgumentException, ParseException, SipException {
    }

}
