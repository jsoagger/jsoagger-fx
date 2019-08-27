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
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController.DragResizer;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DialogStageWrapper extends BorderPane {

  protected Stage dialogStage = null;
  protected boolean canShow = false;


  /**
   * Constructor
   */
  public DialogStageWrapper() {
    setStyle("-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4); " + "-fx-background-insets: 1; " + "-fx-background-radius: 6;-fx-background-color:transparent;");
    dialogStage = new Stage();
  }


  public void setContent(Node content) {
    if (content != null) {
      canShow = true;
      NodeHelper.setHVGrow(content);
      setCenter(content);
    }
  }

  private void _prepareShow(boolean autohide) {
    if (canShow) {
      dialogStage.initModality(Modality.APPLICATION_MODAL);

      final Scene scene = new Scene(this);
      dialogStage.setScene(scene);
      dialogStage.initOwner(ViewStructure.primaryStage());

      // hide on escape
      scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
        if (e.getCode() == KeyCode.ESCAPE) {
          hide();
        }
      });

      DragResizer.makeResizable(this, dialogStage);

      // Desired x coordinate for centered dialog
      final DoubleProperty x = new SimpleDoubleProperty();
      x.bind(ViewStructure.primaryStage().xProperty().add(ViewStructure.primaryStage().widthProperty().subtract(dialogStage.widthProperty()).divide(2)));

      // Desired x coordinate for centered dialog
      final DoubleProperty y = new SimpleDoubleProperty();
      y.bind(ViewStructure.primaryStage().yProperty().add(ViewStructure.primaryStage().heightProperty().subtract(dialogStage.heightProperty()).divide(3)));

      // Update dialog's x coordinate when x defined above changes
      x.addListener((ChangeListener<Number>) (obs, oldValue, newValue) -> dialogStage.setX(newValue.doubleValue()));

      // Update dialog's y coordinate when y defined above changes
      y.addListener((ChangeListener<Number>) (obs, oldValue, newValue) -> dialogStage.setY(newValue.doubleValue()));


      // Unbind x and y when dialog has been shown, to allow user to
      // resize without interference
      dialogStage.setOnShown(event -> {
        x.unbind();
        y.unbind();

        // handle autohide
        if(autohide) {
          Timeline tm = new Timeline(new KeyFrame(Duration.millis(1500), e -> {
            dialogStage.hide();
          }));
          tm.play();
        }
      });

      // Rebind x and y when dialog is hidden, so re-showing will properly
      // center it again
      dialogStage.setOnHidden(event -> {
        // x.unbind();
        // x.bind(parentWindow.xProperty().add(parentWindow.widthProperty().subtract(dialog.widthProperty()).divide(2)));
        // y.unbind();
        // y.bind(parentWindow.yProperty().add(parentWindow.heightProperty().subtract(dialog.heightProperty()).divide(2)));
      });

      dialogStage.getScene().setFill(Color.TRANSPARENT); // Fill our scene with nothing
      dialogStage.initStyle(StageStyle.TRANSPARENT); // Important one!
    }
  }


  public void showAndWait() {
    if(canShow) {
      _prepareShow(false);
      dialogStage.showAndWait();
      canShow = false;
    }
  }


  public void show() {
    if(canShow) {
      _prepareShow(false);
      dialogStage.show();
      canShow = false;
    }
  }


  public void show(boolean autohide) {
    if(canShow) {
      _prepareShow(autohide);
      dialogStage.getScene().getRoot().opacityProperty().set(0);
      dialogStage.show();

      Timeline timeline = new Timeline();
      KeyFrame key = new KeyFrame(Duration.millis(100),
          new KeyValue (dialogStage.getScene().getRoot().opacityProperty(), 1));
      timeline.getKeyFrames().add(key);
      timeline.play();
      canShow = false;
    }
  }

  public void hide() {
    if(Platform.isFxApplicationThread()) {
      dialogStage.hide();
    }
    else {
      Platform.runLater(()-> dialogStage.hide());
    }
    canShow = true;
  }


  /**
   * Get the dialogStage
   *
   * @return the dialogStage
   */
  public Stage getDialogStage() {
    return dialogStage;
  }
}
