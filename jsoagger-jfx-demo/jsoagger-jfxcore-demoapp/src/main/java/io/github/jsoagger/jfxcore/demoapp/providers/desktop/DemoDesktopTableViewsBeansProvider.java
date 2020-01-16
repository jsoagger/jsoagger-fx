/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.desktop;


import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.ConvertViewToJson;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullFlowViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
@ConvertViewToJson
public class DemoDesktopTableViewsBeansProvider {

  @Bean
  @Named("DemoTableViewExample1RSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example1/DemoTableViewExample1RSView.xml"})
  public RootStructureController DemoTableViewExample1RSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example1/DemoTableViewExample1RSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoTableViewExample1RSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example1/DemoTableViewExample1RSContentView.xml"})
  public RootStructureContentController DemoTableViewExample1RSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example1/DemoTableViewExample1RSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoTableViewExample1TableView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example1/DemoTableViewExample1TableView.xml"})
  public FullTableViewController DemoTableViewExample1TableView() {
    FullTableViewController rscc = new FullTableViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example1/DemoTableViewExample1TableView.json");
    return rscc;
  }




  @Bean
  @Named("DemoTableViewExample2RSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example2/DemoTableViewExample2RSView.xml"})
  public RootStructureController DemoTableViewExample2RSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example2/DemoTableViewExample2RSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoTableViewExample2RSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example2/DemoTableViewExample2RSContentView.xml"})
  public RootStructureContentController DemoTableViewExample2RSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example2/DemoTableViewExample2RSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoTableViewExample2View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example2/DemoTableViewExample2View.xml"})
  public FullTableViewController DemoTableViewExample2View() {
    FullTableViewController rscc = new FullTableViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example2/DemoTableViewExample2View.json");
    return rscc;
  }




  @Bean
  @Named("DemoTableViewExample3RSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example3/DemoTableViewExample3RSView.xml"})
  public RootStructureController DemoTableViewExample3RSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example3/DemoTableViewExample3RSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoTableViewExample3RSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example3/DemoTableViewExample3RSContentView.xml"})
  public RootStructureContentController DemoTableViewExample3RSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example3/DemoTableViewExample3RSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoTableViewExample3View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/table/example3/DemoTableViewExample3View.xml"})
  public FullTableViewController DemoTableViewExample3View() {
    FullTableViewController rscc = new FullTableViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/table/example3/DemoTableViewExample3View.json");
    return rscc;
  }




  @Bean
  @Named("DemoListExample1RSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example1/DemoListExample1RSView.xml"})
  public RootStructureController DemoListExample1RSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example1/DemoListExample1RSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoListExample1RSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example1/DemoListExample1RSContentView.xml"})
  public RootStructureContentController DemoListExample1RSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example1/DemoListExample1RSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoListExample1View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example1/DemoListExample1View.xml"})
  public FullFlowViewController DemoListExample1View() {
    FullFlowViewController rscc = new FullFlowViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("FlowMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("DemoCenteredFullFlowFixedPaginationLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example1/DemoListExample1View.json");
    return rscc;
  }



  @Bean
  @Named("DemoListExample2RSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example2/DemoListExample2RSView.xml"})
  public RootStructureController DemoListExample2RSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("DemoExamplesTableMessageSource"));
    mobileLayoutRSView.addViewDefinition("io/github/jsoagger/jfxcore/demoapp/desktop/flow/example2/DemoListExample2RSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoListExample2RSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example2/DemoListExample2RSContentView.xml"})
  public RootStructureContentController DemoListExample2RSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example2/DemoListExample2RSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoListExample2View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example2/DemoListExample2View.xml"})
  public FullFlowViewController DemoListExample2View() {
    FullFlowViewController rscc = new FullFlowViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("FlowMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("DemoCenteredFullFlowFixedPaginationLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example2/DemoListExample2View.json");
    return rscc;
  }





  @Bean
  @Named("DemoListExample3RSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example3/DemoListExample3RSView.xml"})
  public RootStructureController DemoListExample3RSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("FlowMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example3/DemoListExample3RSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoListExample3RSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example3/DemoListExample3RSContentView.xml"})
  public RootStructureContentController DemoListExample3RSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example3/DemoListExample3RSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoListExample3View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example3/DemoListExample3View.xml"})
  public FullFlowViewController DemoListExample3View() {
    FullFlowViewController rscc = new FullFlowViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("FlowMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example3/DemoListExample3View.json");
    return rscc;
  }






  @Bean
  @Named("DemoListExample4RSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example4/DemoListExample4RSView.xml"})
  public RootStructureController DemoListExample4RSView() {
    DoubleHeaderRootStructureController mobileLayoutRSView = new DoubleHeaderRootStructureController();
    mobileLayoutRSView.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    mobileLayoutRSView.setMessageSource((MessageSource) Services.getBean("FlowMessageSource"));
    mobileLayoutRSView.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example4/DemoListExample4RSView.json");
    return mobileLayoutRSView;
  }


  @Bean
  @Named("DemoListExample4RSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example4/DemoListExample4RSContentView.xml"})
  public RootStructureContentController DemoListExample4RSContentView() {
    RootStructureContentController rscc = new RootStructureContentController();
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example4/DemoListExample4RSContentView.json");
    return rscc;
  }


  @Bean
  @Named("DemoListExample4View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example4/DemoListExample4View.xml"})
  public FullFlowViewController DemoListExample4View() {
    FullFlowViewController rscc = new FullFlowViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("FlowMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("Example4FlowLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example4/DemoListExample4View.json");
    return rscc;
  }


  @Bean
  @Named("DemoListExample5View")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example5/DemoListExample5View.xml"})
  public FullFlowViewController DemoListExample5View() {
    FullFlowViewController rscc = new FullFlowViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("FlowMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("DemoCenteredHorizontalFlowFixedPaginationLayoutManager"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/flow/example5/DemoListExample5View.json");
    return rscc;
  }


  @Bean
  @Named("DemoToValidateTableView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/dashboard/tovalidate/DemoToValidateTableView.xml"})
  public FullTableViewController DemoToValidateTableView() {
    FullTableViewController rscc = new FullTableViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("CommonDashboardMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    rscc.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/common/dashboard/tovalidate/DemoToValidateTableView.json");
    return rscc;
  }

  @Bean
  @Named("DemoTasksTableView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/dashboard/tasks/DemoTasksTableView.xml"})
  public FullTableViewController DemoTasksTableView() {
    FullTableViewController rscc = new FullTableViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("CommonDashboardMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    rscc.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/common/dashboard/tasks/DemoTasksTableView.json");
    return rscc;
  }

  @Bean
  @Named("DemoWipTableView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/dashboard/wip/DemoWipTableView.xml"})
  public FullTableViewController DemoWipTableView() {
    FullTableViewController rscc = new FullTableViewController();
    rscc.setMessageSource((MessageSource) Services.getBean("CommonDashboardMessageSource"));
    rscc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rscc.setLayoutManager((IViewLayoutManager) Services.getBean("FullTableViewFixedPaginationLayoutManager"));
    rscc.setTableResponsiveMatrix((TableResponsiveMatrix) Services.getBean("DefaultTableResponsiveMatrix"));
    rscc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/common/dashboard/wip/DemoWipTableView.json");
    return rscc;
  }
}
