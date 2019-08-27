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

package io.github.jsoagger.jfxcore.engine.search;




import java.net.URL;

import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.AbstractViewLayoutManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * A layout where children are layed out in 3 horizontal panes. The center pane and the right pane
 * are scrolled. The left pane stays at its position all time.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SearchViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "SearchViewLayout.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane searchFormHeader;
  @FXML
  protected Pane searchContent;
  @FXML
  protected Pane searchResultContent;

  @FXML
  protected Pane searchFormFooter;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public SearchViewLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    Node topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);
    Node centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    Node bottomNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.BOTTOM);

    if (topNode != null) {
      setTop(topNode);
    }

    if (centerNode != null) {
      setCenter(centerNode);
    }

    if (bottomNode != null) {
      setBottom(bottomNode);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }


  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
   *=============================================================================*/
  private void setTop(Node topNode) {
    searchFormHeader.getChildren().clear();
    searchFormHeader.getChildren().add(topNode);
  }


  public void setBottom(Node node) {
    searchFormFooter.getChildren().clear();
    searchFormFooter.getChildren().add(node);
  }


  public void setCenter(Node node) {
    Platform.runLater(() -> {
      searchContent.getChildren().clear();
      searchContent.getChildren().add(node);
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return this.getClass().getResource(FXML_LOCATION);
  }
}
