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

package io.github.jsoagger.jfxcore.engine.components.header.comps;




import java.util.concurrent.CompletableFuture;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarBackButtonClicked;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PopStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;

import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NavigationBar extends VBox implements IBuildable, NavigableToolbar {

  private static double BOTTOM_Y_COORD = 35.0;

  private AnchorPane pane = new AnchorPane();
  private Label location0 = new Label("Location 0");
  private Label location1 = new Label("Location 1");

  protected Hyperlink backIcon = new Hyperlink();
  protected AbstractViewController controller;


  /**
   * Constructor
   */
  public NavigationBar() {
    super();

    IconUtils.setHeaderNavigationBack(backIcon);
    backIcon.getStyleClass().addAll("transparent-focus", "empty-padding", "scale-down-on-click");
    backIcon.setOnAction(e -> goBack());

    pane.getChildren().addAll(location0, location1, backIcon);
    getChildren().add(pane);
    NodeHelper.setHVGrow(pane);
    getStyleClass().add("ep-header-center-area-nav-bar-container");

    location0.getStyleClass().add("previous-location-item-label");
    location1.getStyleClass().add("current-location-item-label");

    backIcon.setLayoutX(16);
    backIcon.setLayoutY(15);

    location0.setLayoutX(46);
    location0.setLayoutY(18);

    location1.setLayoutX(22);
    location1.setLayoutY(65);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
  }


  @Override
  public void updateLocation(UpdateCurrentLocationEvent current) {
    Node previousLoc = current.getLocationNode();

    boolean hasPrevious = previousLoc != null;
    backIcon.setVisible(hasPrevious);
    location0.setVisible(hasPrevious);

    if (hasPrevious) {
      // location0.textProperty().set(previousLoc);
      // location0.setOpacity(1);
    }

    location1.textProperty().unbind();
    location1.textProperty().bind(current.getCurrentView().contentLocationProperty());
  }


  private void animatePushLocation(Node node) {
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_CIRC);
    ScaleTransition st = NodeHelper.scaleIn(node, Duration.millis(500));
    st.setInterpolator(ei);
    st.setFromX(0.3);

    ParallelTransition pt = new ParallelTransition(st);
    pt.play();
  }


  /**
   * Nav bar is currently minized and need to be updated. It displays now one item, this item is the
   * previous item.
   */
  private void updateLocation() {

    // KeyValue kv1 = new KeyValue(location.scaleXProperty(), 1,
    // Interpolator.EASE_OUT);
    // KeyValue kv2 = new KeyValue(location.scaleYProperty(), 1,
    // Interpolator.EASE_OUT);
    // KeyValue kv3 = new KeyValue(location.opacityProperty(), 1,
    // Interpolator.EASE_OUT);
    //
    // KeyFrame kf = new KeyFrame(Duration.millis(600), kv1, kv2, kv3);
    // Timeline timeline = new Timeline();
    // timeline.getKeyFrames().addAll(kf);
    // timeline.play();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @param ev
   */
  @Override
  public void goBack(HeaderNavbarFireBackButton ev) {
    goBack();
  }


  @Override
  public void goBack() {
    CompletableFuture.runAsync(() -> {
      PopStructureContentEvent pop = new PopStructureContentEvent();
      controller.dispatchEvent(pop);
    });

    CompletableFuture.runAsync(() -> {
      // must inform structure content that back was clicked
      // it may update its toolbar
      HeaderNavbarBackButtonClicked ev = new HeaderNavbarBackButtonClicked.Builder().build();
      controller.dispatchEvent(ev);
    });
  }


  protected void zoomInUniqueLocation() {
    location0.setVisible(false);

    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_EXPO);
    ScaleTransition st = NodeHelper.scaleIn(location1, Duration.millis(500));
    st.setInterpolator(ei);
    st.setFromX(0.2);
    st.setFromY(0.2);
    st.play();
  }


  protected void zoomOutUpdateToAndScaleInLocation1(PushStructureContentEvent current) {
    ScaleTransition st = NodeHelper.scaleOut(location1, Duration.millis(300));
    st.setToX(0.6);
    st.setToY(0.6);

    FadeTransition ft = NodeHelper.fadeOut(location1, Duration.millis(300));
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_CIRC);
    ParallelTransition pt = new ParallelTransition(st, ft);
    pt.setInterpolator(ei);

    pt.setOnFinished(e -> {
      location1.textProperty().unbind();
      ScaleTransition st2 = NodeHelper.scaleIn(location1, Duration.millis(500));
      st2.setFromX(0.6);
      st2.setFromY(0.6);

      EasingInterpolator ei2 = new EasingInterpolator(EasingMode.IN_EXPO);
      FadeTransition ft2 = NodeHelper.fadeIn(location1, Duration.millis(200));
      ParallelTransition pt2 = new ParallelTransition(st2, ft2);
      pt2.setInterpolator(ei2);
      pt2.play();
    });
    pt.play();

  }


  @Override
  public void setCurrentLocationTo(SetCurrentLocationEvent event) {
    // TODO Auto-generated method stub

  }
}
