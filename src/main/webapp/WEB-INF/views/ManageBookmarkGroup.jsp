<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Title</title>
        <script>
            function deleteRow(id) {
                fetch('/front-controller/delete-bookmark-group?id=' + id, { method: 'POST' })
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
        <h1>북마크 그룹 관리</h1>

        <a href="/">홈</a>
        <span>|</span>
        <a href="search-history">위치 히스토리 목록</a>
        <span>|</span>
        <a href="show-wifi">OPEN API 와이파이 정보 가져오기</a>
        <span>|</span>
        <a href="show-bookmark">북마크 보기</a>
        <span>|</span>
        <a href="manage-bookmark-group">북마크 그룹 관리</a>
        <form action="bookmark-group-save-form" method="get">
            <button type="submit">북마크 그룹 추가하기</button>
        </form>
        <p>manage bookmark</p>
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
