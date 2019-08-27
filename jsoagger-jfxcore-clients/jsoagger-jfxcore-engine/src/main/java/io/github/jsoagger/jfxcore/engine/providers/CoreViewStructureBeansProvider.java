/**
 *
 */

package io.github.jsoagger.jfxcore.engine.providers;


import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.jfxcore.api.services.ApplicationContextService;
import io.github.jsoagger.jfxcore.api.services.CommonComponentsServices;
import io.github.jsoagger.jfxcore.api.services.GlobalComponentsService;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.services.ViewConfigurationService;
import io.github.jsoagger.jfxcore.engine.providers.integration.CommonCompsServices;
import io.github.jsoagger.jfxcore.engine.providers.integration.JSoaggerFXApplicationContextService;
import io.github.jsoagger.jfxcore.engine.providers.integration.JSonViewConfigurationService;
import io.github.jsoagger.jfxcore.engine.providers.integration.JsonGlobalCompsService;

/**
 * @author Ramilafananana  VONJISOA
 */
@BeansProvider
public class CoreViewStructureBeansProvider {

  /**
   * Constructor
   */
  public CoreViewStructureBeansProvider() {
  }


  @Bean
  @Singleton
  @Named("ApplicationContextService")
  public ApplicationContextService appContextService() {
	JSoaggerFXApplicationContextService mobile = new JSoaggerFXApplicationContextService();
    return mobile;
  }


  @Bean
  @Singleton
  @Named("ViewConfigurationService")
  public ViewConfigurationService viewConfigurationService() {
    JSonViewConfigurationService viewConfigurationService = new JSonViewConfigurationService();
    return viewConfigurationService;
  }

  @Bean
  @Singleton
  @Named("GlobalComponentsService")
  public GlobalComponentsService jsonGlobalCompsService() {
    JsonGlobalCompsService jsonGlobalComps = new JsonGlobalCompsService();
    return jsonGlobalComps;
  }

  @Bean
  @Singleton
  @Named("CommonCompsServices")
  public CommonCompsServices commonCompsServices() {
    CommonCompsServices commonCompsServices = new CommonCompsServices();
    return commonCompsServices;
  }

  @Bean
  @Singleton
  @Named("IntegrationService")
  public Services integrationService() {
    Services services = Services.instance();
    services.setAppContextService((ApplicationContextService) BeanFactory.instance().getBean("ApplicationContextService"));
    services.setCommonComponentsServices((CommonComponentsServices) BeanFactory.instance().getBean("CommonCompsServices"));
    services.setGlobalConfigService((GlobalComponentsService) BeanFactory.instance().getBean("GlobalComponentsService"));
    services.setViewConfigurationService((ViewConfigurationService) BeanFactory.instance().getBean("ViewConfigurationService"));
    return services;
  }
}
