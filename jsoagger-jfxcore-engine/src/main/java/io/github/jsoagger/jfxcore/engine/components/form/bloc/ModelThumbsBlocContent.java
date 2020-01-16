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

package io.github.jsoagger.jfxcore.engine.components.form.bloc;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.ModelSoftTypeIconPresenter;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ModelThumbsBlocContent extends StackPane implements IFormBlocContent {

  /**
   * Constructor
   */
  public ModelThumbsBlocContent() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void build(VLViewComponentXML blocConfig, IJSoaggerController controller) {
    final String styleClass = blocConfig.getPropertyValue("contentStyleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      getStyleClass().clear();
      getStyleClass().addAll(styleClass.split(","));
    }

    String provider = blocConfig.getPropertyValue("provider");
    if (StringUtils.isNotBlank(provider)) {
      ModelSoftTypeIconPresenter p = (ModelSoftTypeIconPresenter) Services.getBean(provider);
      Node n = p.provideIcon(controller, blocConfig);
      getChildren().add(n);
    } else {
      // placeholder-image.png
      Image img = new Image("/images/placeholder-image.png", 500, 500, true, true);
      ImageView imgView = new ImageView(img);
      getChildren().add(imgView);
      setAlignment(Pos.CENTER);
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<IFormFieldsetRow> getRows() {
    return new ArrayList();
  }
}
