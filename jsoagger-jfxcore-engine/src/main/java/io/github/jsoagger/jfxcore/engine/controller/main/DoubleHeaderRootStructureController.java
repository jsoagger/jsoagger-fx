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

package io.github.jsoagger.jfxcore.engine.controller.main;



import java.net.URL;

import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * {@link RootStructureController} have only one header, this controller handles additionnal header.
 * <p>
 *
 * @author Ramilafananana VONJISOA
 *
 */
public class DoubleHeaderRootStructureController extends RootStructureController {

  @FXML
  protected Pane secondaryHeaderStack;

  @FXML
  protected Pane allHeaderStack;

  @FXML
  protected Pane defaultActionNodeWrapper;


  public DoubleHeaderRootStructureController() {
    super();
  }


  @Override
  protected void process() {
    super.process();
    secondaryHeaderStack.managedProperty().bind(secondaryHeaderStack.visibleProperty());
    secondaryHeaderStack.setVisible(false);
  }

  @Override
  public VLViewConfigXML config() {
    VLViewConfigXML config = super.config();
    return config;
  }


  @Override
  public void setMaterialNode(Node materialNode) {
    this.materialNode = materialNode;
    if (materialNode != null) {
      Platform.runLater(() -> {
        defaultActionNodeWrapper.getChildren().clear();
        defaultActionNodeWrapper.getChildren().add(materialNode);
        initMaterialNodeDrag();
      });
    }
  }

  public void hideAllHeader() {
    if (Platform.isFxApplicationThread()) {
      allHeaderStack.setVisible(false);
      AnchorPane.setTopAnchor(rootStructureWrapper, 0.0);
    } else {
      Platform.runLater(() -> {
        allHeaderStack.setVisible(false);
        AnchorPane.setTopAnchor(rootStructureWrapper, 0.0);
      });
    }
  }

  public void showAllHeader() {
    if (Platform.isFxApplicationThread()) {
      allHeaderStack.setVisible(true);
      setAnchors();
    } else {
      Platform.runLater(() -> {
        allHeaderStack.setVisible(true);
        setAnchors();
      });
    }
  }

  /*-----------------------------------------------------------------------------
  |
  |  HANDLING SECONDARY HEADER
  |
   *=============================================================================*/
  public void setSecondaryheader(Node node) {
    if (Platform.isFxApplicationThread()) {
      _doSetSecondaryheader(node);
    } else {
      Platform.runLater(() -> _doSetSecondaryheader(node));
    }
  }

  public void hideSecondaryHeader() {
    if (Platform.isFxApplicationThread()) {
      _doHideSecondaryheader();
    } else {
      Platform.runLater(() -> _doHideSecondaryheader());
    }
  }

  @Override
  public void popContent() {
    super.popContent();
    secondaryHeaderStack.setVisible(true);
  }

  private void _doHideSecondaryheader() {
    secondaryHeaderStack.setVisible(false);
    // secondaryHeaderStack.getChildren().clear();
  }

  public void _doSetSecondaryheader(Node node) {
    secondaryHeaderStack.setVisible(true);
    secondaryHeaderStack.getChildren().clear();
    secondaryHeaderStack.getChildren().add(node);

    secondaryHeaderStack.heightProperty()
        .addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
          pushedContentWrapper.translateYProperty().set(newValue.doubleValue() + 2);
          rootStructureWrapper.translateYProperty().set(newValue.doubleValue() + 2);
        });
  }


  /*-----------------------------------------------------------------------------
  |
  |  OVERRIDING FXML LOCATION
  |
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  protected URL getFXMLFileName() {
    return RootStructureController.class.getResource("DoubleHeaderRootStructureView.fxml");
  }

  @Override
  public void displayMainView() {
    super.displayMainView();
  }


  @Override
  public void displayPushedView() {
    super.displayPushedView();
  }
}
