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




import java.util.Arrays;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ItemInitialCircleIconPresenter extends CellPresenterImpl implements ModelIconPresenter {

  List<String> firstCat = Arrays.asList("a", "c", "f", "g", "h", "k", "l", "m", "z", "u");
  List<String> secondCat = Arrays.asList("b", "d", "e", "i", "n");

  private Label label = new Label();
  private ModelIdentityPresenter identityPresenter;


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration) {
    final StackPane graphic = new StackPane();
    graphic.setAlignment(Pos.CENTER);
    graphic.getChildren().addAll(label);

    label.getStyleClass().add("item-initial-circle-icon-label");

    if (getIdentityPresenter() != null) {
      String allidentity = getIdentityPresenter().identityOf(controller, configuration);
      String processInitialFromIdentity = processInitialFromIdentity(allidentity, controller, configuration);
      if (StringUtils.isNotBlank(processInitialFromIdentity)) {
        label.setText(processInitialFromIdentity);
      }

      processColoration(graphic);
    }

    return graphic;
  }


  public void processColoration(StackPane graphic) {
    if (getCaractersToDisplay() == 1) {
      if (firstCat.contains(label.getText().substring(0, 0).toLowerCase())) {
        graphic.getStyleClass().add("item-initial-circle-icon-label-wrapper-primary");
      } else {
        graphic.getStyleClass().add("item-initial-circle-icon-label-wrapper-grey");
      }
    } else if (getCaractersToDisplay() > 1) {
      if (firstCat.contains(label.getText().substring(0, 1).toLowerCase())) {
        graphic.getStyleClass().add("item-initial-circle-icon-label-wrapper-primary");
      } else if (secondCat.contains(label.getText().substring(0, 1).toLowerCase())) {
        graphic.getStyleClass().add("item-initial-circle-icon-label-wrapper-grey");
      }
    } else {
      graphic.getStyleClass().add("item-initial-circle-icon-label-wrapper-accent");
    }

    String graphicSize = getGraphicStyleClass();
    graphic.getStyleClass().add(graphicSize);
  }


  public String processInitialFromIdentity(String identityString, IJSoaggerController controller, VLViewComponentXML configuratin) {
    int carac = getCaractersToDisplay();
    if (getIdentityPresenter() != null) {
      StringBuffer identity = new StringBuffer();
      if (StringUtils.isNotBlank(identityString)) {
        for(String token: identityString.split(" ")) {
          String t = token.substring(0, 1);
          identity.append(t.toUpperCase());
        }
        return identity.toString().substring(0, carac);
      }
    }

    return null;
  }


  public int getCaractersToDisplay() {
    return 2;
  }


  public String getGraphicStyleClass() {
    return "item-initial-circle-icon-label-wrapper-medium";
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    final StackPane graphic = new StackPane();
    graphic.setAlignment(Pos.CENTER);
    graphic.getChildren().addAll(label);

    label.getStyleClass().add("item-initial-circle-icon-label");

    if (getIdentityPresenter() != null) {
      String allidentity = getIdentityPresenter().identityOf(controller, configuration, forModel);
      String processInitialFromIdentity = processInitialFromIdentity(allidentity, controller, configuration);
      if (StringUtils.isNotBlank(processInitialFromIdentity)) {
        label.setText(processInitialFromIdentity);
      }
    } else {
      label.setText("AN0".substring(0, getCaractersToDisplay()));
    }

    processColoration(graphic);
    return graphic;
  }


  /**
   * Getter of identityPresenter
   *
   * @return the identityPresenter
   */
  public ModelIdentityPresenter getIdentityPresenter() {
    return identityPresenter;
  }


  /**
   * Setter of identityPresenter
   *
   * @param identityPresenter the identityPresenter to set
   */
  public void setIdentityPresenter(ModelIdentityPresenter identityPresenter) {
    this.identityPresenter = identityPresenter;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return provideIcon(controller, configuration, forModel);
  }

}
