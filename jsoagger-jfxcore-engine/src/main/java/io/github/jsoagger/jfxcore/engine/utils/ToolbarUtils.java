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

package io.github.jsoagger.jfxcore.engine.utils;


import java.util.List;
import java.util.Optional;

import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.contextmenu.EllipsisActionButton;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.components.toolbar.inline.FormInlineToobar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;

/**
 * Helper for Generating a {@link AbstractToolbar} from an action model.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ToolbarUtils {

  private static final String TOOLBAR_IMPL = "toolbarImpl";


  /**
   * Build Toolbar from model
   *
   * @param config
   * @return {@link IBuildable}
   */
  public static Optional<AbstractToolbar> buildToolbar(AbstractViewController controller, IToolbarHolder toolbarHolder) {
    Assert.notNull(controller);
    Assert.notNull(toolbarHolder);

    VLViewComponentXML toolbarConfig = toolbarHolder.getToolbarConfiguration();
    if (toolbarConfig != null) {
      String toolBarImpl = toolbarConfig.getPropertyValue(TOOLBAR_IMPL, "BasicHToolbar");
      AbstractToolbar toolbar = (AbstractToolbar) Services.getBean(toolBarImpl);
      toolbar.buildFrom(controller, toolbarHolder);
      return Optional.ofNullable(toolbar);
    }

    return Optional.empty();
  }


  /**
   * Generates an hyperlink with ELLIPSIS_V.
   */
  public static EllipsisActionButton ellipsisVActionsButton(VLViewComponentXML moreActionConfig, final AbstractViewController controller, final CriteriaContext criteriaContext) {
    final EllipsisActionButton ellipsisButton = new EllipsisActionButton();
    ellipsisButton.setVerlical();
    if (moreActionConfig != null && moreActionConfig.hasSubComponent()) {
      ellipsisButton.build(moreActionConfig, controller, criteriaContext);
    }
    return ellipsisButton;
  }


  /**
   * Generates an hyperlink with ELLIPSIS_H.
   */
  public static Node ellipsisHActionsButton(final VLViewComponentXML moreActionConfig, final AbstractViewController controller, final CriteriaContext criteriaContext) {
    final EllipsisActionButton ellipsisButton = new EllipsisActionButton();
    ellipsisButton.setHorizontal();
    ellipsisButton.build(moreActionConfig, controller, criteriaContext);
    return ellipsisButton;
  }


  /**
   * @param toolbarConfig
   * @param controller
   * @param dvAttrListFieldset
   * @return
   */
  public static Node ellipsisActionsButton(List<VLViewComponentXML> moreActionsConfig, AbstractViewController controller, final CriteriaContext criteriaContext, Node contextualTo) {

    final EllipsisActionButton ellipsisButton = new EllipsisActionButton();
    ellipsisButton.setVerlical();

    if (moreActionsConfig != null && !moreActionsConfig.isEmpty()) {
      ellipsisButton.build(moreActionsConfig, controller, criteriaContext, contextualTo);
    }

    return ellipsisButton;
  }


  /**
   * Build toolbar on a row of a form.
   *
   * @param controller
   * @param inlineActionsCfg
   * @param vlInputComponentWrapper
   * @return {@link Node}
   */
  public static FormInlineToobar formInlineToolbar(AbstractViewController controller, VLViewComponentXML inlineActionsCfg, FormFieldsetRow row, int indexInRow) {
    FormInlineToobar formInlineToolbar = (FormInlineToobar) Services.getBean("FormInlineToobar");
    formInlineToolbar.setForRow(row);
    formInlineToolbar.setForIndex(indexInRow);
    formInlineToolbar.buildFrom(controller, inlineActionsCfg);

    // nothing found but something declared
    if (formInlineToolbar.getChildren().size() == 0) {
      return null;
    }

    return formInlineToolbar;
  }
}
