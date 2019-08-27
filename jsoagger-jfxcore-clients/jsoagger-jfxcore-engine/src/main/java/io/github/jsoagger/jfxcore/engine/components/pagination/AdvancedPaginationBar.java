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

package io.github.jsoagger.jfxcore.engine.components.pagination;


import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider.Direction;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class AdvancedPaginationBar extends PaginationBar {

  protected Hyperlink previousButton = new Hyperlink();
  protected Hyperlink nextButton = new Hyperlink();
  protected Hyperlink firstButton = new Hyperlink();
  protected Hyperlink lastButton = new Hyperlink();


  /**
   * Constructor
   */
  public AdvancedPaginationBar() {
    super();

    previousButton.getStyleClass().add("advanced-pagination-button");
    nextButton.getStyleClass().add("advanced-pagination-button");
    firstButton.getStyleClass().add("advanced-pagination-button");
    lastButton.getStyleClass().add("advanced-pagination-button");

    nextButton.setTooltip(new Tooltip("Next"));
    nextButton.disableProperty().bind(Bindings.not(hasNext));

    lastButton.setTooltip(new Tooltip("Last"));
    lastButton.disableProperty().bind(Bindings.not(hasNext));

    previousButton.setTooltip(new Tooltip("Back"));
    previousButton.disableProperty().bind(Bindings.not(hasPrevious));

    firstButton.setTooltip(new Tooltip("First"));
    firstButton.disableProperty().bind(Bindings.not(hasPrevious));

    layout.getChildren().addAll(rowsPerPageLabel, rowsPerPageCombo, itemsCount, firstButton, previousButton, nextButton, lastButton);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    String position = configuration.getPropertyValue("position", "right");
    if ("right".equals(position)) {
      layout.getChildren().add(2, NodeHelper.horizontalSpacer());
    } else if ("left".equals(position)) {
      layout.getChildren().add(NodeHelper.horizontalSpacer());
    }

    // center default
    else {
      layout.getChildren().add(2, NodeHelper.horizontalSpacer());
      layout.getChildren().add(NodeHelper.horizontalSpacer());
    }

    // extract css from configuration
    String styleClass = configuration.getPropertyValue("styleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
      layout.getStyleClass().addAll(styleClass.split(","));
    } else {
      layout.getStyleClass().add("advanced-pagination-bar");
    }

    nextButton.setOnAction(e -> pageable.nextPage(model));
    previousButton.setOnAction(e -> pageable.previousPage(model));
    firstButton.setOnAction(e -> pageable.firstPage(model));
    lastButton.setOnAction(e -> pageable.lastPage(model));
  }


  /**
   * Getter of pageable
   *
   * @return the pageable
   */
  @Override
  public IPageable getPageable() {
    return pageable;
  }


  /**
   * Setter of pageable
   *
   * @param pageable the pageable to set
   */
  @Override
  public void setPageable(IPageable pageable) {
    this.pageable = pageable;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return layout;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isLoadMorePagination() {
    return false;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Direction currentDirection() {
    return null;
  }
}
