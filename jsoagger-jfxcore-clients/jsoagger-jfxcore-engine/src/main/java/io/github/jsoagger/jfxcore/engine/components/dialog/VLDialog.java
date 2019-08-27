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

package io.github.jsoagger.jfxcore.engine.components.dialog;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class VLDialog extends BorderPane implements IBuildable {

  protected IDialogHeader dialogHeader;
  protected IDialogContent dialogContent;
  protected IDialogFooter dialogFooter;
  protected DialogStageWrapper dialogStageWrapper;
  protected boolean hideFooter = false;
  protected StackPane pane = new StackPane();


  /**
   * Constructor
   */
  public VLDialog() {
    pane.getChildren().add(this);
    getStyleClass().add("ep-dialog");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    String dialogHeaderImpl = configuration.getPropertyValue("headerImpl", "DialogHeader");
    dialogHeader = (IDialogHeader) Services.getBean(dialogHeaderImpl);
    dialogHeader.buildFrom(controller, configuration);
    setTop(dialogHeader.getDisplay());

    String dialogContentImpl = configuration.getPropertyValue("contentImpl", "DialogContent");
    dialogContent = (IDialogContent) Services.getBean(dialogContentImpl);
    dialogContent.buildFrom(controller, configuration);
    NodeHelper.setHVGrow(dialogContent.getDisplay());
    setCenter(dialogContent.getDisplay());

    String dialogFooterImpl = configuration.getPropertyValue("footerImpl", "DialogFooter");
    dialogFooter = (IDialogFooter) Services.getBean(dialogFooterImpl);
    dialogFooter.buildFrom(controller, configuration);
    setBottom(dialogFooter.getDisplay());
  }

  public void hideFooter() {
    hideFooter = true;
    dialogFooter.getDisplay().setManaged(false);
  }

  public void show(boolean autoHide) {
    Platform.runLater(() -> {
      _beforeShow();
      Stage dialogStage = dialogStageWrapper.getDialogStage();
      dialogStage.setMinWidth(340);
      dialogStage.setMaxWidth(340);

      if(hideFooter) {
        dialogStage.setMinHeight(164);
        dialogStage.setMaxHeight(164);
      }
      else {
        dialogStage.setMinHeight(236);
        dialogStage.setMaxHeight(236);
      }


      if(autoHide) {
        dialogStageWrapper.show(autoHide);
      }
      else {
        dialogStageWrapper.show();
      }
    });
  }

  public void hide() {
    dialogStageWrapper.hide();
  }


  public void show() {
    show(false);
  }

  public void showAndWait() {
    dialogStageWrapper = _beforeShow();
    Platform.runLater(() -> {
      dialogStageWrapper.showAndWait();
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return pane;
  }

  protected abstract DialogStageWrapper _beforeShow();
}
