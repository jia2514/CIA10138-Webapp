package com.joyfulresort.roomschedule.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface RoomScheduleDAO {

	List<Object[]> getAllByCurDate();
	
	List<Object[]> getOneByCurDate(Map<String, String> map);

	List<Object[]> getByCompositeQuery(Map<String, String> map);

}
