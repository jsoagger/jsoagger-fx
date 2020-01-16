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




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepContent;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepFooter;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepHeader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;

/**
 * Displays a step in a wizard. A step can haven different status: erro, valid, working.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardStep extends AnchorPane implements IWizardStep {

  //private static final Logger logR = LoggerFactory.getLogger(WizardStep.class);

  private static final String WIZARD_STEP_CONTENT = "WizardStepContent";
  private static final String WIZARD_STEP_FOOTER = "WizardStepFooter";
  private static final String WIZARD_FORM_STEP = "wizard-step";
  private static final String FOOTER_IMPL = "footerImpl";
  private static final String HEADER_IMPL = "headerImpl";
  private static final String CONTENT_IMPL = "contentImpl";

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  protected VLViewComponentXML configuration;
  protected AbstractViewController controller;

  protected IWizardStepContent stepContent;
  protected IWizardStepHeader stepHeader;
  protected IWizardStepFooter stepFooter;

  protected int index;
  protected String title;
  protected String description;

  protected SimpleBooleanProperty valid = new SimpleBooleanProperty(true);


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    NodeHelper.setStyleClass(this, configuration, "stepStyleClass", true);

    buildTitle();
    buildHeader();
    buildContent();
    buildFooter();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void validate() {
    stepContent.validate();
    valid.set(stepContent.isValid());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isValid() {
    return stepContent.isValid();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayErrors() {
    stepContent.displayErrors();
    stepHeader.displayErrors(isValid());
  }


  /**
   * Load wizard settings.
   */
  protected void buildTitle() {
    String titleLabel = configuration.getPropertyValue(XMLConstants.TITLE);
    if (StringUtils.isNotBlank(titleLabel)) {
      title = (controller.getLocalised(titleLabel));
    }

    String descriptionLabel = configuration.getPropertyValue(XMLConstants.DESCRIPTION);
    if (StringUtils.isNotBlank(descriptionLabel)) {
      description = (controller.getLocalised(titleLabel));
    }
  }


  /**
   * Builds the footer
   */
  private void buildFooter() {
    String footerImpl = configuration.getPropertyValue(FOOTER_IMPL);
    if (StringUtils.isEmpty(footerImpl)) {
      footerImpl = WIZARD_STEP_FOOTER;
    }

    stepFooter = (IWizardStepFooter) Services.getBean(footerImpl);
    stepFooter.buildFrom(controller, configuration);
    Node bottom = stepFooter.getDisplay();
    getChildren().add(bottom);
    AnchorPane.setBottomAnchor(bottom, 0.);
    AnchorPane.setLeftAnchor(bottom, 0.);
    AnchorPane.setRightAnchor(bottom, 0.);
  }


  /**
   * Builds the footer
   */
  private void buildHeader() {
    String headerImpl = configuration.getPropertyValue(HEADER_IMPL);
    boolean displayHeader = configuration.booleanPropertyValueOf("displayHeader").orElse(true);
    if (StringUtils.isEmpty(headerImpl)) {
      headerImpl = "WizardStepHeader";
    }

    stepHeader = (IWizardStepHeader) Services.getBean(headerImpl);
    stepHeader.buildFrom(controller, configuration);
    if (displayHeader) {
      Node header = stepHeader.getDisplay();
      getChildren().add(header);
      AnchorPane.setLeftAnchor(header, 0.);
      AnchorPane.setRightAnchor(header, 0.);
      AnchorPane.setTopAnchor(header, 0.);
    }
  }


  /**
   * Builds the content
   */
  private void buildContent() {
    String contentImpl = configuration.getPropertyValue(CONTENT_IMPL);
    if (StringUtils.isEmpty(contentImpl)) {
      contentImpl = WIZARD_STEP_CONTENT;
    }

    stepContent = (IWizardStepContent) Services.getBean(contentImpl);
    stepContent.buildFrom(controller, configuration);

    Node setCenter = stepContent.getDisplay();
    ScrollPane pane  = new ScrollPane();
    pane.setContent(setCenter);
    pane.setFitToHeight(true);
    pane.setFitToWidth(true);

    pane.setVbarPolicy(ScrollBarPolicy.NEVER);
    pane.setHbarPolicy(ScrollBarPolicy.NEVER);

    getChildren().add(pane);
    AnchorPane.setBottomAnchor(pane, 100.);
    AnchorPane.setLeftAnchor(pane, 0.);
    AnchorPane.setRightAnchor(pane, 0.);

    boolean displayHeader = configuration.booleanPropertyValueOf("displayHeader").orElse(true);
    if(displayHeader) {
      AnchorPane.setTopAnchor(pane, 120.);
    }
    else {
      AnchorPane.setTopAnchor(pane, 0.);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void select() {
    stepContent.select();
  }


  /**
   * Getter of wizardConfiguration
   *
   * @return the wizardConfiguration
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * Setter of wizardConfiguration
   *
   * @param wizardConfiguration the wizardConfiguration to set
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  @Override
  public IJSoaggerController getController() {
    return controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  @Override
  public void setController(IJSoaggerController controller) {
    this.controller = (AbstractViewController) controller;
  }


  /**
   * Getter of stepContent
   *
   * @return the stepContent
   */
  @Override
  public IWizardStepContent getStepContent() {
    return stepContent;
  }


  /**
   * Setter of stepContent
   *
   * @param stepContent the stepContent to set
   */
  public void setStepContent(IWizardStepContent stepContent) {
    this.stepContent = stepContent;
  }


  /**
   * Getter of stepHeader
   *
   * @return the stepHeader
   */
  @Override
  public IWizardStepHeader getStepHeader() {
    return stepHeader;
  }


  /**
   * Setter of stepHeader
   *
   * @param stepHeader the stepHeader to set
   */
  public void setStepHeader(IWizardStepHeader stepHeader) {
    this.stepHeader = stepHeader;
  }


  /**
   * Getter of stepFooter
   *
   * @return the stepFooter
   */
  @Override
  public IWizardStepFooter getStepFooter() {
    return stepFooter;
  }


  /**
   * Setter of stepFooter
   *
   * @param stepFooter the stepFooter to set
   */
  public void setStepFooter(IWizardStepFooter stepFooter) {
    this.stepFooter = stepFooter;
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
  public void setIndex(int stepIndex) {
    this.index = stepIndex;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int getIndex() {
    return index;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   *
   * @return
   */
  @Override
  public SimpleBooleanProperty validProperty() {
    return valid;
  }
}
