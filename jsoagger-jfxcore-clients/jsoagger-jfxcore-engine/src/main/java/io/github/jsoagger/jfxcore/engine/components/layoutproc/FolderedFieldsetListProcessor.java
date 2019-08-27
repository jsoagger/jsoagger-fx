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

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IForwardEditableFieldsetsProcessor;
import io.github.jsoagger.jfxcore.api.IForwardEditor;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.ProcessViewFormBlocContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.flow.FlowContent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FolderedFieldsetListProcessor implements IForwardEditableFieldsetsProcessor {

  private FlowContent folderTable = null;
  private VBox layout = new VBox();

  /**
   * @param controller
   * @param contentCfg The wizardConfiguration of the FormFieldsets
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML fieldsetListConfig) {
    return process(controller, fieldsetListConfig, null);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML fieldsetListConfig, IForwardEditor editor) {

    int index = 0;

    // one level below, the fieldset list
    for (final VLViewComponentXML fieldsetConfig : fieldsetListConfig.getSubcomponents()) {

      VLViewComponentXML realConfig = fieldsetConfig;
      if (StringUtils.isNotBlank(fieldsetConfig.getReference())) {
        realConfig = ComponentUtils.resolveDefinition((AbstractViewController)controller, fieldsetConfig.getReference()).orElse(null);
      }

      if (realConfig != null) {
        String componentProcessorName = realConfig.getProcessor();

        String blocContentImpl = realConfig.getPropertyValue("blocContentImpl");

        ProcessViewFormBlocContent bc = (ProcessViewFormBlocContent) Services.getBean("ProcessViewFormBlocContent");
        bc.build(realConfig, controller);
        StandardViewController c = (StandardViewController) bc.getProcessedController();
        AbstractTableStructure ts = (AbstractTableStructure) c.processedElement();

        if(index == 0) {
          folderTable = (FlowContent) ts;
          layout.getChildren().add(bc.getDisplay());
        }
        else {
          if(ts instanceof FlowContent) {
            FlowContent fc = (FlowContent) ts;
            // have loaded already
            if(fc.getContent().getChildren().size() > 0) {
              folderTable.getContent().getChildren().addAll(fc.getContent().getChildren());
            }
            // beiing loaded
            else {
              //folderTable.getContent().getChildren().
              fc.getContent().getChildren().addListener(new ListChangeListener<Node>() {

                @Override
                public void onChanged(Change<? extends Node> c) {
                  boolean next = c.next();
                  if(next) {
                    List n = c.getAddedSubList();
                    for(Object node : n) {
                      if(!"LoadingPane".equalsIgnoreCase(((Node)node).getId())) {
                        folderTable.getContent().getChildren().addAll(((Node)node));
                      }
                    }
                  }
                }});
            }
          }
        }
      }
      index++;
    }
    return layout;
  }


}
