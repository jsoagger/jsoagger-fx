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

package io.github.jsoagger.jfxcore.engine.client.apiimpl;




import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IIStepListenerHandler;
import io.github.jsoagger.jfxcore.api.IStepListener;

/**
 * Listener of step execution.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class AfterStepListenerHandler implements IIStepListenerHandler {

  //  private static final Logger logR = LoggerFactory.getLogger(AfterStepListenerHandler.class);
  protected List<IStepListener> listeners = new ArrayList<>();


  /**
   * Constructor
   */
  public AfterStepListenerHandler() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest) {
    for(IStepListener s: listeners) {
      s.afterStep(actionRequest);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IStepListener> getListeners() {
    return listeners;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setListeners(List<IStepListener> listeners) {
    this.listeners = listeners;
  }
}
