/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;

import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.ConvertViewToJson;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.detailsview.IEmptyDetailsview;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.layout.EmptyDetailsView;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
@ConvertViewToJson
public class CoreDetailsBeansProvider {

  public CoreDetailsBeansProvider() {}

  @Bean
  @Named("EmptyDetailsView")
  public IEmptyDetailsview EmptyDetailsView() {
    return new EmptyDetailsView();
  }
}
