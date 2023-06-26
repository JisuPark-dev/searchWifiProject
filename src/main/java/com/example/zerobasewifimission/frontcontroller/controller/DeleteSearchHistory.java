package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.frontcontroller.MyView;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteSearchHistory implements Controller {
    private SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id"); // 요청 파라미터에서 id 값을 가져옵니다.

        if (id != null && !id.isEmpty()) {
            try {
                searchHistoryRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        return new MyView("/WEB-INF/views/DeleteSearchHistory.jsp");
    }
}
