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

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldsetBlocProcessor;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.form.FormTextBlobBloc;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormFieldsetBloc;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SingleBlobBlocsFieldsetProcessor implements IFieldsetBlocProcessor {

  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML fieldsetCfg) {
    return process(controller, fieldsetCfg, null, null, false);
  }


  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML contentCfg, List inputCollector, List<IFormFieldsetRow> formRowsCollector, boolean first) {

    final VBox blocContainer = new VBox();
    NodeHelper.setHgrow(blocContainer);
    blocContainer.setStyle("-fx-spacing: 16;");

    final List<VLViewComponentXML> blocConfigs = contentCfg.getSubcomponents();

    // maximum one bloc
    if ((blocConfigs != null) && (blocConfigs.size() > 1)) {
      // throw new
      // IllegalArgumentException("SingleBlobBlocsFieldsetProcessor
      // supports max 1 child block");
    }

    for (final VLViewComponentXML blockCfg : blocConfigs) {
      final VBox internalContainer = new VBox();
      internalContainer.setStyle("-fx-spacing: 16");
      final FormFieldsetBloc block = new FormTextBlobBloc();
      block.buildFrom(blockCfg, (AbstractViewController) controller);
      NodeHelper.setHVGrow(block);

      for(FormFieldsetRow row: block.getRows()) {
        if (inputCollector != null) {
          inputCollector.addAll(row.getEntries());
        }

        if (formRowsCollector != null) {
          formRowsCollector.add(row);
        }
      }

      internalContainer.getChildren().add(block);
      blocContainer.getChildren().add(internalContainer);
    }

    return blocContainer;
  }
}
