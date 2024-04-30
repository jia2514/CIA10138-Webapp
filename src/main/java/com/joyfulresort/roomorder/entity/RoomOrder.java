package com.joyfulresort.roomorder.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.joyfulresort.member.entity.Member;
import com.joyfulresort.roomorderitem.entity.RoomOrderItem;

@Entity
//@DynamicInsert 這是Hibernate可以自動填入預設值的設定
//@DynamicUpdate 這是Hibernate可以自動填入預設值的設定
@Table(name = "room_order")
public class RoomOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_order_id", updatable = false)
	private Integer roomOrderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	private Member member;
	
//	@CreationTimestamp 這是Hibernate自動填入今日時間的設定
	@Column(name = "order_date")
	private Timestamp orderDate = new Timestamp(System.currentTimeMillis());

	@Column(name = "room_order_state" ,nullable = false, columnDefinition = "int default 1")
	private Byte roomOrderState = 1;

	@Column(name = "check_in_date")
	private Date checkInDate;

	@Column(name = "check_out_date")
	private Date checkOutDate;

	@Column(name = "refund_state",nullable = false, columnDefinition = "int default 0")
	private Byte refundState = 0;

	@OneToMany(mappedBy = "roomOrder", cascade = CascadeType.ALL)
	@OrderBy("room_order_item_id asc")
	private Set<RoomOrderItem> roomOrderItems;

	public Integer getRoomOrderId() {
		return roomOrderId;
	}

	public void setRoomOrderId(Integer roomOrderId) {
		this.roomOrderId = roomOrderId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	
	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Byte getRoomOrderState() {
		return roomOrderState;
	}

	public void setRoomOrderState(Byte roomOrderState) {
		this.roomOrderState = roomOrderState;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Byte getRefundState() {
		return refundState;
	}

	public void setRefundState(Byte refundState) {
		this.refundState = refundState;
	}

	public Set<RoomOrderItem> getRoomOrderItems() {
		return roomOrderItems;
	}

	public void setRoomOrderItems(Set<RoomOrderItem> roomOrderItems) {
		this.roomOrderItems = roomOrderItems;
	}

	
	@Override
	public String toString() {
		return "RoomOrder [roomOrderId=" + roomOrderId + ", orderDate=" + orderDate + ", roomOrderState="
				+ roomOrderState + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", refundState="
				+ refundState + ", roomOrderItems=" + roomOrderItems + "]";
	}



	
	
	
}
