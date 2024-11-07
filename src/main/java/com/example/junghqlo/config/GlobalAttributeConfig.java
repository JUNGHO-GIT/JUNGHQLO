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
    // URI에서 파트들을 추출
    String uri = request.getRequestURI().split(";")[0]; // ';' 이후 문자열 제거
    String[] uriParts = uri.split("/");
    String page = (uriParts.length > 2) ? uriParts[2] : "";
    String pageUp = !page.isEmpty() ? Character.toUpperCase(page.charAt(0)) + page.substring(1) : "";
    String theme = (uriParts.length > 3) ? uriParts[3] : "";

    // ex. listBoard -> List
    if (theme.contains(pageUp)) {
        String modifiedTheme = theme.replace(pageUp, "");
        if (!modifiedTheme.isEmpty()) {
            theme = modifiedTheme.substring(0, 1).toUpperCase() + modifiedTheme.substring(1);
        } else {
            theme = "";
        }
    } else {
        theme = "";
    }


    model.addAttribute("TITLE", TITLE);
    model.addAttribute("ADMIN", ADMIN);
    model.addAttribute("STORAGE", STORAGE);

    model.addAttribute("URL", uri);
    model.addAttribute("page", page);
    model.addAttribute("PAGE", pageUp);
    model.addAttribute("THEME", theme);
  }
}

