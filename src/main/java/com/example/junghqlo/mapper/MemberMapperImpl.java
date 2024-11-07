package com.example.junghqlo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.example.junghqlo.model.Member;

@Repository
public class MemberMapperImpl implements MemberMapper {

  // 0. constructor injection ----------------------------------------------------------------------
  SqlSessionTemplate sqlSession;
  MemberMapperImpl(SqlSessionTemplate sqlSession) {
    this.sqlSession = sqlSession;
  }

  // 1-1. listMember ------------------------------------------------------------------------------
  @Override
  public List<Member> listMember(String sort) {

    return sqlSession.selectList("listMember", sort);
  }

  // 1-2. searchMember -----------------------------------------------------------------------------
  @Override
  public List<Member> searchMember(String searchType, String keyword) throws Exception {

    Map<String, Object> map = new HashMap<>();
    map.put("searchType", searchType);
    map.put("keyword", keyword);

    return sqlSession.selectList("searchMember", map);
  }

  // 2. detailMember -------------------------------------------------------------------------------
  @Override
  public Member detailMember(Integer member_number) {

    return sqlSession.selectOne("detailMember", member_number);
  }

  // 2-1. numberMember -----------------------------------------------------------------------------
  @Override
  public Integer numberMember(String member_id) {

    return sqlSession.selectOne("numberMember", member_id);
  }

  // 2-1. findMemberId -----------------------------------------------------------------------------
  @Override
  public String findMemberId(String member_name, String member_email) {

    Map<String, Object> map = new HashMap<>();
    map.put("member_name", member_name);
    map.put("member_email", member_email);

    return sqlSession.selectOne("findMemberId", map);
  }

  // 2-2. findMemberPw -----------------------------------------------------------------------------
  @Override
  public String findMemberPw(String member_name, String member_id, String member_email) {

    Map<String, Object> map = new HashMap<>();
    map.put("member_name", member_name);
    map.put("member_id", member_id);
    map.put("member_email", member_email);

    return sqlSession.selectOne("findMemberPw", map);
  }

  // 2-3 checkMemberId ----------------------------------------------------------------------------
  @Override
  public Integer checkMemberId(String member_id) {

    Integer result = 0;

    if ((Integer) sqlSession.selectOne("checkMemberId", member_id) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 2-4 checkMemberIdPw --------------------------------------------------------------------------
  @Override
  public Integer checkMemberIdPw(String member_id, String member_pw) {

    Integer result = 0;

    Map<String, Object> map = new HashMap<>();
    map.put("member_id", member_id);
    map.put("member_pw", member_pw);

    if ((Integer) sqlSession.selectOne("checkMemberIdPw", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 3. addMember ----------------------------------------------------------------------------------
  @Override
  public void addMember(Member member) {

    sqlSession.insert("addMember", member);
  }

  // 4. updateMember -------------------------------------------------------------------------------
  @Override
  public void updateMember(Member member) {

    sqlSession.update("updateMember", member);
  }

  // 4-1. updateMemberPw ---------------------------------------------------------------------------
  @Override
  public Integer updateMemberPw(String member_id, String member_pw) {

    Map<String, Object> map = new HashMap<>();
    map.put("member_id", member_id);
    map.put("member_pw", member_pw);

    return sqlSession.selectOne("updateMemberPw", map);
  }

  // 5. deleteMember -------------------------------------------------------------------------------
  @Override
  public Integer deleteMember(String member_name, String member_id, String member_pw) {

    Integer result = 0;

    Map<String, Object> map = new HashMap<>();
    map.put("member_name", member_name);
    map.put("member_id", member_id);
    map.put("member_pw", member_pw);

    if (sqlSession.delete("deleteMember", map) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 6. logoutMember -------------------------------------------------------------------------------
  @Override
  public void logoutMember(HttpSession session) {

    session.invalidate();
  }
}