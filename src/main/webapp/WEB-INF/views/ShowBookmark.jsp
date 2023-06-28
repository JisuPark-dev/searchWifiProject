<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h1>북마크 목록</h1>

        <a href="/">홈</a>
        <span>|</span>
        <a href="search-history">위치 히스토리 목록</a>
        <span>|</span>
        <a href="download-wifi">OPEN API 와이파이 정보 가져오기</a>
        <span>|</span>
        <a href="show-bookmark">북마크 보기</a>
        <span>|</span>
        <a href="manage-bookmark-group">북마크 그룹 관리</a>
        <p></p>

        <table border='5'>
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
