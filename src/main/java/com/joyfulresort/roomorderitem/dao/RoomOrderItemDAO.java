package com.joyfulresort.roomorderitem.dao;

import java.sql.Date;
import java.util.List;

import com.joyfulresort.roomorder.entity.RoomOrder;
import com.joyfulresort.roomorderitem.entity.RoomOrderItem;

public interface RoomOrderItemDAO {

	int insert(RoomOrderItem entity);

	int update(RoomOrderItem entity);
	 
	RoomOrderItem getByRoomOrderItemId(Integer id);
	
	List<RoomOrderItem> getByRoomTypeId(Integer id);
	
	List<RoomOrderItem> getByRoomOrderId(Integer id);
	
	List<RoomOrderItem> getByRoomOrder(RoomOrder roomOrder);
	
	List<RoomOrderItem> getByDate(Date date);
	
	List<RoomOrderItem> getAll();
	
//	List<RoomOrderItem> getByCompositeQuery(Map<String, String> map);
//	
//	List<RoomOrderItem> getAll(int currentPage);
//	
//	long getTotal();

	
	
}
