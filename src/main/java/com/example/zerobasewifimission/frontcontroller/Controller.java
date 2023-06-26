package com.example.zerobasewifimission.frontcontroller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    void process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException;
}