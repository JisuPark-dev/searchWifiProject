<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "와이파이 정보 구하기" %></h1>

<a href="/Gradle___com_example___zerobase_wifi_mission_1_0_SNAPSHOT_war/">홈</a>
<span>|</span>
<a href="search-history">위치 히스토리 목록</a>
<span>|</span>
<a href="show-wifi">OPEN API 와이파이 정보 가져오기</a>
<span>|</span>
<a href="sqlite-test">sqlite test</a>
<form action="searchWifi-20" method="post">
  LAT: <input type="number" name="LAT"  />
  LNT: <input type="number" name="LNT"  />
  <button type="submit">근처 와이파이 정보 보기</button>
</form>
</body>
</html>