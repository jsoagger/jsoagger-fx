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

package io.github.jsoagger.jfxcore.engine.components.listform;




import java.util.List;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.list.impl.AbstractListCell;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * @author Administrator
 *
 */
public class SearchableListFormItemPresenter extends PreferenceItemPresenterFactory implements IListFormCellPresenter {

  private String title;
  private String key;
  private String context;
  private boolean multipleSection;
  private final ToggleGroup group = new ToggleGroup();
  private final ObservableList<IListFormValue> selectedValues = FXCollections.observableArrayList();
  private ListFormCellFactory cell;


  public SearchableListFormItemPresenter() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public ListFormCellFactory getCell() {
    return cell;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setCell(AbstractListCell cell) {
    this.cell = (ListFormCellFactory) cell;
  }


  @Override
  public String getOwner() {
    return "SYSTEM";
  }


  @Override
  public String getKey() {
    return key;
  }


  @Override
  public String getContext() {
    return context;
  }


  @Override
  public void setContext(String context) {
    this.context = context;
  }


  @Override
  public void setKey(String key) {
    this.key = key;
  }


  private void loadSearchPane() {
    final ListView listView = cell.getListView();

    final HBox box = new HBox();
    box.setSpacing(32);

    final Hyperlink label = new Hyperlink(this.label.getText() + ": ");
    box.getChildren().add(label);
    label.setOnAction(e -> loadSearchPane());

    final HBox currentSelected = new HBox();
    currentSelected.setSpacing(32);
    //    cell.getRootView().getChildren().add(currentSelected);

    final HBox searchWrapper = new HBox();
    final TextField searchField = new TextField();
    final Button searchButton = new Button("Search");
    HBox.setHgrow(searchField, Priority.ALWAYS);
    searchWrapper.getChildren().addAll(searchField, searchButton);
    //cell.getRootView().getChildren().add(searchWrapper);

    final HBox actionsBox = new HBox();
    final Button save = new Button("Save");
    final Button back = new Button("Back");
    back.setOnAction(e -> {
      //cell.getRootView().getChildren().clear();
      //cell.getRootView().getChildren().add(listView);
    });
    save.setOnAction(e -> {
      //cell.getRootView().getChildren().clear();
      //cell.getRootView().getChildren().add(listView);
    });

    actionsBox.getChildren().addAll(save, back);
    // cell.getRootView().getChildren().add(actionsBox);
  }


  final HBox box = new HBox();


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    box.setSpacing(32);
    box.getChildren().add(label);
    box.addEventFilter(MouseEvent.MOUSE_CLICKED, ev -> loadSearchPane());

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    box.getChildren().add(spacer);

    final Label valueLabel = new Label();
    valueLabel.setOpacity(0.24);
    box.getChildren().add(valueLabel);

    final Hyperlink icon = new Hyperlink();
    IconUtils.setFontIcon("mdi-chevron-right:22", icon);
    box.getChildren().add(icon);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return box;
  }


  @Override
  public void processUpdate(List<IListFormValue> selected) {

  }
}
