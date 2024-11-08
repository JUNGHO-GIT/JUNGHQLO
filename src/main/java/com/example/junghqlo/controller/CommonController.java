package com.example.junghqlo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

	// home ------------------------------------------------------------------------------------------
	@GetMapping("/")
	public String home() throws Exception {
		return "/pages/home";
  }

  // contact ---------------------------------------------------------------------------------------
  @GetMapping("/contact")
  public String contact() throws Exception {
    return "/pages/contact/contact";
  }
}