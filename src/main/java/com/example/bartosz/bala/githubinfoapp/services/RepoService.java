package com.example.bartosz.bala.githubinfoapp.services;

import com.example.GiHubRepository;
import com.example.bartosz.bala.githubinfoapp.model.RepoInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepoService {

  private List<RepoInfo> repoListSorted = new ArrayList<>();


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

  public void sortList(String[] chooseSortType, List<RepoInfo> list) {

    if (chooseSortType[1].equalsIgnoreCase("asc")) {
      repoListSorted = list.stream().sorted(Comparator.comparingInt(RepoInfo::getStars)).collect(Collectors.toList());
    } else {
      repoListSorted = list.stream().sorted(Comparator.comparingInt(RepoInfo::getStars).reversed()).collect(Collectors.toList());
    }

  }

  public List<RepoInfo> getAll(){

    return repoListSorted;
  }


}
