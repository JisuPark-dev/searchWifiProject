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
<div>이 페이지는 조회 기록 목록 화면입니다.</div>
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
<a href="/">홈으로 돌아가기</a>
</body>
</html>
