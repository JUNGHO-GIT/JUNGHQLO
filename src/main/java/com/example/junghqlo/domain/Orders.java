package com.example.junghqlo.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Orders {

  // fields --------------------------------------------------------------------------------------->
  private Integer orders_number;
  private Integer product_number;
  private String product_name;
  private String member_id;
  private Integer orders_quantity;
  private Integer orders_totalPrice;
  private String product_imgsUrl;
  private LocalDateTime orders_date;
  private LocalDateTime orders_update;

  // getter --------------------------------------------------------------------------------------->
  public Integer getOrders_number() {
    return this.orders_number;
  }
  public Integer getProduct_number() {
    return this.product_number;
  }
  public String getProduct_name() {
    return this.product_name;
  }
  public String getMember_id() {
    return this.member_id;
  }
  public Integer getOrders_quantity() {
    return this.orders_quantity;
  }
  public Integer getOrders_totalPrice() {
    return this.orders_totalPrice;
  }
  public String getProduct_imgsUrl() {
    return this.product_imgsUrl;
  }
  public LocalDateTime getOrders_date() {
    return this.orders_date;
  }
  public LocalDateTime getOrders_update() {
    return this.orders_update;
  }

  // setter --------------------------------------------------------------------------------------->
  public void setOrders_quantity(Integer orders_quantity) {
    this.orders_quantity = orders_quantity;
  }
  public void setProduct_number(Integer product_number) {
    this.product_number = product_number;
  }
  public void setProduct_name(String product_name) {
    this.product_name = product_name;
  }
  public void setMember_id(String member_id) {
    this.member_id = member_id;
  }
  public void setOrders_number(Integer orders_number) {
    this.orders_number = orders_number;
  }
  public void setOrders_totalPrice(Integer orders_totalPrice) {
    this.orders_totalPrice = orders_totalPrice;
  }
  public void setProduct_imgsUrl(String product_imgsUrl) {
    this.product_imgsUrl = product_imgsUrl;
  }
  public void setOrders_date(LocalDateTime orders_date) {
    this.orders_date = orders_date;
	}
  public void setOrders_update(LocalDateTime orders_update) {
    this.orders_update = orders_update;
  }

}