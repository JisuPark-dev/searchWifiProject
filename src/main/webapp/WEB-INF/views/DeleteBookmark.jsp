<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
  <script>
    function deleteRow(id) {
      fetch('/front-controller/delete-bookmark-complete?id=' + id, { method: 'POST' })
              .then(response => {
                if (!response.ok) throw new Error('Deletion failed');
                return response.text();
              })
              .then(data => {
                alert('Row with id ' + id + ' was successfully deleted.');
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

<a href="/">홈</a>
<span>|</span>
<a href="search-history">위치 히스토리 목록</a>
<span>|</span>
<a href="download-wifi">OPEN API 와이파이 정보 가져오기</a>
<span>|</span>
<a href="show-bookmark">북마크 보기</a>
<span>|</span>
<a href="manage-bookmark-group">북마크 그룹 관리</a>
<p>정말로 북마크 삭제하실래요 ?</p>

    <table>
      <tr>
        <td>북마크 그룹 이름</td>
        <td>${bookmark.bookmark_group_name}</td>
      </tr>
      <tr>
        <td>와이파이명</td>
        <td>${bookmark.wifi_name}</td>
      </tr>
      <tr>
        <td>등록일자</td>
        <td>${bookmark.created_date}</td>
      </tr>
    </table>
      <div>
        <button type = "button" onclick="window.location.href='http://localhost:8082/front-controller/show-bookmark'">뒤로가기</button>
        <span> | </span>
        <button type='button' onclick='deleteRow(${id})'>삭제</button>
      </div>

</body>
</html>
