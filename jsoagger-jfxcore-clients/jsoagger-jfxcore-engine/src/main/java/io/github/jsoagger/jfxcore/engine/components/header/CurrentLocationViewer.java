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

package io.github.jsoagger.jfxcore.engine.components.header;




import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * CurrentLocationViewer is a {@link Label} displaying the current location of the root structure
 * and its children. Its is used to be displayed on the top header of the structure. There is at
 * most one {@link CurrentLocationViewer} by {@link RootStructureController}.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CurrentLocationViewer extends StackPane implements IBuildable {

  private boolean displayFullPath;
  private Label label = new Label();

  private SimpleStringProperty shortLocation = new SimpleStringProperty();
  private String fullLocation;


  /**
   * Constructor
   */
  public CurrentLocationViewer() {
    getStyleClass().add("current-location-viewer");

    label.getStyleClass().add("current-location-viewer-label");
    label.textProperty().bind(shortLocation);
    getChildren().add(label);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    displayFullPath = configuration.getBooleanProperty("fullPath", false);
  }


  public void setShortLocation(String location) {
    Platform.runLater(() -> this.shortLocation.set(location));
  }


  public void setFullLocation(String location) {
    this.fullLocation = location;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
