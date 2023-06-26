package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.frontcontroller.MyView;
import com.example.zerobasewifimission.repository.WifiInfoRepository;
import org.json.JSONArray;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadWifiInfo implements Controller {
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) {
        MyView view = new MyView("/WEB-INF/views/DownLoadWifiInfo.jsp");

        try {
            int startIdx = 1;
            int endIdx = 1000;
            int cnt = 1;
            String jsonString;

            while (!(jsonString = wifiInfoRepository.getWifiInfoFromApi(startIdx, endIdx)).equals("failed to get response")) {
                JSONArray rows;
                try {
                    //받은 데이터 파싱하기
                    rows = wifiInfoRepository.parseWifiInfo(jsonString);
                } catch (JSONException e) {
                    System.err.println("Error while parsing JSON data: " + e.getMessage());
                    break;
                }

                //db에 데이터 저장
                wifiInfoRepository.saveWifiInfo(cnt, rows);

                startIdx += 1000;
                endIdx += 1000;
                cnt += 1000;
            }
        } catch (Exception e) {
            System.err.println("An error occurred during the Wi-Fi data processing: " + e.getMessage());
        }

        return view;
    }
}
