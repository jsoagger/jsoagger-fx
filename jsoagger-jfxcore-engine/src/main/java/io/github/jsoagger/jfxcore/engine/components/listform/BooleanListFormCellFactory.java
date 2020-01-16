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




import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.list.impl.AbstractListCell;
import io.github.jsoagger.jfxcore.engine.components.tab.PopTabContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ViewForwardLayoutManager;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class BooleanListFormCellFactory extends AbstractListCell<IListFormValue> {

  /**
   * Constructor
   */
  public BooleanListFormCellFactory() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateItem(IListFormValue item, boolean empty) {
    super.updateItem(item, empty);
    setGraphic(null);
    setText(null);

    if (item != null) {
      final HBox box = new HBox();
      box.getStyleClass().add("hand-hover");
      box.setStyle("-fx-alignment:CENTER_LEFT;" + "-fx-spacing: 10;" + "-fx-padding: 0 0 0 16;");
      if (item.preferenceItem() == null || item.preferenceItem().isMultipleSection()) {
        final CheckBox checkBox = new JFXCheckBox();
        checkBox.allowIndeterminateProperty().set(false);
        checkBox.setSelected(item.selectedProperty().get());
        item.selectedProperty().bind(checkBox.selectedProperty());

        box.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
          checkBox.fire();
          processDataUpdate(item, false);
        });

        box.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
          checkBox.fire();
          processDataUpdate(item, false);
        });

        final Label label = new Label();
        label.setText(item.getDisplayedValue());
        box.getChildren().addAll(label, NodeHelper.horizontalSpacer(), checkBox);
        label.getStyleClass().add("ep-list-form-data-value");

      } else {
        final RadioButton button = new JFXRadioButton();
        button.setToggleGroup(item.preferenceItem().getToggleGroup());

        final boolean selected = item.selectedProperty().get();
        button.selectedProperty().set(selected);
        item.selectedProperty().bind(button.selectedProperty());

        final Label label = new Label();
        label.getStyleClass().add("ep-list-form-data-value");
        label.setText(item.getDisplayedValue());
        box.getChildren().addAll(label, NodeHelper.horizontalSpacer(), button);


        // !! update when mouse is click not when select change because
        // table upate cells all time
        box.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
          button.fire();
          processDataUpdate(item, true);
        });
        box.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
          button.fire();
          processDataUpdate(item, true);
        });

        setGraphic(box);
      }
    }
  }


  /**
   * Process update of data
   */
  private void processDataUpdate(IListFormValue item, boolean goback) {
    List<IListFormValue> s = new ArrayList<>();
    for(IListFormValue e1: getListView().getItems()) {
      if (e1.selectedProperty().get()) {
        s.add(e1);
      }
    }

    item.preferenceItem().processUpdate(s);

    // if desktop self, pop layoutmanager
    AbstractViewController parent = controller.getParent();
    boolean desktopselfView =  isSelfView(parent) && AbstractApplicationRunner.isDesktop();
    if(desktopselfView) {
      StandardViewController c = (StandardViewController) parent.getParent();
      IViewLayoutManager m = c != null ?  c.getLayoutManager() : null;
      if(m != null && m instanceof ViewForwardLayoutManager) {
        ((ViewForwardLayoutManager)m).popContent();
      }
    }
    else {
      if (controller.getParent() != null && controller.getParent() instanceof StandardTabPaneController) {
        ((StandardTabPaneController) controller.getParent()).handle(new PopTabContentEvent());
      } else if (goback) {
        NodeHelper.goBack(controller);
      }
    }
  }

  /**
   *
   * @param controller
   * @return
   */
  private boolean isSelfView(AbstractViewController controller) {
    if(controller != null) {
      for(String def: controller.getViewDefinitions()) {
        if(def.contains("Self")) return true;
      }
    }
    return false;
  }
}
