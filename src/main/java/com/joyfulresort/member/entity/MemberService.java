package com.joyfulresort.member.entity;

public interface MemberService {

	Member getMemberByMemberId(Integer memberId);
	Member getMemberByMemberPhone(String memberPhone);
	Member getMemberByMemberName(String memberName);
	
	
}
