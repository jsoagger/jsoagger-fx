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

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.input.InputMode;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormFieldsetBloc extends BorderPane {

  /*-----------------------------------------------------------------------------
  | STATIC ATTRIBUTES
  *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | Private ATTRIBUTES
  *=============================================================================*/
  protected final List<FormFieldsetRow> rows = new ArrayList<>();
  protected final VBox container = new VBox();


  /*-----------------------------------------------------------------------------
  | Public methods
  *=============================================================================*/
  /**
   * Constructor
   */
  public FormFieldsetBloc() {
    NodeHelper.setHgrow(this);
    getStyleClass().add("form-fieldset-bloc");
  }


  /**
   * @param leftBlocCfg
   * @param controller
   */
  public void buildFrom(VLViewComponentXML blocConfig, AbstractViewController controller) {
    if (blocConfig.propertyValueOf(XMLConstants.TITLE).isPresent()) {
      final Node titleBloc = buildBloctitle(blocConfig, controller);
      container.getChildren().add(titleBloc);
    }

    final VBox rowsWrapper = new VBox();
    rowsWrapper.getStyleClass().add("wizard-rows-wrapper");
    container.getChildren().add(rowsWrapper);
    NodeHelper.setHVGrow(rowsWrapper);
    final String styleClass = blocConfig.getPropertyValue("contentStyleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      rowsWrapper.getStyleClass().addAll(styleClass.split(","));
    }

    Iterator<VLViewComponentXML> iterator = blocConfig.getSubcomponents().iterator();
    while (iterator.hasNext()) {
      VLViewComponentXML rowConfig = iterator.next();

      final String modeString = blocConfig.getPropertyValue(XMLConstants.MODE);

      // TODO handle visible if on rows
      final FormFieldsetRow row = new FormFieldsetRow(controller, blocConfig, rowConfig, InputMode.from(modeString), !iterator.hasNext());
      rowsWrapper.getChildren().add(row);
      rows.add(row);

      NodeHelper.setHgrow(row);
    }

    rowsWrapper.getChildren().add(NodeHelper.verticalSpacer());

    // container.setStyle("-fx-background-color: white;");
    NodeHelper.setHVGrow(container);
    setCenter(container);
  }


  /**
   * @param blocConfig
   * @return
   */
  protected Node buildBloctitle(VLViewComponentXML blocConfig, AbstractViewController controller) {
    final HBox bloctitle = new HBox();
    final String title = blocConfig.getPropertyValue(XMLConstants.TITLE);
    final Label label = new Label(controller.getLocalised(title));

    final String styleClass = blocConfig.getPropertyValue("titleStyleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      bloctitle.getStyleClass().addAll(styleClass.split(","));
    }

    bloctitle.getChildren().addAll(label);
    return bloctitle;
  }


  /**
   * Get the rows
   *
   * @return the rows
   */
  public List<FormFieldsetRow> getRows() {
    return rows;
  }
}
