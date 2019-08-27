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

package io.github.jsoagger.jfxcore.engine.components.presenter;



import java.util.Map;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelContextMenuPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelEllipsisMenuPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.contextmenu.EllipsisActionButton;
import io.github.jsoagger.jfxcore.engine.components.list.impl.AbstractListCell;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class CellPresenterFactory implements IBuildable {

  protected AbstractListCell cell;
  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;
  protected OperationData data;
  protected Map<String, Object> parameters;


  /**
   * Getter of cell
   *
   * @return the cell
   */
  public AbstractListCell getCell() {
    return cell;
  }


  /**
   * Setter of cell
   *
   * @param cell the cell to set
   */
  public void setCell(AbstractListCell cell) {
    this.cell = cell;
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;
  }


  public AbstractViewController getController() {
    return controller;
  }


  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }

  /**
   * Getter of iconPresenter
   *
   * @return the iconPresenter
   */
  public ModelIconPresenter getIconPresenter() {
    return null;
  }


  /**
   * Setter of iconPresenter
   *
   * @param iconPresenter the iconPresenter to set
   */
  public void setIconPresenter(ModelIconPresenter iconPresenter) {

  }


  /**
   * Getter of identityPresenter
   *
   * @return the identityPresenter
   */
  public ModelIdentityPresenter getIdentityPresenter() {
    return null;
  }


  /**
   * Setter of identityPresenter
   *
   * @param identityPresenter the identityPresenter to set
   */
  public void setIdentityPresenter(ModelIdentityPresenter identityPresenter) {

  }


  /**
   * Getter of contextMenuPresenter
   *
   * @return the contextMenuPresenter
   */
  public ModelContextMenuPresenter getContextMenuPresenter() {
    return null;
  }


  /**
   * Setter of contextMenuPresenter
   *
   * @param contextMenuPresenter the contextMenuPresenter to set
   */
  public void setContextMenuPresenter(ModelContextMenuPresenter contextMenuPresenter) {

  }


  /**
   * Getter of ellipsisMenuPresenter
   *
   * @return the ellipsisMenuPresenter
   */
  public ModelEllipsisMenuPresenter getEllipsisMenuPresenter() {
    return null;
  }


  /**
   * Setter of ellipsisMenuPresenter
   *
   * @param ellipsisMenuPresenter the ellipsisMenuPresenter to set
   */
  public void setEllipsisMenuPresenter(ModelEllipsisMenuPresenter ellipsisMenuPresenter) {

  }


  /**
   * Getter of secondaryLabelPresenter
   *
   * @return the secondaryLabelPresenter
   */
  public ModelSecondaryLabelPresenter getSecondaryLabelPresenter() {
    return null;
  }


  /**
   * Setter of secondaryLabelPresenter
   *
   * @param secondaryLabelPresenter the secondaryLabelPresenter to set
   */
  public void setSecondaryLabelPresenter(ModelSecondaryLabelPresenter secondaryLabelPresenter) {

  }


  /**
   * Getter of ellipsisMenu
   *
   * @return the ellipsisMenu
   */
  public EllipsisActionButton getEllipsisMenu() {
    return null;
  }


  /**
   * Setter of ellipsisMenu
   *
   * @param ellipsisMenu the ellipsisMenu to set
   */
  public void setEllipsisMenu(EllipsisActionButton ellipsisMenu) {

  }


  /**
   * Getter of mainLabelStyles
   *
   * @return the mainLabelStyles
   */
  public String getMainLabelStyles() {
    return null;
  }


  /**
   * Setter of mainLabelStyles
   *
   * @param mainLabelStyles the mainLabelStyles to set
   */
  public void setMainLabelStyles(String mainLabelStyles) {}


  /**
   * Getter of descriptionLabelStyles
   *
   * @return the descriptionLabelStyles
   */
  public String getDescriptionLabelStyles() {
    return null;
  }


  /**
   * Setter of descriptionLabelStyles
   *
   * @param descriptionLabelStyles the descriptionLabelStyles to set
   */
  public void setDescriptionLabelStyles(String descriptionLabelStyles) {}


  /**
   * Getter of forData
   *
   * @return the forData
   */
  public OperationData getForData() {
    return data;
  }


  /**
   * Setter of forData
   *
   * @param forData the forData to set
   */
  public void setForData(OperationData forData) {
    this.data = forData;
  }


  public Node getIdentityContainer() {
    return null;
  }


  public Node getIconContainer() {
    return null;
  }


  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }
}
