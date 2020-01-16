/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.desktop;

import java.util.Properties;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.MenuConfigurationProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.demoapp.comps.DashboardController;
import io.github.jsoagger.jfxcore.engine.components.menu.PrimaryMenuProvider;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.HeaderLessTwoPanesViewController;
import io.github.jsoagger.jfxcore.engine.controller.SecondaryMenuController;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoHeaderLessBeansProvider {

  @Bean
  @Singleton
  @Named("HeaderLessMessageSource")
  public MessageSource HeaderLessMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.headerless.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.dashboard.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.table.message");
    return messageSource;
  }


  @Bean
  @Named("HeaderLessRSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessRSView.xml"})
  public RootStructureController HeaderLessRSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessRSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("HeaderLessRSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessRSContentView.xml"})
  public RootStructureContentController HeaderLessRSContentView() {
    RootStructureContentController mobileLayoutRSView = new RootStructureContentController();
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessRSContentView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("HeaderLessContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessContentView.xml"}, outputFileName ="HeaderLessContentView")
  public HeaderLessTwoPanesViewController HeaderLessContentView() {
    HeaderLessTwoPanesViewController mobileLayoutRSView = new HeaderLessTwoPanesViewController();
    mobileLayoutRSView.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    mobileLayoutRSView.setLayoutManager((IViewLayoutManager) Services.getBean("TwoHPanesWithLeftMenuLayoutManager"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessContentView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("HeaderLessLeftMenuViewProvider")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessLeftMenuView.xml"})
  public PrimaryMenuProvider HeaderLessLeftMenuViewProvider() {
    PrimaryMenuProvider menuProvider = new PrimaryMenuProvider();
    menuProvider.setPrimaryMenu("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessLeftMenuView.json");
    return menuProvider;
  }


  @Bean
  @Named("HeaderLessLeftMenuView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/HeaderLessLeftMenuView.xml"})
  public SecondaryMenuController HeaderLessLeftMenuView() {
    SecondaryMenuController sc = new SecondaryMenuController();
    sc.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    sc.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    sc.setMenuProvider((MenuConfigurationProvider) Services.getBean("HeaderLessLeftMenuViewProvider"));
    return sc;
  }



  @Bean
  @Named("HeaderLessToValidateTableView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/common/dashboard/tovalidate/DemoToValidateTableView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/tovalidate/HeaderLessToValidateTableView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/tovalidate")
  public FullTableViewController HeaderLessToValidateTableView() {
    FullTableViewController c = new FullTableViewController();
    c.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    c.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/tovalidate/DemoToValidateTableView.json");
    return c;
  }


  @Bean
  @Named("HeaderLessTasksTableView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/common/dashboard/tasks/DemoTasksTableView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/tasks/HeaderLessTasksTableView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/tasks")
  public FullTableViewController HeaderLessTasksTableView() {
    FullTableViewController c = new FullTableViewController();
    c.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/tasks/DemoTasksTableView.json");
    c.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    return c;
  }



  @Bean
  @Named("HeaderLessWipTableView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/common/dashboard/wip/DemoWipTableView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wip/HeaderLessWipTableView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wip")
  public FullTableViewController HeaderLessWipTableView() {
    FullTableViewController c = new FullTableViewController();
    c.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wip/DemoWipTableView.json");
    c.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    return c;
  }


  @Bean
  @Named("HeaderLessDashboardView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/dashboard/DemoDashboardView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/dashboard/HeaderLessDashboardView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/dashboard/", outputFileName = "HeaderLessDashboardView")
  public DashboardController HeaderLessDashboardView() {
    DashboardController c = new DashboardController();
    c.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/dashboard/HeaderLessDashboardView.json");
    return c;
  }


  @Bean
  @Named("HeaderLessDemoAdministrationView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/administration/DemoAdministrationView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/administration/HeaderLessDemoAdministrationView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/administration/", outputFileName = "HeaderLessDemoAdministrationView")
  public StandardController HeaderLessDemoAdministrationView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("DemoAdministrationMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/administration/HeaderLessDemoAdministrationView.json");
    return c;
  }



  @Bean
  @Named("DemoHeaderLessTableViewView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example2/DemoTableViewExample2View.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/table/DemoHeaderLessTableView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/table/", outputFileName = "DemoHeaderLessTableView")
  public FullTableViewController DemoHeaderLessTableViewView() {
    FullTableViewController c = new FullTableViewController();
    c.setMessageSource((MessageSource) Services.getBean("HeaderLessMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewLayoutManager"));
    c.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/table/DemoHeaderLessTableView.json");
    return c;
  }


  @Bean
  @Named("HLWizardContentViewWizard")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/WizardContentView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wizard/HeaderLessWizardContentView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wizard/")
  public WizardViewController HLWizardContentViewWizard() {
    WizardViewController c = new WizardViewController();
    c.setMessageSource((MessageSource) Services.getBean("WizardMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setWizardProperties((Properties) Services.getBean("wizardProperties"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wizard/WizardContentView.json");
    return c;
  }


  @Bean
  @Named("HLAnotherWizardView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/AnotherWizardView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wizard/HeaderLessAnotherWizardView.xml" },
  outputFilePath= "/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wizard/")
  public WizardViewController HLAnotherWizardView() {
    WizardViewController c = new WizardViewController();
    c.setMessageSource((MessageSource) Services.getBean("WizardMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/headerless/wizard/AnotherWizardContentView.json");
    return c;
  }
}

