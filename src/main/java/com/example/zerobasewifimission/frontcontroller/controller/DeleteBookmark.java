package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.Bookmark;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkRepository;

import java.util.Map;

public class DeleteBookmark implements Controller {
    BookmarkRepository bookmarkRepository = BookmarkRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        Bookmark bookmark = bookmarkRepository.getOneBookmark(paramMap.get("id"));
        System.out.println("bookmark.getId() = " + bookmark.getId());
        System.out.println("bookmark.getBookmark_group_name() = " + bookmark.getBookmark_group_name());
        model.put("bookmark", bookmark);
        model.put("id", paramMap.get("id"));
        return "DeleteBookmark";
    }
}
