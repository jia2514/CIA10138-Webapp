package com.joyfulresort.room.entity;

import com.joyfulresort.roomtype.entity.RoomType;

public interface RoomDAO {

	Long getValidByRoomType(RoomType entity);
	
}
