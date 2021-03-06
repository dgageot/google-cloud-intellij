/*
 * Copyright 2018 Google Inc. All Rights Reserved.
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

package com.google.cloud.tools.intellij.appengine.java.cloud;

import com.google.cloud.tools.intellij.appengine.java.cloud.flexible.UserSpecifiedPathDeploymentSource;
import com.google.cloud.tools.intellij.appengine.java.facet.standard.AppEngineStandardFacet;
import com.google.common.collect.ImmutableList;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModulePointer;
import com.intellij.openapi.module.ModulePointerManager;
import com.intellij.openapi.project.Project;
import com.intellij.remoteServer.configuration.deployment.DeploymentSource;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/** An {@link AppEngineDeploymentSourceProvider} that collects module-based deployment sources. */
public class AppEngineUserSpecifiedPathDeploymentSourceProvider
    implements AppEngineDeploymentSourceProvider {

  /**
   * Collects a list of {@link UserSpecifiedPathDeploymentSource sources} available for deployment
   * to App Engine flexible:
   *
   * <p>User browsable jar/war deployment sources are included only if there are no App Engine
   * standard modules - those that have an App Engine standard facet.
   *
   * @return a list of {@link UserSpecifiedPathDeploymentSource}
   */
  @Override
  public List<DeploymentSource> getDeploymentSources(@NotNull Project project) {
    boolean hasStandardModules =
        Stream.of(ModuleManager.getInstance(project).getModules())
            .anyMatch(AppEngineStandardFacet::hasFacet);

    if (!hasStandardModules) {
      return ImmutableList.of(createUserSpecifiedPathDeploymentSource(project));
    }

    return ImmutableList.of();
  }

  private static UserSpecifiedPathDeploymentSource createUserSpecifiedPathDeploymentSource(
      @NotNull Project project) {
    ModulePointer modulePointer =
        ModulePointerManager.getInstance(project)
            .create(UserSpecifiedPathDeploymentSource.moduleName);

    return new UserSpecifiedPathDeploymentSource(modulePointer);
  }
}
