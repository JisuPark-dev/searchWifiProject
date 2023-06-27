package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.Bookmark;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;

import java.util.Map;

public class BookmarkGroupEditForm implements Controller {
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String id = paramMap.get("id");
        Bookmark oneBookmarkGroup = bookmarkGroupRepository.getOneBookmarkGroup(id);

        model.put("no", id);
        model.put("name", oneBookmarkGroup.getName());
        model.put("BO", oneBookmarkGroup.getBO());

        return "BookmarkGroupEditForm";
    }
}
