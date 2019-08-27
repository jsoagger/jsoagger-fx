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

package io.github.jsoagger.jfxcore.engine.components.layoutproc;




import java.util.List;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldsetBlocProcessor;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormBlocTitlePane;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;

/**
 * Generates content with a {@link FormBlocTitlePane}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormBlocProcessor implements IFieldsetBlocProcessor {

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML blocConfig) {
    return process(controller, blocConfig, null, null, false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML blocConfig, List inputCollector, List<IFormFieldsetRow> formRowsCollector, boolean first) {
    final FormBlocTitlePane titlePane = new FormBlocTitlePane(blocConfig, (AbstractViewController) controller);
    for(IFormFieldsetRow row: titlePane.getRows()) {
      if (inputCollector != null) {
        inputCollector.addAll(row.getEntries());
      }

      if (formRowsCollector != null) {
        formRowsCollector.add(row);
      }
    }

    if (first) {
      titlePane.setFirst();
    }

    return titlePane;
  }
}
