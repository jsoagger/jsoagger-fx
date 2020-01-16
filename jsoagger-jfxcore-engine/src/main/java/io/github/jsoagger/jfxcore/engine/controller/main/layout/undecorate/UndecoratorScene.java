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

package io.github.jsoagger.jfxcore.engine.controller.main.layout.undecorate;




import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Custom {@link Scene} in order to hide the window bar and add custom controls.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class UndecoratorScene extends Scene {

  private Undecorator undecorator;


  /**
   * Basic constructor with built-in behavior
   *
   * @param stage The main stage
   * @param root your UI to be displayed in the Stage
   */
  public UndecoratorScene(Stage stage, Region root) {

    super(root);

    undecorator = new Undecorator();
    undecorator.buildFrom(stage, root);
    super.setRoot(undecorator);

    // Transparent scene and stage
    stage.initStyle(StageStyle.TRANSPARENT);
    super.setFill(Color.TRANSPARENT);

    // Default Accelerators
    undecorator.installAccelerators(this);
  }


  public void setAsStageDraggable(Stage stage, Node node) {
    undecorator.setAsStageDraggable(stage, node);
  }


  public void setBackgroundOpacity(double opacity) {
    undecorator.getBackgroundNode().setOpacity(opacity);
  }


  public void setBackgroundPaint(Paint paint) {
    undecorator.removeDefaultBackgroundStyleClass();
    undecorator.getBackgroundNode().setFill(paint);
  }


  public Undecorator getUndecorator() {
    return undecorator;
  }


  public void setFadeInTransition() {
    undecorator.setFadeInTransition();
  }


  public void setFadeOutTransition() {
    undecorator.setFadeOutTransition();
  }
}
