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

package io.github.jsoagger.jfxcore.engine.components.wizard.editor;




import io.github.jsoagger.jfxcore.api.IAttributeInlineEditionHandler;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.components.GroupedInLineEditor;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class GroupedAttributesInlineEditionHandler implements IAttributeInlineEditionHandler {

  /**
   * Constructor
   */
  public GroupedAttributesInlineEditionHandler() {}


  /**
   * @{inheritedDoc}
   */
  public void onInlineAction(AbstractViewController controller, VLViewComponentXML configuration, InputComponentWrapper componentWrapper) {
    IBuildable inPlaceEditor = new GroupedInLineEditor();
    inPlaceEditor.buildFrom((IJSoaggerController) controller, configuration);
    ((GroupedInLineEditor) inPlaceEditor).setOwner(componentWrapper);
    ((GroupedInLineEditor) inPlaceEditor).showEditor();
  }
}
