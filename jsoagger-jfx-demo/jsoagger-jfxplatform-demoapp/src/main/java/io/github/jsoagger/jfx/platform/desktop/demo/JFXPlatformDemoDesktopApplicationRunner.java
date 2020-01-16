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

package io.github.jsoagger.jfx.platform.desktop.demo;

import io.github.jsoagger.core.utils.StringUtils;

import javafx.application.Application;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 7 mars 2018
 */
public class JFXPlatformDemoDesktopApplicationRunner  {

  /**
   * Default Constructor
   */
  public JFXPlatformDemoDesktopApplicationRunner() {
    super();
  }

  /**
   * JMV ARGS -> jsoagger.platform.launch.with.preloader=TRUE
   *
   * @param args
   */
  public static void main(String[] args) {
    boolean withPreloader = Boolean.valueOf(getArgs("jsoagger.platform.launch.with.preloader=", args, "false"));
    if (withPreloader) {
      System.setProperty("javafx.preloader", "io.github.jsoagger.jfx.core.ui.preloader.desktop.EPDesktopPreloader");
    }
    Application.launch(JFXPlatformDemoDesktopClientRunner.class, args);
  }


  /**
   * @param key
   * @param args
   * @param defaultValue
   * @return
   */
  private static String getArgs(String key, String[] args, String defaultValue) {
    for (String arg : args) {
      if (arg.equals(key)) {
        return StringUtils.substringAfter(arg, key);
      }
    }
    return defaultValue;
  }
}
