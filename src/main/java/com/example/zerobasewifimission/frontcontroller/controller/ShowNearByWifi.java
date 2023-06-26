package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.WifiInfo;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;
import com.example.zerobasewifimission.repository.WifiInfoRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowNearByWifi implements Controller {
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //위치기반 검색시 해당 검색 기록 저장
        String xc = request.getParameter("LAT");
        String yc = request.getParameter("LNT");
        searchHistoryRepository.saveSearchHistory(xc,yc);

        List<WifiInfo> wifiInfoList = wifiInfoRepository.getNearbyWifiInfo(xc, yc);
        request.setAttribute("wifiInfoList", wifiInfoList);
        request.setAttribute("xc",xc);
        request.setAttribute("yc",yc);

        String viewPath = "/WEB-INF/views/ShowNearByWifi.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }
}
