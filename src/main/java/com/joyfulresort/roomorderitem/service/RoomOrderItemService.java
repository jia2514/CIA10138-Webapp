package com.joyfulresort.roomorderitem.service;

import java.util.List;

import com.joyfulresort.roomorder.entity.RoomOrder;
import com.joyfulresort.roomorderitem.entity.RoomOrderItem;

public interface RoomOrderItemService {

	int addRoomOrderItem(RoomOrderItem roomOrderItem);
	
	int updateRoomOrderItem(RoomOrderItem roomOrderItem);
	
	RoomOrderItem getRoomOrderItemByRoomOrderItemId(Integer roomOrderItemId);
	
	List<RoomOrderItem> getRoomOrderItemByRoomTypeId(Integer roomTypeId);
	
	List<RoomOrderItem> getRoomOrderItemByRoomOrderId(Integer roomOrderId);
	
	List<RoomOrderItem> getRoomOrderItemByRoomOrder(RoomOrder roomOrder);
	
	List<RoomOrderItem> getAllRoomOrderItem();
	
//	List<RoomOrderItem> getAllRoomOrderItems(int currentPage);
//	
//	int getPageTotal();
//	
//	List<RoomOrderItem> getRoomOrderItemsByCompositeQuery(Map<String, String[]> map);
//	
}
