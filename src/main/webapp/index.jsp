<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css"/>
</head>
<body>
<h1><%= "와이파이 정보 구하기" %></h1>
<nav>
<a href="/">홈</a>
<span>|</span>
<a href="front-controller/search-history">위치 히스토리 목록</a>
<span>|</span>
<a href="front-controller/download-wifi">OPEN API 와이파이 정보 가져오기</a>
<span>|</span>
<a href="front-controller/show-bookmark">북마크 보기</a>
<span>|</span>
<a href="front-controller/manage-bookmark-group">북마크 그룹 관리</a>
</nav>
<div class="location">
  <form action="front-controller/show-nearbyWifi-20" method="post" >
    LAT: <input type="number" step="any" id="lat" name="LAT" />
    LNT: <input type="number" step="any" id="lnt" name="LNT" />
    <button type="submit">근처 와이파이 정보 보기</button>
  </form>
  <button onclick="getLocation()">내 위치 가져오기</button>
</div>
<table>
  <tr>
    <th>거리</th>
    <th>관리번호</th>
    <th>자치구</th>
    <th>와이파이명</th>
    <th>도로명주소</th>
    <th>상세주소</th>
    <th>설치위치</th>
    <th>설치유형</th>
    <th>설치기관</th>
    <th>서비스구분</th>
    <th>망종류</th>
    <th>설치년도</th>
    <th>실내외구분</th>
    <th>WIFI접속환경</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>작업일자</th>
  </tr>
</table>

<script>
  function getLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(fillFormWithPosition);
    } else {
      alert("Geolocation is not supported by this browser.");
    }
  }
  function fillFormWithPosition(position) {
    document.getElementById('lat').value = position.coords.latitude;
    document.getElementById('lnt').value = position.coords.longitude;
  }
</script>
</body>
</html>

