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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header;


import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class EditStructureTableHeaderImpl extends FiltrableTableHeaderImpl {

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    // WHEN EDITING A STRUCTURE WE SET THE TITLE OF THE TABLE
    // TO THE STRUCTURE CONTENT CURRENT MODEL
    OperationData data = ((AbstractViewController)controller).getStructureContent().getFormModelData();
    String name = (String) ReflectionUIUtils.invokeGetterOn(data, "masterAttributes.name");
    String version = (String) ReflectionUIUtils.invokeGetterOn(data, "attributes.versionInfo.versionId");
    String iteration = String.valueOf(ReflectionUIUtils.invokeGetterOn(data, "attributes.iterationInfo.iterationNumber"));

    String title = name + ", " + version + "." + iteration;
    this.title.setText(title);
    this.title.getStyleClass().setAll("ep-edit-structure-title-label");
  }
}
