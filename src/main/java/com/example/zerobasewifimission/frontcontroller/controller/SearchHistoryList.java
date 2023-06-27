package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.SearchHistory;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.frontcontroller.ModelView;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import java.util.List;
import java.util.Map;

public class SearchHistoryList implements Controller {
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<SearchHistory> searchHistories = searchHistoryRepository.findAll();
        ModelView mv = new ModelView("SearchHistoryList");
        mv.getModel().put("searchHistories", searchHistories);

        return mv;
    }
}
