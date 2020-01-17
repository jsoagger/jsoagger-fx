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

package io.github.jsoagger.jfxcore.components.presenter;



import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.IModelAttributePresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 févr. 2018
 */
public class ModelWorkStatusPresenter extends CellPresenterImpl implements IModelAttributePresenter {

  private Label label = new Label();


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    boolean locked = RCUtils.isLocked((IOperationResult) controller.getModel());
    String lockedBy = RCUtils.getLockedBy((IOperationResult) controller.getModel());
    boolean wc = RCUtils.isWorkingCopy((IOperationResult) controller.getModel());

    if (locked) {
      label.setTooltip(new Tooltip(lockedBy));
      if (wc) {
        IconUtils.setFontIcon("mdi-lock-open:28", label);
        label.getGraphic().getStyleClass().add("accent-ikonli");
      } else {
        IconUtils.setFontIcon("mdi-lock:28", label);
        label.getGraphic().getStyleClass().add("accent-ikonli");
      }
    }

    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    Object dataValue = ReflectionUIUtils.invokeGetterOn(forModel, "attributes.workInfo.isWorkingCopy");
    if (dataValue != null && Boolean.TRUE.booleanValue() == Boolean.valueOf(dataValue.toString())) {
      IconUtils.setFontIcon("mdi-lock-open:20", label);
      label.getGraphic().getStyleClass().add("yellow-ikonli");
    }

    return label;
  }
}
