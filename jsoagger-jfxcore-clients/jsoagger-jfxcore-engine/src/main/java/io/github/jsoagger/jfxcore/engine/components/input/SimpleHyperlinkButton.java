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


import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToLabeledHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.AbstractComponent;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleHyperlinkButton extends AbstractComponent implements InjectableComponent {

  protected Hyperlink link = new Hyperlink();
  protected Function<Void, Void> callback = null;


  /**
   * Constructor
   */
  public SimpleHyperlinkButton() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    link.setId(this.id);

    ComponentToLabeledHelper.setTooltip(configuration, link, (AbstractViewController) controller);
    ComponentToButtonBaseHelper.setOnAction(configuration, link, (AbstractViewController) controller);

    final String display = configuration.getPropertyValue(XMLConstants.HYPERLINK_DISPLAY_MODE);
    if (StringUtils.isBlank(display)) {
      link.setContentDisplay(ContentDisplay.LEFT);
      ComponentToLabeledHelper.setText(configuration, link, false, (AbstractViewController) controller);
      IconUtils.setIcon(link, configuration);
    }

    else if ("GRAPHIC_ONLY".equalsIgnoreCase(display)) {
      link.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      IconUtils.setIcon(link, configuration);
    }

    else if ("TEXT_ONLY".equalsIgnoreCase(display)) {
      link.setContentDisplay(ContentDisplay.TEXT_ONLY);
      ComponentToLabeledHelper.setText(configuration, link, false, (AbstractViewController) controller);
    }

    final String style = configuration.getPropertyValue(XMLConstants.STYLE_CLASS);
    if (StringUtils.isNotBlank(style)) {
      link.getStyleClass().addAll(Arrays.asList(style.split(",")));
    }
  }


  public void toUpperCase() {
    link.setText(link.getText() != null ? link.getText().toUpperCase() : "");
  }


  /**
   * @param contextualTo
   */
  public void setContextualTo(Node contextualTo) {
    ComponentToButtonBaseHelper.setOnContextualAction(configuration, link, controller, contextualTo);
  }


  /**
   * @param criteriaContext
   */
  public void filter(CriteriaContext criteriaContext) {
    if (configuration.getComponentById("Validator") != null) {
      final VLViewComponentXML componentXML = configuration.getComponentById("Validator").orElse(null);

      if (componentXML != null) {
        final Optional<String> visible = componentXML.propertyValueOf(XMLConstants.VISIBLE);
        if (visible.isPresent()) {
          link.setVisible(criteriaContext.isTrue(visible.get()));
        }

        final Optional<String> active = componentXML.propertyValueOf(XMLConstants.ACTIVE);
        if (active.isPresent()) {
          link.setDisable(!criteriaContext.isTrue(active.get()));
        }
      }
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return link;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getComponent() {
    return link;
  }
}
