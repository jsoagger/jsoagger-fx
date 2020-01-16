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



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Properties;

import javax.imageio.ImageIO;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
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
public class ModelThumbPresenter extends CellPresenterImpl implements ModelIconPresenter {

  protected static final String NO_THUMB = "/images/placeholder-image.png";
  protected static final String NO_THUMB_IMAGE_PATH = "no.thumb.image.path";
  protected static final String THUMB_WIDTH = "platform.thumbnail.width";
  protected static final String THUMB_HEIGHT = "platform.thumbnail.height";

  protected String noThumbImagePath;

  // Do not forget afterPropertiesSet
  protected Properties platformProperties;
  protected final StackPane content = new StackPane();

  // needs GetThumbOperation
  protected IOperation getThumbOperation;


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
    OperationData data = (OperationData) forModel;
    if (forModel == null) {
      final SingleResult sr = (SingleResult) controller.getModel();
      data = sr.getData();
    }

    final String fullId = (String) data.getAttributes().get("fullId");
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


  protected void displayThumb(String fullId) {
    final JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    getThumbOperation.doOperation(query, res -> {
      if (res == null || res.hasBusinessError()) {
        displayEmptyThumb();
      } else {
        try {
          final SingleResult sr = (SingleResult) res;
          final byte[] imgbytes = (byte[]) sr.getMetaData().get("thumb");

          if (imgbytes != null && imgbytes.length > 0) {
            getJavaFXImage(imgbytes);
          } else {
            displayEmptyThumb();
          }
        } catch (final Exception e) {
          e.printStackTrace();
          displayEmptyThumb();
        }
      }
    }, ex -> {
      ex.printStackTrace();
      displayEmptyThumb();
    });

  }


  protected void displayEmptyThumb() {
    // placeholder-image.png
    final Image img = new Image(NO_THUMB, 90, 90, true, true);
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


  public void getJavaFXImage(byte[] rawPixels) {
    try {

      final BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(rawPixels)));
      final java.awt.Image transistionImg = originalImage.getScaledInstance(originalImage.getWidth() / 4, originalImage.getHeight() / 4, java.awt.Image.SCALE_SMOOTH);
      final BufferedImage currentImage = new BufferedImage(originalImage.getWidth() / 4, originalImage.getHeight() / 4, BufferedImage.TYPE_INT_ARGB);

      final Graphics2D g2d = currentImage.createGraphics();
      g2d.drawImage(transistionImg, 0, 0, null);

      final Image image = SwingFXUtils.toFXImage(currentImage, null);
      final ImageView imgView = new ImageView(image);
      Platform.runLater(() -> {
        content.getChildren().add(imgView);
      });
    } catch (final Exception ex) {
      ex.printStackTrace();
      displayEmptyThumb();
    }
  }

  public void afterPropertiesSet() throws Exception {
    final String width = platformProperties.getProperty(THUMB_WIDTH, "120");
    final String height = platformProperties.getProperty(THUMB_HEIGHT, "120");
    noThumbImagePath = platformProperties.getProperty(NO_THUMB_IMAGE_PATH, NO_THUMB);
  }


  public Properties getPlatformProperties() {
    return platformProperties;
  }


  public void setPlatformProperties(Properties platformProperties) {
    this.platformProperties = platformProperties;
  }
}
