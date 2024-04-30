package com.joyfulresort.roomtype.entity;

public class RoomTypeServiceImpl implements RoomTypeService{

	private RoomTypeDAO dao;
	
	public RoomTypeServiceImpl() {
		// TODO Auto-generated constructor stub
		dao = new RoomTypeDAOImpl();
	}

	@Override
	public RoomType getRoomTypeByRoomTypeId(Integer roomTypeId) {
		return dao.getById(roomTypeId);
	}
	
	
	

}
