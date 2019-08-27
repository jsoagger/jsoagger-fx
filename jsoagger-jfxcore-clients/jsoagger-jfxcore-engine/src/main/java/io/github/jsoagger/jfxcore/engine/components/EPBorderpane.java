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

package io.github.jsoagger.jfxcore.engine.components;




import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * A {@link BorderPane} with fixed size empty default left and right {@link Pane}.
 * <p>
 * <TODO> Later todo, do not scroll the menu with the content as the content can be infinite.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EPBorderpane extends BorderPane {

  private ObjectProperty<Node> topWrapper = new SimpleObjectProperty<>();
  private SimpleDoubleProperty gap = new SimpleDoubleProperty(400);

  private StackPane left = new StackPane();
  private StackPane right = new StackPane();

  protected double hideRightGap = 1600;
  protected double hideLeftGap = 1000;


  /**
   * Constructor
   */
  public EPBorderpane() {
    left.setPrefWidth(400);
    left.managedProperty().bind(left.visibleProperty());
    HBox.setHgrow(left, Priority.NEVER);
    setLeft(left);

    right.setMinWidth(400);
    right.managedProperty().bind(right.visibleProperty());
    right.visibleProperty().bind(Bindings.lessThan(hideRightGap, widthProperty()));
    HBox.setHgrow(right, Priority.ALWAYS);
    setRight(right);

    // add listener to root scene
    widthProperty().addListener((ChangeListener<Number>) (observableValue, oldSceneWidth, newSceneWidth) -> sceneWidthChange(newSceneWidth));
  }


  protected void sceneWidthChange(Number newSceneWidth) {
    left.visibleProperty().set(widthProperty().greaterThan(hideLeftGap).getValue());
    // System.out.println("newSceneWidth: " + newSceneWidth);
    // right.setVisible(newSceneWidth.doubleValue() > 800);
  }


  public void setBTop(Pane top) {
    Pane leftGap = (Pane) getGapNode();
    Pane rightGap = (Pane) getGapNode();

    leftGap.prefHeightProperty().bind(top.heightProperty());
    rightGap.prefHeightProperty().bind(top.heightProperty());

    HBox t = new HBox();
    HBox.setHgrow(top, Priority.ALWAYS);
    t.getChildren().addAll(leftGap, top, rightGap);

    setTop(t);
  }


  public void setBLeft(Node left) {
    this.left.getChildren().add(left);
  }


  public void setBCenter(Node center) {
    StackPane wrapper = new StackPane();
    wrapper.setPrefWidth(USE_COMPUTED_SIZE);
    wrapper.getChildren().add(center);
    setCenter(wrapper);
  }


  public void setBRight(Node right) {
    this.right.getChildren().add(right);
  }


  private Node getGapNode() {
    Pane pane = new Pane();
    pane.minWidthProperty().bind(pane.maxWidthProperty());
    pane.maxWidthProperty().bind(gap);
    return pane;
  }


  public final ObjectProperty<Node> topWrapperProperty() {
    return this.topWrapper;
  }


  public final javafx.scene.Node getTopWrapper() {
    return this.topWrapperProperty().get();
  }


  public final void setTopWrapper(final javafx.scene.Node topWrapper) {
    this.topWrapperProperty().set(topWrapper);
  }
}
