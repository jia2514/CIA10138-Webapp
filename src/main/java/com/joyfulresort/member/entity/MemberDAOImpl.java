package com.joyfulresort.member.entity;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import util.HibernateUtil;

public class MemberDAOImpl implements MemberDAO {

	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;

	public MemberDAOImpl() {
		// TODO Auto-generated constructor stub
		factory = HibernateUtil.getSessionFactory();
	}

	// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
	// 以避免請求執行緒共用了同個 Session
	private Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public Member getById(Integer id) {
		return getSession().get(Member.class, id);
	}

	@Override
	public Member getByPhone(String phone) {
		List<Member> list = getSession().createQuery("from Member where memberPhone= :memberPhone", Member.class)
				.setParameter("memberPhone", phone).list();
		Member member = null;
		for (Member mem : list) {
			member = mem;
		}
		return member;
	}

	@Override
	public Member getByName(String name) {
		List<Member> list = getSession().createQuery("from Member where memberName= :memberName", Member.class)
				.setParameter("memberName", name).list();
		Member member = null;
		for (Member mem : list) {
			member = mem;
		}

		return member;
	}

}
