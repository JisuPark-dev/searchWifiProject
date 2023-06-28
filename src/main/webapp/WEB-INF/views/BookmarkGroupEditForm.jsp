<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    </head>
    <body>
        <h1>북마크 그룹 수정</h1>
        <%@ include file="jspf/navigation.jspf"%>
        <form action="bookmark-group-edit-complete" method="post" accept-charset="utf-8">
            <table>
                <tr>
                    <td class="td1">북마크 이름</td>
                    <td class="td2"><input type="text" id="name" name="name" value="${name}"/></td>
                </tr>
                <tr>
                    <td class="td1">북마크 순서</td>
                    <td class="td2"><input type="number" step="any" id="BO" name="BO" value="${BO}"/></td>
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
