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

package io.github.jsoagger.jfxcore.engine.components.picker;



import java.util.function.Function;

//import org.controlsfx.control.PopOver;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

//import org.controlsfx.control.PopOver;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.input.AbstractInputComponent;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class AbstractObjectPicker extends AbstractInputComponent {

  /*-----------------------------------------------------------------------------
  | PRIVATE FIEDLS
   *=============================================================================*/
  private final StackPane rootContainer = new StackPane();
  protected FlowPane chipsContainer = null;
  protected final Hyperlink picker = new Hyperlink();

  protected final HBox bottomToolbar = new HBox();
  protected Hyperlink okButton = new Hyperlink();
  protected Function<Object, Object> okCallback;

  //protected PopOver popOver;
  protected VBox content = null;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
   *=============================================================================*/
  public AbstractObjectPicker() {
    super();
  }


  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    content = new VBox();
    content.setSpacing(16);
    content.setStyle("-fx-padding: 24;");

    // build the input
    chipsContainer = new FlowPane();
    chipsContainer.setHgap(2);
    chipsContainer.setVgap(2);
    chipsContainer.setStyle("-fx-border-color: -grey-color-200; " + "-fx-border-width: 1;");

    // build the picker icon
    picker.setFocusTraversable(false);

    final HBox box = new HBox();
    box.setAlignment(Pos.CENTER_LEFT);
    box.getChildren().addAll(chipsContainer, picker);
    NodeHelper.setHgrow(box);
    NodeHelper.setHgrow(chipsContainer);

    rootContainer.getChildren().add(box);
    rootContainer.setStyle("-fx-padding: 0 0 4 0;");

    // show popup when clicked
    picker.setOnAction(e -> {
      buildAndShowPopover();
    });

    okButton = new Hyperlink();
    bottomToolbar.getChildren().addAll(okButton);
    bottomToolbar.setStyle("-fx-alignment: CENTER_RIGHT;" + "-fx-spacing: 32;" + "-fx-padding: 8;" + "-fx-pref-height: 52;");

    okButton.getStyleClass().add("dialog-hyperlink ");
    okButton.setFocusTraversable(false);
    okButton.setText(controller.getLocalised("OK_LABEL").toUpperCase());
    okButton.setOnAction(e -> {
      okButtonCallBack();
    });

    content.getChildren().add(bottomToolbar);
  }


  /**
   * @param okcallback2
   */
  public void setOKButtonCallback(Function<Object, Object> okcallback) {
    okCallback = okcallback;
  }


  protected void okButtonCallBack() {
    //popOver.hide();
    if (okCallback != null) {
      okCallback.apply(null);
    }
  }


  /**
   * Override this method to build content of popover. In that way, the content is build when the
   * popover is to be displeyed. Not when it is constructed.
   */
  protected void buildAndShowPopover() {
    /* popOver = new PopOver();
    popOver.setContentNode(content);
    popOver.setAutoHide(true);
    popOver.setAutoFix(true);
    popOver.setHideOnEscape(true);
    popOver.setDetachable(true);
    popOver.setDetached(false);
    popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
    popOver.show(chipsContainer);*/
  }


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.engine.components.api.IValideableInput#getDisplay()
   */
  @Override
  public Node getDisplay() {
    return rootContainer;
  }


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.engine.components.common.custom.AbstractComponent# getComponent()
   */
  @Override
  public Node getComponent() {
    return chipsContainer;
  }
}
