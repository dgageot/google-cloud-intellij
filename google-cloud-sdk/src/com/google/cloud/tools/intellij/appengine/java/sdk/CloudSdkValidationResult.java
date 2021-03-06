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

package com.google.cloud.tools.intellij.appengine.java.sdk;

import com.google.cloud.tools.appengine.cloudsdk.CloudSdk;

/** The result of a failed validation of a CloudSdk installation. */
public enum CloudSdkValidationResult {
  CLOUD_SDK_NOT_FOUND(
      CloudSdkMessageBundle.message("appengine.cloudsdk.location.invalid.message"), true),
  CLOUD_SDK_NOT_MINIMUM_VERSION(
      CloudSdkMessageBundle.message(
          "appengine.cloudsdk.version.support.message", CloudSdk.MINIMUM_VERSION),
      false),
  MALFORMED_PATH(
      CloudSdkMessageBundle.message("appengine.cloudsdk.location.badchars.message"), true),
  NO_APP_ENGINE_COMPONENT(
      CloudSdkMessageBundle.message("appengine.cloudsdk.java.components.missing")
          + "\n"
          + CloudSdkMessageBundle.message("appengine.cloudsdk.java.components.howtoinstall"),
      true),
  CLOUD_SDK_VERSION_FILE_ERROR(
      CloudSdkMessageBundle.message("appengine.cloudsdk.version.file.error"), false);

  private final String message;
  private final boolean isError;

  CloudSdkValidationResult(String message, boolean isError) {
    this.message = message;
    this.isError = isError;
  }

  public boolean isError() {
    return isError;
  }

  public boolean isWarning() {
    return !isError;
  }

  public String getMessage() {
    return message;
  }
}
