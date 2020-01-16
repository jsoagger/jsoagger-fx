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

package io.github.jsoagger.jfxcore.engine.components.layoutproc;


import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetGroupLayout;
import io.github.jsoagger.jfxcore.api.IFieldsetProcessor;
import io.github.jsoagger.jfxcore.api.IForwardEditableFieldsetsProcessor;
import io.github.jsoagger.jfxcore.api.IForwardEditor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.FormFieldset;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Generates a list of {@link FormFieldset} and wrap them in {@link VBox}. With a
 * {@link FormFieldsetGroupLayout} with which we can select each fieldset group.
 * <p>
 * Each fieldset may have its own processor. If no processor is declared for a fieldset, we use the
 * default one.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormFieldsetListProcessor implements IForwardEditableFieldsetsProcessor {

  private static final String FORM_FIELDSET_PROCESSOR = "FormFieldsetProcessor";
  private static final String FIELDSET_IMPL = "fieldsetImpl";
  private final static String ACTIONS = "Actions";


  /**
   * @param controller
   * @param contentCfg The wizardConfiguration of the FormFieldsets
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML fieldsetListConfig) {
    return process(controller, fieldsetListConfig, null);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML fieldsetListConfig, IForwardEditor editor) {
    String layoutImpl = fieldsetListConfig.getPropertyValue("layoutImpl");
    if (StringUtils.isEmpty(layoutImpl)) {
      layoutImpl = "FieldsetTopTabedLayout";
    }

    Boolean displayGroupSelector = fieldsetListConfig.getBooleanProperty("displayGroupSelector", true);
    IFieldsetGroupLayout layoutManager = (IFieldsetGroupLayout) Services.getBean(layoutImpl);
    layoutManager.setRootConfig(fieldsetListConfig);

    // one level below, the fieldset list
    for (final VLViewComponentXML fieldsetConfig : fieldsetListConfig.getSubcomponents()) {

      VLViewComponentXML realConfig = fieldsetConfig;
      if (StringUtils.isNotBlank(fieldsetConfig.getReference())) {
        realConfig = ComponentUtils.resolveDefinition((AbstractViewController)controller, fieldsetConfig.getReference()).orElse(null);
      }

      if (realConfig != null && !ACTIONS.equals(realConfig.getId())) {

        String componentProcessorName = realConfig.getProcessor();
        if (StringUtils.isEmpty(componentProcessorName)) {
          componentProcessorName = FORM_FIELDSET_PROCESSOR;
        }

        IFieldsetProcessor fieldsetProcessor = (IFieldsetProcessor) Services.getBean(componentProcessorName);
        IFieldset fieldset = fieldsetProcessor.processFieldset(controller, realConfig);
        fieldset.setForwardEditor(editor);
        layoutManager.addFieldset(fieldset);
        //NodeHelper.setHVGrow(fieldset.getDisplay());
      }
    }

    layoutManager.setDisplaySelectors(displayGroupSelector);
    layoutManager.displayAll();
    return layoutManager.getDisplay();
  }
}
