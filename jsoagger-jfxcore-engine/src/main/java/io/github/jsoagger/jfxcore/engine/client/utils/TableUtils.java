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

package io.github.jsoagger.jfxcore.engine.client.utils;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class TableUtils {

  public static int getColumnAt(TableView tableView, Point2D point) {
    TableCell selected = getTableCellAt(tableView, point);
    if (selected == null) {
      return -1;
    }
    return tableView.getColumns().indexOf(selected.getTableColumn());
  }


  public static int getRowAt(TableView<?> tableView, Point2D point) {
    TableCell<?, ?> selected = getTableCellAt(tableView, point);
    if (selected == null) {
      return -1;
    }
    return selected.getTableRow().getIndex();
  }


  private static TableCell<?, ?> getTableCellAt(TableView<?> tableView, Point2D point) {
    point = tableView.localToScene(point);
    Set<Node> lookupAll = getTableCells(tableView);
    TableCell<?, ?> selected = null;
    for (Node cellNode : lookupAll) {
      Bounds boundsInScene = cellNode.localToScene(cellNode.getBoundsInLocal(), true);
      if (boundsInScene.contains(point)) {
        selected = (TableCell<?, ?>) cellNode;
        break;
      }
    }
    return selected;
  }


  private static Set<Node> getTableCells(TableView<?> tableView) {
    Set<Node> l = tableView.lookupAll("*");
    Set<Node> r = new HashSet<>();
    for (Node node : l) {
      if (node instanceof TableCell<?, ?>) {
        r.add(node);
      }
    }
    return r;
  }


  public static String getColumnName(TableView<?> tableView, int i) {
    ObservableList<?> columns = tableView.getColumns();
    TableColumn<?, ?> tableColumn = (TableColumn<?, ?>) columns.get(i);
    return tableColumn.getText();
  }


  public static String getTableCellText(TableView<?> tableView, int row, int column) {
    if (column == -1 || row == -1) {
      return null;
    }
    String scolumn = getColumnName(tableView, column);
    if (scolumn == null || "".equals(scolumn)) {
      scolumn = "" + column;
    }
    // return new JsonObject().addProperty("cell", new
    // JSONArray().add(row).add(getColumnName(tableView,
    // column))).toString();
    return null;
  }


  public static TableCell<?, ?> getCellAt(TableView<?> tableView, int row, int column) {
    TableCell<?, ?> cell = getVisibleCellAt(tableView, row, column);
    if (cell != null) {
      return cell;
    }

    TableColumn tableColumn = tableView.getColumns().get(column);
    cell = (TableCell) tableColumn.getCellFactory().call(tableColumn);
    ObservableValue cellObservableValue = tableColumn.getCellObservableValue(row);
    if (cellObservableValue == null)
      return null;
    Object value = cellObservableValue.getValue();
    Method updateItem;
    try {
      updateItem = cell.getClass().getDeclaredMethod("updateItem", new Class[] {Object.class, Boolean.TYPE});
      updateItem.setAccessible(true);
      updateItem.invoke(cell, value, false);
      return cell;
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }


  public static TableCell<?, ?> getVisibleCellAt(TableView<?> tableView, int row, int column) {
    Set<Node> lookupAll = getTableCells(tableView);
    TableCell<?, ?> cell = null;
    for (Node node : lookupAll) {
      TableCell<?, ?> cell1 = (TableCell<?, ?>) node;
      TableRow<?> tableRow = cell1.getTableRow();
      TableColumn<?, ?> tableColumn = cell1.getTableColumn();
      if (tableRow.getIndex() == row && tableColumn == tableView.getColumns().get(column)) {
        cell = cell1;
        break;
      }
    }
    if (cell != null) {
      return cell;
    }
    return null;
  }


  public static Point2D getPoint(TableView<?> tableView, int columnIndex, int rowIndex) {
    Set<Node> tableRowCell = tableView.lookupAll(".table-row-cell");
    TableRow<?> row = null;
    for (Node tableRow : tableRowCell) {
      TableRow<?> r = (TableRow<?>) tableRow;
      if (r.getIndex() == rowIndex) {
        row = r;
        break;
      }
    }
    Set<Node> cells = row.lookupAll(".table-cell");
    for (Node node : cells) {
      TableCell<?, ?> cell = (TableCell<?, ?>) node;
      if (tableView.getColumns().indexOf(cell.getTableColumn()) == columnIndex) {
        Bounds bounds = cell.getBoundsInParent();
        Point2D localToParent = cell.localToParent(bounds.getWidth() / 2, bounds.getHeight() / 2);
        Point2D rowLocal = row.localToScene(localToParent, true);
        return rowLocal;
      }
    }
    return null;
  }


  public static String getSelection(TableView<?> tableView) {
    TableViewSelectionModel<?> selectionModel = tableView.getSelectionModel();
    if (!selectionModel.isCellSelectionEnabled()) {
      ObservableList<Integer> selectedIndices = selectionModel.getSelectedIndices();
      if (tableView.getItems().size() == selectedIndices.size()) {
        return "all";
      }
      if (selectedIndices.size() == 0) {
        return "";
      }
      return getRowSelectionText(selectedIndices);
    }

    @SuppressWarnings("rawtypes")
    ObservableList<TablePosition> selectedCells = selectionModel.getSelectedCells();
    int[] rows = new int[selectedCells.size()];
    int[] columns = new int[selectedCells.size()];
    int rowCount = tableView.getItems().size();
    int columnCount = tableView.getColumns().size();

    if (selectedCells.size() == rowCount * columnCount) {
      return "all";
    }

    if (selectedCells.size() == 0) {
      return "";
    }
    JsonObject cells = new JsonObject();
    List value = new ArrayList<>();
    for (int i = 0; i < selectedCells.size(); i++) {
      TablePosition<?, ?> cell = selectedCells.get(i);
      rows[i] = cell.getRow();
      columns[i] = cell.getColumn();
      List<String> cellValue = new ArrayList<>();
      cellValue.add(cell.getRow() + "");
      cellValue.add(getColumnName(tableView, cell.getColumn()));
      value.add(cellValue);
    }
    cells.addProperty("cells", value.toString());
    return cells.toString();
  }


  public static List<String> getSelectedColumnText(TableView<?> tableView, int[] columns) {
    List<String> text = new ArrayList<>();
    for (int column : columns) {
      String columnName = getColumnName(tableView, column);
      text.add(escapeSpecialCharacters(columnName));
    }
    return text;
  }


  protected static String escapeSpecialCharacters(String name) {
    return name.replaceAll("/", "\\\\/");
  }


  public static String getRowSelectionText(ObservableList<Integer> selectedIndices) {
    JsonObject pa = new JsonObject();
    //return pa.("rows", selectedIndices).toString();
    return selectedIndices.toString();
  }


  @SuppressWarnings("unchecked")
  public static void selectCells(TableView<?> tableView, String value) {
    @SuppressWarnings("rawtypes")
    TableViewSelectionModel selectionModel = tableView.getSelectionModel();
    selectionModel.clearSelection();
    // JsonObject cells = new JsonObject(value);
    JsonObject cells = new JsonObject();
    String object =  cells.get("cells").getAsString();
    for (int i = 0; i < object.split(";").length; i++) {
      int rowIndex = Integer.parseInt(object.split(";")[0]);
      // int columnIndex = getColumnIndex(jsonArray.getString(1));
      @SuppressWarnings("rawtypes")
      TableColumn column = tableView.getColumns().get(0);
      if (getVisibleCellAt(tableView, rowIndex, 0) == null) {
        tableView.scrollTo(rowIndex);
        tableView.scrollToColumn(column);
      }
      selectionModel.select(rowIndex, column);
    }
  }
}
