package com.example.junghqlo.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.junghqlo.domain.Orders;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.OrdersMapper;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.stripe.exception.StripeException;

@Service
public class OrdersServiceImpl implements OrdersService {

  // 0. constructor injection --------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private OrdersMapper ordersMapper;
  OrdersServiceImpl(OrdersMapper ordersMapper) {
  this.ordersMapper = ordersMapper;
  }

  // 1. getOrdersList ----------------------------------------------------------------------------->
  @Override
  public PageHandler<Orders> getOrdersList(Integer pageNumber, Integer itemsPer, String member_id, String sort, Orders orders) throws Exception {

    logger.info("getOrdersList SERVICE  호출 !!!!!");

    List<Orders> content = ordersMapper.getOrdersList(member_id, sort);

    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;

    // Ensure the pageNumber is greater than 0
    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    // Ensure the pageNumber does not exceed pageLast, only if pageLast is greater than 0
    if (pageLast > 0 && pageNumber > pageLast) {
      pageNumber = pageLast;
    }

    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    List<Orders> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. getOrdersDetails -------------------------------------------------------------------------->
  @Override
  public Orders getOrdersDetails(Integer orders_number) throws Exception {

    logger.info("getOrdersDetails SERVICE  호출 !!!!!");

    return ordersMapper.getOrdersDetails(orders_number);
  }

  // 3. searchOrders ------------------------------------------------------------------------------>
  @Override
  public PageHandler<Orders> searchOrders(Integer pageNumber, Integer itemsPer, String searchType, String keyword, String member_id, Orders orders) throws Exception {

    logger.info("searchOrders SERVICE  호출 !!!!!");

    List<Orders> content = ordersMapper.searchOrders(searchType, keyword, member_id);
    Integer itemsTotal = content.size();
    Integer pageLast = (itemsTotal + itemsPer - 1) / itemsPer;
    Integer pageStart = (pageNumber - 1) * itemsPer;
    Integer pageEnd = Math.min(pageStart + itemsPer, itemsTotal);

    if (pageNumber <= 0) {
      pageNumber = 1;
    }

    List<Orders> pageContent = content.subList(pageStart, pageEnd);

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 3-1. getStripePrice -------------------------------------------------------------------------->
  @Override
  public String getStripePrice(Integer orders_number) throws StripeException {
    logger.debug("getStripePrice() 호출 !!!!!");

    return ordersMapper.getStripePrice(orders_number);
  }

  // 4. addOrders --------------------------------------------------------------------------------->
  @Override
  public void addOrders(Orders orders) throws Exception {

    logger.info("addOrders SERVICE  호출 !!!!!");

    String product_imgsUrl = orders.getProduct_imgsUrl();

    String googleBucketName="jungho-bucket";
    String googleFolderPath="JUNGHQLO/DB/orders/";
    String googleFileName;
    String googleBucketUrl;

    if (product_imgsUrl == null) {
      googleBucketUrl="https://storage.googleapis.com/jungho-bucket/JUNGHQLO/images/icon/logo.png";
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
    ordersMapper.addOrders(orders);
  }

  // 4-2. successOrders 4-3. failOrders 5. updateOrders

  // 5-1. updateOrdersStock ----------------------------------------------------------------------->
  @Override
  public Integer updateProductStock(Integer product_number, Integer product_stock, Integer orders_quantity) throws Exception {

    logger.debug("updateOrdersStock() 호출 !!!!!");

    return ordersMapper.updateProductStock(product_number, product_stock, orders_quantity);
  }

  // 6. deleteOrders ------------------------------------------------------------------------------>
  @Override
  public void deleteOrders(Integer orders_number) {

    logger.debug("deleteOrders() 호출 !!!!!");

    ordersMapper.deleteOrders(orders_number);
  }

}