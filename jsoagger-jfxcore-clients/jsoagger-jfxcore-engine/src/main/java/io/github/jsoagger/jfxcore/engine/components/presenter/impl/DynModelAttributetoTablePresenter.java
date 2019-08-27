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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl;



import java.util.Collection;
import java.util.List;

import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider;
import io.github.jsoagger.jfxcore.api.presenter.IModelAttributePresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelEnumeratedValueTranslater;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import com.google.gson.JsonObject;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Display dynamical attribute in a table view.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 févr. 2018
 */
public class DynModelAttributetoTablePresenter extends CellPresenterImpl implements IModelAttributePresenter {

  private final Label label = new Label("--");
  private ModelEnumeratedValueTranslater enumeratedTypeTranslater;


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    return present(controller, configuration, null);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    final String attributePath = configuration.getPropertyValue("attributePath");
    String dynattributename = null;
    final String rootPath = null;
    // the dyn attribute name is the last part of the attribute path
    if (attributePath.split("\\.").length > 0) {
      final String[] t = attributePath.split("\\.");
      dynattributename = attributePath;
      //StringUtils.substringBeforeLast(attributePath, dynattributename);
    }

    if (controller instanceof FullTableViewController) {
      final FullTableViewController c = (FullTableViewController) controller;
      final SingleTableStructure t = (SingleTableStructure) c.processedElement();
      final IPaginatedDataProvider dp = t.getDataProvider();
      final MultipleResult attr = (MultipleResult) dp.getTypeDynamicalAttributes();

      if(attr != null) {
        final List<OperationData> datas = attr.getData();
        final OperationData p = getDynamicalAttributeDefnition(dynattributename, datas);
        if (p != null) {
          setValue(p, rootPath, dynattributename, (OperationData) forModel);
        }
      }
    }

    return label;
  }


  private void setValue(OperationData attributeDefinition, String attributeRootPath, String attributeLogicalPath, OperationData forModel) {
    final String mc = getDynamicalColumnMapping(attributeDefinition);
    final String attributeName = attributeRootPath + "dyn_att_" + mc;
    final Object value = getModelAttribute(forModel, attributeName);

    label.setText("--");
    if (value == null || StringUtils.isEmpty(String.valueOf(value))) {
      final Object o = getDefaulValue(attributeDefinition, attributeLogicalPath);
      if (o != null) {
        label.setText((String) o);
      }
    } else {
      label.setText((String) value);
    }
  }


  private OperationData getDynamicalAttributeDefnition(String logicalName, List<OperationData> datas) {
    for (final OperationData data : datas) {
      if (logicalName.equalsIgnoreCase((String) data.getAttributes().get("logicalName"))) {
        return data;
      }
    }
    return null;
  }


  private String getDynamicalColumnMapping(OperationData d) {
    final OperationData nest = d.getNestedAttributes();
    final int mappedCol = (int) nest.getAttributes().get("mappedColumnNumber");
    return String.valueOf(mappedCol);
  }


  private Object getNestedAttributeValue(OperationData d, String attributeName) {
    final OperationData nest = d.getNestedAttributes();
    final Collection attributes = (Collection) nest.getAttributes().get("attributes");
    for (final Object o : attributes) {
      final JsonObject jo = (JsonObject) o;
      if (jo.get("attributeName").getAsString().equals(attributeName)) {
        return jo.get("attributeValue");
      }
    }

    return null;
  }


  private Object getDefaulValue(OperationData d, String attributeName) {
    return getNestedAttributeValue(d, "defaultValue");
  }


  private void translate(AbstractViewController controller, VLViewComponentXML configuration) {
    final Object datavalue = ReflectionUIUtils.invokeGetterOn(null, null);
    initEnumeratedTypeTranslater(configuration);

    if (datavalue != null) {
      if (enumeratedTypeTranslater != null) {
        // TO DO Asunch if long running
        final String realVal = enumeratedTypeTranslater.translate(controller, configuration, datavalue.toString());
        label.setText(realVal);
      } else {
        label.setText(datavalue.toString());
      }
    }
  }


  private void initEnumeratedTypeTranslater(VLViewComponentXML config) {
    final String enumeratedTypeTranslater = config.getPropertyValue("enumeratedTypeTranslater");
    if (StringUtils.isNotBlank(enumeratedTypeTranslater)) {
      this.enumeratedTypeTranslater = (ModelEnumeratedValueTranslater) Services.getBean(enumeratedTypeTranslater);
    }
  }

}
