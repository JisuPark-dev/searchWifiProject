package com.example.zerobasewifimission.repository;

import com.example.zerobasewifimission.domain.WifiInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiInfoRepository {
    String WIFI_API_URL = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";
    private static final WifiInfoRepository instance = new WifiInfoRepository();
    public static WifiInfoRepository getInstance() {
        return instance;
    }
    private WifiInfoRepository() {
    }

    //공공 API에서 wifi 정보 가져오기
    public String getWifiInfoFromApi(int startIdx, int endIdx){
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

    //API에서 가져온 wifi 정보 파싱하기
    public JSONArray parseWifiInfo(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject tbPublicWifiInfo = jsonObject.getJSONObject("TbPublicWifiInfo");
        JSONArray rows = tbPublicWifiInfo.getJSONArray("row");
        return rows;
    }

    //db에 파싱된 와이파이 정보 저장
    public void saveWifiInfo(int cnt, JSONArray rows) {
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


            //데이터베이스에 저장
            Connection conn = null;
            try {
                // JDBC 드라이버 로드
                try {
                    Class.forName("org.sqlite.JDBC");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // DB 연결
                conn = DriverManager.getConnection(WIFI_API_URL);

                System.out.println("SQLite DB에 연결되었습니다.");
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
    }

    public List<WifiInfo> getNearbyWifiInfo(String xc, String yc) {
        List<WifiInfo> wifiInfoList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(WIFI_API_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT *, sqrt(((CAST(lat AS DOUBLE) - ?) * (CAST(lat AS DOUBLE) - ?)) + ((CAST(lnt AS DOUBLE) - ?) * (CAST(lnt AS DOUBLE) - ?))) as distance FROM wifi ORDER BY distance ASC LIMIT 20")) {

            pstmt.setDouble(1, Double.parseDouble(xc));
            pstmt.setDouble(2, Double.parseDouble(xc));
            pstmt.setDouble(3, Double.parseDouble(yc));
            pstmt.setDouble(4, Double.parseDouble(yc));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WifiInfo wifi = new WifiInfo();
                    wifi.setDistance(rs.getDouble("distance"));
                    wifi.setId(rs.getString("id"));
                    wifi.setRegion(rs.getString("region"));
                    wifi.setName(rs.getString("name"));
                    wifi.setAddress1(rs.getString("address1"));
                    wifi.setAddress2(rs.getString("address2"));
                    wifi.setFloor(rs.getString("floor"));
                    wifi.setInstallType(rs.getString("install_type"));
                    wifi.setInstallMby(rs.getString("install_mby"));
                    wifi.setServiceType(rs.getString("service_type"));
                    wifi.setCmcwr(rs.getString("cmcwr"));
                    wifi.setInstallYear(rs.getString("install_year"));
                    wifi.setInout(rs.getString("inout"));
                    wifi.setRemars3(rs.getString("remars3"));
                    wifi.setLat(rs.getString("lat"));
                    wifi.setLnt(rs.getString("lnt"));
                    wifi.setWorkTime(rs.getString("work_time"));

                    wifiInfoList.add(wifi);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return wifiInfoList;
    }
}
