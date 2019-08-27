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

package io.github.jsoagger.jfxcore.components.welcome;



import io.github.jsoagger.jfxcore.api.INoContentPane;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.message.NoContentPaneHelper;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class WelcomeController extends StandardController { 


  /**
   * Constructor
   */
  public WelcomeController() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();

    VLViewComponentXML root = getRootComponent();
    VLViewComponentXML noContentPane = root.getComponentById("NoContentPane").orElse(null);
    INoContentPane node = NoContentPaneHelper.buildFrom(this, noContentPane);
    processedView(node.getDisplay());
  }
}
