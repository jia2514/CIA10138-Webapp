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
        
        let result = [];
        for (let objkey in obj) {

        	if (objkey !== 'total') {
                result.push(objkey+'號房剩餘'+obj[objkey]+'間');
            }
        }
        eventList.push({
            title: saleState,
            start: key,
            description: result,
            backgroundColor: 'transparent',
            borderColor: 'transparent',
            textColor: textColor
        });
    }

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

            var selectedData = query.filter(function(obj) {
                var objDate = new Date(obj[1]);
                return objDate >= startDate && objDate <= endDate;
            });
            
            var minEmptyRoomsById = {};

        
         selectedData.forEach(function(obj) {
             var roomTypeId = obj[0]; 
             var emptyRooms = obj[3]; 

             
             if (minEmptyRoomsById.hasOwnProperty(roomTypeId)) {
                 minEmptyRoomsById[roomTypeId] = Math.min(minEmptyRoomsById[roomTypeId], emptyRooms);
             } else { 
                 minEmptyRoomsById[roomTypeId] = emptyRooms;
             }
         });

        var emptyRoomURL = '';
        var paramCount =0;
         for (var roomTypeId in minEmptyRoomsById) {
             if (minEmptyRoomsById.hasOwnProperty(roomTypeId)) {
                 var minEmptyRooms = minEmptyRoomsById[roomTypeId];
                 paramCount+=1;
                 emptyRoomURL+=("&roomTypeId=" + roomTypeId + "&minEmptyRooms=" + minEmptyRooms );
             }
         }

            	
            var customPageURL = "${pageContext.request.contextPath}/backend/roomorder/addRoomOrder.jsp?"+"checkindate="+info.startStr+"&checkoutdate="+info.endStr+emptyRoomURL+"&paramCount="+paramCount;
           
            window.location.href = customPageURL;
          },
        initialDate: startDate,
        navLinks: true,
        businessHours: false,
        editable: false,
        selectable: true,
        height: 'auto',
        aspectRatio: 2,
        events: eventList,
        eventMouseEnter: function(info) {
            // 当鼠标进入日历事件时显示 popover
            $(info.el).popover({
                title: info.event.title,
                content: info.event.extendedProps.description.join("<br>"),
                trigger: 'hover',
                placement: 'top',
                container: 'body',
                html: true // 允许内容包含 HTML
            });
            $(info.el).popover('show');
        },
        
    });

    window.addEventListener('resize', function() {
        calendar.render();
    });

    calendar.render();
});

  
  
  
</script>



</body>
</html>