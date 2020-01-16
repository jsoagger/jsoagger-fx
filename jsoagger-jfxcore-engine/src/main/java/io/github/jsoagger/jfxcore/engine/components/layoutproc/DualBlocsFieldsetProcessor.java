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




import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldsetBlocProcessor;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormFieldsetBloc;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DualBlocsFieldsetProcessor implements IFieldsetBlocProcessor {

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML fieldsetCfg) {
    return process(controller, fieldsetCfg, null, null, false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML blocCfg, List inputCollector, List<IFormFieldsetRow> formRowsCollector, boolean first) {
    final FormFieldsetBloc leftBlock = new FormFieldsetBloc();
    final FormFieldsetBloc rightBlock = new FormFieldsetBloc();

    final String styleClass = blocCfg.getPropertyValue(XMLConstants.STYLE_CLASS);
    if (StringUtils.isNotBlank(styleClass)) {
      leftBlock.getStyleClass().addAll(styleClass.split(","));
      rightBlock.getStyleClass().addAll(styleClass.split(","));
    }

    rightBlock.managedProperty().bind(rightBlock.visibleProperty());

    final List<VLViewComponentXML> blocConfigs = blocCfg.getSubcomponents();
    if ((blocConfigs == null) || blocConfigs.isEmpty() || (blocConfigs.size() > 2)) {
      // do nothing

    } else {

      VLViewComponentXML leftBlocCfg = null;
      VLViewComponentXML rightBlocCfg = null;

      try {
        leftBlocCfg = blocConfigs.get(0);
        rightBlocCfg = blocConfigs.get(1);
      } catch (final IndexOutOfBoundsException e) {
      }

      if (leftBlocCfg != null) {
        leftBlock.buildFrom(leftBlocCfg, (AbstractViewController) controller);
        NodeHelper.setHVGrow(leftBlock);

        if (inputCollector != null) {
          if (!"view".equals(leftBlocCfg.getPropertyValue("mode"))) {
            for(FormFieldsetRow row: leftBlock.getRows()) {
              inputCollector.addAll(row.getEntries());
              formRowsCollector.add(row);
            }
          }
        }
      }

      if (rightBlocCfg != null) {
        rightBlock.buildFrom(rightBlocCfg, (AbstractViewController) controller);
        NodeHelper.setHgrow(rightBlock);

        if (inputCollector != null) {
          for(FormFieldsetRow row: rightBlock.getRows()) {
            inputCollector.addAll(row.getEntries());
            formRowsCollector.add(row);
          }
        }
      } else {
        rightBlock.setVisible(false);
      }
    }

    final HBox blocsContainer = new HBox();
    blocsContainer.setSpacing(16);
    blocsContainer.getChildren().addAll(leftBlock, rightBlock);
    NodeHelper.setHgrow(blocsContainer);

    return blocsContainer;
  }
}
