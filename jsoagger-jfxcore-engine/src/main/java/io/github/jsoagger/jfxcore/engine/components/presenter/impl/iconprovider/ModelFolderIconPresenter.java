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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider;



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Icon associadte to the type of the businness data
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public class ModelFolderIconPresenter extends CellPresenterImpl implements ModelIconPresenter {

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration) {
    final Label label = IconUtils.getFontIcon("mdi-folder:20");
    label.getStyleClass().add("ep-folder-icon");
    label.getGraphic().getStyleClass().add("folder-ikonli");
    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    final Label label = IconUtils.getFontIcon("mdi-folder:20");
    label.getStyleClass().add("ep-folder-icon");
    label.getGraphic().getStyleClass().add("folder-ikonli");
    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    final Label label = IconUtils.getFontIcon("mdi-folder:20");
    label.getStyleClass().add("ep-folder-icon");
    label.getGraphic().getStyleClass().add("folder-ikonli");
    return label;
  }
}
