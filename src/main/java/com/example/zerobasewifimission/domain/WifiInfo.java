package com.example.zerobasewifimission.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WifiInfo {
    private double distance;
    private String id;
    private String region;
    private String name;
    private String address1;
    private String address2;
    private String floor;
    private String installType;
    private String installMby;
    private String serviceType;
    private String cmcwr;
    private String installYear;
    private String inout;
    private String remars3;
    private String lat;
    private String lnt;
    private String workTime;
    public WifiInfo() {
    }

    public WifiInfo(double distance, String id, String region, String name, String address1, String address2, String floor, String installType, String installMby, String serviceType, String cmcwr, String installYear, String inout, String remarks3, String lat, String lnt, String workTime) {
        this.distance = distance;
        this.id = id;
        this.region = region;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.floor = floor;
        this.installType = installType;
        this.installMby = installMby;
        this.serviceType = serviceType;
        this.cmcwr = cmcwr;
        this.installYear = installYear;
        this.inout = inout;
        this.remars3 = remars3;
        this.lat = lat;
        this.lnt = lnt;
        this.workTime = workTime;
    }
}
