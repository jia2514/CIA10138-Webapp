<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<%@ include file="/backend/htmlfile/css.html" %>

<style type="text/css">
body {
	padding-left: 20px;
}
table, th, td {
  border:1px solid black;
}
th, td {
	height: 50px;
	width: 100px;
}

input[type="submit"] {
    margin-top: 5px; /* 添加输入框和按钮之间的间距 */
    padding: 5px; /* 添加输入框和按钮的内边距 */
}

input[type="submit"] {
    background-color: #007bff; /* 按钮背景颜色 */
    color: #ffffff; /* 按钮文字颜色 */
    border: none; /* 移除按钮边框 */
    cursor: pointer; /* 鼠标悬停时显示手型 */
}

input[type="submit"]:hover {
    background-color: #0056b3; /* 鼠标悬停时按钮背景颜色 */
}

</style>



<title>Demo</title>
</head>
<body>
	<%@ include file="/backend/htmlfile/content1.html" %>
	
	<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded ">
    <div class="G3_content">
	
	
	<h2>查詢空房系統</h2>
	<c:if test="${not empty errorMsgs}"> 
	<font style="color:red">請修正以下錯誤:</font>
 	<ul> 
		<c:forEach var="message" items="${errorMsgs}"> 
 			<li style="color:red">${message.value}</li> 
 		</c:forEach> 
 	</ul> 
    </c:if>
	
	<div>
	<a href="${pageContext.request.contextPath}/roomschedule/roomschedule.do?action=getIn2Month">查詢近兩個月的所有空房</a>
	<br>
	<form action="${pageContext.request.contextPath}/roomschedule/roomschedule.do" method="post">
		<p><label>選擇房型：</label></p>
		<select name="roomTypeId">
			<option value="">請選擇房型</option>
			<option value="1">品趣客房(2人)</option>
			<option value="2">童旅主題房(2大2小)</option>
			<option value="3">童漾家庭房(4人)</option>
			
		</select>
		
		<p><label>查詢空房</label></p>
		<input type="date" name="startquerydate"> ～ <input type="date" name="endquerydate"><br>
		<p><input type="submit" value="送出"></p>
		<input type="hidden" name="action" value="getCompositeQuery">
	</form>
	
	</div>
    </div>
    </div>
	</div>

<%@ include file="/backend/htmlfile/content2.html" %>
<%@ include file="/backend/htmlfile/script.html" %>


</body>
</html>