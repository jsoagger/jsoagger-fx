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

package io.github.jsoagger.jfxcore.engine.accessrule;


import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IAccessRuleResolver;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import com.google.gson.JsonObject;

import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class AddLinkBetweenTwoObjectsButtonAccessRuleResolver extends AbstractRuleResolver {

  // needs IsRoleALinkedWithRoleBOperation
  private IOperation operation;


  /**
   * @{inheritedDoc}
   */
  @Override
  public UIAccessRule isAccessible(IJSoaggerController controller, VLViewComponentXML compConfig) {
    SimpleObjectProperty<UIAccessRule> rule = new SimpleObjectProperty<IAccessRuleResolver.UIAccessRule>(UIAccessRule.HIDDEN);

    OperationData operationDataRoleB = (OperationData) get("forModel");
    OperationData operationDataRoleA = ((StructureContentController) controller).getStructureContent().getFormModelData();

    if ((operationDataRoleA != null) && (operationDataRoleB != null)) {
      String roleA = (String) operationDataRoleA.getAttributes().get("fullId");
      String roleB = (String) operationDataRoleB.getAttributes().get("fullId");
      String linkClass = compConfig.getPropertyValue("linkClass");

      if (StringUtils.isNotBlank(roleA) && StringUtils.isNotBlank(roleB)) {
        JsonObject query = new JsonObject();
        query.addProperty("roleA", roleA);
        query.addProperty("roleB", roleB);
        query.addProperty("linkClass", linkClass);

        operation.doOperation(query, res -> {
          SingleResult r = (SingleResult) res;
          String val = (String) r.getMetaData().get("isLinked");
          if ("true".equalsIgnoreCase(val)) {
            rule.set(UIAccessRule.HIDDEN);
          } else {
            rule.set(UIAccessRule.SHOW);
          }
        }, e -> {
          e.printStackTrace();
        });
      }
    }

    return rule.get();
  }
}
