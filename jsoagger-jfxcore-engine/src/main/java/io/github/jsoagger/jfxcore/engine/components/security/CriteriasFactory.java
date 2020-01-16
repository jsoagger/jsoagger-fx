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

package io.github.jsoagger.jfxcore.engine.components.security;

import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewContextFilterGroupXML;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewContextFilterXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CriteriasFactory {

  /**
   * Calcul les criterias dans le contexte de la vue. Les criterias sont calculés par rapport au
   * rootModel de la vue. Si cette derniere est nulle au moment du calcul, les criterias associées
   * seront vides.
   */
  public static CriteriaContext processCriterias(IJSoaggerController view) {
    if (view.getModel() == null) {
      return null;
    }

    return processCriterias(view.getModel(), view);
  }


  /**
   * Calcul les criterias par rapport au model passé en parametre et non pas par rapport au rootModel
   * de la vue. Peut être utile pour les lignes dans une table par exemple. En effet pour calculer les
   * contexte menus, les criterias doivent être calculés par rapport a une ligne selectionnée.
   */
  public static CriteriaContext processCriterias(Object model, IJSoaggerController view) {
    final CriteriaContext context = new CriteriaContext();

    if (view.config().getFiltersGroup() != null && view.config().getFiltersGroup().getFilters() != null) {
      for (final VLViewContextFilterXML contextFilterXML : view.config().getFiltersGroup().getFilters()) {

        final String handlerName = contextFilterXML.getHandler();
        if (StringUtils.isNotBlank(handlerName)) {
          final IContextParamSetter contextParamSetter = (IContextParamSetter) Services.getBean(handlerName);
          contextParamSetter.process(view, model, context);
        }
      }
    }

    // first resolve the context-filter-group
    final String reference = "allfilters";
    // final VLViewContextFilterGroupXML resolved =
    // resolveFilterGroup(reference, view);
    final VLViewContextFilterGroupXML resolved = null;
    if (resolved != null) {

      // when found the context-filter-group, resolve each context-filter
      // inside it
      // instanciate it and call process to populate the viewcontext
      final List<VLViewContextFilterXML> contextFilters = resolved.getFilters();
      if (contextFilters != null) {
        for (final VLViewContextFilterXML ctxFlt : contextFilters) {
          final VLViewContextFilterXML resolvedFlt = resolveFilterContext(ctxFlt.getRef(), (AbstractViewController) view);

          if (resolved != null) {
            // process view context
            final String handlerName = resolvedFlt.getHandler();
            try {
              final Class clazz = Class.forName(handlerName);
              final IContextParamSetter contextParamSetter = (IContextParamSetter) clazz.newInstance();
              contextParamSetter.process(view, model, context);

            } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
              e.printStackTrace();
            }
          }

          else {
            //System.out.println("Error context-filter not resolved with reference : " + reference);
          }
        }
      }
    } else {
      //System.out.println("Error context-filter group not resolved with reference : " + reference);
    }

    return context;
  }


  private static VLViewContextFilterXML resolveFilterContext(String id, AbstractViewController abstractView) {
    VLViewContextFilterXML result = null;

    if (abstractView.config() != null) {
      if (abstractView.config().getFiltersGroup() != null) {
        for (final VLViewContextFilterXML grp : abstractView.config().getFiltersGroup().getFilters()) {
          if (id.equalsIgnoreCase(grp.getId())) {
            result = grp;
          }
        }
      }
    }

    // if not found find it in the parent wizardConfiguration if there is
    // one
    // parent not parent means this id a root view
    if (result == null && abstractView.getRootStructure() != null) {
      final VLViewConfigXML rootConfig = abstractView.getRootStructure().viewContext().getViewConfig();
      if (rootConfig != null && rootConfig.getFiltersGroup() != null) {
        for (final VLViewContextFilterXML grp : abstractView.config().getFiltersGroup().getFilters()) {
          if (id.equalsIgnoreCase(grp.getId())) {
            result = grp;
          }
        }
      }
    }

    if (result == null) {
      result = Services.getFiltersContext(id);
    }

    return result;
  }
}
