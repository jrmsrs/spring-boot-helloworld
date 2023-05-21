package io.jrmsrs.echo.model;

public class ObjectResponse {
  private String message;
  private String status = "200";

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
