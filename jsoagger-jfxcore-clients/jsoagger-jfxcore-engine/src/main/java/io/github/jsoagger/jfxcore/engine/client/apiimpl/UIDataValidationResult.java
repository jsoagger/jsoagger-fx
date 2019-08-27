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



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.IUIValidationMessage;
import io.github.jsoagger.jfxcore.api.UIDataValidationResultStatus;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class UIDataValidationResult implements IUIDataValidationResult {

  protected List<IUIValidationMessage> messages = new ArrayList<>();
  protected UIDataValidationResultStatus status = UIDataValidationResultStatus.VALID;


  /**
   * Constructor
   */
  public UIDataValidationResult() {}


  @Override
  public boolean hasMessage() {
    return messages != null && !messages.isEmpty();
  }

  @Override
  public UIDataValidationResultStatus getStatus() {
    return status;
  }


  @Override
  public void setStatus(UIDataValidationResultStatus status) {
    this.status = status;
  }


  /**
   * @return
   */
  public static IUIDataValidationResult success() {
    return new UIDataValidationResult();
  }


  /**
   * @return
   */
  public static IUIDataValidationResult error() {
    UIDataValidationResult result = new UIDataValidationResult();
    result.status = UIDataValidationResultStatus.NOT_VALID;
    return result;
  }

  public static class Builder {

    private List<IUIValidationMessage> messages;
    private UIDataValidationResultStatus status;


    public Builder messages(List<IUIValidationMessage> messages) {
      this.messages = messages;
      return this;
    }


    public Builder addMessage(IUIValidationMessage message) {
      if (messages == null) {
        messages = new ArrayList<>();
      }

      this.messages.add(message);
      return this;
    }


    public Builder addMessage(String message) {
      if (messages == null) {
        messages = new ArrayList<>();
      }

      IUIValidationMessage msg = new UIValidationMessage.Builder().message(message).build();
      this.messages.add(msg);
      return this;
    }


    public Builder status(UIDataValidationResultStatus status) {
      this.status = status;
      return this;
    }


    public IUIDataValidationResult build() {
      return new UIDataValidationResult(this);
    }
  }


  private UIDataValidationResult(Builder builder) {
    this.messages = builder.messages;
    this.status = builder.status;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.client.apiimpl.IUIDataValidationResult#getMessages()
   */
  @Override
  public List<IUIValidationMessage> getMessages() {
    return messages;
  }


  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.engine.client.apiimpl.IUIDataValidationResult#setMessages(java.util.List)
   */
  @Override
  public void setMessages(List<IUIValidationMessage> messages) {
    this.messages = messages;
  }
}
