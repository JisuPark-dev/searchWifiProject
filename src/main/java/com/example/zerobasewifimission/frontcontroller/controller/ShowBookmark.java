package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.Bookmark;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkRepository;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

public class ShowBookmark implements Controller {
    BookmarkRepository bookmarkRepository = BookmarkRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();

        model.put("bookmarks",bookmarks);
        return "ShowBookmark";
    }
}
