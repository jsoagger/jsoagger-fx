/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.comps;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionRequest;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.list.impl.DefaultModelListCell;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 *
 */
public class DemoStaticModelFlowCell extends MultiPresenterFactory {

  @FXML
  private Pane rootContainer;
  @FXML
  private Pane iconContainer;
  @FXML
  private Pane centerContainer;
  @FXML
  private Pane mainLabelContainer;
  @FXML
  private Pane secondaryLabelContainer;
  @FXML
  private Pane quickActionsContainer;
  @FXML
  private Pane rightActionsContainer;
  @FXML
  private Pane ternaryLabelContainer;

  private boolean isLoadingChild = false;

  /**
   * Default Constructor
   */
  public DemoStaticModelFlowCell() {
    NodeHelper.loadFXML(DefaultModelListCell.class.getResource("DefaultModelListCell.fxml"), this);
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    // The icon, may be set to null and build it in presenter
    if (iconPresenter != null) {
      Node icon = iconPresenter.provideIcon(controller, configuration, forData);
      if (icon != null) {
        iconContainer.getChildren().add(icon);
      }
    }

    // The identity information
    if (identityPresenter != null) {
      Node cell = identityPresenter.provideIdentityOf(controller, configuration, forData);
      if (cell != null) {
        if (StringUtils.isNotBlank(extraParameters.get("mainLabelStyleClass"))) {
          cell.getStyleClass().addAll(extraParameters.get("mainLabelStyleClass").split(","));
        }
        mainLabelContainer.getChildren().addAll(cell);
      }
    }

    Label goRight = new Label();
    IconUtils.setFontIcon("fa-angle-right:20", goRight);
    rightActionsContainer.getChildren().addAll(goRight);

    rootContainer.getStyleClass().add("hand-hover");
    rootContainer.setStyle("-fx-padding:4 16 4 0;-fx-border-width:0 0 1 0;-fx-border-color:-internal-border-color");

    // build row click handler
    VLViewComponentXML rowClickHandler = tableConfig.getComponentById("RowClickHandler").orElse(null);
    if(rowClickHandler != null) {
      rootContainer.getStyleClass().add("hand-hover");
      rootContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onClick(e, rowClickHandler));
    }
  }

  protected void onClick(MouseEvent event, VLViewComponentXML rowClickHandler) {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ae -> isLoadingChild = false));

    // ROW HAVE BEEN TOUCHED
    if(AbstractApplicationRunner.isMobile()) {
      isLoadingChild = true;
      timeline.play();
      _doRowClickAction(rowClickHandler);
    }
    // ROW CLICKED
    else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !isLoadingChild) {
      isLoadingChild = true;
      timeline.play();
      _doRowClickAction(rowClickHandler);
    }
  }

  private void _doRowClickAction(VLViewComponentXML rowClickHandler) {
    IAction action = (IAction) Services.getBean(rowClickHandler.getPropertyValue("action"));
    if(action != null) {
      IActionRequest actionRequest = new ActionRequest.Builder().controller(controller)
          .args(rowClickHandler.getPropertyValue("args"))
          .build();
      actionRequest.setProperty("source", this);
      actionRequest.setProperty("sourceData", getForData());
      action.setData(getForData());
      action.execute(actionRequest, null);
    }
  }

  @Override
  public Node getDisplay() {
    return rootContainer;
  }
}
