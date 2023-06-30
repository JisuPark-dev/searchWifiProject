<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
        <script>
            function deleteRow(id) {
                fetch('/front-controller/delete?id=' + id, { method: 'POST' })
                    .then(response => {
                        if (!response.ok) throw new Error('Deletion failed');
                        return response.text();
                    })
                    .then(data => {
                        alert(id + '번째 위치 검색 기록이 성공적으로 삭제되었습니다.');
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

        <%@ include file="jspf/navigation.jspf"%>
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
