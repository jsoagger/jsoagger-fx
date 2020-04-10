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

package io.github.jsoagger.jfxcore.engine.components.input;


import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToLabeledHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleButton extends ActionableComp implements InjectableComponent {

  protected static final String SIMPLE_BUTTON = "simple-button";
  protected static final String REGEX = ",";

  protected Button button = NodeHelper.jfxButton("");


  /**
   * Constructor
   */
  public SimpleButton() {
    super();
    button.addEventFilter(ActionEvent.ACTION, this::handle);
    button.setCursor(Cursor.HAND);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void destroy() {
    super.destroy();
    button.removeEventFilter(ActionEvent.ACTION, this::handle);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    button
        .setDefaultButton(configuration.booleanPropertyValueOf(XMLConstants.DEFAULT).orElse(false));
    ComponentToLabeledHelper.setTooltip(configuration, button, (AbstractViewController) controller);
    IconUtils.setIcon(button, configuration);

    NodeHelper.styleClassAddAll(button, configuration);

    final String upperCase = configuration.getPropertyValue("upperCase");
    ComponentToLabeledHelper.setText(configuration, button, "true".equalsIgnoreCase(upperCase),
        (AbstractViewController) controller);

    if (AbstractApplicationRunner.isDesktop()) {
      final String displayMode =
          configuration.getPropertyValue(XMLConstants.HYPERLINK_DISPLAY_MODE, "LEFT");
      button.setContentDisplay(ContentDisplay.valueOf(displayMode));
    } else {
      final String displayMode =
          configuration.getPropertyValue(XMLConstants.HYPERLINK_DISPLAY_MODE);
      if (StringUtils.isNotBlank(displayMode)) {
        button.setContentDisplay(ContentDisplay.valueOf(displayMode));
      }
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void handle(ActionEvent e) {
    task = new ButtonActionTask(button, e, this);
    new Thread(task).start();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return button;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return button;
  }


  public void toUpperCase() {
    button.setText(button.getText().toUpperCase());
  }
}
