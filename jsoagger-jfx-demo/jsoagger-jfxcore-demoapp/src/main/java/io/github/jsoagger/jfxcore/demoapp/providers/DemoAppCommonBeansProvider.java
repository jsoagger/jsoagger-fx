/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers;


import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.demoapp.DemoTablesRowCriteriaSetter;
import io.github.jsoagger.jfxcore.demoapp.action.CreateDemoSavedSearchAction;
import io.github.jsoagger.jfxcore.demoapp.comps.ContextInputView;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoAppRootMenuHeader;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoBarChart;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoDashboardItem;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoDashboardPercentItem;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoFlowItemIdentityPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoFlowItemPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoMobileModelFlowThumbPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoModelFlowThumbPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoModelStatusPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoPieChart;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoStaticModelFlowCell;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoStaticParentItemFlowCell;
import io.github.jsoagger.jfxcore.demoapp.map.DemoMapController;
import io.github.jsoagger.jfxcore.demoapp.tableaction.BeginTaskFromTableRowAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.CloseTaskFromTableRowAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.CloseTasksButtonDemoAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.CopyButtonDemoAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.DeleteButtonDemoAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.ExportButtonDemoAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.PrintButtonDemoAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.RefreshButtonDemoAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.ReopenTaskFromTableRowAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.ValidateTaskFromTableRowAction;
import io.github.jsoagger.jfxcore.demoapp.tableaction.ValidateTasksButtonDemoAction;
import io.github.jsoagger.jfxcore.engine.components.list.impl.DefaultModelListCell;
import io.github.jsoagger.jfxcore.engine.components.presenter.FlowItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.HorizontalFlowItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.LargeItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.table.TableStructureFilter;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.providers.integration.JsonLocalCompsService;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoAppCommonBeansProvider {

  @Bean
  @Named("DemoMapRSContentView")
  @View(locations = "/io/github/jsoagger/jfxcore/demoapp/desktop/map/DemoMapRSContentView.xml")
  public RootStructureContentController DemoMapRSContentView() {
    RootStructureContentController s = new RootStructureContentController();
    s.setMessageSource((MessageSource) Services.getBean("DemoAdministrationMessageSource"));
    s.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/desktop/map/DemoMapRSContentView.json");
    return s;
  }


  @Bean
  @Named("DemoMapRSView")
  @View(locations = "/io/github/jsoagger/jfxcore/demoapp/desktop/map/DemoMapRSView.xml")
  public RootStructureController DemoMapRSView() {
    RootStructureController s = new RootStructureController();
    s.setMessageSource((MessageSource) Services.getBean("DemoAdministrationMessageSource"));
    s.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    s.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/desktop/map/DemoMapRSView.json");
    return s;
  }


  @Bean
  @Named("DemoMapView")
  @View(locations = "/io/github/jsoagger/jfxcore/demoapp/desktop/map/DemoMapView.xml")
  public DemoMapController DemoMapView() {
    DemoMapController s = new DemoMapController();
    s.setMessageSource((MessageSource) Services.getBean("DemoAdministrationMessageSource"));
    s.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    s.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/desktop/map/DemoMapView.json");
    return s;
  }


  @Bean
  @Singleton
  @Named("DemoTablesRowCriteriaSetter")
  public DemoTablesRowCriteriaSetter demoTablesRowCriteriaSetter() {
    DemoTablesRowCriteriaSetter s = new DemoTablesRowCriteriaSetter();
    return s;
  }


  @Bean
  @Singleton
  @Named("SearchCommonComponents")
  public JsonLocalCompsService SearchCommonComponents() {
    JsonLocalCompsService comps = new JsonLocalCompsService();
    comps.getConfigurationFiles().add("/io/github/jsoagger/jfxcore/demoapp/desktop/search-components.xml");
    comps.getConfigurationFiles().add("/io/github/jsoagger/jfxcore/demoapp/desktop/element-components.xml");
    return comps;
  }

  @Bean
  @Singleton
  @Named("AllDemoCommonComponents")
  public JsonLocalCompsService AllDemoCommonComponents() {
    JsonLocalCompsService comps = new JsonLocalCompsService();
    comps.getConfigurationFiles().add("/io/github/jsoagger/jfxcore/demoapp/desktop/search-components.xml");
    comps.getConfigurationFiles().add("/io/github/jsoagger/jfxcore/demoapp/desktop/element-components.xml");
    comps.getConfigurationFiles().add("/io/github/jsoagger/jfxcore/demoapp/desktop/common-components.xml");
    return comps;
  }



  @Bean
  @Singleton
  @Named("DemoPreferencesMessageSource")
  public MessageSource DemoPreferencesMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.preferences.message");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    return messageSource;
  }

  @Bean
  @Singleton
  @Named("DemoExamplesTableMessageSource")
  public MessageSource DemoExamplesTableMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.table.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.message");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    return messageSource;
  }


  @Bean
  @Singleton
  @Named("DemoAdministrationMessageSource")
  public MessageSource DemoAdministrationMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.administration.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.map.message");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    return messageSource;
  }


  @Bean
  @Singleton
  @Named("FlowMessageSource")
  public MessageSource FlowMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.flow.message");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    return messageSource;
  }

  @Bean
  @Singleton
  @Named("PreferencesMessageSource")
  public MessageSource PreferencesMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.common.dashboard.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.preferences.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.mobile.preferences.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.message");
    return messageSource;
  }

  @Bean
  @Singleton
  @Named("CommonDashboardMessageSource")
  public MessageSource CommonDashboardMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.common.dashboard.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.table.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.details.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.dashboard.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.dialog.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.components.preferences.message");

    return messageSource;
  }

  @Bean
  @Singleton
  @Named("RooStructureMessageSource")
  public MessageSource RooStructureMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.rootstructure.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.engine.controller.localization");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.details.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.dashboard.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.dialog.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.components.preferences.message");
    messageSource.getBasenames().add("i18n.generalBundle");
    return messageSource;
  }

  @Bean
  @Singleton
  @Named("MobileMessageSource")
  public MessageSource MobileMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.mobile.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.rootstructure.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.details.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.dashboard.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.dialog.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.components.preferences.message");
    return messageSource;
  }



  @Bean
  @Named("DemoTableByTitleFilter")
  public TableStructureFilter DemoTableByTitleFilter() {
    TableStructureFilter f = new TableStructureFilter();
    f.setMasterAttributePath("attributes.title");
    return f;
  }

  @Bean
  @Named("DemoTableByModelFilter")
  public TableStructureFilter DemoTableByModelFilter() {
    TableStructureFilter f = new TableStructureFilter();
    f.setMasterAttributePath("attributes.model");
    return f;
  }

  @Bean
  @Named("DemoFlowItemPresenter")
  public FlowItemPresenterFactory DemoFlowItemPresenter() {
    FlowItemPresenterFactory factory = new FlowItemPresenterFactory();
    factory.setIconPresenter((ModelIconPresenter) BeanFactory.instance().getBean("DemoModelLargeThumbPresenter"));
    factory.setIdentityPresenter((ModelIdentityPresenter) BeanFactory.instance().getBean("DemoFlowItemIdentityPresenter"));
    factory.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) BeanFactory.instance().getBean("ModelDescriptionPresenter"));
    return factory;
  }

  @Bean
  @Named("DemoFlowItemPresenter4")
  public FlowItemPresenterFactory DemoFlowItemPresenter4() {
    FlowItemPresenterFactory factory = new FlowItemPresenterFactory();
    factory.setIconPresenter((ModelIconPresenter) BeanFactory.instance().getBean("DemoModelLargeThumbPresenter"));
    factory.setIdentityPresenter((ModelIdentityPresenter) BeanFactory.instance().getBean("DemoFlowItemIdentityPresenter"));
    return factory;
  }


  @Bean
  @Named("PaginatedFlowItemPresenter1")
  public LargeItemPresenterFactory PaginatedFlowItemPresenter1() {
    //LargeItemPresenterFactory factory = new LargeItemPresenterFactory();
    //factory.setIconPresenter((ModelIconPresenter) BeanFactory.instance().getBean("DemoModelThumbPresenter"));
    //factory.setIdentityPresenter((ModelIdentityPresenter) BeanFactory.instance().getBean("DemoFlowItemIdentityPresenter"));
    //factory.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) BeanFactory.instance().getBean("ModelDescriptionPresenter"));
    return DemoHorizontalFlowItemPresenter();
  }

  @Bean
  @Named("PaginatedFlowItemPresenter2")
  public LargeItemPresenterFactory PaginatedFlowItemPresenter2() {
    DemoFlowItemPresenter factory = new DemoFlowItemPresenter();
    factory.setIconPresenter((ModelIconPresenter) BeanFactory.instance().getBean("DemoModelLargeThumbPresenter"));
    factory.setIdentityPresenter((ModelIdentityPresenter) BeanFactory.instance().getBean("DemoFlowItemIdentityPresenter"));
    factory.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) BeanFactory.instance().getBean("ModelDescriptionPresenter"));
    return factory;
  }

  @Bean
  @Named("DemoHorizontalFlowItemPresenter")
  public FlowItemPresenterFactory DemoHorizontalFlowItemPresenter() {
    HorizontalFlowItemPresenterFactory factory = new HorizontalFlowItemPresenterFactory();
    factory.setIconPresenter((ModelIconPresenter) BeanFactory.instance().getBean("DemoModelSmallThumbPresenter"));
    factory.setIdentityPresenter((ModelIdentityPresenter) BeanFactory.instance().getBean("DemoFlowItemIdentityPresenter"));
    factory.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) BeanFactory.instance().getBean("ModelDescriptionPresenter"));
    return factory;
  }

  @Bean
  @Named("DemoMobileHorizontalFlowItemPresenter")
  public FlowItemPresenterFactory DemoMobileHorizontalFlowItemPresenter() {
    HorizontalFlowItemPresenterFactory factory = new HorizontalFlowItemPresenterFactory();
    factory.setIconPresenter((ModelIconPresenter) BeanFactory.instance().getBean("DemoMobileModelFlowThumbPresenter"));
    factory.setIdentityPresenter((ModelIdentityPresenter) BeanFactory.instance().getBean("DemoFlowItemIdentityPresenter"));
    factory.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) BeanFactory.instance().getBean("ModelDescriptionPresenter"));
    return factory;
  }

  @Bean
  @Named("DemoModelStatusPresenter")
  public DemoModelStatusPresenter DemoModelStatusPresenter() {
    DemoModelStatusPresenter t = new DemoModelStatusPresenter();
    return t;
  }

  @Bean
  @Named("DemoAppRootMenuHeader")
  public DemoAppRootMenuHeader demoAppRootMenuHeader() {
    return new DemoAppRootMenuHeader();
  }

  @Bean
  @Named("DemoHeaderLogoPresenter")
  public io.github.jsoagger.jfxcore.demoapp.comps.DemoHeaderLogoPresenter demoHeaderLogoPresenter() {
    return new io.github.jsoagger.jfxcore.demoapp.comps.DemoHeaderLogoPresenter();
  }

  @Bean
  @Named("DemoDashboardMessageSource")
  public MessageSource dashboardMessageSource() {
    MessageSource m = new MessageSource();
    m.getBasenames().add("io.github.jsoagger.core.ui.jfx.demoapp.mobile.dashboard.message");
    return m;
  }

  @Bean
  @Named("ContextInputView")
  public ContextInputView contextInputView() {
    return new ContextInputView();
  }

  @Bean
  @Named("DemoDashboardPercentItem")
  public DemoDashboardPercentItem providesDemoDashboardPercentItem() {
    return new DemoDashboardPercentItem();
  }


  @Bean
  @Named("DemoBarChart")
  public DemoBarChart demoBarChart() {
    return new DemoBarChart();
  }

  @Bean
  @Named("DemoPieChart")
  public DemoPieChart providesDemoPieChart() {
    return new DemoPieChart();
  }

  @Bean
  @Named("DemoDashboardItem")
  public DemoDashboardItem providesDemoDashboardItem() {
    return new DemoDashboardItem();
  }

  @Bean
  @Named("DemoMobileModelFlowThumbPresenter")
  public ModelIconPresenter DemoModelFlowThumbPresenter() {
    DemoMobileModelFlowThumbPresenter p = new DemoMobileModelFlowThumbPresenter();
    p.setWidth(120);
    p.setHeight(100);
    return p;
  }

  // 320x225
  @Bean
  @Named("DemoModelLargeThumbPresenter")
  public DemoModelFlowThumbPresenter DemoModelLargeThumbPresenter() {
    DemoModelFlowThumbPresenter p = new DemoModelFlowThumbPresenter();
    p.setWidth(120);
    p.setHeight(100);
    return p;
  }

  @Bean
  @Named("DemoModelSmallThumbPresenter")
  public ModelIconPresenter DemoModelSmallThumbPresenter() {
    DemoModelFlowThumbPresenter p = new DemoModelFlowThumbPresenter();
    p.setWidth(120);
    p.setHeight(100);
    return p;
  }

  @Bean
  @Named("DemoFlowItemIdentityPresenter")
  public DemoFlowItemIdentityPresenter DemoFlowItemIdentityPresenter() {
    DemoFlowItemIdentityPresenter p = new DemoFlowItemIdentityPresenter();
    return p;
  }

  @Bean
  @Named("DemoAdminItemListCellPresenter")
  public MultiPresenterFactory DemoAdminItemListCellPresenter() {
    MultiPresenterFactory d = new MultiPresenterFactory();
    d.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    d.setIconPresenter((ModelIconPresenter) Services.getBean("AdminStaticIconPresenter"));
    return d;
  }

  @Bean
  @Named("DemoAdminItemCellFactory")
  public DefaultModelListCell DemoAdminItemCellFactory() {
    DefaultModelListCell m = new DefaultModelListCell();
    return m;
  }

  @Bean
  @Named("DemoStaticModelFlowCell")
  public DemoStaticModelFlowCell DemoStaticModelFlowCell() {
    DemoStaticModelFlowCell m = new DemoStaticModelFlowCell();
    m.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    m.setIconPresenter((ModelIconPresenter) Services.getBean("AdminStaticIconPresenter"));
    return m;
  }


  @Bean
  @Named("DemoStaticParentItemFlowCell")
  public DemoStaticParentItemFlowCell DemoStaticParentItemFlowCell() {
    DemoStaticParentItemFlowCell m = new DemoStaticParentItemFlowCell();
    m.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    return m;
  }

  @Bean
  @Named("CopyButtonDemoAction")
  public CopyButtonDemoAction copyButtonDemoAction() {
    CopyButtonDemoAction m = new CopyButtonDemoAction();
    return m;
  }

  @Bean
  @Named("CloseTaskFromTableRowAction")
  public CloseTaskFromTableRowAction closeTaskFromTableRowAction() {
    CloseTaskFromTableRowAction m = new CloseTaskFromTableRowAction();
    return m;
  }

  @Bean
  @Named("CloseTasksButtonDemoAction")
  public CloseTasksButtonDemoAction closeTasksButtonDemoAction() {
    CloseTasksButtonDemoAction m = new CloseTasksButtonDemoAction();
    return m;
  }

  @Bean
  @Named("DeleteButtonDemoAction")
  public DeleteButtonDemoAction deleteButtonDemoAction() {
    DeleteButtonDemoAction m = new DeleteButtonDemoAction();
    return m;
  }

  @Bean
  @Named("ExportButtonDemoAction")
  public ExportButtonDemoAction exportButtonDemoAction() {
    ExportButtonDemoAction m = new ExportButtonDemoAction();
    return m;
  }

  @Bean
  @Named("PrintButtonDemoAction")
  public PrintButtonDemoAction printButtonDemoAction() {
    PrintButtonDemoAction m = new PrintButtonDemoAction();
    return m;
  }

  @Bean
  @Named("RefreshButtonDemoAction")
  public RefreshButtonDemoAction refreshButtonDemoAction() {
    RefreshButtonDemoAction m = new RefreshButtonDemoAction();
    return m;
  }

  @Bean
  @Named("CreateSavedSearchAction")
  public CreateDemoSavedSearchAction createSavedSearchAction() {
    CreateDemoSavedSearchAction m = new CreateDemoSavedSearchAction();
    return m;
  }

  @Bean
  @Named("BeginTaskFromTableRowAction")
  public BeginTaskFromTableRowAction beginTaskFromTableRowAction() {
    BeginTaskFromTableRowAction m = new BeginTaskFromTableRowAction();
    return m;
  }

  @Bean
  @Named("ReopenTaskFromTableRowAction")
  public ReopenTaskFromTableRowAction reopenTaskFromTableRowAction() {
    ReopenTaskFromTableRowAction m = new ReopenTaskFromTableRowAction();
    return m;
  }

  @Bean
  @Named("ValidateTaskFromTableRowAction")
  public ValidateTaskFromTableRowAction validateTaskFromTableRowAction() {
    ValidateTaskFromTableRowAction m = new ValidateTaskFromTableRowAction();
    return m;
  }

  @Bean
  @Named("ValidateTasksButtonDemoAction")
  public ValidateTasksButtonDemoAction validateTasksButtonDemoAction() {
    ValidateTasksButtonDemoAction m = new ValidateTasksButtonDemoAction();
    return m;
  }
}

