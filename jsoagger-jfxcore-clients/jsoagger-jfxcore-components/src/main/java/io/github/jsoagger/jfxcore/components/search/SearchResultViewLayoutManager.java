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



import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.AbstractViewLayoutManager;
import io.github.jsoagger.jfxcore.components.search.controller.SearchResultController;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * A layout where children are layed out in 3 horizontal panes. The center pane and the right pane
 * are scrolled. The left pane stays at its position all time.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SearchResultViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "SearchResultViewLayoutManager.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  private Pane srSearchResultHeader;
  @FXML
  protected Pane srLeftFixedAreaSection;
  @FXML
  protected Pane srHeaderSection;
  @FXML
  protected Pane srCenterFixedAreaSection;
  @FXML
  protected Pane srRightFixedAreaSection;
  @FXML
  protected Pane srSearchSummary;
  @FXML
  protected Pane srCenter;
  @FXML
  protected Pane srFooter;
  @FXML
  protected ScrollPane mainScrollPane;
  @FXML
  protected Text srResultCount;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public SearchResultViewLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    srSearchResultHeader.managedProperty().bind(srSearchResultHeader.visibleProperty());
    srSearchResultHeader.setVisible(false);

    srLeftFixedAreaSection.managedProperty().bind(srLeftFixedAreaSection.visibleProperty());
    srLeftFixedAreaSection.maxWidthProperty().bind(srLeftFixedAreaSection.prefWidthProperty());
    srLeftFixedAreaSection.minWidthProperty().bind(srLeftFixedAreaSection.prefWidthProperty());

    srRightFixedAreaSection.managedProperty().bind(srRightFixedAreaSection.visibleProperty());
    srRightFixedAreaSection.maxWidthProperty().bind(srRightFixedAreaSection.prefWidthProperty());

    srCenterFixedAreaSection.minWidthProperty().bind(srCenterFixedAreaSection.maxWidthProperty());
    srHeaderSection.managedProperty().bind(srHeaderSection.visibleProperty());

    NodeHelper.setStyleClass(srHeaderSection, layoutManageable.getConfiguration(), "headerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(srLeftFixedAreaSection, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(srCenterFixedAreaSection, layoutManageable.getConfiguration(), "centerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(srRightFixedAreaSection, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(srFooter, layoutManageable.getConfiguration(), "footerSectionAreaStyleClass", false);

    final Node leftNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT);
    final Node centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    final Node rightNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT);
    final Node topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);
    final Node bottomNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.BOTTOM);

    if (leftNode != null) {
      setLeft(leftNode);
    }

    if (centerNode != null) {
      setCenter(centerNode);
    }

    if (rightNode != null) {
      setRight(rightNode);
    }

    if (topNode != null) {
      setTop(topNode);
    }

    if (bottomNode != null) {
      setBottom(bottomNode);
    }

    if (layoutManageable instanceof SearchResultController) {
      srHeaderSection.visibleProperty().bind(((SearchResultController) layoutManageable).showTypeInSummaryProperty());
      ((SearchResultController) layoutManageable).resultCountProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
        Platform.runLater(() -> {
          if (newValue.intValue() > 0) {
            srResultCount.setText(newValue.toString() + " item(s) found");
          } else {
            srResultCount.setText("No items found");
          }
        });
      });

      ((SearchResultController) layoutManageable).searchSummaryProperty().addListener((ChangeListener<Node>) (observable, oldValue, newValue) -> {
        Platform.runLater(() -> {
          if (newValue == null) {
            srSearchSummary.getChildren().clear();
          } else {
            srSearchSummary.getChildren().clear();
            srSearchSummary.getChildren().add(newValue);
          }
        });
      });
    }
  }


  private void setBottom(Node bottomNode) {
    srFooter.getChildren().clear();
    srFooter.getChildren().add(bottomNode);
  }


  private void setTop(Node topNode) {
    srSearchResultHeader.getChildren().clear();
    srSearchResultHeader.getChildren().add(topNode);
    srSearchResultHeader.setVisible(true);
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
  public void setLeft(Node node) {
    srLeftFixedAreaSection.getChildren().clear();
    srLeftFixedAreaSection.getChildren().add(node);
  }


  public void setCenter(Node node) {
    srCenter.getChildren().clear();
    srCenter.getChildren().add(node);
  }


  public void setRight(Node node) {
    Platform.runLater(() -> {
      srRightFixedAreaSection.getChildren().clear();
      srRightFixedAreaSection.getChildren().add(node);
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    final IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    final IResponsiveSizing centerSize = areasSize.getSizeOf(1);
    final IResponsiveSizing rightSize = areasSize.getSizeOf(2);

    IResponsiveAware.doResize(srLeftFixedAreaSection, leftSize);
    IResponsiveAware.doResize(srCenterFixedAreaSection, centerSize);
    IResponsiveAware.doResize(srRightFixedAreaSection, rightSize);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    mainScrollPane = null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return this.getClass().getResource(FXML_LOCATION);
  }
}
