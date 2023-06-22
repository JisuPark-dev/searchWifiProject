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

import static java.lang.Double.parseDouble;

//조회시 내 주변 20개 불러오기
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
        writer.println("LAT: <input type=\"number\" step=\"any\" id=\"lat\" name=\"LAT\"  />");
        writer.println("LNT: <input type=\"number\" step=\"any\" id=\"lnt\" name=\"LNT\"  />");
        writer.println("<button type=\"submit\">근처 와이파이 정보 보기</button>");
        writer.println("</form>");
        writer.println("<button type=\"button\" onclick=\"getLocation()\">내 위치 가져오기</button>");

        writer.println("<script>");
        writer.println("function getLocation() {");
        writer.println("  if (navigator.geolocation) {");
        writer.println("    navigator.geolocation.getCurrentPosition(fillFormWithPosition);");
        writer.println("  } else {");
        writer.println("    alert(\"Geolocation is not supported by this browser.\");");
        writer.println("  }");
        writer.println("}");
        writer.println("function fillFormWithPosition(position) {");
        writer.println("  document.getElementById('lat').value = position.coords.latitude;");
        writer.println("  document.getElementById('lnt').value = position.coords.longitude;");
        writer.println("}");
        writer.println("</script>");
        writer.println("<table border='17'>");
        writer.println("<tr><th>거리</th><th>관리번호</th><th>자치구</th><th>와이파이명</th><th>도로명주소</th><th>상세주소</th>" +
                "<th>설치위치</th><th>설치유형</th><th>설치기관</th><th>서비스구분</th><th>망종류</th><th>설치년도</th>" +
                "<th>실내외구분</th><th>WIFI접속환경</th><th>X좌표</th><th>Y좌표</th><th>작업일자</th></tr>");
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
//
            String sql = "SELECT *, sqrt(((CAST(lat AS DOUBLE) - ?) * (CAST(lat AS DOUBLE) - ?)) + ((CAST(lnt AS DOUBLE) - ?) * (CAST(lnt AS DOUBLE) - ?))) as distance FROM wifi ORDER BY distance ASC LIMIT 20";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, parseDouble(xc));
            pstmt.setDouble(2, parseDouble(xc));
            pstmt.setDouble(3, parseDouble(yc));
            pstmt.setDouble(4, parseDouble(yc));
            ResultSet rs = pstmt.executeQuery();

            // 데이터 출력
            while(rs.next()){
                double distance = rs.getDouble("distance");
                String id = rs.getString("id");
                String region = rs.getString("region");
                String name = rs.getString("name");
                String address1 = rs.getString("address1");
                String address2 = rs.getString("address2");
                String floor = rs.getString("floor");
                String install_type = rs.getString("install_type");
                String install_mby = rs.getString("install_mby");
                String service_type = rs.getString("service_type");
                String cmcwr = rs.getString("cmcwr");
                String install_year = rs.getString("install_year");
                String inout = rs.getString("inout");
                String remars3 = rs.getString("remars3");
                String lat = rs.getString("lat");
                String lnt = rs.getString("lnt");
                String work_time = rs.getString("work_time");

                writer.println("<tr><td>"+ distance +"</td><td>"+ id + "</td><td>"+region+"</td><td>"+name+"</td><td>"+address1+"</td><td>"+address2+"</td><td>"
                        +floor+"</td><td>" +install_type+"</td><td>"+install_mby+"</td><td>"+service_type+"</td><td>"+cmcwr+"</td><td>"
                        +install_year+"</td><td>" +inout+"</td><td>"+remars3+"</td><td>"+lat+"</td><td>"+lnt+"</td><td>"
                        +work_time+"</td></tr>");
//                System.out.println("id: " + id + ", region: " + region+", name: "+name+", address1: "+address1);
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
        writer.println("<div>현재 내 위치</div>");
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
                pstmt.setDouble(1, Double.parseDouble(xc));
                pstmt.setDouble(2, Double.parseDouble(yc));
                pstmt.setString(3, date.toString());
                pstmt.executeUpdate();

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
