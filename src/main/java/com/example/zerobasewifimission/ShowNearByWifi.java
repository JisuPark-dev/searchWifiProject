package com.example.zerobasewifimission;

import com.example.zerobasewifimission.domain.WifiInfo;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;
import com.example.zerobasewifimission.repository.WifiInfoRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//조회시 내 주변 20개 불러오기
@WebServlet(name = "searchWifi", urlPatterns = "/searchWifi-20")
public class ShowNearByWifi extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        //위치기반 검색시 해당 검색 기록 저장
        String xc = request.getParameter("LAT");
        String yc = request.getParameter("LNT");
        searchHistoryRepository.saveSearchHistory(xc,yc);

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

        List<WifiInfo> wifiInfoList = wifiInfoRepository.getNearbyWifiInfo(xc, yc);
        for (WifiInfo wifi : wifiInfoList) {
        writer.println("<tr><td>"+ wifi.getDistance() +"</td><td>"+ wifi.getId() + "</td><td>"+wifi.getRegion()+"</td><td>"+wifi.getName()
                +"</td><td>"+wifi.getAddress1()+"</td><td>"+wifi.getAddress2()+"</td><td>"
                +wifi.getFloor()+"</td><td>" +wifi.getInstallType()+"</td><td>"+wifi.getInstallMby()
                +"</td><td>"+wifi.getServiceType()+"</td><td>"+wifi.getCmcwr()+"</td><td>"
                +wifi.getInstallYear()+"</td><td>" +wifi.getInout()+"</td><td>"+wifi.getRemars3()
                +"</td><td>"+wifi.getLat()+"</td><td>"+wifi.getLnt()+"</td><td>" +wifi.getWorkTime()+"</td></tr>");
        }

        writer.println("<div>현재 내 위치</div>");
        writer.println("<div>LAT : "+xc+"</div>");
        writer.println("<div>LNT : "+yc+"</div>");

        writer.println("</body>");
        writer.println("</html>");
    }
}
