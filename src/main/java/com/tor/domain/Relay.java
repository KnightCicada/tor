package com.tor.domain;

import java.util.Date;

public class Relay {
    private int id;
    private Date relayDate;
    private String routerName;
    private String countryCode;
    private int bandwidth;
    private int uptime;
    private String ip;
    private String hostname;
    private String platform;

    public Date getRelayDate() {
        return relayDate;
    }

    public void setRelayDate(Date relayDate) {
        this.relayDate = relayDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getUptime() {
        return uptime;
    }

    public void setUptime(int uptime) {
        this.uptime = uptime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Relay{" +
                "id=" + id +
                ", relayDate='" + relayDate + '\'' +
                ", routerName='" + routerName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", bandwidth=" + bandwidth +
                ", uptime=" + uptime +
                ", ip='" + ip + '\'' +
                ", hostname='" + hostname + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
