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

// frontController에서 공통적인 부분 처리 1)map에서 찾아서 있으면 매핑해줌 2)Controller interface를 통해서 controller형식 통일
// 3) ViewResolver 사용 4) render(model)호출
// ModelView를 통해서 메시지 주고받음. Controller의 Servlet 의존성 제거.
@WebServlet(name = "frontControllerServlet", urlPatterns = "/front-controller/*")
public class FrontControllerServlet extends HttpServlet {
    private Map<String, Controller> controllerMap = new HashMap<>();

    public FrontControllerServlet() {
        controllerMap.put("/front-controller/delete", new DeleteSearchHistory());
        controllerMap.put("/front-controller/delete-bookmark-group", new DeleteBookmarkGroup());
        controllerMap.put("/front-controller/delete-bookmark", new DeleteBookmark());
        controllerMap.put("/front-controller/delete-bookmark-complete", new DeleteBookmarkComplete());
        controllerMap.put("/front-controller/edit-bookmark-group", new BookmarkGroupEditForm());
        controllerMap.put("/front-controller/bookmark-group-edit-complete", new BookmarkGroupEditComplete());
        controllerMap.put("/front-controller/download-wifi", new DownLoadWifiInfo());
        controllerMap.put("/front-controller/search-history", new SearchHistoryList());
        controllerMap.put("/front-controller/show-nearbyWifi-20", new ShowNearByWifi());
        controllerMap.put("/front-controller/wifi-detail", new WifiDetail());
        controllerMap.put("/front-controller/show-bookmark", new ShowBookmark());
        controllerMap.put("/front-controller/create-bookmark", new CreateBookmark());
        controllerMap.put("/front-controller/manage-bookmark-group", new ManageBookmarkGroup());
        controllerMap.put("/front-controller/bookmark-group-save-form", new BookmarkGroupSaveForm());
        controllerMap.put("/front-controller/bookmark-group-save-complete", new BookmarkGroupSaveComplete());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Controller controller = getController(request);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);
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
