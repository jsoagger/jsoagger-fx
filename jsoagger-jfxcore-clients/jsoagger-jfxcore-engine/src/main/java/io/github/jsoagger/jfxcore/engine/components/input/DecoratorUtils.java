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

package io.github.jsoagger.jfxcore.engine.components.input;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DecoratorUtils {

  public static void decorateError(Node node) {
    Node redRect = rectDecoration(Color.RED);
    Platform.runLater(() -> {
      //Decorator.removeAllDecorations(node);
      //Decorator.addDecoration(node, new GraphicDecoration(redRect, Pos.TOP_LEFT));
    });

  }


  public static Node rectDecoration(Color color) {
    Rectangle d = new Rectangle(7, 7);
    d.setFill(color);
    return d;
  }


  public static Node errorDecoration() {
    Label label = new Label();
    // GlyphsDude.setIcon(label, FontAwesomeIcon.TIMES, "12");
    label.getStyleClass().add("red-icon");
    return label;
  }


  public static Node warningDecoration(Color color) {
    Rectangle d = new Rectangle(7, 7);
    d.setFill(color);
    return d;
  }
}
