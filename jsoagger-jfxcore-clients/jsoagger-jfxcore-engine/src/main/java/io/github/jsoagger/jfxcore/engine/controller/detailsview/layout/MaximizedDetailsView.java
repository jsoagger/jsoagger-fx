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

package io.github.jsoagger.jfxcore.engine.controller.detailsview.layout;


import java.net.URL;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.detailsview.IDetailsViewHeader;
import io.github.jsoagger.jfxcore.api.detailsview.IMaximizedDetailsView;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.tab.VLTab;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 16 févr. 2018
 */
public class MaximizedDetailsView extends DetailsView implements IMaximizedDetailsView, IToolbarHolder {

  @FXML
  private Pane headerExternalContainer;

  @FXML
  private Pane leftSectionContainer;

  @FXML
  private Pane rootContainer;

  @FXML
  private Pane leftSectionContainerExternal;

  @FXML
  private Pane thumbContainer;

  @FXML
  private Pane quickInfoContainer;

  @FXML
  private Pane centerContainer;

  @FXML
  private Pane identityContainer;

  @FXML
  private ScrollPane viewActionsContainer;

  @FXML
  private Pane viewActionsContainerContent;

  @FXML
  private Pane editActionsContainer;

  @FXML
  private ButtonBase donePopContentButton;

  @FXML
  private Pane contentContainer;

  @FXML
  private Pane centerContainerViewLayout;

  @FXML
  private Pane centerContainerEditLayout;

  @FXML
  private Pane centerContainerEditLayoutWrapper;

  @FXML
  private Pane iconContainer;

  @FXML
  private Pane tabItemsContainer;

  @FXML
  private Pane headerInternalContainer;


  private IDetailsViewHeader header;
  private ModelIconPresenter iconProvider;
  private VLViewComponentXML configuration;


  /**
   * Default Constructor
   */
  public MaximizedDetailsView() {
    super();
    URL url = getFXMLLocation();
    NodeHelper.loadFXML(url, this);
  }


  /**
   * @return
   */
  protected URL getFXMLLocation() {
    return MaximizedDetailsView.class.getResource("MaximizedDetailsView.fxml");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.LEFT) {
      return leftSectionContainerExternal;
    } else if (position == ViewLayoutPosition.CENTER) {
      return centerContainer;
    } else if (position == ViewLayoutPosition.TOP) {
      return headerExternalContainer;
    }

    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    this.configuration = configuration;

    buildTabs(configuration);
    tabItemsContainer.getChildren().clear();
    for(VLTab t: tabs) {
      tabItemsContainer.getChildren().add(t);
    }

    leftSectionContainer.setVisible(true);
    NodeHelper.styleClassSetAll(this, configuration, "styleClass", "ep-maximized-details-view-root-pane");

    header.buildFrom(controller, configuration);
    identityContainer.getChildren().add(header.getDisplay());

    if (iconProvider != null) {
      Node icon = iconProvider.provideIcon(controller, configuration);
      iconContainer.getChildren().add(icon);
    }

    // build actions
    VLViewComponentXML actionsConfig = configuration.getComponentById("Actions").orElse(null);
    if (actionsConfig != null) {
      Optional<AbstractToolbar> optional = ToolbarUtils.buildToolbar((AbstractViewController) controller, this);
      optional.ifPresent(t -> viewActionsContainerContent.getChildren().add(t.getDisplay()));
    }

    IconUtils.setFontIcon("mdi-undo:18", donePopContentButton);
    donePopContentButton.setOnAction(e -> {
      popContent();
      ((AbstractViewController) controller).getStructureContent().setCurrentEditingTableStructure(null);
    });

    // if revision controlled and WC color it
    OperationData data = ((AbstractViewController)controller).getOpData();
    Boolean wc = (Boolean) data.getAttributes().get("workInfo.isWorkingCopy");
    if(wc != null && wc) {
      PseudoClass pc = PseudoClass.getPseudoClass("wc");
      headerInternalContainer.pseudoClassStateChanged(pc, true);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * Getter of iconProvider
   *
   * @return the iconProvider
   */
  public ModelIconPresenter getIconProvider() {
    return iconProvider;
  }


  /**
   * Setter of iconProvider
   *
   * @param iconProvider the iconProvider to set
   */
  public void setIconProvider(ModelIconPresenter iconProvider) {
    this.iconProvider = iconProvider;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getTabContentArea() {
    return contentContainer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setHeader(IDetailsViewHeader header) {
    this.header = header;
  }

  private AbstractViewController pushedController;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void pushContent(IJSoaggerController controller, Node node) {
    pushedController = (AbstractViewController) controller;
    if (Platform.isFxApplicationThread()) {
      _doPushContent(node);
    } else {
      Platform.runLater(() -> {
        _doPushContent(node);
      });
    }
  }


  public void _doPushContent(Node node) {
    centerContainerViewLayout.setVisible(false);
    centerContainerViewLayout.setManaged(false);
    centerContainerEditLayoutWrapper.setVisible(true);
    centerContainerEditLayoutWrapper.setManaged(true);

    NodeHelper.setHVGrow(node);
    centerContainerEditLayout.getChildren().clear();
    centerContainerEditLayout.getChildren().add(node);

    viewActionsContainer.setVisible(false);
    viewActionsContainer.setManaged(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void popContent() {
    pushedController.beforePop();

    centerContainerViewLayout.setVisible(true);
    centerContainerViewLayout.setManaged(true);
    centerContainerEditLayoutWrapper.setVisible(false);
    centerContainerEditLayoutWrapper.setManaged(false);

    viewActionsContainer.setVisible(true);
    viewActionsContainer.setManaged(true);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getToolbarConfiguration() {
    return configuration.getComponentById("Actions").orElse(null);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public CriteriaContext criteriaContext() {
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public IDetailsViewHeader getHeader() {
    return header;
  }
}
