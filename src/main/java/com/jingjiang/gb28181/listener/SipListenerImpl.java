package com.jingjiang.gb28181.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sip.*;

@Component
public class SipListenerImpl implements SipListener {

    private final static Logger logger = LoggerFactory.getLogger(SipListenerImpl.class);


    private final SipProcessProxy sipProcessProxy;

    public SipListenerImpl(SipProcessProxy sipProcessProxy) {
        this.sipProcessProxy = sipProcessProxy;
    }


    @Override
    public void processRequest(RequestEvent requestEvent) {
        logger.debug("处理请求");
        sipProcessProxy.process(requestEvent);
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        logger.debug("处理响应");
        sipProcessProxy.process(responseEvent);
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        logger.debug("超时");
    }

    @Override
    public void processIOException(IOExceptionEvent exceptionEvent) {
        logger.debug("出现IO异常");
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        logger.debug("事务已终止");
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        logger.debug("对话已终止");
    }

}