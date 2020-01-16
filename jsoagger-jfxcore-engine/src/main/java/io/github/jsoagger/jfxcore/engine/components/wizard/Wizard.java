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


import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.wizard.IWizard;
import io.github.jsoagger.jfxcore.api.wizard.IWizardContent;
import io.github.jsoagger.jfxcore.api.wizard.IWizardFooter;
import io.github.jsoagger.jfxcore.api.wizard.IWizardHeader;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Default Implementation of wizard.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class Wizard extends AnchorPane implements IWizard {

  //private static final Logger logR = LoggerFactory.getLogger(Wizard.class);

  private static final String FOOTER_IMPL = "footerImpl";
  private static final String CONTENT_IMPL = "contentImpl";
  private static final String HEADER_IMPL = "headerImpl";
  private static final String STEPS_RELATIVE_PATH = "WizardSteps";

  private IWizardHeader header;
  private IWizardContent content;
  private IWizardFooter footer;

  protected Double dialogHeight;
  protected Double dialogWidth;

  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;


  /**
   * Constructor
   */
  public Wizard() {

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

    NodeHelper.styleClassSetAll(this, configuration, "styleClass", "wizard");

    loadSetting();
    buildHeader();
    buildContent();
    buildFooter();

    layoutContent();
  }


  private void layoutContent() {
    //    center.getStyleClass().add("wizard-center-content-wrapper");
    boolean displayHeader = configuration.getBooleanProperty("displayHeader", true);
    Node centerContent = ((IBuildable) content).getDisplay();
    getChildren().add(centerContent);
    if (footer != null && !footer.getDisplay().isVisible()) {
      AnchorPane.setBottomAnchor(centerContent, 60.0);
      AnchorPane.setLeftAnchor(centerContent, 0.0);
      AnchorPane.setRightAnchor(centerContent, 0.0);
      AnchorPane.setTopAnchor(centerContent, displayHeader ? 60. : 0);
    }
    else {
      AnchorPane.setBottomAnchor(centerContent, 0.0);
      AnchorPane.setLeftAnchor(centerContent, 0.0);
      AnchorPane.setRightAnchor(centerContent, 0.0);
      AnchorPane.setTopAnchor(centerContent, displayHeader ? 60. : 0);
    }
  }


  /**
   * Builds the footer of the {@link Wizard}.
   */
  private void buildFooter() {
    String footerImpl = configuration.getPropertyValue(FOOTER_IMPL);
    if (StringUtils.isNotEmpty(footerImpl)) {
      footer = (IWizardFooter) Services.getBean(footerImpl);
      footer.buildFrom(controller, configuration);

      Node node = footer.getDisplay();
      getChildren().add(node);
      AnchorPane.setBottomAnchor(node, 0.0);
      AnchorPane.setLeftAnchor(node, 0.0);
      AnchorPane.setRightAnchor(node, 0.0);
    }
  }


  /**
   * Builds the listViewPaneHeader of the {@link Wizard}.
   */
  protected void buildHeader() {
    String headerImpl = configuration.getPropertyValue(HEADER_IMPL, "WizardHeader");
    boolean displayHeader = configuration.getBooleanProperty("displayHeader", true);
    if (displayHeader) {
      header = (IWizardHeader) Services.getBean(headerImpl);
      header.buildFrom(controller, configuration);

      Node node = header.getDisplay();
      getChildren().add(node);
      AnchorPane.setTopAnchor(node, 0.0);
      AnchorPane.setLeftAnchor(node, 0.0);
      AnchorPane.setRightAnchor(node, 0.0);
    }
  }


  /**
   * Builds the content of the wizard
   */
  protected void buildContent() {
    String contentImpl = configuration.getPropertyValue(CONTENT_IMPL);
    if (StringUtils.isBlank(contentImpl)) {
      contentImpl = "WizardContent";
    }

    content = (IWizardContent) Services.getBean(contentImpl);
    ((IBuildable) content).buildFrom(controller, configuration.getComponentById(STEPS_RELATIVE_PATH).orElse(null));
  }


  /**
   * Load wizard settings.
   */
  protected void loadSetting() {
    final String width = configuration.getPropertyValue(XMLConstants.DIALOG_WIDTH);
    if (!StringUtils.isEmpty(width)) {
      dialogWidth = Double.valueOf(width);
    }

    final String height = configuration.getPropertyValue(XMLConstants.DIALOG_HEIGHT);
    if (!StringUtils.isEmpty(height)) {
      dialogHeight = Double.valueOf(height);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IWizardHeader getHeader() {
    return header;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setHeader(IWizardHeader header) {
    this.header = header;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IWizardContent getContent() {
    return content;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setContent(IWizardContent content) {
    this.content = content;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IWizardFooter getFooter() {
    return footer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setFooter(IWizardFooter footer) {
    this.footer = footer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int getStepsSize() {
    return content.getStepsSize();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void select(int index) {
    content.select(index);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IWizardStep getStep(int curStepIdx) {
    return content.getStep(curStepIdx);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setValid(int curStepIdx) {
    content.setValid(curStepIdx);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setError(int curStepIdx) {
    content.setError(curStepIdx);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleValidationResult(IActionRequest actionRequest, IUIDataValidationResult result) {
    // forward to wizard content
    content.handleValidationResult(actionRequest, result);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleValidationResult(IActionRequest actionRequest, IActionResult actionResult) {
    // forward to wizard content
    content.handleValidationResult(actionRequest, actionResult);
  }
}
