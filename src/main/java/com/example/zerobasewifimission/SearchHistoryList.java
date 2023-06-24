package com.example.zerobasewifimission;

import com.example.zerobasewifimission.domain.SearchHistory;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "searchHistoryList", urlPatterns = "/search-history")
public class SearchHistoryList extends HttpServlet {
    //싱글톤
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

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

        List<SearchHistory> searchHistories = searchHistoryRepository.findAll();
        for (SearchHistory history : searchHistories) {
            writer.println("<tr><td>" + history.getId() + "</td><td>" + history.getXc() + "</td><td>" + history.getYc() + "</td><td>" + history.getTime() + "</td><td><button type='button' onclick='deleteRow(" + history.getId() + ")'>삭제</button></td></tr>");
        }

        writer.println("</table>");
        writer.println("<a href=\"/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/\">홈으로 돌아가기</a>");
        writer.println("</body>");
        writer.println("</html>");
    }
}


