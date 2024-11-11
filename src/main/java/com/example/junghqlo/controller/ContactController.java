package com.example.junghqlo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

  // contact ---------------------------------------------------------------------------------------
  @GetMapping("/contact")
  public String contact() throws Exception {
    return "/pages/contact/contact";
  }
}