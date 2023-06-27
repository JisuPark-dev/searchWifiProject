package com.example.zerobasewifimission.frontcontroller.controller;

import com.example.zerobasewifimission.frontcontroller.Controller;

import java.util.Map;

public class ManageBookmark implements Controller {
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "ManageBookmark";
    }
}
