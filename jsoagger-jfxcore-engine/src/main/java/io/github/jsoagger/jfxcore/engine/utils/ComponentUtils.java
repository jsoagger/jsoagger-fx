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

package io.github.jsoagger.jfxcore.engine.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.core.utils.StringUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IForwardEditableFieldsetsProcessor;
import io.github.jsoagger.jfxcore.api.IForwardEditor;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.components.input.SimpleButton;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;

import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ComponentUtils {

  //protected static final Logger //LOG = LogManager.getLogger(ComponentUtils.class);
  protected static Map<String, VLViewComponentXML> CACHE = new HashMap<>();
  protected static final String VALIDATOR = "Validator";


  /**
   * List passed in parameter is a list where components have not been resolved, mean id is empty, ref
   * is not empty
   *
   * @param controller
   * @param components
   * @return
   */
  public static List<IBuildable> resolveAndGenerate(AbstractViewController controller, List<VLViewComponentXML> components) {
    Assert.notNull(components);
    Assert.notNull(controller);

    final List<VLViewComponentXML> resolvedComponents = resolveDefinitions(controller, components);
    return generate(controller, resolvedComponents);
  }


  /**
   *
   * Generates {@link IBuildable} from given list of components.
   *
   * @param controller
   * @param components
   * @return {@link List}
   */
  public static List<IBuildable> generate(AbstractViewController controller, List<VLViewComponentXML> resolved) {
    Assert.notNull(resolved);
    Assert.notNull(controller);

    List<IBuildable> result = new ArrayList<>();
    for (VLViewComponentXML definition : resolved) {
      IBuildable buildable = generate(controller, definition);
      if (buildable != null) {
        result.add(buildable);
      }
    }

    return result;
  }


  public static IBuildable generate(AbstractViewController controller, VLViewComponentXML resolved) {
    String componentType = resolved.getPropertyValue("type");
    if (StringUtils.isNotBlank(componentType)) {
      IBuildable buildable = build(controller, resolved);
      return buildable;
    }

    return null;
  }


  /**
   * Resolve the model with the given identifier.
   *
   * @param controller
   * @param id
   * @return VLViewComponentXML
   */
  public static Optional<VLViewComponentXML> resolveOptionalModel(AbstractViewController controller, String id) {
    VLViewComponentXML componentXML = resolveModel(controller, id);
    if (componentXML == null) {
      return Optional.empty();
    }
    return Optional.of(componentXML);
  }


  /**
   * Resolve the model with the given identifier.
   *
   * @param controller
   * @param id
   * @return VLViewComponentXML
   */
  public static VLViewComponentXML resolveModel(AbstractViewController controller, String id) {
    Assert.notNull(controller);
    Assert.notNull(id);

    ////LOG.trace(MessageFormat.format("Searching for component with identifier {0}", id));
    VLViewComponentXML result = getFromCache(id).orElse(null);

    if (result == null) {

      //LOG.trace(MessageFormat.format("Component with identifier {0} not found from cache, seraching from local config", id));

      result = getFromLocalConfig(controller, id).orElse(null);
      if (result == null) {

        //LOG.trace(MessageFormat.format("Component with identifier {0} not found from local config, searching from root structure config", id));

        if (controller.getRootStructure() != null) {
          //LOG.trace(MessageFormat.format("Root structure is {0}", controller.getRootStructure().getClass()));
          result = getFromRootStructureConfig(controller.getRootStructure(), id).orElse(null);
        }

        if (result == null) {
          //LOG.trace(MessageFormat.format("Component with identifier {0} not found from root structure config, searching from global config", id));
          result = getFromGlobalConfig(id).orElse(null);
        }
      }
    }

    if (result == null) {
      //LOG.warn(MessageFormat.format("Component with identifier {0} not found at all!!", id));
      return null;
    } else {
      String criteria = result.getCriteria();
      if (StringUtils.isNotBlank(criteria)) {
        boolean crit = controller.evaluateFilter(result);
        if (!crit) {
          return null;
        }
      }
    }

    return result;
  }


  /**
   * @param type
   * @param controller
   * @param wizardConfiguration
   * @return IBuildable
   */
  public static IBuildable build(AbstractViewController controller, VLViewComponentXML configuration) {
    String componentType = configuration.getPropertyValue("type");
    if (StringUtils.isNotBlank(componentType)) {
      IBuildable buildable = build(componentType, controller, configuration);
      return buildable;
    }

    return null;
  }


  /**
   * @param type
   * @param controller
   * @param wizardConfiguration
   * @return IBuildable
   */
  public static IBuildable build(String type, AbstractViewController controller, VLViewComponentXML configuration) {
    IBuildable buildable = (IBuildable) Services.getBean(type);
    if(buildable == null) {
      throw new RuntimeException("Buildable with type "+ type + " not provided in application context" );
    }

    buildable.buildFrom(controller, configuration);
    return buildable;
  }


  /**
   * Components are list of referenced components to resolver in this controller or parent controller.
   *
   * @param components
   * @param controller
   * @return List
   */
  public static List<VLViewComponentXML> resolveDefinitions(final AbstractViewController controller, final List<VLViewComponentXML> refs) {
    Assert.notNull(controller);
    Assert.notNull(refs);

    List<VLViewComponentXML> result = new ArrayList<>();
    if (!refs.isEmpty()) {
      for(VLViewComponentXML e : refs) {
        VLViewComponentXML optional = resolveDefinition(controller, e).orElse(null);
        if (optional != null) result.add(optional);
        //else System.out.println("Not resolved configuration component -> " + e.getReference());
      }
    }

    return result;
  }


  /**
   * Resolve the model with the given identifier. Sometimes, the source {@link VLViewComponentXML}
   * holds some attributes to copied in the destination.
   *
   * @param controller
   * @param ref
   * @return VLViewComponentXML
   */
  public static Optional<VLViewComponentXML> resolveDefinition(AbstractViewController controller, VLViewComponentXML ref) {
    Assert.notNull(controller);
    Assert.notNull(ref);

    // sometimes holds processor or controller not reference to something to
    // build
    if (StringUtils.isNotBlank(ref.getReference())) {
      Optional<VLViewComponentXML> optional = resolveDefinition(controller, ref.getReference());
      optional.ifPresent(solved -> {
        copySourcesAttributes(ref, solved);
      });

      return optional;
    }

    return Optional.empty();
  }


  protected static void copySourcesAttributes(VLViewComponentXML referenced, VLViewComponentXML resolved) {

    if(referenced.getVisibleIf() != null)
      resolved.setVisibleIf(referenced.getVisibleIf());

    if(referenced.getCriteria() != null && resolved.getCriteria() == null)
      resolved.setCriteria(referenced.getCriteria());

    if(referenced.getSeparatorAfter() != null)
      resolved.setSeparatorAfter(referenced.getSeparatorAfter());

    if(referenced.getResponsiveOrder() != null)
      resolved.setResponsiveOrder(referenced.getResponsiveOrder());

    if(referenced.getMasterColumn() != null)
      resolved.setMasterColumn(referenced.getMasterColumn());

    if (StringUtils.isNotBlank(referenced.getDefaultButton())) {
      if(!resolved.getProperties().containsKey("default"))
        resolved.getProperties().put("default", referenced.getDefaultButton());
    }

    if (referenced.getComponentById(VALIDATOR) != null) {
      if (resolved.getComponentById(VALIDATOR) != null) {
        final Optional<VLViewComponentXML> comp = resolved.getComponentById(VALIDATOR);
        comp.ifPresent(c -> resolved.getSubcomponents().remove(c));
      }

      final Optional<VLViewComponentXML> d = referenced.getComponentById(VALIDATOR);
      d.ifPresent(f -> resolved.getSubcomponents().add(f));
    }
  }


  /**
   * Resolve all subcomponents from toResolveActionsCfg. These subcomponents must have not empty
   * reference. Only subcomponents with non empty reference attribute will be treated.
   *
   * @param from
   * @param rootComponent
   */
  public static List<VLViewComponentXML> resolveReferencedSubComponents(final AbstractViewController controller, final VLViewComponentXML from) {
    Assert.notNull(controller);
    Assert.notNull(from);

    if (from.hasSubComponent()) {
      return resolveDefinitions(controller, from.getSubcomponents());
    }
    return new ArrayList<>();
  }


  /**
   * Resolve the model with the given identifier.
   *
   * @param controller
   * @param id
   * @return VLViewComponentXML
   */
  public static Optional<VLViewComponentXML> resolveDefinition(AbstractViewController controller, String id) {
    Assert.notNull(controller);
    Assert.notNull(id);

    //LOG.trace(MessageFormat.format("Searching component with identifier {0} from local config", id));
    VLViewComponentXML result = getFromLocalConfig(controller, id).orElse(null);

    if (result == null) {
      //LOG.trace(MessageFormat.format("Component with identifier {0} not found from local config, searching from root structure config", id));

      if (controller.getRootStructure() != null) {
        //LOG.trace(MessageFormat.format("Root structure is {0}", controller.getRootStructure().getClass()));
        result = getFromRootStructureConfig(controller.getRootStructure(), id).orElse(null);
      }

      if (result == null) {
        //LOG.trace(MessageFormat.format("Searching for component with identifier {0} form cache", id));
        result = getFromCache(id).orElse(null);

        if (result == null) {
          //LOG.trace(MessageFormat.format("Component with identifier {0} not found from cache, searching from global config", id));
          result = getFromGlobalConfig(id).orElse(null);

          if (result != null) {
            if(!CACHE.containsKey(result.getId()))
              CACHE.put(result.getId(), result);
          }
        }
      }
    }

    if (result == null) {
      //LOG.warn(MessageFormat.format("Component with identifier {0} not found at all!!", id));
      //System.out.println("WARNING ---- Component with identifier (" + id +") not found at all!!");
      return Optional.empty();
    } else {
      String criteria = result.getCriteria();
      if (StringUtils.isNotBlank(criteria)) {
        boolean crit = controller.evaluateFilter(result);
        if (!crit) {
          //System.out.println(" ----> Control hide (" + id + ") by criteria : " + criteria);
          return Optional.empty();
        }
        else {
          //System.out.println(" ++++> Control show (" + id + ") by criteria : " + criteria);
        }
      }
      else {
        //System.out.println(" ******> Control without criteria (" + id + ")");
      }
    }

    return Optional.of(result);
  }


  private static Optional<VLViewComponentXML> getFromLocalConfig(AbstractViewController controller, String id) {
    VLViewConfigXML configXML = controller.viewContext().getViewConfig();
    if (configXML.getComponentById(id).isPresent()) {
      return configXML.getComponentById(id);
    }

    if (controller.getCommonComponents() != null) {
      if (controller.getCommonComponents().getComponent(id) != null) {
        return Optional.of(controller.getCommonComponents().getComponent(id));
      }
    }

    if (controller.getParent() != null) {
      VLViewConfigXML cfg = controller.getParent().viewContext().getViewConfig();
      return cfg.getComponentById(id);
    }

    return Optional.empty();
  }


  private static Optional<VLViewComponentXML> getFromRootStructureConfig(RootStructureController rootController, String id) {
    VLViewConfigXML configXML = rootController.viewContext().getViewConfig();
    if (configXML.getComponentById(id).isPresent()) {
      return configXML.getComponentById(id);
    }

    if (rootController.getCommonComponents() != null) {
      if (rootController.getCommonComponents().getComponent(id) != null) {
        return Optional.of(rootController.getCommonComponents().getComponent(id));
      }
    }

    return Optional.empty();
  }


  private static Optional<VLViewComponentXML> getFromCache(String id) {
    return Optional.empty();
  }


  public static Optional<VLViewComponentXML> getFromGlobalConfig(String id) {
    if (ViewStructure.isViewConfigOffLine()) {
      VLViewComponentXML comp =  Services.getCompFromGlobalConfig(id);
      return Optional.ofNullable(comp);
    }

    return Optional.empty();
  }


  public static boolean isAttributeMandatory(final VLViewComponentXML attrConfig) {
    final VLViewComponentXML displayConfig = attrConfig.getNullableComponentById(XMLConstants.VALIDATION_CONFIG);
    boolean mandatory = false;
    if (displayConfig != null) {
      mandatory = displayConfig.getBooleanProperty(XMLConstants.MANDATORY);
    }

    return mandatory;
  }


  public static VLViewComponentXML resolveComponent(VLViewComponentXML component, String id) {
    return component.getNullableComponentById(id);
  }


  /**
   * Generates a single material button
   *
   * @param controller
   * @param config
   * @return Null or Node
   */
  public static Node buildMaterialButton(AbstractViewController controller, VLViewComponentXML config) {
    if (config != null) {
      SimpleButton button = new SimpleButton();
      button.buildFrom(controller, config);
      button.getDisplay().getStyleClass().add("button-material");
      return button.getDisplay();
    }

    return null;
  }


  /**
   * Builds a component. If the component declares a {@link IComponentProcessor}, this processor will
   * be called.
   * <p>
   * If the components declares a contentImpl as a {@link IBuildable}, this {@link IBuildable} will be
   * builded.
   *
   * @param controller
   * @param configuration
   * @return {@link Node} or Null
   */
  public static Node buildFromProcessor(AbstractViewController controller, VLViewComponentXML configuration) {
    // content can be processed either from processor
    IComponentProcessor processor = (IComponentProcessor) Services.getBean(configuration.getProcessor());
    if (processor != null) {
      if (controller instanceof StandardViewController) {
        Object element = processor.processElement(controller, configuration);
        ((StandardViewController) controller).processedElement(element);
      }

      Node processed = processor.process(controller, configuration);
      return processed;
    }

    // or given an implementation
    else {
      String contentImpl = configuration.getPropertyValue("contentImpl");
      if (StringUtils.isNotBlank(contentImpl)) {
        IBuildable buildable = (IBuildable) Services.getBean(contentImpl);
        buildable.buildFrom(controller, configuration);
        return buildable.getDisplay();
      }
    }

    return null;
  }


  /**
   * Builds a component. If the component declares a {@link IComponentProcessor}, this processor will
   * be called.
   * <p>
   * If the components declares a contentImpl as a {@link IBuildable}, this {@link IBuildable} will be
   * builded.
   *
   * @param controller
   * @param configuration
   * @return {@link Node} or Null
   */
  public static Node buildFromProcessor(AbstractViewController controller, VLViewComponentXML configuration, IForwardEditor forwardEditor) {
    if (forwardEditor == null) {
      return buildFromProcessor(controller, configuration);
    }

    // content can be processed either from processor
    IComponentProcessor processor = (IComponentProcessor) Services.getBean(configuration.getProcessor());
    if (processor != null && processor instanceof IForwardEditableFieldsetsProcessor) {
      Node processed = ((IForwardEditableFieldsetsProcessor) processor).process(controller, configuration, forwardEditor);
      return processed;
    }

    else if (processor != null) {
      Node processed = processor.process(controller, configuration);
      return processed;
    }

    // or given an implementation
    else {
      String contentImpl = configuration.getPropertyValue("contentImpl");
      if (StringUtils.isNotBlank(contentImpl)) {
        IBuildable buildable = (IBuildable) Services.getBean(contentImpl);
        buildable.buildFrom(controller, configuration);
        return buildable.getDisplay();
      }
    }

    return null;
  }
}
