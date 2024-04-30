package com.joyfulresort.roomschedule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;

import util.HibernateUtil;

public class RoomScheduleDAOImpl implements RoomScheduleDAO {

	// SessionFactory 為 thread-safe，可宣告為屬性讓請求執行緒們共用
	private SessionFactory factory;

	public RoomScheduleDAOImpl() {
		// TODO Auto-generated constructor stub
		factory = HibernateUtil.getSessionFactory();
	}

	// Session 為 not thread-safe，所以此方法在各個增刪改查方法裡呼叫
	// 以避免請求執行緒共用了同個 Session
	private Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public List<Object[]> getAllByCurDate() {

		String sql = "WITH RECURSIVE dates (v_date) AS " + " ( SELECT CURDATE() " + "   UNION ALL "
				+ "   SELECT v_date + INTERVAL 1 DAY " + "   FROM dates "
				+ "   WHERE v_date + INTERVAL 1 DAY <= ADDDATE(CURDATE(), INTERVAL 2 MONTH) - INTERVAL 1 DAY ) "
				+ " SELECT rt.room_type_id, " + " d.v_date, " + " COUNT(rs.room_schedule_id) as roomtotal, "
				+ " (SELECT COUNT(*) FROM room WHERE room_type_id = rt.room_type_id and room_sale_state = 0 ) - "
				+ " COUNT(rs.room_schedule_id) as remptyroomtotal " + " FROM dates d " + " CROSS JOIN room_type rt "
				+ " LEFT JOIN room_schedule rs on (d.v_date = rs.room_schedule_date AND rt.room_type_id = rs.room_type_id) "
				+ " GROUP BY rt.room_type_id, d.v_date " + " ORDER BY d.v_date, rt.room_type_id; ";

		List<Object[]> list = getSession().createSQLQuery(sql).addScalar("room_type_id", IntegerType.INSTANCE)
				.addScalar("v_date", DateType.INSTANCE).addScalar("roomtotal", LongType.INSTANCE)
				.addScalar("remptyroomtotal", LongType.INSTANCE).list();

		return list;
	}

