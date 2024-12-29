package com.JUNGHQLO.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Orders {

  // fields ----------------------------------------------------------------------------------------
  private Integer orders_number;
  private Integer orders_product_number;
  private String orders_product_name;
  private String orders_member_id;
  private Integer orders_quantity;
  private Integer orders_totalPrice;
  private String orders_imgsUrl;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime orders_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime orders_update;
}