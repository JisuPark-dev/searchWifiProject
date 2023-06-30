<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
        <script>
            function deleteRow(id) {
                fetch('/front-controller/delete-bookmark-group?id=' + id, { method: 'POST' })
                    .then(response => {
                        if (!response.ok) throw new Error('Deletion failed');
                        return response.text();
                    })
                    .then(data => {
                        alert(id + '번째 북마크 그룹이 성공적으로 삭제되었습니다.');
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
        <h1>북마크 그룹 관리</h1>

        <%@ include file="jspf/navigation.jspf"%>
        <form action="bookmark-group-save-form" method="get">
            <button type="submit">북마크 그룹 추가하기</button>
        </form>
        <table>
            <tr><th>ID</th><th>북마크 이름</th><th>순서</th><th>등록일자</th><th>수정일자</th><th>비고</th></tr>
            <c:forEach var="bookmarkGroups" items="${bookmarkGroups}">
                <tr>
                    <td>${bookmarkGroups.no}</td>
                    <td>${bookmarkGroups.name}</td>
                    <td>${bookmarkGroups.BO}</td>
                    <td>${bookmarkGroups.created_time}</td>
                    <td>${bookmarkGroups.modify_time}</td>
                    <td>
                        <button type='button' onclick='window.location.href="edit-bookmark-group?id=${bookmarkGroups.no}"'>수정</button>
                        <button type='button' onclick='deleteRow(${bookmarkGroups.no})'>삭제</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
