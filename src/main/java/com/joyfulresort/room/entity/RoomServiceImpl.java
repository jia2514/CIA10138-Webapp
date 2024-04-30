package com.joyfulresort.room.entity;

import com.joyfulresort.roomtype.entity.RoomType;
import com.joyfulresort.roomtype.entity.RoomTypeServiceImpl;

public class RoomServiceImpl implements RoomService{

	// 一個 service 實體對應一個 dao 實體
			private RoomDAO dao;
			
			public RoomServiceImpl() {
				// TODO Auto-generated constructor stub
				dao = new RoomDAOImpl();
			}
	
	
	@Override
	public Long getValidByRoomType(int roomTypeId) {
		RoomTypeServiceImpl rtService = new RoomTypeServiceImpl();
		RoomType roomType = rtService.getRoomTypeByRoomTypeId(roomTypeId);
		
		return dao.getValidByRoomType(roomType);
	}

}
