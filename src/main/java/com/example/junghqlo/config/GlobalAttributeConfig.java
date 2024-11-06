package com.example.junghqlo.config;

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
  public void globalAttributes(Model model) {
    model.addAttribute("TITLE", TITLE);
    model.addAttribute("ADMIN", ADMIN);
    model.addAttribute("STORAGE", STORAGE);
  }
}
