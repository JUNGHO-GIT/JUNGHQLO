package com.example.junghqlo.controller;

import java.text.MessageFormat;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
  private static String page = "product";
  private static String PAGE = "Product";

  // 1-1. listProduct (GET) ------------------------------------------------------------------------
  @GetMapping("/listProduct")
  public String listProduct(
    @ModelAttribute Product product,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
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

    PageHandler<Product> pageHandler = (
      productService.listProduct(pageNumber, itemsPer, sort, product)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  };

  // 1-2. searchProduct (GET) ----------------------------------------------------------------------
  @GetMapping("/searchProduct")
  public String searchProduct(
    @ModelAttribute Product product,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    @RequestParam String searchType,
    @RequestParam String keyword,
    Model model
  ) throws Exception {

    // searchType order
    if (searchType == null || keyword == null) {
      return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
    }
    else if (searchType.equals("name")) {
      searchType="product_name";
    }
    else if (searchType.equals("detail")) {
      searchType="product_detail";
    }

    PageHandler<Product> pageHandler = (
      productService.searchProduct(pageNumber, itemsPer, searchType, keyword, product)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  }

  // 1-1. categoryProduct (GET) -----------------------------------------------------------------
  @GetMapping("/categoryProduct")
  public String categoryProduct(
    @ModelAttribute Product product,
    @RequestParam(defaultValue = "default") String sort,
    @RequestParam(defaultValue = "1") Integer pageNumber,
    @RequestParam(defaultValue = "9") Integer itemsPer,
    @RequestParam String category,
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

    PageHandler<Product> pageHandler = (
      productService.categoryProduct(pageNumber, itemsPer, category, sort, product)
    );

    // 모델
    model.addAttribute("sort", sort);
    model.addAttribute("pageHandler", pageHandler);
    model.addAttribute("LIST", pageHandler.getContent());

    return MessageFormat.format("/pages/{0}/{1}List", page, page);
  }

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

  // 3. addProduct (GET) ---------------------------------------------------------------------------
  @GetMapping("/addProduct")
  public String addProduct() throws Exception {

    return MessageFormat.format("/pages/{0}/{1}Add", page, page);
  }

  // 3. addProduct (POST) --------------------------------------------------------------------------
  @PostMapping("/addProduct")
  public String addProduct (
    @ModelAttribute Product product,
    @RequestParam String product_name,
    @RequestParam String product_detail,
    @RequestParam Integer product_price
  ) throws Exception {

    productService.addProduct(product, product_name, product_detail, product_price);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
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
  @PostMapping("/updateProduct")
  public String updateProduct(
    @ModelAttribute Product product,
    @RequestParam String product_imgsUrl1,
    @RequestParam String product_imgsUrl2
  ) throws Exception {

    productService.updateProduct(product, product_imgsUrl1, product_imgsUrl2);

    return MessageFormat.format("redirect:/{0}/list{1}", page, PAGE);
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