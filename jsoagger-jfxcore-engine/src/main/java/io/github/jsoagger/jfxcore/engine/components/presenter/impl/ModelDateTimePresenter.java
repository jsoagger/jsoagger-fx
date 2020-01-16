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



import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.DateUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.IModelAttributePresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 févr. 2018
 */
public class ModelDateTimePresenter extends CellPresenterImpl implements IModelAttributePresenter {

  private Label label = new Label();
  private SimpleDateFormat df = null;
  private String fullDisplayFormat;
  private String shortDisplayFormat;
  private String saveFormat;

  private String defaultFormat = "dd/MM/YY hh:mm";


  /**
   * Default Constructor
   */
  public ModelDateTimePresenter() {
    label.getStyleClass().add("ep-description-presenter");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    VLViewComponentXML dtf = configuration.getComponentById("DisplayFormat").orElse(null);
    if (dtf != null) {
      fullDisplayFormat = dtf.getPropertyValue("fullDisplayFormat") == null ? defaultFormat : dtf.getPropertyValue("fullDisplayFormat");
      shortDisplayFormat = dtf.getPropertyValue("shortDisplayFormat") == null ? defaultFormat : dtf.getPropertyValue("fullDisplayFormat");
      saveFormat = dtf.getPropertyValue("saveFormat");
    }

    if (StringUtils.isNotBlank(saveFormat)) {
      String attributePath = configuration.getPropertyValue("attributePath");
      String dataValue = getModelAttribute(controller, attributePath);
      setDate((AbstractViewController) controller, configuration, dataValue);
    }

    return label;
  }


  public String setDate(AbstractViewController controller, VLViewComponentXML configuration, String dataValue) {
    if (StringUtils.isNotBlank(saveFormat)) {
      if (dataValue != null) {
        Date date = DateUtils.readFromString(dataValue, saveFormat);
        if (date == null) {
          label.setText("--");
          return "--";
        }

        if (isToday(date)) {
          df = new SimpleDateFormat(shortDisplayFormat);
          label.setText(df.format(date));
        } else {
          df = new SimpleDateFormat(fullDisplayFormat);
          label.setText(df.format(date));
        }
      }
    }
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    VLViewComponentXML dtf = configuration.getComponentById("DisplayFormat").orElse(null);
    if (dtf != null) {
      fullDisplayFormat = dtf.getPropertyValue("fullDisplayFormat") == null ? defaultFormat : dtf.getPropertyValue("fullDisplayFormat");
      shortDisplayFormat = dtf.getPropertyValue("shortDisplayFormat") == null ? defaultFormat : dtf.getPropertyValue("fullDisplayFormat");
      saveFormat = dtf.getPropertyValue("saveFormat");
    }

    if (StringUtils.isNotBlank(saveFormat)) {
      String attributePath = configuration.getPropertyValue("attributePath");
      String dataValue = getModelAttribute((OperationData) forModel, attributePath);
      setDate((AbstractViewController) controller, configuration, dataValue);
    }

    return label;
  }


  private boolean isToday(Date date) {
    if (date == null) {
      return false;
    }
    Date today = new Date();
    return (date.getYear() == today.getYear()) && (date.getDay() == today.getDay()) && (date.getMonth() == today.getMonth());
  }
}
