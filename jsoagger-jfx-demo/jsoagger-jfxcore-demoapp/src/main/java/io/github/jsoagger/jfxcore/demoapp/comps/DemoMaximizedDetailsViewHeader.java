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

package io.github.jsoagger.jfxcore.demoapp.comps;

import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.api.detailsview.IDetailsViewHeader;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.components.rc.RCMaximizedDetailsViewHeader;

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
public class DemoMaximizedDetailsViewHeader implements IBuildable, IToolbarHolder, IDetailsViewHeader {

  protected DemoDetailsViewHeaderPresenter presenter;

  @FXML
  private Pane rootPane;
  @FXML
  private Pane identityContainer;
  @FXML
  private Pane actionsContainer;
  @FXML
  private Pane contextMenuContainer;
  @FXML
  private Pane workStatusContainer;
  @FXML
  private Pane iconContainer;

  private VLViewComponentXML configuration;
  private AbstractViewController controller;

  /**
   * Constructor
   */
  public DemoMaximizedDetailsViewHeader() {
    super();
    final URL location = getFXMLLocation();
    NodeHelper.loadFXML(location, this);
  }

  protected URL getFXMLLocation() {
    return RCMaximizedDetailsViewHeader.class.getResource("RCMaximizedDetailsViewHeader.fxml");
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
      final Node identity = presenter.getIdentityPresenter().provideIdentityOf(controller, configuration);
      identityContainer.getChildren().add(identity);
    }

    // set icon container to null to hide this
    iconContainer.managedProperty().bind(Bindings.isNotEmpty(iconContainer.getChildren()));
    if (presenter.getIconPresenter() != null) {
      final Node icon = presenter.getIconPresenter().provideIcon(controller, configuration);
      iconContainer.getChildren().add(icon);
    }

    // build actions
    final VLViewComponentXML actionsConfig = configuration.getComponentById("HeaderActions").orElse(null);
    if (actionsConfig != null) {
      // !! TODO CORRECT THIS
      // BUILD WITH WRONG CONFIGURATION FILE,
      /*
       * Optional<AbstractToolbar> optional = ToolbarUtils.buildToolbar(controller, this);
       * optional.ifPresent(t -> actionsContainer.getChildren().add(t.getDisplay()));
       */
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getHeaderIdentity() {
    ModelIdentityPresenter realpresenter = null;
    if (presenter.getHeaderIdentityProvider() != null) {
      realpresenter = presenter.getHeaderIdentityProvider();
    }

    if (realpresenter == null) {
      realpresenter = presenter.getIdentityPresenter();
    }

    if (realpresenter != null) {
      final Node identity = realpresenter.provideIdentityOf(controller, configuration);
      return identity;
    }

    return null;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String getIdentity() {
    if (presenter.getIdentityPresenter() != null) {
      final String identity = presenter.getIdentityPresenter().identityOf(controller, configuration);
      return identity;
    }
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
    this.presenter = (DemoDetailsViewHeaderPresenter) presenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hideDetailsIdentity() {
    rootPane.getChildren().clear();
    rootPane.setVisible(false);
    rootPane.setManaged(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showIdentity() {
    identityContainer.setVisible(true);
    // identityContainer.setManaged(true);
  }
}
