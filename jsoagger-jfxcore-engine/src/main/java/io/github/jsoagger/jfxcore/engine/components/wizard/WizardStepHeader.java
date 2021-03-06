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

package io.github.jsoagger.jfxcore.engine.components.wizard;



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepHeader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardStepHeader extends StackPane implements IWizardStepHeader {

  //private static final Logger logR = LoggerFactory.getLogger(WizardStepHeader.class);
  private Label title = new Label();


  /**
   * Constructor
   */
  public WizardStepHeader() {
    getStyleClass().add("wizard-step-listViewPaneHeader");
    title.getStyleClass().addAll("wizard-step-listViewPaneHeader-title", "h5");
    title.setWrapText(true);
    getChildren().add(title);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayErrors(boolean valid) {}


  /**
   * Getter of title
   *
   * @return the title
   */
  @Override
  public Label getTitle() {
    return title;
  }


  /**
   * Setter of title
   *
   * @param title the title to set
   */
  public void setTitle(Label title) {
    this.title = title;
  }
}
