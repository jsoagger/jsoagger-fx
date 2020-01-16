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

package io.github.jsoagger.jfxcore.engine.components.tab.styled2;




import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InternalTabPane extends VBox {

  private final HBox headerTabsContainer = new HBox();
  private final StackPane contentContainer = new StackPane();
  private final Map<InternalTab, Node> allContents = new HashMap<>();


  public InternalTabPane(boolean internal) {
    setSpacing(5);
    setId("InternalTabPane-primary");

    HBox.setHgrow(headerTabsContainer, Priority.ALWAYS);

    headerTabsContainer.setSpacing(0);
    headerTabsContainer.setAlignment(Pos.CENTER);
    headerTabsContainer.setStyle("-fx-border-width: 0; " + "-fx-padding: 0 0 0 0;" + "-fx-border-color: #607D8B;" + "-fx-border-width: 0.5;" + "-fx-border-radius: 3;" + "-fx-background-radius: 3;"
        + "-fx-background-color: white;" + "-fx-min-height: 50;" + "-fx-min-width: 120;");

    getChildren().add(headerTabsContainer);
    getChildren().add(contentContainer);

    HBox.setHgrow(contentContainer, Priority.ALWAYS);
    VBox.setVgrow(contentContainer, Priority.ALWAYS);
    HBox.setHgrow(this, Priority.ALWAYS);
    VBox.setVgrow(this, Priority.ALWAYS);
    if (!internal) {
      contentContainer.setStyle("-fx-background-color: transparent;" + "-fx-border-color: #607D8B;" + "-fx-border-width: 0;" + "-fx-border-radius: 0 0 4 4;" + "-fx-background-radius: 0 0 4 4;"
          + "-fx-effect: innershadow(two-pass-box, rgba(154, 133, 121, 0.5), 8, 0.1, 1, 1);");
    } else {
      contentContainer.setStyle("-fx-background-color: transparent;" + "-fx-border-color: #607D8B;" + "-fx-border-width: 0;" + "-fx-border-radius: 0 0 4 4;" + "-fx-background-radius:0 0 4 4;");
    }
  }


  public void addTab(final InternalTab... tabs) {

    for (final InternalTab tab : tabs) {
      headerTabsContainer.getChildren().add(tab.getTitle());
      allContents.put(tab, tab.getContent());
      tab.setOnMouseClicked(arg0 -> select(tab));
    }
  }


  public void select(InternalTab tab) {
    final Node content = allContents.get(tab);
    contentContainer.getChildren().clear();

    for (final Node internalTab : headerTabsContainer.getChildren()) {
      if (InternalTab.class.isInstance(internalTab)) {
        ((InternalTab) internalTab).select(internalTab.equals(tab));
      }
    }

    contentContainer.getChildren().add(content);
  }


  public void select(int index) {
    select(allContents.keySet().iterator().next());
  }
}
