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

package io.github.jsoagger.jfxcore.engine.components.table.cell;



import java.lang.reflect.Method;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class ActionnableCell extends TableCell {

  protected final AbstractViewController controller;
  protected final VLViewComponentXML cellConfig;


  /**
   * Constructor
   *
   * @param controller
   */
  public ActionnableCell(AbstractViewController controller, VLViewComponentXML cellConfig) {
    super();
    this.controller = controller;
    this.cellConfig = cellConfig;
  }


  /**
   * Initiate the action associated to the button
   */
  protected void setAction() {

    final String action = cellConfig.getPropertyValue(XMLConstants.PROCEDURE);

    if (StringUtils.isNotBlank(action)) {
      getActionner().addEventFilter(ActionEvent.ACTION, e -> {
        Method method = null;
        try {

          method = controller.getClass().getMethod(action, TableRow.class, TableCell.class);
          method.invoke(controller, getTableRow(), this);

        } catch (final Exception e1) {
          throw new IllegalArgumentException("Error calling procedure with name : " + action + " on class " + controller.getClass(), e1);
        }
      });
    }
  }


  /**
   *
   * @return
   */
  public abstract ButtonBase getActionner();
}
