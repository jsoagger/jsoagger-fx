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



import io.github.jsoagger.core.bridge.result.OperationMessage;
import io.github.jsoagger.jfxcore.api.IActionMessage;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ActionMessage implements IActionMessage {

  private String title;
  private String body;
  private int level;


  /**
   * Constructor
   */
  public ActionMessage() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getTitle() {
    return title;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getBody() {
    return body;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setBody(String body) {
    this.body = body;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int getLevel() {
    return level;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setLevel(int level) {
    this.level = level;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ActionMessage [");
    if (title != null) {
      builder.append("title=");
      builder.append(title);
      builder.append(", ");
    }
    if (body != null) {
      builder.append("body=");
      builder.append(body);
      builder.append(", ");
    }
    builder.append("level=");
    builder.append(level);
    builder.append("]");
    return builder.toString();
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public static class ActionMessageBuilder {

    private String title;
    private String body;
    private int level;


    public ActionMessageBuilder title(String title) {
      this.title = title;
      return this;
    }


    public ActionMessageBuilder body(String body) {
      this.body = body;
      return this;
    }


    public ActionMessageBuilder level(int level) {
      this.level = level;
      return this;
    }


    public ActionMessage build() {
      return new ActionMessage(this);
    }


    public ActionMessageBuilder from(OperationMessage operationMessage) {
      this.title = operationMessage.getTitle();
      this.body = operationMessage.getDetail();

      try {
        this.level = Integer.valueOf(operationMessage.getCode());
      } catch (NumberFormatException e) {
      }

      return this;
    }
  }


  private ActionMessage(ActionMessageBuilder builder) {
    this.title = builder.title;
    this.body = builder.body;
    this.level = builder.level;
  }
}
