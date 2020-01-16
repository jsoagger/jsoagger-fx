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


import java.util.Optional;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.AbstractComponent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.control.Labeled;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractInputComponent extends AbstractComponent {

  //private static final Logger logR = LoggerFactory.getLogger(AbstractInputComponent.class);
  protected Optional<String> prompt = null;


  /**
   * Constructor
   */
  public AbstractInputComponent() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    prompt = configuration.propertyValueOf(XMLConstants.PROMPT);
    if(getDisplay() != null) {
      NodeHelper.styleClassAddAll(getDisplay(), configuration, "styleClass", "jsoagger-control");
    }
  }


  /**
   * Synchronize between the current value to model
   */
  @Override
  public void commitModification() {
    if (owner != null) {
      owner.commitModification();
    }
  }


  @Override
  public void cancelModification() {
    if (owner != null) {
      owner.cancelModification();
    }
  }


  public void setInErrorState() {
    getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
  }


  /**
   * @{inheritedDoc}
   */
  public void setText(String value) {
    if (getComponent() instanceof Labeled) {
      final Labeled labeled = (Labeled) getComponent();
      labeled.setText(value);
    }
  }


  /**
   * @param changeListener
   */
  public void addValueChangeListner(ChangeListener changeListener) {
    if (owner != null) {
      owner.initialInternalValueProperty().addListener(changeListener);
    }
  }

  /**
   * @param changeListener
   */
  public void addCurrentValueChangeListener(ChangeListener changeListener) {
    if (owner != null) {
    	owner.currentInternalValueProperty().addListener(changeListener);
    }
  }


  public void setInValidState() {
    getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
  }


  /**
   * This value will be validated
   *
   * @param vlConstraint
   * @return
   */
  public Object getValueToValidate() {
    // combobox empty value
    if ("__VIDE__".equalsIgnoreCase(owner.currentInternalValueProperty().get())) {
      return null;
    }
    return owner.currentInternalValueProperty().get();
  }
}
