<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h1>북마크 그룹 수정</h1>

        <a href="/">홈</a>
        <span>|</span>
        <a href="search-history">위치 히스토리 목록</a>
        <span>|</span>
        <a href="show-wifi">OPEN API 와이파이 정보 가져오기</a>
        <span>|</span>
        <a href="show-bookmark">북마크 보기</a>
        <span>|</span>
        <a href="manage-bookmark-group">북마크 그룹 관리</a>

        <form action="bookmark-group-edit-complete" method="post" accept-charset="utf-8">
            <table>
                <tr>
                    <td>북마크 이름</td>
                    <td><input type="text" id="name" name="name" value="${name}"/></td>
                </tr>
                <tr>
                    <td>북마크 순서</td>
                    <td><input type="number" step="any" id="BO" name="BO" value="${BO}"/></td>
                    <input type="hidden" id="id" name = "id" value="${no}"/>
                </tr>
            </table>
            <div>
                <button type = "button" onclick="window.location.href='http://localhost:8082/front-controller/manage-bookmark-group'">뒤로가기</button>
                <span> | </span>
                <button type="submit">수정</button>
            </div>
        </form>
    </body>
</html>
