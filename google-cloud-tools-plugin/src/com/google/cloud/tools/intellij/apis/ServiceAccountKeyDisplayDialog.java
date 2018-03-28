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

package com.google.cloud.tools.intellij.apis;

import com.google.cloud.tools.intellij.util.GctBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Dialog confirming the download of the service account JSON key with information on how to set the
 * environment variables for local run.
 */
public class ServiceAccountKeyDisplayDialog extends DialogWrapper {
  private static final String CLOUD_PROJECT_ENV_VAR_KEY = "GOOGLE_CLOUD_PROJECT";
  private static final String CREDENTIAL_ENV_VAR_KEY = "GOOGLE_APPLICATION_CREDENTIALS";
  private static final String ENV_VAR_DISPLAY_FORMAT = "%s=%s";

  private JLabel yourServiceAccountKeyLabel;
  private JLabel envVarInfoText;
  private JTable envVarTable;
  private JButton printButton;
  private JLabel downloadPathLabel;
  private JPanel mainPanel;
  private JPanel extensionPanel;


  ServiceAccountKeyDisplayDialog(@Nullable Project project, @NotNull String gcpProjectId,
      @NotNull String downloadPath) {
    super(project);
    init();

    setTitle(GctBundle.message("cloud.apis.service.account.key.downloaded.title"));
    downloadPathLabel.setText(downloadPath);

    envVarInfoText.setBackground(mainPanel.getBackground());

    String credentialEnvVar =
        String.format(ENV_VAR_DISPLAY_FORMAT, CREDENTIAL_ENV_VAR_KEY, downloadPath);
    String cloudProjectEnvVar =
        String.format(ENV_VAR_DISPLAY_FORMAT, CLOUD_PROJECT_ENV_VAR_KEY, gcpProjectId);

    DefaultTableModel tableModel = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    tableModel.setColumnCount(1);
    tableModel.addRow(new String[]{credentialEnvVar});
    tableModel.addRow(new String[]{cloudProjectEnvVar});
    envVarTable.setModel(tableModel);
    envVarTable.setRowSelectionAllowed(false);
  }

  @NotNull
  @Override
  protected Action[] createActions() {
    return new Action[] {getOKAction()};
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return mainPanel;
  }

  public JPanel getExtensionPanel() {
    return extensionPanel;
  }
}
