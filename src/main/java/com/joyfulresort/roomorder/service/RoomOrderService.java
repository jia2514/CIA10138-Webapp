package com.joyfulresort.roomorder.service;

import java.util.List;
import java.util.Map;

import com.joyfulresort.roomorder.entity.RoomOrder;


public interface RoomOrderService {

	int cancelRoomOrder(Integer roomOrderId);
	
	int completeRoomOrder(Integer roomOrderId);
	
	int refundRoomOrder(Integer roomOrderId);
	
	RoomOrder addRoomOrder(RoomOrder roomOrder);
	
	RoomOrder updateRoomOrder(RoomOrder roomOrder);
	
	RoomOrder getRoomOrderByRoomOrderId(Integer roomOrderId);
	
	List<RoomOrder> getAllRoomOrders(int currentPage);
	
	int getPageTotal();
	
	List<RoomOrder> getRoomOdersByCompositeQuery(Map<String, String[]> map);
	
}
