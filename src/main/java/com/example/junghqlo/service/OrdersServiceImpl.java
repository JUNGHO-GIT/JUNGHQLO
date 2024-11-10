package com.example.junghqlo.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.OrdersMapper;
import com.example.junghqlo.model.Orders;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.stripe.exception.StripeException;

@Service
public class OrdersServiceImpl implements OrdersService {

  // 0. constructor injection ----------------------------------------------------------------------
  private OrdersMapper ordersMapper;
  OrdersServiceImpl(OrdersMapper ordersMapper) {
    this.ordersMapper = ordersMapper;
  }

  // 1. listOrders ---------------------------------------------------------------------------------
  @Override
  public PageHandler<Orders> listOrders(
    Integer pageNumber,
    Integer itemsPer,
    String sort,
    String type,
    String keyword,
    String member_id,
    Orders orders
  ) throws Exception {

    List<Orders> content = ordersMapper.listOrders(sort, type, keyword, member_id);

    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Orders> pageContent;

    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. detailOrders -------------------------------------------------------------------------------
  @Override
  public Orders detailOrders(
    Integer orders_number
  ) throws Exception {

    return ordersMapper.detailOrders(orders_number);
  }

  // 2-1. getStripePrice ---------------------------------------------------------------------------
  @Override
  public String getStripePrice(
    Integer orders_number
  ) throws StripeException {

    return ordersMapper.getStripePrice(orders_number);
  }

  // 3. saveOrders ----------------------------------------------------------------------------------
  @Override
  public void saveOrders(
    Orders orders
  ) throws Exception {

    String product_imgsUrl = orders.getProduct_imgsUrl();

    String googleBucketName="jungho-bucket";
    String googleFolderPath="JUNGHQLO/DB/orders/";
    String googleFileName;
    String googleBucketUrl;

    if (product_imgsUrl == null) {
      googleBucketUrl="https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE/icon/logo.png";
      orders.setProduct_imgsUrl(googleBucketUrl);
    }
    else {

      // URL
      URL url = new URL(product_imgsUrl);
      InputStream inputStream = url.openStream();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      // googleFileName
      googleFileName="_orders_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // download image
      int bytesRead;
      byte[] buffer = new byte[4096];
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      byte[] imageBytes = outputStream.toByteArray();

      // upload image
      storage.create(
        com.google.cloud.storage.BlobInfo
          .newBuilder(googleBucketName, googleFolderPath + googleFileName)
          .build(),
        imageBytes
      );

      // Path to Google Cloud Storage bucket URL
      googleBucketUrl="https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName;

      // Path to imgsUrl
      orders.setProduct_imgsUrl(googleBucketUrl);
    }
    ordersMapper.saveOrders(orders);
  }

  // 4-1. updateProductStock -----------------------------------------------------------------------
  @Override
  public void updateProductStock(
    Integer product_number,
    Integer product_stock,
    Integer orders_quantity
  ) throws Exception {

    ordersMapper.updateProductStock(product_number, product_stock, orders_quantity);
  }

  // 5. deleteOrders -------------------------------------------------------------------------------
  @Override
  public Integer deleteOrders(
    Integer orders_number
  ) throws Exception {

    Integer result = 0;

    if (ordersMapper.deleteOrders(orders_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

}