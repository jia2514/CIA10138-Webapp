<%@page import="com.joyfulresort.roomschedule.entity.RoomScheduleCount"%>
<%@page import="java.sql.Date"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%@ include file="/backend/htmlfile/css.html" %>


<style type="text/css">
     * {
       padding: 0;
       margin: 0;
     }
     ol,
     ul,
     li {
       list-style: none;
     }
     :root {
       /* 定义变量 --变量名 通过var得到变量值 
   		(1)  定义到根元素  => 页面中都就可以使用
           (2)  定义到父元素  => 子元素可以使用*/
       --line-height: 40px;
     }
     .calendar {
       width: 300px;
       margin: 50px auto;
       background-color: aliceblue;
       padding: 10px;
     }
     .calendar-header {
       display: flex;
       justify-content: space-between;
       font-size: 14px;
       line-height: 40px;
     }
     .calendar-title {
       display: flex;
       justify-content: space-between;
       font-size: 14px;
       line-height: 40px;
     }
     .calendar .calendar-list {
       display: flex;
       flex-wrap: wrap;
       text-align: center;
       font-size: 14px;
       line-height: var(--line-height);
     }
     .calendar .calendar-list li {
       flex: 1;
       flex-basis: -webkit-calc(100% / 7);
       border: 1px solid transparent;
       box-sizing: border-box;
       color: black;
     }
     .calendar-list li:hover {
       border-color: dodgerblue;
     }

     .calendar .calendar-list .qday {
       background-color: pink;
     }

     .calendar .calendar-list .prevMonth,
     .calendar .calendar-list .nextMonth {
       color: #666;
     }
     .calendar .calendar-list .qMonth {
       color: black;
       font-weight: 500;
     }
     .my_empty_calendar{
     width:80%;
     height:50%
     }
     
     .fc .fc-toolbar.fc-header-toolbar {
    margin-bottom: 0.5em;
}
     .fc .fc-toolbar-title {
    font-size: 1.2em;
    margin: 0px;
}
.fc .fc-daygrid-body-natural .fc-daygrid-day-events {
    margin-bottom: 0;
}

.fc .fc-button {
   
    border-radius: 0.25em;
    
    font-size: 0.8em;
    font-weight: 400;
    line-height: 1.5;
    padding: 0.4em 0.65em;
    text-align: center;
    
}


.fc .fc-daygrid-day-top {
    display: inline-block;
   
}

a.fc-daygrid-day-number{
font-size: 1em;
}
.fc .fc-daygrid-body-unbalanced .fc-daygrid-day-events {
    min-height: 1.5em;
    position: relative;
}

.fc-h-event .fc-event-title {
   font-size: 1.2em;
}
   </style>




<title>Demo</title>

</head>

<body>

<%@ include file="/backend/htmlfile/content1.html" %>
	
	<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded ">
    <div class="G3_content">

<div class="my_empty_calendar">
	<div id='calendar'></div>
	
	</div>
	
	
	
	
	
	

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


var listQuery = JSON.parse('<%= jsonStr %>');
console.log(listQuery);

  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
	
    var eventList = [];
    var listFirst = listQuery[0];
    var startDate = listFirst[1];
    var roomTypeId = 0;
    
    var emptyNum = null;
    listQuery.forEach(function(obj) {
    	emptyNum = obj[3];
    	var textcolor = null;
    	var saleStatus = null;
    	roomTypeId = obj[0];
    	if(emptyNum==0){
			textcolor = '#C0C0C0';
			saleState = '已無空房';
    	}else if(emptyNum==1){
			textcolor = 'red';
			saleState = '即將滿房';
    	}else{
			textcolor = 'green';
			saleState = '熱銷中';
    	}
    	   	
    	eventList.push({
        	 title: '剩餘:' + emptyNum +'間', 
             start: obj[1],
             backgroundColor: 'transparent',
             borderColor: 'transparent',
             textColor: textcolor
        });
    });
    
    
    
    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: '',
            center: 'title',
            right: 'prev,next today',
            classNames: 'calendar-title'
        },
        
          select: function(info) {
            alert('selected ' + info.startStr + ' to ' + info.endStr);
         
            var startDate = new Date(info.startStr);
            var endDate = new Date(info.endStr);

            var selectedData = listQuery.filter(function(obj) {
                var objDate = new Date(obj[1]);
                return objDate >= startDate && objDate <= endDate;
            });
            var minEmptyRooms = selectedData.reduce(function(min, obj) {
                return Math.min(min, obj[3]);
            }, Infinity);

            	
            var customPageURL = "${pageContext.request.contextPath}/backend/roomorder/addRoomOrder.jsp?"+"roomTypeId1="+roomTypeId+"&checkindate="+info.startStr+"&checkoutdate="+info.endStr+"&minEmptyRooms1="+minEmptyRooms;
            
            // 导航到自定义页面
            window.location.href = customPageURL;
          },
        
        
        
        initialDate: startDate,
        navLinks: false,
        businessHours: false,
        editable: false,
        selectable: true,
        height: 'auto',
        aspectRatio: 2,
        events: eventList,
       
        
    });

    window.addEventListener('resize', function() {
        calendar.render();
    });

    
    
    
    
    
    
    
    
    calendar.render();
});

</script>



</body>
</html>