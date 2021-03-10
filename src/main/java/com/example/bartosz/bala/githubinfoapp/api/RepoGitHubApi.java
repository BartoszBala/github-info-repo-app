package com.example.bartosz.bala.githubinfoapp.api;


import com.example.GiHubRepository;
import com.example.bartosz.bala.githubinfoapp.model.RepoInfo;
import com.example.bartosz.bala.githubinfoapp.services.RepositoriumConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repositories/{owner}/")
public class RepoGitHubApi {

  private static final String DOMAIN = "https://api.github.com/users/";

  @Autowired
  private RepositoriumConverter repositoriumConverter;


  @GetMapping
  public String getAllRepoForUser(@RequestParam String sort, @PathVariable("owner") String owner) {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity httpEntity = new HttpEntity(httpHeaders);

    String url = DOMAIN + owner + "/repos";
    ResponseEntity<String> exchange = restTemplate.exchange(url,
        HttpMethod.GET,
        httpEntity,
        String.class);

    String json = exchange.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    String response = null;

    try {
      GiHubRepository[] repos = objectMapper.readValue(json, GiHubRepository[].class);
      List<RepoInfo> list = repositoriumConverter.convertGitHubRepositoryToRepoInfo(repos);

      List<RepoInfo> sortedList = sortList(chooseSortType(sort), list);


      response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sortedList);


    } catch (JsonProcessingException e) {
      //TODO log
    }


    return response;
  }

  private List<RepoInfo> sortList(String[] chooseSortType, List<RepoInfo> list) {

    if (chooseSortType[1].equalsIgnoreCase("asc")) {
      return list.stream().sorted(Comparator.comparingInt(RepoInfo::getStars)).collect(Collectors.toList());
    } else {
      return list.stream().sorted(Comparator.comparingInt(RepoInfo::getStars).reversed()).collect(Collectors.toList());
    }

  }

  private String[] chooseSortType(String sort) {

    String[] sortBy = sort.split(",");
    if (sortBy.length == 2 && Arrays.asList("desc", "asc").contains(sortBy[1])) {   //fixme ingore casesensitive
      return sortBy;
    }
    return null;

  }


}
