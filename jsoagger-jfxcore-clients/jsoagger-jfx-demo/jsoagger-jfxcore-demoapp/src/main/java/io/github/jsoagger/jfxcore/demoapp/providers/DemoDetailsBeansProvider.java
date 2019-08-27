/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.detailsview.IDetailsViewHeader;
import io.github.jsoagger.jfxcore.api.detailsview.IMaximizedDetailsView;
import io.github.jsoagger.jfxcore.api.presenter.ModelHeaderIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.services.LocalComponentsService;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoDetailsViewHeaderPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoMaximizedDetailsViewHeader;
import io.github.jsoagger.jfxcore.demoapp.comps.details.FullIdentityHeaderPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.details.FullIdentityPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.details.StubStructureContentModelReferenceProvider;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.DetailsViewController;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.layout.MaximizedDetailsView;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.layout.MaximizedDetailsViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.providers.integration.JsonLocalCompsService;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoDetailsBeansProvider {

  @Bean
  @Singleton
  @Named("DetailsViewMessageSource")
  public MessageSource DemoPreferencesMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.details.message");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    return messageSource;
  }

  @Bean
  @Named("DetailsViewCommonComponents")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/DetailsViewComponents.xml"})
  public JsonLocalCompsService DetailsViewCommonComponents() {
    JsonLocalCompsService c = new JsonLocalCompsService();
    c.getConfigurationFiles().add("/io/github/jsoagger/jfxcore/demoapp/desktop/details/DetailsViewComponents.json");
    return c;
  }


  @Bean
  @Named("RootDetailsView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/DetailsView.xml"})
  public DetailsViewController RootDetailsView() {
    DetailsViewController c = new DetailsViewController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("MaximizedDetailsViewRootLayoutManager"));
    c.setModelProvider((IModelProvider) Services.getBean("StubStructureContentModelReferenceProvider"));
    c.setMaximizedDetailsView("MaximizedDetailsViewController");
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/DetailsView.json");
    return c;
  }

  @Bean
  @Named("IdentityLessRootDetailsView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/DetailsView.xml"})
  public DetailsViewController IdentityLessRootDetailsView() {
    DetailsViewController c = new DetailsViewController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("MaximizedDetailsViewRootLayoutManager"));
    c.setModelProvider((IModelProvider) Services.getBean("StubStructureContentModelReferenceProvider"));
    c.hideIdentityProperty().set(true);
    c.setMaximizedDetailsView("MaximizedDetailsViewController");
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/DetailsView.json");
    return c;
  }


  @Bean
  @Named("MaximizedDetailsViewController")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/MaximizedDetailsView.xml"})
  public MaximizedDetailsViewController MaximizedDetailsViewController() {
    MaximizedDetailsViewController c = new MaximizedDetailsViewController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setDetailsView((IMaximizedDetailsView) Services.getBean("DemoMaximizedDetailsViewImpl"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("MaximizedDetailsViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/MaximizedDetailsView.json");
    return c;
  }


  @Bean
  @Named("DemoMaximizedDetailsViewImpl")
  public MaximizedDetailsView DemoMaximizedDetailsViewImpl() {
    MaximizedDetailsView c = new MaximizedDetailsView();
    c.setHeader((IDetailsViewHeader) Services.getBean("DemoMaximizedDetailsViewHeader"));
    return c;
  }


  @Bean
  @Named("DemoMaximizedDetailsViewHeader")
  public DemoMaximizedDetailsViewHeader DemoMaximizedDetailsViewHeader() {
    DemoMaximizedDetailsViewHeader c = new DemoMaximizedDetailsViewHeader();
    c.setPresenter((MultiPresenterFactory) Services.getBean("DemoDetailsViewHeaderPresenter"));
    return c;
  }


  @Bean
  @Named("DemoDetailsViewHeaderPresenter")
  public DemoDetailsViewHeaderPresenter DemoDetailsViewHeaderPresenter() {
    DemoDetailsViewHeaderPresenter c = new DemoDetailsViewHeaderPresenter();
    c.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("FullIdentityPresenter"));
    c.setHeaderIdentityProvider((ModelHeaderIdentityPresenter) Services.getBean("FullIdentityHeaderPresenter"));
    return c;
  }



  @Bean
  @Named("FullIdentityHeaderPresenter")
  public FullIdentityHeaderPresenter FullIdentityHeaderPresenter() {
    FullIdentityHeaderPresenter c = new FullIdentityHeaderPresenter();
    return c;
  }

  @Bean
  @Named("FullIdentityPresenter")
  public FullIdentityPresenter FullIdentityPresenter() {
    FullIdentityPresenter c = new FullIdentityPresenter();
    return c;
  }


  @Bean
  @Named("StubStructureContentModelReferenceProvider")
  public StubStructureContentModelReferenceProvider StubStructureContentModelReferenceProvider() {
    StubStructureContentModelReferenceProvider c = new StubStructureContentModelReferenceProvider();
    return c;
  }



  @Bean
  @Named("DemoMaximizedInformationTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedInformationTabView.xml"})
  public StandardController DemoMaximizedInformationTabView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTabPaneLayoutManager"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedInformationTabView.json");
    return c;
  }

  @Bean
  @Named("DemoSelfInformationTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedInformationTabView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfInformationTabView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/", outputFileName = "DemoSelfInformationTabView")
  public StandardController DemoSelfInformationTabView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedInformationTabView.json");
    return c;
  }



  @Bean
  @Named("DemoMaximizedIllustrationView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedIllustrationView.xml"})
  public StandardController DemoMaximizedIllustrationView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTabPaneLayoutManager"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedIllustrationView.json");
    return c;
  }

  @Bean
  @Named("DemoSelfIllustrationTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedIllustrationView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfIllustrationTabView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self", outputFileName = "DemoSelfIllustrationTabView")
  public StandardController DemoSelfIllustrationTabView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfIllustrationTabView.json");
    return c;
  }



  @Bean
  @Named("DemoMaquette3DContentView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette3DContentView.xml"})
  public StandardController DemoMaquette3DContentView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTabPaneLayoutManager"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette3DContentView.json");
    return c;
  }

  @Bean
  @Named("DemoSelfMaquette3DTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette3DContentView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfMaquette3DTabView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self", outputFileName = "DemoSelfIllustrationTabView")
  public StandardController DemoSelfMaquette3DTabView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfMaquette3DTabView.json");
    return c;
  }


  @Bean
  @Named("DemoMaquette2DContentView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette2DContentView.xml"})
  public StandardController DemoMaquette2DContentView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTabPaneLayoutManager"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette2DContentView.json");
    return c;
  }

  @Bean
  @Named("DemoSelfMaquette2DTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette2DContentView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfMaquette2DTabView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self", outputFileName = "DemoSelfIllustrationTabView")
  public StandardController DemoSelfMaquette2DTabView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DetailsViewMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    c.setCommonComponents((LocalComponentsService) Services.getBean("DetailsViewCommonComponents"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfMaquette2DTabView.json");
    return c;
  }
}
