package com.example.zerobasewifimission.repository;

import com.example.zerobasewifimission.domain.Bookmark;
import com.example.zerobasewifimission.domain.BookmarkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookmarkRepository {
    private static final String URL = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";

    private static final BookmarkRepository instance = new BookmarkRepository();
    private Connection connection;
    public static BookmarkRepository getInstance() {
        return instance;
    }

    private BookmarkRepository(){
        try {
            //SQLite JDBC 드라이버를 로딩
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void SaveBookmark(String bookmarkGroupName, String wifiName) {
        Connection connection = null;
        try {
            System.out.println("SQLite DB에 연결되었습니다.");
            java.util.Date date = new Date();

            String sql = "SELECT no FROM bookmarkgroup WHERE name = ?";
            int bookmarkGroupId;

            try {
                //SQLite JDBC 드라이버를 로딩
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(URL);

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, bookmarkGroupName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    bookmarkGroupId = rs.getInt("no");
                } else {
                    throw new Exception("Bookmark group not found.");
                }

                sql = "INSERT INTO bookmark (bookmark_group_id, wifi_name, created_date) VALUES (?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, bookmarkGroupId);
                pstmt.setString(2, wifiName);
                pstmt.setString(3, date.toString());

                pstmt.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("데이터가 성공적으로 삽입되었습니다.");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }


    public List<Bookmark> findAll() {
        List<Bookmark> bookmarks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT bookmark.id, bookmarkgroup.name as bookmark_group_name, bookmark.wifi_name, bookmark.created_date FROM bookmark INNER JOIN bookmarkgroup ON bookmark.bookmark_group_id = bookmarkgroup.no")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String bookmark_group_name = rs.getString("bookmark_group_name");
                String wifi_name = rs.getString("wifi_name");
                String created_date = rs.getString("created_date");
                bookmarks.add(new Bookmark(id, bookmark_group_name, wifi_name, created_date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarks;
    }


    public Bookmark getOneBookmark(String id) {
        Bookmark bookmark = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bookmark WHERE id = ?")) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                bookmark = new Bookmark();
                bookmark.setBookmark_group_name(rs.getString("bookmark_group_name"));
                bookmark.setWifi_name(rs.getString("wifi_name"));
                bookmark.setCreated_date(rs.getString("created_date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmark;
    }

    public void deleteById(String id) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("delete from bookmark where id = ?")) {

            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting search history with id " + id, e);
        }
    }
}
