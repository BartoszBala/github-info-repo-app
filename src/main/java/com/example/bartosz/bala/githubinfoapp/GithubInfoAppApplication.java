package com.example.bartosz.bala.githubinfoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GithubInfoAppApplication {

  public static void main(String[] args) {
    System.setProperty("spring.jackson.serialization.INDENT_OUTPUT", "true");
    SpringApplication.run(GithubInfoAppApplication.class, args);
  }

}
