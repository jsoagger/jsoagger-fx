/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreI18NBeansProvider {

  public CoreI18NBeansProvider() {}

  @Bean
  @Singleton
  @Named("CoreMessageSource")
  public MessageSource rooStructureMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.engine.controller.localization");
    messageSource.getBasenames().add("i18n.generalBundle");
    return messageSource;
  }
}
