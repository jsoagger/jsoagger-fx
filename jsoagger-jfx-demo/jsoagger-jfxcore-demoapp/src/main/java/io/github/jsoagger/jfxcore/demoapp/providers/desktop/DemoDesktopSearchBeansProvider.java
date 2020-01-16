/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.desktop;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.IViewResolver;
import io.github.jsoagger.jfxcore.api.services.LocalComponentsService;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.components.search.comps.SearchAllTypesFormViewResolver;
import io.github.jsoagger.jfxcore.components.search.comps.SearchAllTypesResultViewResolver;
import io.github.jsoagger.jfxcore.components.search.controller.MultiformSearchController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchFormController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchResultController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchRootFormController;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.header.ToolbarController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoDesktopSearchBeansProvider {

  @Bean
  @Singleton
  @Named("SearchMessageSource")
  public MessageSource SearchMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.search.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.message");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    return messageSource;
  }

  @Bean
  @Named("SearchAllRSContentView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllRSContentView.xml"})
  public RootStructureContentController SearchAllRSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllRSContentView.json");
    return rscc;
  }


  @Bean
  @Named("SearchAllRSView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllRSView.xml"})
  public DoubleHeaderRootStructureController SearchAllRSView() {
    DoubleHeaderRootStructureController rscc = new DoubleHeaderRootStructureController();
    rscc.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    rscc.setCommonComponents((LocalComponentsService) Services.getBean("SearchCommonComponents"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllRSView.json");
    return rscc;
  }


  @Bean
  @Named("SearchablePrimaryHeaderToolbarView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchPrimaryHeaderToolbarView.xml"
      ,"/io/github/jsoagger/jfxcore/engine/controller/HeaderToolbar.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/desktop/search/")
  public ToolbarController SearchablePrimaryHeaderToolbarView() {
    ToolbarController t = new ToolbarController();
    t.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("SearchablePrimaryHeaderToolbarResponsiveMatrix"));
    t.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    t.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchPrimaryHeaderToolbarView.json");
    return t;
  }


  @Bean
  @Named("SearchSecondaryHeaderToolbarView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchSecondaryHeaderToolbarView.xml"})
  public ToolbarController SearchSecondaryHeaderToolbarView() {
    ToolbarController t = new ToolbarController();
    t.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    t.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchSecondaryHeaderToolbarView.json");
    return t;
  }






  @Bean
  @Singleton
  @Named("SearchAllTypesView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllTypesView.xml"})
  public MultiformSearchController searchAllTypesView() {
    MultiformSearchController p = new MultiformSearchController();
    p.setSearchFormViewResolver(searchAllTypesFormViewResolver());
    p.setSearchResultViewResolver(searchAllTypesResultViewResolver());
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllTypesView.json");
    p.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    return p;
  }

  @Bean
  @Named("SearchAllTypesRootFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllTypesRootFormView.xml"})
  public SearchRootFormController searchAllTypesRootFormView() {
    SearchRootFormController p = new SearchRootFormController();
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/SearchAllTypesRootFormView.json");
    p.setLayoutManager((IViewLayoutManager) Services.getBean("SearchFormLayoutContentManager"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    return p;
  }


  @Bean
  @Named("DemoSearchVehicleFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchVehiclesFormView.xml"})
  public SearchFormController DemoSearchVehicleFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchVehiclesFormView.json");
    return p;
  }


  @Bean
  @Named("DemoSearchManufacturerFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchManufacturerFormView.xml"})
  public SearchFormController DemoSearchManufacturerFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchManufacturerFormView.json");
    return p;
  }


  @Bean
  @Named("DemoSearchPersonFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchPersonFormView.xml"})
  public SearchFormController DemoSearchPersonFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchPersonFormView.json");
    return p;
  }

  @Bean
  @Named("DemoSearchDocumentFormView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchDocumentFormView.xml"})
  public SearchFormController DemoSearchDocumentFormView() {
    SearchFormController p = new SearchFormController();
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/form/SearchDocumentFormView.json");
    return p;
  }

  @Bean
  @Named("DemoSearchResultView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/search/result/DemoSearchResultView.xml"})
  public SearchResultController DemoSearchResultView() {
    SearchResultController p = new SearchResultController();
    p.setLayoutManager((IViewLayoutManager) Services.getBean("SearchResultLayoutManager"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("SearchMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/search/result/DemoSearchResultView.json");
    return p;
  }


  @Bean
  @Singleton
  @Named("SearchAllTypesResultViewResolver")
  public IViewResolver searchAllTypesResultViewResolver() {
    SearchAllTypesResultViewResolver p = new SearchAllTypesResultViewResolver();
    p.setDefinitionFile("/io/github/jsoagger/jfxcore/demoapp/desktop/searchViews.properties");
    p.init();
    return p;
  }

  @Bean
  @Singleton
  @Named("SearchAllTypesFormViewResolver")
  public IViewResolver searchAllTypesFormViewResolver() {
    SearchAllTypesFormViewResolver p = new SearchAllTypesFormViewResolver();
    p.setDefinitionFile("/io/github/jsoagger/jfxcore/demoapp/desktop/searchViews.properties");
    p.init();
    return p;
  }
}
