package com.example.junghqlo.config;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.junghqlo.adapter.FileAdapter;
import com.example.junghqlo.adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceListParams;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem.AdjustableQuantity;

@Configuration
public class StripeConfig {

  // 0. api key setting ----------------------------------------------------------------------------
  @Value("${stripe-pk}")
  String publicKey;

  @Value("${stripe-sk}")
  String secretKey;

  @Bean
  void initStripe() {
    Stripe.apiKey = secretKey;
  }
  @Bean
  AdjustableQuantity adjustableQuantity() {
    return SessionCreateParams.LineItem.AdjustableQuantity.builder().build();
  }

  // 0. logger -------------------------------------------------------------------------------------
  private static Logger logger = LoggerFactory.getLogger(StripeConfig.class);
  private static Gson gson = new GsonBuilder()
  .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
  .registerTypeAdapter(File.class, new FileAdapter())
  .setPrettyPrinting()
  .create();

  // 1. createProduct ------------------------------------------------------------------------------
  public Product createProduct(
    String productName,
    String productDetail,
    String productUrl
  ) throws StripeException {

    List<String> imgsArray = new ArrayList<>();

    // 이미지를 ','로 구분하여 리스트에 추가
    String[] imgs = productUrl.split(",");
    for (String img : imgs) {
      imgsArray.add(img);
    }

    ProductCreateParams productParams = ProductCreateParams.builder()
      .setName(productName)
      .setDescription(productDetail)
      .setActive(true)
      .addAllImage(imgsArray)
      .build();

    return Product.create(productParams);
  }

  // 2. createPrice --------------------------------------------------------------------------------
  public Price createPrice(
    String productId,
    Integer productPrice
  ) throws StripeException {

    PriceCreateParams priceParams = PriceCreateParams.builder()
      .setUnitAmount(new BigDecimal(productPrice).longValue())
      .setCurrency("krw")
      .setRecurring(PriceCreateParams.Recurring.builder().build())
      .setProduct(productId)
      .setActive(true)
      .build();

    return Price.create(priceParams);
  }

  // 3. updateProduct ------------------------------------------------------------------------------
  public Product updateProduct (
    String productId,
    String productName,
    String productDetail,
    String productUrl
  ) throws StripeException {

    List<String> imgsArray = new ArrayList<>();

    // 이미지를 ','로 구분하여 리스트에 추가
    String[] imgs = productUrl.split(",");
    for (String img : imgs) {
      imgsArray.add(img);
    }

    ProductUpdateParams.Builder productParamsBuilder = ProductUpdateParams.builder()
      .setName(productName)
      .setDescription(productDetail)
      .setActive(true)
      .addAllImage(imgsArray);

    ProductUpdateParams productParams = productParamsBuilder.build();

    return Product.retrieve(productId).update(productParams);
  }

  // 4. updatePrice --------------------------------------------------------------------------------
  public Price updatePrice(
    String productId,
    Integer productPrice
  ) throws StripeException {

    PriceCreateParams priceParams = PriceCreateParams.builder()
      .setUnitAmount(new BigDecimal(productPrice).longValue())
      .setCurrency("krw")
      .setRecurring(PriceCreateParams.Recurring.builder().build())
      .setProduct(productId)
      .setActive(true)
      .build();

    // delete the existing price object
    Price existingPrice = Price.list(
      PriceListParams.builder().setProduct(productId).build()
    ).getData().get(0);

    existingPrice.setActive(false);

    return Price.create(priceParams);
  }

  // 5. deleteProduct ------------------------------------------------------------------------------
  public Product deleteProduct(
    String productId
  ) throws StripeException {

    ProductUpdateParams.Builder productParamsBuilder = ProductUpdateParams.builder()
      .setActive(false);

    ProductUpdateParams productParams = productParamsBuilder.build();

    return Product.retrieve(productId).update(productParams);
  }

  // 5. deletePrice --------------------------------------------------------------------------------
  public Price deletePrice(
    String productId
  ) throws StripeException {

    PriceUpdateParams.Builder priceParamsBuilder = PriceUpdateParams.builder()
      .setActive(false);

    PriceUpdateParams priceParams = priceParamsBuilder.build();

    return Price.retrieve(productId).update(priceParams);
  }

}