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



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAccessRuleResolver;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DeleteObjectFromTableFromSearchAccessRuleResolver extends AbstractRuleResolver implements IAccessRuleResolver {

  /**
   * Constructor
   */
  public DeleteObjectFromTableFromSearchAccessRuleResolver() {
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public UIAccessRule isAccessible(IJSoaggerController controller, VLViewComponentXML compConfig) {
    OperationData roleB = (OperationData) properties.get("forModel");

    if (((AbstractViewController) controller).getStructureContent() == null) {
      throw new RuntimeException("Structure CurrentEditingTableStructure must be set and must be a FullTableStructureController");
    }

    FullTableStructureController tableEditingController = (FullTableStructureController) ((AbstractViewController)controller).getStructureContent().getCurrentEditingTableStructure();

    if (roleB != null && tableEditingController != null) {
      if (tableEditingController.containsModel(roleB)) {
        return UIAccessRule.SHOW;
      } else {
        return UIAccessRule.HIDDEN;
      }
    }

    return UIAccessRule.HIDDEN;
  }
}
