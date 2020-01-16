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

package io.github.jsoagger.jfxcore.engine.components.message;




import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.INoContentPane;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NoContentPaneHelper {

  /**
   * @param controller
   * @param noContentPaneConfig
   * @return
   */
  public static INoContentPane buildFrom(AbstractViewController controller, VLViewComponentXML noContentPaneConfig) {
    INoContentPane noContentPane = null;
    String impl = "NoContentPane";
    if ((noContentPaneConfig != null) && StringUtils.isNotBlank((noContentPaneConfig.getPropertyValue("contentImpl")))) {
      impl = noContentPaneConfig.getPropertyValue("contentImpl");
    }

    noContentPane = (INoContentPane) Services.getBean(impl);
    noContentPane.buildFrom((IJSoaggerController) controller, noContentPaneConfig);
    return noContentPane;
  }
}
