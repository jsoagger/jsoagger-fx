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



import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ICriteriaContext;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

/**
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class RCFiltersContext implements IContextParamSetter {

  /**
   * Constructor
   */
  public RCFiltersContext() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void process(IJSoaggerController view, Object model, ICriteriaContext criteriaContext) {

    boolean isLockedByMe = false;

    if (model != null) {
      String lockedString = RCUtils.getModelAttribute(((SingleResult) model).getData(), "attributes.workInfo.lockedSince");
      boolean isLockedBySomeone = StringUtils.isNotBlank(lockedString);

      String isWorkingCopyString = RCUtils.getModelAttribute(((SingleResult) model).getData(), "attributes.workInfo.isWorkingCopy");
      criteriaContext.setFilter("is_working_copy", isWorkingCopyString);
      boolean isWorkingCopy = Boolean.TRUE == Boolean.valueOf(isWorkingCopyString);

      String isLatestIterationString = RCUtils.getModelAttribute(((SingleResult) model).getData(), "attributes.iterationInfo.isLatestIteration");
      String isLatestVersionString = RCUtils.getModelAttribute(((SingleResult) model).getData(), "attributes.versionInfo.isLatestVersion");
      boolean isLatestIteration = Boolean.TRUE == Boolean.valueOf(isLatestIterationString);
      boolean isLatestVersion = Boolean.TRUE == Boolean.valueOf(isLatestVersionString);

      criteriaContext.setFilter("can_checkout", Boolean.valueOf(isLatestIteration && !isWorkingCopy && !isLockedBySomeone && isLatestVersion).toString());
      criteriaContext.setFilter("can_checkin", Boolean.valueOf(isLatestIteration && isWorkingCopy).toString());
      criteriaContext.setFilter("can_revise", Boolean.valueOf(isLatestIteration && !isWorkingCopy && !isLockedBySomeone && isLatestVersion).toString());
      criteriaContext.setFilter("latest_iteration", isLatestIterationString);
      criteriaContext.setFilter("has_working_copy", Boolean.valueOf(!isWorkingCopy && isLockedBySomeone).toString());

      criteriaContext.setFilter("can_delete_primary_content_item", Boolean.valueOf(isWorkingCopy).toString());
      criteriaContext.setFilter("can_delete_attachment_content_item", Boolean.valueOf(isWorkingCopy).toString());

      criteriaContext.setFilter("can_manage_structure", Boolean.valueOf(isWorkingCopy).toString());
      criteriaContext.setFilter("can_manage_reference", Boolean.valueOf(isWorkingCopy).toString());
      criteriaContext.setFilter("can_upload_attachment_content_item", Boolean.valueOf(isWorkingCopy).toString());
      criteriaContext.setFilter("can_upload_primary_content_item", Boolean.valueOf(isWorkingCopy).toString());

      criteriaContext.setFilter("can_manage_referenced_docs", Boolean.valueOf(isWorkingCopy).toString());
      criteriaContext.setFilter("can_manage_describeded_docs", Boolean.valueOf(isWorkingCopy).toString());
    }

    else {
      System.out.println();
      System.out.println("######### ERROR : CORRECT THIS MODEL FOR CRITERIA IS NULL ####################");
      System.out.println("######### ERROR : CORRECT THIS MODEL FOR CRITERIA IS NULL ####################");
      System.out.println("######### ERROR : CORRECT THIS MODEL FOR CRITERIA IS NULL ####################");

      criteriaContext.setFilter("is_working_copy", "false");

      criteriaContext.setFilter("can_checkout", "false");
      criteriaContext.setFilter("can_checkin", "false");
      criteriaContext.setFilter("can_revise", "false");
      criteriaContext.setFilter("latest_iteration", "false");
      criteriaContext.setFilter("has_working_copy", "false");

      criteriaContext.setFilter("can_delete_primary_content_item", "false");
      criteriaContext.setFilter("can_delete_attachment_content_item", "false");

      criteriaContext.setFilter("can_manage_structure", "false");
      criteriaContext.setFilter("can_manage_reference", "false");
      criteriaContext.setFilter("can_upload_attachment_content_item", "false");
      criteriaContext.setFilter("can_upload_primary_content_item", "false");
    }
  }
}
