package com.example.junghqlo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.OrdersMapper;
import com.example.junghqlo.model.Orders;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.stripe.exception.StripeException;

@Service
public class OrdersServiceImpl implements OrdersService {

  @Value("${storage}")
  private String STORAGE;

  @Value("${bucket-main}")
  private String BUCKET_MAIN;

  @Value("${bucket-folder}")
  private String BUCKET_FOLDER;

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


  // 3. saveOrders ---------------------------------------------------------------------------------
  @Override
  public void saveOrders(
    @ModelAttribute Orders orders,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // 변수 선언
    StringBuilder newImgsUrlBuilder = new StringBuilder();
    String existingImgsUrl = orders.getProduct_imgsUrl();
    String newImgsUrl = "";
    String mergedImgsUrl = "";
    String googleFileName = "";

    // 이미지가 있을 경우 google cloud storage에 업로드
    if (imgsFile.size() > 0) {
      for (int i = 0; i < imgsFile.size(); i++) {
        MultipartFile file = imgsFile.get(i);
        byte[] bytes = file.getBytes();

        // 고유한 파일 이름 생성 (인덱스 추가)
        googleFileName = String.format(
          "orders_%s_%d.webp",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")),
          i
        );

        // storage 객체 생성
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // blobId 생성
        BlobId blobId = BlobId.of(BUCKET_MAIN, BUCKET_FOLDER + "/orders/" + googleFileName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
          .setContentType(file.getContentType())
          .setContentDisposition("inline; filename=\"" + googleFileName + "\"")
          .build();

        Blob blob = storage.create(blobInfo, bytes);
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // 업로드된 파일 이름을 newImgsUrl에 추가
        if (newImgsUrlBuilder.length() > 0) {
          newImgsUrlBuilder.append(",");
        }
        newImgsUrlBuilder.append(googleFileName);
      }

      // 최종 newImgsUrl 설정
      newImgsUrl = newImgsUrlBuilder.toString().trim();
    }

    // 이미지 URL 합치기
    if (existingImgsUrl != null && existingImgsUrl.length() > 0) {
      if (newImgsUrl != null && newImgsUrl.length() > 0) {
        mergedImgsUrl = existingImgsUrl + "," + newImgsUrl;
      }
      else {
        mergedImgsUrl = existingImgsUrl;
      }
    }
    else {
      mergedImgsUrl = newImgsUrl;
    }

    // orders 는 리턴 없음
    ordersMapper.saveOrders(orders, mergedImgsUrl);
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