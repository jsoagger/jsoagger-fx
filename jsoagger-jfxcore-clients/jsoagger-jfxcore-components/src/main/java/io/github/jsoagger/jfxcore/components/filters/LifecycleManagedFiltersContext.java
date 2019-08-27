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

package io.github.jsoagger.jfxcore.components.filters;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ICriteriaContext;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class LifecycleManagedFiltersContext implements IContextParamSetter {

  /**
   * Constructor
   */
  public LifecycleManagedFiltersContext() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void process(IJSoaggerController view, Object model, ICriteriaContext criteriaContext) {
    // can not manage states if has working copy
    String lockedString = RCUtils.getModelAttribute(((SingleResult) model).getData(), "attributes.workInfo.lockedSince");
    boolean isLocked = StringUtils.isNotBlank(lockedString);

    String isLatestIterationString = RCUtils.getModelAttribute(((SingleResult) model).getData(), "attributes.iterationInfo.isLatestIteration");
    boolean isLatestIteration = Boolean.TRUE == Boolean.valueOf(isLatestIterationString);

    boolean canManageState = !isLocked && isLatestIteration;
    if (canManageState) {
      OperationData data = ((SingleResult) model).getData();
      String stateByDenote = (String) data.getLinks().get("stateByDenote");
      String stateByPromote = (String) data.getLinks().get("stateByPromote");
      if (StringUtils.isNotBlank(stateByDenote)) {
        criteriaContext.setFilter("can_denote", Boolean.TRUE.toString());
      }

      if (StringUtils.isNotBlank(stateByPromote)) {
        criteriaContext.setFilter("can_promote", Boolean.TRUE.toString());
      }
    }
    else {
      criteriaContext.setFilter("can_promote", Boolean.FALSE.toString());
      criteriaContext.setFilter("can_denote", Boolean.FALSE.toString());
    }
  }
}
