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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.layout;



import java.net.URL;

import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Table structure where the top and bottom are no fixed.
 * They all scroll together.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 11 févr. 2018
 */
public class NoFixedPaneTableStructureContentLayoutManager extends FullTableStructureContentLayoutManager implements IViewLayoutManager {

	@FXML
	protected VBox defaultActionsWrapper;

  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return FullTableStructureContentLayoutManager.class.getResource("NoFixedPaneTableStructureContent.fxml");
  }
}
