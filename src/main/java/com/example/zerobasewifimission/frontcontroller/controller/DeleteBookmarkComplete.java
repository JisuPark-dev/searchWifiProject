package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkRepository;

import java.awt.print.Book;
import java.util.Map;

public class DeleteBookmarkComplete implements Controller {
    BookmarkRepository bookmarkRepository = BookmarkRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String bookmark_id = paramMap.get("id");
        bookmarkRepository.deleteById(bookmark_id);
        return "DeleteBookmarkComplete";
    }
}
