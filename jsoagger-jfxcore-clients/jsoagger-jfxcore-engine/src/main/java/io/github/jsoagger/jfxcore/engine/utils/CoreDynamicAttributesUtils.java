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

package io.github.jsoagger.jfxcore.engine.utils;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 26 févr. 2018
 */
public class CoreDynamicAttributesUtils {

  static String location = "/comp/CoreAttributesTemplates.xml";
  static VLViewConfigXML templates = null;


  public static VLViewComponentXML fromData(IJSoaggerController controller, OperationData attributeConfig)  {
    String attributeId = (String) attributeConfig.getAttributes().get("attributeName");

    String defaultValue = (String) attributeConfig.getAttributes().getOrDefault("defaultValue", "");
    String sourceValueListKeys = (String) attributeConfig.getAttributes().getOrDefault("sourceValueListKeys", "");
    String enumeratedDataLoader = (String) attributeConfig.getAttributes().getOrDefault("enumeratedDataLoader", "");
    String prompt = (String) attributeConfig.getAttributes().getOrDefault("prompt", "");
    String viewUtility = (String) attributeConfig.getAttributes().getOrDefault("viewUtility", "BasicAttributeInputView");
    String editUtility = (String) attributeConfig.getAttributes().getOrDefault("editUtility", "InputText");
    String attributeName = (String) attributeConfig.getAttributes().getOrDefault("attributeName", "");
    String attributePath = "data.attributes.softAttributeColumns.dyn_att_" + attributeConfig.getAttributes().getOrDefault("mappedColumnNumber", "-1");
    String enumeratedKey = (String) attributeConfig.getAttributes().getOrDefault("enumeratedKey", "");

    String label = (String) attributeConfig.getAttributes().get("displayName");
    if (label == null) {
      label = (String) attributeConfig.getAttributes().getOrDefault("i18nKey", "__NO_NAME__");
      label = controller.getLocalised(label.toString());
    }

    String mandatory = attributeConfig.getAttributes().getOrDefault("mandatory", Boolean.FALSE).toString();
    String minLength = attributeConfig.getAttributes().getOrDefault("minLength", -1).toString();
    String maxLength = attributeConfig.getAttributes().getOrDefault("maxLength", -1).toString();
    String minValue = attributeConfig.getAttributes().getOrDefault("minValue", "").toString();
    String maxValue = attributeConfig.getAttributes().getOrDefault("maxValue", "").toString();
    String blankAllowed = attributeConfig.getAttributes().getOrDefault("blankAllowed", Boolean.TRUE).toString();
    String displayOrder = attributeConfig.getAttributes().getOrDefault("displayOrder", -1).toString();

    VLViewComponentXML row = new VLViewComponentXML();

    // general properties
    row.getProperties().put("defaultValue", defaultValue);
    row.getProperties().put("sourceValueListKeys", sourceValueListKeys);
    row.getProperties().put("enumeratedDataLoader", enumeratedDataLoader);
    row.getProperties().put("label", label);
    row.getProperties().put("prompt", prompt);
    row.getProperties().put("viewUtility", viewUtility);
    row.getProperties().put("editUtility", editUtility);
    row.getProperties().put("attributeName", attributeName);
    row.getProperties().put("attributePath", attributePath);
    row.getProperties().put("enumeratedDataLoader", enumeratedDataLoader);
    row.getProperties().put("enumeratedKey", enumeratedKey);
    row.getProperties().put("displayOrder", displayOrder);

    // validation properties
    VLViewComponentXML validationConfig = new VLViewComponentXML();
    validationConfig.setId("Validation");
    validationConfig.getProperties().put("mandatory", mandatory);
    validationConfig.getProperties().put("minLength", minLength);
    validationConfig.getProperties().put("maxLength", maxLength);
    validationConfig.getProperties().put("minValue", minValue);
    validationConfig.getProperties().put("maxValue", maxValue);
    validationConfig.getProperties().put("blankAllowed", blankAllowed);
    row.addSubconfg(validationConfig);

    // Format
    // TODO
    VLViewComponentXML formatConfig = new VLViewComponentXML();
    formatConfig.setId("Format");
    row.addSubconfg(formatConfig);

    // add the component definition ot the controller components
    // definitions list
    row.setId(attributeId);
    controller.viewContext().getViewConfig().addComponentDefinition(row);


    //System.out.println();
    //System.out.println(attributeConfig);
    //System.out.println(row);

    // generates the row
    VLViewComponentXML rowConfig = new VLViewComponentXML();
    rowConfig.setId("FormRow");
    VLViewComponentXML rows = new VLViewComponentXML();
    rows.setId("Rows");
    rowConfig.addSubconfg(rows);

    VLViewComponentXML ref = new VLViewComponentXML();
    rows.addSubconfg(ref);
    ref.setReference(attributeId);

    return rowConfig;
  }
}
