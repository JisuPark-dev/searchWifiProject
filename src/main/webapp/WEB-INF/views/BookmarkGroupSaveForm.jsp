<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <meta charset="utf-8">
    </head>
    <body>
        <h1>북마크 그룹 추가</h1>

        <a href="/">홈</a>
        <span>|</span>
        <a href="search-history">위치 히스토리 목록</a>
        <span>|</span>
        <a href="show-wifi">OPEN API 와이파이 정보 가져오기</a>
        <span>|</span>
        <a href="show-bookmark">북마크 보기</a>
        <span>|</span>
        <a href="manage-bookmark-group">북마크 그룹 관리</a>

        <form action="bookmark-group-save-complete" method="post" accept-charset="utf-8">
            <table>
                <tr>
                    <td>북마크 이름</td>
                    <td><input type="text" id="name" name="name"/></td>
                </tr>
                <tr>
                    <td>북마크 순서</td>
                    <td><input type="number" step="any" id="BO" name="BO"/></td>
                </tr>
            </table>
            <div>
                <button type = "button" onclick="history.back()">뒤로가기</button>
                <span> | </span>
                <button type="submit">추가</button>
            </div>
        </form>
    </body>
</html>
