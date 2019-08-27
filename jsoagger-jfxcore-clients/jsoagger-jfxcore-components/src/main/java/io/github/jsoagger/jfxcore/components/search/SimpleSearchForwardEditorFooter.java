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

package io.github.jsoagger.jfxcore.components.search;


import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IForwardEditorFooter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchFormController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchRootFormController;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Footer of forward view editor of search view.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class SimpleSearchForwardEditorFooter extends StackPane implements IForwardEditorFooter {

  IFormRowEditor formRowEditor;
  Button doSearchButton;
  AbstractViewController controller;


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    doSearchButton = new Button("Launch search");
    // getChildren().add(doSearchButton);

    doSearchButton.getStyleClass().add("ep-as-forward-editor-search-button");
    getStyleClass().add("ep-as-forward-editor-search-button-wrapper");

    doSearchButton.setOnAction(e -> doSearch());
    doSearchButton.prefWidthProperty().bind(widthProperty());
  }


  protected void doSearch() {
    formRowEditor.onOk();
    final AbstractViewController ctrl = controller;
    Task<Void> t = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        if (controller instanceof SearchRootFormController) {
          SearchRootFormController controller = (SearchRootFormController) ctrl;
          controller.doSearch();
        } else {
          if (controller instanceof SearchFormController) {
            SearchFormController controller = (SearchFormController) ctrl;
            controller.doSearch();
          }
        }

        return null;
      }
    };

    Thread thread = new Thread(t);
    thread.setDaemon(true);
    thread.setName("__ep__Do_search_thread_ep__");
    thread.start();
  }


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.api.IDisplayable#getDisplay()
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.components.wizard.editor.components.
   * IForwardEditorFooter#setForwardEditor(io.github.jsoagger.jfxcore.components.
   * wizard.editor.components.IFormRowEditor)
   */
  @Override
  public void setForwardEditor(IFormRowEditor simpleForwardEditor) {
    formRowEditor = simpleForwardEditor;
  }
}
