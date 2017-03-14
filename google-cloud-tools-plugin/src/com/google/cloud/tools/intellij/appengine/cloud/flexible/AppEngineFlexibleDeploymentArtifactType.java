/*
 * Copyright 2017 Google Inc. All Rights Reserved.
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

package com.google.cloud.tools.intellij.appengine.cloud.flexible;

import java.nio.file.Path;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the supported Java artifact types that we can deploy to App Engine flexible
 * environment.
 */
public enum AppEngineFlexibleDeploymentArtifactType {
  UNKNOWN,
  JAR,
  WAR;

  /**
   * Returns the right {@code AppEngineFlexibleDeploymentArtifactType} for the given {@code
   * deployPackage}.
   */
  @NotNull
  public static AppEngineFlexibleDeploymentArtifactType typeForPath(@Nullable Path deployPackage) {
    if (deployPackage != null) {
      if (deployPackage.toString().endsWith(".jar")) {
        return JAR;
      } else if (deployPackage.toString().endsWith(".war")) {
        return WAR;
      }
    }
    return UNKNOWN;
  }

  @Override
  public String toString() {
    return "." + name().toLowerCase(Locale.US);
  }
}