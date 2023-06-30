<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  <head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <script>
      function deleteRow(id) {
        fetch('/front-controller/delete-bookmark-complete?id=' + id, { method: 'POST' })
                .then(response => {
                  if (!response.ok) throw new Error('Deletion failed');
                  return response.text();
                })
                .then(data => {
                  alert(id + '번째 북마크가 성공적으로 삭제되었습니다.');
                  window.location.href='http://localhost:8082/front-controller/show-bookmark'; // This line has been changed
                })
                .catch(error => {
                  console.error('Error:', error);
                  alert('Deletion failed.');
                });
      }
    </script>
  </head>
  <body>
    <h1>북마크 삭제</h1>
    <%@ include file="jspf/navigation.jspf"%>
    <p>정말로 북마크 삭제하실래요 ?</p>
      <table>
        <tr>
          <td class="td1">북마크 그룹 이름</td>
          <td class="td2">${bookmark.bookmark_group_name}</td>
        </tr>
        <tr>
          <td class="td1">와이파이명</td>
          <td class="td2">${bookmark.wifi_name}</td>
        </tr>
        <tr>
          <td class="td1">등록일자</td>
          <td class="td2">${bookmark.created_date}</td>
        </tr>
      </table>
        <div>
          <button type = "button" onclick="window.location.href='http://localhost:8082/front-controller/show-bookmark'">뒤로가기</button>
          <span> | </span>
          <button type='button' onclick='deleteRow(${id})'>삭제</button>
        </div>
  </body>
</html>
