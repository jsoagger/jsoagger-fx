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

package io.github.jsoagger.jfxcore.engine.model;




import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import com.google.gson.JsonObject;

/**
 * Transforms a static list or list loaded from a configured {@link IOperation} into a list of
 * {@link EnumeratedValueModel}.
 * <p>
 * This can be used for handling lifecycle status selection for example.
 * <p>
 * This can be used for static list of string selection for example.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class StringListToEnumeratedValueLoader implements IEnumeratedValuesLoader {

  private MessageSource messageSource;

  // NEVER AUTOWIRE
  // MUST BE INJECTED
  private IOperation operation;
  private List<IEnumeratedValueModel> result = new ArrayList<>(10);

  /**
   * These values will be excluded from final value when values are loaded from generic value list.
   */
  private List<String> exlusions = new ArrayList<>();

  /**
   * The are static list of values. These values will be included in final list additional to values
   * loaded by remote operation.
   */
  private List<String> inclusions = new ArrayList<>();

  /**
   * Internationalisation
   */
  // needs internatinalisation
  //protected MessageSource messageSource;


  /**
   * Constructor
   */
  public StringListToEnumeratedValueLoader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IEnumeratedValueModel> loadValues(IJSoaggerController controller, VLViewComponentXML configuration) {
    JsonObject query = new JsonObject();
    query.addProperty("containerOid", controller.getModelContainerFullId());

    if (operation != null) {
      operation.doOperation(query, e -> {
        MultipleResult multipleResult = (MultipleResult) e;
        result.addAll(toEnumeratedValues(multipleResult));
      });
    }

    result.addAll(handleInclusions());
    return result;
  }


  public List<IEnumeratedValueModel> handleInclusions() {
    List<IEnumeratedValueModel> vals = new ArrayList<>();
    for(String r: inclusions) {
      boolean skip = false;
      for (IEnumeratedValueModel m : result) {
        if (m.getSavedValue().equals(r)) {
          skip = true;
        }
      }

      if (!skip) {
        IEnumeratedValueModel model = new EnumeratedValueModel();
        model.setValue(r);
        model.setValue(messageSource.get(r));
        model.setSavedValue(r);
        vals.add(model);
      }
    };

    return vals;
  }


  public List<IEnumeratedValueModel> toEnumeratedValues(MultipleResult result) {
    List<IEnumeratedValueModel> vals = new ArrayList<>();
    List<String> realInclusions = new ArrayList<>(inclusions);

    if (result != null) {
      List<OperationData> datas = result.getData();
      if (datas != null) {
        for(OperationData data: datas) {
          String internalValue = (String) ReflectionUIUtils.invokeGetterOn(data, "attributes.internalValue");
          if (!exlusions.contains(internalValue)) {
            IEnumeratedValueModel model = new EnumeratedValueModel();
            model.setValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.displayValue"));
            model.setSavedValue(internalValue);
            model.setSourceModel(data);
            vals.add(model);

            realInclusions.remove(internalValue);
          }
        }
      }
    }

    for(String r: inclusions) {
      IEnumeratedValueModel model = new EnumeratedValueModel();
      model.setValue(r);
      model.setValue(messageSource.get(r));
      model.setSavedValue(r);
      vals.add(model);
    }

    return vals;
  }


  /**
   * Getter of operation
   *
   * @return the operation
   */
  public IOperation getOperation() {
    return operation;
  }


  /**
   * Setter of operation
   *
   * @param operation the operation to set
   */
  public void setOperation(IOperation operation) {
    this.operation = operation;
  }


  public List<String> getExlusions() {
    return exlusions;
  }


  public void setExlusions(List<String> exlusions) {
    this.exlusions = exlusions;
  }


  public List<String> getInclusions() {
    return inclusions;
  }


  public void setInclusions(List<String> inclusions) {
    this.inclusions = inclusions;
  }


  /**
   * @return the messageSource
   */
  public MessageSource getMessageSource() {
    return messageSource;
  }


  /**
   * @param messageSource the messageSource to set
   */
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }
}
