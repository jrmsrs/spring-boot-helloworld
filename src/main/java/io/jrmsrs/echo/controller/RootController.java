package io.jrmsrs.echo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jrmsrs.echo.model.ObjectResponse;

@RestController
@RequestMapping("/")
public class RootController {
  @GetMapping("")
  public Object getRoot() {
    ObjectResponse response = new ObjectResponse();
    response.setMessage("See available endpoints at /swagger-ui.html");
    return response;
  }

  @GetMapping("error")
  @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
  public Object getError() {
    ObjectResponse response = new ObjectResponse();
    response.setStatus("4XX Client Error");
    response.setMessage("Something went wrong. See docs at /swagger-ui.html for more info.");
    return response;
  }
}
