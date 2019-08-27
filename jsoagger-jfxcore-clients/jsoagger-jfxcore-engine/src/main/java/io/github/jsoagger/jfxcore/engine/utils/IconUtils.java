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

package io.github.jsoagger.jfxcore.engine.utils;



import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewRootMenuRowXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 22 janv. 2018
 */
public class IconUtils {

  /**
   * @param ikonli
   * @return
   */
  public static Label getFontIcon(String ikonli) {
    Label label = new Label();

    if (StringUtils.isEmpty(ikonli)) {
      return label;
    }

    if (ikonli.startsWith("classpath:")) {
      /* Callback<ImageView, Void> callback = param -> {
        label.setGraphic(param);
        return null;
      };

      SVGUtils.newInstance(callback).loadSVGImage(IconUtils.class.getResource(StringUtils.substringAfterLast(ikonli, "classpath:")), 40, 40);
       */
    } else {
      FontIcon icon = new FontIcon(ikonli);
      label.setGraphic(icon);
    }

    return label;
  }


  /**
   * @param ikonli
   * @return
   */
  public static Labeled setFontIcon(String ikonli, Labeled label) {
    try {
      FontIcon icon = new FontIcon(ikonli);
      label.setGraphic(icon);
    }catch (Exception e) {
      e.printStackTrace();
    }
    return label;
  }


  /**
   * @param ikonli
   * @return
   */
  public static Labeled setFontIcon(String ikonli, String style, Labeled label) {
    try {
      FontIcon icon = new FontIcon(ikonli);
      icon.getStyleClass().add(style);
      label.setGraphic(icon);
    }catch (Exception e) {
      e.printStackTrace();
    }
    return label;
  }


  /**
   * @param icon
   * @param configuration
   */
  public static void setIcon(Labeled labeled, VLViewComponentXML configuration) {
    try {
      String ikonli = configuration.getPropertyValue("ikonli");
      if (StringUtils.isNotBlank(ikonli)) {
        FontIcon icon = new FontIcon(ikonli);
        labeled.setGraphic(icon);

        String iconStyleClass = configuration.getPropertyValue("iconStyleClass");
        if (StringUtils.isNotBlank(iconStyleClass)) {
          icon.getStyleClass().addAll(iconStyleClass.split(","));
        }
      }
    } catch (final Exception e1) {
      e1.printStackTrace();
      // TODO try another mathod
    }
  }


  /**
   * @param icon
   * @param configuration
   */
  public static void setIcon(Labeled labeled, VLViewRootMenuRowXML configuration) {
    try {
      String ikonli = configuration.getIcon();
      if (StringUtils.isNotBlank(ikonli)) {
        FontIcon icon = new FontIcon(ikonli);
        labeled.setGraphic(icon);

        String iconStyleClass = configuration.getIconStyleClass();
        if (StringUtils.isNotBlank(iconStyleClass)) {
          icon.getStyleClass().addAll(iconStyleClass.split(","));
        }
      }
    } catch (final Exception e1) {
      e1.printStackTrace();
      // TODO try another method
    }
  }


  /**
   * @param navigationIcon
   * @param configuration
   */
  public static void setNavigationIcon(Labeled navigationIcon, VLViewComponentXML configuration) {
    try {
      FontIcon icon = new FontIcon("mdi-chevron-right:22");
      navigationIcon.setGraphic(icon);
      icon.getStyleClass().add("chevron-right-navigation-icon");
    }catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void setHeaderNavigationBack(Labeled navigationIcon) {
    try {
      FontIcon icon = new FontIcon("mdi-chevron-left:38");
      navigationIcon.setGraphic(icon);
      icon.getStyleClass().add("chevron-left-header-navigation-icon");
    }catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void setHeaderLocationSeparator(Label separator) {
    try {
      FontIcon icon = new FontIcon("mdi-chevron-right:20");
      separator.setGraphic(icon);
      icon.getStyleClass().add("header-location-separator");
    }catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void setContentLocationSeparator(Label separator) {
    try {
      FontIcon icon = new FontIcon("fa-caret-square-o-right:12");
      separator.setGraphic(icon);
      icon.getStyleClass().add("content-location-separator");
    }catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static Node getErrorIcon() {
    Label label = new Label();
    try {
      FontIcon icon = new FontIcon("fa-exclamation:20");
      label.setGraphic(icon);
    }catch (Exception e) {
      e.printStackTrace();
    }
    return label;
  }


  public static void collpasedIcon(Labeled label) {
    try {
      FontIcon icon = new FontIcon("fa-arrow-right:20");
      label.setGraphic(icon);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void expandedIcon(Labeled label) {
    try {
      FontIcon expandedIcon = new FontIcon("fa-arrow-left:20");
      label.setGraphic(expandedIcon);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
}
