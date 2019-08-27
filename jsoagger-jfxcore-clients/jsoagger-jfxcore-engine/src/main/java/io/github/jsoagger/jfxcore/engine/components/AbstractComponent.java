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

package io.github.jsoagger.jfxcore.engine.components;



import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IComponent;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * * Is a component inside the view. A component can be a {@link TextField}, a {@link Label}, a
 * {@link Button} or al controls or {@link Node} displayed in a view.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractComponent implements IComponent, InjectableComponent, IBuildable {

  public static final String DISPLAY_CONFIG = "DisplayConfig";

  protected String id;
  protected VLViewComponentXML configuration;
  protected AbstractViewController controller;
  protected boolean visible;

  protected IInputComponentWrapper owner;


  /**
   * Default constructor
   */
  public AbstractComponent() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;
    id = configuration.getId();
    controller.addIComponent(this);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
    owner.currentInternalValueProperty().unbind();
    owner.initialInternalValueProperty().unbind();
  }


  /**
   * @param editInputComponent
   */
  public void setOwner(IInputComponentWrapper owner) {
    this.owner = owner;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public final String getInternalId() {
    return id;
  }


  /**
   * @return the id
   */
  public String getId() {
    return id;
  }


  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * @return the wizardConfiguration
   */
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * @param wizardConfiguration the wizardConfiguration to set
   */
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
    id = configuration.getId();
  }


  /**
   * Bind the displayed value to another component
   *
   * @param label
   */
  public void addDisplayBinding(Label label) {}


  /**
   * Get the node to display, may be different from the real component.
   *
   * @return the content
   */
  @Override
  public abstract Node getDisplay();


  /**
   * Reference to the real component, the display may be wrapped in multiple boxes.
   *
   * @return
   */
  public abstract Node getComponent();


  /**
   * @param internalVal
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getEnumeratedValue(java.lang.String)
   */
  public IEnumeratedValueModel getEnumeratedValue(String internalVal) {
    return owner.getEnumeratedValue(internalVal);
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#isEditing()
   */
  public boolean isEditing() {
    return owner.isEditing();
  }


  /**
   *
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#cancelModification()
   */
  public void cancelModification() {
    owner.cancelModification();
  }


  /**
   *
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#commitModification()
   */
  public void commitModification() {
    owner.commitModification();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#currentInternalValueProperty()
   */
  public final SimpleStringProperty currentInternalValueProperty() {
    return owner.currentInternalValueProperty();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getCurrentInternalValue()
   */
  public final Object getCurrentInternalValue() {
    return owner.getCurrentInternalValue();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getInitialInternalValue()
   */
  public final Object getInitialInternalValue() {
    return owner.getInitialInternalValue();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#isLastRow()
   */
  public final boolean isLastRow() {
    return owner.isLastRow();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#isVisible()
   */
  public final boolean isVisible() {
    return owner.isVisible();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getAttributeName()
   */
  public final String getAttributeName() {
    return owner.getAttributeName();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getAttributePath()
   */
  public final String getAttributePath() {
    return owner.getAttributePath();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#isMinimized()
   */
  public final boolean isMinimized() {
    return owner.isMinimized();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getEnumeratedValueModels()
   */
  public List<IEnumeratedValueModel> getEnumeratedValueModels() {
    return owner.getEnumeratedValueModels();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getDisplayFormat()
   */
  public String getDisplayFormat() {
    return owner.getDisplayFormat();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getSaveFormat()
   */
  public String getSaveFormat() {
    return owner.getSaveFormat();
  }


  /**
   * Getter of owner
   *
   * @return the owner
   */
  public IInputComponentWrapper getOwner() {
    return owner;
  }
}
