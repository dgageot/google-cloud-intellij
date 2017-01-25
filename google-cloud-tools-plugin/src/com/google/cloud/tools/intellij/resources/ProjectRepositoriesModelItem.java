/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.intellij.resources;

import com.google.api.services.source.model.Repo;
import com.google.cloud.tools.intellij.login.CredentialedUser;
import com.google.cloud.tools.intellij.util.GctBundle;
import com.google.cloud.tools.intellij.vcs.CloudRepositoryService;

import com.intellij.openapi.components.ServiceManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * TreeNode representation of the set of available Cloud Source Repositories for a given GCP
 * project.
 */
public class ProjectRepositoriesModelItem extends DefaultMutableTreeNode {

  private String cloudProject;
  private CredentialedUser user;
  private CloudRepositoryService cloudRepositoryService;

  public ProjectRepositoriesModelItem(@NotNull String cloudProject,
      @NotNull CredentialedUser user) {
    this.cloudProject = cloudProject;
    this.user = user;

    cloudRepositoryService = ServiceManager.getService(CloudRepositoryService.class);

    setUserObject(cloudProject);
  }

  public void loadRepositories(Runnable onComplete) {
    cloudRepositoryService
        .listAsync(user, cloudProject)
        .thenAccept(response -> {
          if (response == null) {
            return;
          }

          removeAllChildren();
          List<Repo> repositories = response.getRepos();
          if (!response.isEmpty() && repositories != null) {
            repositories.forEach(repo -> {
              Object name = repo.get("name");
              if (name != null) {
                add(new RepositoryModelItem(name.toString()));
              }
            });
          } else {
            add(new ResourceEmptyModelItem(GctBundle.message("cloud.repository.list.empty")));
          }

          onComplete.run();
        })
        .exceptionally(response -> {
          removeAllChildren();
          add(new ResourceErrorModelItem(GctBundle.message("cloud.repository.list.error")));

          onComplete.run();
          return null;
        });
  }

}
