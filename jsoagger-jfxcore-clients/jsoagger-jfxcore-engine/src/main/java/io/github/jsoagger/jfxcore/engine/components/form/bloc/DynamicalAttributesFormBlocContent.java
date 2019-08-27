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

package io.github.jsoagger.jfxcore.engine.components.form.bloc;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.input.InputMode;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.CoreDynamicAttributesUtils;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The controller should have been downloaded dynamical attribute definition lis to handle. They
 * should be found alongside with business type of the model
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DynamicalAttributesFormBlocContent extends StackPane implements IFormBlocContent {

  private static final String FORM_BLOC_CONTENT = "form-bloc-content";

  protected VLViewComponentXML blocConfig;
  protected AbstractViewController controller;
  protected InputMode mode;
  protected final List<IFormFieldsetRow> rows = new ArrayList<>();


  /**
   * Constructor
   */
  public DynamicalAttributesFormBlocContent() {
    getStyleClass().add(FORM_BLOC_CONTENT);
    managedProperty().bindBidirectional(visibleProperty());
  }


  /**
   * Constructor
   */
  @Override
  public void build(VLViewComponentXML blocConfig, IJSoaggerController controller) {
    this.blocConfig = blocConfig;
    this.controller = (AbstractViewController) controller;

    final String modeString = blocConfig.getPropertyValue(XMLConstants.MODE);
    mode = InputMode.from(modeString);

    SingleResult sr = (SingleResult) controller.getModel();
    OperationData d = sr.getData();

    List<OperationData> datas = (List<OperationData>) d.getBusinessType().get("softAttributes");
    if (datas != null)
      for (OperationData data : datas) {
        VLViewComponentXML rowConfig;
        try {
          rowConfig = CoreDynamicAttributesUtils.fromData(controller, data);
          blocConfig.addSubconfg(rowConfig);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    final VBox rowsWrapper = new VBox();
    rowsWrapper.getStyleClass().add("wizard-rows-wrapper");
    NodeHelper.setHVGrow(rowsWrapper);

    final String styleClass = blocConfig.getPropertyValue("contentStyleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      getStyleClass().clear();
      getStyleClass().addAll(styleClass.split(","));
    }

    // for each row
    Iterator<VLViewComponentXML> iterator = blocConfig.getSubcomponents().iterator();
    while (iterator.hasNext()) {
      VLViewComponentXML rowConfig = iterator.next();

      // TODO handle visible if on rows
      final FormFieldsetRow row = new FormFieldsetRow((AbstractViewController) controller, blocConfig, rowConfig, mode, !iterator.hasNext());
      rowsWrapper.getChildren().add(row);
      row.setOwner(this);
      rows.add(row);

      // NodeHelper.styleClassAddAll(row, blocConfig, "rowsStyleClass",
      // "form-fieldset-row");
    }

    // add pseudo class last to last row
    if (rows.size() > 0) {
      rows.get(rows.size() - 1).setLast();
    }

    getChildren().addAll(rowsWrapper, NodeHelper.verticalSpacer());
  }


  public void onDefinitionLoadError(Throwable ex) {
  }


  /**
   * Get the rows
   *
   * @return the rows
   */
  @Override
  public List<IFormFieldsetRow> getRows() {
    return rows;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
