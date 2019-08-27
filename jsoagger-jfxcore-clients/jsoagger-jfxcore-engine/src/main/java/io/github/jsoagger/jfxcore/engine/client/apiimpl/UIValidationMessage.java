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

package io.github.jsoagger.jfxcore.engine.client.apiimpl;

import io.github.jsoagger.jfxcore.api.IUIValidationMessage;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class UIValidationMessage implements IUIValidationMessage {

  private String message;


  /**
   * Constructor
   */
  public UIValidationMessage() {}


  @Override
  public String getMessage() {
    return message;
  }


  @Override
  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UIValidationMessage [");
    if (message != null) {
      builder.append("message=");
      builder.append(message);
    }
    builder.append("]");
    return builder.toString();
  }

  public static class Builder {

    private String message;


    public Builder message(String message) {
      this.message = message;
      return this;
    }


    public UIValidationMessage build() {
      return new UIValidationMessage(this);
    }
  }


  private UIValidationMessage(Builder builder) {
    this.message = builder.message;
  }
}
