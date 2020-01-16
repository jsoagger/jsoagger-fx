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




import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.wizard.IWizardHeader;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardHeader extends StackPane implements IWizardHeader {

  //private static final Logger logR = LoggerFactory.getLogger(WizardHeader.class);

  private static final String TITLE2 = "title";
  private static final String WIZARD_HEADER_LABEL = "wizard-title";
  private static final String WIZARD_HEADER = "wizard-header";

  protected Label title = new Label();
  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;
  private HBox layout = new HBox();


  /**
   * Constructor
   */
  public WizardHeader() {
    layout.setAlignment(Pos.CENTER);
    getChildren().add(layout);
  }


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
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;
    NodeHelper.styleClassAddAll(this, configuration, "headerStyleClass", WIZARD_HEADER);
    buildTitle();
    buildCloseButton();
  }


  protected void buildCloseButton() {
    boolean showAsDialog = Boolean.valueOf(configuration.getPropertyValue(XMLConstants.DIALOG));
    if(showAsDialog) {
      Hyperlink link = new Hyperlink();
      link.getStyleClass().add("transparent-focus");
      FontIcon icon = new FontIcon("mdi-close:32");
      link.setGraphic(icon);
      icon.getStyleClass().add("wizard-close-icon");

      layout.getChildren().addAll(NodeHelper.horizontalSpacer(), link);
      if (controller instanceof WizardViewController) {
        link.setOnAction(e -> ((WizardViewController) controller).hide());
      }
    }
    else {
      layout.getChildren().addAll(NodeHelper.horizontalSpacer());
    }
  }


  protected void buildTitle() {
    String title = configuration.getPropertyValue(TITLE2);
    if (StringUtils.isNotBlank(title)) {
      this.title.setText(controller.getLocalised(title));
      NodeHelper.styleClassSetAll(this.title, configuration, "titleStyleClass", "WIZARD_HEADER_LABEL");
      layout.getChildren().add(this.title);
    }
  }
}
