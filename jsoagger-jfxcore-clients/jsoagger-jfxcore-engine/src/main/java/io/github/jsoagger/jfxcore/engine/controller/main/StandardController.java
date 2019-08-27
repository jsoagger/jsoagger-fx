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

package io.github.jsoagger.jfxcore.engine.controller.main;



import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Controller for view which dont need to declare one. For example, for views that has declared
 * processor and do not have specific task to do.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class StandardController extends StandardViewController {

  /**
   * Default Constructor
   */
  public StandardController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return processedView();
    }

    return super.getNodeOnPosition(position);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void postProcess() {
    super.postProcess();
    if (processedView() == null) {
      processedView(new StackPane());
    }
  }
}
