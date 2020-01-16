/**
 *
 */
package io.github.jsoagger.jfxcore.engine.controller.main.layout;

import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import javafx.application.Application.Parameters;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Can be used in mobile. Each tab is equaly sized and grow to fill its parent.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class BottomTabPaneViewStructure extends ViewStructure   {

  private StackPane pane = new StackPane();
  private StackPane content = new StackPane();

  /**
   * Constructor
   */
  public BottomTabPaneViewStructure() {
    super();

    pane.getChildren().add(content);
    //pane.setStyle("-fx-border-width:1;-fx-border-color:blue;-fx-alignment:CENTER;");
    pane.setId("BottomTabPaneViewStructure-Pane");

    content.maxWidthProperty().bind(pane.widthProperty());
    content.setId("BottomTabPaneViewStructure-Content");
    //content.setStyle("-fx-background-color:red");

    pane.widthProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        platformSceneWidth().set(newValue.doubleValue());
      }});
  }


  @Override
  public Parent getParentNode() {
    return getRootViewStructure();
  }


  @Override
  public void init() {
    super.init();
  }

  @Override
  public void displayInSecondaryWR(Pane node) {
    super.displayInSecondaryWR(node);
    //secondaryContent.setMaxWidth(300);
    //secondaryContent.setPrefWidth(300);
    content.getChildren().add(secondaryContent);
    //node.prefWidthProperty().bind(content.widthProperty());
  }

  @Override
  public void hideSecondaryRS() {
    super.hideSecondaryRS();
    content.getChildren().remove(secondaryContent);
  }

  @Override
  public void initFromPrimaryStage(Stage stage, Parameters parameters) {
    super.initFromPrimaryStage(stage, parameters);
    initRefreshCTRPlusRListener();
  }

  @Override
  public Pane getRootViewStructure() {
    return pane;
  }

  @Override
  public Pane getRootViewStructureHeaderArea() {
    return null;
  }

  @Override
  public Pane getRootViewStructureContentArea() {
    return content;
  }

  @Override
  public void selectTab(int tabIndex) {
  }

  @Override
  public void removeTab(String tabId) {
  }

  @Override
  public void removeTab(int tabIndex) {
  }

  @Override
  public void selectTab(String tabId) {
  }

  @Override
  public void add(RootStructureController rootStructure) {
  }

  @Override
  public void remove(RootStructureController rootStructure) {

  }

  @Override
  public void closeAllTabs() {

  }

  @Override
  public ObjectProperty<ViewStructureStatus> statusProperty() {
    return null;
  }

  @Override
  public ViewStructureStatus getStatus() {
    return null;
  }

  @Override
  public void setStatus(ViewStructureStatus status) {
  }
}
