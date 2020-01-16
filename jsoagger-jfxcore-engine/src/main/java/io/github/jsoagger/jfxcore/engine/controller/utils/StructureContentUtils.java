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

package io.github.jsoagger.jfxcore.engine.controller.utils;




import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.api.services.Services;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class StructureContentUtils {

  public static StructureContentController forId(RootStructureContentController rootStructureContentController, RootStructureController rootStructure, PushStructureContentEvent fromPush,
      VLViewComponentXML config) {

    Assert.notNull(rootStructure);
    Assert.notNull(config);

    String structureContentImpl = config.getPropertyValue("viewLayout");
    boolean isWizard = config.getBooleanProperty("isWizardView", false);

    StructureContentController controller = (StructureContentController) Services.getBean(structureContentImpl);
    if (fromPush != null) {
      controller.setSourceEvent(fromPush);
      controller.setFormModelData((OperationData) fromPush.getModel());
      controller.setForModelId(fromPush.getModelFullId());
    }

    controller.setRootStructureContent(rootStructureContentController);
    controller.setRootStructure(rootStructure);
    // controller.setWizardView(isWizard);

    // must build config manually because it does not exist
    final VLViewConfigXML configXML = new VLViewConfigXML();
    configXML.addEmptyRootContent();
    VLViewComponentXML rootComp = configXML.getComponents().get(0);
    rootComp.setProperties(config.getProperties());

    controller.initViewContext(configXML, rootStructure.getRootContext());
    controller.setInitialized(true);
    controller.build();

    return controller;
  }


  public static StructureContentController forId(RootStructureContentController rootStructureContentController, RootStructureController rootStructure, VLViewComponentXML config) {
    return forId(rootStructureContentController, rootStructure, null, config);
  }
}
