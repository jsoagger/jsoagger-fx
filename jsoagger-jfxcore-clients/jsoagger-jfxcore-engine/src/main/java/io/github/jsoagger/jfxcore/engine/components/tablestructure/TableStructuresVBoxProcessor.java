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

package io.github.jsoagger.jfxcore.engine.components.tablestructure;



import java.util.Iterator;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 13 févr. 2018
 */
public class TableStructuresVBoxProcessor implements IComponentProcessor {

  private VBox layout = new VBox();


  /**
   * Default Constructor
   */
  public TableStructuresVBoxProcessor() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML contentCfg) {
    NodeHelper.setStyleClass(layout, contentCfg, "tableStuctureVboxStyleClass", true);

    if (contentCfg.hasSubComponent()) {
      Iterator<VLViewComponentXML> it = contentCfg.getSubcomponents().iterator();
      while(it.hasNext()) {
        VLViewComponentXML tableStructureConfig = it.next();
        boolean separatorAfter = tableStructureConfig.getBooleanProperty("separatorAfter", false);

        IComponentProcessor processor = (IComponentProcessor) Services.getBean(tableStructureConfig.getProcessor());
        Node component = processor.process(controller, tableStructureConfig);
        layout.getChildren().add(component);

        if(it.hasNext() && separatorAfter) {
          layout.getChildren().add(NodeHelper.horizontalVisibleSpacer());
        }
      }
    }

    return layout;
  }
}
