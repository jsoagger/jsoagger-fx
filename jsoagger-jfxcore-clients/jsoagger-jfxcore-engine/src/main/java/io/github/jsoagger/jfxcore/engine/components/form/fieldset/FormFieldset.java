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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetContent;
import io.github.jsoagger.jfxcore.api.IFieldsetHeader;
import io.github.jsoagger.jfxcore.api.IForwardEditor;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * * this is the fieldset not each bloc inside the fieldset, one fieldset may be composed of many
 * blocs
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormFieldset extends BorderPane implements IFieldset {

  private static final String CONTENT_IMPL = "contentImpl";
  private static final String HEADER_IMPL = "headerImpl";
  private static final String WIZARD_FIELDSET_ROOT_CONTAINER = "form-fieldset";

  /*-----------------------------------------------------------------------------
  | PRIVATE ATTRIBUTES
   *=============================================================================*/
  private VLViewComponentXML configuration = null;
  private AbstractViewController controller = null;
  private boolean isValid = false;

  // this is the fieldset not each bloc inside the fieldset
  // one fieldset may be composed of many blocs
  private IFieldsetHeader fieldsetHeader;
  private IFieldsetContent fieldsetContent;

  private IForwardEditor forwardeditor;

  /*-----------------------------------------------------------------------------
  | PUBLIC METHOD
   *=============================================================================*/
  /**
   * Constructor
   */
  public FormFieldset() {

  }

  /**
   * @param wizardConfiguration
   * @param controller
   */
  @Override
  public void build(VLViewComponentXML configuration, IJSoaggerController controller) {
    this.controller = (AbstractViewController) controller;
    this.configuration = configuration;

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

    Boolean displayFieldsetTitle = configuration.getBooleanProperty("displayFieldsetTitle", false);
    if (displayFieldsetTitle) {
      String headerImp = configuration.getPropertyValue(HEADER_IMPL);
      if (StringUtils.isEmpty(headerImp)) {
        headerImp = "FormFieldsetHeader";
      }

      fieldsetHeader = (IFieldsetHeader) Services.getBean(headerImp);
      if (fieldsetHeader == null) {
        throw new RuntimeException(MessageFormat.format("Bean with name {0} not found", headerImp));
      }
      fieldsetHeader.build(configuration, controller);
      setTop(fieldsetHeader.getDisplay());
    }

    // can not be null, if null instanciate default impl
    String contentImpl = configuration.getPropertyValue(CONTENT_IMPL);
    if (StringUtils.isEmpty(contentImpl)) {
      contentImpl = "FormFieldsetContent";
    }

    fieldsetContent = (IFieldsetContent) Services.getBean(contentImpl);
    NodeHelper.styleClassSetAll(this, configuration, "formFieldsetStyleClass", WIZARD_FIELDSET_ROOT_CONTAINER);

    fieldsetContent.setOwner(this);
    fieldsetContent.build(configuration, controller);
    setCenter(fieldsetContent.getDisplay());
  }

  /**
   * Show the editor within the fieldset.
   *
   * !!IF NOTHING HAPPENS WHEN FORWARD EDING, MAKE SURE FIRST THAT LAYOUT MANAGER HAS IMPLEMENTED PUSH
   * AND POP METHODS
   *
   */
  @Override
  public void beginForwardEdition(Node editor) {
    if (forwardeditor != null) {
      forwardeditor.beginEdition(editor);
    } else {
      if (controller instanceof StandardViewController) {
        ((StandardViewController) controller).getLayoutManager().pushContent(editor);
      }
    }
  }

  /**
   * !!IF NOTHING HAPPENS WHEN FORWARD EDING, MAKE SURE FIRST THAT LAYOUT MANAGER HAS IMPLEMENTED PUSH
   * AND POP METHODS
   */
  @Override
  public void endForwardEdition() {
    if (forwardeditor != null) {
      forwardeditor.endEdition();
    } else {
      if (controller instanceof StandardViewController) {
        // if controlle has layout manager
        if(((StandardViewController) controller).getLayoutManager() != null) {
          ((StandardViewController) controller).getLayoutManager().popContent();
        }
        // controller do not have layout manager
        // so it has layout the content by itsef
        else {
          ((StandardViewController) controller).endForwardEdition();
        }
      }
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void validate() {
    fieldsetContent.validate();
    isValid = fieldsetContent.isValid();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isValid() {
    return isValid;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isNotValid() {
    return isValid == Boolean.FALSE;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayErrors() {
    if (fieldsetHeader != null) {
      fieldsetHeader.displayErrors(isValid);
    }

    fieldsetContent.displayErrors();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return controller;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public IFieldsetHeader getFieldsetHeader() {
    return fieldsetHeader;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void setFieldsetHeader(IFieldsetHeader fieldsetHeader) {
    this.fieldsetHeader = fieldsetHeader;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public IFieldsetContent getFieldsetContent() {
    return fieldsetContent;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void setFieldsetContent(IFieldsetContent fieldsetContent) {
    this.fieldsetContent = fieldsetContent;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void setController(IJSoaggerController controller) {
    this.controller = (AbstractViewController) controller;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String getTitle() {
    if (fieldsetHeader != null) {
      return fieldsetHeader.getTitle();
    }

    return "";
  }

  /**
   * @return
   */
  public List<IInputComponentWrapper> getRows() {
    if (fieldsetContent instanceof FormFieldsetContent) {
      return ((FormFieldsetContent) fieldsetContent).getRows();
    }

    return new ArrayList<>();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void setForwardEditor(IForwardEditor editor) {
    forwardeditor = editor;
  }
}
