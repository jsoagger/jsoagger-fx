/**
 *
 */
package io.github.jsoagger.jfxcore.components;

import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.providers.CoreActionsBeanProvider;
import io.github.jsoagger.jfxcore.engine.providers.CoreAuthentificationBeansProvider;
import io.github.jsoagger.jfxcore.engine.providers.CoreBeansProvider;
import io.github.jsoagger.jfxcore.engine.providers.CoreDetailsBeansProvider;
import io.github.jsoagger.jfxcore.engine.providers.CoreFlowContextBeansProvider;
import io.github.jsoagger.jfxcore.engine.providers.CoreI18NBeansProvider;
import io.github.jsoagger.jfxcore.engine.providers.CoreValidationConstraintBeansProvider;
import io.github.jsoagger.jfxcore.engine.providers.GlobalComponentsProvider;

/**
 * Utility class, agregation of core providers for mobile application.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class ApplicationProviderUtils {


  private static List<Class<?>> ALL_PROVIDERS = new ArrayList<>();

  static {
    ALL_PROVIDERS.add(CoreAuthentificationBeansProvider.class);
    ALL_PROVIDERS.add(CoreBeansProvider.class);
    ALL_PROVIDERS.add(CoreDetailsBeansProvider.class);
    ALL_PROVIDERS.add(CoreFlowContextBeansProvider.class);
    ALL_PROVIDERS.add(CoreI18NBeansProvider.class);

    ALL_PROVIDERS.add(CorePreferencesBeanProvider.class);

    ALL_PROVIDERS.add(CoreComponentsBeanProvider.class);
    ALL_PROVIDERS.add(CoreComponentsFlowItemsBeansProvider.class);
    ALL_PROVIDERS.add(CoreComponentsPresentersBeanProvider.class);
    ALL_PROVIDERS.add(CoreComponentsLayoutsBeanProvider.class);
    ALL_PROVIDERS.add(CoreComponentsSearchBeansProvider.class);
    ALL_PROVIDERS.add(CoreComponentsWelcomeBeanProvider.class);

    ALL_PROVIDERS.add(CoreActionsBeanProvider.class);

    ALL_PROVIDERS.add(CoreValidationConstraintBeansProvider.class);
    ALL_PROVIDERS.add(GlobalComponentsProvider.class);
  }

  /**
   * Internal
   */
  private ApplicationProviderUtils() {
  }

  /**
   * All providers.
   *
   * @return
   */
  public static List<Class<?>> getAllProviders(){
    return ALL_PROVIDERS;
  }
}
