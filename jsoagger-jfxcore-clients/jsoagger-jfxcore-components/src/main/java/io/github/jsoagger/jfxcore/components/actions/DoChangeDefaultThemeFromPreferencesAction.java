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

package io.github.jsoagger.jfxcore.components.actions;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.components.css.StyleSheetsManager;
import io.github.jsoagger.jfxcore.engine.utils.ThemeUtils;

/**
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class DoChangeDefaultThemeFromPreferencesAction extends AbstractAction implements IAction {


  static String ACCENT = "io.github.jsoagger.theme.accent.color";
  static String PRIMARY = "io.github.jsoagger.theme.primary.color";


  IOperation getPreferenceValueOperation;//  needs GetPreferenceValueOperation

  /**
   * Constuctor
   */
  public DoChangeDefaultThemeFromPreferencesAction() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    String primary = ThemeUtils.getCssFromPrimaryColor(getPrimaryColor());
    String accent = ThemeUtils.getCssFromAccentColor(getAccentColor());
    final StyleSheetsManager sheetsManager = (StyleSheetsManager) Services.getBean("styleSheetManager");
    sheetsManager.setDefaultTheme(primary, accent);
  }

  private String getPrimaryColor() {
    JsonObject query = new JsonObject();
    query.addProperty("key", PRIMARY);

    List<String> datas = new ArrayList<>();
    if (getPreferenceValueOperation != null) {
      getPreferenceValueOperation.doOperation(query, res -> {
        MultipleResult r = (MultipleResult) res;
        if (r.getData().size() > 0) {
          datas.add((String) r.getData().get(0).getAttributes().get("savedValue"));
        }
      });
    }

    return datas.size() > 0 ? datas.get(0) : "indigo";
  }

  private String getAccentColor() {
    JsonObject query = new JsonObject();
    query.addProperty("key", ACCENT);

    List<String> datas = new ArrayList<>();
    if (getPreferenceValueOperation != null) {
      getPreferenceValueOperation.doOperation(query, res -> {
        MultipleResult r = (MultipleResult) res;
        if (r.getData().size() > 0) {
          datas.add((String) r.getData().get(0).getAttributes().get("savedValue"));
        }
      });
    }

    return datas.size() > 0 ? datas.get(0) : "deepOrange";
  }


  /**
   * @return the getPreferenceValueOperation
   */
  public IOperation getGetPreferenceValueOperation() {
    return getPreferenceValueOperation;
  }


  /**
   * @param getPreferenceValueOperation the getPreferenceValueOperation to set
   */
  public void setGetPreferenceValueOperation(IOperation getPreferenceValueOperation) {
    this.getPreferenceValueOperation = getPreferenceValueOperation;
  }
}
