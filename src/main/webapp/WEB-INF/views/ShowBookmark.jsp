<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    </head>
    <body>
        <div id="header">
            <h1>북마크 목록</h1>
        </div>

        <%@ include file="jspf/navigation.jspf"%>

        <table>
            <tr><th>ID</th><th>북마크 그룹 이름</th><th>와이파이 명</th><th>등록일자</th><th>비고</th></tr>
            <c:forEach var="bookmarks" items="${bookmarks}">
                <tr>
                    <td>${bookmarks.id}</td>
                    <td>${bookmarks.bookmark_group_name}</td>
                    <td><a href = "wifi-detail?id=${wifi.id}&d=0">${bookmarks.wifi_name}<a/></td>
                    <td>${bookmarks.created_date}</td>
                    <td>
                        <button type='button' onclick='window.location.href="delete-bookmark?id=${bookmarks.id}"'>삭제</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
