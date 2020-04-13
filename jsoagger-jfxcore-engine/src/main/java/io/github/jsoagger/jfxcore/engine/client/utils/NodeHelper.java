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

package io.github.jsoagger.jfxcore.engine.client.utils;



import java.net.URL;

import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.ProcessingDialog;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarBackButtonClicked;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PopStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewRootMenuRowXML;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NodeHelper {

  private static final String ROWS_CONTAINER_CSS = "root-menu-rows-container";


  /**
   * Set node's focus transparent
   *
   * @param node
   */
  public static void setTransparentFocus(Node node) {
    node.getStyleClass().add("transparent-focus");
  }


  /*-----------------------------------------------------------------------------
  | HANDLING LOCATION PROPERTY
   *=============================================================================*/
  public static String location(VLViewRootMenuRowXML configuration,
      AbstractViewController controller) {
    String location = configuration.getPropertyValueByName("location");
    if (StringUtils.isNotBlank(location)) {
      location = controller.getLocalised(location);
    }

    return location;
  }


  public static String location(VLViewComponentXML configuration,
      AbstractViewController controller) {
    String location = configuration.getPropertyValue("location");
    if (StringUtils.isNotBlank(location)) {
      location = controller.getLocalised(location);
    }

    return location;
  }


  /*-----------------------------------------------------------------------------
  | STYLE CLASS PROCESSING HELPER
   *=============================================================================*/
  /**
   * Process the styleclass of the node there is one declared.
   *
   * @param node
   * @param add if true use addAll instead of setAll
   * @param configuration
   */
  private static void processStyleClass(Node node, VLViewComponentXML configuration,
      String defaultStyleClass, boolean add) {

    final String styleClass = configuration.getPropertyValue("styleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      if (add) {
        node.getStyleClass().addAll(styleClass.split(","));
      } else {
        node.getStyleClass().setAll(styleClass.split(","));
      }
    } else if (StringUtils.isNotBlank(defaultStyleClass)) {
      if (add) {
        node.getStyleClass().addAll(defaultStyleClass.split(","));
      } else {
        node.getStyleClass().setAll(defaultStyleClass.split(","));
      }
    }
  }


  /**
   * Process the styleclass of the node there is one declared.
   *
   * @param node
   * @param add if true use addAll instead of setAll
   * @param configuration
   */
  private static void processStyleClass(Node node, VLViewComponentXML configuration,
      String styleClassName, String defaultStyleClass, boolean add) {
    if (configuration != null) {

      final String styleClassNameDeclared = configuration.getPropertyValue(styleClassName);
      if (StringUtils.isNotBlank(styleClassNameDeclared)) {
        if (add) {
          node.getStyleClass().addAll(styleClassNameDeclared.split(","));
        } else {
          node.getStyleClass().setAll(styleClassNameDeclared.split(","));
        }
      } else if (StringUtils.isNotBlank(defaultStyleClass)) {
        if (add) {
          node.getStyleClass().addAll(defaultStyleClass.split(","));
        } else {
          node.getStyleClass().setAll(defaultStyleClass.split(","));
        }
      }
    }
  }


  /**
   * Process the styleclass of the node there is one declared, using setAll method.
   *
   * @param node
   * @param configuration
   */
  public static void styleClassSetAll(Node node, VLViewComponentXML configuration) {
    processStyleClass(node, configuration, null, false);
  }


  /**
   * Process the styleclass of the node there is one declared, using addAll method.
   *
   * @param node
   * @param configuration
   */
  public static void styleClassAddAll(Node node, VLViewComponentXML configuration) {
    processStyleClass(node, configuration, null, true);
  }


  /**
   * @param node
   * @param defaultStyleClassValue
   * @param configuration
   */
  public static void styleClassSetAll(Node node, VLViewComponentXML configuration,
      String defaultStyleClassValue) {
    processStyleClass(node, configuration, defaultStyleClassValue, false);
  }


  public static void setStyleClass(Node node, VLViewComponentXML configuration,
      String styleClassName, boolean add) {
    if (configuration != null && node != null) {
      final String styleClass = configuration.getPropertyValue(styleClassName);
      if (StringUtils.isNotBlank(styleClass)) {
        if (add) {
          node.getStyleClass().addAll(styleClass.split(","));
        } else {
          node.getStyleClass().setAll(styleClass.split(","));
        }
      }
    }
  }


  /**
   * @param node
   * @param styleClassName
   * @param configuration
   */
  public static void styleClassAddAll(Node node, VLViewComponentXML configuration,
      String styleClassName) {
    processStyleClass(node, configuration, styleClassName, null, true);
  }


  /**
   * @param node
   * @param styleClassName
   * @param configuration
   */
  public static void styleClassAddAll(Node node, VLViewComponentXML configuration,
      String styleClassName, String defaultStyleClass) {
    processStyleClass(node, configuration, styleClassName, defaultStyleClass, true);
  }


  /**
   * @param node
   * @param styleClassName
   * @param configuration
   */
  public static void styleClassSetAll(Node node, VLViewComponentXML configuration,
      String styleClassName, String defaultStyleClassValue) {

    final String styleClassNameDeclared = configuration.getPropertyValue(styleClassName);
    if (StringUtils.isNotBlank(styleClassNameDeclared)) {
      node.getStyleClass().setAll(styleClassNameDeclared.split(","));
    } else if (StringUtils.isNotBlank(defaultStyleClassValue)) {
      node.getStyleClass().setAll(defaultStyleClassValue.split(","));
    }
  }


  /*-----------------------------------------------------------------------------
  | PROCESSING TITLE OF LABELED
   *=============================================================================*/
  public static void setTitle(Labeled labeled, VLViewComponentXML configuration,
      AbstractViewController controller) {

    // title
    final boolean uppercase = configuration.getBooleanProperty(XMLConstants.UPPERCASE, false);
    final String titleLabel = configuration.getPropertyValue(XMLConstants.TITLE);

    if (StringUtils.isNotBlank(titleLabel)) {
      final String translated = controller.getLocalised(titleLabel, configuration);
      labeled.setText(uppercase ? translated.toUpperCase() : translated);
    }

    // tooltip
    final String tooltipLabel = configuration.getPropertyValue(XMLConstants.TOOLTIP);
    if (StringUtils.isNotBlank(tooltipLabel)) {
      final String tooltipLabeltranslated = controller.getLocalised(tooltipLabel);
      labeled.setTooltip(new Tooltip(tooltipLabeltranslated));
    }
  }


  public static void setTitle(Labeled labeled, VLViewComponentXML configuration,
      AbstractViewController controller, boolean bind) {
    setTitle(labeled, configuration, controller);
    if (bind) {
      labeled.managedProperty().bind(labeled.visibleProperty());
      labeled.visibleProperty().bind(Bindings.greaterThan(labeled.textProperty().length(), 0));
    }
  }


  public static void setDescription(Labeled labeled, VLViewComponentXML configuration,
      AbstractViewController controller) {
    final String descLabel = configuration.getPropertyValue(XMLConstants.DESCRIPTION);
    if (StringUtils.isNotBlank(descLabel)) {
      final String translated = controller.getLocalised(descLabel, configuration);
      labeled.setText(translated);
    }

    setStyleClass(labeled, configuration, "descriptionStyleClass", true);
  }


  public static void setDescription(Labeled labeled, VLViewComponentXML configuration,
      AbstractViewController controller, boolean bind) {
    setDescription(labeled, configuration, controller);
    if (bind) {
      labeled.managedProperty().bind(labeled.visibleProperty());
      labeled.visibleProperty().bind(Bindings.greaterThan(labeled.textProperty().length(), 0));
    }
  }


  public static void setLabel(Labeled labeled, VLViewComponentXML configuration,
      AbstractViewController controller) {
    // title
    final boolean uppercase = configuration.getBooleanProperty(XMLConstants.UPPERCASE, false);
    final String titleLabel = configuration.getPropertyValue(XMLConstants.LABEL);
    final String translated = controller.getLocalised(titleLabel, configuration);
    labeled.setText(uppercase ? translated.toUpperCase() : translated);

    // tooltip
    final String tooltipLabel = configuration.getPropertyValue(XMLConstants.TOOLTIP);
    final String tooltipLabeltranslated = controller.getLocalised(tooltipLabel);
    labeled.setTooltip(new Tooltip(tooltipLabeltranslated));
  }


  public static void addLabel(TextFlow flow, VLViewComponentXML configuration,
      AbstractViewController controller) {
    final Label labeled = new Label();
    setLabel(labeled, configuration, controller);
    flow.getChildren().add(labeled);
  }


  public static void addDescription(TextFlow flow, VLViewComponentXML configuration,
      AbstractViewController controller) {
    final Text labeled = new Text();
    final String descLabel = configuration.getPropertyValue(XMLConstants.DESCRIPTION);
    if (StringUtils.isNotBlank(descLabel)) {
      final String translated = controller.getLocalised(descLabel, configuration);
      labeled.setText(translated);
    }

    final String style = configuration.getPropertyValue("descriptionStyleClass");
    if (StringUtils.isNotBlank(style)) {
      labeled.getStyleClass().addAll(style.split(","));
    }
    flow.getChildren().add(labeled);
  }


  public static String getTitle(VLViewComponentXML configuration,
      AbstractViewController controller) {
    final String titleLabel = configuration.getPropertyValue(XMLConstants.TITLE);
    final String translated = controller.getLocalised(titleLabel, configuration);
    return translated;
  }


  public static String getLabel(VLViewComponentXML configuration,
      AbstractViewController controller) {
    final String titleLabel = configuration.getPropertyValue(XMLConstants.LABEL);
    final String translated = controller.getLocalised(titleLabel, configuration);
    return translated;
  }


  /*-----------------------------------------------------------------------------
  | FXML LOADER
   *=============================================================================*/
  /**
   * Load a content from an FXML definition
   */
  public static Object loadFXML(URL fxmlLocation, Object controller) {
    Object result = null;
    try {
      Assert.notNull(fxmlLocation);
      Assert.notNull(controller);

      final FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(fxmlLocation);
      fxmlLoader.setController(controller);
      fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

      // System.out.println(">>>> loading FXML on : " + controller + ", from url : " +
      // fxmlLocation);
      result = fxmlLoader.load();
    } catch (final Throwable e) {
      e.printStackTrace();
      throw new IllegalArgumentException(e);
    }

    return result;
  }


  /*-----------------------------------------------------------------------------
  | ANIMATION
   *=============================================================================*/
  public static TranslateTransition translateTo(double x, Node node) {
    final TranslateTransition translate = new TranslateTransition();
    translate.setNode(node);
    translate.setAutoReverse(false);
    translate.setToX(x);
    translate.setDuration(Duration.millis(300));
    return translate;
  }


  public static TranslateTransition translateTo(double fromx, double tox, Node node) {
    return translateTo(fromx, tox, node, null);
  }


  public static TranslateTransition translateTo(double fromx, double tox, Node node,
      Duration millis) {
    final TranslateTransition translate = new TranslateTransition();
    translate.setNode(node);
    translate.setAutoReverse(false);
    translate.setToX(tox);
    translate.setFromX(fromx);
    if (millis != null)
      translate.setDuration(millis);
    if (millis == null)
      translate.setDuration(Duration.millis(300));
    return translate;
  }


  public static TranslateTransition translateYTo(double y, Node node) {
    final TranslateTransition translate = new TranslateTransition();
    translate.setNode(node);
    translate.setAutoReverse(false);
    translate.setToY(y);
    translate.setDuration(Duration.millis(700));
    return translate;
  }


  public static TranslateTransition translateYTo(double fromY, double y, Node node) {
    return translateYTo(fromY, y, node, null);
  }

  public static TranslateTransition translateYTo(double fromY, double y, Node node,
      Duration duration) {
    final TranslateTransition translate = new TranslateTransition();
    translate.setNode(node);
    translate.setAutoReverse(false);
    translate.setFromY(fromY);
    translate.setToY(y);

    if (duration == null)
      translate.setDuration(Duration.millis(400));
    if (duration != null)
      translate.setDuration(duration);

    node.translateYProperty().set(fromY);
    return translate;
  }


  public static ScaleTransition scaleIn(Node node, Duration duration) {
    final ScaleTransition st = new ScaleTransition(duration);
    st.setToX(1);
    st.setToY(1);
    st.setFromX(0.1);
    st.setFromY(0.1);
    st.setNode(node);
    st.setAutoReverse(false);
    return st;
  }


  public static ScaleTransition scaleOut(Node node, Duration duration) {
    final ScaleTransition st = new ScaleTransition(duration);
    st.setToX(0.2);
    st.setToY(0.2);
    st.setNode(node);
    st.setAutoReverse(false);
    return st;
  }


  public static FadeTransition fadeOut(Node node, Duration duration) {
    final FadeTransition ft = new FadeTransition(duration);
    ft.setNode(node);
    ft.setAutoReverse(false);
    ft.setToValue(0);
    ft.setFromValue(1);
    return ft;
  }


  public static FadeTransition fadeIn(Node node, Duration duration) {
    final FadeTransition ft = new FadeTransition(duration);
    ft.setNode(node);
    ft.setAutoReverse(false);
    ft.setToValue(1);
    ft.setFromValue(0);
    return ft;
  }


  public static RotateTransition rotate(Node node, Duration duration) {
    final RotateTransition rt = new RotateTransition(duration);
    rt.setToAngle(360);
    return rt;
  }


  public static HBox wrapInHbox(Node node) {
    final HBox wrapper = new HBox();
    wrapper.getChildren().add(node);
    return wrapper;
  }

  public static StackPane wrapInStackPane(Node node) {
    final StackPane wrapper = new StackPane();
    wrapper.setAlignment(Pos.CENTER);
    wrapper.getChildren().add(node);
    return wrapper;
  }

  /**
   * Add growing pane arround a node to make in centered on view.
   *
   * @param node
   */
  public static Node centerInView(Node node) {
    final StackPane stackPane = new StackPane();

    final VBox verticalLayout = new VBox();
    verticalLayout.setAlignment(Pos.CENTER);
    verticalLayout.getChildren().add(NodeHelper.verticalSpacer());
    verticalLayout.getChildren().addAll(node);
    verticalLayout.getChildren().add(NodeHelper.verticalSpacer());

    final HBox horizontalLayout = new HBox();
    horizontalLayout.setAlignment(Pos.CENTER);
    horizontalLayout.getChildren().add(NodeHelper.horizontalSpacer());
    horizontalLayout.getChildren().add(verticalLayout);
    horizontalLayout.getChildren().add(NodeHelper.horizontalSpacer());

    stackPane.setAlignment(Pos.CENTER);
    stackPane.getChildren().add(horizontalLayout);
    NodeHelper.setHVGrow(stackPane);
    return stackPane;
  }

  public static Node getProcessingIndicator() {
    final StackPane pane = new StackPane();
    final JFXSpinner indicator = new JFXSpinner();
    indicator.setStyle("-fx-max-width:16;-fx-max-height:16;");
    pane.getChildren().add(indicator);
    pane.setAlignment(Pos.CENTER);
    NodeHelper.setHgrow(pane);
    return pane;
  }


  /**
   * Generates a pane with EXCLAMATION_TRIANGLE font awesome icon.
   */
  public static Node getErrorIndicator() {
    final Label errorLabel = (Label) IconUtils.getErrorIcon();
    errorLabel.getStyleClass().add("red-icon");
    final StackPane stackPane = new StackPane();
    stackPane.setAlignment(Pos.CENTER);
    stackPane.getChildren().add(errorLabel);
    return stackPane;
  }


  /**
   * Generates a pane with EXCLAMATION_TRIANGLE font awesome icon.
   */
  public static Node getLoadingIndicator() {
    final ProgressIndicator progressIndicator = new ProgressIndicator();
    progressIndicator.getStyleClass().add("progress-indicator");
    final StackPane stackPane = new StackPane();
    stackPane.setAlignment(Pos.CENTER);
    stackPane.getChildren().add(progressIndicator);
    return stackPane;
  }


  /**
   * Wrap the given node in hbox
   *
   * @param node
   * @return
   */
  public static HBox wrapInHbox(Node node, String... css) {
    final HBox wrapper = new HBox();
    wrapper.getChildren().add(node);
    wrapper.getStyleClass().addAll(css);
    NodeHelper.setHgrow(wrapper);
    return wrapper;
  }


  /**
   * Generates a vbox with border radius corner and white background.
   */
  public static VBox vboxContainer(boolean hgrow, boolean vgrow) {
    final VBox container = new VBox();
    container.setSpacing(10);
    container.getStyleClass().add(ROWS_CONTAINER_CSS);

    if (hgrow) {
      setHgrow(container);
    }

    if (vgrow) {
      setVgrow(container);
    }

    return container;
  }


  public static void setHVGrow(Node... nodes) {
    if (nodes != null) {
      for (final Node node : nodes) {
        if (node != null) {
          setHgrow(node);
          setVgrow(node);
        }
      }
    }
  }


  public static void setVgrow(Node... nodes) {
    if (nodes != null) {
      for (final Node node : nodes) {
        VBox.setVgrow(node, Priority.ALWAYS);
      }
    }

  }


  public static void setHgrow(Node... nodes) {
    if (nodes != null) {
      for (final Node node : nodes) {
        if (node != null) {
          HBox.setHgrow(node, Priority.ALWAYS);
        }
      }
    }
  }


  /**
   * Get the coordinate of a node on the scene
   *
   * @param node
   * @return
   */
  public static Point2D findScreenLocation(Node node) {
    double x = 0;
    double y = 0;

    for (Node n = node; n != null; n = n.getParent()) {
      final Bounds parentBounds = n.getBoundsInParent();
      x += parentBounds.getMinX();
      y += parentBounds.getMinY();
    }

    final Scene scene = node.getScene();
    x += scene.getX();
    y += scene.getY();

    final Window window = scene.getWindow();
    x += window.getX();
    y += window.getY();

    final Point2D screenLoc = new Point2D(x, y);
    return screenLoc;
  }


  /**
   * Generate an horizontal invisible spacer
   *
   * @return
   */
  public static Node horizontalSpacer() {
    final Pane pane = new Pane();
    pane.managedProperty().bind(pane.visibleProperty());
    pane.getStyleClass().add("horizontal-spacer");
    setHgrow(pane);
    return pane;
  }

  /**
   * Generate an horizontal visible spacer
   *
   * @return
   */
  public static Node horizontalVisibleSpacer() {
    final Pane pane = new Pane();
    pane.managedProperty().bind(pane.visibleProperty());
    pane.getStyleClass().add("horizontal-visible-spacer");
    setHgrow(pane);
    return pane;
  }


  /**
   * Generate an horizontal spacer
   *
   * @return
   */
  public static Node verticalSpacer() {
    final Pane pane = new Pane();
    pane.managedProperty().bind(pane.visibleProperty());
    pane.getStyleClass().add("vertical-spacer");
    setVgrow(pane);
    return pane;
  }


  /**
   * Wrap a node into a scrollpane
   *
   * @param node
   * @return
   */
  public static ScrollPane makeScrollable(Node node) {
    final ScrollPane scrollPane = new ScrollPane();

    final StackPane stackPane = new StackPane();
    stackPane.getChildren().add(node);

    final Group scrollPaneContent = new Group(stackPane);
    scrollPaneContent.setAutoSizeChildren(true);
    scrollPane.setContent(scrollPaneContent);

    scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    scrollPane.setFitToHeight(false);
    scrollPane.setFitToWidth(true);
    scrollPane.setFocusTraversable(false);
    scrollPane.setStyle(
        "-fx-background-color:white;-fx-padding:0 0 0 0;" + "-fx-border-color:transparent;");
    return scrollPane;
  }


  /**
   * Wrap a node into a scrollpane, this scrollpane is attended to be in the center of the view.
   * Because a padding of 32 is set in the right side of the scrollpane.
   *
   * @param node
   * @return
   */
  public static ScrollPane makeCentralScrollable(Node node) {
    final ScrollPane scrollPane = new ScrollPane();
    scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    scrollPane.setContent(node);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);
    scrollPane.setFocusTraversable(false);
    scrollPane.setStyle(
        "-fx-background-color: transparent;" + "-fx-padding:0 ;" + "-fx-border-color:transparent;");
    return scrollPane;
  }


  /**
   * @param display
   * @return
   */
  public static HBox wrapInGrowingHbox(Node node) {
    final HBox wrapper = new HBox();
    wrapper.getChildren().add(node);
    NodeHelper.setHgrow(wrapper);
    return wrapper;
  }


  /**
   * @return
   */
  public static Node verticalSeparator() {
    final Separator separator = new Separator();
    separator.setOrientation(Orientation.VERTICAL);
    return separator;
  }


  /**
   * @return
   */
  public static Node headerVerticalSeparator() {
    final HBox box = new HBox();

    box.setStyle("-fx-background-color:white;-fx-opacity:0.70");
    box.minHeightProperty().bind(box.prefHeightProperty());
    box.maxHeightProperty().bind(box.prefHeightProperty());
    box.minWidthProperty().bind(box.prefWidthProperty());
    box.maxWidthProperty().bind(box.prefWidthProperty());
    box.setPrefWidth(2);
    box.setPrefHeight(30);

    // translate the box because the toolbar is top right aligned
    // we can not align the toolbar center, too complex to handle!!!
    box.setTranslateY(-2);

    return box;
  }


  public static String getDisplayName(OperationData data) {
    if (data.getAttributes().get("name") == null) {
      return (String) data.getMasterAttributes().get("name");
    }
    return (String) data.getAttributes().get("name");
  }


  /**
   * Product a {@link PopStructureContentEvent} and a {@link HeaderNavbarBackButtonClicked}
   *
   * @param dispatcher
   */
  public static void goBack(AbstractViewController dispatcher) {
    final PopStructureContentEvent pop = new PopStructureContentEvent();
    dispatcher.dispatchEvent(pop);

    // must inform structure content that back was clicked
    // it may update its toolbar
    final HeaderNavbarBackButtonClicked ev = new HeaderNavbarBackButtonClicked.Builder().build();
    dispatcher.dispatchEvent(ev);
  }

  /**
   * Get a fa-caret-square-o-right.
   *
   * @return
   */
  public static Node getSep() {
    Label separator = new Label();
    FontIcon icon = new FontIcon("fa-caret-square-o-right:14");
    separator.setGraphic(icon);
    icon.getStyleClass().add("ep-structure-location-item-label-ikonli");
    return separator;
  }

  public static Node getSep(String iconStyleClass) {
    Label separator = new Label();
    FontIcon icon = new FontIcon("fa-caret-square-o-right:14");
    separator.setGraphic(icon);
    separator.getStyleClass().add(iconStyleClass);
    return separator;
  }


  public static Node headerMessage(String message, String icon) {
    HBox messagebox = new HBox();
    messagebox.setStyle("-fx-spacing: 16;-fx-alignment:CENTER");

    Label mss = new Label(message);
    mss.getStyleClass().add("ep-header-message");

    Label ic = new Label();
    if (StringUtils.isNotBlank(icon)) {
      IconUtils.setFontIcon(icon, ic);
      ic.getGraphic().getStyleClass().add("white-ikonli");
    }

    messagebox.getChildren().addAll(ic, mss);
    return messagebox;
  }

  public static void showHeaderMessage(IJSoaggerController controller, String message,
      String icon) {
    Node msg = headerMessage(message, icon);
    ((RootStructureController) controller.getRootStructure()).showMessage(msg);
  }

  public static void showHeaderSuccessCreateMessage(IJSoaggerController controller) {
    Node msg = headerMessage("Item successfully created", "gmi-cloud-done:32");
    ((RootStructureController) controller.getRootStructure()).showMessage(msg);
  }

  public static void showHeaderErrorCreateMessage(IJSoaggerController controller) {
    Node msg = headerMessage("Error occurs, object not created.", "gmi-error-outline:32");
    ((RootStructureController) controller.getRootStructure()).showMessage(msg);
  }


  public static void showHeaderSuccessDeleteMessage(IJSoaggerController controller) {
    Node msg = headerMessage("Item(s) successfully deleted", "gmi-delete-sweep:32");
    ((RootStructureController) controller.getRootStructure()).showMessage(msg);
  }

  public static void showHeaderErrorDeleteMessage(IJSoaggerController controller) {
    Node msg = headerMessage("Error occurs, object not deleted.", "gmi-error-outline:32");
    ((RootStructureController) controller.getRootStructure()).showMessage(msg);
  }

  public static void showHeaderErrorMessage(IJSoaggerController controller, String message) {
    Node msg = headerMessage(message, "gmi-error-outline:32");
    ((RootStructureController) controller.getRootStructure()).showMessage(msg);
  }

  public static void showHeaderSuccessMessage(IJSoaggerController controller, String message) {
    Node msg = headerMessage(message, "gmi-cloud-done:32");
    ((RootStructureController) controller.getRootStructure()).showMessage(msg);
  }

  /**
   * Use only when wizard is processing (Finish button)
   *
   * @param c
   */
  public static void showProcessingDialog(WizardViewController c) {
    ProcessingDialog d = new ProcessingDialog.Builder().title("Processing")
        .message("Please wait ...").buildPrimary(c);
    c.setProcessingPane(d);
    d.show(true);
  }


  public static Button jfxButton(String label) {
    Button button = new JFXButton(label);
    button.getStyleClass().remove(0);
    button.getStyleClass().removeAll("jfx-button", "button");
    button.getStyleClass().addAll("ep-button", "button");
    return button;
  }
}
