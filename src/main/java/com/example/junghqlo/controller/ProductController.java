package com.example.junghqlo.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.junghqlo.domain.Member;
import com.example.junghqlo.domain.Product;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.service.ProductService;

@Controller
public class ProductController {

  // 0. constructor injection -------------------------------------------------------------------->
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private ProductService productService;
  ProductController(ProductService productService) {
  this.productService = productService;
  }

  // 1. getProductList (GET) ---------------------------------------------------------------------->
  @GetMapping("/product/getProductList")
  public String getProductList (
    @RequestParam(value="sort", required = false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Product product
  ) throws Exception {

    logger.info("getProductList GET 호출 !!!!!");

    // sort order
    if (sort == null || sort.equals("default")) {
      sort="RAND()";
    }
    else if (sort.equals("nameASC")) {
      sort="product_name ASC";
    }
    else if (sort.equals("nameDESC")) {
      sort="product_name DESC";
    }
    else if (sort.equals("priceASC")) {
      sort="product_price ASC";
    }
    else if (sort.equals("priceDESC")) {
      sort="product_price DESC";
    }
    else if (sort.equals("dateASC")) {
      sort="product_date ASC";
    }
    else if (sort.equals("dateDESC")) {
      sort="product_date DESC";
    }

    PageHandler<Product> page = productService.getProductList(pageNumber, itemsPer, sort, product);
    List<Product> productList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("productList", productList);

    return "/product/productList";
  }

  // 1-1. getProductCategory (GET) ---------------------------------------------------------------->
  @GetMapping("/product/getProductCategory")
  public String getProductCategory(
    @RequestParam(value="category") String category,
    @RequestParam(value="sort", required = false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Product product
  ) throws Exception {

    logger.info("getProductCategory GET 호출 !!!!!");

    sort="product_number DESC";
    // category order
    if      (category.equals("outer")) {
      category="'아우터'";
    }
    else if (category.equals("top")) {
      category="'상의'";
    }
    else if (category.equals("bottom")) {
      category="'하의'";
    }
    else if (category.equals("bag")) {
      category="'가방'";
    }
    else if (category.equals("shoes")) {
      category="'신발'";
    }
    else if (category.equals("acc")) {
      category="'악세서리'";
    }

    PageHandler<Product> page = productService.getProductCategory(pageNumber, itemsPer, category, sort, product);
    List<Product> productList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("productList", productList);

    return "/product/productList";
  }

  // 2. getProductDetails (GET) ------------------------------------------------------------------->
  @GetMapping("/product/getProductDetails")
  public String getProductDetail (
    @RequestParam("product_number") Integer product_number,
    Model model,
    Product product,
    Member member
  ) throws Exception {

    logger.info("getProductDetails GET 호출 !!!!!");

    model.addAttribute("productModel", productService.getProductDetails(product_number));

    return "/product/productDetails";
  }

  // 3. searchProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/product/searchProduct")
  public String searchProduct(
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam("searchType") String searchType,
    @RequestParam("keyword") String keyword,
    Model model,
    Product product
  ) throws Exception {

    logger.info("searchProduct GET 호출 !!!!!");

    // searchType order
    if (searchType == null || keyword == null) {
      return "redirect:/product/getProductList";
    }
    else if (searchType.equals("name")) {
      searchType="product_name";
    }
    else if (searchType.equals("details")) {
      searchType="product_details";
    }

    PageHandler<Product> page = productService.searchProduct(pageNumber, itemsPer, keyword, searchType, product);
    List<Product> productList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("productList", productList);

    return "/product/productSearch";
  }

  // 4. addProduct (GET) -------------------------------------------------------------------------->
  @GetMapping("/product/addProduct")
  public String addProduct() throws Exception {

    logger.info("addProduct GET 호출 !!!!!");

    return "/product/productAdd";
  }

  // 4. addProduct (POST) ------------------------------------------------------------------------->
  @PostMapping("/product/addProduct")
  public String addProduct (
    @RequestParam("product_name") String product_name,
    @RequestParam("product_details") String product_details,
    @RequestParam("product_price") Integer product_price,
    Product product
  ) throws Exception {

    logger.info("addProduct POST 호출 !!!!!");

    productService.addProduct(product, product_name, product_details, product_price);

    return "redirect:/product/getProductList";
  }

  // 5. updateProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/product/updateProduct")
  public String updateProduct(
    @RequestParam("product_number") Integer product_number,
    Model model
  ) throws Exception {

    logger.info("updateProduct GET 호출 !!!!!");

    Product product = productService.getProductDetails(product_number);
    model.addAttribute("productModel", product);

    return "/product/productUpdate";
  }

  // 5. updateProduct (POST) ---------------------------------------------------------------------->
  @PostMapping("/product/updateProduct")
  public String updateProduct(
    @ModelAttribute Product product,
    @RequestParam("product_imgsUrl1") String product_imgsUrl1,
    @RequestParam("product_imgsUrl2") String product_imgsUrl2
  ) throws Exception {

    logger.info("updateProduct POST 호출 !!!!!");

    productService.updateProduct(product, product_imgsUrl1, product_imgsUrl2);

    return "redirect:/product/getProductList";
  }

  // 6. deleteProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/product/deleteProduct")
  public String deleteProduct(
    @RequestParam("product_number") Integer product_number,
    Model model
  ) throws Exception {

    logger.info("deleteProduct GET 호출 !!!!!");

    Product product = productService.getProductDetails(product_number);
    model.addAttribute("productModel", product);

    return "/product/productDelete";
  }

  // 6. deleteProduct (POST) ---------------------------------------------------------------------->
  @PostMapping("/product/deleteProduct")
  public String deleteProduct(
    @RequestParam("product_number") Integer product_number
  ) throws Exception {

    logger.info("deleteProduct POST 호출 !!!!!");

    productService.deleteProduct(product_number);

    return "redirect:/product/getProductList";
  }

}