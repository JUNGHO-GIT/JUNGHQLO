package com.example.junghqlo.service;

import javax.servlet.http.HttpSession;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Member;

public interface MemberService {

  // 1. getMemberList ----------------------------------------------------------------------------->
  PageHandler<Member> getMemberList(Integer pageNumber, Integer itemsPer, String sort, Member member) throws Exception;

  // 2. getMemberDetails -------------------------------------------------------------------------->
  Member getMemberDetails(Integer member_number) throws Exception;

  // 2-1. getMemberNumber ------------------------------------------------------------------------->
  Integer getMemberNumber(String member_id) throws Exception;

  // 3. searchMember ------------------------------------------------------------------------------>
  PageHandler<Member> searchMember(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Member member) throws Exception;

  // 3-1. findMemberId ---------------------------------------------------------------------------->
  String findMemberId(String member_name, String member_email) throws Exception;

  // 3-2. findMemberPw ---------------------------------------------------------------------------->
  String findMemberPw( String member_name, String member_id,String member_email) throws Exception;

  // 4. addMember --------------------------------------------------------------------------------->
  void addMember(Member member) throws Exception;

  // 4-1. checkMemberId --------------------------------------------------------------------------->
  String checkMemberId(String member_id) throws Exception;

  // 4-2. checkMemberIdPw ------------------------------------------------------------------------->
  String checkMemberIdPw(String member_id, String member_pw) throws Exception;

  // 4-3. sendEmail 4-4. checkEmail 4-5. loginMember

  // 4-6. logoutMember ---------------------------------------------------------------------------->
  void logoutMember(HttpSession session) throws Exception;

  // 5. updateMember ------------------------------------------------------------------------------>
  void updateMember(Member member) throws Exception;

  // 5-1. updateMemberPw -------------------------------------------------------------------------->
  Integer updateMemberPw(String member_id, String member_pw) throws Exception;

  // 6. deleteMember ------------------------------------------------------------------------------>
  Integer deleteMember(String member_name, String member_id, String member_pw) throws Exception;

}