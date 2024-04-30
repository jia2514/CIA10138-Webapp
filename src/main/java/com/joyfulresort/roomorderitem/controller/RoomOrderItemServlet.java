package com.joyfulresort.roomorderitem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joyfulresort.roomorderitem.entity.RoomOrderItem;
import com.joyfulresort.roomorderitem.service.RoomOrderItemService;
import com.joyfulresort.roomorderitem.service.RoomOrderItemServiceImpl;

@WebServlet("/roomorder/roomorderitem.do")
public class RoomOrderItemServlet extends HttpServlet{

	// 一個 servlet 實體對應一個 service 實體
		private RoomOrderItemService roomOrderItemService;

		@Override
		public void init() throws ServletException {
			roomOrderItemService = new RoomOrderItemServiceImpl();
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
				case "getByDate":
					forwardPath = getByDate(req, res);
					break;
				case "getByPeople":
					forwardPath = getByPeople(req, res);
					break;
				case "getByDatePeople":
					forwardPath = getByDatePeople(req, res);
					break;
				case "getByRoomType":
					forwardPath = getByRoomType(req, res);
					break;
				case "getAll":
					forwardPath = getAll(req, res);
					break;
				default:
					forwardPath = "/backend/roomorder/roomquery.jsp";
				}

			} else

			{
				forwardPath = "/backend/roomorder/roomquery.jsp";
			}

			res.setContentType("text/html; charset=UTF-8");
			RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
			dispatcher.forward(req, res);
		}

		private String getAll(HttpServletRequest req, HttpServletResponse res) {
			List<RoomOrderItem> roomOrderItems = roomOrderItemService.getAllRoomOrderItem();
			req.setAttribute("roomOrderItems", roomOrderItems);
			return null;
		}

		private String getByRoomType(HttpServletRequest req, HttpServletResponse res) {
			Integer roomTypeId = Integer.valueOf(req.getParameter("roomTypeId"));
			List<RoomOrderItem> roomOrderItems = roomOrderItemService.getRoomOrderItemByRoomTypeId(roomTypeId);
			req.setAttribute("roomOrderItems", roomOrderItems);
			return "/backend/roomorder/roomcalender.jsp";
		}

		private String getByDatePeople(HttpServletRequest req, HttpServletResponse res) {
			
			return null;
		}

		private String getByPeople(HttpServletRequest req, HttpServletResponse res) {
			// TODO Auto-generated method stub
			return null;
		}

		private String getByDate(HttpServletRequest req, HttpServletResponse res) {
			// TODO Auto-generated method stub
			return null;
		}
	
	
	
	
}
