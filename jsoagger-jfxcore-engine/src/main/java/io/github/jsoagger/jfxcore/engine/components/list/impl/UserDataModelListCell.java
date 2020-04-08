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
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * List cell where the item presented is not {@link OperationData} but the userData associated to
 * the cell.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 28 janv. 2018
 */
public class UserDataModelListCell<T> extends AbstractListCell<T> {

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
  private Pane rightActionsContainer;

  private HBox rootContainer2;

  /**
   * Default Constructor
   */
  public UserDataModelListCell() {
    try {
      NodeHelper.loadFXML(DefaultModelListCell.class.getResource("UserDataModelListCell.fxml"),
          this);
    } catch (Exception e) {
    }

    if (rootContainer == null) {
      rootContainer = new StackPane();
      rootContainer2 = new HBox();
      iconContainer = new HBox();
      centerContainer = new VBox();
      mainLabelContainer = new HBox();
      secondaryLabelContainer = new HBox();
      rightActionsContainer = new HBox();

      rootContainer.getChildren().add(rootContainer2);
      rootContainer2.getChildren().addAll(iconContainer, centerContainer, rightActionsContainer);
      centerContainer.getChildren().addAll(mainLabelContainer, secondaryLabelContainer);

      NodeHelper.setHgrow(centerContainer);

      rootContainer.getStyleClass().add("default-list-cellpresenter-external-container");
      iconContainer.getStyleClass().add("default-list-cellpresenter-icon-container");
      centerContainer.getStyleClass().add("default-list-cellpresenter-center-container");
      mainLabelContainer.getStyleClass().add("default-list-cellpresenter-primarylabel-container");
      secondaryLabelContainer.getStyleClass()
          .add("default-list-cellpresenter-secondarylabel-container");
      rightActionsContainer.getStyleClass()
          .add("default-list-cellpresenter-rightactions-container");
    }
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

      AbstractViewController controller = presenter.getController();
      VLViewComponentXML configuration = presenter.getConfiguration();
      Object userData = getUserData();

      // The icon, may be set to null and build it in presenter
      if (presenter.getIconPresenter() != null) {
        Node icon = presenter.getIconPresenter().provideIcon(controller, configuration, userData);
        if (icon != null) {
          iconContainer.getChildren().clear();
          iconContainer.getChildren().add(icon);
        }
      }

      // The identity information
      if (presenter.getIconPresenter() != null) {
        Node cell =
            presenter.getIdentityPresenter().provideIdentityOf(controller, configuration, userData);
        if (cell != null) {
          mainLabelContainer.getChildren().clear();
          mainLabelContainer.getChildren().add(cell);
        }
      }

      // the secondary label
      if (presenter.getSecondaryLabelPresenter() != null) {
        Node secondaryLabel = presenter.getSecondaryLabelPresenter().provideLabel(controller,
            configuration, userData);
        if (secondaryLabel != null) {
          secondaryLabelContainer.getChildren().clear();
          secondaryLabelContainer.getChildren().add(secondaryLabel);
        }
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
   * Getter of rightActionsContainer
   *
   * @return the rightActionsContainer
   */
  public Pane getRightActionsContainer() {
    return rightActionsContainer;
  }


  /**
   * Setter of rightActionsContainer
   *
   * @param rightActionsContainer the rightActionsContainer to set
   */
  public void setRightActionsContainer(Pane rightActionsContainer) {
    this.rightActionsContainer = rightActionsContainer;
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
