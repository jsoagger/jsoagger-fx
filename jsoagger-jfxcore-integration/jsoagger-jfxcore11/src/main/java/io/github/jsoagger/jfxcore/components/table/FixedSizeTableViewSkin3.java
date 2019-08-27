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

package io.github.jsoagger.jfxcore.components.table;




import java.lang.reflect.Method;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
// BEGIN JAVA 9
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TableViewSkin;
// BEGIN JAVA 9
import javafx.scene.layout.Region;

/**
 * @author Administrator
 *
 */
public class FixedSizeTableViewSkin3<T extends TableView> extends TableViewSkin {

  private ScrollBar scrollBarHorizontal;
  private ScrollBar scrollBarVertical;
  private boolean fillWidthCache;
  private double prefWidthCache;
  private Region placeholderRegion;
  private double prefHeightCache;
  private TableHeaderRow tableHeaderRow;
  private boolean showHeader = true;


  public FixedSizeTableViewSkin3(T tableView) {
    super(tableView);
    this.tableHeaderRow = getTableHeaderRow9();
  }


  public FixedSizeTableViewSkin3(T tableView, boolean showHeader) {
    this(tableView);
    this.showHeader = showHeader;

    if (!showHeader && tableHeaderRow != null) {
      tableHeaderRow.setVisible(false);
      tableHeaderRow.setManaged(false);
      tableHeaderRow.setMaxHeight(0);
      tableHeaderRow.setPrefHeight(0);
    }
  }

  public TableHeaderRow getTableHeaderRow9() {
    try {
      Method method = this.getClass().getSuperclass().getSuperclass().getDeclaredMethod("getTableHeaderRow", new Class[0]);
      // this needs --add-open argument on JVM in java9
      // --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED
      method.setAccessible(true);
      return (TableHeaderRow) method.invoke(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
