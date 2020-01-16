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

package io.github.jsoagger.jfxcore.engine.components.autocomplete;




import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Administrator
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class VLAutocomplete<T> extends TextField {

  private final ListView<T> proposalListView = new ListView<>();
  private final ObservableNumberValue visibleRow = new SimpleIntegerProperty(10);
  private VLAutoCompleteProposalListPopup proposalListPopup = null;
  private Function<T, Boolean> itemSelectedFunction = null;
  private Function<Object, List<T>> getProposalsFunction = null;
  private Class proposalListCell;


  /**
   * Constructor
   */
  public VLAutocomplete() {
    proposalListView.setId("item-presenter-listview");
    getStyleClass().add("item-presenter-administrator-search-field");
    // Listen for Slider value changes
    textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
      if (newValue != null && newValue.length() >= 1) {
        search();
      } else {
        proposalListView.getItems().clear();
        if (proposalListPopup.isShowing()) {
          proposalListPopup.hide();
        }
      }
    });

    proposalListPopup = new VLAutoCompleteProposalListPopup(this);
    registerListener();
  }


  /**
   * set proposalListCell
   */
  @SuppressWarnings({"rawtypes"})
  public void setProposalListCell(Class proposalListCell) {
    this.proposalListCell = proposalListCell;
  }


  private void registerListener() {
    addEventFilter(KeyEvent.KEY_RELEASED, key -> {
      if (key.getCode() == KeyCode.ESCAPE) {
        if (proposalListPopup.isShowing()) {
          proposalListPopup.hide();
        }
      }
    });
  }


  private synchronized void search() {
    if (!proposalListPopup.isShowing()) {
      proposalListPopup.show(this);
    }

    proposalListView.getItems().clear();
    Platform.runLater(() -> {
      // final List<Account> persons =
      // UIServicesLocator.getPrincipalsService().getAccountByLoginLike(getText());
      final List<T> proposals = getProposalsFunction.apply(textProperty().get());
      for (final T elm : proposals) {
        proposalListView.getItems().add(elm);
      }
    });
  }


  /**
   * Set the selectedCallbackFuntion
   *
   * @param selectedCallbackFuntion the selectedCallbackFuntion to set
   */
  @SuppressWarnings("rawtypes")
  public void setSelectedCallbackFuntion(Function<T, Boolean> selectedCallbackFuntion) {
    this.itemSelectedFunction = selectedCallbackFuntion;
    proposalListView.setCellFactory(param -> {
      final VLAutoCompleteListCell<T> cell = (VLAutoCompleteListCell<T>) ReflectionUIUtils.newInstance(proposalListCell);
      cell.setSelectedCallBack(t -> {
        final T item = (T) t.getItem();
        proposalListView.getItems().remove(item);
        selectedCallbackFuntion.apply(item);
        return true;
      });
      return cell;
    });
  }


  /**
   *
   * @param function
   */
  public void setLoadProposalsFunction(Function<Object, List<T>> function) {
    this.getProposalsFunction = function;
  }


  /**
   * Get the selectedCallbackFuntion
   *
   * @return the selectedCallbackFuntion
   */
  public Function<T, Boolean> getSelectedCallbackFuntion() {
    return itemSelectedFunction;
  }


  /**
   * Get the popup wrapping the listview
   */
  public VLAutoCompleteProposalListPopup getListPopup() {
    return proposalListPopup;
  }


  /**
   * Get the listview displaying the result
   *
   * @return the
   */
  public ListView<T> getListView() {
    return proposalListView;
  }


  /**
   * @return
   */
  public ObservableNumberValue visibleRowCountProperty() {
    return visibleRow;
  }
}
