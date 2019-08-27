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




import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarBackButtonClicked;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PopStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * * A header toolbar where primary menu is displayed in the top left side, just on its left is
 * displayed the previous location label and below the primary button is displayed the current
 * location.
 * <p>
 * This navigation bar should be used only in the top header right of the application.
 * <p>
 * For nomwn, this toolbar is not minimizable, but should be in future.
 * <p>
 * The configuration file must declare only one button that is supposed to be the primary button.
 * This button will be layed out automatically on the primary button location.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PrimaryMenuWithNavigationBar extends VBox implements IBuildable, NavigableToolbar, IHeaderToolbar {

  protected StackPane previousLocation = new StackPane();
  protected StackPane currentLocation = new StackPane();

  protected Button backIcon = new Button();
  protected AbstractViewController controller;
  protected IBuildable primaryMenuButton;

  private HBox topToolbar = new HBox();
  private HBox bottomToolbar = new HBox();


  /**
   * Constructor
   */
  public PrimaryMenuWithNavigationBar() {
    super();

    IconUtils.setHeaderNavigationBack(backIcon);
    backIcon.setOnAction(e -> goBack());
    backIcon.getStyleClass().addAll("app-header-button", "hand-hover");
    backIcon.managedProperty().bind(backIcon.visibleProperty());

    // TO DO HANDLE PREVIOUS AND CURRENT
    previousLocation.managedProperty().bind(previousLocation.visibleProperty());
    previousLocation.setVisible(false);

    topToolbar.getChildren().addAll(backIcon, currentLocation);
    bottomToolbar.getChildren().add(previousLocation);
    getChildren().addAll(topToolbar, bottomToolbar);

    topToolbar.getStyleClass().add("ep-primary-menu-with-navbar-top-toolbar");
    bottomToolbar.getStyleClass().add("ep-primary-menu-with-navbar-bottom-toolbar");
    getStyleClass().add("ep-primary-menu-with-navbar-toolbar");

    topToolbar.minWidthProperty().bind(topToolbar.prefWidthProperty());
    // topToolbar.maxWidthProperty().bind(topToolbar.prefWidthProperty());
    topToolbar.minHeightProperty().bind(topToolbar.prefHeightProperty());
    topToolbar.maxHeightProperty().bind(topToolbar.prefHeightProperty());

    bottomToolbar.minWidthProperty().bind(bottomToolbar.prefWidthProperty());
    // bottomToolbar.maxWidthProperty().bind(bottomToolbar.prefWidthProperty());
    bottomToolbar.minHeightProperty().bind(bottomToolbar.prefHeightProperty());
    bottomToolbar.maxHeightProperty().bind(bottomToolbar.prefHeightProperty());

    bottomToolbar.managedProperty().bind(bottomToolbar.visibleProperty());
    NodeHelper.setHVGrow(topToolbar, bottomToolbar, this);

    bottomToolbar.setVisible(false);
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    List<IBuildable> links = ComponentUtils.resolveAndGenerate((AbstractViewController) controller, configuration.getSubcomponents());
    if (links.size() > 1) {
      // prohibited!!!! but ..
    }

    if (links.size() == 1) {
      primaryMenuButton = links.get(0);
      Node button = primaryMenuButton.getDisplay();
      topToolbar.getChildren().add(0, button);
    }
  }


  @Override
  public void updateLocation(UpdateCurrentLocationEvent current) {
    Node location = current.getLocationNode();
    boolean hasPrevious = current.isHasPrevious();
    backIcon.setVisible(hasPrevious);

    Platform.runLater(() -> {
      currentLocation.getChildren().clear();
      currentLocation.getChildren().add(location);
    });
  }


  @Override
  public void setCurrentLocationTo(SetCurrentLocationEvent event) {
    if (event.getLocation() != null) {
      currentLocation.getChildren().clear();
      currentLocation.getChildren().add(event.getLocation());
    }
  }


  /**
   * When navigationItems size is equals to 2, and the user has clicked on back button, fade out
   * location0 and backIcon together, in order to have more stable effect.
   * <p>
   * At the end of the effect, navigationItems is updated because the visibility of back icon is
   * binded to navigationItems size.
   */
  private void fadeOutLocation0AndBackIconAndRemoveLastItem() {
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_CIRC);
    FadeTransition ft = NodeHelper.fadeOut(previousLocation, Duration.millis(100));
    FadeTransition bif = NodeHelper.fadeOut(backIcon, Duration.millis(100));

    ParallelTransition pt = new ParallelTransition(ft, bif);
    pt.setInterpolator(ei);
    pt.play();
  }


  private void animatePushLocation(Node node) {
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_CIRC);
    ScaleTransition st = NodeHelper.scaleIn(node, Duration.millis(100));
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
    PopStructureContentEvent pop = new PopStructureContentEvent();
    controller.dispatchEvent(pop);

    // must inform structure content that back was clicked
    // it may update its toolbar
    HeaderNavbarBackButtonClicked ev = new HeaderNavbarBackButtonClicked.Builder().build();
    controller.dispatchEvent(ev);
  }


  /**
   * Removed the zoom if unique node because the effect is not so good. May be improved later.
   */
  protected void zoomInUniqueLocation() {}


  protected void zoomOutUpdateToAndScaleInLocation1(PushStructureContentEvent current) {
    ScaleTransition st = NodeHelper.scaleOut(currentLocation, Duration.millis(300));
    st.setToX(0.6);
    st.setToY(0.6);

    FadeTransition ft = NodeHelper.fadeOut(currentLocation, Duration.millis(300));
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_CIRC);
    ParallelTransition pt = new ParallelTransition(st, ft);
    pt.setInterpolator(ei);

    pt.setOnFinished(e -> {
      ScaleTransition st2 = NodeHelper.scaleIn(currentLocation, Duration.millis(500));
      st2.setFromX(0.6);
      st2.setFromY(0.6);

      EasingInterpolator ei2 = new EasingInterpolator(EasingMode.IN_EXPO);
      FadeTransition ft2 = NodeHelper.fadeIn(currentLocation, Duration.millis(200));
      ParallelTransition pt2 = new ParallelTransition(st2, ft2);
      pt2.setInterpolator(ei2);
      pt2.play();
    });
    pt.play();

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void hideBottomToolbar() {
    bottomToolbar.setVisible(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void showBottomToolbar() {
    bottomToolbar.setVisible(true);
    bottomToolbar.setVisible(false);
  }
}
