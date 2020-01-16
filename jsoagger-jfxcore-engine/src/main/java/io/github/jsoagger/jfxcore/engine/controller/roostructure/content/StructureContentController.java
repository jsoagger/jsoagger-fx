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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.content;




import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetStandardRightActions;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Structure content model is the model shared among its children. The structure content model is
 * set when building the view and it can not be changed.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class StructureContentController extends AbstractViewController {

  protected RootStructureContentController rootStructureContentController;
  protected RootStructureController rootStructureController;

  protected AbstractViewController currentContent;
  protected SimpleObjectProperty<AbstractViewController> currentEditingTableStructure = new SimpleObjectProperty<>();

  protected String scIdentifier;

  /** The content location must be handled by the child implementor. */
  protected SimpleStringProperty contentLocation = new SimpleStringProperty();

  /**
   * As structure content model can not be changed, this model can be used to echange datas with
   * children.
   */
  protected String modelFullId;
  protected SimpleObjectProperty<OperationData> formModelData = new SimpleObjectProperty<>();
  protected PushStructureContentEvent sourceEvent;


  /**
   * Constructor
   */
  public StructureContentController() {
    super();
    registerListener(CoreEvent.HeaderNavbarBackButtonClicked);
  }


  /**
   *
   */
  public SimpleStringProperty contentLocationProperty() {
    return contentLocation;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.HeaderNavbarBackButtonClicked)) {
      boolean hasCustomActions = getRootComponent() != null && getRootComponent().getBooleanProperty("hasCustomHeaderActions");
      if (!hasCustomActions) {
        HeaderNavbarSetStandardRightActions he = new HeaderNavbarSetStandardRightActions.Builder().build();
        dispatchEvent(he);
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public <T extends GenericEvent> void dispatchEvent(T event) {
    rootStructureController.dispatchEvent(event);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public RootStructureController getRootStructure() {
    return rootStructureController;
  }


  /**
   * Getter of scIdentifier
   *
   * @return the scIdentifier
   */
  public String getScIdentifier() {
    return scIdentifier;
  }


  /**
   * Setter of scIdentifier
   *
   * @param scIdentifier the scIdentifier to set
   */
  public void setScIdentifier(String scIdentifier) {
    this.scIdentifier = scIdentifier;
  }


  /**
   * @param rootStructureContentController
   */
  public void setRootStructureContent(RootStructureContentController rootStructureContentController) {
    this.rootStructureContentController = rootStructureContentController;
  }


  public boolean isSame(StructureContentController another) {
    return scIdentifier.equalsIgnoreCase(another.scIdentifier);
  }


  /**
   * Getter of rootStructureContentController
   *
   * @return the rootStructureContentController
   */
  public RootStructureContentController getRootStructureContentController() {
    return rootStructureContentController;
  }


  /**
   * Setter of rootStructureContentController
   *
   * @param rootStructureContentController the rootStructureContentController to set
   */
  public void setRootStructureContentController(RootStructureContentController rootStructureContentController) {
    this.rootStructureContentController = rootStructureContentController;
  }


  /**
   * Getter of rootStructureController
   *
   * @return the rootStructureController
   */
  public RootStructureController getRootStructureController() {
    return rootStructureController;
  }


  /**
   * Setter of rootStructureController
   *
   * @param rootStructureController the rootStructureController to set
   */
  public void setRootStructureController(RootStructureController rootStructureController) {
    this.rootStructureController = rootStructureController;
  }


  public void setRootStructure(RootStructureController root) {
    rootStructureController = root;
  }


  public void setForModelId(String modelFullId) {
    this.modelFullId = modelFullId;
  }


  public String __getForModelId() {
    if (StringUtils.isNotBlank(modelFullId)) {
      return modelFullId;
    }

    if (formModelData.get() != null) {
      return (String) formModelData.get().getAttributes().get("fullId");
    }

    return modelFullId;
  }


  /**
   * Getter of formModelData
   *
   * @return the formModelData
   */
  public OperationData getFormModelData() {
    return formModelData.get();
  }


  /**
   * Setter of formModelData
   *
   * @param formModelData the formModelData to set
   */
  public void setFormModelData(OperationData formModelData) {
    this.formModelData.set(formModelData);;
  }


  public SimpleObjectProperty<OperationData> formModelDataProperty() {
    return formModelData;
  }


  /**
   * Getter of sourceEvent
   *
   * @return the sourceEvent
   */
  public PushStructureContentEvent getSourceEvent() {
    return sourceEvent;
  }


  /**
   * Setter of sourceEvent
   *
   * @param sourceEvent the sourceEvent to set
   */
  public void setSourceEvent(PushStructureContentEvent sourceEvent) {
    this.sourceEvent = sourceEvent;
  }


  /**
   * Getter of currentContent
   *
   * @return the currentContent
   */
  public AbstractViewController getCurrentContent() {
    return currentContent;
  }


  /**
   * Setter of currentContent
   *
   * @param currentContent the currentContent to set
   */
  public void setCurrentContent(AbstractViewController currentContent) {
    this.currentContent = currentContent;
  }


  public AbstractViewController getCurrentEditingTableStructure() {
    return currentEditingTableStructure.get();
  }


  public void setCurrentEditingTableStructure(AbstractViewController currentEditingTableStructure) {
    this.currentEditingTableStructure.set(currentEditingTableStructure);
  }

  public SimpleObjectProperty<AbstractViewController> currentEditingTableStructureProperty() {
    return currentEditingTableStructure;
  }

  // protected abstract void applyContentMatrix(ResponsiveAreaSize areasSize);
}
