<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Title</title>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    </head>
    <body>
        <h1>북마크 그룹 추가</h1>
        <%@ include file="jspf/navigation.jspf"%>

        <form action="bookmark-group-save-complete" method="post" accept-charset="utf-8">
            <table>
                <tr>
                    <td class="td1">북마크 이름</td>
                    <td class="td2"><input type="text" id="name" name="name"/></td>
                </tr>
                <tr>
                    <td class="td1">북마크 순서</td>
                    <td class="td2"><input type="number" step="any" id="BO" name="BO"/></td>
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
