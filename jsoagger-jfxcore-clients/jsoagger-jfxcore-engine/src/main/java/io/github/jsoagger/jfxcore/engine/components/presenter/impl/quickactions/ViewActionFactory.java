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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.accessrule.AbstractRuleResolver;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAccessRuleResolver.UIAccessRule;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLAccessRulesResolverXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Generates a list of {@link ViewActionPresenter} from configuration.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public class ViewActionFactory {

  public static Node viewActions(AbstractViewController controller, VLViewComponentXML actionsConfiguration) {
    return viewActions(controller, actionsConfiguration, null);
  }


  public static Node viewActions(AbstractViewController controller, VLViewComponentXML actionsConfiguration, OperationData forModel) {
    return viewActions(controller, actionsConfiguration, forModel, null);
  }

  /**
   * Create a view action for a given cell. We need sometimes data from the cell.
   *
   * @param controller
   * @param actionsConfiguration
   * @param forModel
   * @param forcell
   * @return
   */
  public static Node viewActions(AbstractViewController controller, VLViewComponentXML actionsConfiguration, OperationData forModel, Object forcell) {
    String orientation = actionsConfiguration.getPropertyValue("orientation", "horizontal");

    if (forcell != null) {
      forModel.getMeta().put("sourceNode", forcell);
    }

    if (orientation.equals("horizontal")) {
      return horizontalViewActions(controller, actionsConfiguration, forModel);
    } else {
      return verticalViewActions(controller, actionsConfiguration, forModel);
    }
  }


  public static VBox verticalViewActions(AbstractViewController controller, VLViewComponentXML actionsConfiguration) {
    return verticalViewActions(controller, actionsConfiguration, null);
  }


  public static VBox verticalViewActions(AbstractViewController controller, VLViewComponentXML actionsConfiguration, OperationData forModel) {
    VBox box = new VBox();
    NodeHelper.setStyleClass(box, actionsConfiguration, "styleClass", true);
    List<Node> actionPresenters = process(controller, actionsConfiguration, forModel);
    for(Node n : actionPresenters) {
      box.getChildren().add(n);
    }
    return box;
  }


  public static HBox horizontalViewActions(AbstractViewController controller, VLViewComponentXML actionsConfiguration) {
    return horizontalViewActions(controller, actionsConfiguration, null);
  }


  public static HBox horizontalViewActions(AbstractViewController controller, VLViewComponentXML actionsConfiguration, OperationData forModel) {
    HBox box = new HBox();

    if(!AbstractApplicationRunner.isDesktop()) {
      NodeHelper.setHgrow(box);
      box.widthProperty().addListener(new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          double d = newValue.doubleValue() / box.getChildren().size();
          for(Node node: box.getChildren()) {
            ReflectionUIUtils.setPrefWidth(node, d);
          }
        }});
    }
    box.getStyleClass().add("ep-view-actions-container");
    NodeHelper.setStyleClass(box, actionsConfiguration, "styleClass", true);
    List<Node> actionPresenters = process(controller, actionsConfiguration, forModel);
    for(Node n : actionPresenters) {
      box.getChildren().add(n);
      if(!AbstractApplicationRunner.isDesktop()) {
        NodeHelper.setHgrow(n);
      }
    }
    return box;
  }


  public static List<Node> process(AbstractViewController controller, VLViewComponentXML actionsConfiguration) {
    return process(controller, actionsConfiguration, null);
  }


  public static List<Node> process(AbstractViewController controller, VLViewComponentXML actionsConfiguration, OperationData forModel) {
    OperationData realModel = forModel;
    List<Node> actions = new ArrayList<>();

    if (realModel == null) {
      IOperationResult or = (IOperationResult) controller.getModel();
      if (or != null) {
        realModel = (OperationData) or.rootData();
      }
    }

    if (actionsConfiguration != null && actionsConfiguration.hasSubComponent()) {
      String customFiltersContextSetter = actionsConfiguration.getPropertyValue("customFiltersContextSetter");

      if (StringUtils.isNotBlank(customFiltersContextSetter)) {
        final IContextParamSetter contextParamSetter = (IContextParamSetter) Services.getBean(customFiltersContextSetter);
        contextParamSetter.process(controller, controller.getModel(), controller.viewContext().getCriterias());
      }

      for (VLViewComponentXML actionConfig : actionsConfiguration.getSubcomponents()) {

        String criteria = actionConfig.getCriteria();
        if (StringUtils.isNotBlank(criteria)) {
          boolean crit = controller.evaluateFilter(actionConfig);
          if (!crit) {
            continue;
          }
        }

        if (isAccessible(controller, actionConfig, forModel) == UIAccessRule.SHOW) {

          String type = actionConfig.getPropertyValue("type");
          if ("switchableButton".equals(type)) {

            VLViewComponentXML switchableactions = actionConfig.getComponentById("Actions").orElse(null);
            if (switchableactions != null) {
              List<VLViewComponentXML> comps = ComponentUtils.resolveDefinitions(controller, switchableactions.getSubcomponents());
              StackPane pane = new StackPane();

              ViewActionPresenter p1 = null;
              ViewActionPresenter p2 = null;

              for (VLViewComponentXML comp : comps) {
                String presenterId = comp.getPropertyValue("presenter");
                ViewActionPresenter presenter = (ViewActionPresenter) Services.getBean(presenterId);
                presenter.setForModel(realModel);
                presenter.buildFrom(controller, comp);
                pane.getChildren().add(presenter.getDisplay());
                if (p1 == null) {
                  p1 = presenter;
                } else {
                  p2 = presenter;
                }
              }

              p1.setRelativeTo(p2);
              p2.setRelativeTo(p1);

              actions.add(pane);
            }
          } else {
            String presenterId = actionConfig.getPropertyValue("presenter");
            ViewActionPresenter presenter = (ViewActionPresenter) Services.getBean(presenterId);
            presenter.setForModel(realModel);
            presenter.buildFrom(controller, actionConfig);
            actions.add(presenter.getDisplay());
          }
        }
      }
    }

    return actions;
  }


  private static UIAccessRule isAccessible(AbstractViewController controller, VLViewComponentXML actionsConfiguration, OperationData forModel) {
    if (forModel != null && actionsConfiguration.getAccessRules() != null && actionsConfiguration.getAccessRules().getRuleResolver() != null) {
      VLAccessRulesResolverXML accessRulesResolverXML = actionsConfiguration.getAccessRules().getRuleResolver();
      String name = accessRulesResolverXML.getName();
      if (StringUtils.isNotBlank(name)) {
        AbstractRuleResolver accessRuleResolver = (AbstractRuleResolver) controller.getSpringBean(name);
        accessRuleResolver.put("forModel", forModel);
        return accessRuleResolver.isAccessible(controller, actionsConfiguration);
      }
    }

    return UIAccessRule.SHOW;
  }

}
