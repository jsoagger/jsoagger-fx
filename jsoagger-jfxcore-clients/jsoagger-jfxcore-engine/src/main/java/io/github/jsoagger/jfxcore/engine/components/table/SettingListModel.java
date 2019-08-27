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

package io.github.jsoagger.jfxcore.engine.components.table;



import java.io.Serializable;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SettingListModel implements Serializable {

  private static final long serialVersionUID = 7709212965950815938L;
  private String title;
  private boolean isDefault;

  /**
   * 
   * @param title
   * @param isDefault
   */
  public SettingListModel(String title, boolean isDefault) {
    super();
    this.title = title;
    this.isDefault = isDefault;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the isDefault
   */
  public boolean isDefault() {
    return isDefault;
  }

  /**
   * @param isDefault the isDefault to set
   */
  public void setDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }
}
