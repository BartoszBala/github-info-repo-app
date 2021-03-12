package com.example.bartosz.bala.githubinfoapp.api;


import com.example.GiHubRepository;
import com.example.bartosz.bala.githubinfoapp.model.RepoInfo;
import com.example.bartosz.bala.githubinfoapp.services.GitHubService;
import com.example.bartosz.bala.githubinfoapp.services.RepoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//http://localhost:8080/repositories/BartoszBala/?sort=star,desc
@RestController
@RequestMapping("/repositories/{owner}/")
public class RepoGitHubApi {


  @Autowired
  private RepoService repoService;

  @Autowired
  private GitHubService gitHubService;


  @RequestMapping
  public List<RepoInfo> getAllRepoForUser(@RequestParam String sort, @PathVariable("owner") String owner) {

    String json = gitHubService.getUserReposFromGitHub(owner);


    ObjectMapper objectMapper = new ObjectMapper();
    GiHubRepository[] repos = new GiHubRepository[0];
    try {
      repos = objectMapper.readValue(json, GiHubRepository[].class);
    } catch (JsonProcessingException e) {
      //TODO logs
    }

    repoService.sortList(chooseSortType(sort), repoService.convertGitHubRepositoryToRepoInfo(repos));

    return repoService.getAll();
  }



  private String[] chooseSortType(String sort) {

    String[] sortBy = sort.split(",");
    if (sortBy.length == 2 && Arrays.asList("desc", "asc").contains(sortBy[1])) {   //fixme ingore casesensitive
      return sortBy;
    }
    return null;

  }


}
