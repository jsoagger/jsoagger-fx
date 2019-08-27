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

import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.list.ListViewContent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ViewForwardLayoutManager;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Display checkbox for each cell
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class YesNoListFormCellPresenter extends PreferenceItemPresenterFactory implements IListFormCellPresenter {

  String context;
  VLViewComponentXML configuration;

  CheckBox checkBox = new JFXCheckBox();
  RadioButton radioButton = new JFXRadioButton();
  HBox box = new HBox();

  /** single or multiple. single, on click on one item, other items are unchecked */
  private String mode;

  //SetPreferenceValueOperation
  IOperation setPreferenceValueOperation;

  boolean isStaticValue = false;

  /**
   * Constructor
   */
  public YesNoListFormCellPresenter() {
    super();
    box.getStyleClass().add("hand-hover");
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    mode = getParameters() != null ? (String) getParameters().get("mode") : "single";

    box.getStyleClass().add("item-presenter-row-container");
    box.setSpacing(32);
    box.getChildren().add(label);

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    box.getChildren().add(spacer);


    // value of checkbox/radio can be a static one or true false
    // static means, each cel has declared staticValue in its configuration
    // if no static value, value is true/false
    String staticValue = (String) getForData().getAttributes().get("staticValue");
    isStaticValue = StringUtils.isNotBlank(staticValue);


    boolean selected = false;
    String savedVal = null;
    if (currentValues.size() == 1) {
      IListFormValue val = currentValues.get(0);
      savedVal = val.getSavedValue();
      selected = isStaticValue ? savedVal.equalsIgnoreCase(staticValue) : Boolean.valueOf(savedVal);
    }

    if ("single".equalsIgnoreCase(mode)) {
      box.getChildren().add(radioButton);
      radioButton.setUserData(staticValue);
      radioButton.setToggleGroup(getToggleGroup());
      radioButton.setSelected(selected);

      box.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
        radioButton.fire();
        selectRadio(radioButton, true);
      });
      box.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
        radioButton.fire();
        selectRadio(radioButton, true);
      });

    } else {
      box.getChildren().add(checkBox);
      checkBox.setSelected(selected);
      box.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
        checkBox.fire();
        processDataUpdate(checkBox.selectedProperty().get(), false);
      });
      box.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
        checkBox.fire();
        processDataUpdate(checkBox.selectedProperty().get(), false);
      });
    }
  }

  @Override
  public ToggleGroup getToggleGroup() {
    if(getCell() != null) {
      ListViewContent lvc = (ListViewContent) getCell().getUserData();
      return lvc.getToggleGroup();
    }
    return new ToggleGroup();
  }



  @Override
  public String getOwner() {
    return "SYSTEM";
  }


  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  @Override
  public String getContext() {
    return this.context;
  }


  @Override
  public void setContext(String context) {
    this.context = context;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return box;
  }

  private void processDataUpdate(boolean selected, boolean goback) {
    Object v = isStaticValue ? radioButton.getUserData() : selected;
    List<IListFormValue> s = new ArrayList<>();

    if(currentValues.size() > 0) {
      IListFormValue val = currentValues.get(0);
      val.setSavedValue(v);
      s.add(val);
    }

    JsonObject query = new JsonObject();
    query.addProperty("key", getKey());
    query.addProperty("value", selected);

    setPreferenceValueOperation.doOperation(query, res -> {
      processUpdate(s);
      if (goback)
        NodeHelper.goBack(controller);
    });
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void processUpdate(List<IListFormValue> selected) {
  }

  private void selectRadio(RadioButton radioButton, boolean goback) {
    Object v = isStaticValue ? radioButton.getUserData() : radioButton.selectedProperty().get();
    List<IListFormValue> s = new ArrayList<>();

    if(currentValues.size() > 0) {
      IListFormValue val = currentValues.get(0);
      val.setSavedValue(v);
      s.add(val);
    }

    JsonObject query = new JsonObject();
    query.addProperty("key", getKey());
    query.addProperty("value", v != null ? v.toString() : "");

    setPreferenceValueOperation.doOperation(query, res -> {
      processUpdate(s);
      if (goback) {
        SingleResult sr = (SingleResult) controller.getModel();
        ParentItemPresenter parent = (ParentItemPresenter) sr.getData().getMeta().get("parentItem");
        if (parent != null) {
          parent.updateDisplay();
        }

        // call go back only if we are not in self mode and in desktop
        boolean desktopselfView = parent != null && isSelfView(parent.getController()) && AbstractApplicationRunner.isDesktop();
        if(!desktopselfView) NodeHelper.goBack(controller);

        if(desktopselfView) {
          StandardViewController c = (StandardViewController) parent.getController().getParent();
          IViewLayoutManager m = c != null ?  c.getLayoutManager() : null;
          if(m != null && m instanceof ViewForwardLayoutManager) {
            ((ViewForwardLayoutManager)m).popContent();
          }
        }
      }
    });
  }

  /**
   * @return the setPreferenceValueOperation
   */
  public IOperation getSetPreferenceValueOperation() {
    return setPreferenceValueOperation;
  }

  /**
   * @param setPreferenceValueOperation the setPreferenceValueOperation to set
   */
  public void setSetPreferenceValueOperation(IOperation setPreferenceValueOperation) {
    this.setPreferenceValueOperation = setPreferenceValueOperation;
  }

  /**
   *
   * @param controller
   * @return
   */
  private boolean isSelfView(AbstractViewController controller) {
    for(String def: controller.getViewDefinitions()) {
      if(def.contains("Self")) return true;
    }
    return false;
  }
}
