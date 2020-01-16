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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetBlocProcessor;
import io.github.jsoagger.jfxcore.api.IFieldsetContent;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ISelectableComponent;
import io.github.jsoagger.jfxcore.api.ViewInputComponent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormBlocTitlePane;
import io.github.jsoagger.jfxcore.engine.components.input.AbstractInputComponent;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.FormBlocProcessor;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * {@link FormFieldsetContent} is the top level wrapper of a {@link IFieldset} content.
 * <p>
 * It can also collect editable input, very useful when used inside wizard, so its help to validate
 * a form.
 * <p>
 * It {@link IFieldsetContent} uses by default {@link FormBlocProcessor} as
 * {@link IFieldsetBlocProcessor} to build the content. So, each content is
 * {@link FormBlocTitlePane} with a customisable content.
 * <p>
 * The implementation of the {@link IFieldsetBlocProcessor} is declared in configuration file.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormFieldsetContent extends VBox implements IFieldsetContent {

  //private static final Logger //logR = LoggerFactory.getLogger(FormFieldsetContent.class);

  private static final String FORM_FIELDSER_CONTENT_CONTAINER = "form-fieldset-content";
  private static final String CONTENT_STYLE_CLASS = "contentStyleClass";
  private static final String REGEX = ",";

  private VLViewComponentXML configuration = null;
  private AbstractViewController controller = null;
  private IFieldset owner;

  /**
   * List of ISelectableComponent if layout in view supporting that
   */
  private List<ISelectableComponent> selectableComponents = new ArrayList<>();
  private String headerLabel = null;

  /**
   * Reference to rows are needed if we want to validate the content of fieldset. Should be empty for
   * no validable content.
   */
  private final List<IInputComponentWrapper> rows = new ArrayList<>();
  private final List<IFormFieldsetRow> formRows = new ArrayList<>();

  private boolean isValid = false;


  /**
   * Constructor
   */
  public FormFieldsetContent() {
    getStyleClass().add(FORM_FIELDSER_CONTENT_CONTAINER);
  }


  /**
   * Constructor
   */
  @Override
  public void build(VLViewComponentXML configuration, IJSoaggerController controller) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    // may be useful sometimes
    headerLabel = configuration.getPropertyValue(XMLConstants.TITLE);

    // styleClass
    NodeHelper.setStyleClass(this, configuration, "contentStyleClass", false);

    if (configuration.hasSubComponent()) {
      boolean first = true;

      Iterator<VLViewComponentXML> iterator = configuration.getSubcomponents().iterator();
      while (iterator.hasNext()) {

        VLViewComponentXML blocConfig = iterator.next();
        String processorName = blocConfig.getProcessor();

        if (StringUtils.isBlank(processorName)) {
          processorName = "FormBlocProcessor";
        }

        final IFieldsetBlocProcessor processor = (IFieldsetBlocProcessor) Services.getBean(processorName);
        if (processor == null) {
          throw new IllegalArgumentException("Processor nor found " + processorName);
        }

        // Process the content of the fieldset
        final Node bloc = processor.process(controller, blocConfig, rows, formRows, first);
        getChildren().addAll(bloc);
        if (ISelectableComponent.class.isAssignableFrom(bloc.getClass())) {
          selectableComponents.add((ISelectableComponent) bloc);
        }

        final String styleClass = configuration.getPropertyValue(CONTENT_STYLE_CLASS);
        if (StringUtils.isNotBlank(styleClass)) {
          bloc.getStyleClass().clear();
          bloc.getStyleClass().addAll(styleClass.split(REGEX));
        }

        first = false;

        if (!iterator.hasNext()) {
          bloc.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
        }
      }
    }

    for(IFormFieldsetRow row: formRows) {
      row.setFieldset(owner);
    }

    processDependantNodes();
  }

  /**
   * validate entries
   *
   * @return
   */
  @Override
  public void validate() {
    // commit the form before validating
    for(IInputComponentWrapper editInput: rows) {
      editInput.commitModification();
      editInput.validate();
    }

    //logR.debug("End Commit values from form to backing bean");
    //logR.debug("Backing bean to string : ");

    if (controller.getModel() != null) {
      //logR.debug(controller.getModel().toString());
    }

    // validate each row
    for(IInputComponentWrapper row: rows) {
      //row.validate();
    }

    isValid = true;
    for (IInputComponentWrapper row : rows) {
      if (row.isNotValid()) {
        isValid = false;
        break;
      }
    }

    //logR.debug("Form is valid : " + isValid);
  }


  /**
   * Displays form error
   */
  @Override
  public void displayErrors() {
    Platform.runLater(() -> {
      for(IInputComponentWrapper e: rows) {
        e.displayError();
      }
    });
  }


  /**
   * Handles visible if attribute
   */
  protected void processDependantNodes() {
    final List<ViewInputComponent> entries = new ArrayList<>();
    for(IFormFieldsetRow e: formRows) {
      entries.addAll(e.getViewEntries());
    }

    for (final ViewInputComponent row : entries) {
      final String visibleIf = row.getConfiguration().getVisibleIf();
      if (StringUtils.isNotEmpty(visibleIf)) {

        // entry is not visible and no managed
        row.getDisplay().setVisible(false);

        // means that visibility of this component depends on the
        // value of a component
        // VisibleIF format: componentId,value
        if (visibleIf.contains(REGEX)) {
          final String attrId = visibleIf.split(REGEX)[0];
          final String attrValue = visibleIf.split(REGEX)[1];
          final AbstractInputComponent component = (AbstractInputComponent) controller.getComponent(attrId);
        }
      }
    }
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
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
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
  public void setController(IJSoaggerController controller) {
    this.controller = (AbstractViewController) controller;
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
  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }


  /**
   * Getter of rows
   *
   * @return the rows
   */
  public List<IInputComponentWrapper> getRows() {
    return rows;
  }


  /**
   * Getter of formRows
   *
   * @return the formRows
   */
  public List<IFormFieldsetRow> getFormRows() {
    return formRows;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * Getter of selectableComponents
   *
   * @return the selectableComponents
   */
  @Override
  public List<ISelectableComponent> getSelectableComponents() {
    return selectableComponents;
  }


  /**
   * Setter of selectableComponents
   *
   * @param selectableComponents the selectableComponents to set
   */
  public void setSelectableComponents(List<ISelectableComponent> selectableComponents) {
    this.selectableComponents = selectableComponents;
  }


  /**
   * Getter of headerLabel
   *
   * @return the headerLabel
   */
  @Override
  public String getHeaderLabel() {
    return headerLabel;
  }


  /**
   * Setter of headerLabel
   *
   * @param headerLabel the headerLabel to set
   */
  public void setHeaderLabel(String headerLabel) {
    this.headerLabel = headerLabel;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setOwner(IFieldset owner) {
    this.owner = owner;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IFieldset getOwner() {
    return owner;
  }
}
