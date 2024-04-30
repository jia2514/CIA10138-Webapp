package com.joyfulresort.room.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.joyfulresort.roomtype.entity.RoomType;

import util.HibernateUtil;

public class RoomDAOImpl implements RoomDAO{

	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
		private SessionFactory factory;

		public RoomDAOImpl() {
			// TODO Auto-generated constructor stub
			factory = HibernateUtil.getSessionFactory();
		}

		// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
		// 以避免請求執行緒共用了同個 Session
		private Session getSession() {
			return factory.getCurrentSession();
		}

	
	
	@Override
	public Long getValidByRoomType(RoomType entity) {
		Long emptyCount = getSession().createQuery("select count(*) from Room r "
				+ "where r.roomType = :roomType and r.roomSaleState = 0 " , Long.class)
				.setParameter("roomType", entity)
				.uniqueResult();
		return emptyCount;
	}

}
