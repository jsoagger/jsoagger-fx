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


import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.engine.utils.MimeTyPeUtils;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * For displaying mimetype of content holder. Icons come from
 * http://aalmiray.github.io/ikonli/cheat-sheet-hawconsstroke.html
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public class ModelMimeTypePresenter extends CellPresenterImpl implements ModelIconPresenter {

  private static final String NO_THUMB = "hws-document";
  private static final String NO_THUMB_SIZE = "32";

  private final StackPane content = new StackPane();
  private final SingleResult contentInfo = new SingleResult();

  // needs GetContentInfoOperation
  private IOperation getContentInfoOperation;
  private String styleClass;
  private int iconSize;

  public ModelMimeTypePresenter() {
    setStyleClass("ep-model-mime-type-container");
    setIconSize(32);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration) {
    return provideIcon(controller, configuration, null);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    final OperationData data = (OperationData) forModel;
    final String fullId = (String) data.getAttributes().get("fullId");
    content.getStyleClass().setAll(getStyleClass().split(","));

    if (StringUtils.isEmpty(fullId)) {
      displayEmptyThumb();
    } else {
      if (contentInfo.getData() == null) {
        final JsonObject query = new JsonObject();
        query.addProperty("fullId", fullId);
        query.addProperty("role", "primary");
        getContentInfoOperation.doOperation(query, res -> {
          final MultipleResult f = (MultipleResult) res;
          if (f.getData().size() == 1) {
            contentInfo.setData(f.getData().get(0));
          }
        }, ex -> {
          // ex.printStackTrace();
        });
      }

      displayThumb();
    }
    return content;
  }


  private void displayThumb() {
    if (contentInfo.getData() != null) {
      try {
        final String mimetype = (String) contentInfo.getData().getAttributes().get("mimeType");
        final Label label = new Label();
        IconUtils.setFontIcon(MimeTyPeUtils.getFontIcon(mimetype), label);
        Platform.runLater(() -> {
          content.getChildren().add(label);
        });
      } catch (final Exception e) {
        // e.printStackTrace();
        displayEmptyThumb();
      }
    } else {
      displayEmptyThumb();
    }
  }


  private void displayEmptyThumb() {
    final Label label = new Label();
    String thumb = NO_THUMB.concat(":").concat(String.valueOf(getIconSize()));
    IconUtils.setFontIcon(thumb, label);

    Platform.runLater(() -> {
      content.getChildren().add(label);
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return provideIcon(controller, configuration, forModel);
  }


  /**
   * @return the getContentInfoOperation
   */
  public IOperation getGetContentInfoOperation() {
    return getContentInfoOperation;
  }


  /**
   * @param getContentInfoOperation the getContentInfoOperation to set
   */
  public void setGetContentInfoOperation(IOperation getContentInfoOperation) {
    this.getContentInfoOperation = getContentInfoOperation;
  }

  /**
   * @return the styleClass
   */
  public String getStyleClass() {
    return styleClass;
  }

  /**
   * @param styleClass the styleClass to set
   */
  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }

  /**
   * @return the iconSize
   */
  public int getIconSize() {
    return iconSize;
  }

  /**
   * @param iconSize the iconSize to set
   */
  public void setIconSize(int iconSize) {
    this.iconSize = iconSize;
  }
}
