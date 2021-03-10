package com.example.bartosz.bala.githubinfoapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepoInfo {

  private String fullName;
  private String description;
  private String cloneUrl;
  private Integer stars;
  private String createdAt;

}
