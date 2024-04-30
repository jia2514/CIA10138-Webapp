package com.joyfulresort.roomorder.service;

import static util.Constants.PAGE_MAX_RESULT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.joyfulresort.roomorder.dao.RoomOrderDAO;
import com.joyfulresort.roomorder.dao.RoomOrderDAOImpl;
import com.joyfulresort.roomorder.entity.RoomOrder;


//搭配 JSP / Thymeleaf 後端渲染畫面，將交易動作至於 view filter
public class RoomOrderServiceImpl implements RoomOrderService{

	// 一個 service 實體對應一個 dao 實體
		private RoomOrderDAO dao;
		
		public RoomOrderServiceImpl() {
			// TODO Auto-generated constructor stub
			dao = new RoomOrderDAOImpl();
		}
	
	@Override
	public RoomOrder addRoomOrder(RoomOrder roomOrder) {
		int roomOrderId = dao.insert(roomOrder);
		return getRoomOrderByRoomOrderId(roomOrderId);
	}

	@Override
	public RoomOrder updateRoomOrder(RoomOrder roomOrder) {
		int i = dao.update(roomOrder);
		return getRoomOrderByRoomOrderId(roomOrder.getRoomOrderId());
	}

	@Override
	public RoomOrder getRoomOrderByRoomOrderId(Integer roomOrderId) {
		return dao.getById(roomOrderId);
	}

	@Override
	public List<RoomOrder> getAllRoomOrders(int currentPage) {
		System.out.println("45+" + currentPage);
		System.out.println("46+" + dao.getAll(currentPage));
		return dao.getAll(currentPage);
	}

	@Override
	public int getPageTotal() {
		long total = dao.getTotal();
		// 計算每頁PAGE_MAX_RESULT筆的話總共有幾頁
		System.out.println("52+" + total);
		int pageQty = (int)(total % PAGE_MAX_RESULT == 0 ? (total / PAGE_MAX_RESULT) : (total / PAGE_MAX_RESULT + 1));
		System.out.println("54+" + pageQty);
		return pageQty;
	}

	@Override
	public List<RoomOrder> getRoomOdersByCompositeQuery(Map<String, String[]> map) {
		Map<String, String> query = new HashMap<>();
		// Map.Entry即代表一組key-value
		Set<Map.Entry<String, String[]>> entry = map.entrySet();
		
		for (Map.Entry<String, String[]> row : entry) {
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
		
	
		return dao.getByCompositeQuery(query);
	}

	@Override
	public int cancelRoomOrder(Integer roomOrderId) {
		RoomOrder roomOrder = dao.getById(roomOrderId);
		long miliseconds = System.currentTimeMillis();
		long checkInMiliseconds = roomOrder.getCheckInDate().getTime();
		long differenceInTime = checkInMiliseconds - miliseconds;
        int differenceInDays = (int)(differenceInTime / (1000 * 3600 * 24));

        if (differenceInDays <= 3) {
        	roomOrder.setRoomOrderState((byte)0);
        	roomOrder.setRefundState((byte)0);
            return dao.update(roomOrder);
        } else {
        	roomOrder.setRoomOrderState((byte)3);
        	roomOrder.setRefundState((byte)1);
        	return dao.update(roomOrder);
        }
	}

	@Override
	public int completeRoomOrder(Integer roomOrderId) {
		RoomOrder roomOrder = dao.getById(roomOrderId);
		roomOrder.setRoomOrderState((byte)2);
    	roomOrder.setRefundState((byte)0);
		return dao.update(roomOrder);
	}

	@Override
	public int refundRoomOrder(Integer roomOrderId) {
		RoomOrder roomOrder = dao.getById(roomOrderId);
		roomOrder.setRoomOrderState((byte)0);
    	roomOrder.setRefundState((byte)2);
		return dao.update(roomOrder);
	}

	
	
}
