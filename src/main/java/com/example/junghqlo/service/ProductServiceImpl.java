package com.example.junghqlo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.config.StripeConfig;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.mapper.ProductMapper;
import com.example.junghqlo.model.Product;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class ProductServiceImpl implements ProductService {

  // 0. constructor injection --------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private ProductMapper productMapper;
  private StripeConfig stripeConfig;
  ProductServiceImpl(ProductMapper productMapper, StripeConfig stripeConfig) {
  this.stripeConfig = stripeConfig;
  this.productMapper = productMapper;
  }

  // 1. getProductList----------------------------------------------------------------------------->
  @Override
  public PageHandler<Product> getProductList(Integer pageNumber, Integer itemsPer, String sort, Product product) throws Exception {

    logger.info("getProductList SERVICE 호출 !!!!!");

    List<Product> content = productMapper.getProductList(sort);

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

    List<Product> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 1-1. getProductCategory ---------------------------------------------------------------------->
  @Override
  public PageHandler<Product> getProductCategory(Integer pageNumber, Integer itemsPer, String category, String sort, Product product) throws Exception {

    logger.info("getProductCategory SERVICE 호출 !!!!!");

    List<Product> content = productMapper.getProductCategory(category, sort);

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

    List<Product> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 2. getProductDetails ------------------------------------------------------------------------->
  @Override
  public Product getProductDetails(Integer product_number) throws Exception {

    logger.info("detailsProduct SERVICE  호출 !!!!!");

    return productMapper.getProductDetails(product_number);
  }

  // 3. searchProduct ----------------------------------------------------------------------------->
  @Override
  public PageHandler<Product> searchProduct(Integer pageNumber, Integer itemsPer, String searchType, String keyword, Product product) throws Exception {

    logger.info("searchProduct SERVICE 호출 !!!!!");

    List<Product> content = productMapper.searchProduct(searchType, keyword);

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

    List<Product> pageContent;

    // If pageStart is greater than or equal to itemsTotal, set pageContent to an empty list
    if (pageStart >= itemsTotal) {
      pageContent = new ArrayList<>();
    }
    else {
      pageContent = content.subList(pageStart, pageEnd);
    }

    return new PageHandler<>(pageNumber, pageStart, pageEnd, 1, pageLast, itemsPer, itemsTotal, pageContent);
  }

  // 4. addProduct -------------------------------------------------------------------------------->
  @Override
  public void addProduct(Product product, String product_name, String product_details, Integer product_price) throws Exception {

    logger.info("addProduct SERVICE  호출 !!!!!");

    MultipartFile product_imgsFile1 = product.getProduct_imgsFile1();
    MultipartFile product_imgsFile2 = product.getProduct_imgsFile2();

    String googleFileName1;
    String googleFileName2;
    String googleBucketUrl1;
    String googleBucketUrl2;
    String googleBucketName="jungho-bucket";
    String googleFolderPath="JUNGHQLO/DB/product/";
    String googleNoImageUrl
    = "https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE/icon/noimage.png";

    if (product_imgsFile1.isEmpty()) {
      googleBucketUrl1 = googleNoImageUrl;
      product.setProduct_imgsUrl1(googleBucketUrl1);
    }
    else if (product_imgsFile2.isEmpty()) {
      googleBucketUrl2 = googleNoImageUrl;
      product.setProduct_imgsUrl2(googleBucketUrl2);
    }
    else {

      // google cloud storage
      byte[] bytes1 = product_imgsFile1.getBytes();
      byte[] bytes2 = product_imgsFile2.getBytes();

      // file name
      googleFileName1 = "_product_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";
      googleFileName2 = "_product_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_sub.png";
      Storage storage = StorageOptions.getDefaultInstance().getService();

      // set google cloud storage
      BlobId blobId1 = BlobId.of(googleBucketName, googleFolderPath + googleFileName1);
      BlobId blobId2 = BlobId.of(googleBucketName, googleFolderPath + googleFileName2);

      BlobInfo blobInfo1 = BlobInfo.newBuilder(blobId1)
        .setContentType(product_imgsFile1.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName1 + "\"")
        .build();
      BlobInfo blobInfo2 = BlobInfo.newBuilder(blobId2)
        .setContentType(product_imgsFile2.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName2 + "\"")
        .build();

      Blob blob1 =  storage.create(blobInfo1, bytes1);
      blob1.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      Blob blob2 =  storage.create(blobInfo2, bytes2);
      blob2.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // path to Google Cloud Storage bucket URL
      googleBucketUrl1 = "https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName1;

      googleBucketUrl2 = "https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName2;

      // stripe id, price set
      com.stripe.model.Product stripe_id
      = stripeConfig.createProduct(product_name, product_details, googleBucketUrl1, googleBucketUrl2);

      com.stripe.model.Price stripe_price
      = stripeConfig.createPrice(stripe_id.getId(), product_price);

      // set stripe to product
      product.setStripe_id(stripe_id.getId());
      product.setStripe_price(stripe_price.getId());
      product.setProduct_imgsUrl1(googleBucketUrl1);
      product.setProduct_imgsUrl2(googleBucketUrl2);
    }
    productMapper.addProduct(product);
  }

  // 5. updateProduct ----------------------------------------------------------------------------->
  @Override
  public void updateProduct(Product product, String product_imgsUrl1, String product_imgsUrl2) throws Exception {

    logger.info("updateProduct SERVICE 호출 !!!!!");

    MultipartFile product_imgsFile1 = product.getProduct_imgsFile1();
    MultipartFile product_imgsFile2 = product.getProduct_imgsFile2();

    String googleFileName1;
    String googleFileName2;
    String googleBucketUrl1 = product_imgsUrl1;
    String googleBucketUrl2 = product_imgsUrl2;
    String googleBucketName = "jungho-bucket";
    String googleFolderPath = "JUNGHQLO/DB/product/";
    String googleNoImageUrl = "https://storage.googleapis.com/jungho-bucket/JUNGHQLO/IMAGE/icon/noimage.png";

    Storage storage = StorageOptions.getDefaultInstance().getService();

    if (!product_imgsFile1.isEmpty() && product_imgsFile1.getSize() != 0) {
      // google cloud storage
      byte[] bytes1 = product_imgsFile1.getBytes();

      // file name
      googleFileName1 = "_product_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";

      // set google cloud storage
      BlobId blobId1 = BlobId.of(googleBucketName, googleFolderPath + googleFileName1);

      BlobInfo blobInfo1 = BlobInfo.newBuilder(blobId1)
        .setContentType(product_imgsFile1.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName1 + "\"")
        .build();

      Blob blob1 = storage.create(blobInfo1, bytes1);

      blob1.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // path to Google Cloud Storage bucket URL
      googleBucketUrl1 = "https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName1;

      product.setProduct_imgsUrl1(googleBucketUrl1);
    }

    if (!product_imgsFile2.isEmpty() && product_imgsFile2.getSize() != 0) {
      // google cloud storage
      byte[] bytes2 = product_imgsFile2.getBytes();

      // file name
      googleFileName2 = "_product_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_sub.png";

      // set google cloud storage
      BlobId blobId2 = BlobId.of(googleBucketName, googleFolderPath + googleFileName2);

      BlobInfo blobInfo2 = BlobInfo.newBuilder(blobId2)
        .setContentType(product_imgsFile2.getContentType())
        .setContentDisposition("inline; filename=\"" + googleFileName2 + "\"")
        .build();

      Blob blob2 = storage.create(blobInfo2, bytes2);

      blob2.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      // path to Google Cloud Storage bucket URL
      googleBucketUrl2 = "https://storage.googleapis.com/" + googleBucketName + "/" + googleFolderPath + googleFileName2;

      product.setProduct_imgsUrl2(googleBucketUrl2);
    }

    // stripe id, price set
    com.stripe.model.Product stripe_id
      = stripeConfig.updateProduct(product.getStripe_id(), product.getProduct_name(), product.getProduct_details(), googleBucketUrl1, googleBucketUrl2);

    com.stripe.model.Price stripe_price
      = stripeConfig.updatePrice(stripe_id.getId(), product.getProduct_price());

    // set stripe to product
    product.setStripe_id(stripe_id.getId());
    product.setStripe_price(stripe_price.getId());

    productMapper.updateProduct(product);
  }


  // 6. deleteProduct ----------------------------------------------------------------------------->
  @Override
  public void deleteProduct(Integer product_number) throws Exception {

    logger.info("deleteProduct SERVICE  호출 !!!!!");

    // 1. get product
    Product product = getProductDetails(product_number);

    // 2. delete product in stripe
    stripeConfig.deleteProduct(product.getStripe_id());
    stripeConfig.deletePrice(product.getStripe_price());

    productMapper.deleteProduct(product);
  }

}