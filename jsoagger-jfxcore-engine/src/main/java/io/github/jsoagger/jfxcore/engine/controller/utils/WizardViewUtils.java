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
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;
import com.google.gson.JsonObject;

/**
 * Utility for building {@link WizardViewController}.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardViewUtils {

  //private static final Logger LogR = LogManager.getLogger(RootStructureController.class);


  /**
   * Load model associated to this controller
   *
   * @param controller
   */
  private static void loadModel(AbstractViewController controller) {
    // use only in edition mode
    if (controller.getModelProvider() != null) {
      IModelProvider modelProvider = controller.getModelProvider();
      modelProvider.loadModel(controller, null);
    }
    // in create mode do not provide
    // a modelProvider to your wizard
    else {
      SingleResult sr = new SingleResult();
      sr.setData(new OperationData());
      controller.setModel(sr);

      // add container
      sr.getData().getAttributes().put("fullId", controller.getModelContainerFullId());
    }
  }


  /**
   * @param controller
   * @param viewId
   * @return
   */
  public static WizardViewController forWizardId(final AbstractViewController controller, StructureContentController structureContentController, String viewId, Object initialData) {
    if(controller instanceof RootStructureController) {
      RootStructureController rsc = controller instanceof RootStructureController ? (RootStructureController) controller : controller.getRootStructure();
      return doBuildWizard(rsc, structureContentController, null, viewId, (ObjectModel) initialData);
    }

    return doBuildWizard(controller.getRootStructure(), structureContentController, controller, viewId, (ObjectModel) initialData);
  }


  /**
   * @param controller
   * @param viewId
   * @return
   */
  public static WizardViewController forWizardId(final RootStructureController controller, StructureContentController structureContentController, String viewId) {
    if (controller instanceof RootStructureController) {
      return forWizardId(controller, structureContentController, viewId, null);
    } else {
      RootStructureController rootStructureController = controller.getRootStructure();
      return forWizardId(rootStructureController, structureContentController, viewId, null);
    }
  }


  /**
   * Builds {@link WizardViewController}
   *
   * @param rootStructureController
   * @param identifier
   * @param initialData
   * @return
   */
  public static WizardViewController doBuildWizard(RootStructureController rootStructureController, StructureContentController structureContentController,AbstractViewController parent, String identifier, ObjectModel initialData) {
    Assert.notNull(rootStructureController);
    Assert.notNull(identifier);

    WizardViewController controller = (WizardViewController) Services.getBean(identifier);
    controller.setStructureContentController(structureContentController);
    controller.setRootStructure(rootStructureController);
    controller.setParent(parent);

    loadModel(controller);

    try {
      final VLViewConfigXML configXML = Services.getConfigurationFile(controller);
      controller.initViewContext(configXML, rootStructureController.getRootContext());
      controller.build();

      if (controller.isDialog()) {
        // Platform.runLater(() -> controller.show());
      } else {
        // rootStructureController.layout(controller);
      }

      controller.navTo(0);
      return controller;
    } catch (final SecurityException | IllegalArgumentException e) {
      e.printStackTrace();
      //LogR.error(e);
    }

    throw new RuntimeException(MessageFormat.format("Error initialializing : {0}", identifier));
  }


  public static void copyAllAttributesFrom(SingleResult sr, JsonObject jo) {
    for(String key: sr.getData().getAttributes().keySet()) {
      Object value = sr.getData().getAttributes().get(key);

      if(value == null) {
        jo.addProperty(key, "");
      }
      else {
        jo.addProperty(key, String.valueOf(value));
      }
    }
  }
}
