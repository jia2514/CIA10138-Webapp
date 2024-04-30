package com.joyfulresort.roomorder.dao;

import java.util.List;
import java.util.Map;

import com.joyfulresort.roomorder.entity.RoomOrder;



public interface RoomOrderDAO {

	
	int insert(RoomOrder entity);

	int update(RoomOrder entity);
	 
	RoomOrder getById(Integer id);
	
	List<RoomOrder> getAll();
	
	List<RoomOrder> getByCompositeQuery(Map<String, String> map);
	
	List<RoomOrder> getAll(int currentPage);
	
	long getTotal();
	
	
}
