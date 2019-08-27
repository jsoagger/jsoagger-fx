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

package io.github.jsoagger.jfxcore.engine.components.listform;




import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.components.list.utils.FixedSizeListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DefaultListFormListView extends FixedSizeListView<IListFormCellPresenter> {

  // the parent node of this list view and previous displayed node
  private Pane parentLayout;
  private ObservableList<Node> previousNodes = FXCollections.observableArrayList();

  // these function are applied when item on the listview is clicked
  // to show its details
  private Function<Void, Void> backFunction;
  private Function<Node, Void> showFunction;


  /**
   * Constructor
   */
  public DefaultListFormListView() {
    super();
  }


  /**
   * Getter of parentLayout
   *
   * @return the parentLayout
   */
  public Pane getParentLayout() {
    return parentLayout;
  }


  /**
   * Setter of parentLayout
   *
   * @param parentLayout the parentLayout to set
   */
  public void setParentLayout(Pane parentLayout) {
    showFunction = t -> {
      previousNodes.addAll(parentLayout.getChildren());
      parentLayout.getChildren().clear();
      parentLayout.getChildren().add(t);
      return null;
    };

    backFunction = t -> {
      parentLayout.getChildren().clear();
      parentLayout.getChildren().addAll(previousNodes);
      previousNodes.clear();
      return null;
    };
    this.parentLayout = parentLayout;
  }


  /**
   * Getter of backFunction
   *
   * @return the backFunction
   */
  public Function<Void, Void> getBackFunction() {
    return backFunction;
  }


  /**
   * Setter of backFunction
   *
   * @param backFunction the backFunction to set
   */
  public void setBackFunction(Function<Void, Void> backFunction) {
    this.backFunction = backFunction;
  }


  /**
   * Getter of showFunction
   *
   * @return the showFunction
   */
  public Function<Node, Void> getShowFunction() {
    return showFunction;
  }


  /**
   * Setter of showFunction
   *
   * @param showFunction the showFunction to set
   */
  public void setShowFunction(Function<Node, Void> showFunction) {
    this.showFunction = showFunction;
  }
}
