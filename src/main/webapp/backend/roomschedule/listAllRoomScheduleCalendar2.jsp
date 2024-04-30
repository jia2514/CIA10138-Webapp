<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="com.joyfulresort.roomschedule.entity.RoomScheduleCount" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%@ include file="/backend/htmlfile/css.html" %>

<style type="text/css">
.calendar-container {
    display: flex;
    overflow-x: auto;
    scroll-snap-type: x mandatory;
    -webkit-overflow-scrolling: touch;
}

.month {
    flex: 1;
    scroll-snap-align: center;
    border: 1px solid #ccc;
    margin: 10px;
    padding: 10px;
    box-sizing: border-box;
}

.week {
    display: flex;
}

.day {
    flex: 1;
    text-align: center;
    border: 1px solid #ccc;
    padding: 5px;
    width: 40px; /* 调整单元格宽度 */
}

table {
    border-collapse: collapse; /* 去除表格边框重叠 */
    width: 100%; /* 设置表格宽度为100% */
}

th, td {
    border: 1px solid #ccc; /* 设置单元格边框 */
}

/* 调整表格标题字体样式和大小 */
th {
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    padding: 10px;
}

/* 调整表格单元格字体样式和大小 */
td {
    font-size: 14px;
    text-align: center;
    padding: 10px;
}

/* 鼠标悬停时改变单元格背景颜色 */
td:hover {
    background-color: #f0f0f0;
}

</style>



<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #calendar {
    max-width: 1100px;
    margin: 0 auto;
  }

</style>




<title>Demo</title>

</head>

<body>

<%@ include file="/backend/htmlfile/content1.html" %>
	
	<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded ">
    <div class="G3_content">


	<h4>空房查詢</h4>
	 
	
	<div id='calendar'></div>
	
	
	
	
	
	
	
	

	<a href="${pageContext.request.contextPath}/backend/roomschedule/roomscheduleselect.jsp">回首頁</a>


    </div>
    </div>
    </div>

<%@ include file="/backend/htmlfile/content2.html" %>
<%@ include file="/backend/htmlfile/script.html" %>


<%
	String jsonStr = (String)request.getAttribute("roomScheduleCount");
	
%>

<script src="<c:url value='/backend/static/js/index.global.js'></c:url>"></script>

<script>


var query = JSON.parse('<%= jsonStr %>');


  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    
    var first = query[0];
    var startDate = first[1]; //第一天
   
    var eventSort = [];

    query.forEach(function(obj) {
        var roomTypeInfo = [];						
  
        if (eventSort[obj[1]]) {
            eventSort[obj[1]].total += obj[3];
            eventSort[obj[1]][obj[0]] = obj[3];
        } else {
            roomTypeInfo.total = obj[3];
            roomTypeInfo[obj[0]] = obj[3];
            
            eventSort[obj[1]] = roomTypeInfo;
        }
    });

    console.log(eventSort);
    
    
    
    
   
    var eventList = [];
    
    for (var key in eventSort) {
        var obj = eventSort[key];
        var saleState, textColor;

        if (obj.total == 0) {
            textColor = '#C0C0C0';
            saleState = '已無空房';
        } else if (obj.total < 3) {
            textColor = 'red';
            saleState = '即將滿房';
        } else {
            textColor = 'green';
            saleState = '熱銷中';
        }

        eventList.push({
            title: saleState,
            start: key, 
            backgroundColor: 'transparent',
            borderColor: 'transparent',
            textColor: textColor
        });
    }

    
    
    
    var calendar = new FullCalendar.Calendar(calendarEl, {
    	 headerToolbar: {
    	        left: 'prev,next today',
    	        center: 'title',
    	        right: ''
    	      },
    	      initialDate: startDate,
    	      navLinks: true, // can click day/week names to navigate views
    	      businessHours: false, // display business hours
    	      editable: false,
    	      selectable: true,
    	      events: eventList
    	    });

    	    calendar.render();
  });

</script>



</body>
</html>