package com.example.junghqlo.service;

import javax.servlet.http.HttpSession;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Member;

public interface MemberService {

  // 1. listMember ------------------------------------------------------------------------------
  PageHandler<Member> listMember(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    Member member
  ) throws Exception;

  // 1-3. findMemberId -----------------------------------------------------------------------------
  String findMemberId(
    String member_name,
    String member_email
  ) throws Exception;

  // 1-4. findMemberPw -----------------------------------------------------------------------------
  String findMemberPw(
    String member_id,
    String member_name,
    String member_email
  ) throws Exception;

  // 2-1. detailMember -----------------------------------------------------------------------------
  Member detailMember(
    Integer member_number
  ) throws Exception;

  // 2-2. getMemberNumber --------------------------------------------------------------------------
  Integer getMemberNumber(
    String member_id
  ) throws Exception;

  // 2-3 checkMemberId ----------------------------------------------------------------------------
  Integer checkMemberId(
    String member_id
  ) throws Exception;

  // 2-4 checkMemberIdPw --------------------------------------------------------------------------
  Integer checkMemberIdPw(
    String member_id,
    String member_pw
  ) throws Exception;

  // 3. saveMember ----------------------------------------------------------------------------------
  void saveMember(
    Member member
  ) throws Exception;

  // 4-1. updateMember -----------------------------------------------------------------------------
  void updateMember(
    Member member
  ) throws Exception;

  // 4-2. updateMemberPw ---------------------------------------------------------------------------
  Integer updateMemberPw(
    String member_id,
    String member_pw
  ) throws Exception;

  // 5. deleteMember -------------------------------------------------------------------------------
  Integer deleteMember(
    String member_name,
    String member_id,
    String member_pw
  ) throws Exception;

  // 6. logoutMember -------------------------------------------------------------------------------
  void logoutMember(
    HttpSession session
  ) throws Exception;

}