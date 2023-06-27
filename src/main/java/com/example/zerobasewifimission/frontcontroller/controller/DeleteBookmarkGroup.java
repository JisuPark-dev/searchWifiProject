package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;

import java.util.Map;

public class DeleteBookmarkGroup implements Controller {
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String id = paramMap.get("id");
        if (id != null && !id.isEmpty()) {
            try {
                bookmarkGroupRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        return "DeleteBookmarkGroup";
    }
}
