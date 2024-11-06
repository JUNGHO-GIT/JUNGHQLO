package com.example.junghqlo.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.junghqlo.handler.PageHandler;
import com.example.junghqlo.model.Member;
import com.example.junghqlo.model.Product;
import com.example.junghqlo.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

  // 0. static -------------------------------------------------------------------------------------
  private static final String PAGES = "/pages/product";
  private static final String PAGE = "product";
  private static final String PAGE_UP = "Product";

  // 0. constructor injection -------------------------------------------------------------------->
  private ProductService productService;
  ProductController(ProductService productService) {
    this.productService = productService;
  }

  // 1. getProductList (GET) ---------------------------------------------------------------------->
  @GetMapping("/getProductList")
  public String getProductList (
    @RequestParam(required = false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Product product
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
    List<Product> productList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("productList", productList);

    return PAGES + "/productList";
  }

  // 1-1. getProductCategory (GET) ---------------------------------------------------------------->
  @GetMapping("/getProductCategory")
  public String getProductCategory(
    @RequestParam String category,
    @RequestParam(required = false) String sort,
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    Model model,
    Product product
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

    PageHandler<Product> page = productService.getProductCategory(pageNumber, itemsPer, category, sort, product);
    List<Product> productList = page.getContent();

    model.addAttribute("page", page);
    model.addAttribute("productList", productList);

    return PAGES + "/productList";
  }

  // 2. getProductDetails (GET) ------------------------------------------------------------------->
  @GetMapping("/getProductDetails")
  public String getProductDetail (
    @RequestParam Integer product_number,
    Model model,
    Product product,
    Member member
  ) throws Exception {

    model.addAttribute("productModel", productService.getProductDetails(product_number));

    return PAGES + "/productDetails";
  }

  // 3. searchProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/searchProduct")
  public String searchProduct(
    @RequestParam(defaultValue="1") Integer pageNumber,
    @RequestParam(defaultValue="9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model,
    Product product
  ) throws Exception {

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

    return PAGES + "/productSearch";
  }

  // 4. addProduct (GET) -------------------------------------------------------------------------->
  @GetMapping("/addProduct")
  public String addProduct() throws Exception {

    return PAGES + "/productAdd";
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

    return "redirect:/product/getProductList";
  }

  // 5. updateProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/updateProduct")
  public String updateProduct(
    @RequestParam Integer product_number,
    Model model
  ) throws Exception {

    Product product = productService.getProductDetails(product_number);
    model.addAttribute("productModel", product);

    return PAGES + "/productUpdate";
  }

  // 5. updateProduct (POST) ---------------------------------------------------------------------->
  @PostMapping("/updateProduct")
  public String updateProduct(
    @ModelAttribute Product product,
    @RequestParam String product_imgsUrl1,
    @RequestParam String product_imgsUrl2
  ) throws Exception {

    productService.updateProduct(product, product_imgsUrl1, product_imgsUrl2);

    return "redirect:/product/getProductList";
  }

  // 6. deleteProduct (GET) ----------------------------------------------------------------------->
  @GetMapping("/deleteProduct")
  public String deleteProduct(
    @RequestParam Integer product_number,
    Model model
  ) throws Exception {

    Product product = productService.getProductDetails(product_number);
    model.addAttribute("productModel", product);

    return PAGES + "/productDelete";
  }

  // 6. deleteProduct (POST) ---------------------------------------------------------------------->
  @PostMapping("/deleteProduct")
  public String deleteProduct(
    @RequestParam Integer product_number
  ) throws Exception {

    productService.deleteProduct(product_number);

    return "redirect:/product/getProductList";
  }
}