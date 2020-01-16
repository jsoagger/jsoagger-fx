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

package io.github.jsoagger.jfxcore.engine.components.presenter;



import java.util.HashMap;
import java.util.Map;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelContextMenuPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelEllipsisMenuPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelHeaderIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.components.contextmenu.EllipsisActionButton;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Agragation of presenter used for example for cells that need multiple presenter to diplay data in
 * one Cell.
 * <p>
 * Used for example to provide multiple data {@link ListCell}
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class MultiPresenterFactory extends CellPresenterFactory implements IBuildable {

  protected SimpleBooleanProperty selectable = new SimpleBooleanProperty(false);
  protected CheckBox selection = new CheckBox();

  protected OperationData forData;
  protected Map<String, String> extraParameters = new HashMap<>();

  protected ModelIconPresenter iconPresenter;
  protected ModelIdentityPresenter identityPresenter;
  protected ModelHeaderIdentityPresenter headerIdentityProvider;
  protected ModelContextMenuPresenter contextMenuPresenter;
  protected ModelEllipsisMenuPresenter ellipsisMenuPresenter;
  protected ModelSecondaryLabelPresenter secondaryLabelPresenter;

  protected EllipsisActionButton ellipsisMenu;
  protected VLViewComponentXML tableConfig;

  String mainLabelStyles = null;
  String descriptionLabelStyles = null;

  VLViewComponentXML iconClickHandler = null;
  boolean isLoadingChild;

  @FXML
  private Pane selectionContainer;


  /**
   * Default Constructor
   */
  public MultiPresenterFactory() {
    super();
  }



  public void init() {
    selectableProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      if (newValue) {
        Pane pane = getSelectionPaneWrapper();
        if (pane != null) {
          pane.managedProperty().bind(pane.visibleProperty());
          pane.getChildren().add(0, selection);
        }
      } else {
        Pane pane = getSelectionPaneWrapper();
        if (pane != null) {
          if (pane.getChildren().get(0) instanceof CheckBox) {
            pane.getChildren().remove(selection);
          }
        }
      }
    });
  }


  public CheckBox getSelection() {
    return selection;
  }


  /**
   * Getter of extraParameters
   *
   * @return the extraParameters
   */
  public Map<String, String> getExtraParameters() {
    return extraParameters;
  }


  /**
   * Setter of extraParameters
   *
   * @param extraParameters the extraParameters to set
   */
  public void setExtraParameters(Map<String, String> extraParameters) {
    this.extraParameters = extraParameters;
  }


  /**
   * Getter of iconPresenter
   *
   * @return the iconPresenter
   */
  @Override
  public ModelIconPresenter getIconPresenter() {
    return iconPresenter;
  }


  /**
   * Setter of iconPresenter
   *
   * @param iconPresenter the iconPresenter to set
   */
  @Override
  public void setIconPresenter(ModelIconPresenter iconPresenter) {
    this.iconPresenter = iconPresenter;
  }


  /**
   * Getter of identityPresenter
   *
   * @return the identityPresenter
   */
  @Override
  public ModelIdentityPresenter getIdentityPresenter() {
    return identityPresenter;
  }


  /**
   * Setter of identityPresenter
   *
   * @param identityPresenter the identityPresenter to set
   */
  @Override
  public void setIdentityPresenter(ModelIdentityPresenter identityPresenter) {
    this.identityPresenter = identityPresenter;
  }


  /**
   * Getter of contextMenuPresenter
   *
   * @return the contextMenuPresenter
   */
  @Override
  public ModelContextMenuPresenter getContextMenuPresenter() {
    return contextMenuPresenter;
  }


  /**
   * Setter of contextMenuPresenter
   *
   * @param contextMenuPresenter the contextMenuPresenter to set
   */
  @Override
  public void setContextMenuPresenter(ModelContextMenuPresenter contextMenuPresenter) {
    this.contextMenuPresenter = contextMenuPresenter;
  }


  /**
   * Getter of ellipsisMenuPresenter
   *
   * @return the ellipsisMenuPresenter
   */
  @Override
  public ModelEllipsisMenuPresenter getEllipsisMenuPresenter() {
    return ellipsisMenuPresenter;
  }


  /**
   * Setter of ellipsisMenuPresenter
   *
   * @param ellipsisMenuPresenter the ellipsisMenuPresenter to set
   */
  @Override
  public void setEllipsisMenuPresenter(ModelEllipsisMenuPresenter ellipsisMenuPresenter) {
    this.ellipsisMenuPresenter = ellipsisMenuPresenter;
  }


  /**
   * Getter of secondaryLabelPresenter
   *
   * @return the secondaryLabelPresenter
   */
  @Override
  public ModelSecondaryLabelPresenter getSecondaryLabelPresenter() {
    return secondaryLabelPresenter;
  }


  /**
   * Setter of secondaryLabelPresenter
   *
   * @param secondaryLabelPresenter the secondaryLabelPresenter to set
   */
  @Override
  public void setSecondaryLabelPresenter(ModelSecondaryLabelPresenter secondaryLabelPresenter) {
    this.secondaryLabelPresenter = secondaryLabelPresenter;
  }


  /**
   * Getter of ellipsisMenu
   *
   * @return the ellipsisMenu
   */
  @Override
  public EllipsisActionButton getEllipsisMenu() {
    return ellipsisMenu;
  }


  /**
   * Setter of ellipsisMenu
   *
   * @param ellipsisMenu the ellipsisMenu to set
   */
  @Override
  public void setEllipsisMenu(EllipsisActionButton ellipsisMenu) {
    this.ellipsisMenu = ellipsisMenu;
  }


  /**
   * Getter of configuration
   *
   * @return the configuration
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * Setter of configuration
   *
   * @param configuration the configuration to set
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;

    if (configuration != null) {
      mainLabelStyles = configuration.getPropertyValue("mainLabelStyleClass");
      descriptionLabelStyles = configuration.getPropertyValue("descriptionLabelStyleClass");
    }
  }


  /**
   * Getter of mainLabelStyles
   *
   * @return the mainLabelStyles
   */
  @Override
  public String getMainLabelStyles() {
    return mainLabelStyles;
  }


  /**
   * Setter of mainLabelStyles
   *
   * @param mainLabelStyles the mainLabelStyles to set
   */
  @Override
  public void setMainLabelStyles(String mainLabelStyles) {
    this.mainLabelStyles = mainLabelStyles;
  }


  /**
   * Getter of descriptionLabelStyles
   *
   * @return the descriptionLabelStyles
   */
  @Override
  public String getDescriptionLabelStyles() {
    return descriptionLabelStyles;
  }


  /**
   * Setter of descriptionLabelStyles
   *
   * @param descriptionLabelStyles the descriptionLabelStyles to set
   */
  @Override
  public void setDescriptionLabelStyles(String descriptionLabelStyles) {
    this.descriptionLabelStyles = descriptionLabelStyles;
  }


  /**
   * Getter of forData
   *
   * @return the forData
   */
  @Override
  public OperationData getForData() {
    return forData;
  }


  /**
   * Setter of forData
   *
   * @param forData the forData to set
   */
  @Override
  public void setForData(OperationData forData) {
    this.forData = forData;
  }


  @Override
  public Node getDisplay() {
    return null;
  }


  public BooleanProperty selectedProperty() {
    return selection.selectedProperty();
  }


  public SimpleBooleanProperty selectableProperty() {
    return selectable;
  }



  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    if(selectionContainer != null) {
      selectionContainer.managedProperty().bind(selectionContainer.visibleProperty());
      selectionContainer.setVisible(false);
    }

    if (configuration != null) {
      boolean selectable = configuration.getBooleanProperty("selectable", false);
      selectableProperty().set(selectable);
    }

    Object o = ((StandardViewController) this.controller).processedElement();
    if (o instanceof AbstractTableStructure) {
      tableConfig = ((AbstractTableStructure) o).getContentConfig();
      iconClickHandler = tableConfig.getComponentById("IconClickHandler").orElse(null);
    }

    // when icon is clicked
    if(iconClickHandler != null) {
      buildIconClickHandler();
    }

    // when icon is clicked
    buildIdentityClickHandler();
  }

  protected void buildIconClickHandler() {
    Node iconContainer = getIconContainer();
    if (iconClickHandler != null && iconContainer != null) {
      iconContainer.getStyleClass().add("hand-mouse-hover");

      if(AbstractApplicationRunner.isMobile()) {
        // avoid multiple touch loading same view multiple times!!
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ae -> isLoadingChild = false));

        iconContainer.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
          if (iconClickHandler != null && !AbstractApplicationRunner.isApplicationScrolling() && !isLoadingChild && e.getTouchCount() == 1) {
            isLoadingChild = true;
            timeline.play();
            ComponentToButtonBaseHelper.setButtonActions(controller, iconClickHandler, iconContainer, e, getForData());
          }
        });
      }
      else {
        iconContainer.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
          if (iconClickHandler != null && e.getClickCount() == 2) {
            ComponentToButtonBaseHelper.setButtonActions(controller, iconClickHandler, iconContainer, e, getForData());
          }
        });
      }
    }
  }

  protected void buildIdentityClickHandler() {
    Node identityContainer = getIdentityContainer();
    if(tableConfig != null) {
      VLViewComponentXML identityClickHandler = tableConfig.getComponentById("IdentityClickHandler").orElse(null);
      if (identityClickHandler != null && identityContainer != null) {
        identityContainer.getStyleClass().add("hand-mouse-hover");

        if(AbstractApplicationRunner.isMobile()) {

          Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ae -> isLoadingChild = false));

          identityContainer.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
            if (iconClickHandler != null && !AbstractApplicationRunner.isApplicationScrolling() && !isLoadingChild && e.getTouchCount() == 1) {
              isLoadingChild = true;
              timeline.play();
              ComponentToButtonBaseHelper.setButtonActions(controller, iconClickHandler, identityContainer, e, getForData());
            }
          });
        }
        else {
          identityContainer.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (identityClickHandler != null && e.getClickCount() == 2) {
              ComponentToButtonBaseHelper.setButtonActions(controller, identityClickHandler, identityContainer, e, getForData());
            }
          });
        }
      }
    }
  }


  /**
   * If the node is selectable, must return a pane that will contain a select box allowing user to
   * select this flow item.
   *
   * @return
   */
  public Pane getSelectionPaneWrapper() {
    return selectionContainer;
  }


  public void deselect() {
    selection.selectedProperty().set(false);
  }


  /**
   * @return
   */
  public ModelHeaderIdentityPresenter getHeaderIdentityProvider() {
    return headerIdentityProvider;
  }


  /**
   * @param headerIdentityProvider
   */
  public void setHeaderIdentityProvider(ModelHeaderIdentityPresenter headerIdentityProvider) {
    this.headerIdentityProvider = headerIdentityProvider;
  }


  @Override
  public Node getIdentityContainer() {
    return null;
  }


  @Override
  public Node getIconContainer() {
    return null;
  }



  public void setTableConfig(VLViewComponentXML tableConfig) {
    this.tableConfig = tableConfig;
  }
}
