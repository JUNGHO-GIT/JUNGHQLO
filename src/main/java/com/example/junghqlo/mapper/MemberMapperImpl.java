package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.junghqlo.domain.Member;

@Repository
public class MemberMapperImpl implements MemberMapper {

  // 0. constructor injection --------------------------------------------------------------------->
  SqlSessionTemplate sqlSession;
  MemberMapperImpl(SqlSessionTemplate sqlSession) {
  this.sqlSession = sqlSession;
  }

  // 1. getMemberList ----------------------------------------------------------------------------->
  @Override
  public List<Member> getMemberList(String sort) {

    return sqlSession.selectList("getMemberList", sort);
  }

  // 2. getMemberDetails -------------------------------------------------------------------------->
  @Override
  public Member getMemberDetails(Integer member_number) {

    return sqlSession.selectOne("getMemberDetails", member_number);
  }

  // 2-1. getMemberNumber ------------------------------------------------------------------------->
  @Override
  public Integer getMemberNumber(String member_id) {

    return sqlSession.selectOne("getMemberNumber", member_id);
  }

  // 3. searchMember ------------------------------------------------------------------------------>
  @Override
  public List<Member> searchMember(String searchType, String keyword) throws Exception {

    Map<String, Object> params = new HashMap<>();
    params.put("searchType", searchType);
    params.put("keyword", keyword);

    return sqlSession.selectList("searchMember", params);
  }

  // 3-1. findMemberId ---------------------------------------------------------------------------->
  @Override
  public String findMemberId(String member_name, String member_email) {

    Map<String, Object> params = new HashMap<>();
    params.put("member_name", member_name);
    params.put("member_email", member_email);

    return sqlSession.selectOne("findMemberId", params);
  }

  // 3-2. findMemberPw ---------------------------------------------------------------------------->
  @Override
  public String findMemberPw(String member_name, String member_id, String member_email) {

    Map<String, Object> params = new HashMap<>();
    params.put("member_name", member_name);
    params.put("member_id", member_id);
    params.put("member_email", member_email);

    return sqlSession.selectOne("findMemberPw", params);
  }

  // 4. addMember --------------------------------------------------------------------------------->
  @Override
  public void addMember(Member member) {

    sqlSession.insert("addMember", member);
  }

  // 4-1. checkMemberId --------------------------------------------------------------------------->
  @Override
  public String checkMemberId(String member_id) {

    return sqlSession.selectOne("checkMemberId", member_id);
  }

  // 4-2. checkMemberIdPw
  @Override
  public String checkMemberIdPw(String member_id, String member_pw) {

    Map<String, Object> params = new HashMap<>();
    params.put("member_id", member_id);
    params.put("member_pw", member_pw);

    return sqlSession.selectOne("checkMemberIdPw", params);
  }

  // 4-3. sendEmail 4-4. checkEmail 4-5. loginMember

  // 4-6. logoutMember ---------------------------------------------------------------------------->
  @Override
  public void logoutMember(HttpSession session) {

    session.invalidate();
  }

  // 5. updateMember ------------------------------------------------------------------------------>
  @Override
  public void updateMember(Member member) {

    sqlSession.update("updateMember", member);
  }

  // 5-1. updateMemberPw -------------------------------------------------------------------------->
  @Override
  public String updateMemberPw(String member_id, String member_pw) {

    Map<String, Object> params = new HashMap<>();
    params.put("member_id", member_id);
    params.put("member_pw", member_pw);

    return sqlSession.selectOne("updateMemberPw", params);
  }

  // 6. deleteMember ------------------------------------------------------------------------------>
  @Override
  public Integer deleteMember(String member_name, String member_id, String member_pw) {

    Map<String, Object> params = new HashMap<>();
    params.put("member_name", member_name);
    params.put("member_id", member_id);
    params.put("member_pw", member_pw);

    return sqlSession.selectOne("deleteMember", params);
  }

}