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
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.engine.components.list.utils.AbstractListView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class StandardListViewController extends StandardViewController {

  private SimpleObjectProperty<OperationData> selectedModel = new SimpleObjectProperty<>();
  private AbstractListView listView;


  /**
   * Default Constructor
   */
  public StandardListViewController() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return processedView();
    }
    return super.getNodeOnPosition(position);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
  }


  /**
   * Getter of selectedModel
   *
   * @return the selectedModel
   */
  public SimpleObjectProperty<OperationData> getSelectedModel() {
    return selectedModel;
  }


  /**
   * Setter of selectedModel
   *
   * @param selectedModel the selectedModel to set
   */
  public void setSelectedModel(OperationData selectedModel) {
    this.selectedModel.set(selectedModel);
  }


  public void selectFirstItem() {
    if (listView != null) {
      listView.selectFirstItem();
    }
  }


  /**
   *
   */
  public void setListView(AbstractListView listView) {
    this.listView = listView;
  }
}
