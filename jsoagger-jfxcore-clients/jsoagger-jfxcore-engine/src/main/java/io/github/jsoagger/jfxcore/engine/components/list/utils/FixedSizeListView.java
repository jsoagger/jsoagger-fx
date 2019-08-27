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

package io.github.jsoagger.jfxcore.engine.components.list.utils;




import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FixedSizeListView<T> extends AbstractListView<T> {

  private final BooleanProperty fillWidth = new SimpleBooleanProperty(this, "fillWidth");


  /**
   * Constructor
   */
  public FixedSizeListView() {
    super();
    minHeightProperty().bind(prefHeightProperty());
  }


  public final BooleanProperty fillWidthProperty() {
    return fillWidth;
  }


  public final boolean isFillWidth() {
    return fillWidth.get();
  }


  public final void setFillWidth(boolean fillWidth) {
    this.fillWidth.set(fillWidth);
  }


  @SuppressWarnings("rawtypes")
  @Override
  protected Skin createDefaultSkin() {
    Object[] argsValues = new Object[] {this};
    Skin skin =  (Skin) ReflectionUIUtils.newInstance("io.github.jsoagger.jfxcore.components.list.utils.FixedSizeListViewSkin", argsValues, ListView.class);
    return skin;
  }
}
