package com.demo.rest.jmeter;

import com.demo.rest.jmeter.Algorithms.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
class HomeControler {
  @GetMapping("/test1")
  public ResponseEntity<String> test1() {
    try {
      Fannkuchredux fannkuchredux = new Fannkuchredux();
      fannkuchredux.InitBenchmark();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>("Unknown error", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("Finish test", new HttpHeaders(), HttpStatus.OK);
  }

  @GetMapping("/test2")
  public ResponseEntity<String> test2() {
    try {
      NBody nbody = new NBody();
      nbody.InitBenchmark();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>("Unknown error", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("Finish test", new HttpHeaders(), HttpStatus.OK);
  }

  @GetMapping("/test3")
  public ResponseEntity<String> test3() {
    try {
      Mandelbrot mandelBrot = new Mandelbrot();
      mandelBrot.InitBenchmark();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>("Unknown error", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("Finish test", new HttpHeaders(), HttpStatus.OK);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleMissingRequestBody(Exception ex) {
    return new ResponseEntity<>("asdas", new HttpHeaders(), HttpStatus.OK);
  }
}
