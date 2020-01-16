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

package io.github.jsoagger.jfxcore.engine.controller.main.layout;




import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.components.TopTabPanedItem;

import javafx.application.Application.Parameters;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Desktop top tab paned view structure.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TopTabPanedViewStructure extends ViewStructure {

  @FXML
  private Pane companyLogoArea;
  @FXML
  private Pane headerTabPaneArea;
  @FXML
  private Pane headerButtonsArea;
  @FXML
  private Pane structureContentArea;
  @FXML
  private Pane structureRootStackPane;
  @FXML
  private Label dateLabel = new Label();

  private final List<RootStructureController> tabs = new ArrayList<>();


  /**
   * Constructor
   */
  public TopTabPanedViewStructure() {
    super();
    status.set(ViewStructureStatus.BUILDING_STRUCTURE);
    final URL fxmlLocation = TopTabPanedViewStructure.class.getResource("TopTabPanedViewStructure.fxml");
    NodeHelper.loadFXML(fxmlLocation, this);

    buildRootTab();
  }


  protected void buildRootTab() {
    final TopTabPanedItem item = new TopTabPanedItem("Main tab", null);
    SimpleDateFormat dtf = new SimpleDateFormat("d-MM-yyyy");
    //dateLabel.textProperty().set(dtf.format(new Date()));
  }


  @Override
  public void buildStructure() {
    super.buildStructure();
  }


  @Override
  public void init() {
    super.init();
  }


  @Override
  public void initFromPrimaryStage(Stage stage, Parameters parameters) {
    super.initFromPrimaryStage(stage, parameters);
    initRefreshCTRPlusRListener();
  }



  @Override
  public void selectTab(int tabIndex) {
  }


  @Override
  public void removeTab(String tabId) {
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void removeTab(int tabIndex) {

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void selectTab(String tabId) {

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void add(RootStructureController rootStructure) {
    tabs.add(rootStructure);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void remove(RootStructureController rootStructure) {
    tabs.remove(rootStructure);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void closeAllTabs() {
    for(RootStructureController t : tabs) {
      t.destroy();
    }
    tabs.clear();
  }


  /**
   * @return
   */
  @Override
  public final ObjectProperty<ViewStructureStatus> statusProperty() {
    return this.status;
  }


  /**
   * @return
   */
  @Override
  public final io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructureStatus getStatus() {
    return this.statusProperty().get();
  }


  /**
   * @param status
   */
  @Override
  public final void setStatus(final io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructureStatus status) {
    this.statusProperty().set(status);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getRootViewStructure() {
    return structureRootStackPane;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getRootViewStructureHeaderArea() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getRootViewStructureContentArea() {
    return structureContentArea;
  }
}
