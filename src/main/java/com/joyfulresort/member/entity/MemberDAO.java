package com.joyfulresort.member.entity;


public interface MemberDAO {

	Member getById(Integer id);
	Member getByPhone(String phone);
	Member getByName(String name);
	
}
