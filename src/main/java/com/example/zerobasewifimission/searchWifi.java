package com.example.zerobasewifimission;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;


@WebServlet(name = "searchWifi", urlPatterns = "/searchWifi-20")
public class searchWifi extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        String xc = request.getParameter("LAT");
        String yc = request.getParameter("LNT");

        connect(xc,yc);

        response.getHeader("Date");



        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>JSP - Hello World</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>" + "와이파이 정보 구하기" + "</h1>");

        writer.println("<a href=\"/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/\">홈</a>");
        writer.println("<span>|</span>");
        writer.println("<a href=\"search-history\">위치 히스토리 목록</a>");
        writer.println("<span>|</span>");
        writer.println("<a href=\"show-wifi\">OPEN API 와이파이 정보 가져오기</a>");
        writer.println("<span>|</span>");
        writer.println("<a href=\"sqlite-test\">sqlite test</a>");
        writer.println("<form action=\"searchWifi-20\" method=\"post\">");
        writer.println("LAT: <input type=\"number\" name=\"LAT\"  />");
        writer.println("LNT: <input type=\"number\" name=\"LNT\"  />");
        writer.println("<button type=\"submit\">근처 와이파이 정보 보기</button>");
        writer.println("</form>");
        writer.println("<table border='17'>");
        writer.println("<tr><th>거리</th><th>관리번호</th><th>자치구</th><th>와이파이명</th><th>도로명주소</th><th>상세주소</th>" +
                "<th>설치위치</th><th>설치유형</th><th>설치기관</th><th>서비스구분</th><th>망종류</th><th>설치년도</th>" +
                "<th>실내외구분</th><th>WIFI접속환경</th><th>X좌표</th><th>Y좌표</th><th>작업일자</th></tr>");
        writer.println("<div>LAT : "+xc+"</div>");
        writer.println("<div>LNT : "+yc+"</div>");

        writer.println("</body>");
        writer.println("</html>");




    }
    public static void connect(String xc, String yc) {
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

            Date date = new Date();
            System.out.println(date.toString());

            String sql = "INSERT INTO searchhistory (xc,yc,time) " +
                    "VALUES (?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(xc));
                pstmt.setInt(2, Integer.parseInt(yc));
                pstmt.setString(3, date.toString());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("데이터가 성공적으로 삽입되었습니다.");

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
