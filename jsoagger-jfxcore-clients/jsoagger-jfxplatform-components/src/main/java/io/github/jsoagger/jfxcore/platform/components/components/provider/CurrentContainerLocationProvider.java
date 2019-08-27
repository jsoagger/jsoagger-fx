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

package io.github.jsoagger.jfxcore.platform.components.components.provider;




import java.util.Arrays;
import java.util.Iterator;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ILocationProvider;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CurrentContainerLocationProvider extends AbstractModelPresenter implements ILocationProvider {

  /**
   * Constructor
   */
  public CurrentContainerLocationProvider() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideCurrentLocationOf(IJSoaggerController controller, VLViewComponentXML configuration) {
    String currentContainerPath = controller.getModelContainerPath();
    if (currentContainerPath.equalsIgnoreCase("/")) {
      currentContainerPath = "/Application";
    }

    HBox box = new HBox();
    box.setAlignment(Pos.CENTER_LEFT);
    box.setSpacing(4);

    String[] locations = currentContainerPath.split("\\/");

    Iterator<String> iterator = Arrays.asList(locations).iterator();
    while (iterator.hasNext()) {
      Text l = new Text();
      l.setText(iterator.next());
      l.getStyleClass().add("simple-details-view-location-item");
      box.getChildren().add(l);

      if (iterator.hasNext()) {
        Label separator = new Label();
        IconUtils.setContentLocationSeparator(separator);
        box.getChildren().add(separator);
      }
    }

    return box;
  }
}
