package com.jingjiang.gb28181.listener.strategy.request;

import com.jingjiang.gb28181.listener.strategy.RequestProcessStrategy;
import com.jingjiang.gb28181.SipResponseUtils;
import gov.nist.javax.sip.RequestEventExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.header.ContactHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Request;
import java.util.Calendar;
import java.util.Locale;

@Component
public class RegisterRequestProcess implements RequestProcessStrategy {

    private final static Logger logger = LoggerFactory.getLogger(RegisterRequestProcess.class);

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
        logger.debug("处理REGISTER请求");
        Request request = requestEvent.getRequest();
        sipResponseUtils.ok(
                requestEvent,
                headerFactory.createDateHeader(Calendar.getInstance(Locale.ENGLISH)),
                request.getHeader(ContactHeader.NAME),
                request.getExpires()
        );
    }

}
