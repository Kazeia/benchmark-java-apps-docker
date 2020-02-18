package com.demo.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
class HomeControler {

  @GetMapping("/")
  public String home() {
    return "helloWorld";
  }

}