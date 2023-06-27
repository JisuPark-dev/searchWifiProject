package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;

import java.util.Map;

public class BookmarkGroupEditComplete implements Controller {
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        bookmarkGroupRepository.updateBookmarkGroup(
                Integer.parseInt(paramMap.get("id"))
                , paramMap.get("name")
                , Integer.parseInt( paramMap.get("BO"))
        );

        return "BookmarkGroupEditComplete";
    }
}
