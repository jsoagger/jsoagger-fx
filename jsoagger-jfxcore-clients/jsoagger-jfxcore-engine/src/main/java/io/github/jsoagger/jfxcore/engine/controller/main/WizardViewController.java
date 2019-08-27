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



import java.util.Properties;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.UIDataValidationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.wizard.IWizard;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.dialog.HideDialogEvent;
import io.github.jsoagger.jfxcore.engine.components.dialog.ShowDialogEvent;
import io.github.jsoagger.jfxcore.engine.components.dialog.VLDialog;
import io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler.WizardInitializator;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PopStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Standard controller of wizard.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardViewController extends StandardViewController {

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  protected boolean showAsDialog;
  protected int curStepIdx = -1;
  protected VLViewComponentXML wizardConfiguration;

  protected IWizard wizard;

  /*-----------------------------------------------------------------------------
  | Static fields
   *=============================================================================*/
  public static double WIZARD_DEFAULT_WIDTH;
  public static double WIZARD_DEFAULT_HEIGHT;
  public static final int UNDEFINED = -1;

  /*-----------------------------------------------------------------------------
  | Class fields
   *=============================================================================*/
  protected Stage dialogStage = null;
  protected Properties wizardProperties;
  protected StackPane wizardWrapper = new StackPane();

  protected Double dialogHeight;
  protected Double dialogWidth;
  protected SimpleObjectProperty<OperationData> relativeTo = new SimpleObjectProperty<>();
  private VLDialog processingPane;


  /*-----------------------------------------------------------------------------
  | Constructor
   *=============================================================================*/
  /**
   * @param controller
   */
  public WizardViewController() {
    super();
    wizardProperties = (Properties) Services.getBean("wizardProperties");
    WIZARD_DEFAULT_HEIGHT = Double.valueOf(wizardProperties.getProperty("height"));
    WIZARD_DEFAULT_WIDTH = Double.valueOf(wizardProperties.getProperty("width"));
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return wizard.getDisplay();
    }

    return super.getNodeOnPosition(position);
  }


  /**
   * @return
   */
  public SimpleObjectProperty<OperationData> relativeToProperty() {
    return relativeTo;
  }


  /*----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  public void process() {
    super.process();

    if (getModel() == null) {
      throw new IllegalArgumentException("Model is mandatory for wizard processing, please provide one");
    }
    loadSettings();

    wizardConfiguration = getRootComponent().getComponentById("Wizard").orElse(null);
    if (wizardConfiguration == null) {
      throw new IllegalArgumentException("Wizard wizardConfiguration not found");
    }

    String wizardImpl = wizardConfiguration.getPropertyValue("wizardImpl");
    if (StringUtils.isEmpty(wizardImpl)) {
      wizardImpl = "Wizard";
    }

    wizard = (IWizard) Services.getBean(wizardImpl);
    wizard.buildFrom(this, wizardConfiguration);

    if(isDialog()) {
      wizardWrapper.setStyle("-fx-padding:28;-fx-background-color:transparent;" + "-fx-effect:dropshadow(three-pass-box,derive(black,20%),0.0,0,0,0.0);");
    }

    wizardWrapper.getChildren().clear();
    wizardWrapper.getChildren().add(wizard.getDisplay());
    processedView(wizardWrapper);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean beginForwardEdition(IFormRowEditor simpleForwardEditor) {
    super.beginForwardEdition(simpleForwardEditor);
    wizardWrapper.getChildren().clear();
    wizardWrapper.getChildren().add(simpleForwardEditor.getDisplay());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean endForwardEdition() {
    super.endForwardEdition();
    wizardWrapper.getChildren().clear();
    wizardWrapper.getChildren().add(wizard.getDisplay());
    return true;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void postProcess() {
    super.postProcess();
    String initializator = wizardConfiguration.getPropertyValue("wizardInitilizator");
    if (StringUtils.isNotBlank(initializator)) {
      WizardInitializator bean = (WizardInitializator) Services.getBean(initializator);
      bean.initialize(this);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleValidationResult(IActionRequest actionRequest, IUIDataValidationResult result) {
    super.handleValidationResult(actionRequest, result);

    // forward it to wizard
    wizard.handleValidationResult(actionRequest, result);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleActionResult(IActionRequest actionRequest, IActionResult actionResult) {
    super.handleActionResult(actionRequest, actionResult);

    // the wizard have been successfully processed
    if (actionResult.isSuccess()) {

      if (actionResult.getStatus() == ActionResultStatus.WIZARD_GO_NEXT || actionResult.getStatus() == ActionResultStatus.WIZARD_GO_PREVIOUS) {
        // do nothing
      } else {
        if (isDialog()) {
          hide();
        } else {
          PopStructureContentEvent ev = new PopStructureContentEvent();
          dispatchEvent(ev);
        }
      }
    }
    // error, display the message in the wizard error pane
    else {
      wizard.handleValidationResult(actionRequest, actionResult);
    }
  }


  public void hide() {
    if (dialogStage != null) {
      dispatchEvent(new HideDialogEvent(dialogStage));
      Platform.runLater(() -> {
        SimpleIntegerProperty toto = new SimpleIntegerProperty(50);
        toto.addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
          dialogStage.setWidth(dialogStage.getWidth() * (1 / newValue.doubleValue()));
          dialogStage.setHeight(dialogStage.getHeight() * (1 / newValue.doubleValue()));
        });

        EasingInterpolator interpolator = new EasingInterpolator(EasingMode.SWING_FROM);

        final Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(new Duration(200), new KeyValue(toto, 1, interpolator));
        timeline.getKeyFrames().addAll(kf);
        timeline.setOnFinished(e -> dialogStage.hide());
        timeline.play();
      });
    }
  }


  public void hideProcessingPane() {
    if(processingPane != null) {
      processingPane.hide();
    }
  }


  /**
   * Setter of wizardProperties
   *
   * @param wizardProperties the wizardProperties to set
   */
  public void setWizardProperties(Properties wizardProperties) {
    this.wizardProperties = wizardProperties;
  }


  /**
   * Check if show in dialog or not
   *
   * @return
   */
  public boolean isDialog() {
    return showAsDialog;
  }


  /**
   * Loads properties value from wizardConfiguration file
   */
  public void loadSettings() {
    VLViewComponentXML root = config().getRootComponent();
    wizardConfiguration = root.getComponentById("Wizard").orElse(null);
    showAsDialog = Boolean.valueOf(wizardConfiguration.getPropertyValue(XMLConstants.DIALOG));

    final String width = wizardConfiguration.getPropertyValue(XMLConstants.DIALOG_WIDTH);
    if (!StringUtils.isEmpty(width)) {
      dialogWidth = Double.valueOf(width);
    }

    final String height = wizardConfiguration.getPropertyValue(XMLConstants.DIALOG_HEIGHT);
    if (!StringUtils.isEmpty(height)) {
      dialogHeight = Double.valueOf(height);
    }
  }


  /**
   * Cancel action, hide dialog
   */
  public void cancel() {
    if (dialogStage != null) {
      hide();
    }
  }


  /**
   * @{inheritedDoc}
   */
  public void show() {
    dialogStage = new Stage(StageStyle.TRANSPARENT);
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.setTitle("TODO WIZARD TITLE");
    dialogStage.initOwner(ViewStructure.primaryStage());


    Parent rootScene = (Parent) processedView();
    StackPane dialogWrapper = new StackPane();
    dialogWrapper.getChildren().add(rootScene);

    double w = dialogWidth == null ? WIZARD_DEFAULT_WIDTH : dialogWidth;
    if(!AbstractApplicationRunner.isDesktop()) {
      w = ViewStructure.instance().getScene().getWidth() * 0.90;
    }

    double h = dialogHeight == null ? WIZARD_DEFAULT_HEIGHT : dialogHeight;

    final Scene scene = new Scene(dialogWrapper, w, h, null);
    dialogStage.setScene(scene);


    // hide on escape
    scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        cancel();
      }
    });

    DragResizer.makeResizable((Region) processedView.getChildren().get(0), dialogStage);

    // Desired x coordinate for centered dialog
    final DoubleProperty x = new SimpleDoubleProperty();
    x.bind(ViewStructure.primaryStage().xProperty().add(ViewStructure.primaryStage().widthProperty().subtract(dialogStage.widthProperty()).divide(2)));

    // Desired y coordinate for centered dialog
    final DoubleProperty y = new SimpleDoubleProperty();
    if(AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
      // displays on bottom
      Bounds b = ViewStructure.primaryStage().getScene().getRoot().getBoundsInLocal();
      dialogStage.setY(320);
    }
    else {
      y.bind(ViewStructure.primaryStage().yProperty().add(ViewStructure.primaryStage().heightProperty().subtract(dialogStage.heightProperty()).divide(3)));
    }


    // Update dialog's x coordinate when x defined above changes
    x.addListener((ChangeListener<Number>) (obs, oldValue, newValue) -> dialogStage.setX(newValue.doubleValue()));

    // Update dialog's y coordinate when y defined above changes
    y.addListener((ChangeListener<Number>) (obs, oldValue, newValue) -> dialogStage.setY(newValue.doubleValue()));


    dialogStage.setOpacity(1);
    if (showAsDialog) {
      dialogWrapper.setStyle("-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4); " + "-fx-background-insets: 12; " + "-fx-background-radius: 6;-fx-background-color:white");
      dispatchEvent(new ShowDialogEvent(dialogStage));
      dialogStage.onHidingProperty().addListener(e -> {
        dispatchEvent(new HideDialogEvent(dialogStage));
      });

      // allow the dialog to be dragged around.
      final Delta dragDelta = new Delta();
      rootScene.setOnMousePressed(mouseEvent -> {
        // record a delta distance for the drag and drop operation.
        dragDelta.x = dialogStage.getX() - mouseEvent.getScreenX();
        dragDelta.y = dialogStage.getY() - mouseEvent.getScreenY();
      });
      rootScene.setOnMouseDragged(mouseEvent -> {
        dialogStage.setX(mouseEvent.getScreenX() + dragDelta.x);
        dialogStage.setY(mouseEvent.getScreenY() + dragDelta.y);
      });

      // Unbind x and y when dialog has been shown, to allow user to
      // resize without interference
      dialogStage.setOnShown(event -> {
        x.unbind();
        y.unbind();
      });

      scene.setFill(Color.TRANSPARENT); // Fill our scene with nothing
    }
    // not dilaog show ass forward
    else {

    }

  }


  /**
   * Go to next page without validation.
   */
  public void navTo(int nextStepIdx) {
    if (nextStepIdx < 0 || nextStepIdx > wizard.getStepsSize()) {
      return;
    }
    wizard.select(nextStepIdx);
    curStepIdx = nextStepIdx;
  }


  /**
   * Validate current page and go to next page if it is valid.
   */
  protected IUIDataValidationResult nextPage() {
    final IWizardStep step = wizard.getStep(curStepIdx);
    step.validate();

    final boolean isValid = step.isValid();
    if (isValid) {
      wizard.setValid(curStepIdx);
      if (hasNextPage()) {
        navTo(curStepIdx + 1);
      }
    } else {
      wizard.setError(curStepIdx);
      return UIDataValidationResult.error();
    }

    // recall it to hide errors displayed on previous validation
    step.displayErrors();
    return UIDataValidationResult.success();
  }


  /**
   * Navigates to the previous page of the wizard
   */
  protected IUIDataValidationResult priorPage() {
    navTo(curStepIdx - 1);
    return UIDataValidationResult.success();
  }


  /**
   * Check if has next page
   */
  protected boolean hasNextPage() {
    return curStepIdx < wizard.getStepsSize();
  }


  /**
   * Check if have prior page
   */
  protected boolean hasPriorPage() {
    return curStepIdx > 1;
  }


  /**
   */
  public IUIDataValidationResult next() {
    return nextPage();
  }


  public void back() {
    priorPage();
  }


  /**
   * @return
   */
  public IUIDataValidationResult validateCurrentStep() {
    final IWizardStep step = wizard.getStep(curStepIdx);
    step.validate();

    final boolean isValid = step.isValid();
    step.displayErrors();

    if (!isValid) {
      return UIDataValidationResult.error();
    }
    return UIDataValidationResult.success();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void refreshDatas() {
    super.refreshDatas();
  }


  /**
   * @return
   */
  public IWizardStep getCurrentStep() {
    return wizard.getStep(curStepIdx);
  }

  class Delta {

    double x, y;
  }

  /**
   * {@link DragResizer} can be used to add mouse listeners to a {@link Region} and make it resizable
   * by the user by clicking and dragging the border in the same way as a window.
   * <p>
   * Only height resizing is currently implemented. Usage:
   *
   * <pre>
   * DragResizer.makeResizable(myAnchorPane);
   * </pre>
   *
   */
  public static class DragResizer {

    /**
     * The margin around the control that a user can click in to start resizing the region.
     */
    private static final int RESIZE_MARGIN = 2;

    private final Region region;

    private double y;

    private boolean initMinHeight;

    private boolean dragging;
    private Stage stage;


    private DragResizer(Region aRegion, Stage astage) {
      region = aRegion;
      stage = astage;
    }


    public static void makeResizable(Region region, Stage astage) {
      final DragResizer resizer = new DragResizer(region, astage);

      region.setOnMousePressed(event -> resizer.mousePressed(event));
      region.setOnMouseDragged(event -> resizer.mouseDragged(event));
      region.setOnMouseMoved(event -> resizer.mouseOver(event));
      region.setOnMouseReleased(event -> resizer.mouseReleased(event));
    }


    protected void mouseReleased(MouseEvent event) {
      dragging = false;
      region.setCursor(Cursor.DEFAULT);
    }


    protected void mouseOver(MouseEvent event) {
      if (isInDraggableZone(event) || dragging) {
        region.setCursor(Cursor.S_RESIZE);
      } else {
        region.setCursor(Cursor.DEFAULT);
      }
    }


    protected boolean isInDraggableZone(MouseEvent event) {
      return event.getY() > region.getHeight() - RESIZE_MARGIN;
    }


    protected void mouseDragged(MouseEvent event) {
      if (!dragging) {
        return;
      }

      double mousey = event.getY();
      stage.setHeight(stage.getY() - event.getScreenY() + 20 + stage.getHeight());
      y = mousey;
    }


    protected void mousePressed(MouseEvent event) {

      // ignore clicks outside of the draggable margin
      if (!isInDraggableZone(event)) {
        return;
      }

      dragging = true;

      // make sure that the minimum height is set to the current height
      // once,
      // setting a min height that is smaller than the current height will
      // have no effect
      if (!initMinHeight) {
        region.setMinHeight(region.getHeight());
        initMinHeight = true;
      }

      y = event.getY();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getModelContainerFullId() {
    String id = super.getModelContainerFullId();
    if(StringUtils.isEmpty(id)) {
      if(getParent() != null) {
        id = getParent().getModelContainerFullId();
      }
      else {
        id = getRootStructure().getModelContainerFullId();
      }
    }
    return super.getModelContainerFullId();
  }


  /**
   * Reference to a processing pane associted to this pane.
   *
   * @param d
   */
  public void setProcessingPane(VLDialog d) {
    this.processingPane = d;
  }
}
