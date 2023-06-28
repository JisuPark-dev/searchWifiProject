package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.BookmarkGroup;
import com.example.zerobasewifimission.domain.WifiInfo;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;
import com.example.zerobasewifimission.repository.WifiInfoRepository;

import java.util.List;
import java.util.Map;

public class WifiDetail implements Controller {
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String id = paramMap.get("id");
        double distance = Double.parseDouble(paramMap.get("d"));
        WifiInfo wifiDetail = wifiInfoRepository.getWifiDetail(id);
        List<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.findAll();

        model.put("wifiDetail",wifiDetail);
        model.put("distance", distance);
        model.put("bookmarkGroups", bookmarkGroups);
        return "WifiDetail";
    }
}
