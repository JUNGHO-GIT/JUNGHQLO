package com.JUNGHQLO.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

  // fields ----------------------------------------------------------------------------------------
  private Integer product_number;
  private String product_name;
  private String product_detail;
  private Integer product_price;
  private Integer product_stock;
  private String product_brand;
  private String product_category;
  private String product_origin;
  private String product_imgsUrl;

  // stripe
  private String product_stripe_id;
  private String product_stripe_price;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime product_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime product_update;
}