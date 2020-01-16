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



import io.github.jsoagger.jfxcore.api.ICriteriaContext;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.ICriteriaEvaluator;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewFilterParamXML;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewFilterXML;

/**
 * Recursivly evalutes the boolean value of a filter in the given context
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class CriteriaEvaluatorImpl implements ICriteriaEvaluator {

  @Override
  public boolean evaluate(IJSoaggerController controller, VLViewFilterXML noeud, ICriteriaContext criteriaContext) {

    if (noeud == null) {
      return true;
    }

    // si le noeud est un 'not' il aura un unique filter(fils) à evaluer
    if (!noeud.notEmpty()) {
      final VLViewFilterXML uniqueFils = noeud.getNot().getFilter();
      final VLViewFilterXML resolvedFilter = Services.resolveFilter(controller, uniqueFils.getName());
      final boolean result = evaluate(controller, resolvedFilter, criteriaContext);
      return !result;
    }

    // sil il n'y a pas de and et de or et de not, on a atteint la feuille
    // de l'arbre
    // on va evaluer le param associer
    if (noeud.andEmpty() && noeud.orEmpty() && noeud.notEmpty()) {
      return evaluate(noeud.getParam(), criteriaContext);

    }
    // sinon calclu recursif
    else {
      if (!noeud.andEmpty()) {
        final VLViewFilterXML resolvedFilter0 = Services.resolveFilter(controller, noeud.and().getFilters().get(0).getName());
        boolean result = evaluate(controller, resolvedFilter0, criteriaContext);
        if (noeud.and().getFilters().size() > 1) {

          for (int i = 1; i < noeud.and().getFilters().size(); i++) {
            final VLViewFilterXML resolvedFilterXX = Services.resolveFilter(controller, noeud.and().getFilters().get(i).getName());
            result = result && evaluate(controller, resolvedFilterXX, criteriaContext);
          }
        }

        return result;
      }

      else {
        final VLViewFilterXML resolvedFilter0 = Services.resolveFilter(controller, noeud.or().getFilters().get(0).getName());
        boolean result = evaluate(controller, resolvedFilter0, criteriaContext);
        if (noeud.or().getFilters().size() > 1) {
          for (int i = 1; i < noeud.or().getFilters().size(); i++) {

            final VLViewFilterXML resolvedFilterXX = Services.resolveFilter(controller, noeud.or().getFilters().get(i).getName());
            result = result | evaluate(controller, resolvedFilterXX, criteriaContext);
          }
        }
        return result;
      }
    }
  }


  private boolean evaluate(VLViewFilterParamXML filterParamXML, ICriteriaContext criteriaContext) {
    if (filterParamXML == null) {
      return false;
    }

    if (filterParamXML.getName() == null) {
      return false;
    }

    if (!criteriaContext.containsFilter(filterParamXML.getName())) {
      //System.out.println("View context does not contains filter for : " + filterParamXML.getName());
      return false;
    }

    final String value = criteriaContext.filterValue(filterParamXML.getName());
    return value != null && value.equalsIgnoreCase(filterParamXML.getValue());
  }
}
