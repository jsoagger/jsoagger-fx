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

import javax.imageio.ImageIO;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Icon associadte to the type of the businness data
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public class PeopleThumbPresenter extends CellPresenterImpl implements ModelIconPresenter {

  private static final String NO_THUMB_H = "/images/avatar/avatar-f.png";
  private static final String NO_THUMB_F = "/images/avatar/avatar-h.png";
  private static final String NO_THUMB_ALL = "/images/avatar/profile.png";

  private final StackPane content = new StackPane();

  // needs GetThumbOperation
  private IOperation getThumbOperation;


  public PeopleThumbPresenter() {

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
    OperationData data = (OperationData) forModel;
    if (data == null) {
      data = ((AbstractViewController)controller).getOpData();
    }
    final String fullId = (String) data.getAttributes().get("fullId");
    String gender = String.valueOf((Double.valueOf(String.valueOf(data.getAttributes().get("gender"))).intValue()));

    final Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        if (StringUtils.isEmpty(fullId)) {
          displayEmptyThumb(gender);
        }

        if (getThumbOperation == null) {
          displayEmptyThumb(gender);
        } else {
          displayThumb(fullId, gender);
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


  private void displayThumb(String fullId, String gender) {
    final JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    getThumbOperation.doOperation(query, res -> {
      if (res == null || res.hasBusinessError()) {
        displayEmptyThumb(gender);
      } else {
        try {
          final SingleResult sr = (SingleResult) res;
          final byte[] imgbytes = (byte[]) sr.getMetaData().get("thumb");

          if (imgbytes != null && imgbytes.length > 0) {
            getJavaFXImage(imgbytes, gender);
          } else {
            displayEmptyThumb(gender);
          }
        } catch (final Exception e) {
          e.printStackTrace();
          displayEmptyThumb(gender);
        }
      }
    }, ex -> {
      ex.printStackTrace();
      displayEmptyThumb(gender);
    });

  }


  private void displayEmptyThumb(String gender) {
    Image img = null;
    if(StringUtils.isEmpty(gender) || "0".equals(gender)) {
      img = new Image(NO_THUMB_ALL, 80, 80, true, true);
    }
    else {
      img = new Image(NO_THUMB_ALL, 80, 80, true, true);
    }

    final ImageView imgView = new ImageView(img);
    Platform.runLater(() -> {
      content.getChildren().add(imgView);
    });
    StackPane wrapper = new StackPane();
    wrapper.getChildren().add(imgView);
    wrapper.getStyleClass().add("ep-people-thumb-container");
    content.getChildren().add(wrapper);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return provideIcon(controller, configuration, forModel);
  }


  public void getJavaFXImage(byte[] rawPixels, String gender) {
    try {

      final BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(rawPixels)));
      final java.awt.Image transistionImg = originalImage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
      final BufferedImage currentImage = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);

      final Graphics2D g2d = currentImage.createGraphics();
      g2d.drawImage(transistionImg, 0, 0, null);

      final Image image = SwingFXUtils.toFXImage(currentImage, null);
      final ImageView imageView = new ImageView(image);

      // set a clip to apply rounded border to the original image.
      final Rectangle clip = new Rectangle(60, 60);
      clip.setArcWidth(180);
      clip.setArcHeight(180);
      imageView.setClip(clip);

      // remove the rounding clip so that our effect can show through.
      // imageView.setClip(null);

      // store the rounded image in the imageView.
      imageView.setImage(image);

      Platform.runLater(() -> {
        content.getChildren().add(imageView);
      });
    } catch (final Exception ex) {
      ex.printStackTrace();
      displayEmptyThumb(gender);
    }
  }
}
