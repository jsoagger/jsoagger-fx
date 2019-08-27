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

package io.github.jsoagger.jfxcore.engine.components.simpledetails;




import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.message.ErrorPane;
import io.github.jsoagger.jfxcore.engine.components.message.NoContentPane;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleDetailsPane extends BorderPane {

  protected SimpleDetailsPaneContent content;
  protected SimpleDetailsPaneTitle titlePane = new SimpleDetailsPaneTitle();
  protected VerticalTabPane verticalTabPane;
  protected VLViewComponentXML tabPaneCfg;
  protected NoContentPane nocontentPane = new NoContentPane();
  protected ErrorPane errorPane = new ErrorPane();


  /**
   * Constructor
   */
  public SimpleDetailsPane() {
    setStyle("-fx-background-color: -background-color;" + "-fx-padding:16");
  }


  /**
   *
   */
  public void setNoContent() {
    setCenter(nocontentPane);
  }


  /**
   * @param tabPaneCfg
   * @param simpleDetailsViewController
   */
  public void buildFrom(VLViewComponentXML tabPaneCfg, AbstractViewController controller) {
    this.tabPaneCfg = tabPaneCfg;
    final SimpleDetailsPane tabPaneContainer = this;

    final Task<Void> processing = new Task<Void>() {

      @Override
      protected Void call() throws Exception {

        try {
          verticalTabPane = new VerticalTabPane();
          content = new SimpleDetailsPaneContent();
          verticalTabPane.setContentContainer(tabPaneContainer);
          verticalTabPane.buildFrom(tabPaneCfg, controller);
          verticalTabPane.select(0);
        } catch (final Exception e) {
          e.printStackTrace();
        }
        return null;
      }
    };

    processing.setOnRunning(e -> {
      final Label processinglabel = new Label();
      setCenter(processinglabel);
    });

    processing.setOnSucceeded(e -> {
      content.setVisible(true);
      content.setTop(titlePane);
      setCenter(content);
      setTop(verticalTabPane);
    });

    processing.setOnFailed(e -> {
      final Label failedlabel = new Label();
      setCenter(failedlabel);
      failedlabel.getStyleClass().add("red-icon");
    });

    Platform.runLater(processing);
  }


  /**
   * @param tabContent
   */
  public void setTabContent(Node tabContent) {
    content.setCenter(tabContent);
  }


  public void setTitle(String title) {
    titlePane.setLabel(title);
  }


  public void setDescription(String desc) {
    titlePane.setDescription(desc);
  }


  public void setTitleRightNode(Node rightNode) {
    titlePane.setRightNode(rightNode);
  }


  /**
   * Set the tabPaneCfg
   *
   * @param tabPaneCfg the tabPaneCfg to set
   */
  public void setTabPaneCfg(VLViewComponentXML tabPaneCfg) {
    this.tabPaneCfg = tabPaneCfg;
  }
}
