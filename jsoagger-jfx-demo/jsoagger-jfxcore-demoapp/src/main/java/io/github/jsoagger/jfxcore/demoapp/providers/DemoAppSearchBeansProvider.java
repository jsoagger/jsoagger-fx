/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.ConvertViewToJson;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.IMultipleEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.model.MultipleSoftTypeToEnumeratedValueLoader;
import io.github.jsoagger.jfxcore.engine.model.SoftTypeToEnumeratedValueLoader;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@BeansProvider
@ConvertViewToJson
public class DemoAppSearchBeansProvider {


  @Bean
  @Named("DemoInstanciableTypesToEnumeratedValueLoader")
  public IMultipleEnumeratedValuesLoader demoInstanciableTypesToEnumeratedValueLoader() {
    MultipleSoftTypeToEnumeratedValueLoader p = new MultipleSoftTypeToEnumeratedValueLoader();
    p.getLoaders().put("Vehicle", "DemoInstanciableVehicleTypeToEnumeratedValueLoader");
    p.getLoaders().put("Document", "DemoInstanciableDocumentTypeToEnumeratedValueLoader");
    p.getLoaders().put("Person", "DemoInstanciablePersonTypeToEnumeratedValueLoader");
    return p;
  }

  @Bean
  @Named("DemoInstanciableVehicleTypeToEnumeratedValueLoader")
  public IEnumeratedValuesLoader demoInstanciableVehicleTypeToEnumeratedValueLoader() {
    SoftTypeToEnumeratedValueLoader p = new SoftTypeToEnumeratedValueLoader();
    p.setGetTypeByPathOperation((IOperation) Services.getBean("GetTypeByPathOperation"));
    p.setOperation((IOperation) Services.getBean("GetInstanciableSoftTypesOperation"));
    p.setIncludeRootTypeInResult(false);
    p.getProperties().put("rootType", "io.github.jsoagger.demo.Type/Vehicles");
    return p;
  }

  @Bean
  @Named("DemoInstanciableDocumentTypeToEnumeratedValueLoader")
  public IEnumeratedValuesLoader demoInstanciableDocumentTypeToEnumeratedValueLoader() {
    SoftTypeToEnumeratedValueLoader p = new SoftTypeToEnumeratedValueLoader();
    p.setGetTypeByPathOperation((IOperation) Services.getBean("GetTypeByPathOperation"));
    p.setOperation((IOperation) Services.getBean("GetInstanciableSoftTypesOperation"));
    p.setIncludeRootTypeInResult(false);
    p.getProperties().put("rootType", "io.github.jsoagger.demo.Type/Document");
    return p;
  }

  @Bean
  @Named("DemoInstanciablePersonTypeToEnumeratedValueLoader")
  public IEnumeratedValuesLoader demoInstanciablePersonTypeToEnumeratedValueLoader() {
    SoftTypeToEnumeratedValueLoader p = new SoftTypeToEnumeratedValueLoader();
    p.setGetTypeByPathOperation((IOperation) Services.getBean("GetTypeByPathOperation"));
    p.setOperation((IOperation) Services.getBean("GetInstanciableSoftTypesOperation"));
    p.setIncludeRootTypeInResult(false);
    p.getProperties().put("rootType", "io.github.jsoagger.demo.Type/Person");
    return p;
  }

  @Bean
  @Named("DemoInstanciableManufacturerTypeToEnumeratedValueLoader")
  public IEnumeratedValuesLoader demoInstanciableManufacturerTypeToEnumeratedValueLoader() {
    SoftTypeToEnumeratedValueLoader p = new SoftTypeToEnumeratedValueLoader();
    p.setGetTypeByPathOperation((IOperation) Services.getBean("GetInstanciableSoftTypesOperation"));
    p.setIncludeRootTypeInResult(false);
    p.getProperties().put("rootType", "io.github.jsoagger.demo.Type/Manufacturer");
    return p;
  }


  @Bean
  @Named("SearchFiltersInSecondaryRSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchFiltersInSecondaryRSContentView.xml"},outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search")
  public RootStructureContentController searchFiltersInSecondaryRSContentView() {
    RootStructureContentController p = new RootStructureContentController();
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/SearchFiltersInSecondaryRSContentView.json");
    return p;
  }

  @Bean
  @Named("SearchFiltersInSecondaryRSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchFiltersInSecondaryRSView.xml"},outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search")
  public RootStructureController searchFiltersInSecondaryRSView() {
    RootStructureController p = new RootStructureController();
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/SearchFiltersInSecondaryRSView.json");
    return p;
  }
}
