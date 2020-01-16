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

package io.github.jsoagger.jfxcore.engine.components.wizard.editor.components;

import io.github.jsoagger.jfxcore.api.IEditInputComponent;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * To edit an inputview inside a popover.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class VLInputPopOverInPlaceEditor {//extends PopOver {

  private final VBox allOverContainer = new VBox();
  private final HBox content = new HBox();
  private final HBox actions = new HBox();

  private final Hyperlink okButton = new Hyperlink();
  private final Hyperlink cancelButton = new Hyperlink();
  private final IEditInputComponent editInputComponent;
  private AbstractViewController controller;


  /**
   * Constructor
   *
   * @param editInputComponent
   * @param controller
   */
  public VLInputPopOverInPlaceEditor(IEditInputComponent editInputComponent, AbstractViewController controller) {

    this.editInputComponent = editInputComponent;
    this.controller = controller;

    //setAutoHide(true);
    //setAutoFix(true);
    //setHideOnEscape(true);
    //setDetachable(false);
    //setArrowLocation(ArrowLocation.TOP_CENTER);

    // focusedProperty().addListener((pop, ov, nv) -> {
    //if (!nv) {
    //  hide();
    // }
    //});

    content.getChildren().add(editInputComponent.getContent());
    buildActions();
    allOverContainer.getChildren().addAll(content, actions);

    NodeHelper.setHVGrow(allOverContainer);
    allOverContainer.setStyle("-fx-border-color:#8c9eff;" + "-fx-border-width: 0;" + "-fx-padding: 32;");

    NodeHelper.setHgrow(editInputComponent.getContent(), content, actions);
  }


  /*
   * (non-Javadoc)
   *
   * @see javafx.stage.Window#show()
   */
  protected void show() {
    //    setContentNode(allOverContainer);
    //  super.show();
    // for these component, reshow the component if file chooser
  }


  /**
   * As the popup show on different window, update the input label in live when the popop component is
   * modified.
   *
   * @param label
   */
  public void addBinding(Label label) {
    // entry.addBinding(label);
  }


  private void buildActions() {
    /*okButton.setFocusTraversable(false);
    okButton.setText(controller.getGLocalised("SAVE_LABEL").toUpperCase());

    cancelButton.setText(controller.getGLocalised("CANCEL_LABEL").toUpperCase());
    cancelButton.setFocusTraversable(false);

    actions.setStyle("-fx-padding: 32 0 0 32; -fx-alignment: CENTER_RIGHT;-fx-spacing:16;");
    actions.getChildren().addAll(NodeHelper.horizontalSpacer(), okButton, cancelButton);
    cancelButton.setOnAction(e -> {
      hide();
    });

    okButton.setOnAction(e -> {
      hide();
      editInputComponent.commitModification();
    });

    editInputComponent.getContent().addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.ENTER) {
        hide();
      }
    });*/
  }


  public void localShow(Node parent) {
    /* if (isShowing()) {
      return;
    }

    show(parent);*/
  }


  /**
   * @param width
   */
  public void setContainerWidth(double width) {
    allOverContainer.setMinWidth(width);
  }


  public Node getRootContent() {
    return allOverContainer;
  }
}
