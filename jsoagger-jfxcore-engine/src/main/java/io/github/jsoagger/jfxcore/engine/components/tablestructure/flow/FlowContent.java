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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.flow;




import java.util.Iterator;
import java.util.Optional;

import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ICountableElements;
import io.github.jsoagger.jfxcore.api.IFlowItemResolver;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.events.ScrollReachTopScreenEvent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FlowContent extends SingleTableStructure implements IBuildable, ICountableElements {

  /*-----------------------------------------------------------------------------
  | static fields
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  private String flowItemResolver;
  private String flowItemImpl;
  private MultipleResult currentData;

  private Pane content = new FlowPane();
  private FlowMode flowMode = FlowMode.FLOWPANE;

  protected boolean alwaysShowNoContentPane = false;
  Timeline updatetimeline = new Timeline();

  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  /**
   * Constructor
   */
  public FlowContent() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    flowMode = FlowMode.from(contentConfiguration.getPropertyValue("flowMode"));
    switch (flowMode) {
      case FLOWPANE:
        content = new FlowPane();
        break;
      case TILEPANE:
        content = new TilePane();
        //((TilePane)content).setAlignment(Pos.TOP_LEFT);
        break;
      case GRID:
          content = new TilePane();
          ((TilePane)content).setPrefColumns(2);
          //((TilePane)content).setAlignment(Pos.TOP_LEFT);
          //((TilePane)content).setPrefTileWidth(180);
          break;
      case VBOX:
        content = new VBox();
        //((VBox) content).setStyle("-fx-alignment:TOP_CENTER;-fx-spacing:4");
        break;

      default:
    	  content = new TilePane();
        break;
    }

    if(content != null) {
    	updatetimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(10)));
    	content.setOnScroll(new EventHandler<ScrollEvent>() {
    		// deltaY > 0 means going up
    		//

			@Override
			public void handle(ScrollEvent event) {
//				System.out.println("-------------------");
//				System.out.println("getVvalue : " + scrollPane.getVvalue());
//				System.out.println("getHvalue : " + scrollPane.getHvalue());
//				System.out.println("getDeltaX : " + event.getDeltaX());
//				System.out.println("getDeltaY : " + event.getDeltaY());
//				System.out.println("getTotalDeltaY : " + event.getTotalDeltaY());
//				System.out.println("getScreenX : " + event.getScreenX());
//				System.out.println("getScreenY : " + event.getScreenY());
//				System.out.println("getX : " + event.getX());
//				System.out.println("getY : " + event.getY());
			}
		});
      // HANDLE MOBILE SCROLL ON TOUCH EVENT
      if(AbstractApplicationRunner.isMobile()) {
        content.setOnScrollStarted(s -> AbstractApplicationRunner.setApplicationScrolling(true));
        content.setOnScrollFinished(s-> AbstractApplicationRunner.setApplicationScrolling(false));
      }
    }

    // extract css from configuration
    final String styleClass = contentConfiguration.getPropertyValue("flowContentStyleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
      content.getStyleClass().addAll(styleClass.split(","));
    }
  }

  private void manageUpdateData() {
	  if(updatetimeline.getStatus() == Status.STOPPED) {
		  updatetimeline.playFromStart();

		  ScrollReachTopScreenEvent event = new ScrollReachTopScreenEvent();
		  event.setSourceController(controller);
		  controller.dispatchEvent(event);
	  }
  }


  /**
   * Result must be identifiable in order to able to be filtered.
   *
   * @param data
   * @return
   */
  private IBuildable buildItem(OperationData data) {
    flowItemImpl = getFlowItemId();

    final MultiPresenterFactory item = (MultiPresenterFactory) Services.getBean(flowItemImpl);
    item.setForData(data);
    item.setTableConfig(rootConfiguration);
    item.buildFrom(controller, contentConfiguration);

    return item;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildContent() {
    super.buildContent();

    NodeHelper.styleClassAddAll(content, contentConfiguration, "flowContentStyleClass", "ep-flow-content-wrapper");
    if (contentConfiguration != null) {
      // extract item factory
      flowItemImpl = contentConfiguration.getPropertyValue("flowItemImpl");
      flowItemResolver = contentConfiguration.getPropertyValue("flowItemResolver");
      if (StringUtils.isBlank(flowItemImpl) && StringUtils.isBlank(flowItemResolver)) {
        throw new IllegalArgumentException("flowItemImpl or flowItemResolver is mandatory");
      }
    }

    getFilteredDatas().addListener((ListChangeListener<OperationData>) c -> {
      buildItems();
    });
    modifyingProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      // f.deselect();
    });

    if(noContentPaneConfiguration != null) {
      alwaysShowNoContentPane = noContentPaneConfiguration.getBooleanProperty("alwaysVisible", false);
    }
  }

  private String getFlowItemId() {
    if (StringUtils.isBlank(flowItemResolver)) {
      return flowItemImpl;
    }

    final IFlowItemResolver iFlowItemResolver = (IFlowItemResolver) Services.getBean(flowItemResolver);
    return iFlowItemResolver.getFlowItem(controller, this);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Optional<Node> getPagination() {
    // return empty because the pagination for flow content is not
    // a fixed component
    return super.getPagination();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void redisplayCurrentDatas() {
    super.redisplayCurrentDatas();
    if (currentData != null)
      setData(currentData);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setData(MultipleResult multipleResult) {

	  content.pseudoClassStateChanged(PseudoClass.getPseudoClass("nocontent"), false);

    final int elementsCount = multipleResult.totaElements();
    this.elementsCount.set(elementsCount);
    currentData = multipleResult;

    final boolean isFirst = multipleResult.getCurrentPageIndex() <= 0;
    if (pagination != null) {
    	if(!pagination.getDisplay().visibleProperty().isBound())
    		pagination.getDisplay().setVisible(true);
    }

    if (pagination != null && pagination.isLoadMorePagination() && !isFirst) {
      // items.clear();
    } else {
      items.clear();
    }

    items.addAll(multipleResult.getData());
  }

  int rowIndex = 0;
  int colIndex = 0;

  private void buildItems() {
	 /* if(flowMode == FlowMode.GRID) {
		    ColumnConstraints col1 = new ColumnConstraints();
		    col1.setPercentWidth(48);

		    ColumnConstraints col2 = new ColumnConstraints();
		    col1.setPercentWidth(48);

		    ((GridPane)content).getColumnConstraints().addAll(col1,col2);
	    }*/


    Platform.runLater(()-> {
	    //content.getChildren().clear();
	    Iterator<OperationData> it = getFilteredDatas().iterator();

	    while(it.hasNext()) {
	      OperationData c = it.next();
	      final IBuildable d = buildItem(c);

	      if(flowMode == FlowMode.GRID) {
	    	 // ((GridPane)content).add(d.getDisplay(), colIndex, rowIndex);

		      colIndex = colIndex + 1;
		      if(colIndex > 1) {
		    	  colIndex = 0;
		    	  rowIndex += 1;
		      }
		      content.getChildren().add(d.getDisplay());
	      }
	      else {
	    	  content.getChildren().add(d.getDisplay());
	      }

	      if(!it.hasNext()) {
	        d.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
	      }
	    }

	    if(alwaysShowNoContentPane) {
	      //content.getChildren().add(noContentPane.getDisplay());
	    }
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setNoContent() {
    if (Platform.isFxApplicationThread()) {
      _doSetNoContent();
    } else {
      Platform.runLater(() -> {
        _doSetNoContent();
      });
    }
  }


  private void _doSetNoContent() {
	content.pseudoClassStateChanged(PseudoClass.getPseudoClass("nocontent"), true);
    content.getChildren().clear();
    if (noContentPane != null) {
      content.getChildren().clear();
      content.getChildren().add(noContentPane.getDisplay());
    }

    if (pagination != null) {
     if(!pagination.getDisplay().visibleProperty().isBound())
    	 pagination.getDisplay().setVisible(false);
      pagination.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("nodata"), true);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setLoading() {
    if (Platform.isFxApplicationThread()) {
      _doSetLoading();
    } else {
      Platform.runLater(() -> {
        _doSetLoading();
      });
    }
  }


  private void _doSetLoading() {
    StackPane p = new StackPane();
    p.setId("LoadingPane");
    p.setStyle("-fx-background-color:white;-fx-alignment:CENTER");
    NodeHelper.setHVGrow(p);
    //content.getChildren().clear();
    //content.getChildren().add(p);
    //p.getChildren().add(NodeHelper.getProcessingIndicator());
    if (pagination != null) {
    	if(!pagination.getDisplay().visibleProperty().isBound())
    		pagination.getDisplay().setVisible(false);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getTableStructure() {
	  return content;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return new StackPane();
  }


  public String getFlowItemResolver() {
    return flowItemResolver;
  }


  public String getFlowItemImpl() {
    return flowItemImpl;
  }


  public MultipleResult getCurrentData() {
    return currentData;
  }

  private enum FlowMode {
    VBOX, FLOWPANE, TILEPANE, GRID;
    public static FlowMode from(String v) {

      if (io.github.jsoagger.core.utils.StringUtils.isBlank(v)) {
        return FLOWPANE;
      }
      if ("vbox".equalsIgnoreCase(v)) {
        return FlowMode.VBOX;
      }
      if ("grid".equalsIgnoreCase(v)) {
          return FlowMode.GRID;
        }
      if ("tilepane".equalsIgnoreCase(v)) {
        return FlowMode.TILEPANE;
      }

      return FLOWPANE;
    }
  }

  /**
   * @return the content
   */
  public Pane getContent() {
    return content;
  }
}
