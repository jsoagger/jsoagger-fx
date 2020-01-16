/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.desktop;

import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.MenuConfigurationProvider;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.demoapp.comps.DashboardController;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoSmallTableIconPresenter;
import io.github.jsoagger.jfxcore.engine.components.list.impl.DefaultModelListCell;
import io.github.jsoagger.jfxcore.engine.components.menu.PrimaryMenuProvider;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.PrimaryMenuController;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.header.ToolbarController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.ParentResponsiveMatrix;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoAppDesktopViewsProvider {


  @Bean
  @Named("DemoDashboardRSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardRSView.xml"})
  public RootStructureController demoDashboardRSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("CommonDashboardMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardRSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoDashboardRSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardRSContentView.xml"})
  public RootStructureContentController DemoDashboardRSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardRSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoDashboardView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardView.xml"})
  public DashboardController DemoDashboardView() {
    DashboardController rscc = new DashboardController();
    rscc.setMessageSource((MessageSource) Services.getBean("CommonDashboardMessageSource"));
    rscc.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardView.json");
    return rscc;
  }

  @Bean
  @Named("PrimaryMenuView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryMenuView.xml"})
  public PrimaryMenuController mobilePrimaryMenuView() {
    PrimaryMenuController pmc = new PrimaryMenuController();
    pmc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    pmc.setMenuProvider((MenuConfigurationProvider) Services.getBean("PrimaryMenuProvider"));
    pmc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryMenuView.json");
    return pmc;
  }


  @Bean
  @Named("PrimaryMenuViewWithLogo")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryMenuViewWithLogo.xml"})
  public PrimaryMenuController primaryMenuViewWithLogo() {
    PrimaryMenuController pmc = new PrimaryMenuController();
    pmc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    pmc.setMenuProvider((MenuConfigurationProvider) Services.getBean("PrimaryMenuProvider"));
    pmc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryMenuViewWithLogo.json");
    return pmc;
  }

  @Bean
  @Named("PrimaryMenuProvider")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryMenuDefinition.xml"})
  public PrimaryMenuProvider primaryMenuProvider() {
    PrimaryMenuProvider pmc = new PrimaryMenuProvider();
    pmc.setPrimaryMenu("/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryMenuDefinition.json");
    return pmc;
  }


  @Bean
  @Named("PrimaryHeaderToolbarView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryHeaderToolbarView.xml",
  "/io/github/jsoagger/jfxcore/engine/controller/HeaderToolbar.xml"})
  public ToolbarController primaryHeaderToolbarView() {
    ToolbarController tpv = new ToolbarController();
    tpv.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    tpv.setResponsiveMatrix((ParentResponsiveMatrix) Services.getBean("DemoPrimaryHeaderToolbarResponsiveMatrix"));
    tpv.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    tpv.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/rootstructure/PrimaryHeaderToolbarView.json");
    return tpv;
  }

  @Bean
  @Named("DemoSmallTableIconPresenter")
  public DemoSmallTableIconPresenter DemoSmallTableIconPresenter() {
    DemoSmallTableIconPresenter ic = new DemoSmallTableIconPresenter();
    return ic;
  }

  @Bean
  @Singleton
  @Named("DemoPrimaryHeaderToolbarResponsiveMatrix")
  public ParentResponsiveMatrix DemoPrimaryHeaderToolbarResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("DemoPrimaryHeaderToolbarResponsiveMatrixDefinition");
    ParentResponsiveMatrix ic = new ParentResponsiveMatrix(matrix);
    return ic;
  }

  @Bean
  @Singleton
  @Named("DemoPrimaryHeaderToolbarResponsiveMatrixDefinition")
  public List<String> DemoPrimaryHeaderToolbarResponsiveMatrixDefinition(){
    List<String> d = new ArrayList<>();
    d.add("0:830#0.10:0.:0.90#:hide:");
    d.add("830#0.20:0.:0.80#:hide:");
    return d;
  }





  @Bean
  @Named("DemoPreferencesRSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/preferences/DemoPreferencesRSView.xml"})
  public RootStructureController DemoPreferencesRSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoPreferencesMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/preferences/DemoPreferencesRSView.json");
    return mobileLayoutRSView;
  }

  @Bean
  @Named("DemoSystemPreferencesRootGroupListView")
  @View(locations= {"/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesRootGroupListView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/preferences/DemoSystemPreferencesRootGroupListView.xml"},
  outputFileName = "DemoSystemPreferencesRootGroupListView", outputFilePath ="/io/github/jsoagger/jfxcore/demoapp/desktop/preferences/")
  public StandardController DemoSystemPreferencesRootGroupListView() {
    StandardController mobileLayoutRSView = new StandardController();
    mobileLayoutRSView.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoPreferencesMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/preferences/DemoSystemPreferencesRootGroupListView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoPreferencesRSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/preferences/DemoPreferencesRSContentView.xml"})
  public RootStructureContentController DemoPreferencesRSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/preferences/DemoPreferencesRSContentView.json");
    return rscc;
  }






  @Bean
  @Named("DemoAdministrationRSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoAdministrationRSView.xml"})
  public RootStructureController DemoAdministrationRSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoAdministrationMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoAdministrationRSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoAdministrationRSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoAdministrationRSContentView.xml"})
  public RootStructureContentController DemoAdministrationRSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoAdministrationRSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoAdministrationView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoAdministrationView.xml"})
  public StandardController DemoAdministrationView() {
    StandardController rscc = new StandardController();
    rscc.setMessageSource((MessageSource) Services.getBean("DemoAdministrationMessageSource"));
    rscc.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedViewLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoAdministrationView.json");
    return rscc;
  }


  @Bean
  @Named("DemoEmptyAdministrationView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoEmptyAdministrationView.xml"})
  public StandardController DemoEmptyAdministrationView() {
    StandardController rscc = new StandardController();
    rscc.setMessageSource((MessageSource) Services.getBean("DemoAdministrationMessageSource"));
    rscc.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoEmptyAdministrationView.json");
    return rscc;
  }

  @Bean
  @Named("DemoAdminItemCellFactory")
  public DefaultModelListCell DemoAdminItemCellFactory() {
    DefaultModelListCell c = new DefaultModelListCell();
    return c;
  }

  @Bean
  @Named("DemoAdminItemListCellPresenter")
  public MultiPresenterFactory DemoAdminItemListCellPresenter() {
    MultiPresenterFactory c = new MultiPresenterFactory();
    c.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    c.setIconPresenter((ModelIconPresenter) Services.getBean("AdminStaticIconPresenter"));
    return c;
  }
}
