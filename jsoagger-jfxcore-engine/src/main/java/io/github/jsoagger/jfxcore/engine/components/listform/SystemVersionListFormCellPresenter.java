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

package io.github.jsoagger.jfxcore.engine.components.listform;




import java.util.List;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * @author Administrator
 */
public class SystemVersionListFormCellPresenter extends PreferenceItemPresenterFactory implements IListFormCellPresenter {

  private String title;
  private String key;
  private String context;
  private final Label valueLabel = new Label();


  public SystemVersionListFormCellPresenter() {
    valueLabel.getStyleClass().add("ep-list-form-data-value");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public ListFormCellFactory getCell() {
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getOwner() {
    return "SYSTEM";
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getKey() {
    return this.key;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getContext() {
    return this.context;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setContext(String context) {
    this.context = context;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setKey(String key) {
    this.key = key;
  }


  final HBox box = new HBox();


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    box.getStyleClass().add("item-presenter-row-container");
    box.getChildren().add(label);

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    box.getChildren().add(spacer);

    valueLabel.setText("0.0.1-SNAPHSOT");
    box.getChildren().add(valueLabel);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return box;
  }


  @Override
  public void processUpdate(List<IListFormValue> selected) {

  }
}
