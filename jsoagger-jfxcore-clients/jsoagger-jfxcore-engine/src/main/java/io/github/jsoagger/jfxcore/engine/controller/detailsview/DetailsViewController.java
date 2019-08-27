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



import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;

/**
 * Details view controller. Set attributes "HideIdentity" to true if want to hide the main identity.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DetailsViewController extends StandardViewController {

  private String minimizedDetailsView;
  private String maximizedDetailsView;

  private final SimpleBooleanProperty hideIdentity = new SimpleBooleanProperty(false);

  // will be minimized, maximized by layout manager
  private final DetailsViewLayout detailsViewLayout = new DetailsViewLayout();
  /*-----------------------------------------------------------------------------
  | PRIVATE ATTRIBUTES
   *=============================================================================*/

  /**
   * Constructor
   */
  public DetailsViewController() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplayIdentity() {
    return detailsViewLayout().getIdentity();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return detailsViewLayout;
    }

    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    detailsViewLayout.setMinimizedViewId(minimizedDetailsView);
    detailsViewLayout.setMaximizedViewId(maximizedDetailsView);
    detailsViewLayout.buildFrom(this, null);

    final String currentLocation = getCurrentLocation();
    if (StringUtils.isNotBlank(currentLocation)) {
      getStructureContent().contentLocationProperty().set(currentLocation);
    }

    hideIdentity.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      handleIdentityDisplay();
    });

    handleIdentityDisplay();
    processedView(detailsViewLayout);
  }


  public void handleIdentityDisplay() {
    if (hideIdentity.get()) {
      detailsViewLayout.maximized.getDetailsView().getHeader().hideDetailsIdentity();
      if (detailsViewLayout.minimized != null)
        detailsViewLayout.minimized.getDetailsView().getHeader().hideDetailsIdentity();
    } else {
      detailsViewLayout.maximized.getDetailsView().getHeader().showIdentity();
      if (detailsViewLayout.minimized != null)
        detailsViewLayout.minimized.getDetailsView().getHeader().showIdentity();
    }
  }


  public String getCurrentLocation() {
    // may be a location?
    final String location = getRootComponent().getPropertyValue("location");
    if (StringUtils.isNotBlank(location)) {
      final String loc = getLocalised(location);
      return loc;
    }

    // or an identity
    final String identity = null;
    if (StringUtils.isNotBlank(identity)) {
      return identity;
    }

    return "No location";
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void pushContent(StandardViewController c, Node node) {
    detailsViewLayout.pushContent(c, node);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void popContent() {
    detailsViewLayout.popContent();
  }


  /**
   * @return
   */
  public DetailsViewLayout detailsViewLayout() {
    return detailsViewLayout;
  }

  /**
   * @author Ramilafananana Vonjisoa
   * @mailTo yvonjisoa@nexitia.com
   * @date 17 févr. 2018
   */
  public enum DetailsViewStatus {
    BUILDING, EMPTY, MINIMIZE, MAXIMIZE, DOCKED;
  }


  public String getMinimizedDetailsView() {
    return minimizedDetailsView;
  }


  public void setMinimizedDetailsView(String minimizedDetailsView) {
    this.minimizedDetailsView = minimizedDetailsView;
  }


  public String getMaximizedDetailsView() {
    return maximizedDetailsView;
  }


  public void setMaximizedDetailsView(String maximizedDetailsView) {
    this.maximizedDetailsView = maximizedDetailsView;
  }

  public void setHideIdentity(boolean value) {
    hideIdentityProperty().set(value);
  }

  @Override
  public void hideIdentity() {
    hideIdentity.set(true);
  }


  public SimpleBooleanProperty hideIdentityProperty() {
    return hideIdentity;
  }
}
