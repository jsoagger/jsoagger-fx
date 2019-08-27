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

package io.github.jsoagger.jfxcore.engine.controller.main.layout.components;




import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class RSCLoadingPane {

  private Pane rootStackPane = null;
  private static RSCLoadingPane instance = null;

  /**
   * TODO load it from spring config Constructor
   */
  private RSCLoadingPane() {
    URL fxmlLocation = RSCLoadingPane.class.getResource("RSCLoadingPane.fxml");
    rootStackPane = (Pane) NodeHelper.loadFXML(fxmlLocation, this);
  }

  public static RSCLoadingPane instance() {
    if(instance == null) {
      instance = new RSCLoadingPane();
    }
    return instance;
  }


  /**
   * Getter of rootStackPane
   *
   * @return the rootStackPane
   */
  public Pane getRootStackPane() {
    return rootStackPane;
  }


  /**
   * Setter of rootStackPane
   *
   * @param rootStackPane the rootStackPane to set
   */
  public void setRootStackPane(Pane rootStackPane) {
    this.rootStackPane = rootStackPane;
  }
}
