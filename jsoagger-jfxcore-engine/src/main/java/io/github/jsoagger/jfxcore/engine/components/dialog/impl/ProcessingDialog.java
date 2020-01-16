/*-
 * ========================LICENSE_START=================================
 * JSoagger 
 * %%
 * Copyright (C) 2019 JSOAGGER
 * %%
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
 * =========================LICENSE_END==================================
 */

package io.github.jsoagger.jfxcore.engine.components.dialog.impl;




import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.dialog.DialogStageWrapper;
import io.github.jsoagger.jfxcore.engine.components.dialog.VLDialog;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 mars 2018
 */
public class ProcessingDialog extends VLDialog {

  /**
   * Constructor
   */
  public ProcessingDialog() {}


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
  }


  @Override
  protected DialogStageWrapper _beforeShow() {
    dialogStageWrapper = new DialogStageWrapper();
    dialogStageWrapper.setContent(this.getDisplay());
    return dialogStageWrapper;
  }

  /**
   * @author Ramilafananana Vonjisoa
   * @mailTo yvonjisoa@nexitia.com
   * @date 15 mars 2018
   */
  public static class Builder {

    private String title;
    private String message;
    private Node icon;


    public Builder title(String title) {
      this.title = title;
      return this;
    }


    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder icon(Node icon) {
      this.icon = icon;
      return this;
    }


    public ProcessingDialog buildPrimary(AbstractViewController controller) {
      return buildAccent(controller);
    }


    public ProcessingDialog buildAccent(AbstractViewController controller) {
      ProcessingDialog dialog = (ProcessingDialog) Services.getBean("ProcessingDialog");
      dialog.buildFrom(controller, getAccentDialogConfig());
      dialog.hideFooter();
      dialog.dialogHeader.setTitle(title);
      dialog.dialogContent.setMessage(message);
      return dialog;
    }


    public ProcessingDialog build(AbstractViewController controller) {
      return buildAccent(controller);
    }
  }

  private static VLViewComponentXML getAccentDialogConfig() {
    return Services.getDialogConfig("accentInformationDialog.xml");
  }
}
