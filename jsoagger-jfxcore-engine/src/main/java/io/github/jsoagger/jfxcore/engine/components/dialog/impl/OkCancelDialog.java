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
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.dialog.DialogStageWrapper;
import io.github.jsoagger.jfxcore.engine.components.dialog.VLDialog;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 mars 2018
 */
public class OkCancelDialog extends VLDialog {

  private final Button okButton = NodeHelper.jfxButton("OK");
  private final Button cancelButton = NodeHelper.jfxButton("CANCEL");
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
    okButton.getStyleClass().addAll("button-primary-border", "button-xl", "button-xl-rounded");
    cancelButton.getStyleClass().addAll("button-xl", "button-xl-rounded");
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
    okButton.addEventFilter(ActionEvent.ACTION, e -> {
      dialogStageWrapper.hide();
      if (okCallBack != null) {
        okCallBack.apply(this);
      }
    });

    cancelButton.requestFocus();
    cancelButton.addEventFilter(ActionEvent.ACTION, e -> {
      dialogStageWrapper.hide();
      if (cancelCallBack != null) {
        cancelCallBack.apply(this);
      }
    });

    final VBox c = new VBox(okButton, cancelButton);
    c.setStyle("-fx-alignment:CENTER;-fx-spacing:16;-fx-padding:8");
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


    private OkCancelDialog _build(OkCancelDialog dialog, Node content2,
        AbstractViewController controller, VLViewComponentXML vlViewComponentXML) {
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

      return dialog;
    }
  }

  private static VLViewComponentXML jojo() {
    VLViewComponentXML xml = new VLViewComponentXML();

    VLViewComponentXML content = new VLViewComponentXML();
    content.setId("Content");
    xml.getSubcomponents().add(xml);
    content.addProperty("ikonli", "fa-info-circle:32");
    content.addProperty("iconStyleClass", "blue-ikonli");
    content.addProperty("styleClass", "ep-dialog-content,ep-dialog-white-content");
    content.addProperty("messageStyleClass", "ep-dialog-message,ep-dialog-white-content-message");

    VLViewComponentXML footer = new VLViewComponentXML();
    footer.setId("Footer");
    footer.addProperty("styleClass", "ep-dialog-footer,ep-dialog-white-footer");
    xml.getSubcomponents().add(footer);


    VLViewComponentXML header = new VLViewComponentXML();
    header.setId("Header");
    header.addProperty("titleStyleClass", "ep-dialog-title");
    header.addProperty("styleClass", "ep-dialog-header");
    xml.getSubcomponents().add(header);

    return xml;
  }

  private static VLViewComponentXML getDialogConfig() {
    // return Services.getDialogConfig("okCancelDialog.xml");
    return jojo();
  }


  private static VLViewComponentXML getPrimaryDialogConfig() {
    // return Services.getDialogConfig("okCancelDialog.xml");
    return jojo();
  }


  private static VLViewComponentXML getAccentDialogConfig() {
    // return Services.getDialogConfig("okCancelDialog.xml");
    return jojo();
  }


  private static VLViewComponentXML getInputDialogConfig() {
    // return Services.getDialogConfig("InputOkCancelDialog.xml");
    return jojo();
  }
}
