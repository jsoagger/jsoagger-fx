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

package io.github.jsoagger.jfxcore.engine.components.wizard;




import java.util.Arrays;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetGroupLayout;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepContent;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.FormFieldset;
import io.github.jsoagger.jfxcore.engine.components.input.AbstractInputComponent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardStepContent extends StackPane implements IWizardStepContent {

  //private static final Logger logR = LoggerFactory.getLogger(WizardStepContent.class);
  private static final String WIZARD_STEP_CONTENT = "wizard-step-content";

  protected Label title = new Label();
  protected ObservableList<IFieldset> fieldsets = FXCollections.observableArrayList();
  protected int index;

  protected VLViewComponentXML configuration;
  protected AbstractViewController controller;


  /**
   * Constructor
   */
  public WizardStepContent() {

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    NodeHelper.styleClassSetAll(this, configuration, "styleClass", WIZARD_STEP_CONTENT);
    // getStyleClass().add(WIZARD_STEP_CONTENT);

    String contentProcessorName = configuration.getProcessor();
    if (StringUtils.isEmpty(contentProcessorName)) {
      contentProcessorName = "FormFieldsetListProcessor";
    }

    IComponentProcessor componentProcessor = (IComponentProcessor) Services.getBean(contentProcessorName);
    if (componentProcessor == null) {
      throw new RuntimeException("Component processor not found : " + contentProcessorName);
    }

    Boolean displayGroupSelector = configuration.getBooleanProperty("displayGroupSelector", true);
    IFieldsetGroupLayout groupLayout = (IFieldsetGroupLayout) componentProcessor.process(controller, configuration);
    groupLayout.setDisplaySelectors(displayGroupSelector);

    fieldsets.addAll(groupLayout.getFieldsets());
    getChildren().add(groupLayout.getDisplay());
  }


  protected HBox buildSelector() {
    HBox selector = new HBox();
    selector.getStyleClass().add("wizard-form-fieldset-selector");

    // displayed only if have more than one bloc
    selector.managedProperty().bind(Bindings.size(fieldsets).greaterThan(1));
    return selector;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void select() {
    //Hide row/component with visible-if attributes
    hideDependentNodes();

    // add listener if we are in edit mode
    postProcess();
  }


  /**
   * Hide row/component with visible-if attributes
   */
  private void hideDependentNodes() {
    for (final IFieldset fieldset : fieldsets) {
      FormFieldset formFieldset = (FormFieldset) fieldset;
      for (final IInputComponentWrapper row : formFieldset.getRows()) {

        final String visibleIf = row.getConfiguration().getVisibleIf();
        // entry is not visible and no managed
        if (StringUtils.isEmpty(visibleIf)) {
          row.getDisplay().visibleProperty().set(true);
        }
        else {
          row.getDisplay().visibleProperty().set(false);

          // means that visibility of this component depends on the
          // value of a component
          // VisibleIF format: componentId,value1,value2 ... valuen
          // VisibleIF format: componentId,value1
          if (visibleIf.contains(",")) {
            final String attrId = visibleIf.split(",")[0];
            final List<String> attrValue = Arrays.asList(visibleIf.split(","));

            final AbstractInputComponent comp = (AbstractInputComponent) controller.getComponent(attrId);
            if(comp != null) {
              String newValKey = (String) comp.getValueToValidate();
              if (attrValue.contains(newValKey)) {
                row.getDisplay().visibleProperty().set(true);
              } else {
                row.getDisplay().visibleProperty().set(false);
              }
            }
            else {
              System.out.println("[WARNING] a valid-attribute have been detected but attribute not found " + attrId);
            }
          }
        }
      }
    }
  }


  public void postProcess() {
    for (final IFieldset fieldset : fieldsets) {
      FormFieldset formFieldset = (FormFieldset) fieldset;
      for (final IInputComponentWrapper row : formFieldset.getRows()) {

        final String visibleIf = row.getConfiguration().getVisibleIf();
        if (StringUtils.isNotEmpty(visibleIf)) {

          // means that visibility of this component depends on the
          // value of a component
          // VisibleIF format: componentId,value1,value2 ... valuen
          // VisibleIF format: componentId,value1
          if (visibleIf.contains(",")) {
            final String attrId = visibleIf.split(",")[0];
            final List<String> attrValue = Arrays.asList(visibleIf.split(","));

            final AbstractInputComponent comp = (AbstractInputComponent) controller.getComponent(attrId);
            if (comp != null) {
              comp.addValueChangeListner((arg0, arg1, arg2) -> {
                String newValKey = (String) comp.getValueToValidate();
                if (attrValue.contains(newValKey)) {
                  row.getDisplay().setVisible(true);
                } else {
                  row.getDisplay().setVisible(false);
                }
              });
            }
          }
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
   * @author Administrator
   *
   */
  public enum WizardStepActionButtonPos {
    LEFT, CENTER, RIGHT;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void validate() {
    for(IFieldset f: fieldsets) {
      f.validate();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isValid() {
    for (IFieldset fieldset : fieldsets) {
      if (fieldset.isNotValid()) {
        return false;
      }
    }
    return true;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayErrors() {
    for(IFieldset e : fieldsets) {
      e.displayErrors();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int getIndex() {
    return index;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getTitle() {
    return title.textProperty().get();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public ObservableList<IFieldset> getFieldsets() {
    return fieldsets;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setFieldsets(ObservableList<IFieldset> fieldsets) {
    this.fieldsets = fieldsets;
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
   * Setter of title
   *
   * @param title the title to set
   */
  @Override
  public void setTitle(Label title) {
    this.title = title;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setIndex(int index) {
    this.index = index;
  }
}
