package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.SearchHistory;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchHistoryList implements Controller {
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<SearchHistory> searchHistories = searchHistoryRepository.findAll();
        request.setAttribute("searchHistories", searchHistories);

        String viewPath = "/WEB-INF/views/SearchHistoryList.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
