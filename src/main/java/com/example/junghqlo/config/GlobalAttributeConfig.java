package com.example.junghqlo.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Configuration
@ControllerAdvice
public class GlobalAttributeConfig {

  @Value("${title}")
  private String TITLE;

  @Value("${admin}")
  private String ADMIN;

  @Value("${storage}")
  private String STORAGE;

  // 1. addAttributes ------------------------------------------------------------------------------
  @ModelAttribute
  public void addAttributes(
    HttpServletRequest request,
    Model model
  ) throws Exception {

    // URI에서 파트들을 추출
    String uri = request.getRequestURI().split(";")[0];
    String[] uriParts = uri.split("/");
    String page = (uriParts.length > 2) ? uriParts[2] : "";
    String pageUp =!page.isEmpty() ? Character.toUpperCase(page.charAt(0)) + page.substring(1) : "";
    String theme = (uriParts.length > 3) ? uriParts[3] : "";

    // ex. listBoard -> List
    if (theme.contains(pageUp)) {
      String modifiedTheme = theme.replace(pageUp, "");
      if (!modifiedTheme.isEmpty()) {
        theme = modifiedTheme.substring(0, 1).toUpperCase() + modifiedTheme.substring(1);
      }
      else {
        theme = "";
      }
    }
    else {
      theme = "";
    }

    // page 에 따라 icon 추가
    String ICON = "";
    if (page.equals("board")) {
      ICON = "fas fa-chalkboard-teacher";
    }
    else if (page.equals("contact")) {
      ICON = "fas fa-map-marker-alt";
    }
    else if (page.equals("member")) {
      ICON = "fas fa-user-cog";
    }
    else if (page.equals("notice")) {
      ICON = "fas fa-bell";
    }
    else if (page.equals("orders")) {
      ICON = "fas fa-credit-card";
    }
    else if (page.equals("product")) {
      ICON = "fas fa-shopping-bag";
    }
    else if (page.equals("qna")) {
      ICON = "fas fa-bell";
    }
    else {
      ICON = "fas fa-home";
    }

    // 모델
    model.addAttribute("TITLE", TITLE);
    model.addAttribute("ADMIN", ADMIN);
    model.addAttribute("STORAGE", STORAGE);
    model.addAttribute("URL", uri);
    model.addAttribute("page", page);
    model.addAttribute("PAGE", pageUp);
    model.addAttribute("THEME", theme);
    model.addAttribute("ICON", ICON);
  }
}