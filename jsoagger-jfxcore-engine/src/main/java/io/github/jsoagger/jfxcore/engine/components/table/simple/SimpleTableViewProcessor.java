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

package io.github.jsoagger.jfxcore.engine.components.table.simple;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IComponent;
import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.table.cell.ActionnableCell;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SimpleTableViewProcessor implements IComponentProcessor {

  private static final String COLUMNS_REL_PATH = "Columns";
  private static final String COLUMN_GRAPHIC_REL_PATH = "Graphic";


  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML configuration) {
    final SimpleTableView simpleTableView = new SimpleTableView<>((AbstractViewController)controller, configuration);

    // process data loader
    // final String dataloader =
    // configuration.getPropertyValue(XMLConstants.DATA_LOADER);
    // if (StringUtils.isNotBlank(dataloader)) {
    // try {
    // final Function dataloaderFunc = (Function)
    // ReflectionUIUtils.invokeGetterOn(controller, dataloader);
    // simpleTableView.setDataLoader(dataloaderFunc);
    // }
    // catch (final Exception e) {
    // e.printStackTrace();
    // }
    // }
    final List<TableColumnBase> cols = getTableColumns((AbstractViewController) controller, configuration, TableColumn.class, simpleTableView.getTableView());
    simpleTableView.setTableColumns(cols);
    controller.addIComponent((IComponent) simpleTableView);

    return simpleTableView;
  }


  public static List<TableColumnBase> getTableColumns(final AbstractViewController controller, final VLViewComponentXML tableDefinition, Class<? extends TableColumnBase> columnClass,
      TableView tableView) {

    // First resolve all table columns referenced
    final VLViewComponentXML toResolveColumnsCfg = tableDefinition.getComponentById(COLUMNS_REL_PATH).orElse(null);
    final List<VLViewComponentXML> resolveColsCfg = ComponentUtils.resolveDefinitions(controller, toResolveColumnsCfg.getSubcomponents());

    final List<TableColumnBase> tableColumns = new ArrayList<>();
    for (final VLViewComponentXML columnConfig : resolveColsCfg) {

      // final TableColumnBase tableColumn = (TableColumnBase)
      // ReflectionUtils.newInstance(columnClass
      // .getCanonicalName());
      final TableColumnBase tableColumn = new TableColumn<>();
      tableColumn.setId(columnConfig.getId());

      // css
      final String css = columnConfig.getPropertyValue("styleClass");
      if (StringUtils.isNotBlank(css)) {
        for(String e: css.split(",")) {
          tableColumn.getStyleClass().add(e);
        }
      }

      // visibility
      final boolean visible = columnConfig.getBooleanProperty(XMLConstants.VISIBLE, true);
      tableColumn.setVisible(visible);

      // label
      String labelString = NodeHelper.getLabel(columnConfig, controller);
      tableColumn.setText(labelString.toUpperCase());

      // cell width
      final String prefWidth = columnConfig.getPropertyValue(XMLConstants.PREF_WIDTH);
      if (!StringUtils.isEmpty(prefWidth)) {
        tableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(Double.valueOf(prefWidth)));
      } else {
        tableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(Double.valueOf(0.10)));
      }

      // sortable
      final boolean sortable = columnConfig.getBooleanProperty(XMLConstants.SORTABLE);
      tableColumn.setSortable(sortable);

      // cell value factory
      final String valueFactory = columnConfig.getPropertyValue(XMLConstants.CELL_VALUE_FACTORY);
      if (!StringUtils.isEmpty(valueFactory)) {
        if (tableColumn instanceof TableColumn) {
          ((TableColumn) tableColumn).setCellValueFactory(new PropertyValueFactory<>(valueFactory));
        }

        if (tableColumn instanceof TreeTableColumn) {
          ((TreeTableColumn) tableColumn).setCellValueFactory(new TreeItemPropertyValueFactory(valueFactory));
        }
      }

      // cell factory
      final String cellFactory = columnConfig.getPropertyValue(XMLConstants.CELL_FACTORY);
      if (!StringUtils.isEmpty(cellFactory)) {

        if (tableColumn instanceof TableColumn) {
          ((TableColumn) tableColumn).setCellFactory(arg0 -> {

            Object cell = null;

            try {

              final Class clazz = Class.forName(cellFactory);

              // cell may be an actionnable or something else
              if (ActionnableCell.class.isAssignableFrom(clazz)) {
                try {

                  final Constructor constructor = clazz.getConstructor(new Class[] {AbstractViewController.class, VLViewComponentXML.class});
                  cell = constructor.newInstance(controller, columnConfig);

                } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e1) {
                  e1.printStackTrace();
                }
              } else {

                cell = clazz.newInstance();
              }

              return (TableCell) cell;
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e2) {
              e2.printStackTrace();
            }
            return null;
          });
        }

        if (tableColumn instanceof TreeTableColumn) {
          ((TreeTableColumn) tableColumn).setCellFactory(arg0 -> {

            Object cell = null;

            try {

              final Class clazz = Class.forName(cellFactory);

              try {

                final Constructor constructor = clazz.getConstructor(new Class[] {AbstractViewController.class, VLViewComponentXML.class});
                cell = constructor.newInstance(controller, columnConfig);

              } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e1) {
                e1.printStackTrace();
              }

              return (TreeTableCell) cell;
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e2) {
              e2.printStackTrace();
            }
            return null;
          });
        }

        // graphic
        final VLViewComponentXML graphicCong = columnConfig.getComponentById(COLUMN_GRAPHIC_REL_PATH).orElse(null);
        if (graphicCong != null) {
          final String graphicClass = graphicCong.getPropertyValue(XMLConstants.CLASS);
          final ButtonBase node = (ButtonBase) ReflectionUIUtils.newInstance(graphicClass);
          ComponentToButtonBaseHelper.setOnAction(graphicCong, node, controller);
          tableColumn.setGraphic(node);
        }
      }

      tableColumns.add(tableColumn);
    }

    return tableColumns;
  }
}
