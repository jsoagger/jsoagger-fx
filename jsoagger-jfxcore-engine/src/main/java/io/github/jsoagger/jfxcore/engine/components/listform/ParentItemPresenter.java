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

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * Is simple label, when clicked load children.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ParentItemPresenter extends PreferenceItemPresenterFactory implements IListFormCellPresenter, EventHandler<MouseEvent> {

  protected Label action = new Label();
  protected Label description = new Label();
  protected HBox graphic = new HBox();
  protected HBox value = new HBox();
  protected boolean builded = false;
  protected boolean isLoadingChild = false;


  /**
   * Constructor
   */
  public ParentItemPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    if (!builded) {
      super.buildFrom(controller, configuration);
      IconUtils.setFontIcon("mdi-chevron-right:22", action);
      graphic.getChildren().addAll(label, NodeHelper.horizontalSpacer(), value, action);
      graphic.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
      graphic.getStyleClass().addAll("hand-hover", "ep-parent-item-presenter");
      builded = true;

      graphic.setAlignment(Pos.CENTER_LEFT);
      value.setAlignment(Pos.CENTER_LEFT);

      value.getChildren().add(getCurrentValueDisplay());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return graphic;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handle(MouseEvent event) {
    // avoid multiple touch loading same view multiple times!!
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ae -> isLoadingChild = false));

    if(AbstractApplicationRunner.isMobile()) {
      if(!AbstractApplicationRunner.isApplicationScrolling() && !isLoadingChild && event.getClickCount() == 1) {
        isLoadingChild = true;
        timeline.play();

        PIPDoClickTask clickTask = new PIPDoClickTask(event);
        new Thread(clickTask).start();
      }
    }
    else {
      if (event.getClickCount() == 2) {
        PIPDoClickTask clickTask = new PIPDoClickTask(event);
        new Thread(clickTask).start();
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void _doClickOrTouch(MouseEvent event) {
    List<VLViewComponentXML> sub = (List<VLViewComponentXML>) getForData().getMeta().get("subComponents");
    boolean hassub = false;
    if (sub != null) {

      for (VLViewComponentXML s : sub) {
        if (s.getId().equals("Handler")) {
          hassub = true;
          getForData().getMeta().put("parentItem", this);
          ComponentToButtonBaseHelper.setButtonActions(controller, s, null, event, getForData());
        }
      }
    }

    if (!hassub) {
      // forward to empty page
      PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId("DemoEmptyPrefenceView").model(getForData()).build();
      controller.dispatchEvent(ev);
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void setContext(String context) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getContext() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getOwner() {
    return null;
  }


  @Override
  public void processUpdate(List<IListFormValue> selected) {}


  public void updateDisplay() {
    loadCurrentValue();
    value.getChildren().clear();
    value.getChildren().add(getCurrentValueDisplay());
  }


  /**
   *
   * @author Ramilafananana VONJISOA
   *
   */
  private class PIPDoClickTask extends Task<Void>{

    MouseEvent e;

    public PIPDoClickTask(MouseEvent e) {
      this.e = e;
    }

    @Override
    protected Void call() throws Exception {
      _doClickOrTouch(e);
      return null;
    }

    @Override
    protected void setException(Throwable t) {
      super.setException(t);
      t.printStackTrace();
    }
  }
}
