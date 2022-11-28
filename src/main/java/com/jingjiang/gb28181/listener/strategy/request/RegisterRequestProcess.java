package com.jingjiang.gb28181.listener.strategy.request;

import com.jingjiang.gb28181.listener.strategy.RequestProcessStrategy;
import com.jingjiang.gb28181.configuration.SipResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.header.ContactHeader;
import javax.sip.header.DateHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Request;
import java.util.Calendar;
import java.util.Locale;

@Component
public class RegisterRequestProcess implements RequestProcessStrategy {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterRequestProcess.class);

    private final SipResponseUtils sipResponseUtils;
    private final HeaderFactory headerFactory;

    public RegisterRequestProcess(SipResponseUtils sipResponseUtil, HeaderFactory headerFactory) {
        this.sipResponseUtils = sipResponseUtil;
        this.headerFactory = headerFactory;
    }

    @Override
    public String getMethod() {
        return Request.REGISTER;
    }

    @Override
    public void process(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        FromHeader fromHeader = (FromHeader) request.getHeader(FromHeader.NAME);
        LOGGER.debug("收到来自 {} 的REGISTER请求", fromHeader.getAddress());

        DateHeader dateHeader = headerFactory.createDateHeader(Calendar.getInstance(Locale.ENGLISH));
        sipResponseUtils.ok(requestEvent, dateHeader, request.getHeader(ContactHeader.NAME), request.getExpires());
    }

}
