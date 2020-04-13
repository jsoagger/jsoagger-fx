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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.tabed;



import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * This layout can be used inside tab pane which need its center element to be always on the center
 * of the content pane.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 1 févr. 2018
 */
public class CenteredSingleElementLayout extends HBox {

  private StackPane leftSpacer = new StackPane();
  private StackPane rightSpacer = new StackPane();
  private VBox centerContainer = new VBox();


  /**
   * Default Constructor
   */
  public CenteredSingleElementLayout() {
    NodeHelper.setHVGrow(centerContainer);
    getChildren().addAll(leftSpacer, centerContainer, rightSpacer);
  }


  public void setCenterElement(Node element) {
    centerContainer.getChildren().clear();
    centerContainer.getChildren().add(element);
  }

  public void addCenterElement(IBuildable buildable) {
    if (buildable != null) {
      centerContainer.getChildren().add(buildable.getDisplay());
    }
  }

  public void addCenterElement(Node node) {
    centerContainer.getChildren().add(node);
  }
}
