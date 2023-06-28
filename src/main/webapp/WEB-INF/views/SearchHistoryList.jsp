<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <script>
            function deleteRow(id) {
                fetch('/front-controller/delete?id=' + id, { method: 'POST' })
                    .then(response => {
                        if (!response.ok) throw new Error('Deletion failed');
                        return response.text();
                    })
                    .then(data => {
                        alert('Row with id ' + id + ' was successfully deleted.');
                        location.reload();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Deletion failed.');
                    });
            }
        </script>
    </head>
    <body>
        <h1>위치 검색 기록</h1>

        <a href="/">홈</a>
        <span>|</span>
        <a href="search-history">위치 히스토리 목록</a>
        <span>|</span>
        <a href="download-wifi">OPEN API 와이파이 정보 가져오기</a>
        <span>|</span>
        <a href="show-bookmark">북마크 보기</a>
        <span>|</span>
        <a href="manage-bookmark-group">북마크 그룹 관리</a>
        <table border='5'>
            <tr><th>ID</th><th>X좌표</th><th>Y좌표</th><th>조회일자</th><th>비고</th></tr>
            <c:forEach var="history" items="${searchHistories}">
            <tr>
                <td>${history.id}</td>
                <td>${history.xc}</td>
                <td>${history.yc}</td>
                <td>${history.time}</td>
                <td>
                    <button type='button' onclick='deleteRow(${history.id})'>삭제</button>
                </td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
