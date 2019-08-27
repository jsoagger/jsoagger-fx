/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.mobile;

import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Eager;
import io.github.jsoagger.core.ioc.api.annotations.I18n;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.MenuConfigurationProvider;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.components.search.comps.SearchAllTypesFormViewResolver;
import io.github.jsoagger.jfxcore.components.search.controller.SearchResultController;
import io.github.jsoagger.jfxcore.demoapp.comps.DashboardController;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoDialogController;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoMobileModelFlowThumbPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoModelStatusPresenter;
import io.github.jsoagger.jfxcore.engine.components.menu.PrimaryMenuProvider;
import io.github.jsoagger.jfxcore.engine.components.presenter.FlowItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.HorizontalFlowItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.PrimaryMenuController;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullFlowViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.header.ToolbarController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.ParentResponsiveMatrix;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoAppMobileViewsProvider {

  @Bean
  @Named("DemoMobileSearchResultView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/search/result/DemoMobileSearchResultView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/search/result")
  public SearchResultController DemoSearchResultView() {
    SearchResultController p = new SearchResultController();
    p.setLayoutManager((IViewLayoutManager) Services.getBean("SearchResultLayoutManager"));
    p.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    p.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/search/result/DemoMobileSearchResultView.json");
    return p;
  }

  @Bean
  @Named("DemoSelfIllustrationTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaximizedIllustrationView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfIllustrationTabView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/mobile/details")
  public StandardController DemoSelfIllustrationTabView() {
    StandardController pmc = new StandardController();
    pmc.setLayoutManager((IViewLayoutManager) Services.getBean("FullTabPaneLayoutManager"));
    pmc.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    pmc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    pmc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/details/DemoMaximizedIllustrationView.json");
    return pmc;
  }

  @Bean
  @Named("DemoSelfMaquette3DTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette3DContentView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfMaquette3DTabView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/mobile/details")
  public StandardViewController DemoSelfMaquette3DTabView() {
    StandardController pmc = new StandardController();
    pmc.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    pmc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    pmc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/details/DemoMaquette3DContentView.json");
    return pmc;
  }

  @Bean
  @Named("DemoSelfMaquette2DTabView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/details/maximized/DemoMaquette2DContentView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/details/self/DemoSelfMaquette2DTabView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/mobile/details")
  public StandardViewController DemoMaquette2DContentView() {
    StandardController pmc = new StandardController();
    pmc.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    pmc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    pmc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/details/DemoMaquette2DContentView.json");
    return pmc;
  }



  @Bean
  @Named("DemoMobileListExample3View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/flow/DemoMobileListExample3View.xml"})
  public FullFlowViewController DemoMobileListExample3View() {
    FullFlowViewController controller = new FullFlowViewController();
    controller.setLayoutManager((IViewLayoutManager) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManager"));
    controller.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    controller.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    controller.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/flow/DemoMobileListExample3View.json");
    return controller;
  }

  @Bean
  @Named("DemoMobileListExample4View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/flow/DemoMobileListExample4View.xml"})
  public FullFlowViewController DemoMobileListExample4View() {
    FullFlowViewController controller = new FullFlowViewController();
    controller.setLayoutManager((IViewLayoutManager) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManager"));
    controller.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    controller.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    controller.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/flow/DemoMobileListExample4View.json");
    return controller;
  }

  @Bean
  @Named("DemoMobileListExample5View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/flow/DemoMobileListExample5View.xml"})
  public FullFlowViewController DemoMobileListExample5View() {
    FullFlowViewController controller = new FullFlowViewController();
    controller.setLayoutManager((IViewLayoutManager) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManager"));
    controller.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    controller.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    controller.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/flow/DemoMobileListExample5View.json");
    return controller;
  }

  @Bean
  @Named("DialogContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/dialog/DialogContentView.xml"}, outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/dialog")
  @I18n(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/dialog/message.properties"}, dest="/io/github/jsoagger/jfxcore/demoapp/mobile/dialog/")
  public DemoDialogController DialogContentView() {
    DemoDialogController p = new DemoDialogController();
    p.setMessageSource((MessageSource) Services.getBean("DialogMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/dialog/DialogContentView.json");
    return p;
  }


  @Bean
  @Named("DemoMobileContentFlowView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/list/DemoMobileListView.xml"})
  public FullFlowViewController mobileLayoutRSView() {
    FullFlowViewController controller = new FullFlowViewController();
    controller.setLayoutManager((IViewLayoutManager) BeanFactory.instance().getBean("DemoMobileContentFlowViewLayoutManager"));
    controller.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    controller.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/list/DemoMobileListView.json");
    return controller;
  }


  @Bean
  @Named("DemoMobileMenuView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/menu/DemoMobileMenuView.xml"})
  @Eager
  @Singleton
  public StandardController DemoMobileMenuView() {
    StandardController pmc = new StandardController();
    pmc.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    pmc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    pmc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/menu/DemoMobileMenuView.json");
    return pmc;
  }


  @Bean
  @Named("MobilePrimaryMenuView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/rootstructure/MobilePrimaryMenuView.xml"})
  public PrimaryMenuController mobilePrimaryMenuView() {
    PrimaryMenuController pmc = new PrimaryMenuController();
    pmc.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    pmc.setMenuProvider((MenuConfigurationProvider) Services.getBean("MobilePrimaryMenuProvider"));
    pmc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/rootstructure/MobilePrimaryMenuView.json");
    return pmc;
  }

  @Bean
  @Named("MobilePrimaryMenuProvider")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/rootstructure/MobilePrimaryMenuDefinition.xml"})
  public PrimaryMenuProvider primaryMenuProvider() {
    PrimaryMenuProvider pmc = new PrimaryMenuProvider();
    pmc.setPrimaryMenu("/io/github/jsoagger/jfxcore/demoapp/mobile/rootstructure/MobilePrimaryMenuDefinition.json");
    return pmc;
  }


  @Bean
  @Named("MobilePrimaryHeaderToolbarView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/mobile/rootstructure/MobilePrimaryHeaderToolbarView.xml",
  "/io/github/jsoagger/jfxcore/engine/controller/HeaderToolbar.xml"}, outputFileName = "MobilePrimaryHeaderToolbarView")
  public ToolbarController primaryHeaderToolbarView() {
    ToolbarController tpv = new ToolbarController();
    tpv.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    tpv.setResponsiveMatrix((ParentResponsiveMatrix) Services.getBean("MobilePrimaryHeaderToolbarResponsiveMatrix"));
    tpv.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    tpv.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/rootstructure/MobilePrimaryHeaderToolbarView.json");
    return tpv;
  }


  @Bean
  @Named("DemoMobileContentFlowView")
  @View(locations = "/io/github/jsoagger/jfxcore/demoapp/mobile/list/DemoMobileListView.xml")
  public FullFlowViewController DemoMobileContentFlowView() {
    FullFlowViewController ctrl  = new FullFlowViewController();
    ctrl.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    ctrl.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    ctrl.setLayoutManager((IViewLayoutManager) Services.getBean("DemoMobileContentFlowViewLayoutManager"));
    return ctrl;
  }


  @Bean
  @Named("MobileLayoutRSContentView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/mobile/MobileLayoutRSContentView.xml"})
  public RootStructureContentController MobileLayoutRSContentView() {
    RootStructureContentController ctrl  = new RootStructureContentController();
    ctrl.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    ctrl.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/MobileLayoutRSContentView.json");
    return ctrl;
  }

  @Bean
  @Named("MobileLayoutRSView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/mobile/MobileLayoutRSView.xml"})
  public DoubleHeaderRootStructureController MobileLayoutRSView() {
    DoubleHeaderRootStructureController ctrl  = new DoubleHeaderRootStructureController();
    ctrl.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    ctrl.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    ctrl.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/MobileLayoutRSView.json");
    return ctrl;
  }

  @Bean
  @Named("MobileRootTabPaneView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/mobile/MobileRootTabPaneView.xml"})
  public StandardTabPaneController MobileRootTabPaneView() {
    StandardTabPaneController ctrl  = new StandardTabPaneController();
    ctrl.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    ctrl.setMessageSource((MessageSource) Services.getBean("MobileMessageSource"));
    ctrl.setLayoutManager((IViewLayoutManager) Services.getBean("FullTabPaneLayoutManager"));
    ctrl.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/MobileRootTabPaneView.json");
    return ctrl;
  }


  @Bean
  @Named("MobileDashboardView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/MobileDashboardView.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard", outputFileName = "MobileDashboardView")
  public DashboardController MobileDashboardView() {
    DashboardController ctrl  = new DashboardController();
    ctrl.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    ctrl.setMessageSource((MessageSource) Services.getBean("DemoDashboardMessageSource"));
    ctrl.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/MobileDashboardView.json");
    return ctrl;
  }

  @Bean
  @Named("DemoMobileToValidateTableView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/DemoMobileToValidateTableView.xml"})
  public FullTableViewController DemoMobileToValidateTableView() {
    FullTableViewController ctrl  = new FullTableViewController();
    ctrl.setMessageSource((MessageSource) Services.getBean("DemoDashboardMessageSource"));
    ctrl.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    ctrl.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    ctrl.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    ctrl.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/DemoMobileToValidateTableView.json");
    return ctrl;
  }

  @Bean
  @Named("DemoMobileTasksTableView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/DemoMobileToValidateTableView.xml"})
  public FullTableViewController DemoMobileTasksTableView() {
    FullTableViewController ctrl  = new FullTableViewController();
    ctrl.setMessageSource((MessageSource) Services.getBean("DemoDashboardMessageSource"));
    ctrl.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    ctrl.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    ctrl.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    ctrl.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/DemoMobileTasksTableView.json");
    return ctrl;
  }

  @Bean
  @Named("DemoMobileWipTableView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/DemoMobileWipTableView.xml"})
  public FullTableViewController DemoMobileWipTableView() {
    FullTableViewController ctrl  = new FullTableViewController();
    ctrl.setMessageSource((MessageSource) Services.getBean("DemoDashboardMessageSource"));
    ctrl.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    ctrl.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    ctrl.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    ctrl.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/mobile/dashboard/DemoMobileWipTableView.json");
    return ctrl;
  }



  @Bean
  @Named("DemoMobileContentFlowViewLayoutManager")
  public FullFlowContentLayoutManager DemoMobileContentFlowViewLayoutManager() {
    FullFlowContentLayoutManager r = new FullFlowContentLayoutManager();
    r.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("DemoMobileContentFlowViewLayoutManagerResponsiveMatrix"));
    return r;
  }

  @Bean
  @Named("DemoMobileContentFlowViewLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix DemoMobileContentFlowViewLayoutManagerResponsiveMatrix() {
    List<String> d = DemoMobileContentFlowViewLayoutManagerResponsiveMatrixDefinition();
    ParentResponsiveMatrix matrix = new ParentResponsiveMatrix(d);
    return matrix;
  }

  @Bean
  @Named("DemoMobileContentFlowViewLayoutManagerResponsiveMatrixDefinition")
  public List<String> DemoMobileContentFlowViewLayoutManagerResponsiveMatrixDefinition(){
    List<String> c = new ArrayList<String>();
    c.add("0#0:0.98:0#hide::hide");
    return c;
  }


  @Bean
  @Named("SearchAllTypesFormViewResolver")
  public SearchAllTypesFormViewResolver searchAllTypesFormViewResolver() {
    SearchAllTypesFormViewResolver r = new SearchAllTypesFormViewResolver();
    r.setDefinitionFile("/io/github/jsoagger/jfxcore/demoapp/mobile/searchViews.properties");
    return r;
  }

  @Bean
  @Named("SearchAllTypesResultViewResolver")
  public SearchAllTypesFormViewResolver SearchAllTypesResultViewResolver() {
    SearchAllTypesFormViewResolver r = new SearchAllTypesFormViewResolver();
    r.setDefinitionFile("/io/github/jsoagger/jfxcore/demoapp/mobile/searchViews.properties");
    return r;
  }


  @Bean
  @Named("DemoModelStatusPresenter")
  public DemoModelStatusPresenter DemoModelStatusPresenter() {
    return new DemoModelStatusPresenter();
  }

  @Bean
  @Named("DemoMobileModelFlowThumbPresenter")
  public DemoMobileModelFlowThumbPresenter DemoMobileModelFlowThumbPresenter() {
    return new DemoMobileModelFlowThumbPresenter();
  }


  @Bean
  @Named("DemoMobileModelLargeFlowThumbPresenter")
  public DemoMobileModelFlowThumbPresenter DemoMobileModelLargeFlowThumbPresenter() {
    DemoMobileModelFlowThumbPresenter f  = new DemoMobileModelFlowThumbPresenter();
    f.setWidth(300);
    f.setHeight(200);
    return f;
  }

  @Bean
  @Named("DemoMobileHorizontalFlowItemPresenter")
  public FlowItemPresenterFactory DemoMobileHorizontalFlowItemPresenter() {
    HorizontalFlowItemPresenterFactory f  = new HorizontalFlowItemPresenterFactory();
    f.setIconPresenter((ModelIconPresenter) Services.getBean("HorizontalFlowItemPresenterFactory"));
    f.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("DemoFlowItemIdentityPresenter"));
    f.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return f;
  }

  @Bean
  @Named("DemoMobileLargeFlowItemPresenter")
  public FlowItemPresenterFactory DemoMobileLargeFlowItemPresenter() {
    FlowItemPresenterFactory f  = new FlowItemPresenterFactory();
    f.setIconPresenter((ModelIconPresenter) Services.getBean("DemoMobileModelLargeFlowThumbPresenter"));
    f.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("DemoFlowItemIdentityPresenter"));
    f.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return f;
  }



  @Bean
  @Named("MobilePrimaryHeaderToolbarResponsiveMatrix")
  public ParentResponsiveMatrix MobilePrimaryHeaderToolbarResponsiveMatrix() {
    List<String> d = MobilePrimaryHeaderToolbarResponsiveMatrixDefinition();
    ParentResponsiveMatrix matrix = new ParentResponsiveMatrix(d);
    return matrix;
  }

  @Bean
  @Named("MobilePrimaryHeaderToolbarResponsiveMatrixDefinition")
  public List<String> MobilePrimaryHeaderToolbarResponsiveMatrixDefinition(){
    List<String> c = new ArrayList<String>();
    c.add("0#0.15:0.85:0.0#::hide");
    return c;
  }



  @Bean
  @Named("DemoScrolLessCenteredFullFlowLayoutManager")
  public FullFlowContentLayoutManager DemoScrolLessCenteredFullFlowLayoutManager() {
    FullFlowContentLayoutManager lyt = new FullFlowContentLayoutManager();
    lyt.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManagerMatrix"));
    return lyt;
  }

  @Bean
  @Named("DemoScrolLessCenteredFullFlowLayoutManagerMatrix")
  public ParentResponsiveMatrix DemoScrolLessCenteredFullFlowLayoutManagerMatrix() {
    List<String> matrix = (List<String>) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManagerMatrixDefinition");
    ParentResponsiveMatrix lyt = new ParentResponsiveMatrix(matrix);
    return lyt;
  }

  @Bean
  @Singleton
  @Named("DemoScrolLessCenteredFullFlowLayoutManagerMatrixDefinition")
  public List<String> DemoScrolLessCenteredFullFlowLayoutManagerMatrixDefinition() {
    List<String> data = new ArrayList<>();
    data.add("0#0:1:0#hide::hide");
    return data;
  }
}
