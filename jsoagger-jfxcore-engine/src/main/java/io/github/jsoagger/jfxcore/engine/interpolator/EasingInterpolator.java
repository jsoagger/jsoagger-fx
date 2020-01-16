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

package io.github.jsoagger.jfxcore.engine.interpolator;




import javafx.animation.Interpolator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EasingInterpolator extends Interpolator {

  /**
   * The easing mode.
   */
  private ObjectProperty<EasingMode> easingMode = new SimpleObjectProperty<>();


  /**
   * Constructs the interpolator with a specific easing mode.
   *
   * @param easingMode The easing mode.
   */
  public EasingInterpolator(EasingMode easingMode) {
    this.easingMode.set(easingMode);
  }


  /**
   * The easing mode property.
   *
   * @return The property.
   * @see #getEasingMode()
   * @see #setEasingMode(EasingMode)
   */
  public ObjectProperty<EasingMode> easingModeProperty() {
    return easingMode;
  }


  /**
   * Gets the easing mode.
   *
   * @return The easing mode.
   * @see #easingModeProperty()
   */
  public EasingMode getEasingMode() {
    return easingMode.get();
  }


  /**
   * Sets the easing mode.
   *
   * @param easingMode The easing mode.
   * @see #easingModeProperty()
   */
  public void setEasingMode(EasingMode easingMode) {
    this.easingMode.set(easingMode);
  }


  /**
   * Curves the function depending on the easing mode.
   *
   * @param v The normalized value (between 0 and 1).
   * @return The resulting value of the function.
   */
  @Override
  protected final double curve(final double v) {
    return easingMode.get().function().apply(v);
  }
}
