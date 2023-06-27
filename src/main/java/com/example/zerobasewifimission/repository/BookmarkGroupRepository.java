package com.example.zerobasewifimission.repository;

import com.example.zerobasewifimission.domain.BookmarkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookmarkGroupRepository {
    private static final String URL = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";
    private static final BookmarkGroupRepository instance = new BookmarkGroupRepository();
    private Connection connection;
    public static BookmarkGroupRepository getInstance() {
        return instance;
    }

    private BookmarkGroupRepository(){
        try {
            //SQLite JDBC 드라이버를 로딩
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void SaveBookmarkGroup(String name, int BO) {
        Connection connection = null;
        try {
            System.out.println("SQLite DB에 연결되었습니다.");
            java.util.Date date = new Date();

            String sql = "INSERT INTO bookmarkgroup (name,BO,created_time) VALUES (?, ?, ?)";
            try {
                //SQLite JDBC 드라이버를 로딩
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, BO);
                pstmt.setString(3, date.toString());

                pstmt.executeUpdate();
            } catch (SQLException e) {
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

    public List<BookmarkGroup> findAll() {
        List<BookmarkGroup> bookmarkGroups = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT no, name, BO, created_time, modify_time FROM bookmarkgroup ORDER BY BO")) {

            while (rs.next()) {
                int no = rs.getInt("no");
                String name = rs.getString("name");
                int BO = rs.getInt("BO");
                String created_time = rs.getString("created_time");
                String modify_time = rs.getString("modify_time");
                bookmarkGroups.add(new BookmarkGroup(no, name, BO, created_time, modify_time));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkGroups;
    }
    public void deleteById(String id) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("delete from bookmarkgroup where no = ?")) {

            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting search history with id " + id, e);
        }
    }

    public BookmarkGroup getOneBookmarkGroup(String id) {
        BookmarkGroup bookmarkGroup = null;
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bookmarkgroup WHERE no = ?")) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                bookmarkGroup = new BookmarkGroup();
                bookmarkGroup.setName(rs.getString("name"));
                bookmarkGroup.setBO(rs.getInt("BO"));
                bookmarkGroup.setCreated_time(rs.getString("created_time"));
                bookmarkGroup.setModify_time(rs.getString("modify_time"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarkGroup;
    }

    public void updateBookmarkGroup(int no, String name, int BO) {
        Connection connection = null;
        try {
            System.out.println("SQLite DB에 연결되었습니다.");
            java.util.Date date = new Date();

            String sql = "UPDATE bookmarkgroup SET name = ?, BO = ?, modify_time = ? WHERE no = ?";
            try {
                //SQLite JDBC 드라이버를 로딩
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, BO);
                pstmt.setString(3, date.toString());
                pstmt.setInt(4, no);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("데이터가 성공적으로 수정되었습니다.");
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



}
