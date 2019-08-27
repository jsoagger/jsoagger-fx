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
import io.github.jsoagger.jfxcore.api.InlineActionHandler;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class InlineAction implements IBuildable {

  protected Hyperlink action = new Hyperlink();
  protected FormFieldsetRow row;
  protected int callerIndex;

  /**
   * Constructor
   */
  public InlineAction() {}


  protected void onAction(AbstractViewController controller, VLViewComponentXML actionButtonConfig) {
    String handler = actionButtonConfig.getPropertyValue("actionHandler");
    if (handler != null) {
      InlineActionHandler actionHandler = (InlineActionHandler) Services.getBean(handler);
      actionHandler.onInlineAction((IJSoaggerController)controller, actionButtonConfig, row, callerIndex);
    }
  }


  @Override
  public Node getDisplay() {
    return action;
  }


  /**
   * Getter of action
   *
   * @return the action
   */
  public Hyperlink getAction() {
    return action;
  }


  /**
   * Setter of action
   *
   * @param action the action to set
   */
  public void setAction(Hyperlink action) {
    this.action = action;
  }


  /**
   * Getter of row
   *
   * @return the row
   */
  public FormFieldsetRow getRow() {
    return row;
  }


  /**
   * Setter of row
   *
   * @param row the row to set
   * @param callerIndex
   */
  public void setRow(FormFieldsetRow row, int callerIndex) {
    this.row = row;
    this.callerIndex = callerIndex;
  }
}
