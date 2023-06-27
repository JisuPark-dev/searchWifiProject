package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.BookmarkGroup;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;

import java.util.List;
import java.util.Map;

public class ManageBookmarkGroup implements Controller {
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.findAll();
        model.put("bookmarkGroups", bookmarkGroups);

        return "ManageBookmarkGroup";
    }
}
