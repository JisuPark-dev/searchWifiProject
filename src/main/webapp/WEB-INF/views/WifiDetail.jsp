<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h1>와이파이 정보 구하기</h1>
        <button onclick="history.back()">뒤로가기</button>
        <a href="/">홈</a>
        <span>|</span>
        <a href="search-history">위치 히스토리 목록</a>
        <span>|</span>
        <a href="show-wifi">OPEN API 와이파이 정보 가져오기</a>
        <span>|</span>
        <a href="show-bookmark">북마크 보기</a>
        <span>|</span>
        <a href="manage-bookmark-group">북마크 그룹 관리</a>
        <p></p>
        <form action="create-bookmark">
            <select name ="bookmark-group">
                <c:forEach var="bookmarkGroups" items="${bookmarkGroups}">
                    <option value = "${bookmarkGroups.name}">${bookmarkGroups.name}</option>
                </c:forEach>
            </select>
<%--            ID, 북마크 이름, 와이파이명, 등록일자, 비고--%>
            <input type="hidden" id="wifi-name" name="wifi-name" value="${wifiDetail.name}">
            <input type="hidden" id="wifi-id" name="wifi-id" value="${wifiDetail.id}">
            <input type = "submit" value="북마크 추가하기">
        </form>
        <table>
            <tr>
                <td>거리</td>
                <td>${distance}</td>
            </tr>
            <tr>
                <td>관리번호</td>
                <td>${wifiDetail.id}</td>
            </tr>
            <tr>
                <td>자치구</td>
                <td>${wifiDetail.region}</td>
            </tr>
            <tr>
                <td>와이파이명</td>
                <td>${wifiDetail.name}</td>
            </tr>
            <tr>
                <td>도로명주소</td>
                <td>${wifiDetail.address1}</td>
            </tr>
            <tr>
                <td>상세주소</td>
                <td>${wifiDetail.address2}</td>
            </tr>
            <tr>
                <td>설치위치(층)</td>
                <td>${wifiDetail.floor}</td>
            </tr>
            <tr>
                <td>설치유형</td>
                <td>${wifiDetail.installType}</td>
            </tr>
            <tr>
                <td>설치기관</td>
                <td>${wifiDetail.installMby}</td>
            </tr>
            <tr>
                <td>서비스구분</td>
                <td>${wifiDetail.serviceType}</td>
            </tr>
            <tr>
                <td>망종류</td>
                <td>${wifiDetail.cmcwr}</td>
            </tr>
            <tr>
                <td>설치연도</td>
                <td>${wifiDetail.installYear}</td>
            </tr>
            <tr>
                <td>실내외구분</td>
                <td>${wifiDetail.inout}</td>
            </tr>
            <tr>
                <td>WIFI접속환경</td>
                <td>${wifiDetail.remars3}</td>
            </tr>
            <tr>
                <td>X좌표</td>
                <td>${wifiDetail.lat}</td>
            </tr>
            <tr>
                <td>Y좌표</td>
                <td>${wifiDetail.lnt}</td>
            </tr>
            <tr>
                <td>작업일자</td>
                <td>${wifiDetail.workTime}</td>
            </tr>
        </table>
    </body>
</html>
