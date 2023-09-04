package com.vanguard.weather.clients.params;

public class Param {

    private String q;
    private String appid;

    public Param(String q, String appid) {
        this.q = q;
        this.appid = appid;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}



