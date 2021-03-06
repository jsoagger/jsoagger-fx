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

package io.github.jsoagger.jfxcore.components.list.utils;



import java.util.Set;

import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.layout.Region;

/**
 * Implementation of listview with fixed size. By default {@link ListView} displays empty rows, this
 * skin will hide them.
 *
 * @author Administrator
 *
 */
public class FixedSizeListViewSkin<T extends ListView> extends ListViewSkin {

  private T listView;
  private ScrollBar scrollBarHorizontal;
  private ScrollBar scrollBarVertical;
  private boolean fillWidthCache;
  private double prefWidthCache;
  private Region placeholderRegion;
  private boolean scrollableVertically = false;


  public FixedSizeListViewSkin(T listView) {
    super(listView);
    this.listView = listView;
    // registerChangeListener(listView.fillWidthProperty(), e -> updateFillWidth());
  }

  private void updateCellsPrefWidth(Observable o) {
    final Insets insets = getSkinnable().getInsets();
    final double prefWidth = getSkinnable().getWidth() + insets.getLeft() + insets.getRight()
        - scrollBarVertical.getWidth();

    if (prefWidth != prefWidthCache) {
      for (int i = 0; i < getItemCount(); i++) {
        final IndexedCell cell = getVirtualFlow().getCell(i);
        if (!cell.isEmpty()) {
          cell.setPrefWidth(prefWidth);
        }
      }

      prefWidthCache = prefWidth;
    }
  }


  private boolean showingPlaceHolder() {
    // checkState9();

    if (getItemCount() == 0) {
      if (placeholderRegion == null) {
        final Object obj = getChildren().get(getChildren().size() - 1);
        if (obj instanceof Node && ((Region) obj).getStyleClass().contains("placeholder")) {
          placeholderRegion = (Region) obj;
        }
      }

      if (placeholderRegion != null) {
        return true;
      }
    }

    return false;
  }


  // removed JDK9, exist in 8 and overrided
  protected void handleControlPropertyChanged(String p) {
    // super.handleControlPropertyChanged(p);
    if ("FILL_WIDTH".equals(p)) {
      // updateFillWidth();
    }
  }


  @SuppressWarnings("rawtypes")
  @Override
  protected double computePrefHeight(double width, double topInset, double rightInset,
      double bottomInset, double leftInset) {

    // BE AWARE OF CONTENT OF THE EACH CELL.
    // IT CAN MAKE THE CELL HEIGHT GROWING AND CALCULATION IS WRONG
    // SO THE LISTVIEW STILL SCOLL VERTICALY
    // --> CONTENT SIZE HEIGHT MUT BE < CELL HEIGHT

    if (showingPlaceHolder()) {
      return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    } else {
      double computedHeight = topInset + bottomInset;
      if (computedHeight < 0) {
        computedHeight = 0;
      }

      for (int i = 0; i < getItemCount(); i++) {
        final IndexedCell cell = getVirtualFlow().getCell(i);
        if (!cell.isEmpty()) {
          computedHeight += cell.getHeight();
        }
      }

      return computedHeight;
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  protected double computeMinHeight(double width, double topInset, double rightInset,
      double bottomInset, double leftInset) {
    return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  protected double computeMaxHeight(double width, double topInset, double rightInset,
      double bottomInset, double leftInset) {
    return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
  }



  @SuppressWarnings("rawtypes")
  @Override
  protected double computePrefWidth(double height, double topInset, double rightInset,
      double bottomInset, double leftInset) {
    double computedWidth = 0;

    if (showingPlaceHolder()) {
      computedWidth += placeholderRegion.getLayoutBounds().getWidth();
    } else {
      for (int i = 0; i < getItemCount(); i++) {
        final IndexedCell cell = getVirtualFlow().getCell(i);
        if (!cell.isEmpty() && cell.getWidth() > computedWidth) {
          computedWidth = cell.getWidth();
        }
      }
      if (scrollBarVertical != null && scrollBarVertical.isVisible()) {
        computedWidth += scrollBarVertical.getWidth();
      }
    }

    if (computedWidth != 0) {
      return computedWidth + leftInset + rightInset;
    } else {
      return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }
  }



  @Override
  protected void layoutChildren(double x, double y, double w, double h) {
    super.layoutChildren(x, y, w, h);
    if (scrollBarHorizontal == null || scrollBarVertical == null) {
      Set<Node> nodes = getSkinnable().lookupAll(".scroll-bar");

      for (Node node : nodes) {
        if (node instanceof ScrollBar) {
          final ScrollBar scrollBar = (ScrollBar) node;

          if (scrollBar.getOrientation() == Orientation.HORIZONTAL) {
            scrollBarHorizontal = scrollBar;
          } else {
            scrollBarVertical = scrollBar;
          }
        }
      }

      // updateFillWidth();
    }
  }


  @Override
  public void dispose() {
    if (fillWidthCache) {
      scrollBarHorizontal.visibleProperty().removeListener(this::updateCellsPrefWidth);
      scrollBarVertical.visibleProperty().removeListener(this::updateCellsPrefWidth);
    }

    listView = null;
    super.dispose();
  }


  /**
   * Setter of scrollableVertically
   *
   * @param scrollableVertically the scrollableVertically to set
   */
  public void setScrollableVertically(boolean scrollableVertically) {
    this.scrollableVertically = scrollableVertically;
  }
}
