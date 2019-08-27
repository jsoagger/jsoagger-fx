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

package io.github.jsoagger.jfxcore.engine.components.toolbar.inline;



import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormInlineToobar extends HBox implements IBuildable {

  protected FormFieldsetRow row;
  protected int callerIndex = 0;
  protected InlineAction defaultInlineAction;

  /**
   * Constructor
   */
  public FormInlineToobar() {
    getStyleClass().add("form-inline-toolbar");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    if (configuration != null && configuration.hasSubComponent()) {

      int index = 0;

      for (VLViewComponentXML refbtnConfig : configuration.getSubcomponents()) {
        VLViewComponentXML btnConfig = ComponentUtils.resolveDefinition((AbstractViewController)controller, refbtnConfig.getReference()).orElse(null);
        if (btnConfig != null) {
          String type = btnConfig.getPropertyValue("type", "FormInlineAction");
          InlineAction action = (InlineAction) ComponentUtils.build(type, (AbstractViewController)controller, btnConfig);
          action.setRow(row, callerIndex);
          getChildren().add(action.getDisplay());

          if(index == 0) {
            defaultInlineAction = action;
          }

          index++;
        }
      }
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
   * @param row
   */
  public void setForRow(FormFieldsetRow row) {
    this.row = row;
  }


  public void setForIndex(int indexInRow) {
    this.callerIndex = indexInRow;
  }


  /**
   * @return the defaultInlineAction
   */
  public InlineAction getDefaultInlineAction() {
    return defaultInlineAction;
  }


  /**
   * @param defaultInlineAction the defaultInlineAction to set
   */
  public void setDefaultInlineAction(InlineAction defaultInlineAction) {
    this.defaultInlineAction = defaultInlineAction;
  }
}
