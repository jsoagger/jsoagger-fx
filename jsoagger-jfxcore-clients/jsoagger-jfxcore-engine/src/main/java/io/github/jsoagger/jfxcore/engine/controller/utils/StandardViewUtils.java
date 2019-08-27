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
import java.util.HashMap;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

/**
 * Utility for building {@link StandardViewController}.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class StandardViewUtils  {


  /**
   * Load model associated to this controller
   *
   * @param controller
   */
  private static void loadModel(AbstractViewController controller) {
    if (controller.getModelProvider() != null) {
      final IModelProvider modelProvider = controller.getModelProvider();
      modelProvider.loadModel(controller, null);
    }
  }


  /**
   * Build a {@link StandardViewController} from the given identifier. The child is not layed out in
   * the parent by root structure and parent are processed.
   *
   * @param identifier
   * @param parent
   * @return {@link StandardViewController}
   */
  public static StandardViewController forChildId(String identifier, StructureContentController structureContentController, StandardViewController parent) {
    Assert.notNull(identifier);
    Assert.notNull(parent);

    final StandardViewController controller = (StandardViewController) Services.getBean(identifier);
    controller.setId(identifier);
    controller.setStructureContentController(structureContentController);
    if (controller.isInitialized()) {
      // may be reinit or clear?
      // return controller;
    }

    controller.setRootStructure(parent.getRootStructure());
    controller.setParent(parent);
    loadModel(controller);

    try {
      final VLViewConfigXML configXML = Services.getConfigurationFile(controller);
      controller.initViewContext(configXML, parent.getRootStructure().getRootContext());
      controller.build();
      controller.setInitialized(true);
      return controller;
    } catch (final Exception e) {
      //LogR.error(e);
      e.printStackTrace();
    }

    throw new RuntimeException(MessageFormat.format("Errorinitialializing : {0}", identifier));
  }


  /**
   * Generates {@link StandardViewController} and layout it inside given
   * {@link RootStructureController}.
   *
   * @param root
   * @param childIdentifier
   */
  public static void forIdAndLayout(AbstractViewController root, String childIdentifier) {
    forIdAndLayout(root, null, childIdentifier, null);
  }


  /**
   * Generates {@link StandardViewController} and layout it inside given
   * {@link RootStructureController}.
   *
   * @param root
   * @param childIdentifier
   * @param rootform
   */
  public static void forIdAndLayout(AbstractViewController root, StructureContentController structureContentController, String childIdentifier, ObjectModel rootForm) {
    Assert.notNull(root);
    Assert.notNull(childIdentifier);

    final StandardViewController controller = (StandardViewController) Services.getBean(childIdentifier);
    controller.setId(childIdentifier);
    controller.setStructureContentController(structureContentController);
    controller.setRootStructure(root.getRootStructure());
    controller.setModel(rootForm);

    if (root instanceof RootStructureController) {
      controller.setRootStructure((RootStructureController) root);
    } else {
      controller.setRootStructure(root.getRootStructure());
    }

    try {
      final VLViewConfigXML configXML = Services.getConfigurationFile(controller);
      controller.initViewContext(configXML, root.getRootStructure().getRootContext());
      controller.build();
      controller.setInitialized(true);

      if (root instanceof RootStructureController) {
        ((RootStructureController) root).addChild(controller);
      }
    } catch (final SecurityException | IllegalArgumentException e) {
      //LogR.error(e);
    }
  }


  /**
   * Generates {@link StandardViewController} without {@link StructureContentController}.
   *
   * @param root
   * @param identifier
   * @return {@link StandardViewController}
   */
  public static StandardViewController forId(RootStructureController root, String identifier, OperationData model) {
    try {
      Assert.notNull(root, "RootStructureController can not be null when building StandardViewController (" + identifier + ")");
      Assert.notNull(identifier);

      final StandardViewController controller = (StandardViewController) Services.getBean(identifier);
      if (controller.isInitialized()) {
        return controller;
      }

      controller.setRootStructure(root);
      controller.setStructureContentController(null);
      controller.setId(identifier);

      if (model == null) {
        loadModel(controller);
      } else {
        final SingleResult singleResult = new SingleResult();
        singleResult.setData(model);
        controller.setModel(singleResult);
      }

      final VLViewConfigXML configXML = Services.getConfigurationFile(controller);
      controller.initViewContext(configXML, root.getRootContext());
      controller.build();
      controller.setInitialized(true);
      return controller;
    } catch (final Exception e) {
      //LogR.error(e);
      e.printStackTrace();
    }

    throw new RuntimeException(MessageFormat.format("Errorinitialializing : {0}", identifier));
  }


  /**
   * Generates {@link StandardViewController} without {@link StructureContentController}.
   *
   * @param root
   * @param identifier
   * @return {@link StandardViewController}
   */
  public static StandardViewController forId(RootStructureController root, String identifier) {
    return forId(root, identifier, null);
  }


  /**
   * Generates {@link StandardViewController} with given identifier. Do not layout the child inside
   * the {@link RootStructureController}
   *
   * @param root
   * @param identifier
   * @return {@link StandardViewController}
   */
  public static StandardViewController forId(RootStructureController root, StructureContentController structureContentController, String identifier) {
    return forId(root, structureContentController, identifier, null);
  }


  /**
   * Generates {@link StandardViewController} with given identifier. Do not layout the child inside
   * the {@link RootStructureController}
   *
   * @param root
   * @param identifier
   * @return {@link StandardViewController}
   */
  public static StandardViewController forId(RootStructureController root, StructureContentController structureContentController, String identifier, OperationData model) {
    try {

      Assert.notNull(root, "RootStructureController can not be null when building StandardViewController (" + identifier + ")");
      Assert.notNull(identifier);

      final StandardViewController controller = (StandardViewController) Services.getBean(identifier);
      if(controller == null) {
        System.out.println("[ERROR] View not found " + identifier);
      }

      if (controller.isInitialized()) {
        return controller;
      }

      controller.setRootStructure(root);
      controller.setStructureContentController(structureContentController);
      controller.setId(identifier);

      if (model == null) {
        loadModel(controller);
      } else {
        final SingleResult singleResult = new SingleResult();
        singleResult.setData(model);
        controller.setModel(singleResult);
      }

      final VLViewConfigXML configXML = Services.getConfigurationFile(controller);
      controller.initViewContext(configXML, root.getRootContext());

      // if the view is builded in the context of a current content, ie
      // structureContentController.currentContent is not null
      // add all criterias of the current to this content
      // because some actions on this view may be conextual to this view
      if (structureContentController != null && structureContentController.getCurrentContent() != null) {
        final HashMap<String, String> filters = structureContentController.getCurrentContent().getAllCriterias();
        controller.viewContext().addCriterias(filters);
      }

      controller.build();
      controller.setInitialized(true);
      return controller;
    } catch (final Exception e) {
      //LogR.error(e);
      e.printStackTrace();
    }

    throw new RuntimeException(MessageFormat.format("Errorinitialializing : {0}", identifier));
  }
}
