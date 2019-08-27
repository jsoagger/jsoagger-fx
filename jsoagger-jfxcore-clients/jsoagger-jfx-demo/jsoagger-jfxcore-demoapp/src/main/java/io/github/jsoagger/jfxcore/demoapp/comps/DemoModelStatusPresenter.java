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

package io.github.jsoagger.jfxcore.demoapp.comps;



import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.ModelAttributePresenter;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoModelStatusPresenter extends ModelAttributePresenter {

  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    Label label = (Label) super.present(controller, configuration);
    String text = label.getText();

    Platform.runLater(() -> {
      label.setText(text.toUpperCase());
      String pc = StringUtils.replace(text, " ", "_");
      PseudoClass cc = PseudoClass.getPseudoClass(pc.toLowerCase());
      label.pseudoClassStateChanged(cc, true);
    });

    return label;
  }


  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    Label label = (Label) super.present(controller, configuration, forModel);
    String text = label.getText();

    Platform.runLater(() -> {
      label.setText(text.toUpperCase());
      String pc = StringUtils.replace(text, " ", "_");
      PseudoClass cc = PseudoClass.getPseudoClass(pc.toLowerCase());
      label.pseudoClassStateChanged(cc, true);
    });

    return label;
  }
}
