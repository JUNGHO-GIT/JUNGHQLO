package com.JUNGHQLO.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@Component
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

  private ZoneId zoneId = ZoneId.systemDefault();

  // 1. override setNonNullParameter ---------------------------------------------------------------
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
    ps.setObject(i, parameter.atZone(zoneId).toOffsetDateTime());
  }
  @Override
  public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return toLocalDateTime(rs.getObject(columnName));
  }
  @Override
  public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return toLocalDateTime(rs.getObject(columnIndex));
  }
  @Override
  public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex)
  throws SQLException {
    return toLocalDateTime(cs.getObject(columnIndex));
  }

  // 2. convert to LocalDateTime -------------------------------------------------------------------
  private LocalDateTime toLocalDateTime(Object object) {
    if (object == null) {
      return null;
    }
    if (object instanceof java.sql.Timestamp) {
      return ((java.sql.Timestamp) object).toLocalDateTime();
    }
    if (object instanceof java.sql.Date) {
      return ((java.sql.Date) object).toLocalDate().atStartOfDay();
    }
    if (object instanceof java.sql.Time) {
      return ((java.sql.Time) object).toLocalTime().atDate(LocalDate.now());
    }
    if (object instanceof String) {
      return LocalDateTime.parse((String) object);
    }
    if (object instanceof LocalDateTime) {
      return (LocalDateTime) object;
    }
    throw new IllegalArgumentException("Cannot convert " + object.getClass() + " to LocalDateTime");
  }

}