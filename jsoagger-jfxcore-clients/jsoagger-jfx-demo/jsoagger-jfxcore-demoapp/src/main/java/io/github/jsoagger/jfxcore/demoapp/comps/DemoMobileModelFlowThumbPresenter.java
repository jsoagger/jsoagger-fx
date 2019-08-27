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



import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;

import javafx.application.Platform;
import javafx.concurrent.Task;
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
public class DemoMobileModelFlowThumbPresenter extends CellPresenterImpl implements ModelIconPresenter {

  protected static final String NO_THUMB = "/images/placeholder-image.png";
  static double DEFAULT_WIDTH = 90;
  static double DEFAULT_HEIGHT = 60;
  static Map<String, ImageView> CACHE = new HashMap<>();

  // needs GetPreferenceValueOperation
  IOperation getPreferenceValueOperation;

  // needs platformProperties
  Properties platformProperties;

  StackPane content = new StackPane();
  String imagesLocation;
  double width;
  double height;

  /**
   * Constructor
   */
  public DemoMobileModelFlowThumbPresenter() {
    super();
    content.getStyleClass().add("ep-flow-item-icon-wrapper");
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
    OperationData d = (OperationData) forModel;
    displayThumb("/defaultimages/" + d.getAttributes().get("id")  + ".png");
    return content;
  }

  protected void displayEmptyThumb() {
    final Image img = new Image(NO_THUMB, getWidth(), getHeight(), true, true);
    final ImageView imgView = new ImageView(img);
    Platform.runLater(() -> {
      content.getChildren().add(imgView);
    });
  }

  protected void displayThumb(String path) {
    content.setAlignment(Pos.CENTER);
    Thread t = new Thread(new LoadImageTask(path));
    t.start();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return provideIcon(controller, configuration, forModel);
  }

  /**
   * @return the width
   */
  public double getWidth() {
    if(width > 0) return width;
    return DEFAULT_WIDTH;
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
    if(height > 0) return height;
    return DEFAULT_HEIGHT;
  }

  /**
   * @param height the height to set
   */
  public void setHeight(double height) {
    this.height = height;
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


  class LoadImageTask extends Task<ImageView>{
    String path;

    LoadImageTask(String path){
      this.path = path;
    }

    @Override
    protected ImageView call() throws Exception {
      content.setAlignment(Pos.CENTER);
      if(!CACHE.containsKey(path)) {
        try (InputStream is = DemoMobileModelFlowThumbPresenter.class.getResourceAsStream(path)) {
          if (is != null) {
            final Image img = new Image(is, getWidth(), getHeight(), false, true);
            final ImageView imgView = new ImageView(img);
            Platform.runLater(() -> {
              content.getChildren().clear();
              content.getChildren().add(imgView);
            });
          }
        } catch (final Exception e) {
          displayEmptyThumb();
        }
      }
      else {
        Platform.runLater(() -> {
          content.getChildren().clear();
          content.getChildren().add(CACHE.get(path));
        });
      }
      return null;
    }

    @Override
    protected void setException(Throwable t) {
      displayEmptyThumb();
    }
  }
}
