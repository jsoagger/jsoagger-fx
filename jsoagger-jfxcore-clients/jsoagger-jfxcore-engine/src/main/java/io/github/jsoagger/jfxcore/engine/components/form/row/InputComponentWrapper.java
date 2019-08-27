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

package io.github.jsoagger.jfxcore.engine.components.form.row;


import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IDisplayable;
import io.github.jsoagger.jfxcore.api.IEditInputComponent;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.api.InlineActionHandler;
import io.github.jsoagger.jfxcore.api.RowLayout;
import io.github.jsoagger.jfxcore.api.ViewInputComponent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.converter.StringConverter;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.input.InputMode;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A input wrapper. Generates label for details view, input for edit view for a component.
 * <p>
 * Minimizable only in view mode.
 *
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputComponentWrapper extends StackPane implements IInputComponentWrapper, IDisplayable, IMinimizable {

  protected boolean mandatory = false;
  protected IEditInputComponent editInputComponent = null;
  protected ViewInputComponent viewInputComponent = null;

  protected SimpleObjectProperty<InputMode> mode = new SimpleObjectProperty();

  /**
   * Internal value is value sent to remote server. IEditInputComponent and ViewInputComponent should
   * use transformer for displaying values and vis versa.
   */
  protected String displayFormat;
  protected String saveFormat;
  protected StringConverter converter;
  protected String multivaluedSeparator = "#";

  protected SimpleStringProperty currentInternalValue = new SimpleStringProperty();
  protected SimpleStringProperty initialInternalValue = new SimpleStringProperty();
  protected SimpleStringProperty defaultInternalValue = new SimpleStringProperty();

  protected VLViewComponentXML configuration = null;
  protected AbstractViewController controller = null;

  protected SimpleStringProperty attributeName = new SimpleStringProperty();
  protected SimpleStringProperty attributePath = new SimpleStringProperty();
  protected SimpleBooleanProperty minimized = new SimpleBooleanProperty(false);

  protected FormFieldsetRow row;

  protected String title = "";
  protected Label viewLabel = new Label();
  protected Label editLabel = new Label();

  protected IEnumeratedValuesLoader enumeratedValuesLoader;
  protected List<IEnumeratedValueModel> enumeratedValueModels;
  protected InlineActionHandler inlineActionHandler;

  protected SimpleBooleanProperty lastRow = new SimpleBooleanProperty(false);
  protected SimpleBooleanProperty visible = new SimpleBooleanProperty(true);
  // TODO handle default value


  /**
   * Constructor
   */
  public InputComponentWrapper() {
    getStyleClass().add("form-fieldset-row-layout-wrapper");

    // by default converter is an IdenpotentConverter
    // means no transformation between internal value and current value
    converter = (StringConverter) Services.getBean("IdenpotentConverter");
    managedProperty().bind(visibleProperty());
  }


  /**
   * @param attributeConfiguration
   * @param controller
   * @param mode
   */
  public void buildFrom(VLViewComponentXML attributeConfiguration, AbstractViewController controller, InputMode mode, boolean lastrow) {

    configuration = attributeConfiguration;
    this.controller = controller;
    mandatory = configuration.isAttributeMandatory();
    lastRow.set(lastrow);

    // attributePath can be different from attributeName
    // as commiting data is not same as fetching data
    attributePath.set(configuration.getPropertyValue("attributePath"));
    attributeName.set(configuration.getPropertyValue("attributeName"));
    if (attributePath.get().isEmpty()) {
      attributePath.set(attributeName.get());
    }

    // inside the form, there may be input and other component
    // if component type is declared, the component is not considered as
    // input
    final String inputType = configuration.getPropertyValue("type");
    if (StringUtils.isEmpty(inputType)) {
      buildLabel();
      loadDefaultValue();
      processFormat();
      loadData();

      buildViewComponent();
      buildEditComponent();
      //final CompletableFuture vm = CompletableFuture.runAsync(() -> buildViewComponent());
      //final CompletableFuture vm2 = CompletableFuture.runAsync(() -> buildEditComponent());
      //CompletableFuture.allOf(vm, vm2).join();

      this.mode.set(mode);

      editLayout.getDisplay().visibleProperty().bind(Bindings.not(viewLayout.getDisplay().visibleProperty()));
      getChildren().addAll(viewLayout.getDisplay(), editLayout.getDisplay());
      NodeHelper.setHgrow(viewLayout.getDisplay(), editLayout.getDisplay(), this);
      VBox.setVgrow(this, Priority.NEVER);
    } else {
      final IBuildable buildable = ComponentUtils.build(inputType, controller, attributeConfiguration);
      getChildren().addAll(buildable.getDisplay());
    }
  }


  public String getTitle() {
    return title;
  }


  private void processFormat() {
    final VLViewComponentXML format = getConfiguration().getComponentById("Format").orElse(null);
    if (format != null) {
      displayFormat = format.getPropertyValue("displayFormat");
      saveFormat = format.getPropertyValue("saveFormat");
      if (StringUtils.isEmpty(displayFormat)) {
        displayFormat = saveFormat;
      }

      // by default converter is an IdenpotentTransformer
      // if the configuration has defined converter, load it
      final String converterName = format.getPropertyValue("converter");
      if (StringUtils.isNotBlank(converterName)) {
        converter = (StringConverter) Services.getBean(converterName);
        converter.setDisplayFormat(displayFormat);
        converter.setSaveFormat(saveFormat);
      }

      multivaluedSeparator = format.getPropertyValue("multivaluedSeparator", "#");
    }

    if (converter != null) {
      converter.setOwner(this);
    }
  }


  private void loadDefaultValue() {
    final String defValue = configuration.getPropertyValue("defaultValue");
    if (StringUtils.isNotBlank(defValue)) {
      defaultInternalValueProperty().set(defValue);
    }
  }


  /**
   * Load the initialy displayed data. If value readen from model is null, default value is loaded if
   * there is one.
   */
  private void loadData() {
    final String enumeratedValName = configuration.getPropertyValue("enumeratedValuesLoader");
    if (StringUtils.isNotBlank(enumeratedValName)) {
      enumeratedValuesLoader = (IEnumeratedValuesLoader) Services.getBean(enumeratedValName);
    }

    if (enumeratedValuesLoader != null) {
      enumeratedValueModels = enumeratedValuesLoader.loadValues(controller, configuration);
    }
    else {
      enumeratedValueModels = new ArrayList<>();
    }

    final Object model = controller.getModel();
    Object val;
    try {
      //val = PropertyUtils.getProperty(model, attributePath.get());
      val = ReflectionUIUtils.invokeGetterOn(model, attributePath.get());
      if (val == null) {
        val = getDefaultInternalValue();
        if (val == null) {
          val = "";
        }
      }

      if (val != null) {
        initialInternalValue.set(val.toString());
        currentInternalValue.set(val.toString());
      }
    } catch (Exception e) {
      // e.printStackTrace();
    }
  }


  @Override
  public void selectFirsEnumeratedValue() {
    if (enumeratedValueModels != null && enumeratedValueModels.size() > 0) {
      final Object val = enumeratedValueModels.get(0).getSavedValue();
      if (val != null) {
        initialInternalValue.set(val.toString());
        currentInternalValue.set(val.toString());
      }
    }
  }


  @Override
  public IEnumeratedValueModel getEnumeratedValue(String internalVal) {
    if (StringUtils.isNotEmpty(internalVal)) {
      if (getEnumeratedValueModels() != null && !getEnumeratedValueModels().isEmpty()) {
        for (final IEnumeratedValueModel mod : getEnumeratedValueModels()) {
          if (mod.getSavedValue().equals(internalVal)) {
            return mod;
          }
        }
      }
    }
    return null;
  }

  // @formatter:off
  private RowLayout editLayout = null;

  private void buildEditComponent() {
    editInputComponent = new EditInputComponent();
    editInputComponent.build(this);

    // String viewLayoutString = configuration.getPropertyValue("editLayout","HorizontalFormRowLayout");
    final String viewLayoutString = configuration.getPropertyValue("editLayout", "VerticalFormRowLayout");
    editLayout = (RowLayout) Services.getBean(viewLayoutString);

    editLayout.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("editing"), true);
    editLayout.addLabel(editLabel);
    editLayout.addValue(editInputComponent.getDisplay());

    // editLayout.getStyleClass().add("form-edit-input-component-wrapper");
    if (lastRow.get()) {
      editLayout.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
    }

    // editLayout.prefWidthProperty().bind(editLayout.maxWidthProperty());
    // editLayout.maxWidthProperty().bind(widthProperty());
    // editLayout.managedProperty().bind(editLayout.visibleProperty());
  }

  @Override
  public Node getEditLayout() {
    return editLayout.getDisplay();
  }

  public IEditInputComponent editInputComponent() {
    return editInputComponent;
  }
  // @formatter:off



  // @formatter:off
  private RowLayout viewLayout = null;

  private void buildViewComponent() {
    String viewLayoutString = null;
    if(AbstractApplicationRunner.isDesktop()) {
      viewLayoutString = configuration.getPropertyValue("viewLayout", "HorizontalFormRowLayout");
    }
    else {

      viewLayoutString = configuration.getPropertyValue("viewLayout", "VerticalFormRowLayout");
    }


    viewLayout = (RowLayout) Services.getBean(viewLayoutString);

    final String viewUtility = configuration.getPropertyValue(io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants.VIEW_UTILITY, "BasicAttributeInputView");
    viewInputComponent = (ViewInputComponent) Services.getBean(viewUtility);
    viewInputComponent.build(this);

    viewLayout.addLabel(viewLabel);
    viewLayout.addValue(viewInputComponent.getDisplay());
    // NodeHelper.setHgrow(viewLabel, viewInputComponent.getDisplay());

    if (lastRow.get()) {
      viewLayout.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
    }


    mode.addListener((ChangeListener<InputMode>) (observable, oldValue, newValue) -> {
      viewLayout.getDisplay().setVisible(newValue == InputMode.VIEW);
    });
  }

  public Node getViewLayout() {
    return viewLayout.getDisplay();
  }

  // @formatter:off
  @Override
  public void switchToEditView() {
    mode.set(InputMode.EDIT);
    final Node parent = viewLayout.getDisplay().getParent();
    if (parent != null) {
      parent.pseudoClassStateChanged(PseudoClass.getPseudoClass("editing"), true);
    }
  }

  @Override
  public void switchToInfoView() {
    getChildren().clear();
    getChildren().add(viewLayout.getDisplay());
    getChildren().add(editLayout.getDisplay());
    mode.set(InputMode.VIEW);
    final Node parent = viewLayout.getDisplay().getParent();
    if (parent != null) {
      parent.pseudoClassStateChanged(PseudoClass.getPseudoClass("editing"), false);
    }
  }


  @Override
  public ViewInputComponent viewInputComponent() {
    return viewInputComponent;
  }
  // @formatter:on


  /**
   * Build the label of the row
   */
  protected void buildLabel() {
    NodeHelper.setLabel(viewLabel, configuration, controller);
    NodeHelper.styleClassSetAll(viewLabel, configuration, XMLConstants.LABEL_STYLE_CLASS, "form-info-label");

    NodeHelper.setLabel(editLabel, configuration, controller);
    NodeHelper.styleClassSetAll(editLabel, configuration, XMLConstants.LABEL_STYLE_CLASS, "form-info-label-edit");

    title = NodeHelper.getLabel(configuration, controller);
    if (mandatory) {
      editLabel.setText(editLabel.getText().concat(" *"));
    }
  }


  /**
   * Can be used when forward editing the attribute for example
   *
   * @return
   */
  public String getLabel() {
    return editLabel.getText();
  }


  @Override
  public VLViewComponentXML configuration() {
    return configuration;
  }


  /**
   * Synchronize info value with edited value
   */
  public void localCommitModification() {
    editInputComponent.commitModification();
  }


  /**
   * Getter of isEditing
   *
   * @return the isEditing
   */
  @Override
  public boolean isEditing() {
    return mode.get() == InputMode.EDIT;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * Setter of row
   *
   * @param row the row to set
   */
  public void setRow(FormFieldsetRow row) {
    this.row = row;
  }


  /**
   * Getter of editInputComponent
   *
   * @return the editInputComponent
   */
  @Override
  public IEditInputComponent getEditInputComponent() {
    return editInputComponent;
  }


  // @formatter:off
  /**
   * Commit/cancel modifications. Commit: Set initial value to current value Cancel: Set current value
   * to initial value
   */
  @Override
  public void cancelModification() {
    currentInternalValue.set(initialInternalValue.get());
    ReflectionUIUtils.invokeSetterOn(controller.getModel(), getAttributePath(), getInitialInternalValue());
  }

  @Override
  public void commitModification() {
    commitModification(true);
  }

  public void commitModification(boolean introspect) {
    final String valToCommit = StringUtils.isEmpty(currentInternalValue.get()) ? defaultInternalValueProperty().get() : currentInternalValue.get();
    initialInternalValue.set(valToCommit);
    if (introspect) {
      ReflectionUIUtils.invokeSetterOn(controller.getModel(), getAttributePath(), getInitialInternalValue());
    }
  }
  // @formatter:on


  /**
   * @return
   */
  @Override
  public final SimpleStringProperty currentInternalValueProperty() {
    return currentInternalValue;
  }


  /**
   * @return
   */
  @Override
  public final String getCurrentInternalValue() {
    if (StringUtils.isEmpty(currentInternalValueProperty().get())) {
      return defaultInternalValueProperty().get();
    }

    return this.currentInternalValueProperty().get();
  }


  public final void setCurrentInternalValue(final String currentInternalValue) {
    this.currentInternalValueProperty().set(currentInternalValue);
  }


  /**
   * @return
   */
  @Override
  public final SimpleStringProperty initialInternalValueProperty() {
    return initialInternalValue;
  }


  /**
   * @return
   */
  @Override
  public final String getInitialInternalValue() {
    return this.initialInternalValueProperty().get();
  }


  public final void setInitialInternalValue(final String initialInternalValue) {
    this.initialInternalValueProperty().set(initialInternalValue);
  }


  /**
   * @return
   */
  public final SimpleBooleanProperty lastRowProperty() {
    return lastRow;
  }


  /**
   * @return
   */
  @Override
  public final boolean isLastRow() {
    return this.lastRowProperty().get();
  }


  public final void setLastRow(final boolean lastRow) {
    this.lastRowProperty().set(lastRow);
  }


  public final SimpleObjectProperty<InputMode> modeProperty() {
    return mode;
  }


  /**
   * @return
   */
  public final io.github.jsoagger.jfxcore.engine.components.input.InputMode getMode() {
    return this.modeProperty().get();
  }


  /**
   * @return
   */
  public final void setMode(final io.github.jsoagger.jfxcore.engine.components.input.InputMode mode) {
    this.modeProperty().set(mode);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {

  }


  public final SimpleStringProperty attributeNameProperty() {
    return attributeName;
  }


  @Override
  public final java.lang.String getAttributeName() {
    return this.attributeNameProperty().get();
  }


  public final void setAttributeName(final java.lang.String attributeName) {
    this.attributeNameProperty().set(attributeName);
  }


  public final SimpleStringProperty attributePathProperty() {
    return attributePath;
  }


  @Override
  public final java.lang.String getAttributePath() {
    return this.attributePathProperty().get();
  }


  public final void setAttributePath(final java.lang.String attributePath) {
    this.attributePathProperty().set(attributePath);
  }


  public final SimpleBooleanProperty minimizedProperty() {
    return minimized;
  }


  @Override
  public final boolean isMinimized() {
    return this.minimizedProperty().get();
  }


  public final void setMinimized(final boolean minimized) {
    this.minimizedProperty().set(minimized);
  }


  /**
   * Getter of configuration
   *
   * @return the configuration
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  @Override
  public IJSoaggerController getController() {
    return controller;
  }


  /**
   * Getter of enumeratedValueModels
   *
   * @return the enumeratedValueModels
   */
  @Override
  public List<IEnumeratedValueModel> getEnumeratedValueModels() {
    return enumeratedValueModels;
  }


  /**
   * @return
   */
  public final SimpleStringProperty defaultInternalValueProperty() {
    return defaultInternalValue;
  }


  /**
   * @return
   */
  public final Object getDefaultInternalValue() {
    return this.defaultInternalValueProperty().get();
  }


  /**
   * @param defaultInternalValue
   */
  public final void setDefaultInternalValue(final String defaultInternalValue) {
    this.defaultInternalValueProperty().set(defaultInternalValue);
  }


  /**
   * Set current value through custom converter
   *
   * @param o
   */
  @Override
  public void setCurrentValue(Object o) {
    final String converted = getConverter().toString(String.valueOf(o));
    currentInternalValueProperty().set(converted);
  }


  /**
   * Setter of converter
   *
   * @param converter the converter to set
   */
  @SuppressWarnings("rawtypes")
  public void setConverter(StringConverter converter) {
    this.converter = converter;
  }


  /**
   * Getter of converter
   *
   * @return the converter
   */
  @Override
  public StringConverter getConverter() {
    return converter;
  }


  /**
   * Getter of displayFormat
   *
   * @return the displayFormat
   */
  @Override
  public String getDisplayFormat() {
    return displayFormat;
  }


  /**
   * Getter of saveFormat
   *
   * @return the saveFormat
   */
  @Override
  public String getSaveFormat() {
    return saveFormat;
  }


  /**
   * @return the multivaluedSeparator
   */
  @Override
  public String getMultivaluedSeparator() {
    return multivaluedSeparator;
  }


  /**
   * Getter of multivaluedSeparator
   *
   * @return the multivaluedSeparator
   */
  @Override
  public String getEscapedMultivaluedSeparator() {
    return "\\" + multivaluedSeparator;
  }


  /**
   *
   */
  @Override
  public void validate() {
    if(getDisplay().isVisible()){
      editInputComponent.validate();
    }
  }


  /**
   *
   */
  @Override
  public boolean isNotValid() {
    if(getDisplay().isVisible()){
      return editInputComponent.isNotValid();
    }
    else {
      return false;
    }
  }


  /**
   *
   */
  @Override
  public void displayError() {
    if(getDisplay().isVisible()) {
      editInputComponent().displayError();
    }
  }


  /**
   * Getter of row
   *
   * @return the row
   */
  @Override
  public IFormFieldsetRow getRow() {
    return row;
  }


  @Override
  public IEnumeratedValuesLoader getEnumeratedValuesLoader() {
    return enumeratedValuesLoader;
  }
}
