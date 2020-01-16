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

package io.github.jsoagger.jfxcore.engine.components.list.impl;




import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.contextmenu.EllipsisActionButton;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ModelSearchResultListCell<T> extends AbstractListCell<T> {

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


  /**
   * Default Constructor
   */
  public ModelSearchResultListCell() {
    NodeHelper.loadFXML(ModelSearchResultListCell.class.getResource("ModelSearchResultListCell.fxml"), this);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);
    setGraphic(null);
    setText(null);

    if ((item != null) && !empty) {
      setGraphic(rootContainer);

      rootContainer.maxWidthProperty().bind(getListView().widthProperty());

      AbstractViewController controller = presenter.getController();
      VLViewComponentXML configuration = presenter.getConfiguration();
      OperationData model = (OperationData) item;

      // The icon, may be set to null and build it in presenter
      boolean hasSeticon = false;
      if (presenter.getIconPresenter() != null) {
        Node icon = presenter.getIconPresenter().provideIcon(controller, configuration, model);
        if (icon != null) {
          iconContainer.getChildren().clear();
          iconContainer.getChildren().add(icon);
        }
        hasSeticon = true;
      }
      iconContainer.setVisible(hasSeticon);
      iconContainer.setManaged(hasSeticon);

      // The identity information
      if (presenter.getIdentityPresenter() != null) {
        Node cell = presenter.getIdentityPresenter().provideIdentityOf(controller, configuration, model);
        if (cell != null) {
          mainLabelContainer.getChildren().clear();
          mainLabelContainer.getChildren().add(cell);
        }
      }

      // the secondary label
      if (presenter.getSecondaryLabelPresenter() != null) {
        Node secondaryLabel = presenter.getSecondaryLabelPresenter().provideLabel(controller, configuration, model);
        if (secondaryLabel != null) {
          secondaryLabelContainer.getChildren().clear();
          secondaryLabelContainer.getChildren().add(secondaryLabel);
        }
      } else {
        secondaryLabelContainer.setManaged(false);
      }

      // The ellipsis menu
      if (presenter.getEllipsisMenuPresenter() != null) {
        Node ellipsisMenu = presenter.getEllipsisMenuPresenter().provideEllipsis(controller, configuration, model);
        presenter.setEllipsisMenu((EllipsisActionButton) ellipsisMenu);

        if (ellipsisMenu != null) {
          rightActionsContainer.getChildren().addAll(ellipsisMenu);

          addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            ellipsisMenu.setVisible(true);
          });

          addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            ellipsisMenu.setVisible(false);
          });
        }
      }

      // the context menu
      if (presenter.getContextMenuPresenter() != null) {
        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> onContextMenu(e));
      }

      // build right actions
      VLViewComponentXML rightActions = ComponentUtils.resolveModel(controller, "CellRightActions");
      if (rightActions != null) {
        Node actions = ViewActionFactory.viewActions(controller, rightActions, model);
        rightActionsContainer.getChildren().clear();
        rightActionsContainer.getChildren().add(actions);
      }

      // build more actions
      VLViewComponentXML moreActions = ComponentUtils.resolveModel(controller, "CellMiddleActions");
      if (moreActions != null) {
        Node actions = ViewActionFactory.viewActions(controller, moreActions, model);
        quickActionsContainer.getChildren().clear();
        quickActionsContainer.getChildren().add(actions);
      }
    }

  }


  protected void onContextMenu(MouseEvent event) {
    if (event.getButton() == MouseButton.SECONDARY) {
      EllipsisActionButton contextMenu = (EllipsisActionButton) presenter.getContextMenuPresenter().provideContextMenu(null, null);
      if (contextMenu != null) {
        //        if (presenter.getEllipsisMenu() != null) {
        //          if (presenter.getEllipsisMenu().contextMenu().isShowing()) {
        //            presenter.getEllipsisMenu().contextMenu().hide();
        //          }
        //        }
        //        contextMenu.contextMenu().show(this);
      }
    }
  }


  /**
   * Getter of rootContainer
   *
   * @return the rootContainer
   */
  public Pane getRootContainer() {
    return rootContainer;
  }


  /**
   * Setter of rootContainer
   *
   * @param rootContainer the rootContainer to set
   */
  public void setRootContainer(Pane rootContainer) {
    this.rootContainer = rootContainer;
  }


  /**
   * Getter of iconContainer
   *
   * @return the iconContainer
   */
  public Pane getIconContainer() {
    return iconContainer;
  }


  /**
   * Setter of iconContainer
   *
   * @param iconContainer the iconContainer to set
   */
  public void setIconContainer(Pane iconContainer) {
    this.iconContainer = iconContainer;
  }


  /**
   * Getter of centerContainer
   *
   * @return the centerContainer
   */
  public Pane getCenterContainer() {
    return centerContainer;
  }


  /**
   * Setter of centerContainer
   *
   * @param centerContainer the centerContainer to set
   */
  public void setCenterContainer(Pane centerContainer) {
    this.centerContainer = centerContainer;
  }


  /**
   * Getter of mainLabelContainer
   *
   * @return the mainLabelContainer
   */
  public Pane getMainLabelContainer() {
    return mainLabelContainer;
  }


  /**
   * Setter of mainLabelContainer
   *
   * @param mainLabelContainer the mainLabelContainer to set
   */
  public void setMainLabelContainer(Pane mainLabelContainer) {
    this.mainLabelContainer = mainLabelContainer;
  }


  /**
   * Getter of secondaryLabelContainer
   *
   * @return the secondaryLabelContainer
   */
  public Pane getSecondaryLabelContainer() {
    return secondaryLabelContainer;
  }


  /**
   * Setter of secondaryLabelContainer
   *
   * @param secondaryLabelContainer the secondaryLabelContainer to set
   */
  public void setSecondaryLabelContainer(Pane secondaryLabelContainer) {
    this.secondaryLabelContainer = secondaryLabelContainer;
  }

}
