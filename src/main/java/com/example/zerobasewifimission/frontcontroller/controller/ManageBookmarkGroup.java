package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.domain.Bookmark;
import com.example.zerobasewifimission.frontcontroller.Controller;
import com.example.zerobasewifimission.repository.BookmarkGroupRepository;

import java.util.List;
import java.util.Map;

public class ManageBookmarkGroup implements Controller {
    BookmarkGroupRepository bookmarkGroupRepository = BookmarkGroupRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        //북마크 추가, 수정, 삭제하는 로직 구현
        String name = paramMap.get("name");
        String boStr = paramMap.get("BO");

        List<Bookmark> bookmarks = bookmarkGroupRepository.findAll();
        model.put("bookmarks",bookmarks);

        return "ManageBookmarkGroup";
    }
}
