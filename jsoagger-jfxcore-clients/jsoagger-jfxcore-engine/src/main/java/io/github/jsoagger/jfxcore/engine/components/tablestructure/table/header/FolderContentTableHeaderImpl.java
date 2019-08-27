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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class FolderContentTableHeaderImpl extends StructureNavigatorTableHeaderImpl {

  /**
   * Constructor
   */
  public FolderContentTableHeaderImpl() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void loadFXML() {
    NodeHelper.loadFXML(FolderContentTableHeaderImpl.class.getResource("FolderContentTableHeader.fxml"), this);
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    NodeHelper.styleClassAddAll(actionsContainer, configuration, "actionsContainerStyleClass");
  }


  @Override
  protected void structureModelUpdated() {
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void setRootModel() {
    controller.modelProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
      Platform.runLater(() -> {
        titleContainer.getChildren().clear();
        SingleResult sr = (SingleResult) newValue;
        OperationData opd = sr.getData();

        if (opd != null) {
          String fullPath = (String) opd.getAttributes().get("path");
          for (String t : fullPath.split("/")) {
            Text l = new Text();
            l.setText(t);
            l.getStyleClass().add("ep-structure-content-location-item-label");
            titleContainer.getChildren().addAll(l, NodeHelper.getSep());
          }

          // remove las sep
          if (titleContainer.getChildren().size() > 1) {
            titleContainer.getChildren().remove(titleContainer.getChildren().size() - 1);
          }
        }
      });
    });
  }
}
