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




import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider.Direction;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LoadMorePaginationBar extends PaginationBar {

  protected HBox layout = new HBox();
  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;

  private Direction direction = Direction.NEXT;
  protected Hyperlink previousButton = new Hyperlink();
  protected Hyperlink nextButton = new Hyperlink();


  /**
   * Constructor
   */
  public LoadMorePaginationBar() {
    super();

    previousButton.getStyleClass().add("simple-pagination-button");
    previousButton.disableProperty().bind(Bindings.not(hasPrevious));
    IconUtils.setFontIcon("fa-angle-up:18", previousButton);

    nextButton.disableProperty().bind(Bindings.not(hasNext));
    IconUtils.setFontIcon("fa-angle-down:18", nextButton);
    layout();
  }

  protected void layout(){
	  if(AbstractApplicationRunner.isDesktop()) {
	      layout.getChildren().addAll(NodeHelper.horizontalSpacer(),rowsPerPageLabel, itemsCount, rowsPerPageCombo, nextButton, NodeHelper.horizontalSpacer());
	    }
	    else {
	      layout.getChildren().addAll(NodeHelper.horizontalSpacer(), rowsPerPageLabel, nextButton);
	    }
  }


  private void addFakeScrollListener() {
    // private ChangeListener<T> listener = (obs, ov, nv) -> {
    // if( getText() != null
    // && getText().equals(String.valueOf(LAST_ITEM))
    // && ((getListView().getHeight()-this.getHeight()
    // -nv.doubleValue()) > 0 )) {
    // System.out.println("last item in view " +
    // " scrolled into view (fetch more data?)");
    // }
    // };
    // layoutYProperty().addListener(listener);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isLoadMorePagination() {
    return true;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    layout.managedProperty().bind(layout.visibleProperty());

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

    previousButton.setText(controller.getLocalised("LESS_LABEL").toUpperCase());
    nextButton.setText(controller.getLocalised("MORE_LABEL").toUpperCase());

    NodeHelper.styleClassSetAll(layout, configuration, "loadMoreLayoutStyleClass", "loadmore-pagination-bar");
    NodeHelper.styleClassSetAll(nextButton, configuration, "loadMoreButtonStyleClass", "load-more-pagination-button");
    NodeHelper.styleClassSetAll(previousButton, configuration, "loadLessButtonStyleClass", "load-more-pagination-button");

    nextButton.setOnAction(e -> {
      direction = Direction.NEXT;
      pageable.nextPage(model);
    });

    previousButton.setOnAction(e -> {
      direction = Direction.PREVIOUS;
      pageable.loadLess(model);
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
