package com.joyfulresort.roomorder.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joyfulresort.member.entity.Member;
import com.joyfulresort.member.entity.MemberService;
import com.joyfulresort.member.entity.MemberServiceImpl;
import com.joyfulresort.roomorder.entity.RoomOrder;
import com.joyfulresort.roomorder.service.RoomOrderService;
import com.joyfulresort.roomorder.service.RoomOrderServiceImpl;
import com.joyfulresort.roomorderitem.entity.RoomOrderItem;
import com.joyfulresort.roomorderitem.service.RoomOrderItemService;
import com.joyfulresort.roomorderitem.service.RoomOrderItemServiceImpl;
import com.joyfulresort.roomschedule.entity.RoomSchedule;
import com.joyfulresort.roomtype.entity.RoomType;
import com.joyfulresort.roomtype.entity.RoomTypeService;
import com.joyfulresort.roomtype.entity.RoomTypeServiceImpl;

@WebServlet("/roomorder/roomorder.do")
public class RoomOrderServlet extends HttpServlet {

	// 一個 servlet 實體對應一個 service 實體
	private RoomOrderService roomOrderService;

	@Override
	public void init() throws ServletException {
		roomOrderService = new RoomOrderServiceImpl();
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
			case "getAll":
				forwardPath = getAllRoomOrders(req, res);
				break;
			case "compositeQuery":
				forwardPath = getCompositeRoomOrdersQuery(req, res);
				break;
			case "addOne":
				forwardPath = addOneRoomOrder(req, res);
				break;
			case "getOneToUpdate":
				forwardPath = getOneToUpdate(req, res);
				break;
			case "updateOne":
				forwardPath = roomOrderUpdate(req, res);
				break;
			case "getOneToCancel":
				forwardPath = getOneToCancel(req, res);
				break;
			case "getOneToComplete":
				forwardPath = getOneToComplete(req, res);
				break;
			case "getOneToRefund":
				forwardPath = getOneToRefund(req, res);
				break;
			default:
				forwardPath = "/backend/roomorder/roomorderselect.jsp";
			}

		} else

		{
			forwardPath = "/backend/roomorder/roomorderselect.jsp";
		}

		res.setContentType("text/html; charset=UTF-8");
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
		dispatcher.forward(req, res);
	}

	
	
	
	
	private String getOneToCancel(HttpServletRequest req, HttpServletResponse res) {
		Integer roomOrderId = Integer.valueOf(req.getParameter("roomOrderId"));
		
		/*******************************************************************/
		RoomOrderService roomOrderSvc = new RoomOrderServiceImpl();
		roomOrderSvc.cancelRoomOrder(roomOrderId);
						
		/***************************準備轉交**********************************/
		
		return "/roomorder/roomorder.do?action=getAll";
	}

	private String getOneToComplete(HttpServletRequest req, HttpServletResponse res) {
		Integer roomOrderId = Integer.valueOf(req.getParameter("roomOrderId"));
		
		/*******************************************************************/
		RoomOrderService roomOrderSvc = new RoomOrderServiceImpl();
		roomOrderSvc.completeRoomOrder(roomOrderId);
						
		/***************************準備轉交**********************************/
		
		return "/roomorder/roomorder.do?action=getAll";
	}

	private String getOneToRefund(HttpServletRequest req, HttpServletResponse res) {
		Integer roomOrderId = Integer.valueOf(req.getParameter("roomOrderId"));
		
		/*******************************************************************/
		RoomOrderService roomOrderSvc = new RoomOrderServiceImpl();
		roomOrderSvc.refundRoomOrder(roomOrderId);
						
		/***************************準備轉交**********************************/
		
		return "/roomorder/roomorder.do?action=getAll";
	}

	private String roomOrderUpdate(HttpServletRequest req, HttpServletResponse res) {
		Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
		req.setAttribute("errorMsgs", errorMsgs);

		/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		
		String str = req.getParameter("roomorderstate");
		Byte roomOrderState = null;
		
		if ((str == null || str.trim().length() == 0)) {
			errorMsgs.put("roomOrderState","請輸入訂單狀態: 請勿空白");
		} 
		try {
			roomOrderState = Byte.parseByte(str.trim());
			// 補錯誤提醒輸入的房型編號沒有房型, 進階讓選單只有現有房型
		} catch (Exception e) {
			errorMsgs.put("roomOrderState","訂單狀態: 請輸入整數0,1,2,3");
		}
		
		
		str = req.getParameter("refundstate");
		Byte refundState = null;
		
		if ((str == null || str.trim().length() == 0)) {
			errorMsgs.put("refundState","請輸入退款狀態: 請勿空白");
		} 
		try {
			refundState = Byte.parseByte(str.trim());
			// 補錯誤提醒輸入的房型編號沒有房型, 進階讓選單只有現有房型
		} catch (Exception e) {
			errorMsgs.put("refundState","退款狀態: 請輸入整數0,1,2");
		}
		
		
		String specialREQ = req.getParameter("specialreq").trim();
		

		// Send the use back to the form, if there were errors
		if (!errorMsgs.isEmpty()) {
			return "/backend/roomorder/updateRoomOrder.jsp";
			
		}
		
		/***************************2.開始修改資料***************************************/
		Integer roomOrderId = Integer.valueOf(req.getParameter("roomOrderId"));
		RoomOrderService roomOrderSvc = new RoomOrderServiceImpl();
		RoomOrder roomOrder = roomOrderSvc.getRoomOrderByRoomOrderId(roomOrderId);
		
		RoomOrderItemService roiSvc = new RoomOrderItemServiceImpl();
		List<RoomOrderItem> roomOrderItems = roiSvc.getRoomOrderItemByRoomOrder(roomOrder);
		Set<RoomOrderItem> roomOrderItemsNew = new LinkedHashSet<>();
		
		roomOrder.setRoomOrderState(roomOrderState);
		roomOrder.setRefundState(refundState);
		
		for(RoomOrderItem roomOrderItem : roomOrderItems ) {
			roomOrderItem.setSpecialREQ(specialREQ);
			roomOrderItemsNew.add(roomOrderItem);
		}
		
		
		roomOrder.setRoomOrderItems(roomOrderItemsNew);;
		
		roomOrderService.addRoomOrder(roomOrder);
		
		
		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		
	    return "/roomorder/roomorder.do?action=getAll";
		
	}
	
	
	
	private String getOneToUpdate(HttpServletRequest req, HttpServletResponse res) {

			Integer roomOrderId = Integer.valueOf(req.getParameter("roomOrderId"));
			
			/***************************2.開始查詢資料****************************************/
			RoomOrderService roomOrderSvc = new RoomOrderServiceImpl();
			RoomOrder roomOrder = roomOrderSvc.getRoomOrderByRoomOrderId(roomOrderId);
							
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("roomOrder", roomOrder);
			return "/backend/roomorder/updateRoomOrder.jsp";
	}
	
	
	
	
	private String addOneRoomOrder(HttpServletRequest req, HttpServletResponse res) {
		Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
		req.setAttribute("errorMsgs", errorMsgs);

		/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		String memberPhone = req.getParameter("memberphone");
		String memberPhoneReg = "^09[0-9]{8}$";
		if (memberPhone == null || memberPhone.trim().length() == 0) {
			errorMsgs.put("memberPhone","會員電話: 請勿空白");
		} else if(!memberPhone.trim().matches(memberPhoneReg)) { //以下練習正則(規)表示式(regular-expression)
			errorMsgs.put("memberPnone","會員電話: 格式為09xxxxxxxx共10碼");
        }
		
				
		java.sql.Date checkInDate = null;
		try {
			checkInDate = java.sql.Date.valueOf(req.getParameter("checkindate").trim());
		} catch (IllegalArgumentException e) {
			errorMsgs.put("checkInDate","請選擇check in日期");
		}
		
		
		java.sql.Date checkOutDate = null;
		try {
			checkOutDate = java.sql.Date.valueOf(req.getParameter("checkoutdate").trim());
		} catch (IllegalArgumentException e) {
			errorMsgs.put("checkOutDate","請選擇check out日期");
		}
		
		
		
		String roomTypeId = req.getParameter("roomtypeid");
		
		Integer roomAmount = Integer.valueOf(req.getParameter("roomamount").trim());
		
		
		
		String specialREQ = req.getParameter("specialreq").trim();
		

		// Send the use back to the form, if there were errors
		if (!errorMsgs.isEmpty()) {
			return "/backend/roomorder/addRoomOrder.jsp";
			
		}
		
		/***************************2.開始新增資料***************************************/
		MemberService memSvc = new MemberServiceImpl();
		Member member = memSvc.getMemberByMemberPhone(memberPhone);
		RoomTypeService rtSvc = new RoomTypeServiceImpl();
//		RoomType roomType = rtSvc.getRoomTypeByRoomTypeId(roomTypeId);
		
		
		RoomOrder roomOrder = new RoomOrder();
		roomOrder.setMember(member);
		// member 新增一個由電話找member的方法
		roomOrder.setCheckInDate(checkInDate);
		roomOrder.setCheckOutDate(checkOutDate);
		
		long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
		int differenceInDays = (int) (diffInMillies / (1000 * 3600 * 24));
		
		int count = 1;
		
		Set<RoomOrderItem> roomOrderItems = new LinkedHashSet<>();
		
		while(count <= roomAmount) {
			Set<RoomSchedule> roomSchedules = new LinkedHashSet<>();
			int count2 = 0;
			RoomOrderItem roomOrderItem = new RoomOrderItem();
			roomOrderItem.setRoomOrder(roomOrder);
//			roomOrderItem.setRoomType(roomType);
//			roomOrderItem.setRoomPrice(roomType.getRoomTypePrice());
			roomOrderItem.setSpecialREQ(specialREQ);
			
			while(count2 < differenceInDays) {
				RoomSchedule roomSchedule = new RoomSchedule();
//				roomSchedule.setRoomType(roomType);
				roomSchedule.setRoomOderItem(roomOrderItem);
				long millies = checkInDate.getTime();
				Date date = new Date(count2 *1000 * 3600 * 24 + millies);
				roomSchedule.setRoomScheduleDate(date);
				roomSchedules.add(roomSchedule);
				count2++;
			}
			roomOrderItem.setRoomSchedules(roomSchedules);
			roomOrderItems.add(roomOrderItem);
			count++;	
		}
		
		roomOrder.setRoomOrderItems(roomOrderItems);
		
		roomOrderService.addRoomOrder(roomOrder);
		
		
		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		
	    return "/roomorder/roomorder.do?action=getAll";
		
	}
	
	
	
	
	
	private String getAllRoomOrders(HttpServletRequest req, HttpServletResponse res) {
		String page = req.getParameter("page");
		int currentPage = (page == null) ? 1 : Integer.parseInt(page);

		List<RoomOrder> roomOderList = roomOrderService.getAllRoomOrders(currentPage);

//		if (req.getSession().getAttribute("PageQty") == null) {
			int PageQty = roomOrderService.getPageTotal();
			req.getSession().setAttribute("PageQty", PageQty);
//		}

		req.setAttribute("roomOrderList", roomOderList);
		req.setAttribute("currentPage", currentPage);

		return "/backend/roomorder/listAllRoomOrders.jsp";
	}

	private String getCompositeRoomOrdersQuery(HttpServletRequest req, HttpServletResponse res) {
		Map<String, String[]> map = req.getParameterMap();

		if (map != null) {
			List<RoomOrder> roomOrderList = roomOrderService.getRoomOdersByCompositeQuery(map);
			req.setAttribute("roomOrderList", roomOrderList);
		} else {
			return "/backend/roomorder/roomorderselect.jsp";
		}
		return "/backend/roomorder/listCompositeQueryRoomOrders.jsp";
	}

}
