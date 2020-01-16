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



import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Skin;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class VLAutoCompletePopupSkin implements Skin<VLAutocomplete> {

  private final VLAutocomplete control;
  private final VLAutoCompleteProposalListPopup suggestionList;
  final int LIST_CELL_HEIGHT = 24;


  @SuppressWarnings("unchecked")
  public VLAutoCompletePopupSkin(VLAutocomplete control) {
    this.control = control;
    suggestionList = control.getListPopup();
    //control.getListView().getStylesheets().add(AutoCompletionBinding.class.getResource("autocompletion.css").toExternalForm()); //$NON-NLS-1$
    //control.getListView().getStylesheets().add(AutoCompletionBinding.class.getResource("autocompletion.css").toExternalForm()); //$NON-NLS-1$
    /**
     * Here we bind the prefHeightProperty to the minimum height between the max visible rows and the
     * current items list. We also add an arbitrary 5 number because when we have only one item we have
     * the vertical scrollBar showing for no reason.
     */
    suggestionList.prefHeightProperty().bind(Bindings.min(control.visibleRowCountProperty(), Bindings.size(control.getListView().getItems())).multiply(LIST_CELL_HEIGHT).add(18));

    // Allowing the user to control ListView width.
    suggestionList.prefWidthProperty().bind(control.widthProperty());
    suggestionList.maxWidthProperty().bind(control.maxWidthProperty());
    suggestionList.minWidthProperty().bind(control.minWidthProperty());

    control.getListView().prefWidthProperty().bind(control.widthProperty());
    control.getListView().maxWidthProperty().bind(control.maxWidthProperty());
    control.getListView().minWidthProperty().bind(control.minWidthProperty());
  }


  @Override
  public VLAutocomplete getSkinnable() {
    return control;
  }


  /*
   * (non-Javadoc)
   *
   * @see javafx.scene.control.Skin#getNode()
   */
  @Override
  public Node getNode() {
    return control.getListView();
  }


  /*
   * (non-Javadoc)
   *
   * @see javafx.scene.control.Skin#dispose()
   */
  @Override
  public void dispose() {
    suggestionList.prefWidthProperty().unbind();
    suggestionList.maxWidthProperty().unbind();
    suggestionList.minWidthProperty().unbind();
  }
}
