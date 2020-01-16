/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoFlowItemIdentityPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoFlowItemPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoModelFlowThumbPresenter;
import io.github.jsoagger.jfxcore.demoapp.comps.DemoModelThumbPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.LargeItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentFixedPaginationLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.ParentResponsiveMatrix;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoDesktopBeansProvider {


  @Bean
  @Named("DemoModelThumbPresenter")
  public DemoModelThumbPresenter demoModelThumbPresenter() {
    DemoModelThumbPresenter l = new DemoModelThumbPresenter();
    l.setGetPreferenceValueOperation((IOperation) Services.getBean("GetPreferenceValueOperation"));
    l.setPlatformProperties((Properties) Services.getBean("platformProperties"));
    return l;
  }

  @Bean
  @Named("DemoModelFlowThumbPresenter")
  public DemoModelFlowThumbPresenter demoModelFlowThumbPresenter() {
    DemoModelFlowThumbPresenter l = new DemoModelFlowThumbPresenter();
    l.setGetPreferenceValueOperation((IOperation) Services.getBean("GetPreferenceValueOperation"));
    l.setPlatformProperties((Properties) Services.getBean("platformProperties"));
    return l;
  }



  @Bean
  @Named("PaginatedFlowItemPresenter1")
  public LargeItemPresenterFactory PaginatedFlowItemPresenter1() {
    LargeItemPresenterFactory l = new LargeItemPresenterFactory();
    l.setIconPresenter((ModelIconPresenter) Services.getBean("DemoModelThumbPresenter"));
    l.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("DemoFlowItemIdentityPresenter"));
    l.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return l;
  }


  @Bean
  @Named("PaginatedFlowItemPresenter2")
  public LargeItemPresenterFactory PaginatedFlowItemPresenter2() {
    DemoFlowItemPresenter l = new DemoFlowItemPresenter();
    l.setIconPresenter((ModelIconPresenter) Services.getBean("DemoModelThumbPresenter"));
    l.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("DemoFlowItemIdentityPresenter"));
    l.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return l;
  }


  @Bean
  @Named("DemoFlowItemIdentityPresenter")
  public DemoFlowItemIdentityPresenter demoFlowItemIdentityPresenter() {
    DemoFlowItemIdentityPresenter f = new DemoFlowItemIdentityPresenter();
    return f;
  }

  @Bean
  @Named("DemoCenteredFullFlowLayoutManager")
  public IViewLayoutManager demoCenteredFullFlowLayoutManager() {
    FullFlowContentLayoutManager f = new FullFlowContentLayoutManager();
    f.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("DemoCenteredFullFlowLayoutManagerMatrix"));
    return f;
  }


  @Bean
  @Named("DemoCenteredFullFlowLayoutManagerMatrix")
  public ParentResponsiveMatrix DemoCenteredFullFlowLayoutManagerMatrix() {
    List<String> def = (List<String>) Services.getBean("DemoCenteredFullFlowFixedPaginationLayoutManagerResponsiveMatrix");
    ParentResponsiveMatrix f = new ParentResponsiveMatrix(def);
    return f;
  }

  @Bean
  @Named("DemoCenteredFullFlowLayoutManagerMatrixDefinition")
  public List<String> DemoCenteredFullFlowLayoutManagerMatrixDefinition(){
    List<String> def =  new ArrayList<String>();
    def.add("0:610#0:1:0#hide:minimize:hide");
    def.add("610:800#0:1:0#hide:minimize:hide");
    def.add("800:1000#0.5:fixed|610:0.5#::");
    def.add("1000#0.5:fixed|650:0.50#::");
    return def;
  }

  @Bean
  @Named("DemoCenteredFullFlowFixedPaginationLayoutManager")
  public IViewLayoutManager DemoCenteredFullFlowFixedPaginationLayoutManager() {
    FullFlowContentFixedPaginationLayoutManager f = new FullFlowContentFixedPaginationLayoutManager();
    f.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("DemoCenteredFullFlowFixedPaginationLayoutManagerResponsiveMatrix"));
    return f;
  }

  @Bean
  @Named("DemoCenteredFullFlowFixedPaginationLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix DemoCenteredFullFlowFixedPaginationLayoutManagerResponsiveMatrix() {
    List<String> def = (List<String>) Services.getBean("DemoCenteredFullFlowLayoutManagerMatrixDefinition");
    ParentResponsiveMatrix f = new ParentResponsiveMatrix(def);
    return f;
  }




  @Bean
  @Named("DemoCenteredHorizontalFlowFixedPaginationLayoutManager")
  public IViewLayoutManager DemoCenteredHorizontalFlowFixedPaginationLayoutManager() {
    FullFlowContentFixedPaginationLayoutManager f = new FullFlowContentFixedPaginationLayoutManager();
    f.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("DemoCenteredHorizontalFlowFixedPaginationLayoutManagerResponsiveMatrix"));
    return f;
  }


  @Bean
  @Named("DemoCenteredHorizontalFlowFixedPaginationLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix DemoCenteredHorizontalFlowFixedPaginationLayoutManagerResponsiveMatrix() {
    List<String> def = (List<String>) Services.getBean("DemoCenteredHorizontalFlowFixedPaginationLayoutManagerResponsiveMatrixDefinition");
    ParentResponsiveMatrix f = new ParentResponsiveMatrix(def);
    return f;
  }

  @Bean
  @Named("DemoCenteredHorizontalFlowFixedPaginationLayoutManagerResponsiveMatrixDefinition")
  public List<String> DemoCenteredHorizontalFlowFixedPaginationLayoutManagerResponsiveMatrixDefinition(){
    List<String> def =  new ArrayList<String>();
    def.add("0:610#0.5:fixed|600:0.5#:minimize:");
    def.add("610:800#0.5:fixed|600:0.5#hide:minimize:hide");
    def.add("800:1000#0.5:fixed|610:0.5#::");
    def.add("1000#0.5:fixed|650:0.50#::");
    return def;
  }



  @Bean
  @Named("Example4FlowLayoutManager")
  public IViewLayoutManager Example4FlowLayoutManager() {
    FullFlowContentFixedPaginationLayoutManager f = new FullFlowContentFixedPaginationLayoutManager();
    f.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("Example4FlowLayoutManagerMatrix"));
    return f;
  }


  @Bean
  @Named("Example4FlowLayoutManagerMatrix")
  public ParentResponsiveMatrix Example4FlowLayoutManagerMatrix() {
    List<String> def = (List<String>) Services.getBean("Example4FlowLayoutManagerMatrixDefinition");
    ParentResponsiveMatrix f = new ParentResponsiveMatrix(def);
    return f;
  }

  @Bean
  @Named("Example4FlowLayoutManagerMatrixDefinition")
  public List<String> Example4FlowLayoutManagerMatrixDefinition(){
    List<String> def =  new ArrayList<String>();
    def.add("0#0:1:0#hide::hide");
    return def;
  }


  @Bean
  @Named("DemoScrolLessCenteredFullFlowLayoutManager")
  public IViewLayoutManager DemoScrolLessCenteredFullFlowLayoutManager() {
    FullFlowContentLayoutManager f = new FullFlowContentLayoutManager();
    f.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManagerMatrix"));
    return f;
  }


  @Bean
  @Named("DemoScrolLessCenteredFullFlowLayoutManagerMatrix")
  public ParentResponsiveMatrix DemoScrolLessCenteredFullFlowLayoutManagerMatrix() {
    List<String> def = (List<String>) Services.getBean("DemoScrolLessCenteredFullFlowLayoutManagerMatrixDefinition");
    ParentResponsiveMatrix f = new ParentResponsiveMatrix(def);
    return f;
  }

  @Bean
  @Named("DemoScrolLessCenteredFullFlowLayoutManagerMatrixDefinition")
  public List<String> DemoScrolLessCenteredFullFlowLayoutManagerMatrixDefinition(){
    List<String> def =  new ArrayList<String>();
    def.add("0#0:1:0#hide::hide");
    return def;
  }
}
