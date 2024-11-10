package com.example.junghqlo.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Product;
import com.example.junghqlo.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

  // 0. constructor injection ----------------------------------------------------------------------
  private ProductService productService;
  ProductController(ProductService productService) {
    this.productService = productService;
  }

  // 0. static -------------------------------------------------------------------------------------
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private static String page = "product";
  private static String PAGE = "Product";

  // 1. listProduct (GET) --------------------------------------------------------------------------
  @GetMapping("/listProduct")
  public String listProduct(
    @ModelAttribute Product product,
    @RequestParam(defaultValue = "all") String sort,
    @RequestParam(defaultValue = "all") String category,
    @RequestParam(defaultValue = "title") String type,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    Model model
  ) throws Exception {

    // 1. Sort handling
    String sortHandler = "";
    if (sort == null || sort.equals("all")) {
      sort = "product_date DESC";
      sortHandler = "all";
    }
    else if (sort.equals("nameASC")) {
      sort = "product_name ASC";
      sortHandler = "nameASC";
    }
    else if (sort.equals("nameDESC")) {
      sort = "product_name DESC";
      sortHandler = "nameDESC";
    }
    else if (sort.equals("priceASC")) {
      sort = "product_price ASC";
      sortHandler = "priceASC";
    }
    else if (sort.equals("priceDESC")) {
      sort = "product_price DESC";
      sortHandler = "priceDESC";
    }
    else if (sort.equals("dateASC")) {
      sort = "product_date ASC";
      sortHandler = "dateASC";
    }
    else if (sort.equals("dateDESC")) {
      sort = "product_date DESC";
      sortHandler = "dateDESC";
    }

    // 2. Category handling
    String categoryHandler = "";
    if (category == null || category.equals("all")) {
      category = "product_category IS NOT NULL";
      categoryHandler = "all";
    }
    else if (category.equals("outer")) {
      category = "product_category = 'outer'";
      categoryHandler = "outer";
    }
    else if (category.equals("top")) {
      category = "product_category = 'top'";
      categoryHandler = "top";
    }
    else if (category.equals("bottom")) {
      category = "product_category = 'bottom'";
      categoryHandler = "bottom";
    }
    else if (category.equals("bag")) {
      category = "product_category = 'bag'";
      categoryHandler = "bag";
    }
    else if (category.equals("shoes")) {
      category = "product_category = 'shoes'";
      categoryHandler = "shoes";
    }
    else if (category.equals("acc")) {
      category = "product_category = 'acc'";
      categoryHandler = "acc";
    }

    // 3. Type handling
    String typeHandler = "";
    if (type == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (type.equals("all")) {
      type = "product_name OR product_detail";
      typeHandler = "all";
    }
    else if (type.equals("name")) {
      type = "product_name";
      typeHandler = "name";
    }
    else if (type.equals("detail")) {
      type = "product_detail";
      typeHandler = "detail";
    }

    // 4. Keyword handling
    String keywordHandler = "";
    if (keyword != null) {
      keywordHandler = keyword;
    }

    // 5. Page handling
    PageHandler<Product> pageHandler = productService.listProduct(
      pageNumber, itemsPer, sort, category, type, keyword, product
    );

    // 모델
    model.addAttribute("sortHandler", sortHandler);
    model.addAttribute("categoryHandler", categoryHandler);
    model.addAttribute("typeHandler", typeHandler);
    model.addAttribute("keywordHandler", keywordHandler);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 2. detailProduct(GET) -------------------------------------------------------------------------
  @GetMapping("/detailProduct")
  public String getProductDetail (
    @ModelAttribute Product product,
    @RequestParam Integer product_number,
    HttpSession session,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", productService.detailProduct(product_number));
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Detail", page, page);
  }

  // 3. saveProduct (GET) --------------------------------------------------------------------------
  @GetMapping("/saveProduct")
  public String saveProduct() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Save", page, page);
  }

  // 3. saveProduct (POST) -------------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/saveProduct")
  public Integer saveProduct(
    @ModelAttribute Product product,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // imgsFile이 null이면 빈 리스트로 초기화
    if (imgsFile == null || imgsFile.size() == 0) {
      imgsFile = new ArrayList<MultipartFile>();
    }

    Integer result = 0;

    if (productService.saveProduct(product, imgsFile) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 4. updateProduct (GET) ------------------------------------------------------------------------
  @GetMapping("/updateProduct")
  public String updateProduct(
    @RequestParam Integer product_number,
    Model model
  ) throws Exception {

    // 모델
    model.addAttribute("MODEL", productService.detailProduct(product_number));

    return MessageFormat.format("/pages/{0}/{1}Update", page, page);
  }

  // 4. updateProduct (POST) -----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/updateProduct")
  public Integer updateProduct(
    @ModelAttribute Product product,
    @RequestParam(required = false) List<MultipartFile> imgsFile
  ) throws Exception {

    // imgsFile이 null이면 빈 리스트로 초기화
    if (imgsFile == null || imgsFile.size() == 0) {
      imgsFile = new ArrayList<MultipartFile>();
    }

    Integer result = 0;

    if (productService.updateProduct(product, imgsFile) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }

  // 5. deleteProduct (POST) -----------------------------------------------------------------------
  @ResponseBody
  @PostMapping("/deleteProduct")
  public Integer deleteProduct(
    @RequestParam Integer product_number
  ) throws Exception {

    Integer result = 0;

    if (productService.deleteProduct(product_number) > 0) {
      result = 1;
    }
    else {
      result = 0;
    }

    return result;
  }
}