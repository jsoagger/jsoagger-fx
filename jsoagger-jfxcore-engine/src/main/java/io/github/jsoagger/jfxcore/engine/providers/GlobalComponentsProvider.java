/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;

import io.github.jsoagger.core.ioc.api.annotations.GlobalComponents;
import io.github.jsoagger.core.ioc.api.annotations.GlobalComponentsToJson;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@GlobalComponentsToJson
public class GlobalComponentsProvider {

  public GlobalComponentsProvider() {}

  @GlobalComponents(
      source = {"classpath:/comp/CoreActions.xml", "classpath:/comp/CoreActionsModel.xml",
          "classpath:/comp/CoreAttributes.xml", "classpath:/comp/CoreComponents.xml"},
      destination = "/io/github/jsoagger/jfxcore/demoapp/mobile/globalcomps")
  public void process() {
    // <value>/comp/CoreActions.xml</value>
    /// <value>/comp/CoreActionsModel.xml</value>
    // <value>/comp/CoreAttributes.xml</value>
    // <value>/comp/CoreColumns.xml</value>
    // <value>/comp/CoreTreeColumns.xml</value>
    // <value>/comp/CoreComponents.xml</value>
  }
}
