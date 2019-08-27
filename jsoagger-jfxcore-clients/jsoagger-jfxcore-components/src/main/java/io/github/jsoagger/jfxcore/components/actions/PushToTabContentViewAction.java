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

package io.github.jsoagger.jfxcore.components.actions;


import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.components.tab.PushTabContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.TernaryMenuController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ViewForwardLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 24 févr. 2018
 */
public class PushToTabContentViewAction extends AbstractAction implements IAction {

  protected OperationData sourceData;
  protected String forModel;
  protected AbstractViewController controller;
  protected String viewId;


  /**
   * Default Constructor
   */
  public PushToTabContentViewAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (AbstractViewController) actionRequest.getController();
    // source controller must be a tabpane controlle
    StandardViewController parent = null;
    if (controller instanceof StandardTabPaneController) {
      parent = (StandardViewController) controller;
    }
    else {
      if(controller.getParent() != null && controller.getParent() instanceof TernaryMenuController) {
        parent = (StandardViewController) controller;
      }
      else {
        if(controller.getParent().getParent() != null &&
            controller.getParent().getParent() instanceof TernaryMenuController) {
          parent = (StandardViewController) controller.getParent();
        }
      }
    }


    if (parent == null) {
      if (controller.getParent() != null && controller.getParent() instanceof StandardTabPaneController) {
        parent = (StandardTabPaneController) controller.getParent();
      }
    }

    forModel = controller.getModelFullId();
    viewId = (String) actionRequest.getProperty("viewId");
    sourceData = (OperationData) actionRequest.getProperty("sourceData");
    if (sourceData == null) {
      sourceData = controller.getOpData();
    }

    if(parent != null && parent instanceof StandardTabPaneController) {
      PushTabContentEvent ev = new PushTabContentEvent();
      ev.setSourceController(controller);
      //ev.setParentController(parent);
      ev.setProperty("viewId", viewId);
      ev.setModel(sourceData);

      // ev.processedContentProperty().addListener((a, b, c) -> updateRSCHeader(controller.getRootStructure(), c));
      parent.handle(ev);
      resultProperty.set(ActionResult.success());
      return ;
    }

    if(controller instanceof StandardController) {
      if(((StandardController)controller).getLayoutManager() instanceof ViewForwardLayoutManager) {
        final StandardViewController view = StandardViewUtils.forId(controller.getRootStructure(), viewId, sourceData);
        if(parent != null)
          view.setParent(parent);

        Platform.runLater(()-> {

          ViewForwardLayoutManager lm = (ViewForwardLayoutManager) ((StandardViewController)view.getParent()).getLayoutManager();
          int pushedc = lm.getPushedContent() != null ? 0 : lm.getPushedContent().size();

          Node content = null;
          if(pushedc == 0 ) {
            content = buildLayout((StandardViewController) view.getParent(), view.processedView());
          }
          else {
            content = buildLayout(null, view.processedView());
          }

          ((StandardViewController)view.getParent()).getLayoutManager().pushContent(content);
        });
      }
    }


    if (parent == null) {
      throw new RuntimeException("Source Controller must be a StandardTabPaneController for PushToTabContentViewAction");
    }
  }


  protected Node buildLayout(StandardViewController parent, Node content) {
    if(parent != null) {
      AnchorPane anchorPane = new AnchorPane();

      Button back = new JFXButton();
      back.getStyleClass().removeAll("button");
      back.getStyleClass().addAll("button-xsmall","button-primary-border", "ep-button");
      IconUtils.setFontIcon("gmi-arrow-back:22", back);
      Node hbox = NodeHelper.wrapInGrowingHbox(back);

      back.setOnAction(e -> parent.getLayoutManager().popContent());

      AnchorPane.setRightAnchor(hbox, 0.);
      AnchorPane.setLeftAnchor(hbox, 0.);
      AnchorPane.setTopAnchor(hbox, 0.);
      anchorPane.getChildren().add(hbox);
      hbox.setStyle("-fx-border-width:0 0 1 0;-fx-background-color:white;"
          + "-fx-border-color:-internal-border-color;"
          + "-fx-padding: 8;-fx-alignment:CENTER_LEFT;-fx-min-height:42");

      ScrollPane sp = new ScrollPane();
      sp.setFitToHeight(true);
      sp.setFitToWidth(true);
      sp.setContent(content);

      AnchorPane.setLeftAnchor(sp, 0.);
      AnchorPane.setRightAnchor(sp, 0.);
      AnchorPane.setBottomAnchor(sp, 0.);
      AnchorPane.setTopAnchor(sp, 55.);
      anchorPane.getChildren().add(sp);
      return anchorPane;
    }

    ScrollPane sp = new ScrollPane();
    sp.setFitToHeight(true);
    sp.setFitToWidth(true);
    sp.setContent(content);

    return sp;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "PushStructureContentAction";
  }


  /**
   * Getter of forModel
   *
   * @return the forModel
   */
  public String getForModel() {
    return forModel;
  }


  /**
   * Setter of forModel
   *
   * @param forModel the forModel to set
   */
  public void setForModel(String forModel) {
    this.forModel = forModel;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Getter of viewId
   *
   * @return the viewId
   */
  public String getRedirectToView() {
    return viewId;
  }


  /**
   * Setter of viewId
   *
   * @param viewId the viewId to set
   */
  public void setRedirectToView(String viewId) {
    this.viewId = viewId;
  }
}
