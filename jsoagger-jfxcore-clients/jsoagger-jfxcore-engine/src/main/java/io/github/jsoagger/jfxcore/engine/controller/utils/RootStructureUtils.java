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

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.components.security.RootContext;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;

/**
 * Utility for building {@link RootStructureController}.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class RootStructureUtils {

  //private static final Logger //LogR = LogManager.getLogger(RootStructureUtils.class);


  /**
   * Load model associated to this controller
   *
   * @param controller
   */
  private static void loadModel(AbstractViewController controller) {
    if (controller.getModelProvider() != null) {
      final IModelProvider modelProvider = controller.getModelProvider();
      modelProvider.loadModel((IJSoaggerController) controller, null);
    }
  }


  /**
   * Build root structure without context
   *
   * @param identifier
   * @return {@link RootStructureController}
   */
  public static RootStructureController forId(String identifier) {
    final RootContext rootContext = (RootContext) Services.getBean("RootContext");
    return forId(identifier, rootContext);
  }


  /**
   * Build root structure without context relative to this model.
   *
   * @param identifier
   * @return {@link RootStructureController}
   */
  public static RootStructureController forId(String identifier, OperationData data) {
    final RootContext rootContext = (RootContext) Services.getBean("RootContext");
    return forId(identifier, rootContext, data);
  }


  public static RootStructureController forId(String identifier, RootContext context, OperationData data) {
    Assert.notNull(identifier);
    Assert.notNull(context);

    final RootStructureController controller = (RootStructureController) Services.getBean(identifier);
    if (data != null) {
      controller.relativeToProperty().set(data);
    }

    controller.setRootContext(context);
    loadModel(controller);

    if (controller.isInitialized()) {
      return controller;
    }
    try {
      final VLViewConfigXML configXML = Services.getConfigurationFile((IJSoaggerController) controller);
      controller.initViewContext(configXML, context);
      controller.build();
      controller.setInitialized(true);
      return controller;
    } catch (final SecurityException | IllegalArgumentException e) {
      //LogR.error(e);
      e.printStackTrace();
    }

    // should never happen
    throw new RuntimeException(MessageFormat.format("Error initialializing : {0}", identifier));
  }


  /**
   * Build root structure in the given context
   *
   * @param identifier
   * @param context
   * @return {@link RootStructureController}
   */
  public static RootStructureController forId(String identifier, RootContext context) {
    return forId(identifier, context, null);
  }
}
