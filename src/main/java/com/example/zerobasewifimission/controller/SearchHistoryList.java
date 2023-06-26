package com.example.zerobasewifimission.controller;

import com.example.zerobasewifimission.domain.SearchHistory;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "searchHistoryList", urlPatterns = "/search-history")
public class SearchHistoryList extends HttpServlet {
    //싱글톤
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<SearchHistory> searchHistories = searchHistoryRepository.findAll();
        request.setAttribute("searchHistories", searchHistories);

        String viewPath = "/WEB-INF/views/SearchHistoryList.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}


