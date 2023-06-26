package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.WifiInfoRepository;
import org.json.JSONArray;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownLoadWifiInfo implements Controller {
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/DownLoadWifiInfo.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);

        int startIdx=1;
        int endIdx = 1000;
        int cnt = 1;

        while (wifiInfoRepository.getWifiInfoFromApi(startIdx, endIdx).length()!=0){
            // 데이터 1000개씩 받기
            String jsonString = wifiInfoRepository.getWifiInfoFromApi(startIdx, endIdx);

            //받은 데이터 파싱하기
            JSONArray rows = wifiInfoRepository.parseWifiInfo(jsonString);

            //db에 데이터 저장
            wifiInfoRepository.saveWifiInfo(cnt, rows);
            startIdx+=1000;
            endIdx+=1000;
            cnt+=1000;
        }
    }
}
