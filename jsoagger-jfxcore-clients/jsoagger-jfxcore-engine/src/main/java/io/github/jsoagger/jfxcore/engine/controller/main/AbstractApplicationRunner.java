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

package io.github.jsoagger.jfxcore.engine.controller.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.jsoagger.jfxcore.engine.client.PlatformType;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.VLExecutorService;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader.StateChangeNotification;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * JavaFX application runner, inherit from {@link Application}.
 *
 * @author Ramilafananana VONJISOA
 *
 */
public abstract class AbstractApplicationRunner extends Application {

  public static final ExecutorService TH_POOL = Executors.newFixedThreadPool(4);

  private static final String MICROSOFT = "microsoft";
  private static final String MAC = "mac";
  private static final String OS_NAME = "os.name";
  private static PlatformType platformType;

  protected static Stage primaryStage;
  protected ViewStructure viewStructure;
  protected Parameters applicationParameters;

  static boolean withPreloader = false;
  static String mode = "MOBILE";

  Pane pane = new StackPane();
  private static boolean applicationScrolling = false;

  /**
   * @{inheritedDoc}
   */
  @Override
  public void init() throws Exception {
    super.init();
    this.applicationParameters = getParameters();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    AbstractApplicationRunner.primaryStage = primaryStage;

    String platformType  = applicationParameters.getNamed().get("jsoagger.client.mode");
    platformType(PlatformType.valueOf(platformType == null ? "MOBILE" : platformType.toUpperCase()));

    if(isMobile() || isSimulMobile()) {
      showMobileSplash(primaryStage);

      Task<Void> task = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          initApplication();
          Platform.runLater(() -> show());
          return null;
        }
      };

      new Thread(task).start();
    }
    else {
      initApplication();
      Platform.runLater(() -> show());
    }
  }



  public void show() {
    viewStructure.initFromPrimaryStage(primaryStage, applicationParameters);
    notifyPreloader(new StateChangeNotification(StateChangeNotification.Type.BEFORE_START));
    animateHideNode(pane);

    if(!isMobile() && !isSimulMobile()) primaryStage.show();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void stop() throws Exception {
    super.stop();
    TH_POOL.shutdownNow();
    VLExecutorService.shutDown();
  }


  public static PlatformType platformType() {
    return platformType;
  }


  public static void platformType(PlatformType type) {
    platformType = type;
  }


  public static boolean isMobile() {
    return platformType() == PlatformType.MOBILE;
  }

  public static boolean isSimulMobile() {
    return platformType() == PlatformType.SIMUL_MOBILE;
  }


  public static boolean isDesktop() {
    return platformType() == PlatformType.DESKTOP;
  }


  public static boolean isDesktopMac() {
    String os = System.getProperty(OS_NAME).toLowerCase();
    if (os.indexOf(MAC) != -1) {
      return true;
    }
    return false;
  }


  public static boolean isDesktopWindows() {
    String os = System.getProperty(OS_NAME).toLowerCase();
    if (os.indexOf(MICROSOFT) != -1) {
      return true;
    }
    return false;
  }


  public static boolean isXPad() {
    return platformType() == PlatformType.XPAD;
  }


  public static Stage primaryStage() {
    return primaryStage;
  }


  public static boolean isInitialized() {
    return platformType != null;
  }

  /**
   * @param key
   * @param args
   * @param defaultValue
   * @return
   */
  public static String getArgs(String key, String[] args, String defaultValue) {
    for (String arg : args) {
      String[] splitted = arg.split("=");
      if(splitted.length == 2) {
        if (splitted[0].equals(key)) {
          return splitted[1];
        }
      }
    }

    return defaultValue;
  }

  /**
   * Show a splash screen.
   */
  private void showMobileSplash(Stage stage) {
    pane.getStyleClass().add("-fx-background-color:white");

    Image image = new Image("/images/logo/JSOAGGER_FX_WHITE.png", 300, 200, true, true);
    ImageView imageView = new ImageView(image);
    pane.getChildren().add(imageView);

    Scene scene = new Scene(pane);
    stage.setResizable(false);
    stage.setFullScreen(!AbstractApplicationRunner.isSimulMobile());
    stage.setScene(scene);
    stage.show();
  }

  private void animateHideNode(Pane node) {
    FadeTransition ft = NodeHelper.fadeOut(node, Duration.millis(2000));
    ft.play();
  }

  /**
   * USE ONLY ON MOBILE TO AVOID TOUCH EVENT WHEN SCROLLING
   *
   * @return
   */
  public static synchronized boolean isApplicationScrolling() {
    return applicationScrolling;
  }

  /**
   * USE ONLY ONE MOBILE.
   *
   * @param val
   * @return
   */
  public static synchronized void setApplicationScrolling(boolean val) {
    AbstractApplicationRunner.applicationScrolling = val;
  }

  public abstract void initApplication();
}
