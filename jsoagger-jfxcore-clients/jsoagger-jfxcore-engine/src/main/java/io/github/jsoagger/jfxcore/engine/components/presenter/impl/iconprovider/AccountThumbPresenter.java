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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider;



import java.util.Map;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.concurrent.Task;
import javafx.scene.Node;

/**
 * An account is not thumbed, the user associated to owner of an account is thumbed.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public class AccountThumbPresenter extends ModelThumbPresenter implements ModelIconPresenter {

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    OperationData data = (OperationData) forModel;
    if (forModel == null) {
      final SingleResult sr = (SingleResult) controller.getModel();
      data = sr.getData();
    }

    // the owner should be linked to the account
    final Map owner = (Map) data.getLinks().get("owner");
    final String fullId = (String) owner.get("fullId");
    final Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        if (StringUtils.isEmpty(fullId)) {
          displayEmptyThumb();
        }

        if (getThumbOperation == null) {
          displayEmptyThumb();
        } else {
          displayThumb(fullId);
        }
        return null;
      }
    };

    final Thread t = new Thread(task);
    t.setDaemon(true);
    t.setName("Thumnail_thread__");
    t.start();

    return content;
  }
}
