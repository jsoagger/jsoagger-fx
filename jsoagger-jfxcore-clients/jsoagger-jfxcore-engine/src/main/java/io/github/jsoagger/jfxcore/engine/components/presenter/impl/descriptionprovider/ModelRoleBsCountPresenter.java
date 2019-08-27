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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.descriptionprovider;



import java.util.function.Consumer;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 28 janv. 2018
 */
public class ModelRoleBsCountPresenter extends CellPresenterImpl implements ModelSecondaryLabelPresenter {

  final static String NO_ITEMS = "NO_ELEMENTS_LABEL_CORE_MSG";
  final static String WITH_ITEMS = "WITH_ELEMENTS_LABEL_CORE_MSG";

  IOperation countRoleBsOperation;
  AbstractViewController controller;
  Label label = new Label();
  String roleB;


  /**
   * Default Constructor
   */
  public ModelRoleBsCountPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideLabel(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    IOperationResult operationResult = (IOperationResult) controller.getModel();
    OperationData operationData = (OperationData) operationResult.rootData();

    roleB = getExtraParameters().get("roleB");
    if (StringUtils.isNotBlank(roleB)) {
      CountRoleBsTask bsTask = new CountRoleBsTask(operationData, this::onSuccess, this::onError);
      new Thread(bsTask).start();
    }

    if (StringUtils.isNotBlank(getDescriptionLabelStyles())) {
      label.getStyleClass().addAll(getDescriptionLabelStyles().split(","));
    }

    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideLabel(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    this.controller = (AbstractViewController) controller;
    OperationData operationData = (OperationData) forModel;

    roleB = getExtraParameters().get("roleB");
    if (StringUtils.isNotBlank(roleB)) {
      CountRoleBsTask bsTask = new CountRoleBsTask(operationData, this::onSuccess, this::onError);
      new Thread(bsTask).start();
    }

    if (StringUtils.isNotBlank(getDescriptionLabelStyles())) {
      label.getStyleClass().addAll(getDescriptionLabelStyles().split(","));
    }

    return label;
  }

  protected class CountRoleBsTask extends Task<Void> {

    OperationData operationData;
    Consumer<IOperationResult> or;
    Consumer<Throwable> e;


    public CountRoleBsTask(OperationData operationData, Consumer<IOperationResult> or, Consumer<Throwable> e) {
      super();
      this.operationData = operationData;
      this.or = or;
      this.e = e;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Void call() {
      try {
        label.setGraphic(new ProgressIndicator());

        if (countRoleBsOperation == null) {
          countRoleBsOperation = (IOperation) Services.getBean("CountObjectLinkRoleBsOperation");
        }

        JsonObject query = new JsonObject();
        String fullId = (String) operationData.getAttributes().get("fullId");
        query.addProperty("oid", fullId);
        query.addProperty("linkClass", roleB);

        countRoleBsOperation.doOperation(query, or, e);
      } catch (Exception e) {
        e.printStackTrace();
        Node errorIcon = IconUtils.getErrorIcon();
        label.setGraphic(errorIcon);
      } finally {
      }

      return null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void failed() {
      super.failed();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void succeeded() {
      super.succeeded();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void running() {
      super.running();
    }
  };


  protected void onSuccess(IOperationResult operationResult) {
    long count = (int) operationResult.getMetaData().get("totalElements");
    if (count > 0) {
      String key = getExtraParameters().get("withRoleBLabel") == null ? WITH_ITEMS : (getExtraParameters().get("withRoleBLabel"));
      label.setText(controller.getLocalised(key, count));
      label.setGraphic(null);
    } else {
      String key = getExtraParameters().get("noRoleBLabel") == null ? NO_ITEMS : (getExtraParameters().get("noRoleBLabel"));
      label.setText(controller.getLocalised(key));
      label.setGraphic(null);
    }
  }


  protected void onError(Throwable ex) {
    label.setGraphic(null);
  }


  /**
   * Getter of countRoleBsOperation
   *
   * @return the countRoleBsOperation
   */
  public IOperation getCountRoleBsOperation() {
    return countRoleBsOperation;
  }


  /**
   * Setter of countRoleBsOperation
   *
   * @param countRoleBsOperation the countRoleBsOperation to set
   */
  public void setCountRoleBsOperation(IOperation countRoleBsOperation) {
    this.countRoleBsOperation = countRoleBsOperation;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Getter of label
   *
   * @return the label
   */
  public Label getLabel() {
    return label;
  }


  /**
   * Setter of label
   *
   * @param label the label to set
   */
  public void setLabel(Label label) {
    this.label = label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return present(controller, configuration, forModel);
  }
}
