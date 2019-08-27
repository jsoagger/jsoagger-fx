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

package io.github.jsoagger.jfxcore.engine.components.pagination;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A pagination bar.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class PaginationBar implements IPaginationBar {

  protected IPageable pageable;
  protected SimpleObjectProperty<MultipleResult> model = new SimpleObjectProperty<>();

  /** Items count label displayed on bottom */
  protected final String totalElementCountFormat = "Page %s/%s - %s entries";
  protected Label itemsCount = new Label();

  /** Items Per Page: V[Combo] */
  protected Label rowsPerPageLabel = new Label();
  protected ComboBox<String> rowsPerPageCombo = new ComboBox();

  protected final SimpleBooleanProperty hasNext = new SimpleBooleanProperty(false);
  protected final SimpleBooleanProperty hasPrevious = new SimpleBooleanProperty(false);

  protected HBox layout = new HBox();


  /**
   * Constructor
   */
  public PaginationBar() {
    rowsPerPageLabel.visibleProperty().bind(rowsPerPageCombo.visibleProperty());
    rowsPerPageCombo.managedProperty().bind(rowsPerPageCombo.visibleProperty());

    rowsPerPageCombo.getStyleClass().addAll("simple-pagination-combo");
    model.addListener((ChangeListener<MultipleResult>) (observable, oldValue, newValue) -> {
      modelUpdated();
    });

    rowsPerPageCombo.getItems().addAll("5", "10", "15", "20");
    rowsPerPageCombo.getSelectionModel().selectFirst();
    rowsPerPageCombo.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
      firstPage();
    });
  }


  /**
   * Get current size of page to load.
   *
   * @return
   */
  @Override
  public int getCurrentPageSize() {
    final String item = rowsPerPageCombo.getSelectionModel().getSelectedItem();
    return Integer.parseInt(item);
  }


  /**
   * Set the value of current page size selction.
   *
   * @param size
   */
  @Override
  public void setCurrentPageSize(String size) {
    for (final String i : rowsPerPageCombo.getItems()) {
      if (i.equalsIgnoreCase(size)) {
        rowsPerPageCombo.getSelectionModel().select(i);
        break;
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    NodeHelper.styleClassSetAll(rowsPerPageLabel, configuration, "resultStyleClass", "simple-pagination-label");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setNoContent() {
    getDisplay().setVisible(false);
  }


  /**
   * Go to next page
   */
  public void nextPage() {
    pageable.nextPage(model);
  }


  /**
   * Go to previous page
   */
  public void previousPage() {
    pageable.previousPage(model);
  }


  /**
   * Go to first page
   */
  public void firstPage() {
    if (model != null && model.get() != null) {
      model.get().addMetaData("pageSize", getCurrentPageSize());
      pageable.firstPage(model);
    }
  }


  /**
   * Go to last page
   */
  public void lastPage() {
    pageable.lastPage(model);
  }


  /**
   * Model updated
   */
  public void modelUpdated() {
    if ((model == null) || (model.get() == null)) {
      hasNext.set(false);
      hasPrevious.set(false);
      setNoContent();
    }

    final MultipleResult result = model.get();
    if ((result == null) || !result.hasElements()) {
      hasNext.set(false);
      hasPrevious.set(false);
      rowsPerPageCombo.setVisible(false);
      setNoContent();
    } else {
      getDisplay().setVisible(true);
      rowsPerPageCombo.setVisible(result.pageElements() > 0);
      if (result.pageElements() > 0) {
        final int curPage = result.getMetaDataIntValue("pageNumber") + 1;
        final String label = String.format(totalElementCountFormat,
            curPage,
            Double.valueOf(String.valueOf(result.getMetaData().get("totalPages"))).intValue(),
            doubleToString(String.valueOf(result.totaElements())));

        Platform.runLater(() -> rowsPerPageLabel.textProperty().set(label));

        hasNext.set(result.hasNext());
        hasPrevious.set(result.hasPrevious());
      }
    }
  }

  private String doubleToString(String d) {
    return String.valueOf(Double.valueOf(d).intValue());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return layout;
  }


  /**
   * Getter of pageable
   *
   * @return the pageable
   */
  public IPageable getPageable() {
    return pageable;
  }


  /**
   * Setter of pageable
   *
   * @param pageable the pageable to set
   */
  @Override
  public void setPageable(IPageable pageable) {
    this.pageable = pageable;
  }


  /**
   * @return
   */
  public final SimpleObjectProperty<MultipleResult> modelProperty() {
    return this.model;
  }


  /**
   * @return
   */
  public final io.github.jsoagger.core.bridge.result.MultipleResult getModel() {
    return this.modelProperty().get();
  }


  /**
   * @param model
   */
  @Override
  public final void setModel(final io.github.jsoagger.core.bridge.result.MultipleResult model) {
    this.modelProperty().set(model);
  }
}
