package com.joyfulresort.roomschedule.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.joyfulresort.roomschedule.dao.RoomScheduleDAO;
import com.joyfulresort.roomschedule.dao.RoomScheduleDAOImpl;
import com.joyfulresort.roomschedule.entity.RoomScheduleCount;

public class RoomScheduleServiceImpl implements RoomScheduleService {

	// 一個 service 實體對應一個 dao 實體
	private RoomScheduleDAO dao;

	public RoomScheduleServiceImpl() {
		// TODO Auto-generated constructor stub
		dao = new RoomScheduleDAOImpl();
	}

	@Override
	public String getALLIn2Month() {

		List<Object[]> list = dao.getAllByCurDate();
		String jsonStr = new JSONArray(list).toString();
		System.out.println("List to JSON: " + jsonStr);

//		List<RoomScheduleCount> rsList = new ArrayList<>();
//
//		for (Object[] object : list) {
//			RoomScheduleCount roomScheduleCount = new RoomScheduleCount();
//			roomScheduleCount.setRoomTypeId((Integer) object[0]);
//			roomScheduleCount.setRoomScheduleDate((Date) object[1]);
//			roomScheduleCount.setEmptyCount((Long) object[3]);
//			rsList.add(roomScheduleCount);
//		}

		return jsonStr;

	}

	@Override
	public String getByCompositeQuery(Map<String, String[]> map) {
		Map<String, String> query = new HashMap<>();
		// Map.Entry即代表一組key-value

		Set<Map.Entry<String, String[]>> entry = map.entrySet();

		for (Map.Entry<String, String[]> row : entry) {
			System.out.println("52+" + row.getKey());
			System.out.println("53+" + row.getValue()[0]);
			String key = row.getKey();
			// 因為請求參數裡包含了action，做個去除動作
			if ("action".equals(key)) {
				continue;
			}
			// 若是value為空即代表沒有查詢條件，做個去除動作
			String value = row.getValue()[0]; // getValue拿到一個String陣列, 接著[0]取得第一個元素檢查
			if (value == null || value.isEmpty()) {
				continue;
			}

			query.put(key, value);
		}

		if (!query.containsKey("startquerydate") && query.containsKey("endquerydate")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(Date.valueOf(query.get("endquerydate")));
			calendar.add(Calendar.MONTH, -2);
			Date endDate2month = new Date(calendar.getTimeInMillis());
			query.put("startquerydate", endDate2month.toString());

		}

		List<Object[]> list = null;

		if (query.containsKey("roomTypeId") && !query.containsKey("startquerydate")
				&& !query.containsKey("endquerydate")) {
			list = dao.getOneByCurDate(query);
		} else {

			list = dao.getByCompositeQuery(query);
		}
		String jsonStr = new JSONArray(list).toString();
		System.out.println("List to JSON: " + jsonStr);
//		List<RoomScheduleCount> rsList = new ArrayList<>();
//
//
//		for (Object[] object : list) {
//			RoomScheduleCount roomScheduleCount = new RoomScheduleCount();
//			roomScheduleCount.setRoomTypeId((Integer) object[0]);
//			roomScheduleCount.setRoomScheduleDate((Date) object[1]);
//			roomScheduleCount.setEmptyCount((Long) object[3]);
//			rsList.add(roomScheduleCount);
//		}

		return jsonStr;

	}

//	@Override
//	public List<RoomScheduleCount> getScheduleCountByRoomTypeIdIn2Month(Integer id, Date date) {
//		Date dateNow = new Date(System.currentTimeMillis());
//		Date date2 = null;
////		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = Calendar.getInstance();
//		if(date.after(dateNow)) {
//			
//	        calendar.setTime(dateNow);
//	        // 增加兩個月
//	        calendar.add(Calendar.MONTH, 2);
//	        date2 = new Date(calendar.getTimeInMillis());
//			
//		}else {
//			
//	        calendar.setTime(date);
//	        // 增加兩個月
//	        calendar.add(Calendar.MONTH, 2);
//	        date2 = new Date(calendar.getTimeInMillis());
//		}
//		
//		
//		
//		RoomTypeDAOImpl rtDAO = new RoomTypeDAOImpl();
//		RoomType roomType = rtDAO.getById(id);
//		
//		RoomDAOImpl rDAO = new RoomDAOImpl();
//		
//		List<Object[]> list = dao.getByRoomTypeIdAndScheduleDate(roomType, date, date2);
//		List<RoomScheduleCount> rsList = new ArrayList<>();
//		for(Object[] object : list) {
//			RoomScheduleCount roomScheduleCount = new RoomScheduleCount();
//			roomType = (RoomType)object[0];
//			roomScheduleCount.setRoomTypeId(roomType.getRoomTypeId());
//			roomScheduleCount.setRoomScheduleDate((Date)object[1]);
//			Long count =rDAO.getValidByRoomType(roomType);
//			
//			roomScheduleCount.setEmptyCount(count-(Long)object[2]);
//			rsList.add(roomScheduleCount);
//		}
//		
//		return rsList;
//	}

}
