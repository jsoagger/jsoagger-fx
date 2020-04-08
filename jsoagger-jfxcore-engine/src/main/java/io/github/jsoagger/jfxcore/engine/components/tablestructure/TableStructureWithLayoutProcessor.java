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
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.scene.Node;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 13 févr. 2018
 */
public class TableStructureWithLayoutProcessor implements IComponentProcessor {

  /**
   * Default Constructor
   */
  public TableStructureWithLayoutProcessor() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML contentCfg) {
    String componentLayoutManager = contentCfg.getPropertyValue("componentLayoutManager");
    String contentImpl = contentCfg.getPropertyValue("contentImpl");

    System.out.println("componentLayoutManager : " + componentLayoutManager);
    System.out.println("contentImpl : " + contentImpl);

    AbstractTableStructure tableStructure = (AbstractTableStructure) Services.getBean(contentImpl);
    tableStructure.buildFrom(controller, contentCfg);

    SimpleTableStructureLayoutManager layoutManager =
        (SimpleTableStructureLayoutManager) Services.getBean(componentLayoutManager);
    layoutManager.layout(tableStructure);

    return layoutManager.getDisplay();
  }
}
