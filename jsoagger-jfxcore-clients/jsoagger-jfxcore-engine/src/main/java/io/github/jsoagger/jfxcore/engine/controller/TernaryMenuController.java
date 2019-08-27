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

package io.github.jsoagger.jfxcore.engine.controller;




import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.menu.ternary.TernaryMenuTab;
import io.github.jsoagger.jfxcore.engine.components.menu.ternary.TernaryMenuTabPane;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.events.CloseMenuEvent;
import io.github.jsoagger.jfxcore.engine.events.MenuPos;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.scene.CacheHint;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TernaryMenuController extends StandardViewController {

  /*-----------------------------------------------------------------------------
  | static fields
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  /** The processed content */
  protected TernaryMenuTabPane rootPane;


  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  /**
   *
   * Constructor
   */
  public TernaryMenuController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void process() {
    try {
      VLViewComponentXML root = getRootComponent();
      rootPane = new TernaryMenuTabPane();
      rootPane.buildFrom(this, root);

      rootPane.setCache(true);
      rootPane.setCacheHint(CacheHint.QUALITY);
      processedView(rootPane);

      VLViewComponentXML tabs = root.getComponentById("Tabs").orElse(null);
      if ((tabs != null) && tabs.hasSubComponent()) {
        for (final VLViewComponentXML tabDefinition : tabs.getSubcomponents()) {
          TernaryMenuTab tab = new TernaryMenuTab();
          tab.buildFrom(this, tabDefinition);
          rootPane.addTab(tab);
        }
      }

      // Pin content button
      final Hyperlink pinOrCloseBtn = (Hyperlink) getPinOrCloseButton();
      rootPane.setPinContentActionButton(pinOrCloseBtn);
      pinOrCloseBtn.setVisible(true);

      // select the first tab
      rootPane.selectFirstTab();

      // display only pin/close button when the mouse is in it
      rootPane.setOnMouseEntered(e -> pinOrCloseBtn.setVisible(true));
      rootPane.setOnMouseExited(e -> pinOrCloseBtn.setVisible(false));
    } catch (Exception ex) {
      // TODO: handle exception
      ex.printStackTrace();
    }
  }


  /**
   * @return
   */
  protected ButtonBase getPinOrCloseButton() {
    Hyperlink button = new Hyperlink();
    IconUtils.setFontIcon("mdi-close:22", button);
    button.getGraphic().getStyleClass().addAll("red-ikonli", "transparent-focus");
    button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

    button.setOnAction(e -> {
      CloseMenuEvent closeMenuEvent = new CloseMenuEvent(MenuPos.TERNARY_MENU);
      dispatchEvent(closeMenuEvent);
    });

    return button;
  }
}
