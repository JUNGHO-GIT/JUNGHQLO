package com.example.junghqlo.mapper;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.junghqlo.model.Member;
import com.example.junghqlo.handler.LocalDateTimeTypeHandler;

@Mapper
public interface MemberMapper {

  // 0. result mapping -----------------------------------------------------------------------------
  @Results ({
    @Result (
      property="member_number",
      column="member_number",
      id=true
    ),
    @Result (
      property="member_id",
      column="member_id"
    ),
    @Result (
      property="member_pw",
      column="member_pw"
    ),
    @Result (
      property="member_name",
      column="member_name"
    ),
    @Result (
      property="member_phone",
      column="member_phone"
    ),
    @Result (
      property="member_email",
      column="member_email"
    ),
    @Result (
      property="member_address1",
      column="member_address1"
    ),
    @Result (
      property="member_address2",
      column="member_address2"
    ),
    @Result (
      property="member_agree",
      column="member_agree"
    ),
    @Result (
      property="member_agree",
      column="member_agree"
    ),
    @Result (
      property="member_date",
      column="member_date",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
    @Result (
      property="member_update",
      column="member_update",
      typeHandler = LocalDateTimeTypeHandler.class
    ),
    @Result (
      property="email_code",
      column="email_code"
    ),
    @Result (
      property="email_code",
      column="email_code"
    ),
  })

  // 1-1. listMember -------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      member
    ORDER BY
      ${sort}
    """
  )
  List<Member> listMember(
    @Param("sort") String sort
  ) throws Exception;

  // 1-2. searchMember -----------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      member
    WHERE
      ${keyword} LIKE CONCAT('%', #{searchType}, '%')
    """
  )
  List<Member> searchMember(
    @Param("searchType") String searchType,
    @Param("keyword") String keyword
  ) throws Exception;

  // 2. detailMember--------------------------------------------------------------------------------
  @Select(
    """
    SELECT
      *
    FROM
      member
    WHERE
      member_number=#{member_number}
    """
  )
  Member detailMember(
    @Param("member_number") Integer member_number
  ) throws Exception;

  // 2-1. numberMember -----------------------------------------------------------------------------
  @Select(
    """
    SELECT
      member_number
    FROM
      member
    WHERE
      member_id=#{member_id}
    """
  )
  Integer numberMember(
    @Param("member_id") String member_id
  ) throws Exception;

  // 2-1. findMemberId -----------------------------------------------------------------------------
  @Select(
    """
    SELECT
      member_id
    FROM
      member
    WHERE
      member_name=#{member_name}
      AND
      member_email=#{member_email}
    """
  )
  String findMemberId(
    @Param("member_name") String member_name,
    @Param("member_email") String member_email
  ) throws Exception;

  // 2-2. findMemberPw -----------------------------------------------------------------------------
  @Select(
    """
    SELECT
      member_pw
    FROM
      member
    WHERE
      member_id=#{member_id}
      AND
      member_name=#{member_name}
      AND
      member_email=#{member_email}
    """
  )
  String findMemberPw(
    @Param("member_id") String member_id,
    @Param("member_name") String member_name,
    @Param("member_email") String member_email
  ) throws Exception;

  // 2-3 checkMemberId ----------------------------------------------------------------------------
  @Select(
    """
    SELECT
      COUNT(1)
    FROM
      member
    WHERE
      member_id = #{member_id}
    """
  )
  Integer checkMemberId(
    @Param("member_id") String member_id
  ) throws Exception;

  // 2-4 checkMemberIdPw --------------------------------------------------------------------------
  @Select(
    """
    SELECT
      COUNT(1)
    FROM
      member
    WHERE
      member_id = #{member_id}
      AND
      member_pw = #{member_pw}
    """
  )
  Integer checkMemberIdPw(
    @Param("member_id") String member_id,
    @Param("member_pw") String member_pw
  ) throws Exception;

  // 3. addMember ----------------------------------------------------------------------------------
  @Insert(
    """
    INSERT INTO
      member(
        member_id,
        member_pw,
        member_name,
        member_phone,
        member_email,
        member_address1,
        member_address2,
        member_agree,
        member_date,
        email_code
      )
    VALUES (
      #{member_id},
      #{member_pw},
      #{member_name},
      #{member_phone},
      #{member_email},
      #{member_address1},
      #{member_address2},
      #{member_agree},
      NOW(),
      #{email_code}
    )
    """
  )
  void addMember(
    Member member
  ) throws Exception;

  // 4. updateMember ------------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      member
    SET
      member_id=#{member_id},
      member_pw=#{member_pw},
      member_name=#{member_name},
      member_phone=#{member_phone},
      member_email=#{member_email},
      member_address1=#{member_address1},
      member_address2=#{member_address2},
      member_agree=#{member_agree},
      member_update=NOW()
    WHERE
      member_id=#{member_id}
    """
  )
  void updateMember(
    Member member
  ) throws Exception;

  // 4-1. updateMemberPw --------------------------------------------------------------------------
  @Update(
    """
    UPDATE
      member
    SET
      member_pw=#{member_pw}
    WHERE
      member_id=#{member_id}
    """
  )
  Integer updateMemberPw(
    @Param("member_id") String member_id,
    @Param("member_pw") String member_pw
  ) throws Exception;

  // 5. deleteMember ------------------------------------------------------------------------------
  @Delete(
    """
    DELETE FROM
      member
    WHERE
      member_name=#{member_name}
    AND
      member_id=#{member_id}
    AND
      member_pw=#{member_pw}
    """
  )
  Integer deleteMember(
    @Param("member_name") String member_name,
    @Param("member_id") String member_id,
    @Param("member_pw") String member_pw
  ) throws Exception;

  // 6. logoutMember -------------------------------------------------------------------------------
  void logoutMember(
    HttpSession session
  ) throws Exception;

}