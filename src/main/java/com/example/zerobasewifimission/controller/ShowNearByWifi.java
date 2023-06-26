package com.example.zerobasewifimission.controller;

import com.example.zerobasewifimission.domain.WifiInfo;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;
import com.example.zerobasewifimission.repository.WifiInfoRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//조회시 내 주변 20개 불러오기
@WebServlet(name = "show-nearbyWifi", urlPatterns = "/show-nearbyWifi-20")
public class ShowNearByWifi extends HttpServlet {
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
