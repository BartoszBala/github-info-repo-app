package com.example.bartosz.bala.githubinfoapp.enums;

public enum ErrorTypeEnum {

  INCORRECT_SORT_PARAMETER("niepoprawny parametr sortowania"), NO_USER("brak użytkownika w GITHUB") ;

  private String message;


  ErrorTypeEnum(String message) {
  this.message=message;
  }

  public String getMessage() {
    return message;
  }
}
