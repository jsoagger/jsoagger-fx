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

package io.github.jsoagger.jfxcore.engine.components.toolbar.htoolbar;


import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IModifiableToolbarHolder;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ModifiableHToolbar extends BasicHToolbar {

  protected final HBox modifyMLeftSection = new HBox();
  protected final HBox modifyMCenterSection = new HBox();
  protected final HBox modifyMRightSection = new HBox();
  protected final HBox modifyingActionsWrapper = new HBox();

  protected final Button modifyButton = new Button();
  protected final Button doneButton = new Button();


  /**
   * Constructor
   */
  public ModifiableHToolbar() {
    super();
    NodeHelper.setHgrow(allOverWrapper);

    modifyingActionsWrapper.getStyleClass().add("ep-modifying-toolbar");
    rootContainer.getStyleClass().add("ep-modifiable-toolbar");
    modifyMLeftSection.getStyleClass().add("ep-modifying-toolbar-left");
    modifyMCenterSection.getStyleClass().add("ep-modifying-toolbar-center");
    modifyMRightSection.getStyleClass().add("ep-modifying-toolbar-right");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(AbstractViewController controller, IToolbarHolder toolbarHolder) {
    super.buildFrom(controller, toolbarHolder);
    buildModifyActions();

    final String styleClass = configuration.getPropertyValue("modifyingActionsStyleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
      modifyingActionsWrapper.getStyleClass().addAll(styleClass.split(","));
    }
  }


  /**
   * Modify menu configuration is menu displayed when the user clicked on modify button on the
   * displayed toolbar.
   * <p>
   * If no modify menu is provided, the modify button is not displayed.
   * <p>
   */
  private void buildModifyActions() {
    if (modifyMenuConfiguration != null) {

      // build left side actions
      final VLViewComponentXML leftActions = modifyMenuConfiguration.getComponentById("LeftActions").orElse(null);
      if (leftActions != null) {
        final List<IBuildable> buildables = buildActionGroup(leftActions);
        for(IBuildable node: buildables) {
          modifyMLeftSection.getChildren().add(node.getDisplay());
        }
        modifyingActionsWrapper.getChildren().addAll(modifyMLeftSection);
      }

      // build center actions
      final VLViewComponentXML centerActions = modifyMenuConfiguration.getComponentById("CenterActions").orElse(null);
      if (centerActions != null) {
        final List<IBuildable> buildables = buildActionGroup(centerActions);
        for(IBuildable node: buildables) {
          modifyMCenterSection.getChildren().add(node.getDisplay());
        }

        if (modifyingActionsWrapper.getChildren().size() > 0) {
          modifyingActionsWrapper.getChildren().addAll(NodeHelper.horizontalSpacer());
        }

        modifyingActionsWrapper.getChildren().addAll(modifyMCenterSection);
      }

      final VLViewComponentXML rightActions = modifyMenuConfiguration.getComponentById("RightActions").orElse(null);
      if (rightActions != null) {
        final List<IBuildable> buildables = buildActionGroup(rightActions);
        for(IBuildable node: buildables) {
          modifyMRightSection.getChildren().add(node.getDisplay());
        }

        if (modifyingActionsWrapper.getChildren().size() > 0) {
          modifyingActionsWrapper.getChildren().addAll(NodeHelper.horizontalSpacer());
        }

        modifyingActionsWrapper.getChildren().addAll(modifyMRightSection);
      }
    }

    allOverWrapper.visibleProperty().bind(Bindings.not(((IModifiableToolbarHolder) toolbarHolder).modifyingProperty()));
    allOverWrapper.managedProperty().bind(allOverWrapper.visibleProperty());

    buildModifyButton();
    buildDoneButton();
  }


  /**
   * Build the modify button.
   */
  private void buildModifyButton() {
    modifyButton.getStyleClass().addAll("table-toolbar-action", "ep-button");
    modifyButton.setOnAction(e -> modifyClicked(controller));
    modifyButton.setText(controller.getGLocalised("EDIT_LABEL").toUpperCase());
    IconUtils.setFontIcon("gmi-more-horiz:18", modifyButton);

    allOverWrapper.getChildren().add(modifyButton);
    if (selectable.get()) {
      //thickButton.setOnAction(e -> thickClicked());
      //modifyingActionsWrapper.getChildren().add(0, thickButton);
    }

    NodeHelper.setHgrow(modifyingActionsWrapper);
    modifyingActionsWrapper.visibleProperty().bind(((IModifiableToolbarHolder) toolbarHolder).modifyingProperty());
    modifyingActionsWrapper.managedProperty().bind(modifyingActionsWrapper.visibleProperty());
    rootContainer.getChildren().add(modifyingActionsWrapper);
  }


  /**
   * Build done button
   */
  private void buildDoneButton() {
    doneButton.getStyleClass().addAll("button-small", "table-toolbar-action", "ep-button");
    doneButton.setOnAction(e -> modifyClicked(controller));
    doneButton.setText(controller.getGLocalised("DONE_LABEL").toUpperCase());
    IconUtils.setFontIcon("fa-check:16", doneButton);
    modifyingActionsWrapper.getChildren().addAll(NodeHelper.horizontalSpacer(), doneButton);
  }


  /**
   * @param controller
   */
  protected void modifyClicked(AbstractViewController controller) {
    if (!((IModifiableToolbarHolder) toolbarHolder).isModifying()) {
      thickButton.setSelected(false);
      ((IModifiableToolbarHolder) toolbarHolder).setModifying(true);
      //modifyButton.setText(controller.getLocalised("Done"));

      // show all selectors
      ((IModifiableToolbarHolder) toolbarHolder).modify();

      // reinit thick button
      isThicked = true;
      thickClicked();
    }

    else {
      ((IModifiableToolbarHolder) toolbarHolder).setModifying(false);
      // modifyButton.setText(null);

      // hide all selectors
      ((IModifiableToolbarHolder) toolbarHolder).cancelModify();
    }
  }


  /**
   * Called when a checkbox or a listcell is clicked.
   */
  public void listCellSelected() {
    isThicked = false;
    thickButton.setSelected(false);
  }
}
