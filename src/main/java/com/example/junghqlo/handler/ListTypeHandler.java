package com.example.junghqlo.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ListTypeHandler extends BaseTypeHandler<List<String>> {

  private static final String DELIMITER = ",";

  // 1. setNonNullParameter ------------------------------------------------------------------------
  @Override
  public void setNonNullParameter(
    PreparedStatement ps,
    int i,
    List<String> parameter,
    JdbcType jdbcType
  ) throws SQLException {
    ps.setString(i, String.join(DELIMITER, parameter));
  }

  // 2. getNullableResult --------------------------------------------------------------------------
  @Override
  public List<String> getNullableResult(
    ResultSet rs,
    String columnName
  ) throws SQLException {

    String columnValue = rs.getString(columnName);
    List<String> result = new ArrayList<>();

    if (columnValue != null) {
      result.addAll(Arrays.asList(columnValue.split(DELIMITER)));
    }
    return result;
  }

  // 3. getNullableResult --------------------------------------------------------------------------
  @Override
  public List<String> getNullableResult(
    ResultSet rs,
    int columnIndex
  ) throws SQLException {

    String columnValue = rs.getString(columnIndex);
    List<String> result  = new ArrayList<>();

    if (columnValue != null) {
      result.addAll(Arrays.asList(columnValue.split(DELIMITER)));
    }
    return result;

  }

  // 4. getNullableResult --------------------------------------------------------------------------
  @Override
  public List<String> getNullableResult(
    CallableStatement cs,
    int columnIndex
  ) throws SQLException {
    String columnValue = cs.getString(columnIndex);
    List<String> result = new ArrayList<>();

    if (columnValue != null) {
      result.addAll(Arrays.asList(columnValue.split(DELIMITER)));
    }

    return result;
  }
}
