package com.example.junghqlo.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class Product {

  // fields --------------------------------------------------------------------------------------->
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
  private String stripe_id;
  private String stripe_price;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime product_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime product_update;

  // getter --------------------------------------------------------------------------------------->
  public Integer getProduct_number() {
    return this.product_number;
  }
  public String getProduct_name() {
    return this.product_name;
  }
  public String getProduct_detail() {
    return this.product_detail;
  }
  public Integer getProduct_price() {
    return this.product_price;
  }
  public Integer getProduct_stock() {
    return this.product_stock;
  }
  public String getProduct_brand() {
    return this.product_brand;
  }
  public String getProduct_category() {
    return this.product_category;
  }
  public String getProduct_origin() {
    return this.product_origin;
  }
  public String getProduct_imgsUrl() {
    return this.product_imgsUrl;
  }
  public LocalDateTime getProduct_date() {
    return this.product_date;
  }
  public LocalDateTime getProduct_update() {
    return this.product_update;
  }
  public String getStripe_id() {
    return this.stripe_id;
  }
  public String getStripe_price() {
    return this.stripe_price;
  }

  // setter --------------------------------------------------------------------------------------->
  public void setProduct_number(Integer product_number) {
    this.product_number = product_number;
  }
  public void setProduct_name(String product_name) {
    this.product_name = product_name;
  }
  public void setProduct_detail(String product_detail) {
    this.product_detail = product_detail;
  }
  public void setProduct_price(Integer product_price) {
    this.product_price = product_price;
  }
  public void setProduct_stock(Integer product_stock) {
    this.product_stock = product_stock;
  }
  public void setProduct_brand(String product_brand) {
    this.product_brand = product_brand;
  }
  public void setProduct_category(String product_category) {
    this.product_category = product_category;
  }
  public void setProduct_origin(String product_origin) {
    this.product_origin = product_origin;
  }
  public void setProduct_imgsUrl(String product_imgsUrl) {
    this.product_imgsUrl = product_imgsUrl;
  }
  public void setProduct_date(LocalDateTime product_date) {
		this.product_date = product_date;
	}
  public void setProduct_update(LocalDateTime product_update) {
    this.product_update = product_update;
  }
  public void setStripe_id(String stripe_id) {
    this.stripe_id = stripe_id;
  }
  public void setStripe_price(String stripe_price) {
    this.stripe_price = stripe_price;
  }

}