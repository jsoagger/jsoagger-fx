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



import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;

/**
 * build {@link AbstractTableStructure} from the configuration. {@link AbstractTableStructure}
 * should be displayed inside layout.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 13 févr. 2018
 */
public class TableStructureProcessor implements IComponentProcessor {

  private AbstractTableStructure processedElement;


  /**
   * Default Constructor
   */
  public TableStructureProcessor() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Object processElement(IJSoaggerController controller, VLViewComponentXML contentCfg) {
    String contentImpl = contentCfg.getPropertyValue("contentImpl");
    processedElement = (AbstractTableStructure) Services.getBean(contentImpl);
    processedElement.buildFrom(controller, contentCfg);
    return processedElement;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML contentCfg) {
    if (processedElement != null) {
      return processedElement.getTableStructure();
    }

    // should not be used, table must be used along side with layout
    processedElement = (AbstractTableStructure) processElement(controller, contentCfg);
    return processedElement.getTableStructure();
  }


  /**
   * Getter of processedElement
   *
   * @return the processedElement
   */
  public AbstractTableStructure getProcessedElement() {
    return processedElement;
  }
}
