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



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Administrator
 *
 */
public class SimpleDetailsViewAllFieldsetsProcessor implements IComponentProcessor {

  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML tabpaneConfig) {

    final VBox allContainer = new VBox();
    NodeHelper.setHVGrow(allContainer);
    allContainer.setStyle("-fx-background-color: #f1f1f1;" + "-fx-spacing:16");
    if (tabpaneConfig.hasSubComponent()) {

      // For each tab
      for (final VLViewComponentXML tabCfg : tabpaneConfig.getSubcomponents()) {
        final boolean visibleInSummary = tabCfg.booleanPropertyValueOf(XMLConstants.CONTENT_VISIBLE_IN_SUMMARY).orElse(true);
        if (visibleInSummary) {
          IComponentProcessor processor = (IComponentProcessor) Services.getBean(tabCfg.getProcessor());
          allContainer.getChildren().add(processor.process(controller, tabCfg));
        }
      }
    }

    return allContainer;
  }
}
