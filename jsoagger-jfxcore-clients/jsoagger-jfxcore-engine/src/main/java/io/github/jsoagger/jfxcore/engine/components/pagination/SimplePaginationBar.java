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
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimplePaginationBar extends PaginationBar {

  protected Direction direction;
  protected Button previousButton = new Button();
  protected Button nextButton = new Button();
  protected Button firstButton = new Button();
  protected Button lastButton = new Button();


  /**
   * Constructor
   */
  public SimplePaginationBar() {
    super();

    previousButton.getStyleClass().addAll("simple-pagination-button");
    previousButton.setTooltip(new Tooltip("Back"));
    previousButton.disableProperty().bind(Bindings.not(hasPrevious));
    IconUtils.setFontIcon("mdi-chevron-left:24", previousButton);

    nextButton.getStyleClass().addAll("simple-pagination-button");
    nextButton.setTooltip(new Tooltip("Next"));
    nextButton.disableProperty().bind(Bindings.not(hasNext));
    IconUtils.setFontIcon("mdi-chevron-right:24", nextButton);

    firstButton.getStyleClass().addAll("simple-pagination-button");
    IconUtils.setFontIcon("mdi-chevron-double-left:24", firstButton);

    lastButton.getStyleClass().addAll("simple-pagination-button");
    IconUtils.setFontIcon("mdi-chevron-double-right:24", lastButton);

    lastButton.setTooltip(new Tooltip("Last"));
    lastButton.disableProperty().bind(Bindings.not(hasNext));

    firstButton.setTooltip(new Tooltip("First"));
    firstButton.disableProperty().bind(Bindings.not(hasPrevious));

    // @formatter:off
    layout.setSpacing(10);
    layout.getChildren().addAll(rowsPerPageLabel, itemsCount, NodeHelper.horizontalSpacer(), rowsPerPageCombo, previousButton, nextButton);
    // @formatter:on
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
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    layout.managedProperty().bind(layout.visibleProperty());

    final String position = configuration.getPropertyValue("position", "right");
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
    final String styleClass = configuration.getPropertyValue("styleClass");
    layout.getStyleClass().add("simple-pagination-bar");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
      layout.getStyleClass().addAll(styleClass.split(","));
    }

    nextButton.setOnAction(e -> {
      direction = Direction.NEXT;
      pageable.nextPage(model);
    });
    previousButton.setOnAction(e -> {
      direction = Direction.PREVIOUS;
      pageable.previousPage(model);
    });

    firstButton.setOnAction(e -> {
      direction = Direction.PREVIOUS;
      pageable.firstPage(model);
    });
    lastButton.setOnAction(e -> {
      direction = Direction.NEXT;
      pageable.lastPage(model);
    });
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
  public Direction currentDirection() {
    return direction;
  }
}
