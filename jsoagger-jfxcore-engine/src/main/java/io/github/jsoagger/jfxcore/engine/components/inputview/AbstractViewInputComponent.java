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

package io.github.jsoagger.jfxcore.engine.components.inputview;


import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.ViewInputComponent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;

/**
 * Viewable attribute can display actions on the right side of it like edition.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractViewInputComponent implements ViewInputComponent {

  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;
  protected InputComponentWrapper inputComponentWrapper;
  protected Node actions = null;


  /**
   * Constructor
   */
  public AbstractViewInputComponent() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    this.configuration = inputComponentWrapper.configuration();
    this.controller = (AbstractViewController) inputComponentWrapper.getController();
    this.inputComponentWrapper = (InputComponentWrapper) inputComponentWrapper;
    NodeHelper.setStyleClass(getDisplay(), configuration, XMLConstants.STYLE_CLASS, true);
  }


  /**
   * @{inheritedDoc}
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
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * Getter of inputComponentWrapper
   *
   * @return the inputComponentWrapper
   */
  @Override
  public InputComponentWrapper getComponentWrapper() {
    return inputComponentWrapper;
  }


  /**
   * @param internalVal
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getEnumeratedValue(java.lang.String)
   */
  public IEnumeratedValueModel getEnumeratedValue(String internalVal) {
    return inputComponentWrapper.getEnumeratedValue(internalVal);
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getAttributeName()
   */
  public final String getAttributeName() {
    return inputComponentWrapper.getAttributeName();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getAttributePath()
   */
  public final String getAttributePath() {
    return inputComponentWrapper.getAttributePath();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getDefaultValue()
   */
  public final Object getDefaultValue() {
    return inputComponentWrapper.getDefaultInternalValue();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getEnumeratedValueModels()
   */
  public List<IEnumeratedValueModel> getEnumeratedValueModels() {
    return inputComponentWrapper.getEnumeratedValueModels();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getDisplayFormat()
   */
  public String getDisplayFormat() {
    return inputComponentWrapper.getDisplayFormat();
  }


  /**
   * @return
   * @see io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper#getSaveFormat()
   */
  public String getSaveFormat() {
    return inputComponentWrapper.getSaveFormat();
  }
}
