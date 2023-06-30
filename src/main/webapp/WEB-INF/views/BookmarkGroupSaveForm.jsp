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

        <form id="bookmarkSaveForm" action="bookmark-group-save-complete" method="post" accept-charset="utf-8">
            <table>
                <tr>
                    <td class="td1">북마크 이름</td>
                    <td class="td2"><input type="text" id="name" name="name" required/></td>
                </tr>
                <tr>
                    <td class="td1">북마크 순서</td>
                    <td class="td2"><input type="number" step="any" id="BO" name="BO" required/></td>
                </tr>
            </table>
            <div>
                <button type = "button" onclick="history.back()">뒤로가기</button>
                <span> | </span>
                <button type="submit">추가</button>
            </div>
        </form>
        <script>
            document.getElementById("bookmarkSaveForm").addEventListener("submit", function(event){
                var name = document.getElementById("name");
                var BO = document.getElementById("BO");

                if(!name.value || !BO.value) {
                    event.preventDefault();  // prevent form submission
                    alert('모든 필드에 값을 입력해 주세요.');  // display a warning
                }
            });
        </script>
    </body>
</html>
