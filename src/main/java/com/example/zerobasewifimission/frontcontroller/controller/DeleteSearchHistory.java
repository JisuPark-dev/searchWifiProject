package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.SearchHistoryRepository;

import java.util.Map;

public class DeleteSearchHistory implements Controller {
    private SearchHistoryRepository searchHistoryRepository = SearchHistoryRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap,Map<String, Object> model)  {
        String id = paramMap.get("id"); // 요청 파라미터에서 id 값을 가져옵니다.

        if (id != null && !id.isEmpty()) {
            try {
                searchHistoryRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "DeleteSearchHistory";
    }
}
