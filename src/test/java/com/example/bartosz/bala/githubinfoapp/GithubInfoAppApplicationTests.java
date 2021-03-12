package com.example.bartosz.bala.githubinfoapp;


import com.example.bartosz.bala.githubinfoapp.api.RepoGitHubApi;
import com.example.bartosz.bala.githubinfoapp.model.RepoInfo;
import com.example.bartosz.bala.githubinfoapp.services.RepoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
    import static org.hamcrest.CoreMatchers.is;
    import static org.hamcrest.Matchers.greaterThanOrEqualTo;
    import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
    import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(RepoGitHubApi.class)
public class GithubInfoAppApplicationTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  RepoService repoService;

  @Test
  public void contextLoads() {
  }

  @Test
  public void givenRepos_whenGetRepos_thenReturnJsonArray()
      throws Exception {

    RepoInfo repo = new RepoInfo("Hello World","First repo","http...",14,"2020-12-12");

    List<RepoInfo> allRepo = Arrays.asList(repo);

    given(repoService.getAll()).willReturn(allRepo);

    mvc.perform(get("/repositories/BartoszBala/?sort=star,desc")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect( jsonPath("$", hasSize(1)))
        .andExpect( jsonPath("$[0].fullName", is(repo.getFullName())));
  }

}
