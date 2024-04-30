package com.joyfulresort.roomorderitem.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.joyfulresort.roomorder.entity.RoomOrder;
import com.joyfulresort.roomorderitem.entity.RoomOrderItem;

import util.HibernateUtil;

public class RoomOrderItemDAOImpl implements RoomOrderItemDAO{

	
	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
		private SessionFactory factory;

		public RoomOrderItemDAOImpl() {
			// TODO Auto-generated constructor stub
			factory = HibernateUtil.getSessionFactory();
		}

		// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
		// 以避免請求執行緒共用了同個 Session
		private Session getSession() {
			return factory.getCurrentSession();
		}

		@Override
		public int insert(RoomOrderItem entity) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int update(RoomOrderItem entity) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public RoomOrderItem getByRoomOrderItemId(Integer id) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		@Override
		public List<RoomOrderItem> getByRoomTypeId(Integer id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomOrderItem> getByRoomOrderId(Integer id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<RoomOrderItem> getByDate(Date date) {
			// TODO Auto-generated method stub
			return null;
		}
		
		

		@Override
		public List<RoomOrderItem> getByRoomOrder(RoomOrder roomOrder) {
			List<RoomOrderItem> list = getSession().createQuery("from RoomOrderItem where roomOrder= :roomOrder", RoomOrderItem.class)
					.setParameter("roomOrder", roomOrder).list();
			return list;
		}

		@Override
		public List<RoomOrderItem> getAll() {
			// TODO Auto-generated method stub
			return null;
		}

		

//		@Override
//		public List<RoomOrderItem> getByCompositeQuery(Map<String, String> map) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public List<RoomOrderItem> getAll(int currentPage) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getTotal() {
//			// TODO Auto-generated method stub
//			return 0;
//		}

		
	
	
	

}
