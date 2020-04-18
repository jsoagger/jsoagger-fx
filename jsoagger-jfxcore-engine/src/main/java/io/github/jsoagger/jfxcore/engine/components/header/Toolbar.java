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

package io.github.jsoagger.jfxcore.engine.components.header;



import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.header.comps.HeaderRightToolbar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.IHeaderToolbar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.MobileWithBottomTabNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.NavigableToolbar;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetCustomRightActions;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetStandardRightActions;
import io.github.jsoagger.jfxcore.engine.components.tab.ReinitHeaderNavigationEvent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class Toolbar extends HBox implements IBuildable {

  protected IBuildable leftToolBar;
  protected IBuildable centerToolBar;

  protected VLViewComponentXML rightActionsComponents;
  protected IBuildable rightToolbar;

  protected IParentResponsiveMatrix responsiveMatrix;
  protected AbstractViewController controller;


  /**
   * Constructor
   */
  public Toolbar() {
    super();
    setHgrow(this, Priority.ALWAYS);
    widthProperty().addListener((ChangeListener<Number>) this::handleParentWidthChange);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    if (configuration != null) {
      NodeHelper.setStyleClass(this, configuration, "styleClass", true);

      final VLViewComponentXML leftActionsComponents =
          ComponentUtils.resolveComponent(configuration, "LeftComponents");
      buildLeftItems((AbstractViewController) controller, leftActionsComponents);

      final VLViewComponentXML centerActionsComponents =
          ComponentUtils.resolveComponent(configuration, "CenterComponents");
      buildCenterItems((AbstractViewController) controller, centerActionsComponents);

      rightActionsComponents = ComponentUtils.resolveComponent(configuration, "RightComponents");
      buildRightItems();
    }
  }


  public void buildRightItems() {
    rightToolbar = getContentImpl(rightActionsComponents, "HeaderRightToolbar");
    if (rightActionsComponents != null) {
      build(rightToolbar, controller, rightActionsComponents);
      getChildren().add(rightToolbar.getDisplay());

      if (AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
        NodeHelper.setHgrow(rightToolbar.getDisplay());
      }

    } else {
      rightToolbar.getDisplay().getStyleClass().add("ep-header-right-area-container");
    }
  }


  public void buildCenterItems(AbstractViewController controller,
      VLViewComponentXML centerActionsComponents) {
    centerToolBar = getContentImpl(centerActionsComponents, "HeaderCenterToolBar");
    if (centerActionsComponents != null && centerActionsComponents.hasSubComponent()) {
      build(centerToolBar, controller, centerActionsComponents);
      getChildren().add(centerToolBar.getDisplay());

      if (AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
        NodeHelper.setHgrow(centerToolBar.getDisplay());
      }
    } else {
      centerToolBar.getDisplay().getStyleClass().add("ep-header-center-area-container");
    }
  }


  public void buildLeftItems(AbstractViewController controller,
      VLViewComponentXML leftActionsComponents) {
    leftToolBar = getContentImpl(leftActionsComponents, "HeaderLeftToolBar");
    if (leftActionsComponents != null) {
      build(leftToolBar, controller, leftActionsComponents);
      getChildren().add(leftToolBar.getDisplay());

      if (AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
        NodeHelper.setHgrow(leftToolBar.getDisplay());
      }
    } else {
      leftToolBar.getDisplay().getStyleClass().add("ep-header-left-area-container");
    }
  }


  protected void build(IBuildable buildable, AbstractViewController controller,
      VLViewComponentXML actionsComponents) {
    buildable.buildFrom(controller, actionsComponents);
  }


  protected void handleParentWidthChange(ObservableValue value, Number oldParentWidth,
      Number newParentWidth) {
    if (responsiveMatrix != null) {
      final IResponsiveAreaSize areasSize =
          responsiveMatrix.getSizeOf(newParentWidth.doubleValue());
      if (areasSize != null) {
        final IResponsiveSizing leftSize = areasSize.getSizeOf(0);
        final IResponsiveSizing centerSize = areasSize.getSizeOf(1);
        final IResponsiveSizing rightSize = areasSize.getSizeOf(2);

        IResponsiveAware.resize(leftToolBar, leftSize);
        IResponsiveAware.resize(centerToolBar, centerSize);
        IResponsiveAware.resize(rightToolbar, rightSize);
      }
    }
  }


  /**
   * Return the implemtation of the toolbar.
   */
  private IBuildable getContentImpl(VLViewComponentXML comp, String defaultImpl) {
    String contentImpl = defaultImpl;
    if (comp != null) {
      contentImpl = comp.getPropertyValue("contentImpl", defaultImpl);
    }

    final IBuildable imp = (IBuildable) Services.getBean(contentImpl);
    return imp;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  public void setResponsiveMatrix(IParentResponsiveMatrix responsiveMatrix) {
    this.responsiveMatrix = responsiveMatrix;
  }


  public void handle(HeaderNavbarSetStandardRightActions ev) {
    ((HeaderRightToolbar) rightToolbar).handle(ev);
  }


  /**
   * @param ev
   */
  public void handle(HeaderNavbarSetCustomRightActions ev) {
    ((HeaderRightToolbar) rightToolbar).handle(ev);
  }


  public void handle(SetCurrentLocationEvent ev) {
    final NavigableToolbar navigableToolbar = getNavigableToolbar();
    if (navigableToolbar != null) {
      navigableToolbar.setCurrentLocationTo(ev);
    }
  }


  public void updateLocation(UpdateCurrentLocationEvent navigationItem) {
    // TODO AT STARTUP TOOLBAR IS NOT INITIALIZED AND RECEIVE THIS EVENT
    // THROW NULL POINTER AT THIS TIME
    // RESOLVE THIS
    try {
      final NavigableToolbar navigableToolbar = getNavigableToolbar();
      if (navigableToolbar != null) {
        navigableToolbar.updateLocation(navigationItem);
      }

      final boolean showHeaderBottom = navigationItem.getShowHeader();
      if (showHeaderBottom) {
        if (leftToolBar != null)
          ((IHeaderToolbar) leftToolBar).showBottomToolbar();
        if (rightToolbar != null)
          ((IHeaderToolbar) rightToolbar).showBottomToolbar();
        if (centerToolBar != null)
          ((IHeaderToolbar) centerToolBar).showBottomToolbar();
        pseudoClassStateChanged(PseudoClass.getPseudoClass("hideBottom"), false);
      } else {
        if (leftToolBar != null)
          ((IHeaderToolbar) leftToolBar).hideBottomToolbar();
        if (rightToolbar != null)
          ((IHeaderToolbar) rightToolbar).hideBottomToolbar();
        if (centerToolBar != null)
          ((IHeaderToolbar) centerToolBar).hideBottomToolbar();
        pseudoClassStateChanged(PseudoClass.getPseudoClass("hideBottom"), true);
      }
    } catch (final Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }


  public void popLocation() {
    final NavigableToolbar navigableToolbar = getNavigableToolbar();
    if (navigableToolbar != null) {
      navigableToolbar.goBack();
    }
  }


  /**
   * @param ev
   */
  public void handle(HeaderNavbarFireBackButton ev) {
    final NavigableToolbar navigableToolbar = getNavigableToolbar();
    if (navigableToolbar != null) {
      navigableToolbar.goBack(ev);
    }
  }


  /**
   * Right toolbar should not be a {@link NavigableToolbar}. Navigable toolbar should be either the
   * left or the center one.
   *
   * @return
   */
  protected NavigableToolbar getNavigableToolbar() {
    if (leftToolBar instanceof NavigableToolbar) {
      return (NavigableToolbar) leftToolBar;
    }
    if (centerToolBar instanceof NavigableToolbar) {
      return (NavigableToolbar) centerToolBar;
    }
    return null;
  }


  public void handle(ReinitHeaderNavigationEvent ev) {
    if (leftToolBar instanceof MobileWithBottomTabNavigationBar) {
      ((MobileWithBottomTabNavigationBar) leftToolBar).reinit();
    }
  }
}
