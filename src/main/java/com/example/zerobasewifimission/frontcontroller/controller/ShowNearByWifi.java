package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.WifiInfo;
import com.example.zerobasewifimission.frontcontroller.Controller;

import com.example.zerobasewifimission.repository.SearchHistoryRepository;
import com.example.zerobasewifimission.repository.WifiInfoRepository;

import java.util.List;
import java.util.Map;

public class ShowNearByWifi implements Controller {
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model)  {
        //위치기반 검색시 해당 검색 기록 저장
        String xc = paramMap.get("LAT");
        String yc = paramMap.get("LNT");
        searchHistoryRepository.saveSearchHistory(xc,yc);

        List<WifiInfo> wifiInfoList = wifiInfoRepository.getNearbyWifiInfo(xc, yc);
        model.put("wifiInfoList", wifiInfoList);
        model.put("xc",xc);
        model.put("yc",yc);

        return "ShowNearByWifi";
    }
}
