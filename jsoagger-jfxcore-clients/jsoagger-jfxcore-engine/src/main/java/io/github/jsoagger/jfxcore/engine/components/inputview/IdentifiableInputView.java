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

package io.github.jsoagger.jfxcore.engine.components.inputview;



import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Display the value of ann attribute by using a {@link ModelIdentityPresenter}.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 31 janv. 2018
 */
public class IdentifiableInputView extends AbstractViewInputComponent {

  private static final String REGEX = ",";
  private final Label label = new Label();


  /**
   * Constructor
   */
  public IdentifiableInputView() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    String identityProvider = configuration.getPropertyValue(XMLConstants.IDENTITY_PROVIDER);
    if (StringUtils.isNotBlank(identityProvider)) {
      ModelIdentityPresenter provider = (ModelIdentityPresenter) Services.getBean(identityProvider);
      String identity = provider.identityOf((IJSoaggerController) controller, configuration);
      label.setText(identity);
    }

    // static style
    String styleClass = configuration.getPropertyValue(XMLConstants.STYLE_CLASS);
    if (StringUtils.isNotBlank(styleClass)) {
      String[] styles = styleClass.split(REGEX);
      label.getStyleClass().addAll(styles);
    }

    // dynamic styles
    String dynamicStyleClass = configuration.getPropertyValue(XMLConstants.STYLE_PROVIDER);
    if (StringUtils.isNotBlank(dynamicStyleClass)) {
      String[] styles = dynamicStyleClass.split(REGEX);
      label.getStyleClass().addAll(styles);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return null;
  }
}
