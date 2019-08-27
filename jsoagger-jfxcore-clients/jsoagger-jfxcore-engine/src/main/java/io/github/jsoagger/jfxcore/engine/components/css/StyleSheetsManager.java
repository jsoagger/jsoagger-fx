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

package io.github.jsoagger.jfxcore.engine.components.css;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.css.IStylesheetManager;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;

import javafx.application.Application;
import javafx.application.Platform;
//import jfxtras.resources.JFXtrasFontRoboto;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class StyleSheetsManager implements IStylesheetManager {

  private final static List<String> loadedStylesheets = new ArrayList<>();
  private List<String> styleSheetsPath = new ArrayList<>();


  /**
   * Constructor
   */
  public StyleSheetsManager() {}


  /**
   * Add the given stylesheet if not already loaded.
   *
   * @param stylesheet
   */
  @Override
  public final void addStyleSheet(String stylesheet) {
    if (!loadedStylesheets.contains(stylesheet)) {
      //// logger.debug("Load stylesheet " + stylesheet);
      loadedStylesheets.add(stylesheet);
      if (Platform.isFxApplicationThread()) {
        addUserAgentStylesheet(stylesheet);
      } else {
        Platform.runLater(() -> {
          addUserAgentStylesheet(stylesheet);
        });
      }
    }
  }


  @Override
  public void reLoadSteelSheets() {
    //// logger.debug("Reloading stylesheet start");
    //// logger.debug("Stylesheet to reload: " + loadedStylesheets.size());

    for (final Iterator<String> iterator = loadedStylesheets.iterator(); iterator.hasNext();) {
      final String stylesheet = iterator.next();
      removeUserAgentStylesheet(stylesheet);
      // //logger.debug("Unload : " + stylesheet);
      //System.out.println("Unload : " + stylesheet);
    }

    for (final Iterator<String> iterator = loadedStylesheets.iterator(); iterator.hasNext();) {
      final String stylesheet = iterator.next();
      addUserAgentStylesheet(stylesheet);
      //// logger.debug("Load : " + stylesheet);
      //System.out.println("Load : " + stylesheet);
    }

    // logger.debug("Reloading stylesheet end");
  }


  private void clearCurrentTheme() {
    List<String> currentThemes = new ArrayList<>();
    for (final Iterator<String> iterator = loadedStylesheets.iterator(); iterator.hasNext();) {
      final String stylesheet = iterator.next();
      if (stylesheet.contains("/css/color/primary/") || stylesheet.contains("/css/color/accent/")) {
        currentThemes.add(stylesheet);
      }
    }

    if (!currentThemes.isEmpty()) {
      for(String s: currentThemes) {
        removeUserAgentStylesheet(s);
        loadedStylesheets.remove(s);
      }
    }
  }

  public void setDefaultTheme(String themePath) {
    clearCurrentTheme();
    loadedStylesheets.add(ResourceUtils.getURL(themePath).toExternalForm());
  }

  public void setDefaultTheme(String primary, String accent) {
    if(Platform.isFxApplicationThread()) {
      _setDefaultTheme(primary, accent);
    }
    else {
      Platform.runLater(()->_setDefaultTheme(primary, accent));
    }

  }

  private void _setDefaultTheme(String primary, String accent) {
    clearCurrentTheme();
    loadedStylesheets.add(0, primary);
    loadedStylesheets.add(0, accent);
    reLoadSteelSheets();
  }

  @Override
  public void loadSteelSheets() {
    // logger.debug("Load stylesheets");

    // init platform
    Application.setUserAgentStylesheet(null);
    //setDefaultPlatformUserAgentStylesheet();

    // Force to load default CSS
    Application.setUserAgentStylesheet(null);

    // load Roboto google font
    if(AbstractApplicationRunner.isDesktop()) {
      //JFXtrasFontRoboto.loadAll();
    }

    if (styleSheetsPath != null) {
      // logger.debug("Core stylesheets size : " + styleSheetsPath.size());

      for (final String stylesheet : styleSheetsPath) {
        try {
          boolean isThemeSheet = false;
          if(defaultThemeWasLoaded()) {
            if (stylesheet.contains("/css/color/primary/") || stylesheet.contains("/css/color/accent/")) {
              isThemeSheet = true;
            }
          }

          if(!isThemeSheet) {
            loadedStylesheets.add(StringUtils.substringAfter(stylesheet, "classpath:"));
          }
        } catch (final IllegalArgumentException e) {
          System.out.println("Stylesheet in error : " + stylesheet);
          e.printStackTrace();
          // logger.error(e);
        }
        // logger.debug("Loading core stylesheet : " + stylesheet);
      }
    }

    final List<String> customStyleSheetsPath = (List<String>) Services.getBean("customStyleSheetsPath");
    if (customStyleSheetsPath != null && !customStyleSheetsPath.isEmpty()) {
      for (final String stylesheet : customStyleSheetsPath) {
        try {
          boolean isThemeSheet = false;
          if(defaultThemeWasLoaded()) {
            if (stylesheet.contains("/css/color/primary/") || stylesheet.contains("/css/color/accent/")) {
              isThemeSheet = true;
            }
          }

          if(!isThemeSheet) {
            loadedStylesheets.add(StringUtils.substringAfter(stylesheet, "classpath:"));
          }
        } catch (final Exception e) {
          e.printStackTrace();
          // logger.error(e);
        }

        // logger.debug("Loading core stylesheet : " + stylesheet);
      }
    }


    for (final Iterator<String> iterator = loadedStylesheets.iterator(); iterator.hasNext();) {
      final String string = iterator.next();
      addUserAgentStylesheet(string);
    }
  }

  /**
   * Default theme is loaded before loading core stylesheet, in that case do not override theme inside loadedstylesheet
   * which are style sheets of current user.
   */
  private boolean defaultThemeWasLoaded() {
    return !loadedStylesheets.isEmpty();
  }

  /**
   * Add a path to a current stylesheets to load
   *
   * @param path
   */
  public void addStyleSheetsPath(String path) {
    getStyleSheetsPath().add(path);
  }

  /**
   * Getter of styleSheetsPath
   *
   * @return the styleSheetsPath
   */
  public List<String> getStyleSheetsPath() {
    return styleSheetsPath;
  }


  /**
   * Setter of coreStyleSheetsPath
   *
   * @param coreStyleSheetsPath the coreStyleSheetsPath to set
   */
  @Override
  public void setStyleSheetsPath(List<String> styleSheetsPath) {
    this.styleSheetsPath = styleSheetsPath;
  }


  /****************************************************************************************************
   *
   * JAVA9 use reflection to access restricted fields and methods(com.sun.javafx.*).
   *
   ****************************************************************************************************/
  public static void setDefaultPlatformUserAgentStylesheet() {
    try {
      final Class<?> paltformImpl = Class.forName("com.sun.javafx.application.PlatformImpl");
      final Method setDefaultPlatformUserAgentStylesheet = paltformImpl.getMethod("setDefaultPlatformUserAgentStylesheet");
      setDefaultPlatformUserAgentStylesheet.setAccessible(true);
      setDefaultPlatformUserAgentStylesheet.invoke(paltformImpl);
    } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
      throw new RuntimeException("Cannot add UserAgentStylesheet as the method is not accessible");
    }
  }


  public static void addUserAgentStylesheet(String stylesheet) {
    try {
      final Class<?> styleManagerClass = Class.forName("com.sun.javafx.css.StyleManager");
      final Method getInstance = styleManagerClass.getMethod("getInstance");
      getInstance.setAccessible(true);
      final Object styleManager = getInstance.invoke(styleManagerClass);
      final Method addUserStyleSheet = styleManagerClass.getMethod("addUserAgentStylesheet", String.class);
      addUserStyleSheet.setAccessible(true);
      addUserStyleSheet.invoke(styleManager, stylesheet);
    } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
      throw new RuntimeException("Cannot add UserAgentStylesheet as the method is not accessible");
    }
  }


  public static void removeUserAgentStylesheet(String stylesheet) {
    try {
      final Class<?> styleManagerClass = Class.forName("com.sun.javafx.css.StyleManager");
      final Method getInstance = styleManagerClass.getMethod("getInstance");
      getInstance.setAccessible(true);
      final Object styleManager = getInstance.invoke(styleManagerClass);
      final Method addUserStyleSheet = styleManagerClass.getMethod("removeUserAgentStylesheet", String.class);
      addUserStyleSheet.setAccessible(true);
      addUserStyleSheet.invoke(styleManager, stylesheet);
    } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
      throw new RuntimeException("Cannot add UserAgentStylesheet as the method is not accessible");
    }
  }
}
