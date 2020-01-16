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


import java.text.MessageFormat;

import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 8 févr. 2018
 */
public class RootStructureContentUtils {

  /**
   * Load model associated to this controller
   *
   * @param controller
   */
  private static void loadModel(AbstractViewController controller) {
    if (controller.getModelProvider() != null) {
      IModelProvider modelProvider = controller.getModelProvider();
      modelProvider.loadModel((IJSoaggerController) controller, null);
    }
  }


  /**
   * Build root structure without context
   *
   * @param identifier
   * @return {@link RootStructureController}
   */
  public static RootStructureContentController forId(String identifier, RootStructureController rootStructure) {
    Assert.notNull(identifier);
    Assert.notNull(rootStructure);

    RootStructureContentController controller = (RootStructureContentController) Services.getBean(identifier);
    controller.setRootStructure(rootStructure);
    loadModel(controller);

    if (controller.isInitialized()) {
      return controller;
    }
    try {
      final VLViewConfigXML configXML = Services.getConfigurationFile((IJSoaggerController) controller);
      controller.initViewContext(configXML, rootStructure.getRootContext());
      controller.build();
      controller.setInitialized(true);
      return controller;
    } catch (final SecurityException | IllegalArgumentException e) {
      //LogR.error(e);
    }

    // should never happen
    throw new RuntimeException(MessageFormat.format("Error initialializing : {0}", identifier));
  }
}
