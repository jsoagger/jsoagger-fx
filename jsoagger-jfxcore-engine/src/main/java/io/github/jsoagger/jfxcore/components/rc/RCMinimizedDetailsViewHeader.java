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

package io.github.jsoagger.jfxcore.components.rc;



import java.net.URL;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.api.detailsview.IDetailsViewHeader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Should be used when the details view is show on right side of an element for example.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 5 févr. 2018
 */
public class RCMinimizedDetailsViewHeader implements IBuildable, IToolbarHolder, IDetailsViewHeader {

  protected RCDetailsViewHeaderPresenter presenter;

  @FXML
  private Pane rootPane;
  @FXML
  private Pane iconContainer;
  @FXML
  private Pane identityContainer;
  @FXML
  private Pane actionsContainer;
  @FXML
  private Pane contextMenuContainer;

  private VLViewComponentXML configuration;
  private AbstractViewController controller;


  /**
   * Constructor
   */
  public RCMinimizedDetailsViewHeader() {
    super();
    URL location = RCMinimizedDetailsViewHeader.class.getResource("RCMinimizedDetailsViewHeader.fxml");
    NodeHelper.loadFXML(location, this);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    identityContainer.managedProperty().bind(Bindings.isNotEmpty(identityContainer.getChildren()));
    if (presenter.getIdentityPresenter() != null) {
      Node identity = presenter.getIdentityPresenter().provideIdentityOf(controller, configuration);
      identityContainer.getChildren().add(identity);
    }

    // build actions
    VLViewComponentXML actionsConfig = configuration.getComponentById("HeaderActions").orElse(null);
    if (actionsConfig != null) {
      Optional<AbstractToolbar> optional = ToolbarUtils.buildToolbar((AbstractViewController) controller, this);
      optional.ifPresent(t -> actionsContainer.getChildren().add(t.getDisplay()));
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getIdentity() {
    if (presenter.getIdentityPresenter() != null) {
      String identity = presenter.getIdentityPresenter().identityOf((IJSoaggerController)controller, configuration);
      return identity;
    }

    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getHeaderIdentity() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
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
   * Getter of presenter
   *
   * @return the presenter
   */
  public MultiPresenterFactory getPresenter() {
    return presenter;
  }


  /**
   * Setter of presenter
   *
   * @param presenter the presenter to set
   */
  public void setPresenter(MultiPresenterFactory presenter) {
    this.presenter = (RCDetailsViewHeaderPresenter) presenter;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void hideDetailsIdentity() {
    identityContainer.setVisible(false);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void showIdentity() {
    identityContainer.setVisible(true);
  }
}
