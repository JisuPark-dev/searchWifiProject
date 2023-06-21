package com.example.zerobasewifimission;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "delete", urlPatterns = "/delete")
public class Delete extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id"); // 요청 파라미터에서 id 값을 가져옵니다.
        System.out.println("111");

        if (id != null && !id.isEmpty()) {
            try {
                // 여기에서 데이터베이스 연결 및 삭제 작업을 수행합니다.
                // 예를 들어, JDBC를 사용한다면 다음과 같이 작성할 수 있습니다.
                // (데이터베이스 연결 정보와 SQL 문은 실제 환경에 맞게 수정해야 합니다.)
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:/Users/sudong/Desktop/제로베이스/wifi_mission/zerobase-wifi-mission/src/main/webapp/WEB-INF/db/publicwifi.sqlite3";
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement("delete from searchhistory where id = ?");
                pstmt.setInt(1, Integer.parseInt(id));
                pstmt.executeUpdate();
            } catch (Exception e) {
                // 오류 발생 시 처리
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error occurred while deleting row with id " + id);
                return;
            }
        }

        // 성공적으로 삭제되었다는 응답을 보냅니다.
        response.getWriter().write("Row with id " + id + " was successfully deleted.");

    }
}

