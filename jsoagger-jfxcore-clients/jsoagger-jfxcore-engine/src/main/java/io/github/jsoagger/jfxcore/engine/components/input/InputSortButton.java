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

package io.github.jsoagger.jfxcore.engine.components.input;



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class InputSortButton extends InputHyperlink {

  private SimpleObjectProperty<InputSortButtonState> state = new SimpleObjectProperty<>(InputSortButtonState.NONE);

  static PseudoClass none = PseudoClass.getPseudoClass("none");
  static PseudoClass down = PseudoClass.getPseudoClass("down");
  static PseudoClass up = PseudoClass.getPseudoClass("up");


  /**
   * Constructor
   */
  public InputSortButton() {
    super();
    state.addListener((ChangeListener<InputSortButtonState>) (observable, oldValue, newValue) -> {
      if (newValue == InputSortButtonState.NONE) {
        getDisplay().pseudoClassStateChanged(none, true);
        getDisplay().pseudoClassStateChanged(down, false);
        getDisplay().pseudoClassStateChanged(up, false);
      }

      else if (newValue == InputSortButtonState.UP) {
        getDisplay().pseudoClassStateChanged(none, false);
        getDisplay().pseudoClassStateChanged(down, false);
        getDisplay().pseudoClassStateChanged(up, true);
      }

      else if (newValue == InputSortButtonState.DOWN) {
        getDisplay().pseudoClassStateChanged(none, false);
        getDisplay().pseudoClassStateChanged(down, true);
        getDisplay().pseudoClassStateChanged(up, false);
      }
    });
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
  }


  @Override
  public void taskCompleted(Object arg, Throwable ex) {
    super.taskCompleted(arg, ex);
  }


  public InputSortButtonState getState() {
    return state.get();
  }


  public void setState(InputSortButtonState state) {
    this.state.set(state);
  }

  /**
   * @author Ramilafananana  VONJISOA
   *
   */
  public enum InputSortButtonState {

    UP("up"), DOWN("down"), NONE("none");

    private String state;


    private InputSortButtonState(String state) {
      this.state = state;
    }


    public String getState() {
      return state;
    }


    public void setState(String state) {
      this.state = state;
    }
  }
}
