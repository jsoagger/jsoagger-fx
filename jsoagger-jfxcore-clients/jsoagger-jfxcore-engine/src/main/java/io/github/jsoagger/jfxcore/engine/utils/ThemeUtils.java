/**
 *
 */
package io.github.jsoagger.jfxcore.engine.utils;

import io.github.jsoagger.core.utils.StringUtils;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ThemeUtils {

  public static String getCssFromPrimaryColor(String color) {

    if(StringUtils.isEmpty(color)) {
      return "/css/color/primary/blue.css";
    }


    switch (color.toLowerCase()) {
      case "blue":
        return "/css/color/primary/blue.css";
      case "cyan":
        return "/css/color/primary/cyan.css";
      case "deep Orange":
        return "/css/color/primary/deep-orange.css";
      case "deep purple":
        return "/css/color/primary/deep-purple.css";
      case "green":
        return "/css/color/primary/green.css";
      case "indigo":
        return "/css/color/primary/indigo.css";
      case "light blue":
        return "/css/color/primary/light-blue.css";
      case "light Green":
        return "/css/color/primary/light-green.css";
      case "lime":
        return "/css/color/primary/lime.css";
      case "orange":
        return "/css/color/primary/orange.css";
      case "pink":
        return "/css/color/primary/pink.css";
      case "purple":
        return "/css/color/primary/purple.css";
      case "red":
        return "/css/color/primary/red.css";
      case "teal":
        return "/css/color/primary/teal.css";
      case "yellow":
        return "/css/color/primary/yellow.css";

      default:
        return "/css/color/primary/blue.css";
    }
  }

  public static String getCssFromAccentColor(String color) {
    if(StringUtils.isEmpty(color)) {
      return "/css/color/accent/deep-orange.css";
    }

    switch (color.toLowerCase()) {
      case "blue":
        return "/css/color/accent/blue.css";
      case "cyan":
        return "/css/color/accent/cyan.css";
      case "deep Orange":
        return "/css/color/accent/deep-orange.css";
      case "deep purple":
        return "/css/color/accent/deep-purple.css";
      case "green":
        return "/css/color/accent/green.css";
      case "indigo":
        return "/css/color/accent/indigo.css";
      case "light blue":
        return "/css/color/accent/light-blue.css";
      case "light Green":
        return "/css/color/accent/light-green.css";
      case "lime":
        return "/css/color/accent/lime.css";
      case "orange":
        return "/css/color/accent/orange.css";
      case "pink":
        return "/css/color/accent/pink.css";
      case "purple":
        return "/css/color/accent/purple.css";
      case "red":
        return "/css/color/accent/red.css";
      case "teal":
        return "/css/color/accent/teal.css";
      case "yellow":
        return "/css/color/accent/yellow.css";

      default:
        return "/css/color/accent/deep-orange.css";
    }
  }
}
