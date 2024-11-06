package com.example.junghqlo.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Product;
import com.example.junghqlo.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

  // 0. constructor injection -------------------------------------------------------------------->
  private ProductService productService;
  ProductController(ProductService productService) {
    this.productService = productService;
  }

  // 0. static -------------------------------------------------------------------------------------
  private static String PAGE = "product";
  private static String PAGE_UP = "Product";
  private static Product MODEL = new Product();
  private static List<Product> LIST = new ArrayList<>();

  // 1. getProductList (GET) ---------------------------------------------------------------------->
  @GetMapping("/getProductList")
  public String getProductList (
    @ModelAttribute Product product,
    @RequestParam(required = false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model
  ) throws Exception {

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
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}List", PAGE, PAGE);
  };

  // 1-1. getProductCategory (GET) ---------------------------------------------------------------->
  @GetMapping("/getProductCategory")
  public String getProductCategory(
    @ModelAttribute Product product,
    @RequestParam String category,
    @RequestParam(required = false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model
  ) throws Exception {

    sort="product_number DESC";
    // category order
    if (category.equals("outer")) {
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

    PageHandler<Product> page
      = productService.getProductCategory(pageNumber, itemsPer, category, sort, product);
    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}List", PAGE, PAGE);
  }

  // 2. getProductDetails (GET) ------------------------------------------------------------------->
  @GetMapping("/getProductDetails")
  public String getProductDetail (
    @ModelAttribute Product product,
    @RequestParam Integer product_number,
    HttpSession session,
    Model model
  ) throws Exception {

    MODEL = productService.getProductDetails(product_number);

    // 모델
    model.addAttribute("MODEL", MODEL);
    model.addAttribute("member_id", session.getAttribute("member_id"));

    return MessageFormat.format("/pages/{0}/{1}Details", PAGE, PAGE);
  }

  // 3. searchProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/searchProduct")
  public String searchProduct(
    @ModelAttribute Product product,
    @RequestParam(required = false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model
  ) throws Exception {

    // searchType order
    if (searchType == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
    }
    else if (searchType.equals("name")) {
      searchType="product_name";
    }
    else if (searchType.equals("details")) {
      searchType="product_details";
    }

    PageHandler<Product> page
      = productService.searchProduct(pageNumber, itemsPer, keyword, searchType, product);

    LIST = page.getContent();

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
    model.addAttribute("LIST", LIST);

    return MessageFormat.format("/pages/{0}/{1}Search", PAGE, PAGE);
  }

  // 4. addProduct (GET) -------------------------------------------------------------------------->
  @GetMapping("/addProduct")
  public String addProduct() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", PAGE, PAGE);
  }

  // 4. addProduct (POST) ------------------------------------------------------------------------->
  @PostMapping("/addProduct")
  public String addProduct (
    @RequestParam String product_name,
    @RequestParam String product_details,
    @RequestParam Integer product_price,
    Product product
  ) throws Exception {

    productService.addProduct(product, product_name, product_details, product_price);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }

  // 5. updateProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/updateProduct")
  public String updateProduct(
    @RequestParam Integer product_number,
    Model model
  ) throws Exception {

    MODEL = productService.getProductDetails(product_number);

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Update", PAGE, PAGE);
  }

  // 5. updateProduct (POST) ---------------------------------------------------------------------->
  @PostMapping("/updateProduct")
  public String updateProduct(
    @ModelAttribute Product product,
    @RequestParam String product_imgsUrl1,
    @RequestParam String product_imgsUrl2
  ) throws Exception {

    productService.updateProduct(product, product_imgsUrl1, product_imgsUrl2);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }

  // 6. deleteProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/deleteProduct")
  public String deleteProduct(
    @RequestParam Product product,
    @RequestParam Integer product_number,
    Model model
  ) throws Exception {

    MODEL = productService.getProductDetails(product_number);

    // 모델
    model.addAttribute("MODEL", MODEL);

    return MessageFormat.format("/pages/{0}/{1}Delete", PAGE, PAGE);
  }

  // 6. deleteProduct (POST) ---------------------------------------------------------------------->
  @PostMapping("/deleteProduct")
  public String deleteProduct(
    @RequestParam Integer product_number
  ) throws Exception {

    productService.deleteProduct(product_number);

    return MessageFormat.format("redirect:/{0}/get{1}List", PAGE, PAGE_UP);
  }
}