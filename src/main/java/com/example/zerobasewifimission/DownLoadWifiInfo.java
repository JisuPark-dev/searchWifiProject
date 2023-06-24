package com.example.zerobasewifimission;

import com.example.zerobasewifimission.repository.WifiInfoRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

//open api 와이파이 정보 가져외
@WebServlet(name = "showWifi", urlPatterns = "/show-wifi")
public class DownLoadWifiInfo extends HttpServlet {
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div>공공 api를 통해서 23304개의 와이파이 정보를 db에 저장했습니다.</div>");
        writer.println("<a href=\"/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/\">홈으로 돌아가기</a>");
        writer.println("</body>");
        writer.println("</html>");

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
