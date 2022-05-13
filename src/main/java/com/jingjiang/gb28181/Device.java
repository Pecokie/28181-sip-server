package com.jingjiang.gb28181;

import javax.sip.header.CallIdHeader;

public class Device {

    /**
     * 设备地址
     */
    private String address;

    private Integer port;

    /**
     * 设备类型
     */
    private String protocol;

    private CallIdHeader callIdHeader;

    private String fromHeader;

    private String toTag;

    private String viaTag;

    public Device(String address, Integer port, String protocol) {
        this.address = address;
        this.port = port;
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public CallIdHeader getCallIdHeader() {
        return callIdHeader;
    }

    public void setCallIdHeader(CallIdHeader callIdHeader) {
        this.callIdHeader = callIdHeader;
    }

    public String getFromHeader() {
        return fromHeader;
    }

    public void setFromHeader(String fromHeader) {
        this.fromHeader = fromHeader;
    }

    public String getToTag() {
        return toTag;
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }

    public String getViaTag() {
        return viaTag;
    }

    public void setViaTag(String viaTag) {
        this.viaTag = viaTag;
    }
}
