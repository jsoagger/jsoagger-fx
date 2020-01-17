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

import com.jfoenix.controls.JFXButton;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.presenter.CellPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Do an action from preference view.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class DoActionPresenterFactory extends CellPresenterFactory implements IListFormCellPresenter {

  private Button button;
  private HBox layout;

  /**
   * Constructor
   */
  public DoActionPresenterFactory() {
    super();
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    layout = new HBox();
    button = new JFXButton();

    String title = (String) getForData().getAttributes().get("title");
    String buttonTitle = (String) getForData().getAttributes().get("buttonTitle");
    if (StringUtils.isNotBlank(buttonTitle)) {
      button.setText(controller.getLocalised(buttonTitle).toUpperCase());
    }
    button.setDefaultButton(true);

    if(StringUtils.isNotBlank(title)) {
    	layout.getChildren().addAll(new Label(controller.getLocalised(title)), NodeHelper.horizontalSpacer());
    }

    layout.getChildren().addAll(button);

    // subcom is on data
    List<VLViewComponentXML> sub = (List<VLViewComponentXML>) getForData().getMeta().get("subComponents");
    VLViewComponentXML conf = new VLViewComponentXML();
    if (sub != null) {
      conf.setSubcomponents(sub);
    }

    getForData().getAttributes().keySet().forEach(key -> {
  	  conf.getProperties().put(key, String.valueOf(getForData().getAttributes().get(key)));
    });

    NodeHelper.styleClassSetAll(layout, conf, "layoutStyleClass", "item-presenter-row-container");
    NodeHelper.styleClassSetAll(button, conf, "buttonStyleClass", "button, button-primary, button-xlarge");
    ComponentToButtonBaseHelper.setOnAction(conf, button, (AbstractViewController) controller, null, e -> {
      processUpdate(null);
      return null;
    });

    //currentValuesPresenter.getStyleClass().add("-fx-background-color:red;");
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
    return "";
  }

  @Override
  public String getOwner() {
    return null;
  }

  @Override
  public void processUpdate(List<IListFormValue> selected) {
  }
}
