/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.mobile;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.I18n;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.IViewResolver;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.components.search.comps.SearchAllTypesFormViewResolver;
import io.github.jsoagger.jfxcore.components.search.comps.SearchAllTypesResultViewResolver;
import io.github.jsoagger.jfxcore.components.search.controller.MultiformSearchController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchFormController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchResultController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchRootFormController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoMobileSearchBeansProvider {

  @Bean
  @Singleton
  @Named("SearchAllTypesView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllTypesView.xml"},outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search")
  public MultiformSearchController searchAllTypesView() {
    MultiformSearchController p = new MultiformSearchController();
    p.setSearchFormViewResolver(searchAllTypesFormViewResolver());
    p.setSearchResultViewResolver(searchAllTypesResultViewResolver());
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/SearchAllTypesView.json");
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    return p;
  }

  @Bean
  @Named("SearchAllTypesRootFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllTypesRootFormView.xml"},outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search")
  @I18n(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/message.properties","/io/github/jsoagger/jfxcore/demoapp/desktop/search/message.properties"},
  dest = "/io/github/jsoagger/jfxcore/demoapp/mobile/")
  public SearchRootFormController searchAllTypesRootFormView() {
    SearchRootFormController p = new SearchRootFormController();
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/SearchAllTypesRootFormView.json");
    p.setLayoutManager((IViewLayoutManager) Services.getBean("SearchFormLayoutContentManager"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    return p;
  }


  @Bean
  @Named("DemoSearchVehicleFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchVehiclesFormView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search/form")
  public SearchFormController DemoSearchVehicleFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/form/SearchVehiclesFormView.json");
    return p;
  }


  @Bean
  @Named("DemoSearchManufacturerFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchManufacturerFormView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search/form")
  public SearchFormController DemoSearchManufacturerFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/form/SearchManufacturerFormView.json");
    return p;
  }


  @Bean
  @Named("DemoSearchPersonFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchPersonFormView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search/form")
  public SearchFormController DemoSearchPersonFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/form/SearchPersonFormView.json");
    return p;
  }

  @Bean
  @Named("DemoSearchDocumentFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchDocumentFormView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search/form")
  public SearchFormController DemoSearchDocumentFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/form/SearchDocumentFormView.json");
    return p;
  }

  @Bean
  @Named("DemoSearchResultView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/result/DemoSearchResultView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search/result")
  public SearchResultController DemoSearchResultView() {
    SearchResultController p = new SearchResultController();
    p.setLayoutManager((IViewLayoutManager) Services.getBean("SearchResultLayoutManager"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/result/DemoSearchResultView.json");
    return p;
  }


  @Bean
  @Singleton
  @Named("SearchAllTypesResultViewResolver")
  public IViewResolver searchAllTypesResultViewResolver() {
    SearchAllTypesResultViewResolver p = new SearchAllTypesResultViewResolver();
    p.setDefinitionFile("/io/github/jsoagger/jfxcore/demoapp/mobile/searchViews.properties");
    p.init();
    return p;
  }

  @Bean
  @Singleton
  @Named("SearchAllTypesFormViewResolver")
  public IViewResolver searchAllTypesFormViewResolver() {
    SearchAllTypesFormViewResolver p = new SearchAllTypesFormViewResolver();
    p.setDefinitionFile("/io/github/jsoagger/jfxcore/demoapp/mobile/searchViews.properties");
    p.init();
    return p;
  }
}
