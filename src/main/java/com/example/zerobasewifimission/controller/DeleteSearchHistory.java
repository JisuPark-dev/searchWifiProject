package com.example.zerobasewifimission.controller;

import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "delete", urlPatterns = "/delete")
public class DeleteSearchHistory extends HttpServlet {
    private SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id"); // 요청 파라미터에서 id 값을 가져옵니다.

        if (id != null && !id.isEmpty()) {
            try {
                searchHistoryRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}

