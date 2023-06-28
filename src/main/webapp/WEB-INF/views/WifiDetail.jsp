<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    </head>
    <body>
        <h1>와이파이 정보 구하기</h1>
        <%@ include file="jspf/navigation.jspf"%>
        <p></p>
        <form action="create-bookmark">
            <select name ="bookmark-group">
                <c:forEach var="bookmarkGroups" items="${bookmarkGroups}">
                    <option value = "${bookmarkGroups.name}">${bookmarkGroups.name}</option>
                </c:forEach>
            </select>
            <input type="hidden" id="wifi-name" name="wifi-name" value="${wifiDetail.name}">
            <input type="hidden" id="wifi-id" name="wifi-id" value="${wifiDetail.id}">
            <input type = "submit" value="북마크 추가하기">
        </form>
        <table class="wifiDetailTable">
            <tr>
                <td class="td1">거리</td>
                <td class="td2">${distance}</td>
            </tr>
            <tr>
                <td class="td1">관리번호</td>
                <td class="td2">${wifiDetail.id}</td>
            </tr>
            <tr>
                <td class="td1">자치구</td>
                <td class="td2">${wifiDetail.region}</td>
            </tr>
            <tr>
                <td class="td1">와이파이명</td>
                <td class="td2">${wifiDetail.name}</td>
            </tr>
            <tr>
                <td class="td1">도로명주소</td>
                <td class="td2">${wifiDetail.address1}</td>
            </tr>
            <tr>
                <td class="td1">상세주소</td>
                <td class="td2">${wifiDetail.address2}</td>
            </tr>
            <tr>
                <td class="td1">설치위치(층)</td>
                <td class="td2">${wifiDetail.floor}</td>
            </tr>
            <tr>
                <td class="td1">설치유형</td>
                <td class="td2">${wifiDetail.installType}</td>
            </tr>
            <tr>
                <td class="td1">설치기관</td>
                <td class="td2">${wifiDetail.installMby}</td>
            </tr>
            <tr>
                <td class="td1">서비스구분</td>
                <td class="td2">${wifiDetail.serviceType}</td>
            </tr>
            <tr>
                <td class="td1">망종류</td>
                <td class="td2">${wifiDetail.cmcwr}</td>
            </tr>
            <tr>
                <td class="td1">설치연도</td>
                <td class="td2">${wifiDetail.installYear}</td>
            </tr>
            <tr>
                <td class="td1">실내외구분</td>
                <td class="td2">${wifiDetail.inout}</td>
            </tr>
            <tr>
                <td class="td1">WIFI접속환경</td>
                <td class="td2">${wifiDetail.remars3}</td>
            </tr>
            <tr>
                <td class="td1">X좌표</td>
                <td class="td2">${wifiDetail.lat}</td>
            </tr>
            <tr>
                <td class="td1">Y좌표</td>
                <td class="td2">${wifiDetail.lnt}</td>
            </tr>
            <tr>
                <td class="td1">작업일자</td>
                <td class="td2">${wifiDetail.workTime}</td>
            </tr>
        </table>
        <button onclick="history.back()">뒤로가기</button>
    </body>
</html>
