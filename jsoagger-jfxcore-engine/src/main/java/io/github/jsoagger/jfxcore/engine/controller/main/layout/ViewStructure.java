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

package io.github.jsoagger.jfxcore.engine.controller.main.layout;


import java.io.File;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import com.google.gson.JsonObject;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.css.IStylesheetManager;
import io.github.jsoagger.jfxcore.api.security.RootContextMode;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.PlatformType;
import io.github.jsoagger.jfxcore.engine.components.css.StyleSheetsManager;
import io.github.jsoagger.jfxcore.engine.components.security.LoginSessionHolder;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.components.RSCLoadingPane;
import io.github.jsoagger.jfxcore.engine.controller.utils.RootStructureUtils;
import io.github.jsoagger.jfxcore.engine.events.LoginSuccessEvent;
import io.github.jsoagger.jfxcore.engine.events.LogoutSuccessEvent;
import io.github.jsoagger.jfxcore.engine.events.SetRootStructureEvent;
import io.github.jsoagger.jfxcore.engine.utils.ThemeUtils;
import javafx.application.Application.Parameters;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class ViewStructure implements IViewStructure {

  private static Set<ISceneSetListener> listeners = new HashSet<>();

  public static final double DEFAULT_HEIGHT = 865.0;
  public static final double DEFAULT_WIDTH = 900.0;
  public static final double DEFAULT_MIN_HEIGHT = 865.0;
  public static final double DEFAULT_MIN_WIDTH = 900.0;

  public static final String LOGIN_ROOT_STRUCTURE_ID = "loginRootStructureId";
  public static final String PLATFORM_ROOT_STRUCTURE_ID = "platformRootStructureId";
  public static final String APPLICATION_DATA_FOLDER_PATH = "applicationDataFolderPath";
  public static final String LOCAL_NOTIFS_FOLDER_PATH_FOLDER_PATH = "localNotificationsFolderPath";
  public static final String TEMP_FOLDER_PATH = "tempFolderPath";
  public static final String FALSE = "false";
  public static final String TRUE = "true";
  public static final String FULL_SCREEN = "fullScreen";
  public static final String APPLICATION_WINDOW_NAME = "applicationWindowName";
  public static final String APPLICATION_CONNECTION_MODE = "applicationConnexionMode";
  public static final String ALLOW_ANONYMOUS = "allow_anonymous";
  public static final String DISALLOW_ANONYMOUS = "disallow_anonymous";
  public static final String PLATFORM_PROPERTIES = "platformProperties";
  public static final String APPLICATION_VIEW_CONFIG_MODE = "application.viewconfig.mode";

  public static final String HEIGHT = "height";
  public static final String WIDTH = "width";
  public static final String MIN_HEIGHT = "minHeight";
  public static final String MIN_WIDTH = "minWidth";
  public static final String MAX_HEIGHT = "maxHeight";
  public static final String MAX_WIDTH = "maxWidth";

  protected IStylesheetManager styleSheetManager;
  protected Properties screenProperties;
  protected Properties platformProperties;


  static double screenEffectiveWidth = 0;
  static double screenEffectiveHeight = 0;

  /**
   * The master structure is the main structure of the view. This one can no be removed from the
   * view.
   */
  protected RootStructureController masterStructure = null;

  /** Previous one, if replace we will call its destroy method */
  protected RootStructureController previous = null;
  protected ObjectProperty<ViewStructureStatus> status =
      new SimpleObjectProperty(ViewStructureStatus.BUILDING_STRUCTURE);

  /**
   * Current view displayed in the center area, whatever its building status
   */
  protected SimpleObjectProperty<RootStructureParam> currentView = new SimpleObjectProperty<>();
  protected SimpleObjectProperty<Scene> scene = new SimpleObjectProperty<>();
  protected static Stage primaryStage = null;
  protected static String applicationViewConfigMode;
  protected static ViewStructure instance;
  protected SimpleDoubleProperty platformSceneWidth = new SimpleDoubleProperty();

  protected StackPane secondaryContent = new StackPane();

  /**
   * Constructor
   */
  public ViewStructure() {
    super();
  }

  /**
   * Get current instance of view structure
   *
   * @return
   */
  public static ViewStructure instance() {
    return instance;
  }


  /**
   * Build the view structure. In this method, do not use stage and scene because they are not
   * available yet at this level.
   */
  @Override
  public void buildStructure() {
    try {
      instance = this;
      init();

      currentView.addListener(this::currentViewChange);

      final String applicationConnMode =
          platformProperties.getProperty(APPLICATION_CONNECTION_MODE);
      if (ALLOW_ANONYMOUS.equalsIgnoreCase(applicationConnMode)) {
        // final String rootStructureName =
        // platformProperties.getProperty(PLATFORM_ROOT_STRUCTURE_ID);
        // loadRootView(rootStructureName);
        anonymousConnect();
      } else if (DISALLOW_ANONYMOUS.equalsIgnoreCase(applicationConnMode)) {
        final String viewId = platformProperties.getProperty(LOGIN_ROOT_STRUCTURE_ID);
        loadRootView(viewId);
      } else {
        throw new IllegalArgumentException(
            MessageFormat.format("Unknown connection mode : {0}, should be {1} or {2}",
                applicationConnMode, ALLOW_ANONYMOUS, DISALLOW_ANONYMOUS));
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }

    Platform.runLater(() -> styleSheetManager.loadSteelSheets());
  }


  /**
   * The platform must provide fake LoginOperation.
   */
  private void anonymousConnect() {
    // set root context of the view to /
    final IOperation stubLogin = (IOperation) Services.getBean("LoginOperation");
    stubLogin.doOperation(new JsonObject(), res -> {
      final LoginSessionHolder loginContext =
          (LoginSessionHolder) Services.getBean("LoginSessionHolder");
      loginContext.setSessionId(UUID.randomUUID().toString());
      loginContext.setLoginResult(res);
      loginContext.setMode(RootContextMode.Anonymous);

      // redirect to welcome view
      final LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent();
      loginSuccessEvent.setMode(RootContextMode.Anonymous);
      listenTo(loginSuccessEvent);
    });
  }


  /**
   * Initialize all {@link Stage} relative objects.
   *
   * @param applicationParameters
   * @param primaryStage
   */
  public void initFromPrimaryStage(Stage stage, Parameters applicationParameters) {
    setPrimaryStage(stage);
    steplatformType(applicationParameters);
    setStagetTitle(stage);

    if (AbstractApplicationRunner.isDesktop()) {
      manageDesktopSizing();
    } else if (AbstractApplicationRunner.isSimulMobile()) {
      manageSimuleMobileSizing();
    } else if (AbstractApplicationRunner.isMobile()) {
      manageMobileSizing();
    }

    if (listeners.size() > 0) {
      for (ISceneSetListener sceneSetListener : listeners) {
        sceneSetListener.onSceneSet(stage.getScene());
      }
    }
  }

  private void steplatformType(Parameters applicationParameters) {
    String platformType =
        applicationParameters.getNamed().getOrDefault("jsoagger-client-mode", "simul_mobile");
    if (StringUtils.isEmpty(platformType))
      throw new RuntimeException("Platform type is mandatory, please provide!");
    AbstractApplicationRunner.platformType(PlatformType.valueOf(platformType.toUpperCase()));
  }

  private void setStagetTitle(Stage stage) {
    final String applicationName = platformProperties.getProperty(APPLICATION_WINDOW_NAME);
    stage.setTitle(applicationName);
  }

  private void manageDesktopSizing() {
    // !! when stage is rezisable buttons (close, minimize maximise are shown)
    primaryStage().setResizable(true);
    primaryStage().setMinHeight(screenMinHeight());
    primaryStage().setMinWidth(screenMinWidth());
    primaryStage().setWidth(screenWidth());
    primaryStage().setHeight(screenHeight());
    primaryStage().setOnHiding(event -> Platform.exit());
  }

  private void manageSimuleMobileSizing() {
    primaryStage().setResizable(false);
    primaryStage().setMinHeight(screenMinHeight());
    primaryStage().setMinWidth(screenMinWidth());
    primaryStage().setWidth(screenWidth());
    primaryStage().setHeight(screenHeight());

    double mx = screenMaxWidth();
    if (mx > 0) {
      primaryStage().setMaxWidth(mx);
      screenEffectiveWidth = mx;
    }

    double mxh = screenMaxHeight();
    if (mxh > 0) {
      primaryStage().setMaxHeight(mxh);
      screenEffectiveHeight = mxh;
    }

    Pane rootView = getRootViewStructure();
    if (primaryStage().getScene() == null) {
      // scene.set(new UndecoratorScene(primaryStage(), rootView));
      System.out.println(">>>>>>>>> : + rootView 1");
      scene.set(new Scene(rootView));
      primaryStage().setScene(scene.get());
    } else {
      System.out.println(">>>>>>>>> : + rootView 2");
      scene.set(primaryStage().getScene());
      primaryStage().getScene().setRoot(rootView);
    }

    rootView.prefWidthProperty().bind(scene.get().getWindow().widthProperty());
    rootView.prefHeightProperty().bind(scene.get().getWindow().heightProperty());
    rootView.maxWidthProperty().bind(scene.get().getWindow().widthProperty());
    rootView.maxHeightProperty().bind(scene.get().getWindow().heightProperty());
  }

  private void manageMobileSizing() {
    primaryStage().setResizable(false);
    primaryStage().setFullScreen(true);

    Pane rootView = getRootViewStructure();
    if (primaryStage().getScene() == null) {
      scene.set(new Scene(rootView));
      primaryStage().setScene(scene.get());
    } else {
      scene.set(primaryStage().getScene());
      primaryStage().getScene().setRoot(rootView);
    }

    rootView.prefWidthProperty().bind(scene.get().getWindow().widthProperty());
    rootView.prefHeightProperty().bind(scene.get().getWindow().heightProperty());
    rootView.maxWidthProperty().bind(scene.get().getWindow().widthProperty());
    rootView.maxHeightProperty().bind(scene.get().getWindow().heightProperty());
  }

  /**
   * Get associated scene
   *
   * @return
   */
  public Scene getScene() {
    return scene.get();
  }

  /**
   * @param primaryStage2
   */
  private void setPrimaryStage(Stage stage) {
    primaryStage = stage;
  }


  public void setLoading() {
    if (Platform.isFxApplicationThread()) {
      getRootViewStructureContentArea().getChildren().clear();
      getRootViewStructureContentArea().getChildren().add(getLoadingPane());
    } else {
      Platform.runLater(() -> {
        getRootViewStructureContentArea().getChildren().clear();
        getRootViewStructureContentArea().getChildren().add(getLoadingPane());
      });
    }
  }


  public void currentViewChange(Object observable, RootStructureParam oldValue,
      RootStructureParam newValue) {
    if (newValue.status.get() == ViewStructureStatus.BUILDING_STRUCTURE) {
      setLoading();
    } else if (newValue.status.get() == ViewStructureStatus.ROOTSTRUCTURE_BUILDED_FAILED) {

    } else if (newValue.status.get() == ViewStructureStatus.ROOTSTRUCTURE_BUILDED_SUCCESS) {
      updateRootStructureTo(newValue.getView());
    }
  }


  protected void loadRootView(String identifier) {
    final RootStructureParam param = new RootStructureParam();
    try {
      setLoading();

      param.setIdentifier(identifier);
      param.status.set(ViewStructureStatus.BUILDING_STRUCTURE_CONTENT);

      final RootStructureController rootStructure = RootStructureUtils.forId(identifier);
      param.setView(rootStructure);
      param.status.set(ViewStructureStatus.ROOTSTRUCTURE_BUILDED_SUCCESS);

      currentView.set(param);
    } catch (final Exception e) {
      param.status.set(ViewStructureStatus.ROOTSTRUCTURE_BUILDED_FAILED);
      e.printStackTrace();
    }
  }


  /**
   * @param e
   */
  // @EventListener
  public void lougoutSuccess(LogoutSuccessEvent e) {
    final String viewId = platformProperties.getProperty(LOGIN_ROOT_STRUCTURE_ID);
    loadRootView(viewId);
  }


  /**
   * If user successfully logged in load welcome view.
   *
   * @param event
   */
  // @EventListener
  public void listenTo(LoginSuccessEvent event) {
    final String rootStructureName = platformProperties.getProperty(PLATFORM_ROOT_STRUCTURE_ID);
    loadRootView(rootStructureName);

    // load stylesheets at last
    Platform.runLater(() -> {
      loadUserPreferredTheme();

      String primary = "Indigo";
      String accent = "Orange";

      // ((StyleSheetsManager) styleSheetManager)
      // .setDefaultTheme(ThemeUtils.getCssFromPrimaryColor(primary),
      // ThemeUtils.getCssFromAccentColor(accent));

    });
  }

  private void loadUserPreferredTheme() {
    try {
      IOperation getPref = (IOperation) Services.getBean("GetPreferenceValueOperation");

      JsonObject query = new JsonObject();
      query.addProperty("key", "io.github.jsoagger.theme.primary.color");
      getPref.doOperation(query, primaryColor -> {
        query.addProperty("key", "io.github.jsoagger.theme.accent.color");
        getPref.doOperation(query, accentColor -> {

          String primary = (String) ((MultipleResult) primaryColor).getData().get(0).getAttributes()
              .get("savedValue");
          String accent = (String) ((MultipleResult) accentColor).getData().get(0).getAttributes()
              .get("savedValue");

          ((StyleSheetsManager) styleSheetManager).setDefaultTheme(
              ThemeUtils.getCssFromPrimaryColor(primary), ThemeUtils.getCssFromAccentColor(accent));
        });
      });
    } catch (Exception e) {
    }
  }


  /**
   * @param setRootStructureEvent
   */
  public void listenTo(SetRootStructureEvent setRootStructureEvent) {
    final String rootStructureName = setRootStructureEvent.getViewId();
    loadRootView(rootStructureName);
  }


  public void updateRootStructureTo(RootStructureController rootStructure) {
    if (previous != null) {
      previous.destroy();
    }
    previous = rootStructure;

    Pane newView = (Pane) rootStructure.processedView();
    newView.prefWidthProperty().set(screenMaxWidth());
    newView.prefHeightProperty().set(screenMaxHeight());
    newView.maxWidthProperty().set(screenMaxWidth());
    newView.maxHeightProperty().set(screenMaxHeight());

    Platform.runLater(() -> {
      getRootViewStructureContentArea().getChildren().add(newView);
      getRootViewStructureContentArea().getChildren().remove(0);
    });
  }


  /**
   * The application initialization method. This method is called immediately after the Application
   * class is loaded and constructed. An application may override this method to perform
   * initialization prior to the actual starting of the application.
   *
   * <p>
   * The implementation of this method provided by the Application class does nothing.
   * </p>
   *
   * <p>
   * NOTE: This method is not called on the JavaFX Application Thread. An application must not
   * construct a Scene or a Stage in this method. An application may construct other JavaFX objects
   * in this method.
   * </p>
   */
  public void init() {
    // Check mandatory folder paths
    final File tempFolderPath = new File(platformProperties.getProperty(TEMP_FOLDER_PATH));
    if (!tempFolderPath.exists()) {
      // final boolean created = tempFolderPath.mkdir();
      final boolean created = false;
      if (!created) {
        // log error creating temp folder
      }
    }

    final File applicationDataFolderPath =
        new File(platformProperties.getProperty(APPLICATION_DATA_FOLDER_PATH));
    if (!applicationDataFolderPath.exists()) {
      // final boolean created = applicationDataFolderPath.mkdir();
      final boolean created = false;
      if (!created) {
        // log error creating temp folder
      }
    }

    final File localNotificationsFolderPath =
        new File(platformProperties.getProperty(LOCAL_NOTIFS_FOLDER_PATH_FOLDER_PATH));
    if (!localNotificationsFolderPath.exists()) {
      // final boolean created = localNotificationsFolderPath.mkdir();
      final boolean created = false;
      if (!created) {
        // log error creating temp folder
      }
    }

    // mode inline off fine
    applicationViewConfigMode =
        platformProperties.getProperty("applicationViewConfigMode", "offline");
  }


  public Window mainWindow() {
    return scene.get().getWindow();
  }


  public static Stage primaryStage() {
    return primaryStage;
  }



  private double screenWidth() {
    final String width = screenProperties.getProperty(WIDTH);
    return StringUtils.isNotBlank(width) ? Double.valueOf(width) : DEFAULT_WIDTH;
  }


  private double screenHeight() {
    final String height = screenProperties.getProperty(HEIGHT);
    return StringUtils.isNotBlank(height) ? Double.valueOf(height) : DEFAULT_HEIGHT;
  }


  private double screenMinWidth() {
    final String width = screenProperties.getProperty(MIN_WIDTH);
    return StringUtils.isNotBlank(width) ? Double.valueOf(width) : DEFAULT_MIN_WIDTH;
  }


  private double screenMinHeight() {
    final String height = screenProperties.getProperty(MIN_HEIGHT);
    return StringUtils.isNotBlank(height) ? Double.valueOf(height) : DEFAULT_MIN_HEIGHT;
  }

  private double screenMaxWidth() {
    final String width = screenProperties.getProperty(MAX_WIDTH);
    return StringUtils.isNotBlank(width) ? Double.valueOf(width) : -1;
  }


  private double screenMaxHeight() {
    final String height = screenProperties.getProperty(MAX_HEIGHT);
    return StringUtils.isNotBlank(height) ? Double.valueOf(height) : -1;
  }


  /**
   * @param styleSheetManager the styleSheetManager to set
   */
  public void setStylesheetManager(IStylesheetManager styleSheetManager) {
    this.styleSheetManager = styleSheetManager;
  }


  /**
   * @param screenPorperties the screenProperties to set
   */
  public void setScreenProperties(Properties screenProperties) {
    this.screenProperties = screenProperties;
  }


  /**
   * @param platformProperties the platformProperties to set
   */
  public void setPlatformProperties(Properties platformProperties) {
    this.platformProperties = platformProperties;
  }


  public static double mainSceneWidth() {
    if (instance().getScene() == null) {
      return 900L;
    }
    return instance().getScene().getWidth();
  }


  public static double mainSceneHeight() {
    if (instance().getScene() == null) {
      return 900L;
    }
    return instance().getScene().getHeight();
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public static class RootStructureParam {

    private String identifier;
    private RootStructureController view;
    protected SimpleObjectProperty<ViewStructureStatus> status = new SimpleObjectProperty<>();


    /**
     * Getter of identifier
     *
     * @return the identifier
     */
    public String getIdentifier() {
      return identifier;
    }


    /**
     * Setter of identifier
     *
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
      this.identifier = identifier;
    }


    /**
     * Getter of view
     *
     * @return the view
     */
    public RootStructureController getView() {
      return view;
    }


    /**
     * Setter of view
     *
     * @param view the view to set
     */
    public void setView(RootStructureController view) {
      this.view = view;
    }
  }


  public static boolean isViewConfigOffLine() {
    return applicationViewConfigMode == null
        || "offline".equalsIgnoreCase(applicationViewConfigMode)
        || !"online".equalsIgnoreCase(applicationViewConfigMode);
  }


  public Pane getLoadingPane() {
    final RSCLoadingPane loadingPane = RSCLoadingPane.instance();
    return loadingPane.getRootStackPane();
  }


  public SimpleDoubleProperty platformSceneWidth() {
    return platformSceneWidth;
  }

  public void setScene(Scene scene) {
    this.scene.set(scene);
  }

  public SimpleObjectProperty<Scene> sceneProperty() {
    return scene;
  }

  public IStylesheetManager getStyleSheetManager() {
    return styleSheetManager;
  }

  public void setStyleSheetManager(IStylesheetManager styleSheetManager) {
    this.styleSheetManager = styleSheetManager;
  }

  public Properties getScreenProperties() {
    return screenProperties;
  }

  public Properties getPlatformProperties() {
    return platformProperties;
  }

  public void displayInSecondaryWR(Pane node) {
    secondaryContent.getChildren().clear();
    secondaryContent.getChildren().add(node);
    secondaryContent.setMinWidth(330);
    secondaryContent.setPrefWidth(330);
    secondaryContent.setMaxWidth(330);
    secondaryContent.setStyle("-fx-border-color:red;-fx-border-width:3");
    secondaryContent.translateXProperty().set(0);

    node.maxWidthProperty().unbind();
    node.minWidthProperty().unbind();
    node.maxWidthProperty().bind(node.prefWidthProperty());
    node.minWidthProperty().bind(node.prefWidthProperty());
    node.setPrefWidth(339);
  }

  public void hideSecondaryRS() {

  }

  /**
   * Keys CTRL + R: reload steelSheets
   */
  protected void initRefreshCTRPlusRListener() {
    scene.get().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
      switch (event.getCode()) {
        case PLUS:
          // CTRL + Bigger view
          if (event.isControlDown()) {
          }
          break;
        case MINUS:
          // CTRL - stretched view
          if (event.isControlDown()) {
          }
          break;
        case R:
          // CTRL + R ==> refresh CSS
          if (event.isControlDown()) {
            styleSheetManager.reLoadSteelSheets();
          }
          break;

        // JavaFX maps the back press to the ESC key.
        // It's the programmers decision, whether or not to handle key events
        // and whether or not to forward them to the OS.
        case ESCAPE:
          if (!AbstractApplicationRunner.isDesktop()) {
            // The application is asked to go background
            // Platform.exit();
          }
        default:
          break;
      }
    });
  }

  public static double screenEffectiveWidth() {
    return screenEffectiveWidth;
  }

  public static double screenEffectiveHeight() {
    return screenEffectiveHeight;
  }

  /**
   * Used by mobile preloader only!
   */
  public Parent getParentNode() {
    return null;
  }

  /**
   * Add a listener to be called when the scene is ready.
   *
   * @param listener
   */
  public static void addISceneSetListener(ISceneSetListener listener) {
    listeners.add(listener);
  }
}
