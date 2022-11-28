package com.jingjiang.gb28181.configuration;

import com.jingjiang.gb28181.domain.Device;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SipDeviceHolder {

    public static Map<String, Device> sipDeviceMap = new ConcurrentHashMap<>();


}
