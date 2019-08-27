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

package io.github.jsoagger.jfxcore.engine.components.listform;



import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ViewForwardLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MultiSelectionListFormItemPresenter extends PreferenceItemPresenterFactory implements IListFormCellPresenter, EventHandler<MouseEvent> {

  private String context;
  private boolean multipleSection;
  private final ToggleGroup group = new ToggleGroup();
  private final ObservableList<IListFormValue> selectedValues = FXCollections.observableArrayList();
  private final HBox currentValuesPresenter;
  private boolean isLoadingChild;

  Pane box;

  /**
   * Constructor
   */
  public MultiSelectionListFormItemPresenter() {
    currentValuesPresenter = new HBox();
    currentValuesPresenter.setSpacing(4);
    currentValuesPresenter.setAlignment(Pos.CENTER);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    final Label icon = new Label();
    IconUtils.setFontIcon("mdi-chevron-right:22", icon);
    icon.getStyleClass().add("item-presenter-goright-indicator");

    if(AbstractApplicationRunner.isDesktop()) {
      box= new HBox();
      box.getStyleClass().add("item-presenter-row-container");
      box.getChildren().add(label);

      final Pane spacer = new Pane();
      HBox.setHgrow(spacer, Priority.ALWAYS);
      box.getChildren().add(spacer);

      box.getChildren().add(currentValuesPresenter);
      loadCurrentValue();

      box.getChildren().add(icon);
      displayValue();
    }
    else {
      box= new HBox();
      box.getStyleClass().add("item-presenter-row-container");
      box.getChildren().add(label);

      final Pane spacer = new Pane();
      HBox.setHgrow(spacer, Priority.ALWAYS);
      box.getChildren().add(spacer);

      box.getChildren().add(currentValuesPresenter);
      loadCurrentValue();

      box.getChildren().add(icon);
      displayValue();
    }

    box.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
    box.getStyleClass().add("hand-hover");
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void handle(MouseEvent event) {
    boolean isMobile = AbstractApplicationRunner.isMobile();
    if(isMobile) {
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ae -> isLoadingChild = false));
      if(!AbstractApplicationRunner.isApplicationScrolling() && !isLoadingChild && event.getClickCount() == 1) {
        isLoadingChild = true;
        timeline.play();

        MIPDoClickTask clickTask = new MIPDoClickTask();
        new Thread(clickTask).start();
      }
    }
    else {
      if (event.getClickCount() == 2) {
        MIPDoClickTask clickTask = new MIPDoClickTask();
        new Thread(clickTask).start();
      }
    }

    if (event.getClickCount() == 1) {
      /* if (isMobile) {
        PushTabContentEvent ev = new PushTabContentEvent();
        ev.setSourceController(controller);

        if (controller.getParent() != null && controller.getParent() instanceof StandardTabPaneController) {
          ev.setParentController((StandardTabPaneController) controller.getParent());
        }

        ev.setProperty("viewId", "SelectPreferenceValueView");
        ev.setModel(model);
        controller.dispatchEvent(ev);
      } else {*/
      // searchin for ternary me,nu layout manager in desktop
      // self diplay mode, because all views are layed out in
      // that manager.
      //}
    }
  }

  private void _doClickOrTouch() {
    OperationData model = getForData();
    List<EnumeratedValueModel> valls = dataLoader.getSelectableValues();
    model.getMeta().put("selectableValue", valls);
    model.getMeta().put("currentValue", currentValues);
    model.getMeta().put("presenter", this);

    if(isSelfPreference(controller)) {
      if(((StandardController)controller).getLayoutManager() instanceof ViewForwardLayoutManager) {
        ViewForwardLayoutManager lm = (ViewForwardLayoutManager) ((StandardController)controller.getParent()).getLayoutManager();
        final StandardViewController view = StandardViewUtils.forId(controller.getRootStructure(), "SelfSelectPreferenceValueView", model);
        view.setParent(controller);
        Platform.runLater(()->{
          lm.pushContent(view.processedView());
        });
      }
    }
    else {
      // see SelectMultiValueController
      PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId("SelectPreferenceValueView").model(model).build();
      controller.dispatchEvent(ev);
    }
  }

  private boolean isSelfPreference(AbstractViewController controller) {
    List<String> views = controller.getViewDefinitions();
    for(String v: views) {
      if(v.contains("Self")) {
        return true;
      }
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void processUpdate(List<IListFormValue> selected) {
    dataLoader.setCurrentValue(selected);
    loadCurrentValue();
    displayValue();
  }

  /**
   * Dispaly current value
   */
  protected void displayValue() {
    currentValuesPresenter.getChildren().clear();
    final Node node = dataLoader.getDisplayNode(currentValues);
    currentValuesPresenter.getChildren().add(node);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return box;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getOwner() {
    return "SYSTEM";
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getKey() {
    if (key == null) {
      key = configuration.getPropertyValue("key");
    }
    return key;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getContext() {
    return context;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setKey(String key) {
    this.key = key;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setContext(String context) {
    this.context = context;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setOnBack(Function<Void, Void> backFunction) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setOnShow(Function<Node, Void> showFunction) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public ObservableList<IListFormValue> getSelectedValues() {
    return selectedValues;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isMultipleSection() {
    if (configuration != null) {
      final String selectionMode = configuration.propertyValueOf("selectionMode").orElse("single");
      multipleSection = "multiple".equalsIgnoreCase(selectionMode);
    }
    return multipleSection;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setMultipleSection(boolean multipleSection) {
    this.multipleSection = multipleSection;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public ToggleGroup getToggleGroup() {
    return group;
  }


  /**
   *
   * @author Ramilafananana VONJISOA
   *
   */
  private class MIPDoClickTask extends Task<Void>{


    public MIPDoClickTask() {
    }

    @Override
    protected Void call() throws Exception {
      _doClickOrTouch();
      return null;
    }

    @Override
    protected void setException(Throwable t) {
      super.setException(t);
      t.printStackTrace();
    }
  }
}
