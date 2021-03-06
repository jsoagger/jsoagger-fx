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



import io.github.jsoagger.jfxcore.api.IModifiableToolbarHolder;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class BasicHToolbar extends AbstractHToolbar {

  /*-----------------------------------------------------------------------------
  | STATIC FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | PRIVATE ATTRIBUTES
   *=============================================================================*/
  protected final CheckBox thickButton = new CheckBox();
  protected boolean isThicked = false;

  protected SimpleBooleanProperty selectable = new SimpleBooleanProperty();


  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
   *=============================================================================*/

  /**
   * Constructor
   */
  public BasicHToolbar() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(AbstractViewController controller, IToolbarHolder toolbarHolder) {
    super.buildFrom(controller, toolbarHolder);

    boolean selectableProp = configuration.getBooleanProperty("selectable");
    this.selectable.set(selectableProp);


    System.out.println(">>>>>>>>>> 11111111");
    if (ellypisMenu != null) {
      allOverWrapper.getChildren().add(ellypisMenu);
    }
  }


  /**
   */
  protected void thickClicked() {
    if (!isThicked) {
      isThicked = true;

      // select all cells
      ((IModifiableToolbarHolder) toolbarHolder).selectCheckboxes(true);
    } else {
      isThicked = false;
      // unselect all select cells
      ((IModifiableToolbarHolder) toolbarHolder).selectCheckboxes(false);
    }
  }

}
