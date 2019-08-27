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

package io.github.jsoagger.jfxcore.engine.events;



import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MaximizeMenuEvent extends GenericEvent {

  private Node node;
  private MenuPos side;


  /**
   * Constructor
   *
   * @param side
   */
  public MaximizeMenuEvent(MenuPos side) {
    this.side = side;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return MaximizeMenuEvent.class;
  }


  /**
   *
   * Constructor
   *
   * @param side
   */
  public MaximizeMenuEvent(MenuPos side, Node node) {
    this.side = side;
    this.node = node;
  }


  /**
   * Get the node
   *
   * @return the node
   */
  public Node getNode() {
    return node;
  }


  /**
   * Set the node
   *
   * @param node the node to set
   */
  public void setNode(Node node) {
    this.node = node;
  }


  /**
   * Get the side
   *
   * @return the side
   */
  public MenuPos getSide() {
    return side;
  }


  /**
   * Set the side
   *
   * @param side the side to set
   */
  public void setSide(MenuPos side) {
    this.side = side;
  }
}
