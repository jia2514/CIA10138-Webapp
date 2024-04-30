package com.joyfulresort.roomorder.dao;


import static util.Constants.PAGE_MAX_RESULT;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.joyfulresort.roomorder.entity.RoomOrder;

import util.HibernateUtil;

public class RoomOrderDAOImpl implements RoomOrderDAO {

	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;

	public RoomOrderDAOImpl() {
		// TODO Auto-generated constructor stub
		factory = HibernateUtil.getSessionFactory();
	}

	// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
	// 以避免請求執行緒共用了同個 Session
	private Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public int insert(RoomOrder roomOrder) {
		// 回傳給 service 剛新增成功的自增主鍵值
		
//		Timestamp time = new Timestamp(System.currentTimeMillis());
//		roomOrder.setOrderDate(time);
//		System.out.println("48"+roomOrder);
		return (Integer) getSession().save(roomOrder);
	}

	@Override
	public int update(RoomOrder entity) {
		try {
			getSession().update(entity);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public RoomOrder getById(Integer id) {
		return getSession().get(RoomOrder.class, id);
	}

	@Override
	public List<RoomOrder> getAll() {
		return getSession().createQuery("from RoomOrder", RoomOrder.class).list();
	}

	@Override
	public List<RoomOrder> getByCompositeQuery(Map<String, String> map) {
		if (map.size() == 0)
			return getAll();

		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<RoomOrder> criteria = builder.createQuery(RoomOrder.class);
		Root<RoomOrder> root = criteria.from(RoomOrder.class);
		
		
		List<Predicate> predicates = new ArrayList<>();
        
		Path<Date> checkInDate = root.get("checkInDate");
		if (map.containsKey("startcheckindate") && map.containsKey("endcheckindate"))
			predicates.add(builder.between(checkInDate, Date.valueOf(map.get("startcheckindate")), Date.valueOf(map.get("endcheckindate"))));
		
		System.out.println("84" + predicates);
		
		Path<Date> checkOutDate = root.get("checkOutDate");
		if (map.containsKey("startcheckoutdate") && map.containsKey("endcheckoutdate"))
			predicates.add(builder.between(checkOutDate, Date.valueOf(map.get("startcheckoutdate")), Date.valueOf(map.get("endcheckoutdate"))));
		
		
		Path<Date> orderDate = root.get("orderDate");
		if (map.containsKey("startorderdate") && map.containsKey("endorderdate"))
			predicates.add(builder.between(orderDate, Date.valueOf(map.get("startorderdate")), Date.valueOf(map.get("endorderdate"))));


		for (Map.Entry<String, String> row : map.entrySet()) {
			Path<String> memberName = root.get("member").get("memberName");
			if ("membername".equals(row.getKey())) {
				predicates.add(builder.like(memberName, "%" + row.getValue() + "%"));
			}

			if ("memberid".equals(row.getKey())) {
				predicates.add(builder.equal(root.get("member").get("memberId"), row.getValue()));
				System.out.println("105" + row.getValue());
			}

			
			
			if ("startcheckindate".equals(row.getKey())) {
				if (!map.containsKey("endcheckindate"))
					predicates.add(builder.greaterThanOrEqualTo(checkInDate, Date.valueOf(row.getValue())));
			}

			System.out.println("114" + predicates);
			
			if ("endcheckindate".equals(row.getKey())) {
				if (!map.containsKey("startcheckindate"))
					predicates.add(builder.lessThanOrEqualTo(checkInDate, Date.valueOf(row.getValue())));
			}
			
			if ("startcheckoutdate".equals(row.getKey())) {
				if (!map.containsKey("endcheckoutdate"))
					predicates.add(builder.greaterThanOrEqualTo(checkOutDate, Date.valueOf(row.getValue())));
			}

			if ("endcheckoutdate".equals(row.getKey())) {
				if (!map.containsKey("startcheckoutdate"))
					predicates.add(builder.lessThanOrEqualTo(checkOutDate, Date.valueOf(row.getValue())));
			}
			
			if ("startorderdate".equals(row.getKey())) {
				if (!map.containsKey("endorderdate"))
					predicates.add(builder.greaterThanOrEqualTo(orderDate, Date.valueOf(row.getValue())));
			}

			if ("endorderdate".equals(row.getKey())) {
				if (!map.containsKey("startorderdate"))
					predicates.add(builder.lessThanOrEqualTo(orderDate, Date.valueOf(row.getValue())));
			}

			System.out.println("141" + predicates);
			
		}

		System.out.println("145" + predicates);
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		criteria.orderBy(builder.asc(root.get("roomOrderId")));
		System.out.println("149" + criteria);
		TypedQuery<RoomOrder> query = getSession().createQuery(criteria);
		System.out.println("151" + query);
		System.out.println("152" + query.getResultList());
		return query.getResultList();
	}

	@Override
	public List<RoomOrder> getAll(int currentPage) {
		int first = (currentPage - 1) * PAGE_MAX_RESULT;
		return getSession().createQuery("from RoomOrder", RoomOrder.class)
				.setFirstResult(first)
				.setMaxResults(PAGE_MAX_RESULT)
				.list();
	}

	@Override
	public long getTotal() {
		return getSession().createQuery("select count(*) from RoomOrder", Long.class).uniqueResult();
	}

	
}
