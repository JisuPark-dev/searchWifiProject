package com.example.zerobasewifimission.frontcontroller;

import com.example.zerobasewifimission.frontcontroller.controller.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

// 공통 처리부분 1)요청 경로에 따른 컨트롤러 매핑 2)요청 파라미터 파싱 3)데이터 모델 생성 4)컨트롤러 실행
// 5)뷰 리졸빙 6)뷰 렌더링
@WebServlet(name = "frontControllerServlet", urlPatterns = "/front-controller/*")
public class FrontControllerServlet extends HttpServlet {
    private Map<String, Controller> controllerMap = new HashMap<>();

    public FrontControllerServlet() {
        controllerMap.put("/front-controller/show-nearbyWifi-20", new ShowNearByWifi());
        controllerMap.put("/front-controller/wifi-detail", new WifiDetail());
        controllerMap.put("/front-controller/download-wifi", new DownLoadWifiInfo());
        //SearchHistory
        controllerMap.put("/front-controller/search-history", new SearchHistoryList());
        controllerMap.put("/front-controller/delete", new DeleteSearchHistory());
        //BookmarkGroup
        controllerMap.put("/front-controller/manage-bookmark-group", new ManageBookmarkGroup());
        controllerMap.put("/front-controller/bookmark-group-save-form", new BookmarkGroupSaveForm());
        controllerMap.put("/front-controller/bookmark-group-save-complete", new BookmarkGroupSaveComplete());
        controllerMap.put("/front-controller/edit-bookmark-group", new BookmarkGroupEditForm());
        controllerMap.put("/front-controller/bookmark-group-edit-complete", new BookmarkGroupEditComplete());
        controllerMap.put("/front-controller/delete-bookmark-group", new DeleteBookmarkGroup());
        //Bookmark
        controllerMap.put("/front-controller/show-bookmark", new ShowBookmark());
        controllerMap.put("/front-controller/create-bookmark", new CreateBookmark());
        controllerMap.put("/front-controller/delete-bookmark", new DeleteBookmark());
        controllerMap.put("/front-controller/delete-bookmark-complete", new DeleteBookmarkComplete());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //getController
        Controller controller = getController(request);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap, model 생성 - servlet 의존성 제거 목적
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        //controller.process 실행-> viewName 반환
        String viewName = controller.process(paramMap, model);
        //viewResolver를 통해서 논리 이름 -> 물리 이름으로 변환
        MyView view = viewResolver(viewName);
        //render 실행 -> request 객체에 데이터 담아서 경로에 맞는 JSP 파일에 전송 및 실행
        view.render(model, request, response);
    }

    private Controller getController(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Controller controller = controllerMap.get(requestURI);
        return controller;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            paramMap.put(paramName, request.getParameter(paramName));
        }
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
