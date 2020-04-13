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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.menu;


import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IDisplayable;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetGroupLayout;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FieldsetFlowLayout extends FlowPane implements IFieldsetGroupLayout {

  private ObservableList<IFieldset> fieldsets = FXCollections.observableArrayList();


  public FieldsetFlowLayout() {
    getStyleClass().add("fieldset-flow-layout-pane");
    setOrientation(Orientation.HORIZONTAL);
  }


  @Override
  public void setDisplaySelectors(Boolean displayGroupSelector) {}


  @Override
  public void addFieldset(IFieldset fieldset) {
    fieldsets.add(fieldset);
    getChildren().add(fieldset.getDisplay());
    NodeHelper.setHgrow(fieldset.getDisplay());
  }

  @Override
  public void addComponents(List<IBuildable> buildables) {
    for (IBuildable buildable : buildables) {
      getChildren().add(buildable.getDisplay());
    }
  }

  @Override
  public void addComponent(IDisplayable displayable) {
    getChildren().add(displayable.getDisplay());
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayAll() {}


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
  public ObservableList<IFieldset> getFieldsets() {
    return fieldsets;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootConfig(VLViewComponentXML fieldsetListConfig) {
    String styleClass = fieldsetListConfig.getPropertyValue("styleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      getStyleClass().addAll(styleClass.split(","));
    }
  }
}
