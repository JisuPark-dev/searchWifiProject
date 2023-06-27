package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.WifiInfo;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.WifiInfoRepository;

import java.util.Map;

public class WifiDetail implements Controller {
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String id = paramMap.get("id");
        double distance = Double.parseDouble(paramMap.get("d"));
        System.out.println("distance = " + distance);
        WifiInfo wifiDetail = wifiInfoRepository.getWifiDetail(id);

        model.put("wifiDetail",wifiDetail);
        model.put("distance", distance);
        return "WifiDetail";
    }
}
