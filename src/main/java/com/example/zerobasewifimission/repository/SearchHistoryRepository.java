package com.example.zerobasewifimission.repository;

import com.example.zerobasewifimission.domain.SearchHistory;

import java.sql.*;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class SearchHistoryRepository {
    private static final String URL = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";
    private static final SearchHistoryRepository instance = new SearchHistoryRepository();
    private Connection connection;
    public static SearchHistoryRepository getInstance() {
        return instance;
    }
    private SearchHistoryRepository() {
        try {
            //SQLite JDBC 드라이버를 로딩
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SearchHistory> findAll() {
        List<SearchHistory> searchHistories = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, xc, yc, time FROM searchhistory")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String xc = rs.getString("xc");
                String yc = rs.getString("yc");
                String time = rs.getString("time");
                searchHistories.add(new SearchHistory(id, xc, yc, time));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchHistories;
    }

    public void deleteById(String id) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("delete from searchhistory where id = ?")) {

            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting search history with id " + id, e);
        }
    }

    public void saveSearchHistory(String xc, String yc) {
        try {
            System.out.println("SQLite DB에 연결되었습니다.");
            DateFormatSymbols dfs = new DateFormatSymbols(Locale.KOREAN);
            dfs.setWeekdays(new String[]{
                    "unused",
                    "일요일",
                    "월요일",
                    "화요일",
                    "수요일",
                    "목요일",
                    "금요일",
                    "토요일"
            });
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd '('E')' HH:mm:ss", dfs);
            String dateStr = sdf.format(new Date());


            String sql = "INSERT INTO searchhistory (xc,yc,time) VALUES (?, ?, ?)";
            executeUpdate(sql, Double.parseDouble(xc), Double.parseDouble(yc), dateStr.toString());

            System.out.println("데이터가 성공적으로 삽입되었습니다.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
