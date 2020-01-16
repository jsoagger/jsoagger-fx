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

package io.github.jsoagger.jfxcore.engine.components.preloader;




import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MobileApplicationPreloader extends Preloader {

  protected Stage mainStage;
  protected StackPane topGroup;


  /**
   * Constructor
   */
  public MobileApplicationPreloader() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void handleProgressNotification(ProgressNotification info) {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void handleApplicationNotification(PreloaderNotification info) {
    if (info instanceof PreloaderErrorNotification) {
      showErrorScreen();
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void start(final Stage initStage) throws Exception {
    this.mainStage = initStage;

    Node splashScreen = getSplashScreen();
    topGroup = new StackPane(splashScreen);
    topGroup.setStyle("-fx-alignment:CENTER;" + "-fx-background-color: white; " + "-fx-border-width:5; " + "-fx-border-color: " + "black;");

    Scene splashScene = new Scene(topGroup, Color.TRANSPARENT);
    mainStage.setScene(splashScene);
    mainStage.initStyle(StageStyle.UNDECORATED);

    if (inDevMode()) {
      mainStage.setWidth(500);
      mainStage.setHeight(800);
    } else {
      mainStage.setFullScreen(true);
    }

    mainStage.show();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
    if (stateChangeNotification.getType() == Type.BEFORE_INIT) {
    }
  }


  /**
   * Display error when error occurs when loading application.
   */
  private void showErrorScreen() {
    topGroup.getChildren().clear();
    StackPane pane = new StackPane();
    pane.setAlignment(Pos.CENTER);
    topGroup.getChildren().add(pane);

    VBox container = new VBox();
    container.setAlignment(Pos.CENTER);

    Label error = new Label();
    error.getStyleClass().add("accent-icon");

    Label errorLabel = new Label("Dont worry, just reload :-)");
    errorLabel.getStyleClass().add("h5");
    errorLabel.setOpacity(0.50);
    container.getChildren().addAll(error, errorLabel);
    pane.getChildren().add(container);
  }


  /**
   * @return
   */
  private boolean inDevMode() {
    return true;
  }


  /**
   * @{inheritedDoc}
   */
  public Node getSplashScreen() {
    StackPane pane = new StackPane();
    ProgressIndicator indicator = new ProgressIndicator(-1);
    indicator.setMaxSize(32, 32);
    pane.getChildren().add(indicator);
    return pane;
  }
}
