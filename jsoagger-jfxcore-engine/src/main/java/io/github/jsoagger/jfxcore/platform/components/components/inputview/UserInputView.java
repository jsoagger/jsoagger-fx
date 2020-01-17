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

package io.github.jsoagger.jfxcore.platform.components.components.inputview;




import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.inputview.AbstractViewInputComponent;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class UserInputView extends AbstractViewInputComponent {

  private final Label label = new Label();
  private VLViewComponentXML configuration;
  private IOperation userAccountOperation;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    String value = (String) ReflectionUIUtils.invokeGetterOn(controller.getModel(), inputComponentWrapper.getAttributePath());
    userAccountOperation.doOperation(value, this::handleResult);
    label.setWrapText(true);
    label.getStyleClass().add("form-value-label");
  }


  /**
   * @param operationResult
   */
  protected void handleResult(IOperationResult operationResult) {
    final ObjectModel account = (ObjectModel) operationResult.rootData();
    label.setText(account.getName());
    label.setTooltip(new Tooltip(account.getName()));
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }
}
