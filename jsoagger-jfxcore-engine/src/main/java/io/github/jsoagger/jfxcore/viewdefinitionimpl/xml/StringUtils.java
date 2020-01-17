/**
 *
 */
package io.github.jsoagger.jfxcore.viewdefinitionimpl.xml;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StringUtils {

  /**
   * @param v
   * @return
   */
  public static boolean isBlank(String v) {
    return v != null & v.trim().equals("");
  }


  /**
   * @param val
   * @return
   */
  public static boolean isEmpty(String val) {
    return val == null || val.trim().equals("");
  }


  /**
   * @param string
   * @return
   */
  public static boolean isNotBlank(String v) {
    return !isBlank(v);
  }

}
