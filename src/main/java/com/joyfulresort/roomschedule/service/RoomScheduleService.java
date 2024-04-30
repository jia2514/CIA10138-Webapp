package com.joyfulresort.roomschedule.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.joyfulresort.roomschedule.entity.RoomScheduleCount;

public interface RoomScheduleService {

//	List<RoomScheduleCount> getScheduleCountByRoomTypeId(Integer id);
//	
//	List<RoomScheduleCount> getScheduleCountByOneScheduleDate(Date date);
//	
//	List<RoomScheduleCount> getScheduleCountByRoomTypeIdIn2Month(Integer id, Date date);
//	
//	List<RoomScheduleCount> getScheduleCountByRoomTypeIdAndScheduleDates(Integer id, Date date1, Date date2);
//	
//	List<RoomScheduleCount> getScheduleCountByScheduleDates(Date date1, Date date2);
//	
	String getALLIn2Month();
	
//	List<RoomScheduleCount> getAllScheduleCount();
	
	String getByCompositeQuery(Map<String, String[]> map);
	
}
