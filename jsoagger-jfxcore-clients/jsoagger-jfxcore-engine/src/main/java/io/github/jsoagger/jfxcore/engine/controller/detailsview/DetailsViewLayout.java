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

package io.github.jsoagger.jfxcore.engine.controller.detailsview;


import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IDockable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.api.detailsview.IEmptyDetailsview;
import io.github.jsoagger.jfxcore.api.detailsview.IMaximizedDetailsView;
import io.github.jsoagger.jfxcore.api.detailsview.IMinimizedDetailsView;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.DetailsViewController.DetailsViewStatus;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.layout.MaximizedDetailsViewController;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.layout.MinimizeDetailsViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 17 févr. 2018
 */
public class DetailsViewLayout extends StackPane implements IBuildable, IMinimizable, IDockable {

  private String minimizedViewId;
  private String maximizedViewId;

  protected MinimizeDetailsViewController minimized;
  protected MaximizedDetailsViewController maximized;

  protected IEmptyDetailsview emptyDetailsView;

  protected SimpleObjectProperty<DetailsViewStatus> status = new SimpleObjectProperty<>(DetailsViewStatus.EMPTY);
  protected AbstractViewController controller;


  /**
   * Default Constructor
   */
  public DetailsViewLayout() {
    status.addListener((ChangeListener<DetailsViewStatus>) (observable, oldValue, newValue) -> {
      if (newValue == DetailsViewStatus.BUILDING) {
        Platform.runLater(() -> {
          getChildren().clear();
          getChildren().add(NodeHelper.getProcessingIndicator());
        });
      }

      else if (newValue == DetailsViewStatus.MAXIMIZE) {
        Platform.runLater(() -> {
          getChildren().clear();
          // getChildren().add(maximizedDetailsView.getDisplay());
          // getChildren().add(minimizedDetailsView.getDisplay());
          getChildren().add(maximized.processedView());
        });
      } else if (newValue == DetailsViewStatus.MINIMIZE) {
        if (minimized != null) {
          Platform.runLater(() -> {
            getChildren().clear();
            getChildren().add(minimized.processedView());
          });
        }
      }
      // EMPTY
      else {
        Platform.runLater(() -> {
          getChildren().clear();
          getChildren().add(emptyDetailsView.getDisplay());
        });
      }
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;

    buildMaximized();
    buildMinimized();

    emptyDetailsView = (IEmptyDetailsview) Services.getBean("EmptyDetailsView");
    emptyDetailsView.buildFrom(controller, configuration);
    status.set(DetailsViewStatus.MAXIMIZE);
  }


  protected void buildMinimized() {
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(minimizedViewId))
      minimized = (MinimizeDetailsViewController) StandardViewUtils.forId(controller.getRootStructure(), controller.getStructureContent(), minimizedViewId);
  }


  protected void buildMaximized() {
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(maximizedViewId))
      maximized = (MaximizedDetailsViewController) StandardViewUtils.forChildId(maximizedViewId, controller.getStructureContent(), (StandardViewController) controller);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {
    status.set(DetailsViewStatus.MINIMIZE);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {
    status.set(DetailsViewStatus.MAXIMIZE);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void dock() {
    status.set(DetailsViewStatus.DOCKED);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void undock() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  public DetailsViewStatus status() {
    return status.get();
  }


  public void pushContent(StandardViewController c, Node node) {
    // maximizedDetailsView.pushContent(c, node);
  }


  public void popContent() {
    // maximizedDetailsView.popContent();
  }


  public IEmptyDetailsview getEmptyDetailsView() {
    return emptyDetailsView;
  }


  public void setEmptyDetailsView(IEmptyDetailsview emptyDetailsView) {
    this.emptyDetailsView = emptyDetailsView;
  }


  public SimpleObjectProperty<DetailsViewStatus> getStatus() {
    return status;
  }


  public void setStatus(SimpleObjectProperty<DetailsViewStatus> status) {
    this.status = status;
  }


  public String getMinimizedViewId() {
    return minimizedViewId;
  }


  public void setMinimizedViewId(String minimizedViewId) {
    this.minimizedViewId = minimizedViewId;
  }


  public String getMaximizedViewId() {
    return maximizedViewId;
  }


  public void setMaximizedViewId(String maximizedViewId) {
    this.maximizedViewId = maximizedViewId;
  }


  public Node getIdentity() {
    if (maximized != null) {
      final IMaximizedDetailsView mdv = maximized.getDetailsView();
      return mdv.getHeader().getHeaderIdentity();
    }

    if (minimized != null) {
      final IMinimizedDetailsView mdv = minimized.getDetailsView();
      return mdv.getHeader().getHeaderIdentity();
    }

    return null;
  }
}
