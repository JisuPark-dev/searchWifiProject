package com.example.zerobasewifimission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "showWifi", urlPatterns = "/show-wifi")
public class ShowWifi extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        int startIdx=1;
        int endIdx = 1000;
        int cnt = 1;


        while (getWifiInfoString(startIdx, endIdx).length()!=0){
            String jsonString = getWifiInfoString(startIdx, endIdx); // 데이터 1000개씩 받기

            //받은 데이터 파싱하기
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject tbPublicWifiInfo = jsonObject.getJSONObject("TbPublicWifiInfo");

            int listTotalCount = tbPublicWifiInfo.getInt("list_total_count");
            System.out.println("listTotalCount = " + listTotalCount);

            JSONObject result = tbPublicWifiInfo.getJSONObject("RESULT");
            String code = result.getString("CODE");
            String message = result.getString("MESSAGE");
            System.out.println("code = " + code);
            System.out.println("message = " + message);

            JSONArray rows = tbPublicWifiInfo.getJSONArray("row");
            for (int i = 0; i < rows.length(); i++) {
                JSONObject row = rows.getJSONObject(i);

                String xSwifiMgrNo = row.getString("X_SWIFI_MGR_NO");
                String xSwifiWrDofc = row.getString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = row.getString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = row.getString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = row.getString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = row.getString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = row.getString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = row.getString("X_SWIFI_INSTL_MBY");
                String X_SWIFI_SVC_SE = row.getString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = row.getString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = row.getString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = row.getString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = row.getString("X_SWIFI_REMARS3");
                String LAT = row.getString("LAT");
                String LNT = row.getString("LNT");
                String WORK_DTTM = row.getString("WORK_DTTM");

                System.out.println("xSwifiWrDofc = " + xSwifiWrDofc);
                System.out.println("xSwifiMgrNo = " + xSwifiMgrNo);
                System.out.println("X_SWIFI_MAIN_NM = " + X_SWIFI_MAIN_NM);
                System.out.println("X_SWIFI_ADRES1 = " + X_SWIFI_ADRES1);
                System.out.println("X_SWIFI_ADRES2 = " + X_SWIFI_ADRES2);
                System.out.println("X_SWIFI_INSTL_FLOOR = " + X_SWIFI_INSTL_FLOOR);
                System.out.println("X_SWIFI_INSTL_TY = " + X_SWIFI_INSTL_TY);
                System.out.println("X_SWIFI_INSTL_MBY = " + X_SWIFI_INSTL_MBY);
                System.out.println("X_SWIFI_SVC_SE = " + X_SWIFI_SVC_SE);
                System.out.println("X_SWIFI_CMCWR = " + X_SWIFI_CMCWR);
                System.out.println("X_SWIFI_CNSTC_YEAR = " + X_SWIFI_CNSTC_YEAR);
                System.out.println("X_SWIFI_INOUT_DOOR = " + X_SWIFI_INOUT_DOOR);
                System.out.println("X_SWIFI_REMARS3 = " + X_SWIFI_REMARS3);
                System.out.println("LAT = " + LAT);
                System.out.println("LNT = " + LNT);
                System.out.println("WORK_DTTM = " + WORK_DTTM);
                System.out.println();
                System.out.println();

                response.getWriter().write("\n"+cnt+"xSwifiWrDofc = " + xSwifiWrDofc);
                cnt++;
                // Continue for the rest of the attributes in the row.
            }
            startIdx+=1000;
            endIdx+=1000;
        }


        response.getWriter().write("\n현재 공공 api에서 와이파이 조회하는 것까지는 함.");
        System.out.println("이제 전체 조회하기를 구현해야함");

        // Perform your operations with the parsed data.

    }


    //공공 와이파이 api사용하여 데이터 받아오는 코드
    private String getWifiInfoString(int startIdx, int endIdx){
        String apiUrl = "http://openapi.seoul.go.kr:8088/726d4463756467643839615a58704b/json/TbPublicWifiInfo/"+startIdx+"/"+endIdx+"/";
        try{
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if(responseCode == 200){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else{
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = br.readLine())!=null){
                response.append(inputLine);
            }
            br.close();
            return response.toString();

        }catch(Exception e) {
            return "failed to get response";
        }
    }

}
