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

package io.github.jsoagger.jfxcore.engine.components.presenter.utils;



import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.services.Services;

import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class ModelIconUtils {

  static List<String> firstCat = Arrays.asList("a", "c", "f", "g", "h", "k", "l", "m", "z", "u");
  static List<String> secondCat = Arrays.asList("b", "d", "e", "i", "n");

  private static String MEDIUM = "medium.";
  private static String SMALL = "small.";

  private Properties iconMapping = new Properties();
  private static ModelIconUtils instance = null;


  /**
   * Get the single instance of icon reolver. When first time loading, it will search for spring bean
   * nammed 'IconDefinitionFiles", and will load all files inside it.
   */
  public static ModelIconUtils instance() {
    if (instance == null) {
      instance = (ModelIconUtils) Services.getBean("ModelIconUtils");
      List<String> iconDefinitionFiles = (List<String>) Services.getBean("IconDefinitionFiles");

      for (String definition : iconDefinitionFiles) {
        Properties definitionMapping = new Properties();
        try (InputStream is = ResourceUtils.getStream(ModelIconUtils.class, definition)) {
          definitionMapping.load(is);
          instance.iconMapping.putAll(definitionMapping);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }

    return instance;
  }


  /**
   * @{inheritedDoc}
   */
  public static Node provideIcon(AbstractViewController controller) {
    try {
      SingleResult model = (SingleResult) controller.getModel();
      OperationData data = (OperationData) model.rootData();
      String businessTypePath = (String) data.getBusinessType().get("logicalPath");
      if (StringUtils.isEmpty(businessTypePath)) {
        businessTypePath = (String) data.getBusinessType().get("internalType");

      }
      String iconPath = (String) data.getAttributes().get("iconPath");

      // icon from classpath
      if (StringUtils.isNotBlank(iconPath)) {
        ImageView imageView = new ImageView(iconPath);
        Label label = new Label();
        label.setGraphic(imageView);
        return label;
      }
      // icon from ikonli
      else {
        return provideIcon(businessTypePath);
      }
    } catch (Exception ex) {
      Label label = IconUtils.getFontIcon("mdi-exclamation");
      label.getStyleClass().add("icon-provider-icon");
      return label;
    }
  }


  public static Node provideIcon(String businessTypePath) {
    try {

      if (StringUtils.isEmpty(businessTypePath)) {
        businessTypePath = "error";
      }

      String icon = instance().iconMapping.getProperty(MEDIUM + businessTypePath);

      Node node = getIcon(icon);
      StackPane wrapper = new StackPane();
      wrapper.getChildren().add(node);

      wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper-medium");
      String initial = instance().iconMapping.getProperty(businessTypePath + ".initial");
      if ((initial != null) && firstCat.contains(initial.toLowerCase())) {
        wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper-primary");
      } else if ((initial != null) && secondCat.contains(initial.toLowerCase())) {
        wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper-grey");
      } else {
        wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper");
      }

      return wrapper;
    } catch (Exception ex) {
      // ex.printStackTrace();

      Label label = IconUtils.getFontIcon("mdi-exclamation");
      label.getStyleClass().add("icon-provider-icon");
      return label;
    }
  }


  private static Node _getIcon(String icon, String businessTypePath) {
    try {
      Node node = getIcon(icon);
      StackPane wrapper = new StackPane();
      wrapper.getChildren().add(node);

      wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper-small");
      String initial = instance().iconMapping.getProperty(businessTypePath + ".initial");
      if ((initial != null) && firstCat.contains(initial.toLowerCase())) {
        wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper-primary");
      } else if ((initial != null) && secondCat.contains(initial.toLowerCase())) {
        wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper-grey");
      } else {
        wrapper.getStyleClass().add("item-initial-circle-icon-label-wrapper");
      }

      return wrapper;
    } catch (Exception ex) {
      // ex.printStackTrace();

      Label label = IconUtils.getFontIcon("mdi-exclamation");
      label.getStyleClass().add("icon-provider-icon");
      return label;
    }
  }


  public static Node provideMediumIcon(String businessTypePath) {
    if (StringUtils.isEmpty(businessTypePath)) {
      businessTypePath = "error";
    }

    String icon = instance().iconMapping.getProperty(MEDIUM + businessTypePath);
    return _getIcon(icon, businessTypePath);
  }


  public static Node provideSmallIcon(String businessTypePath) {
    if (StringUtils.isEmpty(businessTypePath)) {
      businessTypePath = "error";
    }

    String icon = instance().iconMapping.getProperty(SMALL + businessTypePath);
    if (icon == null) {
      return NodeHelper.getErrorIndicator();
    }
    return _getIcon(icon, businessTypePath);
  }


  private static Node getIcon(String iconString) {
    if (iconString.split("#").length > 1) {
      String[] icons = iconString.split("#");
      StackPane pane = new StackPane();
      FontIcon fontIcon = new FontIcon(icons[0]);
      FontIcon fontIcon1 = new FontIcon(icons[1]);
      fontIcon1.translateXProperty().set(10);

      pane.getChildren().addAll(fontIcon, fontIcon1);

      return pane;
    } else {
      FontIcon fontIcon = new FontIcon(iconString);
      return fontIcon;
    }
  }


  public static Node provideSmallIcon(AbstractViewController controller) {
    SingleResult model = (SingleResult) controller.getModel();
    OperationData data = (OperationData) model.rootData();
    String businessTypePath = (String) data.getBusinessType().get("logicalPath");
    if (StringUtils.isEmpty(businessTypePath)) {
      businessTypePath = (String) data.getBusinessType().get("internalType");
    }
    return provideIcon(businessTypePath);
  }
}
