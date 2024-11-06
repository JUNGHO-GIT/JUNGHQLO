package com.example.junghqlo.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Orders;
import com.example.junghqlo.model.Product;
import com.example.junghqlo.service.OrdersService;
import com.example.junghqlo.service.ProductService;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Controller
public class OrdersController {

  // 0. static -------------------------------------------------------------------------------------
  private static final String PAGES = "/pages/orders";
  private static final String PAGE = "orders";
  private static final String PAGE_UP = "Orders";

  // 0. constructor injection --------------------------------------------------------------------->
  private OrdersService ordersService;
  private ProductService productService;
  OrdersController(OrdersService ordersService, ProductService productService) {
    this.ordersService = ordersService;
    this.productService = productService;
  }

  // 1. getOrdersList (GET) ----------------------------------------------------------------------->
  @GetMapping("/orders/getOrdersList")
  public String getOrdersList (
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Orders orders,
    HttpSession session
  ) throws Exception {

    if(sort == null || sort.equals("default")) {
      sort="orders_number DESC";
    }
    else if(sort.equals("nameASC")) {
      sort="product_name ASC";
    }
    else if(sort.equals("nameDESC")) {
      sort="product_name DESC";
    }
    else if(sort.equals("priceASC")) {
      sort="orders_totalPrice ASC";
    }
    else if(sort.equals("priceDESC")) {
      sort="orders_totalPrice DESC";
    }
    else if(sort.equals("dateASC")) {
      sort="orders_date ASC";
    }
    else if(sort.equals("dateDESC")) {
      sort="orders_date DESC";
    }

    String member_id = (String) session.getAttribute("member_id");

    PageHandler<Orders> page = ordersService.getOrdersList(pageNumber, itemsPer, member_id, sort,  orders);
    List<Orders> ordersList = page.getContent();

    // 주문내역이 있는경우
    try {
      if(ordersList != null && ordersList.size() > 0) {
        model.addAttribute("page", page);
        model.addAttribute("ordersList", ordersList);
        return "/pages/orders/ordersList";
      }
      // 주문내역이 없는경우
      else {
        return "/pages/orders/ordersListEmpty";
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return "/pages/orders/ordersListEmpty";
    }
  }

  // 2. getOrdersDetails (GET) -------------------------------------------------------------------->
  @GetMapping("/orders/getOrdersDetails")
  public String getOrdersDetails (
    @RequestParam Integer orders_number,
    Model model,
    Orders orders,
    HttpSession session
  ) throws Exception {

    model.addAttribute("ordersModel", ordersService.getOrdersDetails(orders_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return "/pages/orders/ordersDetails";
  }

  // 3. searchOrders (GET) ------------------------------------------------------------------------>
  @GetMapping("/orders/searchOrders")
  public String searchOrders(
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model,
    Orders orders,
    HttpSession session
  ) throws Exception {

    if(searchType == null || keyword == null) {
      return "redirect:/orders/getOrdersList";
    }
    else if(searchType.equals("name")) {
      searchType="product_name";
    }

    String member_id = (String) session.getAttribute("member_id");

    PageHandler<Orders> page = ordersService.searchOrders(pageNumber, itemsPer, keyword, searchType, member_id, orders);
    List<Orders> ordersList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("ordersList", ordersList);

    return "/pages/orders/ordersSearch";
  }

  // 4. addOrders (POST) -------------------------------------------------------------------------->
  @PostMapping("/orders/addOrders")
  public RedirectView addOrders (
    @RequestParam Integer product_number,
    @RequestParam String product_name,
    @RequestParam Long orders_quantity,
    @RequestParam Integer product_stock,
    Model model,
    Orders orders,
    HttpSession httpSession
  ) throws Exception {

    // 아이디 세션값 가져오기
    String member_id = (String) httpSession.getAttribute("member_id");

    // 전체 금액 계산
    Product product = productService.getProductDetails(product_number);
    Integer totalPrice = product.getProduct_price() * orders_quantity.intValue();

    // orders 객체에 값 삽입
    orders.setProduct_number(product_number);
    orders.setProduct_name(product_name);
    orders.setMember_id(member_id);
    orders.setOrders_quantity(Integer.parseInt(orders_quantity.toString()));
    orders.setOrders_totalPrice(totalPrice);
    ordersService.addOrders(orders);

    // 2. URL 생성
    String priceId = product.getStripe_price();
    String encodedProductName = URLEncoder.encode(product_name, StandardCharsets.UTF_8);

    String SUCCESS_URL
     ="http://www.junghomun.com/JUNGHQLO/orders/successOrders?session_id={CHECKOUT_SESSION_ID}&product_number="+product_number+"&product_stock="+product_stock+"&orders_quantity="+orders_quantity+"&orders_totalPrice="+totalPrice+"&product_name="+encodedProductName;

    String CANCEL_URL
     ="http://www.junghomun.com/JUNGHQLO/orders/successOrders?session_id={CHECKOUT_SESSION_ID}";

    // 3. stripe 결제 처리
    SessionCreateParams params = SessionCreateParams.builder()
    .setMode(SessionCreateParams.Mode.PAYMENT)
    .putMetadata("product_number", Integer.toString(product_number))
    .putMetadata("product_name", product_name)
    .putMetadata("member_id", member_id)
    .putMetadata("product_stock", Integer.toString(product_stock))
    .putMetadata("orders_quantity", orders_quantity.toString())
    .putMetadata("orders_totalPrice", Integer.toString(totalPrice))
    .setSuccessUrl(SUCCESS_URL)
    .setCancelUrl(CANCEL_URL)
    .addLineItem(SessionCreateParams.LineItem.builder()
      .setQuantity(orders_quantity)
      .setPrice(priceId)
      .build())
    .build();

    // 4. stripe session 생성, 뷰 리턴
    Session session = Session.create(params);

    return new RedirectView(session.getUrl());
  }

  // 4-2. successOrders (GET) -------------------------------------------------------------------->
  @GetMapping("/orders/successOrders")
  public String successOrders (
    @RequestParam String session_id,
    @RequestParam String product_number,
    @RequestParam String orders_quantity,
    @RequestParam String product_stock,
    @RequestParam String orders_totalPrice,
    @RequestParam String product_name,
    Model model
  ) throws Exception {

    // 1. 세션 정보 가져오기
    Session session = Session.retrieve(session_id);
    String paymentIntentId = session.getPaymentIntent();

    // 2. 결제 정보 가져오기
    PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
    String paymentStatus = paymentIntent.getStatus();

    // 3. 결제 성공 여부 확인 및 모델에 결제 정보 저장
    if (paymentStatus.equals("succeeded")) {
      model.addAttribute("paymentStatus", "결제 완료");
      model.addAttribute("orders_quantity", orders_quantity);
      model.addAttribute("orders_totalPrice", orders_totalPrice);
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

    return "/pages/orders/ordersSuccess";
  }

  // 4-3. failOrders (GET) ------------------------------------------------------------------------>
  @GetMapping("/orders/failOrders")
  public String failOrders () throws Exception {

    return "/pages/orders/ordersFail";
  }

}