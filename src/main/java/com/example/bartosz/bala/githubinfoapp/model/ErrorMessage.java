package com.example.bartosz.bala.githubinfoapp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ErrorMessage {

  private LocalDate timeStamp;
  private String error;
  private String message;
  private String path;
}
