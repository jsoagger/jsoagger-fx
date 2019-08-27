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


import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.IRSHeaderHolder;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;

/**
 * A standard view can be displayed inside {@link RootStructureController} or inside
 * {@link RootStructureContentController}.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class StandardViewController extends AbstractViewController implements IViewLayoutManageable, IRSHeaderHolder {

  protected RootStructureController rootStructure;
  protected IParentResponsiveMatrix responsiveMatrix;
  protected IViewLayoutManager layoutManager;
  protected SimpleObjectProperty<Object> processedElement = new SimpleObjectProperty<>();
  protected ModelIdentityPresenter identityPresenter;

  /**
   * If a view is displayed in StructureContentController, this attribute should not be empty
   */
  protected StructureContentController structureContentController;

  /**
   * Selected element if different from the model, used in table/list and flow. Select an element is
   * different from navigate through an element. Select may trigger load details, navigate, no.
   **/
  protected SimpleObjectProperty<OperationData> selectedElement = new SimpleObjectProperty<>();
  protected SimpleObjectProperty<OperationData> navigatingElement = new SimpleObjectProperty<>();
  protected boolean needRefreshBeforePop = false;


  /**
   * Constructor
   */
  public StandardViewController() {
    super();
  }


  /**
   *
   */
  public SimpleObjectProperty<OperationData> selectedElementProperty() {
    return selectedElement;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return getRootComponent();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();
    if (processedView() == null) {
      processedView(new StackPane());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void postProcess() {
    super.postProcess();
    if (layoutManager != null) {
      layoutManager.layout(this);
      processedView(layoutManager.getDisplay());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public <T extends GenericEvent> void dispatchEvent(T event) {
    if (rootStructure != null) {
      rootStructure.dispatchEvent(event);
    }
  }


  /**
   * @param root
   */
  public void setRootStructure(RootStructureController root) {
    rootStructure = root;
    root.addChild(this);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public RootStructureController getRootStructure() {
    return rootStructure;
  }


  /**
   * Layout a child StandardViewController inside this view.
   *
   * @param loadedView
   */
  public void layoutChildView(StandardViewController loadedView) {}


  /**
   * Setter of responsiveMatrix
   *
   * @param responsiveMatrix the responsiveMatrix to set
   */
  @Override
  public void setResponsiveMatrix(IParentResponsiveMatrix responsiveMatrix) {
    this.responsiveMatrix = responsiveMatrix;
  }


  /**
   * Getter of structureContentController
   *
   * @return the structureContentController
   */
  public StructureContentController getStructureContentController() {
    return structureContentController;
  }


  /**
   * Setter of structureContentController
   *
   * @param structureContentController the structureContentController to set
   */
  public void setStructureContentController(StructureContentController structureContentController) {
    this.structureContentController = structureContentController;
  }


  /**
   * Getter of layoutManager
   *
   * @return the layoutManager
   */
  public IViewLayoutManager getLayoutManager() {
    return layoutManager;
  }


  public Object processedElement() {
    return processedElement.get();
  }


  public void processedElement(Object element) {
    processedElement.set(element);
  }


  public SimpleObjectProperty<Object> processedElementProperty() {
    return processedElement;
  }


  /**
   * Setter of layoutManager
   *
   * @param layoutManager the layoutManager to set
   */
  public void setLayoutManager(IViewLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }


  /**
   * Getter of responsiveMatrix
   *
   * @return the responsiveMatrix
   */
  @Override
  public IParentResponsiveMatrix getResponsiveMatrix() {
    return responsiveMatrix;
  }


  public boolean isNeedRefreshBeforePop() {
    return needRefreshBeforePop;
  }


  public void setNeedRefreshBeforePop(boolean needRefreshBeforePop) {
    this.needRefreshBeforePop = needRefreshBeforePop;
  }


  /**
   *
   */
  @Override
  public javafx.scene.Node getDisplayIdentity() {
    return null;
  }


  /**
   *
   */
  @Override
  public void hideIdentity() {

  }


  /**
   * @return the identityPresenter
   */
  public ModelIdentityPresenter getIdentityPresenter() {
    return identityPresenter;
  }


  /**
   * @param identityPresenter the identityPresenter to set
   */
  public void setIdentityPresenter(ModelIdentityPresenter identityPresenter) {
    this.identityPresenter = identityPresenter;
  }
}
