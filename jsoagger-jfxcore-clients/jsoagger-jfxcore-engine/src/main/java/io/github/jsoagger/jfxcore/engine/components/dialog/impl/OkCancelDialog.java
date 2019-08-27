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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 mars 2018
 */
public class OkCancelDialog extends VLDialog {

  private final Button okButton = new Button("OK");
  private final Button cancelButton = new Button("CANCEL");
  private Function<Object, Object> okCallBack;
  private Function<Object, Object> cancelCallBack;


  /**
   * Constructor
   */
  public OkCancelDialog() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
  }


  public void okCallBack(Function<Object, Object> okCallBack) {
    this.okCallBack = okCallBack;
  }


  public void canceclCallBack(Function<Object, Object> okCallBack) {
    this.okCallBack = okCallBack;
  }


  @Override
  protected DialogStageWrapper _beforeShow() {
    dialogStageWrapper = new DialogStageWrapper();
    okButton.getStyleClass().add("ep-button");
    okButton.addEventFilter(ActionEvent.ACTION, e -> {
      dialogStageWrapper.hide();
      if (okCallBack != null) {
        okCallBack.apply(this);
      }
    });

    okButton.getStyleClass().add("ep-button");
    cancelButton.addEventFilter(ActionEvent.ACTION, e -> {
      dialogStageWrapper.hide();
      if (cancelCallBack != null) {
        cancelCallBack.apply(this);
      }
    });

    final HBox c = new HBox(cancelButton, okButton);
    c.setStyle("-fx-alignment:CENTER_RIGHT;-fx-spacing:8");
    dialogFooter.setActions(c);

    dialogStageWrapper.setContent(this.getDisplay());
    return dialogStageWrapper;
  }


  /**
   * @param content
   */
  public void setContent(Node content) {

  }

  /**
   * @author Ramilafananana Vonjisoa
   * @mailTo yvonjisoa@nexitia.com
   * @date 15 mars 2018
   */
  public static class Builder {

    private String title;
    private String message;
    private Node content;
    private Function<Object, Object> okCallBack;
    private Function<Object, Object> cancelCallBack;


    public Builder title(String title) {
      this.title = title;
      return this;
    }


    public Builder message(String message) {
      this.message = message;
      return this;
    }


    public Builder content(Node content) {
      this.content = content;
      return this;
    }


    public Builder okCallBack(Function<Object, Object> okCallBack) {
      this.okCallBack = okCallBack;
      return this;
    }


    public Builder cancelCallBack(Function<Object, Object> cancelCallBack) {
      this.cancelCallBack = cancelCallBack;
      return this;
    }


    public OkCancelDialog build(AbstractViewController controller) {
      final OkCancelDialog dialog = (OkCancelDialog) Services.getBean("OkCancelDialog");
      return _build(dialog, content, controller, getDialogConfig());
    }


    public OkCancelDialog buildPrimary(AbstractViewController controller) {
      final OkCancelDialog dialog = (OkCancelDialog) Services.getBean("OkCancelDialog");
      return _build(dialog, content, controller, getPrimaryDialogConfig());
    }


    public OkCancelDialog buildAccent(AbstractViewController controller) {
      final OkCancelDialog dialog = (OkCancelDialog) Services.getBean("OkCancelDialog");
      return _build(dialog, content, controller, getAccentDialogConfig());
    }


    private OkCancelDialog _build(OkCancelDialog dialog, Node content2, AbstractViewController controller, VLViewComponentXML vlViewComponentXML) {
      if (content != null) {
        dialog.setContent(content);
        dialog.buildFrom(controller, getInputDialogConfig());
        dialog.dialogContent.setContent(content);
      } else {
        dialog.buildFrom(controller, getDialogConfig());
      }

      dialog.dialogHeader.setTitle(title);
      dialog.dialogContent.setMessage(message);
      dialog.okCallBack = okCallBack;
      dialog.cancelCallBack = cancelCallBack;

      dialog.okButton.getStyleClass().addAll("button-primary-border-transparent");
      dialog.cancelButton.getStyleClass().addAll("button-primary-border-transparent");
      return dialog;
    }
  }


  private static VLViewComponentXML getDialogConfig() {
    return Services.getDialogConfig("okCancelDialog.xml");
  }


  private static VLViewComponentXML getPrimaryDialogConfig() {
    return Services.getDialogConfig("okCancelDialog.xml");
  }


  private static VLViewComponentXML getAccentDialogConfig() {
    return Services.getDialogConfig("okCancelDialog.xml");
  }


  private static VLViewComponentXML getInputDialogConfig() {
    return Services.getDialogConfig("InputOkCancelDialog.xml");
  }
}
