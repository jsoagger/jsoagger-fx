/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.comps;

import java.util.List;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.list.impl.DefaultModelListCell;
import io.github.jsoagger.jfxcore.engine.components.presenter.CellPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 *
 */
public class DemoStaticParentItemFlowCell extends MultiPresenterFactory implements EventHandler<MouseEvent>{

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
  private HBox display = new HBox();
  CellPresenterFactory presenterf = null;

  /**
   * Default Constructor
   */
  public DemoStaticParentItemFlowCell() {
    NodeHelper.loadFXML(DefaultModelListCell.class.getResource("DefaultModelListCell.fxml"), this);
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    iconContainer.setManaged(false);
    iconContainer.setVisible(false);
    // The identity information
    if (identityPresenter != null) {
      Node cell = identityPresenter.provideIdentityOf(controller, configuration, forData);
      if (cell != null) {
        if (StringUtils.isNotBlank(extraParameters.get("mainLabelStyleClass"))) {
          cell.getStyleClass().addAll(extraParameters.get("mainLabelStyleClass").split(","));
        }
        mainLabelContainer.getChildren().add(cell);
      }
    }

    OperationData data =  getForData();
    String presenter = (String) data.getAttributes().get("presenter");
    if(StringUtils.isNotBlank(presenter)) {
      presenterf= (CellPresenterFactory) Services.getBean(presenter);
      presenterf.setParameters(parameters);
      presenterf.setConfiguration(getConfiguration());
      presenterf.setController(getController());
      presenterf.setForData(getForData());
      presenterf.buildFrom(getController(), getConfiguration());
    }
    else {

      Label goRight = new Label();
      IconUtils.setFontIcon("fa-angle-right:20", goRight);
      rightActionsContainer.getChildren().add(goRight);

      rootContainer.getStyleClass().add("hand-hover");
      rootContainer.addEventFilter(MouseEvent.MOUSE_CLICKED, this);

      rootContainer.setStyle("-fx-padding:8 0 8 0;-fx-border-width:0 0 1 0;-fx-border-color:-internal-border-color");
    }
  }

  @Override
  public void handle(MouseEvent event) {
    // avoid multiple touch loading same view multiple times!!
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ae -> isLoadingChild = false));

    if(AbstractApplicationRunner.isMobile()) {
      if(!AbstractApplicationRunner.isApplicationScrolling() && !isLoadingChild && event.getClickCount() == 1) {
        isLoadingChild = true;
        timeline.play();

        PIPDoClickTask clickTask = new PIPDoClickTask(event);
        new Thread(clickTask).start();
      }
    }
    else {
      if (event.getClickCount() == 2) {
        PIPDoClickTask clickTask = new PIPDoClickTask(event);
        new Thread(clickTask).start();
      }
    }
  }

  @Override
  public Node getDisplay() {
    if(presenterf !=null ) return presenterf.getDisplay();
    return rootContainer;
  }

  @SuppressWarnings("unchecked")
  private void _doClickOrTouch(MouseEvent event) {
    List<VLViewComponentXML> sub = (List<VLViewComponentXML>) getForData().getMeta().get("subComponents");
    boolean hassub = false;
    if (sub != null) {

      for (VLViewComponentXML s : sub) {
        if (s.getId().equals("Handler")) {
          hassub = true;
          getForData().getMeta().put("parentItem", this);
          ComponentToButtonBaseHelper.setButtonActions(controller, s, null, event, getForData());
        }
      }
    }

    if (!hassub) {
      // forward to empty page
      PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId("DemoEmptyPrefenceView").model(getForData()).build();
      controller.dispatchEvent(ev);
    }
  }

  /**
   *
   * @author Ramilafananana VONJISOA
   *
   */
  private class PIPDoClickTask extends Task<Void>{

    MouseEvent e;

    public PIPDoClickTask(MouseEvent e) {
      this.e = e;
    }

    @Override
    protected Void call() throws Exception {
      _doClickOrTouch(e);
      return null;
    }

    @Override
    protected void setException(Throwable t) {
      super.setException(t);
      t.printStackTrace();
    }
  }
}
