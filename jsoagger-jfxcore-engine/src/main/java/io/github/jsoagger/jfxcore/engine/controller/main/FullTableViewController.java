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


import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentLayoutManager;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller to use when the listview or flow content is displayed inside
 * {@link FullFlowContentLayoutManager}
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class FullTableViewController extends FullTableStructureController {

  private TableView tableView;
  private FullTableEditorPane editorPane;


  /**
   * Default Constructor
   */
  public FullTableViewController() {
    editorPane = new FullTableEditorPane();
    registerListener(CoreEvent.RefreshTableCurrentPageEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.RefreshTableCurrentPageEvent)) {
      refreshDatas();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void postProcess() {
    super.postProcess();
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    if (table instanceof TableContent) {
      ((TableContent) table).setResponsiveColumnsMatrix(tableResponsiveMatrix);
    }

    if (table.getTableStructure() instanceof TableView) {
      tableView = (TableView) table.getTableStructure();
      tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      tableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> selectedElementChangedTo((OperationData) newValue));
    }

    model.addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
      forceLoadFirstPage();
      modelChanged();
    });
  }


  public TableView getTableView() {
    return tableView;
  }


  private void modelChanged() {
    if (headerPresenter != null) {
      // TODO reload configuration from AbstractTableStructure
      headerPresenter.buildFrom(this, null);
      setHeader(headerPresenter.getDisplay());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();

    if (position == ViewLayoutPosition.TOP) {
      if (table.getHeader() != null || table.getToolbar().isPresent()) {

        // if ((table.getHeader() != null) &&
        // table.getToolbar().isPresent()) {
        // HBox header = new HBox();
        // header.getStyleClass().add("ep-table-header-mn-wrapper");
        // header.getStyleClass().addAll(table.getToolbarConfiguration().getPropertyValue("toolbarStyleClass").split(","));
        // header.setAlignment(Pos.CENTER);
        // header.getChildren().addAll(table.getHeader().getDisplay(),
        // table.getToolbar().get());
        // return header;
        // }
        //
        // else if (table.getToolbar().isPresent()) {
        // return table.getToolbar().get();
        // }

        return table.getHeader().getDisplay();
      }
    }

    if (position == ViewLayoutPosition.BOTTOM) {
      if (table.getPagination().isPresent()) {
        return table.getPagination().get();
      }
    }

    if (position == ViewLayoutPosition.CENTER) {
      return table.getTableStructure();
    }

    return super.getNodeOnPosition(position);
  }


  /**
   * Get the current selected element in underlaying table.
   */
  public OperationData getSelectedModel() {
    return (OperationData) tableView.getSelectionModel().selectedItemProperty().get();
  }


  public void selectFirstItem() {
    if (tableView != null) {
      tableView.getSelectionModel().clearAndSelect(0);
    }
  }


  @Override
  public void pushContent(StandardViewController c, Node node) {
    if (Platform.isFxApplicationThread()) {
      editorPane.beginEdition(node);
      layoutManager.pushContent(editorPane);
    } else {
      Platform.runLater(() -> {
        editorPane.beginEdition(node);
        layoutManager.pushContent(editorPane);
      });
    }
  }


  public void endEdition() {
    if (Platform.isFxApplicationThread()) {
      editorPane.endEdition();
      layoutManager.popContent();
    } else {
      Platform.runLater(() -> {
        editorPane.endEdition();
        layoutManager.popContent();
      });
    }
  }


  /**
   * Getter of tableResponsiveMatrix
   *
   * @return the tableResponsiveMatrix
   */
  @Override
  public TableResponsiveMatrix getTableResponsiveMatrix() {
    return tableResponsiveMatrix;
  }


  /**
   * Setter of tableResponsiveMatrix
   *
   * @param tableResponsiveMatrix the tableResponsiveMatrix to set
   */
  @Override
  public void setTableResponsiveMatrix(TableResponsiveMatrix tableResponsiveMatrix) {
    this.tableResponsiveMatrix = tableResponsiveMatrix;
  }

  /**
   *
   * @author Ramilafananana Vonjisoa
   * @mailTo yvonjisoa@nexitia.com
   * @date 27 févr. 2018
   */
  public class FullTableEditorPane extends AnchorPane {

    private Button closeAction = new Button();
    private Button minimizeAction = new Button();


    /**
     * Default Constructor
     */
    public FullTableEditorPane() {
      getStyleClass().add("ep-full-table-editor-pane");
      // header.getStyleClass().add("ep-full-table-editor-pane-header");

      IconUtils.setFontIcon("fa-rotate-left:16", closeAction);
      closeAction.addEventFilter(ActionEvent.ACTION, this::popContent);
      closeAction.getStyleClass().add("button-transparent");

      IconUtils.setFontIcon("fa-angle-double-down:22", minimizeAction);
      minimizeAction.addEventFilter(ActionEvent.ACTION, this::minimizePushedContent);
      minimizeAction.getStyleClass().add("button-transparent");
    }


    public void minimizePushedContent(ActionEvent e) {
      layoutManager.minimizePushedContent();
    }


    public void popContent(ActionEvent e) {
      layoutManager.popContent();
    }


    /**
     * Will replace the full table content in the layout manager with this content. The use can cancel
     * it and this pane will be hidden and replaced by the table.
     */
    public void beginEdition(Node node) {
      getChildren().clear();
      getChildren().addAll(node, minimizeAction);
      NodeHelper.setVgrow(node);

      // AnchorPane.setTopAnchor(closeAction, 5.0);
      // AnchorPane.setRightAnchor(closeAction, 30.0);

      AnchorPane.setTopAnchor(minimizeAction, 5.0);
      AnchorPane.setRightAnchor(minimizeAction, 30.0);

      AnchorPane.setTopAnchor(node, 0.0);
      AnchorPane.setBottomAnchor(node, 0.0);
      AnchorPane.setLeftAnchor(node, 0.0);
      AnchorPane.setRightAnchor(node, 0.0);
    }


    public void endEdition() {
      getChildren().clear();
    }
  }


  /**
   * @param roleB
   * @return
   */
  @Override
  public boolean containsModel(OperationData model) {
    boolean contains = false;
    AbstractTableStructure ts = (AbstractTableStructure) processedElement();
    if(ts != null && !ts.getItems().isEmpty()) {
      for (Object o : ts.getItems()) {
        OperationData m = (OperationData) o;
        if (m != null && m.fullIdEquals(model)) {
          contains = true;
          break;
        }
      }
    }

    return contains;
  }


  /**
   * Getter of headerPresenter
   *
   * @return the headerPresenter
   */
  @Override
  public IBuildable getHeaderPresenter() {
    return headerPresenter;
  }


  /**
   * Setter of headerPresenter
   *
   * @param headerPresenter the headerPresenter to set
   */
  @Override
  public void setHeaderPresenter(IBuildable headerPresenter) {
    this.headerPresenter = headerPresenter;
  }
}
