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

package io.github.jsoagger.jfxcore.engine.components.contextmenu;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.controlsfx.control.PopOver;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

//import org.controlsfx.control.PopOver;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.input.SimpleHyperlinkButton;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ContextMenu { //extends PopOver {

  private final AbstractViewController controller;
  private final List<VLViewComponentXML> contextMenuConfigList;
  private final VBox container = new VBox();
  private List<IBuildable> links = null;
  private CriteriaContext criteriaContext = null;


  /**
   * Constructor
   */
  public ContextMenu(AbstractViewController controller, List<VLViewComponentXML> contextMenuConfigList, CriteriaContext criteriaContext) {

    this.controller = controller;
    this.contextMenuConfigList = contextMenuConfigList;
    this.criteriaContext = criteriaContext;

    /* setArrowLocation(ArrowLocation.TOP_CENTER);
    setDetached(false);
    setArrowSize(0);
    setDetachable(false);
    setAutoHide(true);
    setAutoFix(true);
    setHideOnEscape(true);
    requestFocus();
    setCornerRadius(0);
    setTitle("");
    focusedProperty().addListener((pop, ov, nv) -> {
      if (!nv) {
        // hide();
      }
    });

    container.getStyleClass().add("context-menu-container");
    setContentNode(container);
    buildNodes();*/
  }


  private void buildNodes() {
    // resolve buttons
    final List<VLViewComponentXML> resolved = ComponentUtils.resolveDefinitions(controller, contextMenuConfigList);
    final List<VLViewComponentXML> filtered = new ArrayList<>();

    if (criteriaContext != null) {
      for(VLViewComponentXML comp : resolved) {
        if (comp.isFiltered()) {
          if (criteriaContext.isTrue(comp.getCriteria())) {
            filtered.add(comp);
          }
        } else {
          filtered.add(comp);
        }
      }
    } else {
      filtered.addAll(resolved);
    }

    // generate hyperlink
    links = ComponentUtils.generate(controller, filtered);
    final Iterator<IBuildable> iterator = links.iterator();
    while (iterator.hasNext()) {
      IBuildable next = iterator.next();
      Node display = next.getDisplay();

      if (display instanceof Labeled) {
        ((Labeled) display).setContentDisplay(ContentDisplay.LEFT);
      }

      if (display.isVisible()) {
        final HBox row = new HBox();
        NodeHelper.setHgrow(row);
        row.getStyleClass().add("context-menu-row");

        display.getStyleClass().add("context-menu-label");
        row.getChildren().add(display);

        container.getChildren().add(row);
      }
    }
  }


  /**
   * @param contextualTo
   */
  public void setContextualTo(Node contextualTo) {
    if (contextualTo != null) {
      for(IBuildable e: links) {
        ((SimpleHyperlinkButton) e).setContextualTo(contextualTo);
      }
    }
  }
}
