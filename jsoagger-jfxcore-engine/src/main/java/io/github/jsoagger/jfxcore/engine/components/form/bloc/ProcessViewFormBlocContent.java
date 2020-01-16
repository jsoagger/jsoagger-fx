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

package io.github.jsoagger.jfxcore.engine.components.form.bloc;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana  VONJISOA
 */
public class ProcessViewFormBlocContent implements IFormBlocContent {

  private final VBox wrapper = new VBox();
  private AbstractViewController processedController = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(VLViewComponentXML blocConfig, IJSoaggerController controller) {
    final String rootView = blocConfig.getPropertyValue("viewId");


    if (StringUtils.isNotBlank(rootView)) {
      processedController = StandardViewUtils.forId((RootStructureController)controller.getRootStructure(), ((AbstractViewController) controller).getStructureContent(), rootView);
      final Pane processed = (Pane) processedController.processedView();

      Platform.runLater(() -> {
        wrapper.getChildren().add(processed);
        wrapper.managedProperty().bindBidirectional(wrapper.visibleProperty());
        NodeHelper.setHVGrow(wrapper, processed);
      });
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return wrapper;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<IFormFieldsetRow> getRows() {
    return new ArrayList<>();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Property<Boolean> visibleProperty() {
    return wrapper.visibleProperty();
  }

  /**
   * @return the processedController
   */
  public AbstractViewController getProcessedController() {
    return processedController;
  }

  /**
   * @param processedController the processedController to set
   */
  public void setProcessedController(AbstractViewController processedController) {
    this.processedController = processedController;
  }
}
