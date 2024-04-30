package com.joyfulresort.roomtype.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import util.HibernateUtil;

public class RoomTypeDAOImpl implements RoomTypeDAO{

	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
		private SessionFactory factory;

		public RoomTypeDAOImpl() {
			// TODO Auto-generated constructor stub
			factory = HibernateUtil.getSessionFactory();
		}

		// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
		// 以避免請求執行緒共用了同個 Session
		private Session getSession() {
			return factory.getCurrentSession();
		}
	
	
	@Override
	public RoomType getById(Integer id) {
		return getSession().get(RoomType.class, id);
	}

}
