package com.joyfulresort.member.entity;

public class MemberServiceImpl implements MemberService{

	// 一個 service 實體對應一個 dao 實體
			private MemberDAO dao;
			
			public MemberServiceImpl() {
				// TODO Auto-generated constructor stub
				dao = new MemberDAOImpl();
			}

			@Override
			public Member getMemberByMemberId(Integer memberId) {
				return dao.getById(memberId);
			}

			@Override
			public Member getMemberByMemberPhone(String memberPhone) {
				return dao.getByPhone(memberPhone);
			}

			@Override
			public Member getMemberByMemberName(String memberName) {
				return dao.getByName(memberName);
			}
	
	
	
}
