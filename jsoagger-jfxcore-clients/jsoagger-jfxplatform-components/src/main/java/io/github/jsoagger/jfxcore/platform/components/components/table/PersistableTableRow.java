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

package io.github.jsoagger.jfxcore.platform.components.components.table;



import java.util.List;

import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.platform.components.model.PersistableObjectModel;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableRow;
import javafx.scene.control.Tooltip;

/**
 * A row for a persistable table, this is usefull for branching context menu, tooltips, ... all
 * things specific to a row.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class PersistableTableRow<E extends PersistableObjectModel> extends TableRow<E> {

  private static final String CONTEXT_ACTIONS_REL_PATH = "ContextMenu";

  /** The table wizardConfiguration */
  protected VLViewComponentXML tableConfig;

  /** The controller */
  protected AbstractViewController controller;


  /**
   * Constructor
   *
   * @param config
   * @param viewContext
   */
  public PersistableTableRow(VLViewComponentXML config, AbstractViewController viewContext) {
    super();
    this.tableConfig = config;
    this.controller = viewContext;
  }


  @Override
  protected void updateItem(E entry, boolean paramBoolean) {
    super.updateItem(entry, paramBoolean);

    setTooltip(null);
    setContextMenu(null);
    setItem(entry);

    if (!isEmpty()) {
      final Tooltip toolTip = generateTooltipFor(entry);
      if (toolTip != null) {
        setTooltip(toolTip);
      }

      final ContextMenu contextMenu = gerenateContextMenuFor(entry);
      if (contextMenu != null) {
        setContextMenu(contextMenu);
      }
    }
  }


  /**
   * Generates a tooltip for the given entry. An entry is row in the table
   */
  public abstract Tooltip generateTooltipFor(E entry);


  /**
   * Generates a context menu for a row in the table
   */
  public ContextMenu gerenateContextMenuFor(E entry) {
    // First resolve all table columns referenced
    final List<VLViewComponentXML> resolveColsCfg = null;
    final ContextMenu contextMenu = new ContextMenu();
    return contextMenu;
  }
}
