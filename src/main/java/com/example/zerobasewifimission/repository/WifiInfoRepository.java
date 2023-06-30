package com.example.zerobasewifimission.repository;

import com.example.zerobasewifimission.domain.WifiInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class WifiInfoRepository {
    private Properties properties;
    String WIFI_API_URL = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";
    private static final WifiInfoRepository instance = new WifiInfoRepository();
    public static WifiInfoRepository getInstance() {
        return instance;
    }
    private WifiInfoRepository() {
        this.properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //공공 API에서 wifi 정보 가져오기
    public String getWifiInfoFromApi(int startIdx, int endIdx){
        String apiUrl = properties.getProperty("api.url");
        String apiKey = properties.getProperty("api.key");
        String fullUrl = apiUrl + "/" + apiKey + "/json/TbPublicWifiInfo/" + startIdx + "/" + endIdx + "/";
        try{
            URL url = new URL(fullUrl);
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

    public int getTotalCount(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject TbPublicWifiInfo = jsonObject.getJSONObject("TbPublicWifiInfo");
        int listTotalCount = TbPublicWifiInfo.getInt("list_total_count");

        System.out.println("List Total Count: " + listTotalCount);
        return listTotalCount;
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
        //데이터베이스에 저장
        Connection conn = null;
        try {
            // JDBC 드라이버 로드
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC 드라이버 로드");

            // DB 연결
            conn = DriverManager.getConnection(WIFI_API_URL);
            System.out.println("SQLite DB에 연결되었습니다.");

            // SQLite 성능 향상 설정
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA cache_size=1000000");
                stmt.execute("PRAGMA synchronous=0");
                stmt.execute("PRAGMA journal_mode=OFF");
                stmt.execute("PRAGMA locking_mode = EXCLUSIVE");
                stmt.execute("PRAGMA temp_store = MEMORY");
            }

            String sql = "INSERT INTO wifi (id, region, name, address1, address2, floor, install_type, install_mby, service_type, cmcwr, install_year, inout, remars3, lat, lnt, work_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject row = rows.getJSONObject(i);

                    pstmt.setString(1, row.getString("X_SWIFI_MGR_NO"));
                    pstmt.setString(2, row.getString("X_SWIFI_WRDOFC"));
                    pstmt.setString(3, row.getString("X_SWIFI_MAIN_NM"));
                    pstmt.setString(4, row.getString("X_SWIFI_ADRES1"));
                    pstmt.setString(5, row.getString("X_SWIFI_ADRES2"));
                    pstmt.setString(6, row.getString("X_SWIFI_INSTL_FLOOR"));
                    pstmt.setString(7, row.getString("X_SWIFI_INSTL_TY"));
                    pstmt.setString(8, row.getString("X_SWIFI_INSTL_MBY"));
                    pstmt.setString(9, row.getString("X_SWIFI_SVC_SE"));
                    pstmt.setString(10, row.getString("X_SWIFI_CMCWR"));
                    pstmt.setString(11, row.getString("X_SWIFI_CNSTC_YEAR"));
                    pstmt.setString(12, row.getString("X_SWIFI_INOUT_DOOR"));
                    pstmt.setString(13, row.getString("X_SWIFI_REMARS3"));
                    pstmt.setString(14, row.getString("LAT"));
                    pstmt.setString(15, row.getString("LNT"));
                    pstmt.setString(16, row.getString("WORK_DTTM"));

                    //pstmt.executeUpdate();
                    pstmt.addBatch();  // add this statement to the batch
                    cnt++;

                    //execute every 1000 items
                    if (i % 1000 == 0) {
                        pstmt.executeBatch();
                    }
                }
                pstmt.executeBatch(); // execute the remaining queries in the batch
                System.out.println(cnt + "개의 데이터가 성공적으로 삽입되었습니다.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
    }


    public List<WifiInfo> getNearbyWifiInfo(String xc, String yc) {
        List<WifiInfo> wifiInfoList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("####0.00000"); // for formatting the distance

        try (Connection conn = DriverManager.getConnection(WIFI_API_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM wifi")) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WifiInfo wifi = getWifiInfo(rs);
                    double lat = rs.getDouble("lat");
                    double lon = rs.getDouble("lnt");
                    double distance = calculateDistance(Double.parseDouble(xc), Double.parseDouble(yc), lat, lon);
                    wifi.setDistance(Double.parseDouble(df.format(distance))); // use the DecimalFormat here
                    wifiInfoList.add(wifi);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Sort the list by distance
        wifiInfoList.sort(Comparator.comparingDouble(WifiInfo::getDistance));
        // Limit the list to the 20 nearest
        if (wifiInfoList.size() > 20) {
            wifiInfoList = wifiInfoList.subList(0, 20);
        }

        return wifiInfoList;
    }

    public WifiInfo getWifiDetail(String id) {
        WifiInfo wifi = null;
        try (Connection conn = DriverManager.getConnection(WIFI_API_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM wifi WHERE id = ?")) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                wifi = getWifiInfo(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return wifi;
    }

    private static WifiInfo getWifiInfo(ResultSet rs) throws SQLException {
        WifiInfo wifi = new WifiInfo();
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
        return wifi;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
