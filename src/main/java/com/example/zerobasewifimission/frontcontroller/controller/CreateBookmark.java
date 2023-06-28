package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkRepository;

import java.awt.print.Book;
import java.util.Map;

public class CreateBookmark implements Controller {
    BookmarkRepository bookmarkRepository = BookmarkRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        //와이파이 세부정보에서 북마크 그룹을 선택한 정보가 넘어온다. -> 북마크를 만드는 작업을 한다.
        //bookmark repository가져와서 Savebookmark
        String bookmark_group = paramMap.get("bookmark-group");
        String wifi_name = paramMap.get("wifi-name");

        bookmarkRepository.SaveBookmark(bookmark_group, wifi_name);
        return "CreateBookmark";
    }
}
