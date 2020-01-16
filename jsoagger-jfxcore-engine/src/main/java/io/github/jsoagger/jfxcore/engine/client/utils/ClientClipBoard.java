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

package io.github.jsoagger.jfxcore.engine.client.utils;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.InformationDialog;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ClientClipBoard {

  private final static List<OperationData> content = new ArrayList<>();


  /**
   * Constructor
   */
  private ClientClipBoard() {}


  /**
   * Get current content of clipboard
   *
   * @return
   */
  public static List<OperationData> getElements() {
    return new ArrayList<>(content);
  }


  /**
   * Return true if clipboar has element
   *
   * @return
   */
  public static boolean hasElements() {
    return content.size() > 0;
  }


  /**
   *
   * @return
   */
  public static boolean isElementsOfType(Class<?> clazz) {

    boolean result = false;

    if (hasElements()) {
      final Object proto = getElements().get(0);
      if (clazz.isAssignableFrom(proto.getClass())) {
        result = true;
      }
    }

    return result;
  }


  /**
   * Clear current content and add given content to clipboard
   *
   * @param o
   */
  public static void copy(AbstractViewController c, OperationData o) {
    content.clear();

    if (o != null) {
      content.add(o);
      final String message = content.size() + " Element(s) Copied to clipboard";
      new InformationDialog.Builder()
      .title("Copy")
      .message(message)
      .buildAccent(c).show(true);
    }
    else {
      new InformationDialog.Builder()
      .title("Copy")
      .message("No selected items!")
      .buildAccent(c).show(true);
    }
  }


  /**
   * Clear current content and add given content to clipboard
   *
   * @param os
   */
  public static void copy(AbstractViewController c, final List<OperationData> os) {
    content.clear();

    if (os != null && !os.isEmpty()) {
      content.addAll(os);
      final String message = content.size() + " Element(s) Copied to clipboard";
      new InformationDialog.Builder()
      .title("Copy")
      .message(message)
      .buildAccent(c).show(true);
    }
    else {
      new InformationDialog.Builder()
      .title("Copy")
      .message("No selected items!")
      .buildAccent(c).show(true);
    }
  }
}
