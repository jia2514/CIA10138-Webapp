package com.joyfulresort.roomorderitem.service;

import java.util.List;

import com.joyfulresort.roomorder.entity.RoomOrder;
import com.joyfulresort.roomorderitem.dao.RoomOrderItemDAO;
import com.joyfulresort.roomorderitem.dao.RoomOrderItemDAOImpl;
import com.joyfulresort.roomorderitem.entity.RoomOrderItem;

public class RoomOrderItemServiceImpl implements RoomOrderItemService {

	// 一個 service 實體對應一個 dao 實體
	private RoomOrderItemDAO dao;

	public RoomOrderItemServiceImpl() {
		// TODO Auto-generated constructor stub
		dao = new RoomOrderItemDAOImpl();
	}

	@Override
	public int addRoomOrderItem(RoomOrderItem roomOrderItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateRoomOrderItem(RoomOrderItem roomOrderItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RoomOrderItem getRoomOrderItemByRoomOrderItemId(Integer roomOrderItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoomOrderItem> getRoomOrderItemByRoomOrder(RoomOrder roomOrder) {
		return dao.getByRoomOrder(roomOrder);
	}

	@Override
	public List<RoomOrderItem> getRoomOrderItemByRoomTypeId(Integer roomTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoomOrderItem> getRoomOrderItemByRoomOrderId(Integer roomOrderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoomOrderItem> getAllRoomOrderItem() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<RoomOrderItem> getAllRoomOrderItems(int currentPage) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getPageTotal() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public List<RoomOrderItem> getRoomOrderItemsByCompositeQuery(Map<String, String[]> map) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
