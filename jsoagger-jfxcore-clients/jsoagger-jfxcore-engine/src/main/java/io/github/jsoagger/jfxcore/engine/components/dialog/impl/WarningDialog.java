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




import java.util.function.Function;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.dialog.DialogStageWrapper;
import io.github.jsoagger.jfxcore.engine.components.dialog.VLDialog;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 mars 2018
 */
public class WarningDialog extends VLDialog {

  private final Button okButton = new Button("Ok");
  private Function<Object, Object> callBack;


  /**
   * Constructor
   */
  public WarningDialog() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
  }


  public void callBack(Function<Object, Object> callBack) {
    this.callBack = callBack;
  }


  @Override
  protected DialogStageWrapper _beforeShow() {
    dialogStageWrapper = new DialogStageWrapper();
    okButton.addEventFilter(ActionEvent.ACTION, e -> {
      dialogStageWrapper.hide();
      if (callBack != null) {
        callBack.apply(this);
      }
    });

    dialogFooter.setActions(okButton);
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
    private Function<Object, Object> callBack;


    public Builder title(String title) {
      this.title = title;
      return this;
    }


    public Builder message(String message) {
      this.message = message;
      return this;
    }


    public void callBack(Function<Object, Object> callBack) {
      this.callBack = callBack;
    }


    public WarningDialog buildPrimary(AbstractViewController controller) {
      final WarningDialog dialog = (WarningDialog) Services.getBean("WarningDialog");
      dialog.buildFrom(controller, getPrimaryDialogConfig());
      dialog.dialogHeader.setTitle(title);
      dialog.dialogContent.setMessage(message);
      dialog.callBack = this.callBack;
      if(callBack == null) {
        dialog.hideFooter();
      }
      dialog.okButton.getStyleClass().addAll("button-xl", "button-primary");
      return dialog;
    }


    public WarningDialog buildAccent(AbstractViewController controller) {
      final WarningDialog dialog = (WarningDialog) Services.getBean("WarningDialog");
      dialog.buildFrom(controller, getAccentDialogConfig());
      dialog.dialogHeader.setTitle(title);
      dialog.dialogContent.setMessage(message);
      dialog.callBack = this.callBack;
      if(callBack == null) {
        dialog.hideFooter();
      }
      dialog.okButton.getStyleClass().addAll("button-xl", "button-accent");
      return dialog;
    }


    public WarningDialog build(AbstractViewController controller) {
      final WarningDialog dialog = (WarningDialog) Services.getBean("WarningDialog");
      dialog.buildFrom(controller, getDialogConfig());
      dialog.dialogHeader.setTitle(title);
      dialog.dialogContent.setMessage(message);
      dialog.callBack = this.callBack;
      if(callBack == null) {
        dialog.hideFooter();
      }
      dialog.okButton.getStyleClass().addAll("button-primary-border-transparent");
      return dialog;
    }
  }


  private static VLViewComponentXML getPrimaryDialogConfig() {
    return Services.getDialogConfig("primaryWarningDialog.xml");
  }


  private static VLViewComponentXML getAccentDialogConfig() {
    return Services.getDialogConfig("accentWarningDialog.xml");
  }


  private static VLViewComponentXML getDialogConfig() {
    return Services.getDialogConfig("whiteWarningDialog.xml");
  }
}
