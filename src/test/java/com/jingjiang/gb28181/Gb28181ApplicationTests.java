package com.jingjiang.gb28181;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;

@SpringBootTest
class Gb28181ApplicationTests {

    @Autowired
    GB28181ApplicationService gb28181ApplicationService;

    @Test
    void contextLoads() throws InvalidArgumentException, ParseException, SipException {
    }

}
