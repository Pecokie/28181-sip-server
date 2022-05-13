package com.jingjiang.gb28181.listener.strategy;

import javax.sip.RequestEvent;

public interface RequestProcessStrategy {

    String getMethod();

    void process(RequestEvent requestEvent);

}
