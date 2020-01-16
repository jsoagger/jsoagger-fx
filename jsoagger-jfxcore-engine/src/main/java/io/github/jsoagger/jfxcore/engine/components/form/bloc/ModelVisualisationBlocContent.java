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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.header.comps.ScrollableContent;
import com.google.gson.JsonObject;

import javafx.beans.property.Property;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * InitializingBean, call to afterPropertiesSet
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class ModelVisualisationBlocContent implements IFormBlocContent {

  private static final String NO_ILLUSTRATION = "/images/placeholder-image.png";
  private static final String NO_ILLUSTRATION_IMAGE = "no.illustration.image.path";
  private static final String ILLUSTRATRION_WIDTH = "platform.illustration.width";
  private static final String ILLUSTRATRION_HEIGHT = "platform.illustration.height";

  private double width = 600;
  private double height = 600;
  private String noIllustrationImagePath = NO_ILLUSTRATION;

  // needs GetIllustrationOperation
  private IOperation getIllustrationOperation;
  protected Properties platformProperties;
  protected ScrollableContent content;

  /**
   * Constructor
   */
  public ModelVisualisationBlocContent() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(VLViewComponentXML blocConfig, IJSoaggerController controller) {
    final SingleResult sr = (SingleResult) controller.getModel();
    final OperationData data = sr.getData();
    final String fullId = (String) data.getAttributes().get("fullId");

    content = (ScrollableContent) Services.getBean("ScrollableContent");
    content.buildFrom(controller, blocConfig);
    NodeHelper.setVgrow(content);
    displayEmptyIllustration();

    final Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        if (StringUtils.isEmpty(fullId)) {
          displayEmptyIllustration();
        }

        if (getIllustrationOperation == null) {
          displayEmptyIllustration();
        } else {
          displayIllustration(fullId);
        }
        return null;
      }

      @Override
      protected void failed() {
        super.failed();
        displayEmptyIllustration();
      }

      @Override
      protected void setException(Throwable t) {
        super.setException(t);
        displayEmptyIllustration();
      }
    };

    final Thread t = new Thread(task);
    t.setDaemon(true);
    t.setName("Thumnail_thread__");
    t.start();
  }

  private void displayIllustration(String fullId) {
    final JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    getIllustrationOperation.doOperation(query, res -> {
      if (res == null || res.hasBusinessError()) {
        displayEmptyIllustration();
      } else {
        try {
          List<Node> contents = new ArrayList<>();
          final MultipleResult sr = (MultipleResult) res;

          if (sr.getData().size() == 0) {
            displayEmptyIllustration();
          } else {
            for (OperationData data : sr.getData()) {
              final byte[] imgbytes = (byte[]) data.getAttributes().get("illustration");
              if (imgbytes != null && imgbytes.length > 0) {
                Node illus = getJavaFXImage(imgbytes);
                contents.add(illus);
              } else {
                Node empty = emptyIllustration();
                contents.add(empty);
              }
            }
            content.setContent(contents);
          }
        } catch (final Exception e) {
          e.printStackTrace();
          displayEmptyIllustration();
        }
      }
    }, ex -> {
      ex.printStackTrace();
      displayEmptyIllustration();
    });
  }

  private void displayEmptyIllustration() {
    final Image img = new Image(noIllustrationImagePath, getWidth(), getHeight(), true, true);
    final ImageView imgView = new ImageView(img);
    content.setContent(Arrays.asList(imgView));
  }

  private Node emptyIllustration() {
    final Image img = new Image(noIllustrationImagePath, getWidth(), getHeight(), true, true);
    final ImageView imgView = new ImageView(img);
    return imgView;
  }

  public Node getJavaFXImage(byte[] rawPixels) {
    try {
      final Image image = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(rawPixels)), 400, 400, true, true);
      final ImageView imgView = new ImageView(image);
      return imgView;
    } catch (final Exception ex) {
      ex.printStackTrace();
    }

    return emptyIllustration();
  }

  /**
   *
   * @throws Exception
   */
  public void afterPropertiesSet() {
    //final String width = platformProperties.getProperty(ILLUSTRATRION_WIDTH, "600");
    //final String height = platformProperties.getProperty(ILLUSTRATRION_HEIGHT, "600");

    if(platformProperties != null) {
      noIllustrationImagePath = platformProperties.getProperty(NO_ILLUSTRATION_IMAGE, NO_ILLUSTRATION);
    }
    //this.width = Long.valueOf(width);
    //this.height = Long.valueOf(height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return content;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IFormFieldsetRow> getRows() {
    return new ArrayList();
  }

  @Override
  public Property<Boolean> visibleProperty() {
    return content.getDisplay().visibleProperty();
  }

  public IOperation getGetIllustrationOperation() {
    return getIllustrationOperation;
  }

  public void setGetIllustrationOperation(IOperation getIllustrationOperation) {
    this.getIllustrationOperation = getIllustrationOperation;
  }

  public Properties getPlatformProperties() {
    return platformProperties;
  }

  public void setPlatformProperties(Properties platformProperties) {
    this.platformProperties = platformProperties;
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
}
