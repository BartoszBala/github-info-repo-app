package com.example.bartosz.bala.githubinfoapp.api;


import com.example.GiHubRepository;
import com.example.bartosz.bala.githubinfoapp.enums.ErrorTypeEnum;
import com.example.bartosz.bala.githubinfoapp.model.ErrorMessage;
import com.example.bartosz.bala.githubinfoapp.services.RepoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//http://localhost:8080/repositories/BartoszBala/?sort=star,desc
@RestController
@RequestMapping("/repositories/{owner}/")
public class RepoGitHubApi {

  private static final String DOMAIN = "https://api.github.com/users/";


  @Autowired
  private RepoService repoService;


  @RequestMapping
  public <T> List<T> getAllRepoForUser(@RequestParam String sort, @PathVariable("owner") String owner) throws JsonProcessingException {

    try {
      String[] sortType = chooseSortType(sort);
      String json = connectToGitHubApiAndTakeUserRepos(owner);
      ObjectMapper objectMapper = new ObjectMapper();
      GiHubRepository[] repos;

      repos = objectMapper.readValue(json, GiHubRepository[].class);


      repoService.sortList(sortType, repoService.convertGitHubRepositoryToRepoInfo(repos));
    } catch (HttpClientErrorException e) {
      return createErrorMessage(e, owner, sort, ErrorTypeEnum.NO_USER);
    } catch (InvalidParameterException e) {
      return createErrorMessage(e, owner, sort, ErrorTypeEnum.INCORRECT_SORT_PARAMETER);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    }

    return (List<T>) repoService.getAll();
  }

  private <T> List<T> createErrorMessage(Exception e, String owner, String sort, ErrorTypeEnum errorType) {

    List<ErrorMessage> errorMessages = new ArrayList<>();

    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setTimeStamp(LocalDate.now());
    errorMessage.setMessage(errorType.getMessage());
    errorMessage.setError(e.toString());
    errorMessage.setPath("repositories/" + owner + "/?sort=" + sort);
    errorMessages.add(errorMessage);

    return (List<T>) errorMessages;
  }

  private String connectToGitHubApiAndTakeUserRepos(@PathVariable("owner") String owner) throws HttpClientErrorException {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity httpEntity = new HttpEntity(httpHeaders);
    String responseString = null;


    String url = DOMAIN + owner + "/repos";
    ResponseEntity<String> exchange = restTemplate.exchange(url,
        HttpMethod.GET,
        httpEntity,
        String.class);
    responseString = exchange.getBody();


    return responseString;
  }


  private String[] chooseSortType(String sort) throws InvalidParameterException {

    String[] sortBy = sort.split(",");
    if (sortBy.length == 2 && Arrays.asList("desc", "asc").contains(sortBy[1])) {   //fixme ingore casesensitive
      return sortBy;
    } else {
      throw new InvalidParameterException();
    }


  }


}
