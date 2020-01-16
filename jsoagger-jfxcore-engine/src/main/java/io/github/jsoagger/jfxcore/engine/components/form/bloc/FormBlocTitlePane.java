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

package io.github.jsoagger.jfxcore.engine.components.form.bloc;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.ISelectableComponent;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.api.form.IFormBlocFooter;
import io.github.jsoagger.jfxcore.api.form.IFormBlocTitle;
import io.github.jsoagger.jfxcore.api.form.IFormListBlocContent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.input.InputMode;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormBlocTitlePane extends StackPane implements ISelectableComponent {

  private static final String BLOC_CONTENT_IMPL = "blocContentImpl";
  private static final String DISPLAY_BLOC_TITLE = "displayHeader";
  private static final String FORM_TITLE_PANE = "form-bloc-title-pane";
  private static final String BLOC_TITLE_IMPL = "blocTitleImpl";

  /**
   * --layout-- is the main layout, in css, border is attached to it when no actions. When with
   * actions, header has no border and background, the border is attached to
   * --withHeadeActionslayout--
   *
   */
  private VBox layout = new VBox();
  private VBox withHeadeActionslayout = new VBox();

  private IFormBlocTitle blocTitle;
  private IFormBlocContent blocContent;
  private IFormBlocFooter blocFooter;
  private String componentId;

  private InputMode mode;
  private VLViewComponentXML blocConfig;
  private AbstractViewController controller;

  private PseudoClass first = PseudoClass.getPseudoClass("first");
  private PseudoClass last = PseudoClass.getPseudoClass("last");
  private PseudoClass expanded = PseudoClass.getPseudoClass("expanded");
  private PseudoClass withHeaderActions = PseudoClass.getPseudoClass("withHeaderActions");


  /**
   * Constructor
   */
  public FormBlocTitlePane(VLViewComponentXML blocConfig, AbstractViewController controller) {
    this.blocConfig = blocConfig;
    this.controller = controller;

    if(!AbstractApplicationRunner.isDesktop()) {
      //setId("ep-form-bloc-title-pane");
      if(ViewStructure.instance().getScene() != null) {
        minWidthProperty().bind(prefWidthProperty());
        maxWidthProperty().bind(prefWidthProperty());
        prefWidthProperty().bind(ViewStructure.instance().getScene().widthProperty().multiply(0.98));
      }
      else {
        ViewStructure.instance().sceneProperty().addListener(new ChangeListener<Scene>() {

          @Override
          public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
            minWidthProperty().bind(prefWidthProperty());
            maxWidthProperty().bind(prefWidthProperty());
            prefWidthProperty().bind(newValue.widthProperty().multiply(0.98));
          }});
      }
    }


    final String modeString = blocConfig.getPropertyValue(XMLConstants.MODE);
    mode = InputMode.from(modeString);

    final String blockClass = blocConfig.getPropertyValue(XMLConstants.STYLE_CLASS);
    if (StringUtils.isNotBlank(blockClass)) {
      layout.getStyleClass().addAll(blockClass.split(","));
    } else {
      layout.getStyleClass().add(FORM_TITLE_PANE);
    }

    layout.pseudoClassStateChanged(first, false);
    layout.pseudoClassStateChanged(last, false);

    // title may be hidden by the configuration
    boolean displayHeader = blocConfig.getBooleanProperty(DISPLAY_BLOC_TITLE, true);
    if (displayHeader) {
      String blocTitleImpl = blocConfig.getPropertyValue(BLOC_TITLE_IMPL);
      if (StringUtils.isEmpty(blocTitleImpl)) {
        blocTitleImpl = "FormBlocTitle";
      }

      blocTitle = (IFormBlocTitle) Services.getBean(blocTitleImpl);
      blocTitle.build(blocConfig, controller);
      blocTitle.expandedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
        layout.pseudoClassStateChanged(expanded, newValue);
      });

      layout.getChildren().add(0, blocTitle.getDisplay());
    }

    // if some actions after the header
    VLViewComponentXML headerActions = blocConfig.getComponentById("HeaderActions").orElse(null);
    if (headerActions != null) {
      IBuildable heBuildable = new FormBlocHeaderActions();
      heBuildable.buildFrom(controller, blocConfig);
      withHeadeActionslayout.getChildren().add(heBuildable.getDisplay());
      layout.pseudoClassStateChanged(withHeaderActions, true);
    }

    String blocContentImpl = blocConfig.getPropertyValue(BLOC_CONTENT_IMPL);
    if (StringUtils.isEmpty(blocContentImpl)) {
      blocContentImpl = "FormBlocContent";
    }

    // content
    blocContent = (IFormBlocContent) Services.getBean(blocContentImpl);
    blocContent.build(blocConfig, controller);
    withHeadeActionslayout.getChildren().add(blocContent.getDisplay());
    if (blocContent instanceof IFormListBlocContent) {
      ((IFormListBlocContent) blocContent).build(blocConfig, controller, new HBox());
    }

    // footer
    if (blocConfig.getComponentById("Footer").isPresent()) {
      String blocFooterImpl = blocConfig.getPropertyValue("footerImpl");
      if (StringUtils.isEmpty(blocFooterImpl)) {
        blocFooterImpl = "FormBlocFooter";
      }
      blocFooter = (IFormBlocFooter) Services.getBean(blocFooterImpl);
      blocFooter.buildFrom(controller, blocConfig);
      if (blocFooter.getDisplay() != null) {
        withHeadeActionslayout.getChildren().add(blocFooter.getDisplay());
      } else {
        blocFooter = null;
      }
    }

    if (displayHeader) {
      blocTitle.expandedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
        collapseExpand(newValue);
      });
    }

    // if title is not displayed, the content is displayed
    boolean expanded = blocConfig.getBooleanProperty("expanded", true);
    if (blocTitle != null) {
      blocTitle.expandedProperty().set(expanded);
    }

    getChildren().add(layout);
    layout.getChildren().add(withHeadeActionslayout);

    NodeHelper.styleClassAddAll(this, blocConfig, "wrapperStyleClass", "form-bloc-title-pane-wrapper");
    NodeHelper.styleClassAddAll(withHeadeActionslayout, blocConfig, "internalLayoutStyleClass", "form-bloc-title-pane-internal-layout");
  }


  /**
   * @param newValue
   */
  private void collapseExpand(Boolean newValue) {
    blocContent.visibleProperty().setValue(newValue);
    if (blocFooter != null) {
      blocFooter.getDisplay().setVisible(newValue);
    }

    if (blocTitle != null && blocTitle.getDisplay() != null) {
      blocTitle.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("collapsed"), newValue);
    }
  }


  /**
   * @{inheritedDoc}
   */
  public void setFirst() {
    pseudoClassStateChanged(first, true);
    pseudoClassStateChanged(last, false);
    if (blocTitle != null) {
      blocTitle.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("first"), true);
    }
  }


  /**
   * @{inheritedDoc}
   */
  public void setLast() {
    pseudoClassStateChanged(first, false);
    pseudoClassStateChanged(last, true);
  }


  /**
   * @return
   */
  public List<IFormFieldsetRow> getRows() {
    if (blocContent instanceof FormBlocContent) {
      return blocContent.getRows();
      //} else if (blocContent instanceof DynamicalAttributesFormBlocContent) {
      //return ((DynamicalAttributesFormBlocContent) blocContent).getRows();
    }
    return new ArrayList<>();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getSelectionLabel() {
    return blocTitle != null && blocTitle.getLabel() != null && blocTitle.getLabel().getText() != null ? blocTitle.getLabel().getText() : "NO TITLE!";
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node content() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getComponentId() {
    if (componentId == null) {
      componentId = UUID.randomUUID().toString();
    }
    return componentId;
  }


  /**
   * Getter of blocContent
   *
   * @return the blocContent
   */
  public IFormBlocContent getBlocContent() {
    return blocContent;
  }
}
