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

package io.github.jsoagger.jfxcore.engine.components.inputview;




import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PeriodInputView extends AbstractViewInputComponent {

  private static final String SELECTION_VALUES = "io.github.jsoagger.jfxcore.engine.datepicker.periods";
  private static final String customRangePattern = "Between %s and %s";

  protected final DateTimeFormatter thisYearformatter = DateTimeFormatter.ofPattern("dd MMM");
  protected final DateTimeFormatter otherYearsformatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
  private final Label label = new Label("");


  /**
   * Constructor
   */
  public PeriodInputView() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);

    label.textProperty().unbind();
    label.setWrapText(true);
    NodeHelper.styleClassAddAll(label, getConfiguration(), "viewStyleClass", "form-info-value");

    inputComponentWrapper.currentInternalValueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
      updateValue(newValue);
    });

    final String stringDatePeriod = inputComponentWrapper.getCurrentInternalValue();
    updateValue(stringDatePeriod);
  }


  private void updateValue(String internalvalue) {
    if (StringUtils.isNotBlank(internalvalue)) {
      if (internalvalue.startsWith("80")) {
        final String period1 = internalvalue.split("\\|")[1];
        final String period2 = internalvalue.split("\\|")[2];
        label.setText(String.format(customRangePattern, period1, period2));
      } else {
        final IEnumeratedValueModel model = inputComponentWrapper.getEnumeratedValue(internalvalue);
        if(model != null) {
          label.setText(model.getValue());
        }
        else {
          System.out.println("[ERROR] -- "  + inputComponentWrapper + " returns empty model");
        }
      }
    } else {
      final IEnumeratedValueModel anytime = inputComponentWrapper.getEnumeratedValue("10");
      if (anytime != null)
        label.setText(anytime.getValue());
    }
  }


  private String getValue(String key) {
    // load list of selectable values
    if (key != null) {
      final String values = "TODO ADD PERIOD TO LISt VALUE";
      for (final String e : Pattern.compile("#").split(values)) {
        if (e.equals(key)) {
          final String translatedValue = controller.getLocalised(key);
          return translatedValue;
        }
      }
    }

    return null;
  }


  protected String format(LocalDateTime date) {
    // do not display year if same as today
    final LocalDateTime now = LocalDateTime.now();
    if (date.getYear() == now.getYear()) {
      return thisYearformatter.format(date);
    }

    return otherYearsformatter.format(date);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }
}
