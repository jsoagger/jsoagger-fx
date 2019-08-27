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

package io.github.jsoagger.jfxcore.engine.components.menu.quaternary;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class QuaternaryMenuTab extends VBox {

  /** CSS */
  private final static String HEADER_TAB_ITEM_CONTAINER = "quat-tab-item-container";
  private final static String HEADER_TAB_ITEM_TITLE = "quat-tab-item-title";

  private final PseudoClass selected = PseudoClass.getPseudoClass("selected");

  /** The title of the tab */
  private Label title;

  /** The content of the tab */
  private Node content;

  /** The id of the tabs */
  private String internalId;

  /** The tab wizardConfiguration */
  private final VLViewComponentXML config;


  /**
   * Constructor
   *
   * @param config
   * @param text
   */
  public QuaternaryMenuTab(VLViewComponentXML config, String text) {
    this.config = config;
    this.title = new Label();
    this.title.setText(text.toUpperCase());
    this.title.getStyleClass().add(HEADER_TAB_ITEM_TITLE);

    getStyleClass().add(HEADER_TAB_ITEM_CONTAINER);
    NodeHelper.setHVGrow(this);

    setAlignment(Pos.CENTER);
    getChildren().add(title);
  }


  /**
   * Constructor
   *
   * @param config
   * @param text
   * @param content
   */
  public QuaternaryMenuTab(VLViewComponentXML config, String text, Node content) {
    this(config, text);
    this.content = content;
  }


  public void select(boolean value) {
    pseudoClassStateChanged(selected, value);
  }


  /**
   * @return the title
   */
  public Label getTitle() {
    return title;
  }


  /**
   * @param title the title to set
   */
  public void setTitle(Label title) {
    this.title = title;
  }


  /**
   * @return the content
   */
  public Node getContent() {
    return content;
  }


  /**
   * @param content the content to set
   */
  public void setContent(Node content) {
    this.content = content;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final QuaternaryMenuTab other = (QuaternaryMenuTab) obj;
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.getText().equals(other.title.getText())) {
      return false;
    }
    return true;
  }


  /**
   * @return the internalId
   */
  public String getInternalId() {
    return internalId;
  }


  /**
   * @param internalId the internalId to set
   */
  public void setInternalId(String internalId) {
    this.internalId = internalId;
  }


  /**
   * @return the config
   */
  public VLViewComponentXML getConfig() {
    return config;
  }
}
