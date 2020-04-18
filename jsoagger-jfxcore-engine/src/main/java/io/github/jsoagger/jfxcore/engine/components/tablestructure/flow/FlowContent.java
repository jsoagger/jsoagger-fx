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
import java.util.List;
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
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.Node;
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
        // ((TilePane)content).setAlignment(Pos.TOP_LEFT);
        break;
      case GRID:
        content = new TilePane();
        ((TilePane) content).setPrefColumns(2);
        // ((TilePane)content).setAlignment(Pos.TOP_LEFT);
        // ((TilePane)content).setPrefTileWidth(180);
        break;
      case VBOX:
        content = new VBox();
        // ((VBox) content).setStyle("-fx-alignment:TOP_CENTER;-fx-spacing:4");
        break;

      default:
        content = new TilePane();
        break;
    }

    if (content != null) {
      updatetimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(10)));
      if (AbstractApplicationRunner.isMobile()) {
        content.setOnScrollStarted(s -> AbstractApplicationRunner.setApplicationScrolling(true));
        content.setOnScrollFinished(s -> AbstractApplicationRunner.setApplicationScrolling(false));
      }
    }

    final String styleClass = contentConfiguration.getPropertyValue("flowContentStyleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
      content.getStyleClass().addAll(styleClass.split(","));
    }
  }

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
  @SuppressWarnings("unchecked")
  @Override
  public void buildContent() {
    super.buildContent();

    NodeHelper.styleClassAddAll(content, contentConfiguration, "flowContentStyleClass",
        "ep-flow-content-wrapper");
    if (contentConfiguration != null) {
      // extract item factory
      flowItemImpl = contentConfiguration.getPropertyValue("flowItemImpl");
      flowItemResolver = contentConfiguration.getPropertyValue("flowItemResolver");
      if (StringUtils.isBlank(flowItemImpl) && StringUtils.isBlank(flowItemResolver)) {
        throw new IllegalArgumentException("flowItemImpl or flowItemResolver is mandatory");
      }
    }

    getFilteredDatas().addListener((ListChangeListener<OperationData>) c -> {

      if (c.next()) {
        if (c.wasAdded()) {
          if (Platform.isFxApplicationThread()) {
            addItems((List<OperationData>) c.getAddedSubList());
          } else {
            Platform.runLater(() -> {
              addItems((List<OperationData>) c.getAddedSubList());
            });
          }
        } else if (c.wasRemoved()) {
          if (Platform.isFxApplicationThread()) {
            removeItems((List<OperationData>) c.getAddedSubList());
          } else {
            Platform.runLater(() -> {
              removeItems((List<OperationData>) c.getAddedSubList());
            });
          }
        }
      }
    });

    modifyingProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      // f.deselect();
    });

    if (noContentPaneConfiguration != null) {
      alwaysShowNoContentPane =
          noContentPaneConfiguration.getBooleanProperty("alwaysVisible", false);
    }
  }

  private String getFlowItemId() {
    if (StringUtils.isBlank(flowItemResolver)) {
      return flowItemImpl;
    }

    final IFlowItemResolver iFlowItemResolver =
        (IFlowItemResolver) Services.getBean(flowItemResolver);
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
    try {

      content.pseudoClassStateChanged(PseudoClass.getPseudoClass("nocontent"), false);

      final int elementsCount = multipleResult.totaElements();
      this.elementsCount.set(elementsCount);
      currentData = multipleResult;

      final boolean isFirst = multipleResult.getCurrentPageIndex() <= 0;
      if (pagination != null) {
        if (!pagination.getDisplay().visibleProperty().isBound())
          pagination.getDisplay().setVisible(true);
      }

      if (pagination != null && pagination.isLoadMorePagination() && !isFirst) {
        // items.clear();
      } else {
        items.clear();
      }

      if (isFirst) {
        // sometimes node like nocontent pane can be
        // still inside tha pane because threads!!
        // so force and ensure that pane is cleared
        // after new data have been loaded
        removeAllNodesInContentPane();
      }

      items.addAll(multipleResult.getData());
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  int rowIndex = 0;
  int colIndex = 0;


  private void addItems(List<OperationData> items) {

    Iterator<OperationData> it = items.iterator();
    while (it.hasNext()) {
      OperationData c = it.next();

      try {
        final IBuildable d = buildItem(c);

        if (flowMode == FlowMode.GRID) {
          colIndex = colIndex + 1;
          if (colIndex > 1) {
            colIndex = 0;
            rowIndex += 1;
          }

          content.getChildren().add(d.getDisplay());
        } else {
          content.getChildren().add(d.getDisplay());
        }
        if (!it.hasNext()) {
          d.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
        }
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }

    content.requestFocus();
    content.toFront();
  }

  private void removeItems(List<OperationData> items) {

  }

  @Override
  public void removeAllNodesInContentPane() {
    if (Platform.isFxApplicationThread()) {
      content.getChildren().clear();
    } else {
      Platform.runLater(() -> {
        content.getChildren().clear();
      });
    }
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
      if (!pagination.getDisplay().visibleProperty().isBound())
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

    /// content.getChildren().clear();
    // content.getChildren().add(p);
    // p.getChildren().add(NodeHelper.getProcessingIndicator());

    if (pagination != null) {
      if (!pagination.getDisplay().visibleProperty().isBound())
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
