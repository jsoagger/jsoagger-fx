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

package io.github.jsoagger.jfxcore.components.presenter;



import java.util.Map;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Profide full identity of a person
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class AccountFullIdentityPresenter extends AbstractModelPresenter implements ModelIdentityPresenter {

  /**
   * Default Constructor
   */
  public AccountFullIdentityPresenter() {}

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component) {
    final String id = identityOf(controller, component);
    final Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");
    return label;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML component) {
    final SingleResult sr = (SingleResult) controller.getModel();
    return identityOf(controller, component, sr.rootData());
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    OperationData data = (OperationData) forModel;
    if (data == null) {
      final SingleResult sr = (SingleResult) controller.getModel();
      data = (OperationData) sr.rootData();
    }
    // account as principal and owner as link
    final Map owner = (Map) data.getLinks().get("owner");
    final String firstName = (String) owner.get("firstName");
    final String lastName = (String) owner.get("lastName");
    return firstName.toUpperCase() + " " + StringUtils.capitalize(lastName);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    final String id = identityOf(controller, configuration, forModel);
    final Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");
    return label;
  }
}
