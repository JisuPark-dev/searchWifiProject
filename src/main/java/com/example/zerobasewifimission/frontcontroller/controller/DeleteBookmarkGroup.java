package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;

import java.util.Map;
public class DeleteBookmarkGroup implements Controller {
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String id = paramMap.get("id");
        bookmarkGroupRepository.deleteById(id);
        return "DeleteBookmarkGroup";
    }
}
