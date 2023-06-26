package com.example.zerobasewifimission.frontcontroller;

import com.example.zerobasewifimission.frontcontroller.controller.DeleteSearchHistory;
import com.example.zerobasewifimission.frontcontroller.controller.DownLoadWifiInfo;
import com.example.zerobasewifimission.frontcontroller.controller.SearchHistoryList;
import com.example.zerobasewifimission.frontcontroller.controller.ShowNearByWifi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//frontController에서 공통적인 부분 처리 + map에서 찾아서 있으면 매핑해줌+Controller interface를 통해서 controller형식 통일
@WebServlet(name = "frontControllerServlet", urlPatterns = "/front-controller/*")
public class FrontControllerServlet extends HttpServlet {
    private Map<String, Controller> controllerMap = new HashMap<>();

    public FrontControllerServlet() {
        controllerMap.put("/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/front-controller/delete", new DeleteSearchHistory());
        controllerMap.put("/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/front-controller/download-wifi", new DownLoadWifiInfo());
        controllerMap.put("/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/front-controller/search-history", new SearchHistoryList());
        controllerMap.put("/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/front-controller/show-nearbyWifi-20", new ShowNearByWifi());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Controller controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
