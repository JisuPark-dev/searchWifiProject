package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;

import java.util.Map;

public class BookmarkGroupSaveComplete implements Controller {
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String name = paramMap.get("name");
        int bo = Integer.parseInt(paramMap.get("BO"));
        bookmarkGroupRepository.SaveBookmarkGroup(name,bo);

        return "BookmarkGroupSaveComplete";
    }
}
