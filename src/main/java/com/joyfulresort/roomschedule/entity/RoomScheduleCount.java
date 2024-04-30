package com.joyfulresort.roomschedule.entity;

import java.sql.Date;

public class RoomScheduleCount {

	private Integer roomTypeId;
	private Date roomScheduleDate;
	private Long emptyCount;
	
	public Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public Date getRoomScheduleDate() {
		return roomScheduleDate;
	}
	public void setRoomScheduleDate(Date roomScheduleDate) {
		this.roomScheduleDate = roomScheduleDate;
	}
	public Long getEmptyCount() {
		return emptyCount;
	}
	public void setEmptyCount(Long emptyCount) {
		this.emptyCount = emptyCount;
	}
	
	
	@Override
	public String toString() {
		return "RoomScheduleCount [roomTypeId=" + roomTypeId + ", roomScheduleDate=" + roomScheduleDate
				+ ", emptyCount=" + emptyCount + "]";
	}

	

	

}
