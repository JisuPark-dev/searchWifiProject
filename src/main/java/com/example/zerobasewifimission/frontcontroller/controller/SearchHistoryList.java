package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.SearchHistory;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import java.util.List;
import java.util.Map;

public class SearchHistoryList implements Controller {
    SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object>model) {
        List<SearchHistory> searchHistories = searchHistoryRepository.findAll();
        model.put("searchHistories", searchHistories);

        return "SearchHistoryList";
    }
}
