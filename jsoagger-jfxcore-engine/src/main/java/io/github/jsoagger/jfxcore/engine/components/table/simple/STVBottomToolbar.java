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

package io.github.jsoagger.jfxcore.engine.components.table.simple;




import io.github.jsoagger.jfxcore.engine.client.utils.IPageResult;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * Simple Table View Top toolbar of simple table view
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class STVBottomToolbar extends HBox {

  private final Hyperlink next = new Hyperlink();
  private final Hyperlink back = new Hyperlink();

  private final Label elementCount = new Label();
  private final SimpleBooleanProperty hasNext = new SimpleBooleanProperty(false);
  private final SimpleBooleanProperty hasPrevious = new SimpleBooleanProperty(false);
  private final String totalElementCountFormat = "%s-%s   of   %s entries";


  /**
   * Constructor
   */
  public STVBottomToolbar() {
    super();

    setStyle("-fx-background-color: white;" + "-fx-border-width: 0 0 0;" + "-fx-padding: 0 16 0 16;" + "-fx-min-height: 88;" + "-fx-alignment: CENTER_RIGHT;" + "-fx-spacing: 16;");

    next.setTooltip(new Tooltip("Next"));
    next.disableProperty().bind(Bindings.not(hasNext));

    back.setTooltip(new Tooltip("Back"));
    back.disableProperty().bind(Bindings.not(hasPrevious));

    elementCount.setStyle("-fx-font-family: 'Roboto Regular';" + "-fx-font-size: 1.3em;" + "-fx-opacity: 0.87;" + "-fx-padding: 0 32 0 0;");

    // add all
    getChildren().addAll(elementCount, NodeHelper.horizontalSpacer(), back, next);
  }


  public void setPageResult(IPageResult pageResult) {
    if (pageResult != null) {
      hasNext.set(pageResult.getHasNextPage());
      hasPrevious.set(pageResult.getHasPreviousPage());

      if (pageResult.getTotal() > 0) {
        elementCount.setVisible(true);
      }

      setElementCount(pageResult.getTotal(), pageResult.getTotalElements());
    } else {
      hasNext.set(false);
      hasPrevious.set(false);
      setElementCount(0, 0);
      elementCount.setVisible(false);
    }
  }


  private void setElementCount(long count, long total) {
    elementCount.textProperty().set(String.format(totalElementCountFormat, 1, count, total));
  }
}
