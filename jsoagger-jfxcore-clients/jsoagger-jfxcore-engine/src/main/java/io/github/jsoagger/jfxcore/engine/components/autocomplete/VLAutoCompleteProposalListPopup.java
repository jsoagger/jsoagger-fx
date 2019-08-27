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




import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.stage.Window;

/**
 * This is the {@link PopupControl} displaying inside it the {@link ListView} of items proposed by
 * custom autocomplete.
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class VLAutoCompleteProposalListPopup extends PopupControl {

  /***************************************************************************
   * * Private fields *
   **************************************************************************/
  private final VLAutocomplete control;
  /**
   * The maximum number of rows to be visible in the popup when it is showing. By default this value
   * is 10, but this can be changed to increase or decrease the height of the popup.
   */
  private final IntegerProperty visibleRowCount = new SimpleIntegerProperty(this, "visibleRowCount", 10);


  /***************************************************************************
   * * Constructors * *
   **************************************************************************/

  /**
   * Creates a new AutoCompletePopup
   */
  public VLAutoCompleteProposalListPopup(VLAutocomplete control) {
    this.control = control;
    setAutoFix(true);
    setAutoHide(true);
    setHideOnEscape(true);

    getStyleClass().add(DEFAULT_STYLE_CLASS);
  }


  /***************************************************************************
   * * Public API * *
   **************************************************************************/

  /**
   * Show this popup right below the given Node
   * 
   * @param node
   */
  public void show(Node node) {

    if (node.getScene() == null || node.getScene().getWindow() == null) {
      throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window.");
    } // $NON-NLS-1$

    if (isShowing()) {
      return;
    }

    final Window parent = node.getScene().getWindow();
    this.show(parent, parent.getX() + node.localToScene(0, 0).getX() + node.getScene().getX(), parent.getY() + node.localToScene(0, 0).getY() + node.getScene().getY() + control.getHeight());

  }


  public final void setVisibleRowCount(int value) {
    visibleRowCount.set(value);
  }


  public final int getVisibleRowCount() {
    return visibleRowCount.get();
  }


  public final IntegerProperty visibleRowCountProperty() {
    return visibleRowCount;
  }

  /***************************************************************************
   * * Stylesheet Handling *
   **************************************************************************/

  public static final String DEFAULT_STYLE_CLASS = "auto-complete-popup"; //$NON-NLS-1$


  @Override
  protected Skin<?> createDefaultSkin() {
    return new VLAutoCompletePopupSkin(control);
  }
}
