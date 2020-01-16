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



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Icon associadte to the type of the businness data
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public class DemoModelThumbPresenter extends CellPresenterImpl implements ModelIconPresenter {

  private static String DATASET_IMAGE_LOCATION = "io.github.jsoagger.demoapp.dataset.image.location";
  protected static final String NO_THUMB = "/images/placeholder-image.png";
  private final static Map<String, Node> CACHE = new HashMap<>();

  protected StackPane content = new StackPane();
  Properties platformProperties;// needs platformProperties
  IOperation getPreferenceValueOperation;// needs GetPreferenceValueOperation

  String imagesLocation;
  double width;
  double height;

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
    loadImagesLocation();

    if (StringUtils.isNotBlank(imagesLocation)) {
      displayThumb(imagesLocation + "/" + data.getAttributes().get("id") + ".png");
    } else {
      displayEmptyThumb();
    }

    return content;
  }

  protected void displayThumb(String path) {
    final StackPane pane = new StackPane();
    pane.setAlignment(Pos.CENTER);
    pane.setStyle("-fx-background-color:transparent");

    if (CACHE.containsKey(path)) {
      Platform.runLater(() -> {
        content.getChildren().clear();
        content.getChildren().add(CACHE.get(path));
      });
    } else {

      if(path.startsWith("/default")) {
        boolean added = false;

        try (InputStream is = DemoModelThumbPresenter.class.getResourceAsStream(path)) {

          if (is != null) {
            final Image img = new Image(is, 120, 150, true, true);
            final ImageView imgView = new ImageView(img);
            pane.getChildren().add(imgView);
            CACHE.put(path, pane);

            added = true;
          }
        } catch (final IOException e1) {
          //e1.printStackTrace();
          displayEmptyThumb();
        }

        if(!added) {
          displayEmptyThumb();
        }
        else {
          Platform.runLater(() -> {
            content.getChildren().clear();
            content.getChildren().add(pane);
          });
        }
      }
      else {

        boolean added = false;

        try (InputStream is = new FileInputStream(new File(path))) {
          if (is != null) {
            final Image img = new Image(is, 120, 150, true, true);
            final ImageView imgView = new ImageView(img);
            pane.getChildren().add(imgView);
            CACHE.put(path, pane);

            added = true;
          }
        } catch (final IOException e1) {
          //e1.printStackTrace();
          displayEmptyThumb();
        }

        if(!added) {
          displayEmptyThumb();
        }
        else {

          Platform.runLater(() -> {
            content.getChildren().clear();
            content.getChildren().add(pane);
          });
        }
      }
    }
  }

  protected void displayEmptyThumb() {
    // placeholder-image.png
    final Image img = new Image(NO_THUMB, getWidth() > 0 ? getWidth() : 120, getHeight() > 150 ? getHeight() : 90, true, true);
    final ImageView imgView = new ImageView(img);
    Platform.runLater(() -> {
      content.getChildren().add(imgView);
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return provideIcon(controller, configuration, forModel);
  }

  private void loadImagesLocation() {
    if (getPreferenceValueOperation != null) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("key", DATASET_IMAGE_LOCATION);
      getPreferenceValueOperation.doOperation(jsonObject, res -> {
        MultipleResult r = (MultipleResult) res;
        if (r.getData().size() > 0)
          setImagesLocation((String) r.getData().get(0).getAttributes().get("savedValue"));
      });
    }
  }

  private void setImagesLocation(String location) {
    if(StringUtils.isEmpty(location)) {
      this.imagesLocation = "/defaultimages";
      return;
    }
    this.imagesLocation = location;
  }

  /**
   * @return the width
   */
  public double getWidth() {
    return width;
  }

  /**
   * @param width the width to set
   */
  public void setWidth(double width) {
    this.width = width;
  }

  /**
   * @return the height
   */
  public double getHeight() {
    return height;
  }

  /**
   * @param height the height to set
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * @return the platformProperties
   */
  public Properties getPlatformProperties() {
    return platformProperties;
  }

  /**
   * @param platformProperties the platformProperties to set
   */
  public void setPlatformProperties(Properties platformProperties) {
    this.platformProperties = platformProperties;
  }

  /**
   * @return the getPreferenceValueOperation
   */
  public IOperation getGetPreferenceValueOperation() {
    return getPreferenceValueOperation;
  }

  /**
   * @param getPreferenceValueOperation the getPreferenceValueOperation to set
   */
  public void setGetPreferenceValueOperation(IOperation getPreferenceValueOperation) {
    this.getPreferenceValueOperation = getPreferenceValueOperation;
  }

}
