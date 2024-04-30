package com.joyfulresort.roomschedule.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.joyfulresort.roomschedule.service.RoomScheduleService;
import com.joyfulresort.roomschedule.service.RoomScheduleServiceImpl;

@WebServlet("/roomschedule/roomschedule.do")
public class RoomScheduleServlet extends HttpServlet {

	// 一個 servlet 實體對應一個 service 實體
	private RoomScheduleService roomScheduleService;

	@Override
	public void init() throws ServletException {
		roomScheduleService = new RoomScheduleServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String forwardPath = "";

		if (action != null) {
			switch (action) {
//				case "getAll":
//					forwardPath = getAllSchedule(req, res);
//					break;
			case "getIn2Month":
				forwardPath = getBy2Month(req, res);
				break;
			case "getCompositeQuery":
				forwardPath = getCompositeRoomScheduleQuery(req, res);
				break;
			default:
				forwardPath = "/backend/roomschedule/roomscheduleselect.jsp";
			}

		} else

		{
			forwardPath = "/backend/roomschedule/roomscheduleselect.jsp";
		}

		res.setContentType("text/html; charset=UTF-8");
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
		dispatcher.forward(req, res);
	}

	private String getCompositeRoomScheduleQuery(HttpServletRequest req, HttpServletResponse res) {
		Map<String, String[]> map = req.getParameterMap();

		Set<Map.Entry<String, String[]>> entry = map.entrySet();
		int count = 0;
		for (Map.Entry<String, String[]> row : entry) {
			String key = row.getKey();
			// 因為請求參數裡包含了action，做個去除動作
			if ("action".equals(key)) {
				continue;
			}
			// 若是value為空即代表沒有查詢條件，做個去除動作
			String value = row.getValue()[0]; // getValue拿到一個String陣列, 接著[0]取得第一個元素檢查
			if (value == null || value.isEmpty()) {
				count++;
				continue;
			}

		}

		if (count == 3) {
			return "/roomschedule/roomschedule.do?action=getIn2Month";
		} else if (map != null && map.size() != 0) {

			String jsonStr = roomScheduleService.getByCompositeQuery(map);
			JSONArray jsonArr = new JSONArray(jsonStr);
			req.setAttribute("roomScheduleCount", jsonStr);
			List<Integer> rtIdList = new ArrayList<>();
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONArray jsonArray = jsonArr.getJSONArray(i);
				int typeId = jsonArray.getInt(0);
				if (!rtIdList.isEmpty() && typeId != rtIdList.get(0)) {
					return "/roomschedule/roomschedule.do?action=getIn2Month";
				} else {
					rtIdList.add(typeId);
				}
			}

			return "/backend/roomschedule/listCompositeQueryRoomScheduleCalendar.jsp";
		} else {
			return "/roomschedule/roomschedule.do?action=getIn2Month";
		}
	}

	private String getBy2Month(HttpServletRequest req, HttpServletResponse res) {

		/*******************************************************************/
		RoomScheduleService roomScheduleSvc = new RoomScheduleServiceImpl();
		String jsonStr = roomScheduleSvc.getALLIn2Month();

		/*************************** 準備轉交 **********************************/

		req.setAttribute("roomScheduleCount", jsonStr);
		return "/backend/roomschedule/listAllRoomScheduleCalendar.jsp";
	}

}
