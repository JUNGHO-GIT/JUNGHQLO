package com.example.junghqlo.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
@MappedTypes(MultipartFile.class)
public class MultipartFileTypeHandler extends BaseTypeHandler<MultipartFile> {

  // 1. setNonNullParameter ------------------------------------------------------------------------
  public void setNonNullParameter(PreparedStatement ps, int i, MultipartFile parameter, JdbcType jdbcType) throws SQLException {
    try {
      ps.setBlob(i, new javax.sql.rowset.serial.SerialBlob(parameter.getBytes()));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  // 2. getNullableResult --------------------------------------------------------------------------
  @Override
  public MultipartFile getNullableResult(ResultSet rs, String columnName) throws SQLException {
    Blob blob = rs.getBlob(columnName);
    return blobToMultipartFile(blob);
  }

  // 3. getNullableResult --------------------------------------------------------------------------
  @Override
  public MultipartFile getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Blob blob = rs.getBlob(columnIndex);
    return blobToMultipartFile(blob);
  }

  // 4. getNullableResult --------------------------------------------------------------------------
  @Override
  public MultipartFile getNullableResult(CallableStatement cs, int columnIndex)
  throws SQLException {
    Blob blob = cs.getBlob(columnIndex);
    return blobToMultipartFile(blob);
  }

  // 5. blobToMultipartFile ------------------------------------------------------------------------
  private MultipartFile blobToMultipartFile(Blob blob) throws SQLException {
    if (blob == null) {
      return null;
    }
    try {
      byte[] bytes = blob.getBytes(1, (int) blob.length());
      String fileName = UUID.randomUUID().toString();
      String contentType = "application/octet-stream";
      DiskFileItemFactory factory = new DiskFileItemFactory();
      DiskFileItem fileItem = (DiskFileItem) factory.createItem("file", contentType, false, fileName);
      try (OutputStream os = fileItem.getOutputStream()) {
        os.write(bytes);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      return new CommonsMultipartFile(fileItem);
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

}