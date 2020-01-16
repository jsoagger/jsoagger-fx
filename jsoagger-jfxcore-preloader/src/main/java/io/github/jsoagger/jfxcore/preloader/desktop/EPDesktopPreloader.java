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

package io.github.jsoagger.jfxcore.preloader.desktop;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Before building any UI of application, spring context and other heavy stuff must be done. That's
 * why this preloader is builded manually outside of the spring context and application. This
 * preloader must be packed in standalone jar, without spring and other heavy dependencies.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EPDesktopPreloader extends Preloader {

  private static final String PRELOADER_HEIGHT = "preloader.height";
  private static final String PRELOADER_WIDTH = "preloader.width";
  private static final String APPLICATION_WAITING_LABEL = "application.waiting.label";
  private static final String APPLICATION_COPYRIGHT = "application.copyright";
  private static final String APPLICATION_NAME = "application.name";
  private static final String APPLICATION_SLOGAN = "application.slogan";
  private static final String APPLICATION_VERSION = "application.version";

  private static Properties properties;

  private Stage preloaderStage;
  private double width;
  private double height;

  @FXML
  private Pane rootPane;
  @FXML
  private Label waitingLabel;
  @FXML
  private Label slogan;
  @FXML
  private Label version;
  @FXML
  private Text platform;
  @FXML
  private Label fullCopyRight;

  static {
    try {
      properties = new Properties();
      InputStream is = EPDesktopPreloader.class.getResourceAsStream("/application.properties");
      properties.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Constructor
   */
  public EPDesktopPreloader() {
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
  public void handleApplicationNotification(PreloaderNotification notification) {
    if (notification instanceof PreloaderNotification) {
      handleError(notification);
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void start(final Stage initStage) throws Exception {
    preloaderStage = initStage;
    URL fxmlLocation = EPDesktopPreloader.class.getResource("EPDesktopPreloader.fxml");
    loadFXML(fxmlLocation);

    Scene splashScene = new Scene(rootPane);
    initStage.setScene(splashScene);
    initStage.setMinWidth(400);
    initStage.setMinHeight(400);

    initStage.setWidth(width);
    initStage.setHeight(height);
    initStage.initStyle(StageStyle.UNDECORATED);

    animateWaitingLabel();
    initStage.show();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
    if (stateChangeNotification.getType() == Type.BEFORE_START) {
      Platform.runLater(() -> applicationReady());
    }
  }


  private void applicationReady() {
    FadeTransition ft = new FadeTransition(Duration.millis(500), preloaderStage.getScene().getRoot());
    ft.setFromValue(1.0);
    ft.setToValue(0.0);
    ft.setOnFinished(e -> {
      preloaderStage.hide();
    });
    ft.play();
  }

  private void animateWaitingLabel() {
    waitingLabel.translateYProperty().set(30);
    KeyValue kv = new KeyValue(waitingLabel.opacityProperty(), 0);
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), kv));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.setAutoReverse(true);
    timeline.play();
  }

  private void handleError(PreloaderNotification notification) {

  }


  /**
   * Load a content from an FXML definition
   */
  private void loadFXML(URL fxmlLocation) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(fxmlLocation);
      fxmlLoader.setController(this);
      fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
      fxmlLoader.load();

      String deliveredVersion = properties.getProperty(APPLICATION_VERSION);
      version.setText(deliveredVersion);

      String slogan = properties.getProperty(APPLICATION_SLOGAN);
      this.slogan.setText(slogan);

      String name = properties.getProperty(APPLICATION_NAME);
      // this.platform.setText(name);

      String copyright = properties.getProperty(APPLICATION_COPYRIGHT);
      fullCopyRight.setText(copyright);

      String wl = properties.getProperty(APPLICATION_WAITING_LABEL);
      waitingLabel.setText(wl);

      String width = properties.getProperty(PRELOADER_WIDTH);
      this.width = Double.valueOf(width);

      String height = properties.getProperty(PRELOADER_HEIGHT);
      this.height = Double.valueOf(height);

      // allow the dialog to be dragged around.
      final Delta dragDelta = new Delta();
      rootPane.setOnMousePressed(mouseEvent -> {
        // record a delta distance for the drag and drop operation.
        dragDelta.x = preloaderStage.getX() - mouseEvent.getScreenX();
        dragDelta.y = preloaderStage.getY() - mouseEvent.getScreenY();
      });
      rootPane.setOnMouseDragged(mouseEvent -> {
        preloaderStage.setX(mouseEvent.getScreenX() + dragDelta.x);
        preloaderStage.setY(mouseEvent.getScreenY() + dragDelta.y);
      });
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException(e);
    }
  }

  class Delta {

    double x, y;
  }

}
