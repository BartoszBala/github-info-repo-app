package com.example.bartosz.bala.githubinfoapp.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubService {

  private static final String DOMAIN = "https://api.github.com/users/";

  public String getUserReposFromGitHub(String owner){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity httpEntity = new HttpEntity(httpHeaders);

    String url = DOMAIN + owner + "/repos";
    ResponseEntity<String> exchange = restTemplate.exchange(url,
        HttpMethod.GET,
        httpEntity,
        String.class);

    return exchange.getBody();
  }

}