	@Override
	public List<Object[]> getByCompositeQuery(Map<String, String> map) {

		StringBuilder sql = new StringBuilder();
		sql.append("WITH RECURSIVE dates (v_date) AS ");
		sql.append("( SELECT ? UNION ALL ");
		sql.append("SELECT v_date + INTERVAL 1 DAY FROM dates ");
		sql.append("WHERE v_date + INTERVAL 1 DAY <= ADDDATE(?, INTERVAL 2 MONTH) - INTERVAL 1 DAY ) ");
		sql.append("SELECT rt.room_type_id, d.v_date, COUNT(rs.room_schedule_id) as roomtotal, ");
		sql.append("(SELECT COUNT(*) FROM room WHERE room_type_id = rt.room_type_id and room_sale_state = 0 ) - ");
		sql.append("COUNT(rs.room_schedule_id) as remptyroomtotal FROM dates d ");
		sql.append("CROSS JOIN room_type rt ");
		sql.append(
				"LEFT JOIN room_schedule rs on (d.v_date = rs.room_schedule_date AND rt.room_type_id = rs.room_type_id) ");
//		sql.append("WHERE d.v_date >= ? AND d.v_date <= ? ");
//		sql.append("GROUP BY rt.room_type_id, d.v_date; ");

		Date startQueryDate = null;
		Date endQueryDate = null;
		Integer roomTypeId = 0;

		if (map.containsKey("roomTypeId")) {
			if (map.containsKey("startquerydate") && map.containsKey("endquerydate")) {
				startQueryDate = Date.valueOf(map.get("startquerydate"));
				endQueryDate = Date.valueOf(map.get("endquerydate"));
				roomTypeId = Integer.valueOf(map.get("roomTypeId"));
				sql.append(
						"WHERE d.v_date >= ? AND d.v_date <= ? AND rt.room_type_id = ?  AND d.v_date <= DATE_ADD(CURDATE(), INTERVAL 2 MONTH)");
				sql.append("GROUP BY rt.room_type_id, d.v_date ");
				sql.append("ORDER BY d.v_date, rt.room_type_id; ");
				List<Object[]> list = getSession().createSQLQuery(sql.toString())
						.addScalar("room_type_id", IntegerType.INSTANCE).addScalar("v_date", DateType.INSTANCE)
						.addScalar("roomtotal", LongType.INSTANCE).addScalar("remptyroomtotal", LongType.INSTANCE)
						.setParameter(1, startQueryDate).setParameter(2, startQueryDate).setParameter(3, startQueryDate)
						.setParameter(4, endQueryDate).setParameter(5, roomTypeId).list();

				return list;
			} else if (map.containsKey("startquerydate") && !map.containsKey("endquerydate")) {
				startQueryDate = Date.valueOf(map.get("startquerydate"));
				roomTypeId = Integer.valueOf(map.get("roomTypeId"));
				sql.append("WHERE rt.room_type_id = ? AND d.v_date <= DATE_ADD(CURDATE(), INTERVAL 2 MONTH)");
				sql.append("GROUP BY rt.room_type_id, d.v_date ");
				sql.append("ORDER BY d.v_date, rt.room_type_id; ");
				List<Object[]> list = getSession().createSQLQuery(sql.toString())
						.addScalar("room_type_id", IntegerType.INSTANCE).addScalar("v_date", DateType.INSTANCE)
						.addScalar("roomtotal", LongType.INSTANCE).addScalar("remptyroomtotal", LongType.INSTANCE)
						.setParameter(1, startQueryDate).setParameter(2, startQueryDate).setParameter(3, roomTypeId)
						.list();
				System.out.println("104+" + list);
				return list;
			}

		} else {
			if (map.containsKey("startquerydate") && map.containsKey("endquerydate")) {
				startQueryDate = Date.valueOf(map.get("startquerydate"));
				endQueryDate = Date.valueOf(map.get("endquerydate"));
				sql.append(
						"WHERE d.v_date >= ? AND d.v_date <= ? AND d.v_date <= DATE_ADD(CURDATE(), INTERVAL 2 MONTH) ");
				sql.append("GROUP BY rt.room_type_id, d.v_date ");
				sql.append("ORDER BY d.v_date, rt.room_type_id; ");
				List<Object[]> list = getSession().createSQLQuery(sql.toString())
						.addScalar("room_type_id", IntegerType.INSTANCE).addScalar("v_date", DateType.INSTANCE)
						.addScalar("roomtotal", LongType.INSTANCE).addScalar("remptyroomtotal", LongType.INSTANCE)
						.setParameter(1, startQueryDate).setParameter(2, startQueryDate).setParameter(3, startQueryDate)
						.setParameter(4, endQueryDate).list();

				return list;
			} else if (map.containsKey("startquerydate") && !map.containsKey("endquerydate")) {
				startQueryDate = Date.valueOf(map.get("startquerydate"));
				sql.append("WHERE d.v_date <= DATE_ADD(CURDATE(), INTERVAL 2 MONTH) ");
				sql.append("GROUP BY rt.room_type_id, d.v_date ");
				sql.append("ORDER BY d.v_date, rt.room_type_id; ");
				List<Object[]> list = getSession().createSQLQuery(sql.toString())
						.addScalar("room_type_id", IntegerType.INSTANCE).addScalar("v_date", DateType.INSTANCE)
						.addScalar("roomtotal", LongType.INSTANCE).addScalar("remptyroomtotal", LongType.INSTANCE)
						.setParameter(1, startQueryDate).setParameter(2, startQueryDate).list();

				return list;
			}
		}

		List<Object[]> list = new ArrayList<>();
		return list;
	}

	@Override
	public List<Object[]> getOneByCurDate(Map<String, String> map) {

		String sql = "WITH RECURSIVE dates (v_date) AS " + " ( SELECT CURDATE() " + "   UNION ALL "
				+ "   SELECT v_date + INTERVAL 1 DAY " + "   FROM dates "
				+ "   WHERE v_date + INTERVAL 1 DAY <= ADDDATE(CURDATE(), INTERVAL 2 MONTH) - INTERVAL 1 DAY ) "
				+ " SELECT rt.room_type_id, " + " d.v_date, " + " COUNT(rs.room_schedule_id) as roomtotal, "
				+ " (SELECT COUNT(*) FROM room WHERE room_type_id = rt.room_type_id and room_sale_state = 0 ) - "
				+ " COUNT(rs.room_schedule_id) as remptyroomtotal " + " FROM dates d " + " CROSS JOIN room_type rt "
				+ " LEFT JOIN room_schedule rs on (d.v_date = rs.room_schedule_date AND rt.room_type_id = rs.room_type_id) "
				+ " WHERE rt.room_type_id = ? "
				+ " GROUP BY rt.room_type_id, d.v_date " + " ORDER BY d.v_date, rt.room_type_id; ";

		Integer roomTypeId = Integer.valueOf(map.get("roomTypeId"));
		List<Object[]> list = getSession().createSQLQuery(sql).addScalar("room_type_id", IntegerType.INSTANCE)
				.addScalar("v_date", DateType.INSTANCE).addScalar("roomtotal", LongType.INSTANCE)
				.addScalar("remptyroomtotal", LongType.INSTANCE)
				.setParameter(1, roomTypeId).list();

		return list;
	}

//	

}
