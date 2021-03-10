package com.example.bartosz.bala.githubinfoapp.services;

import com.example.GiHubRepository;
import com.example.bartosz.bala.githubinfoapp.model.RepoInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoriumConverter {


  public List<RepoInfo> convertGitHubRepositoryToRepoInfo(GiHubRepository[] repoArray) {
    List<RepoInfo> repoInfoList = new ArrayList<>();
    for (GiHubRepository giHubRepository : repoArray) {
      RepoInfo repoInfo = new RepoInfo();
      repoInfo.setFullName(giHubRepository.getFullName());
      repoInfo.setCloneUrl(giHubRepository.getCloneUrl());
      repoInfo.setDescription(giHubRepository.getDescription());
      repoInfo.setCreatedAt(giHubRepository.getCreatedAt());
      repoInfo.setStars(giHubRepository.getStargazersCount());
      repoInfoList.add(repoInfo);
    }
    return repoInfoList;
  }


}
