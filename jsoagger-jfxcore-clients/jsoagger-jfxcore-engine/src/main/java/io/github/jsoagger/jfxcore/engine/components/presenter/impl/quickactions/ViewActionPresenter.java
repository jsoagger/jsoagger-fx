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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions;



import io.github.jsoagger.jfxcore.engine.accessrule.AbstractRuleResolver;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAccessRuleResolver.UIAccessRule;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import com.jfoenix.controls.JFXButton;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public abstract class ViewActionPresenter implements IBuildable {

  protected Node node;
  protected VLViewComponentXML configuration;
  protected AbstractViewController controller;
  protected String type = null;

  // the model associated
  protected OperationData forModel;

  protected String redirectToViewKey = null;
  protected AbstractRuleResolver visibilityResolver;

  protected ViewActionPresenter relativeTo;


  /**
   * Default Constructor
   */
  public ViewActionPresenter() {}


  public void resolveVisibility() {
    if (visibilityResolver != null) {
      visibilityResolver.put("forModel", forModel);
      UIAccessRule accessrule = visibilityResolver.isAccessible(controller, configuration);
      if (accessrule == UIAccessRule.SHOW) {
        node.setVisible(true);
      } else if (accessrule == UIAccessRule.DISABLED) {
        node.setDisable(true);
      } else if (accessrule == UIAccessRule.HIDDEN) {
        node.setVisible(false);
      } else {
        node.setVisible(false);
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    this.configuration = configuration;
    type = configuration.getPropertyValue("type");
    redirectToViewKey = configuration.getPropertyValue("redirectToViewKey");

    String visibilityResolver = configuration.getPropertyValue("visibilityResolver");
    if (StringUtils.isNotBlank(visibilityResolver)) {
      this.visibilityResolver = (AbstractRuleResolver) Services.getBean(visibilityResolver);
    }

    node = build(configuration);
    resolveVisibility();
  }


  protected ButtonBase build(VLViewComponentXML configuration) {
    if ("button".equals(type)) {
      Button buttonAction = new JFXButton();
      buttonAction.getStyleClass().remove(0);
      buttonAction.getStyleClass().removeAll("jfx-button", "button");

      buttonAction.managedProperty().bind(buttonAction.visibleProperty());
      NodeHelper.setTitle(buttonAction, configuration, controller);
      IconUtils.setIcon(buttonAction, configuration);

      NodeHelper.setStyleClass(buttonAction, configuration, "styleClass", true);
      buttonAction.getStyleClass().add("ep-button");

      String displayMode = configuration.getPropertyValue(XMLConstants.HYPERLINK_DISPLAY_MODE, "LEFT");
      buttonAction.setContentDisplay(ContentDisplay.valueOf(displayMode));

      boolean readOnly = configuration.getBooleanProperty("readOnly", false);
      buttonAction.setDisable(readOnly);
      buttonAction.addEventFilter(ActionEvent.ACTION, e -> onAction(buttonAction));

      return buttonAction;
    } else {
      Hyperlink hyperlinkAction = new Hyperlink();
      hyperlinkAction.managedProperty().bind(hyperlinkAction.visibleProperty());
      NodeHelper.setTitle(hyperlinkAction, configuration, controller);
      NodeHelper.setStyleClass(hyperlinkAction, configuration, "styleClass", true);
      IconUtils.setIcon(hyperlinkAction, configuration);
      boolean readOnly = configuration.getBooleanProperty("readOnly", false);
      hyperlinkAction.setDisable(readOnly);
      hyperlinkAction.addEventFilter(ActionEvent.ACTION, e -> onAction(hyperlinkAction));
      return hyperlinkAction;
    }
  }


  public void onAction(ButtonBase buttonBase) {
    Node previousGraphic = buttonBase.getGraphic();
    javafx.concurrent.Task<Void> t = new javafx.concurrent.Task<Void>() {

      @Override
      protected Void call() throws Exception {
        doAction();
        resolveVisibility();
        if (relativeTo != null) {
          relativeTo.resolveVisibility();
        }
        return null;
      }


      @Override
      protected void succeeded() {
        super.succeeded();
        buttonBase.setDisable(false);
        buttonBase.setGraphic(previousGraphic);
      }


      @Override
      protected void running() {
        super.running();
        buttonBase.pseudoClassStateChanged(PseudoClass.getPseudoClass("processing"), true);
        buttonBase.setDisable(true);
        buttonBase.setGraphic(NodeHelper.getProcessingIndicator());
      }


      @Override
      protected void failed() {
        super.failed();
        buttonBase.setDisable(false);
        buttonBase.pseudoClassStateChanged(PseudoClass.getPseudoClass("processing"), false);
      }


      @Override
      protected void cancelled() {
        super.cancelled();
        buttonBase.setDisable(false);
        buttonBase.pseudoClassStateChanged(PseudoClass.getPseudoClass("processing"), false);
        // buttonBase.setGraphic(previousGraphic);
      }


      @Override
      protected void setException(Throwable t) {
        super.setException(t);
        t.printStackTrace();
        buttonBase.setDisable(false);
        buttonBase.pseudoClassStateChanged(PseudoClass.getPseudoClass("processing"), false);
        // buttonBase.setGraphic(previousGraphic);
      }
    };

    Thread thread = new Thread(t);
    thread.setDaemon(true);
    thread.start();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return node;
  }


  /**
   * Setter of buttonAction
   *
   * @param buttonAction the buttonAction to set
   */
  public void setButtonAction(ButtonBase buttonAction) {}


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
   * Getter of type
   *
   * @return the type
   */
  public String getType() {
    return type;
  }


  /**
   * Setter of type
   *
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }


  public abstract void doAction();


  /**
   * Getter of forModel
   *
   * @return the forModel
   */
  public OperationData getForModel() {
    return forModel;
  }


  /**
   * Setter of forModel
   *
   * @param forModel the forModel to set
   */
  public void setForModel(OperationData forModel) {
    this.forModel = forModel;
  }


  /**
   * Getter of relativeTo
   *
   * @return the relativeTo
   */
  public ViewActionPresenter getRelativeTo() {
    return relativeTo;
  }


  /**
   * Setter of relativeTo
   *
   * @param relativeTo the relativeTo to set
   */
  public void setRelativeTo(ViewActionPresenter relativeTo) {
    this.relativeTo = relativeTo;
  }
}
