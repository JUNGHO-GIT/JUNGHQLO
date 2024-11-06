package com.example.junghqlo.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/orders")
public class OrdersController {

  // 0. constructor injection --------------------------------------------------------------------->
  private OrdersService ordersService;
  private ProductService productService;
  OrdersController(OrdersService ordersService, ProductService productService) {
    this.ordersService = ordersService;
    this.productService = productService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String PAGE = "orders";
  private static String PAGE_UP = "Orders";
  private static Orders MODEL = new Orders();
  private static List<Orders> LIST = new ArrayList<>();

  // 1. getOrdersList (GET) ----------------------------------------------------------------------->
  @GetMapping("/getOrdersList")
  public String getOrdersList (
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Orders orders,
    HttpSession session
  ) throws Exception {

    if (sort == null || sort.equals("default")) {
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

    PageHandler<Orders> page
    = ordersService.getOrdersList(pageNumber, itemsPer, member_id, sort,  orders);

    LIST = page.getContent();

    // 주문내역이 있는경우
    if (LIST != null && LIST.size() > 0) {
      model.addAttribute("sort", sort);
      model.addAttribute("page", page);
      model.addAttribute("LIST", LIST);
      return MessageFormat.format("/pages/{0}/{1}List", PAGE, PAGE);
    }

    // 주문내역이 없는경우
    else {
      return MessageFormat.format("/pages/{0}/{1}ListEmpty", PAGE, PAGE);
    }
  }

  // 2. getOrdersDetails (GET) -------------------------------------------------------------------->
  @GetMapping("/getOrdersDetails")
  public String getOrdersDetails (
    @ModelAttribute Orders orders,
    @RequestParam Integer orders_number,
    HttpSession session,
    Model model
  ) throws Exception {

    MODEL = ordersService.getOrdersDetails(orders_number);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Details", PAGE, PAGE);
  }

  // 3. searchOrders (GET) ------------------------------------------------------------------------>
  @GetMapping("/searchOrders")
  public String searchOrders(
    @ModelAttribute Orders orders,
    @RequestParam(required=false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    HttpSession session,
    Model model
  ) throws Exception {

    if (searchType == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
    }
    else if(searchType.equals("name")) {
      searchType="product_name";
    }

    String member_id = (String) session.getAttribute("member_id");

    PageHandler<Orders> page
    = ordersService.searchOrders(pageNumber, itemsPer, keyword, searchType, member_id, orders);

    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}Search", PAGE, PAGE);
  }

  // 4. addOrders (POST) -------------------------------------------------------------------------->
  @PostMapping("/addOrders")
  public RedirectView addOrders (
    @ModelAttribute Orders orders,
    @RequestParam Integer product_number,
    @RequestParam String product_name,
    @RequestParam Long orders_quantity,
    @RequestParam Integer product_stock,
    HttpSession httpSession,
    Model model
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
      .build()
    )
    .build();

    // 4. stripe session 생성, 뷰 리턴
    Session session = Session.create(params);

    return new RedirectView(session.getUrl());
  }

  // 4-2. successOrders (GET) -------------------------------------------------------------------->
  @GetMapping("/successOrders")
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

    return MessageFormat.format("/pages/{0}/{1}Success", PAGE, PAGE);
  }

  // 4-3. failOrders (GET) ------------------------------------------------------------------------>
  @GetMapping("/failOrders")
  public String failOrders () throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Fail", PAGE, PAGE);
  }

}