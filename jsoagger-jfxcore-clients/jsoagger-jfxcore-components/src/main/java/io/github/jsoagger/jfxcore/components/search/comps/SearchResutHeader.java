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

package io.github.jsoagger.jfxcore.components.search.comps;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SearchResutHeader implements IBuildable {

  private HBox rootContainer = new HBox();
  private Label searchResults = new Label();
  private Button saveSearchButton = new Button("Save");
  AbstractViewController controller;


  /**
   * Constructor
   */
  public SearchResutHeader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;

    VLViewComponentXML config = ComponentUtils.resolveDefinition((AbstractViewController)controller, "SearchResultToolBar").orElse(null);
    if (config != null) {
      NodeHelper.styleClassAddAll(rootContainer, config, "styleClass", "ep-search-result-header-pane");
      NodeHelper.styleClassAddAll(searchResults, config, "searchResultStyleClass", "ep-search-result-header-search-result-label");
    } else {
      rootContainer.getStyleClass().add("ep-search-result-header-pane");
      searchResults.getStyleClass().add("ep-search-result-header-search-result-label");
    }

    boolean withSaveAction = config == null || config.getBooleanProperty("withSaveAction", true);
    saveSearchButton.setVisible(withSaveAction);
    if (withSaveAction) {
      saveSearchButton.getStyleClass().addAll("ep-search-result-header-save-search-button", "button-large", "button-accent");
    }
    // rootContainer.getChildren().addAll(searchResults,
    // NodeHelper.horizontalSpacer(), saveSearchButton);
    rootContainer.getChildren().addAll(searchResults, NodeHelper.horizontalSpacer());
    searchResults.setText(controller.getLocalised("NO_RESULT_LABEL"));
    saveSearchButton.setDisable(true);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootContainer;
  }


  public void count(Number newValue) {
    saveSearchButton.setDisable(1 > newValue.intValue());
    String msg = controller.getLocalised("RESULT_FOUND_LABEL", newValue.intValue());
    Platform.runLater(() -> {
      searchResults.setText(msg);
    });
  }
}
