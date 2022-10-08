package com.jingjiang.gb28181.media.hook.domain;

public class StreamChanged {
    /**
     * 流注册或注销
     */
    private Boolean regist;
    /**
     * 存活时间，单位秒
     */
    private Integer aliveSecond;
    /**
     * 应用名
     */
    private String app;
    /**
     * GMT unix系统时间戳，单位秒
     */
    private Long createStamp;
    /**
     * 服务器id
     */
    private String mediaServerId;
    /**
     * 协议
     */
    private String schema;
    /**
     * 流id
     */
    private String stream;
    /**
     * 观看总人数，包括hls/rtsp/rtmp/http-flv/ws-flv/rtc
     */
    private String totalReaderCount;


    public Boolean getRegist() {
        return regist;
    }

    public void setRegist(Boolean regist) {
        this.regist = regist;
    }

    public Integer getAliveSecond() {
        return aliveSecond;
    }

    public void setAliveSecond(Integer aliveSecond) {
        this.aliveSecond = aliveSecond;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Long getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(Long createStamp) {
        this.createStamp = createStamp;
    }

    public String getMediaServerId() {
        return mediaServerId;
    }

    public void setMediaServerId(String mediaServerId) {
        this.mediaServerId = mediaServerId;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getTotalReaderCount() {
        return totalReaderCount;
    }

    public void setTotalReaderCount(String totalReaderCount) {
        this.totalReaderCount = totalReaderCount;
    }
}
