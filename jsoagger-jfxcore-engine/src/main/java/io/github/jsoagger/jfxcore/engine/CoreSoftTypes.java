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

package io.github.jsoagger.jfxcore.engine;



/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public enum CoreSoftTypes {
  
  // @formatter:off
  APPLICATION_CONTAINER("io.github.jsoagger.Container/Application"), CONTAINER_LINKTYPE("io.github.jsoagger.objectLink.ContainerLink");
  // @formatter:on

  private String path;


  CoreSoftTypes(String path) {
    this.path = path;
  }


  /**
   * Getter of path
   *
   * @return the path
   */
  public String getPath() {
    return path;
  }


  /**
   * Setter of path
   *
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }
}
