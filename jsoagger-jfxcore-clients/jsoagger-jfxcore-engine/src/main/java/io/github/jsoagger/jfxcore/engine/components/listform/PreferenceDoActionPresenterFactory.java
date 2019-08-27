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



import java.io.File;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * Do an action from preference view.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class PreferenceDoActionPresenterFactory extends PreferenceItemPresenterFactory implements IListFormCellPresenter {

  private Button button;
  private HBox layout;
  private HBox currentValuesPresenter;

  /**
   * Constructor
   */
  public PreferenceDoActionPresenterFactory() {
    super();
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    layout = new HBox();
    button = new JFXButton("UPDATE");
    currentValuesPresenter = new HBox();

    String title = (String) getForData().getAttributes().get("buttonTitle");
    if (StringUtils.isNotBlank(title)) {
      button.setText(controller.getLocalised(title).toUpperCase());
    }
    button.setDefaultButton(true);

    layout.getStyleClass().add("item-presenter-row-container");
    layout.setSpacing(32);
    currentValuesPresenter.setAlignment(Pos.CENTER);

    //layout.getChildren().clear();
    displayValue();
    layout.getChildren().addAll(label, NodeHelper.horizontalSpacer());
    layout.getChildren().addAll(currentValuesPresenter, button);

    // subcom is on on data
    List<VLViewComponentXML> sub = (List<VLViewComponentXML>) getForData().getMeta().get("subComponents");
    VLViewComponentXML conf = new VLViewComponentXML();
    if (sub != null) {
      conf.setSubcomponents(sub);
    }

    NodeHelper.styleClassSetAll(button, conf, "buttonStyleClass", "button, button-primary, button-xlarge");
    ComponentToButtonBaseHelper.setOnAction(conf, button, (AbstractViewController) controller, null, e -> {
      processUpdate(null);
      return null;
    });

    currentValuesPresenter.getStyleClass().add("-fx-background-color:red;");
  }

  /**
   * Dispaly current value
   */
  protected void displayValue() {
    String currVal = getCurrentDisplayText();
    Label currValLabel = new Label();
    currentValuesPresenter.getChildren().clear();
    currentValuesPresenter.getChildren().add(currValLabel);
    if (StringUtils.isNotBlank(currVal)) {
      String[] t = currVal.split(File.separator);
      String val = t[t.length - 1];
      currValLabel.setText(val);
      currValLabel.setMaxWidth(100);
      currValLabel.setTooltip(new Tooltip(currVal));
    }
  }

  @Override
  public Node getDisplay() {
    return layout;
  }

  @Override
  public void setKey(String key) {}

  @Override
  public void setContext(String context) {

  }

  @Override
  public String getContext() {
    return null;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getOwner() {
    return null;
  }

  @Override
  public void processUpdate(List<IListFormValue> selected) {
    loadCurrentValue();
    displayValue();
  }
}
