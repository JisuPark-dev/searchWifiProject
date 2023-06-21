package com.example.zerobasewifimission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

//open api 와이파이 정보 가져외
@WebServlet(name = "showWifi", urlPatterns = "/show-wifi")
public class ShowWifi extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div>서울시 모든 공공 와이파이 목록</div>");
        writer.println("<div>db연동해서 데이터 저장하면 됨</div>");
        writer.println("<a href=\"/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/\">홈으로 돌아가기</a>");

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

//                System.out.println("xSwifiWrDofc = " + xSwifiWrDofc);
//                System.out.println("xSwifiMgrNo = " + xSwifiMgrNo);
//                System.out.println("X_SWIFI_MAIN_NM = " + X_SWIFI_MAIN_NM);
//                System.out.println("X_SWIFI_ADRES1 = " + X_SWIFI_ADRES1);
//                System.out.println("X_SWIFI_ADRES2 = " + X_SWIFI_ADRES2);
//                System.out.println("X_SWIFI_INSTL_FLOOR = " + X_SWIFI_INSTL_FLOOR);
//                System.out.println("X_SWIFI_INSTL_TY = " + X_SWIFI_INSTL_TY);
//                System.out.println("X_SWIFI_INSTL_MBY = " + X_SWIFI_INSTL_MBY);
//                System.out.println("X_SWIFI_SVC_SE = " + X_SWIFI_SVC_SE);
//                System.out.println("X_SWIFI_CMCWR = " + X_SWIFI_CMCWR);
//                System.out.println("X_SWIFI_CNSTC_YEAR = " + X_SWIFI_CNSTC_YEAR);
//                System.out.println("X_SWIFI_INOUT_DOOR = " + X_SWIFI_INOUT_DOOR);
//                System.out.println("X_SWIFI_REMARS3 = " + X_SWIFI_REMARS3);
//                System.out.println("LAT = " + LAT);
//                System.out.println("LNT = " + LNT);
//                System.out.println("WORK_DTTM = " + WORK_DTTM);
//                System.out.println();
//                System.out.println();




                //데이터베이스에 저장
                Connection conn = null;
                Statement stmt = null;
                try {
                    // JDBC 드라이버 로드
                    try {
                        Class.forName("org.sqlite.JDBC");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    // SQLite DB 파일 경로 지정
                    String url = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";

                    // DB 연결
                    conn = DriverManager.getConnection(url);

                    System.out.println("SQLite DB에 연결되었습니다.");
                    // SQL 문 생성
//                    stmt = conn.createStatement();
//                    String sql = "insert into wifi (id, region, name,address1, address2, floor,install_type, install_mby, service_type, cmcwr, install_year,inout, remars3,lat, lnt, work_time) " +
//                            "values ("+xSwifiMgrNo+", "+xSwifiWrDofc+", "+X_SWIFI_MAIN_NM+", "+X_SWIFI_ADRES1+","+X_SWIFI_ADRES2+","+X_SWIFI_INSTL_FLOOR+","+X_SWIFI_INSTL_TY+","+X_SWIFI_INSTL_MBY+"," + X_SWIFI_SVC_SE+","+X_SWIFI_CMCWR+" ,"+X_SWIFI_CNSTC_YEAR+","+X_SWIFI_INOUT_DOOR+","+X_SWIFI_REMARS3+","+LAT+","+LNT+","+WORK_DTTM+");";
////                    String sql = "insert into wifi (id, region, name,address1, address2, floor,install_type, install_mby, service_type, cmcwr, install_year,inout, remars3,lat, lnt, work_time) values (\"xSwifiMgrNo\", \"xSwifiWrDofc\", \"X_SWIFI_MAIN_NM\", \"X_SWIFI_ADRES1\",\"X_SWIFI_ADRES2\",\"X_SWIFI_INSTL_FLOOR\",\"X_SWIFI_INSTL_TY\",\"X_SWIFI_INSTL_MBY\",\"X_SWIFI_SVC_SE\",\"X_SWIFI_CMCWR\" ,\"X_SWIFI_CNSTC_YEAR\",\"X_SWIFI_INOUT_DOOR\",\"X_SWIFI_REMARS3\",\"LAT\",\"LNT\",\"WORK_DTTM\");";
//                    stmt.executeUpdate(sql);
                    String sql = "INSERT INTO wifi (id, region, name, address1, address2, floor, install_type, install_mby, service_type, cmcwr, install_year, inout, remars3, lat, lnt, work_time) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, xSwifiMgrNo);
                        pstmt.setString(2, xSwifiWrDofc);
                        pstmt.setString(3, X_SWIFI_MAIN_NM);
                        pstmt.setString(4, X_SWIFI_ADRES1);
                        pstmt.setString(5, X_SWIFI_ADRES2);
                        pstmt.setString(6, X_SWIFI_INSTL_FLOOR);
                        pstmt.setString(7, X_SWIFI_INSTL_TY);
                        pstmt.setString(8, X_SWIFI_INSTL_MBY);
                        pstmt.setString(9, X_SWIFI_SVC_SE);
                        pstmt.setString(10, X_SWIFI_CMCWR);
                        pstmt.setString(11, X_SWIFI_CNSTC_YEAR);
                        pstmt.setString(12, X_SWIFI_INOUT_DOOR);
                        pstmt.setString(13, X_SWIFI_REMARS3);
                        pstmt.setString(14, LAT);
                        pstmt.setString(15, LNT);
                        pstmt.setString(16, WORK_DTTM);

                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    System.out.println(cnt+"번째 데이터가 성공적으로 삽입되었습니다.");

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                cnt++;
            }
            startIdx+=1000;
            endIdx+=1000;
        }
        writer.println("</body>");
        writer.println("</html>");
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
