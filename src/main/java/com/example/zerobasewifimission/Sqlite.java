package com.example.zerobasewifimission;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "sqlite", urlPatterns = "/sqlite-test")
public class Sqlite extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        connect();

        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div>sqlite test 화면입니다.</div>");
        writer.println("<a href=\"/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/\">홈으로 돌아가기</a>");
        writer.println("</body>");
        writer.println("</html>");




    }
    public static void connect() {

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
            String url = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/test.sqlite3";

            // DB 연결
            conn = DriverManager.getConnection(url);

            System.out.println("SQLite DB에 연결되었습니다.");
            // SQL 문 생성

            stmt = conn.createStatement();
            String sql = "INSERT INTO topics (title, body, created,author_name, author_profile) " +
                    "VALUES ('test1', 'test1 is ...','2-23--7-01', 'test1','test1');";
            stmt.executeUpdate(sql);

            System.out.println("데이터가 성공적으로 삽입되었습니다.");

            // 조회 SQL 문 생성
            stmt = conn.createStatement();
            String sql2 = "SELECT id, title FROM topics";
            ResultSet rs = stmt.executeQuery(sql2);

            // 데이터 출력
            while(rs.next()){
                String id = rs.getString("id");
                String title = rs.getString("title");
                System.out.println("id: " + id + ", title: " + title);
            }

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

}
