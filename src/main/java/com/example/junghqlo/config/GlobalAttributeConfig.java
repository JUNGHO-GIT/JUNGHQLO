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

  @ModelAttribute
  public void addAttributes(
    HttpServletRequest request,
    Model model
  ) {

    String uri = request.getRequestURI();
    String[] uriParts = uri.split("/");
    String page = uriParts.length > 2 ? uriParts[2] : "";
    String pageUp = page.isEmpty() ? "" : Character.toUpperCase(page.charAt(0)) + page.substring(1);
    String theme = uri.contains("/get" + pageUp) ? uri.split("/get" + pageUp)[1] : "";

    model.addAttribute("URL", uri);
    model.addAttribute("PAGE", page);
    model.addAttribute("PAGE_UP", pageUp);
    model.addAttribute("THEME", theme);

    model.addAttribute("TITLE", TITLE);
    model.addAttribute("ADMIN", ADMIN);
    model.addAttribute("STORAGE", STORAGE);
  }
}
