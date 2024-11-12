package com.example.junghqlo.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class Orders {

  // fields --------------------------------------------------------------------------------------->
  private Integer orders_number;
  private Integer orders_product_number;
  private String orders_product_name;
  private String orders_member_id;
  private Integer orders_quantity;
  private Integer orders_totalPrice;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime orders_date;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime orders_update;

  // getter --------------------------------------------------------------------------------------->
  public Integer getOrders_number() {
    return this.orders_number;
  }
  public Integer getOrders_product_number() {
    return this.orders_product_number;
  }
  public String getOrders_product_name() {
    return this.orders_product_name;
  }
  public String getOrders_member_id() {
    return this.orders_member_id;
  }
  public Integer getOrders_quantity() {
    return this.orders_quantity;
  }
  public Integer getOrders_totalPrice() {
    return this.orders_totalPrice;
  }
  public LocalDateTime getOrders_date() {
    return this.orders_date;
  }
  public LocalDateTime getOrders_update() {
    return this.orders_update;
  }

  // setter ----------------------------------------------------------------------------------------
  public void setOrders_number(Integer orders_number) {
    this.orders_number = orders_number;
  }
  public void setOrders_product_number(Integer orders_product_number) {
    this.orders_product_number = orders_product_number;
  }
  public void setOrders_product_name(String orders_product_name) {
    this.orders_product_name = orders_product_name;
  }
  public void setOrders_member_id(String orders_member_id) {
    this.orders_member_id = orders_member_id;
  }
  public void setOrders_quantity(Integer orders_quantity) {
    this.orders_quantity = orders_quantity;
  }
  public void setOrders_totalPrice(Integer orders_totalPrice) {
    this.orders_totalPrice = orders_totalPrice;
  }
  public void setOrders_date(LocalDateTime orders_date) {
    this.orders_date = orders_date;
  }
  public void setOrders_update(LocalDateTime orders_update) {
    this.orders_update = orders_update;
  }

}