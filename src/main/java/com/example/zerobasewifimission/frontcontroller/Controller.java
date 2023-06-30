package com.example.zerobasewifimission.frontcontroller;

import java.util.Map;

public interface Controller {
    //paramMap과 model을 인자로 받아서 데이터 받기 및 저장을 수행하고, Controller와 매칭될 JSP의 논리이름을 반환함.
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
