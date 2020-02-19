package com.demo.rest.jmeter;

import com.demo.rest.jmeter.Algorithms.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
class HomeControler {
  @GetMapping("/test1")
  public String test1() {
    Binarytrees binarytrees = new Binarytrees();

    binarytrees.InitBenchmark();

    return "Finish test";
  }

  @GetMapping("/test2")
  public String test2() {
    NBody nbody = new NBody();
    nbody.InitBenchmark();

    return "Finish test";
  }

  @GetMapping("/test3")
  public String test3() {
    Mandelbrot mandelBrot = new Mandelbrot();

    mandelBrot.InitBenchmark();

    return "Finish test";
  }
}