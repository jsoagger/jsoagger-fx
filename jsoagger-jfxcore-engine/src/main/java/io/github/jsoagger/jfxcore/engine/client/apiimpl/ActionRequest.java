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



import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.event.ActionEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ActionRequest implements Serializable, IActionRequest {

  private static final long serialVersionUID = -8278112466939208896L;

  private IBuildable source;
  private IJSoaggerController controller;
  private ActionEvent event;
  private Map<String, Object> datas = new HashMap<>();


  /**
   * Constructor
   */
  public ActionRequest() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public IBuildable getSource() {
    return source;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setSource(IBuildable source) {
    this.source = source;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return controller;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setController(IJSoaggerController controller) {
    this.controller = controller;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public ActionEvent getEvent() {
    return event;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setEvent(ActionEvent event) {
    this.event = event;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ActionRequest [");
    if (source != null) {
      builder.append("source=");
      builder.append(source);
      builder.append(", ");
    }
    if (controller != null) {
      builder.append("controller=");
      builder.append(controller);
      builder.append(", ");
    }
    if (event != null) {
      builder.append("event=");
      builder.append(event);
      builder.append(", ");
    }
    if (getSource() != null) {
      builder.append("getSource()=");
      builder.append(getSource());
      builder.append(", ");
    }
    if (getController() != null) {
      builder.append("getController()=");
      builder.append(getController());
      builder.append(", ");
    }
    if (getEvent() != null) {
      builder.append("getEvent()=");
      builder.append(getEvent());
      builder.append(", ");
    }
    if (getClass() != null) {
      builder.append("getClass()=");
      builder.append(getClass());
      builder.append(", ");
    }
    builder.append("hashCode()=");
    builder.append(hashCode());
    builder.append(", ");
    if (super.toString() != null) {
      builder.append("toString()=");
      builder.append(super.toString());
    }
    builder.append("]");
    return builder.toString();
  }

  public static class Builder {

    private IBuildable source;
    private IJSoaggerController controller;
    private ActionEvent event;
    private Map<String, Object> datas = new HashMap<>();


    public Builder source(IBuildable source) {
      this.source = source;
      return this;
    }


    public Builder controller(AbstractViewController controller) {
      this.controller = (IJSoaggerController) controller;
      return this;
    }


    public Builder data(String key, String value) {
      datas.put(key, value);
      return this;
    }


    public Builder event(ActionEvent event) {
      this.event = event;
      return this;
    }


    public ActionRequest build() {
      return new ActionRequest(this);
    }


    public Builder args(String args) {
      if (StringUtils.hasText(args)) {
        List<String> argsSplited = Arrays.asList(args.split(";"));
        for(String arg: argsSplited) {
          String key = arg.split(":")[0];
          String value = arg.split(":")[1];
          data(key, value);
        }
      }
      return this;
    }
  }


  private ActionRequest(Builder builder) {
    source = builder.source;
    controller = builder.controller;
    event = builder.event;
    datas = builder.datas;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Object getProperty(String key) {
    return datas.get(key);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setProperty(String key, Object value) {
    datas.put(key, value);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Map<String, Object> properties() {
    return datas;
  }
}
