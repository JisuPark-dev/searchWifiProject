package com.example.zerobasewifimission;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "searchHistoryList", urlPatterns = "/search-history")
public class SearchHistoryList extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
//        connect();

        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<script>");
        writer.println("function deleteRow(id) {");
        writer.println("    fetch(`/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/delete?id=${id}`, { method: 'POST' })");
        writer.println("        .then(response => {");
        writer.println("            if (!response.ok) throw new Error('Deletion failed');");
        writer.println("            return response.text();");
        writer.println("        })");
        writer.println("        .then(data => {");
        writer.println("            alert('Row with id ' + id + ' was successfully deleted.');");
        writer.println("            location.reload();");
        writer.println("        })");
        writer.println("        .catch(error => {");
        writer.println("            console.error('Error:', error);");
        writer.println("            alert('Deletion failed.');");
        writer.println("        });");
        writer.println("}");
        writer.println("</script>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<div>이 페이지는 조회 기록 목록 화면입니다.</div>");
        writer.println("<table border='5'>");
        writer.println("<tr><th>ID</th><th>X좌표</th><th>Y좌표</th><th>조회일자</th><th>비고</th></tr>");

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

            // 조회 SQL 문 생성
            stmt = conn.createStatement();
            String sql = "SELECT id, xc,yc,time FROM searchhistory";
            ResultSet rs = stmt.executeQuery(sql);

            // 데이터 출력
//            for (String fruit : items) {
//                writer.println("<tr><td>" + fruit + "</td></tr>");
//            }
            while(rs.next()){
                String id = rs.getString("id");
                String xc = rs.getString("xc");
                String yc = rs.getString("yc");
                String time = rs.getString("time");
                writer.println("<tr><td>" + id + "</td><td>"+xc+"</td><td>"+yc+"</td><td>"+time+"</td><td><button type='button' onclick='deleteRow("+id+")'>삭제</button></td></tr>");
                System.out.println("id: " + id + ", xc: " + xc+", yc: "+yc+", time: "+time);
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



        writer.println("</table>");
        writer.println("<a href=\"/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/\">홈으로 돌아가기</a>");
        writer.println("</body>");
        writer.println("</html>");


    }

//    public static void connect() {

//        Connection conn = null;
//        Statement stmt = null;
//        try {
//            // JDBC 드라이버 로드
//            try {
//                Class.forName("org.sqlite.JDBC");
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            // SQLite DB 파일 경로 지정
//            String url = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";
//
//            // DB 연결
//            conn = DriverManager.getConnection(url);
//
//            System.out.println("SQLite DB에 연결되었습니다.");
//
//            // 조회 SQL 문 생성
//            stmt = conn.createStatement();
//            String sql = "SELECT id, xc,yc,time FROM searchhistory";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            // 데이터 출력
//            while(rs.next()){
//                String id = rs.getString("id");
//                String xc = rs.getString("xc");
//                String yc = rs.getString("yc");
//                String time = rs.getString("time");
//                System.out.println("id: " + id + ", xc: " + xc+", yc: "+yc+", time: "+time);
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }
}


