package com.example.junghqlo.controller;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import com.example.junghqlo.adapter.FileAdapter;
import com.example.junghqlo.adapter.LocalDateTimeAdapter;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Orders;
import com.example.junghqlo.model.Product;
import org.springframework.beans.factory.annotation.Value;
import com.example.junghqlo.service.OrdersService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Controller
@RequestMapping("/orders")
public class OrdersController {

  @Value("${orders-url}")
  String ORDERS_URL;

  // 0. constructor injection ----------------------------------------------------------------------
  private OrdersService ordersService;
  OrdersController(OrdersService ordersService) {
    this.ordersService = ordersService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String page = "orders";
  private static String PAGE = "Orders";

  // 0. logger -------------------------------------------------------------------------------------
  private static Logger logger = LoggerFactory.getLogger(OrdersController.class);
  private static Gson gson = new GsonBuilder()
  .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
  .registerTypeAdapter(File.class, new FileAdapter())
  .setPrettyPrinting()
  .create();

  // 1. listOrders (GET) ---------------------------------------------------------------------------
  @GetMapping("/listOrders")
  public String listOrders(
    @ModelAttribute Orders orders,
    @RequestParam(defaultValue = "all") String sort,
    @RequestParam(defaultValue = "all") String type,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    HttpSession session,
    Model model
  ) throws Exception {

    // 1. Sort handling
    String sortHandler = "";
    if (sort == null || sort.equals("all")) {
      sort = "orders_number DESC";
      sortHandler = "all";
    }
    else if(sort.equals("nameASC")) {
      sort = "orders_product_name ASC";
      sortHandler = "nameASC";
    }
    else if(sort.equals("nameDESC")) {
      sort = "orders_product_name DESC";
      sortHandler = "nameDESC";
    }
    else if(sort.equals("priceASC")) {
      sort = "orders_totalPrice ASC";
      sortHandler = "priceASC";
    }
    else if(sort.equals("priceDESC")) {
      sort = "orders_totalPrice DESC";
      sortHandler = "priceDESC";
    }
    else if(sort.equals("dateASC")) {
      sort = "orders_date ASC";
      sortHandler = "dateASC";
    }
    else if(sort.equals("dateDESC")) {
      sort = "orders_date DESC";
      sortHandler = "dateDESC";
    }

    // 3. Type handling
    String typeHandler = "";
    if (type == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (type.equals("all")) {
      type = "orders_product_name";
      typeHandler = "all";
    }
    else if (type.equals("name")) {
      type = "orders_product_name";
      typeHandler = "name";
    }

    // 4. Keyword handling
    String keywordHandler = "";
    if (keyword != null) {
      keywordHandler = keyword;
    }

    // * member_id 세션값 가져오기
    String member_id = (String) session.getAttribute("member_id");

    // 5. Page handling
    PageHandler<Orders> pageHandler = (
      ordersService.listOrders(pageNumber, itemsPer, sort, type, keyword, member_id, orders)
    );

    // 주문내역이 있는경우
    if (pageHandler.getContent() != null) {
      model.addAttribute("sortHandler", sortHandler);
      model.addAttribute("typeHandler", typeHandler);
      model.addAttribute("keywordHandler", keywordHandler);
      model.addAttribute("pageHandler", pageHandler);
      model.addAttribute("LIST", pageHandler.getContent());

      return MessageFormat.format("/pages/{0}/{1}List", page, page);
    }

    // 주문내역이 없는경우
    else {
      return MessageFormat.format("/pages/{0}/{1}ListEmpty", page, page);
    }
  }

  // 2. detailOrders(GET) --------------------------------------------------------------------------
  @GetMapping("/detailOrders")
  public String detailOrders(
    @RequestParam Integer orders_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", ordersService.detailOrders(orders_number));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 3. saveOrders (GET) ---------------------------------------------------------------------------
  @GetMapping("/saveOrders")
  public String saveOrders() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Save", page, page);
  }

  // 3. saveOrders (POST) --------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/saveOrders")
  public RedirectView saveOrders (
    @ModelAttribute Orders orders,
    @ModelAttribute Product product,
    Model model
  ) throws Exception {

    // 1. 서비스 호출
    ordersService.saveOrders(orders, product);

    // 2. URL 생성
    String stripePrice = (
      URLEncoder.encode(product.getProduct_stripe_price(), StandardCharsets.UTF_8)
    );
    String productName = (
      URLEncoder.encode(product.getProduct_name(), StandardCharsets.UTF_8)
    );

    // 성공했다면 successOrders 로 이동
    String SUCCESS_URL = (
      ORDERS_URL + "/successOrders" +
      "?check_session_id=" + "{CHECKOUT_SESSION_ID}" +
      "&product_number=" + product.getProduct_number() +
      "&product_stock=" + product.getProduct_stock() +
      "&orders_quantity=" + orders.getOrders_quantity() +
      "&orders_totalPrice=" + orders.getOrders_totalPrice() +
      "&product_imgsUrl=" + product.getProduct_imgsUrl() +
      "&product_name=" + productName
    );
    // 실패했다면 failOrders 로 이동
    String CANCEL_URL = (
      ORDERS_URL + "/failOrders"
    );

    // 3. 메타데이터 생성
    Map<String, String> map = new HashMap<>();
    map.put("member_id", orders.getOrders_member_id());
    map.put("product_number", Integer.toString(product.getProduct_number()));
    map.put("product_stock", Integer.toString(product.getProduct_stock()));
    map.put("orders_quantity", Integer.toString(orders.getOrders_quantity()));
    map.put("orders_totalPrice", Integer.toString(orders.getOrders_totalPrice()));
    map.put("product_name", product.getProduct_name());

    // 3. stripe 결제 처리
    SessionCreateParams params = SessionCreateParams.builder()
    .setMode(SessionCreateParams.Mode.PAYMENT)
    .putAllMetadata(map)
    .setSuccessUrl(SUCCESS_URL)
    .setCancelUrl(CANCEL_URL)
    .addLineItem(SessionCreateParams.LineItem.builder()
      .setQuantity(orders.getOrders_quantity().longValue())
      .setPrice(stripePrice)
      .build()
    )
    .build();

    // stripe session 생성, 뷰 리턴
    Session stripSession = Session.create(params);

    return new RedirectView(stripSession.getUrl());
  };

  // 4-1. successOrders (GET) ----------------------------------------------------------------------
  @GetMapping("/successOrders")
  public String successOrders (
    @RequestParam String check_session_id,
    @RequestParam String product_number,
    @RequestParam String product_stock,
    @RequestParam String orders_quantity,
    @RequestParam String orders_totalPrice,
    @RequestParam String product_imgsUrl,
    @RequestParam String product_name,
    Model model
  ) throws Exception {

    // 1. 세션 정보 가져오기
    Session session = Session.retrieve(check_session_id);
    String paymentIntentId = session.getPaymentIntent();

    // 2. 결제 정보 가져오기
    PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
    String paymentStatus = paymentIntent.getStatus();

    // 3. 결제 성공 여부 확인 및 모델에 결제 정보 저장
    if (paymentStatus.equals("succeeded")) {
      model.addAttribute("paymentStatus", "결제 완료");
      model.addAttribute("paymentIntent", paymentIntent);
      model.addAttribute("product_number", product_number);
      model.addAttribute("product_stock", product_stock);
      model.addAttribute("orders_quantity", orders_quantity);
      model.addAttribute("orders_totalPrice", orders_totalPrice);
      model.addAttribute("product_imgsUrl", product_imgsUrl);
      model.addAttribute("product_name", product_name);
    }
    else {
      model.addAttribute("paymentStatus", "결제 실패");
    }

    // 4. 재고 감소 처리
    ordersService.updateProductStock (
      Integer.parseInt(product_number),
      Integer.parseInt(product_stock),
      Integer.parseInt(orders_quantity)
    );

    return MessageFormat.format("/pages/{0}/{1}Success", page, page);
  }

  // 4-2. failOrders (GET) -------------------------------------------------------------------------
  @GetMapping("/failOrders")
  public String failOrders () throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Fail", page, page);
  }
}