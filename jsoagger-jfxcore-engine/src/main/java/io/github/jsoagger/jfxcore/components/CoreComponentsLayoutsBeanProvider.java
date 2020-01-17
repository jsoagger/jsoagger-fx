/**
 *
 */
package io.github.jsoagger.jfxcore.components;

import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.components.search.SearchFormLayoutContentManager;
import io.github.jsoagger.jfxcore.components.search.SearchResultViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FixedLeftRightThreeHPanesViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentFixedPaginationLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullTableFixedPaginationLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullTableStructureContentLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ListLeftDetailsRightLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.MaximizedDetailsViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ScrolledSinglePaneViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ScrolledSingleVPaneViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.SwitchableLeftRightTwoHPanesViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.SwitchableTwoHPanesViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.SwitchableTwoHPanesWithHeaderActionsViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.TwoHPanesViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ViewForwardLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.ParentResponsiveMatrix;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreComponentsLayoutsBeanProvider {

  @Bean
  @Named("AlwaysHideLeftAndRightLayoutManager")
  public IViewLayoutManager alwaysHideLeftAndRightLayoutManager() {
    ScrolledSinglePaneViewLayoutManager p = new ScrolledSinglePaneViewLayoutManager();
    p.setResponsiveMatrix(AlwaysHideLeftAndRightLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("AlwaysHideLeftAndRightLayoutResponsiveMatrix")
  public ParentResponsiveMatrix AlwaysHideLeftAndRightLayoutResponsiveMatrix() {
    List<String> matrix = alwaysHideLeftAndRightLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("AlwaysHideLeftAndRightLayoutResponsiveMatrixDefinition")
  public List<String> alwaysHideLeftAndRightLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0.0#hide::hide");
    return matrix;
  }


  @Bean
  @Named("ScrollLessAlwaysHideLeftAndRightLayoutManager")
  public IViewLayoutManager scrollLessAlwaysHideLeftAndRightLayoutManager() {
    ScrolledSinglePaneViewLayoutManager p = new ScrolledSinglePaneViewLayoutManager();
    p.setResponsiveMatrix(alwaysHideLeftAndRightLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("AlwaysHideLeftAndRightLayoutResponsiveMatrix")
  public ParentResponsiveMatrix alwaysHideLeftAndRightLayoutResponsiveMatrix() {
    List<String> matrix = scrollLessAlwaysHideLeftAndRightLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("AlwaysHideLeftAndRightLayoutResponsiveMatrixDefinition")
  public List<String> scrollLessAlwaysHideLeftAndRightLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0.0#hide::hide");
    return matrix;
  }


  @Bean
  @Named("ScrolledSingleVPaneViewLayoutManager")
  public IViewLayoutManager ScrolledSingleVPaneViewLayoutManager() {
    ScrolledSingleVPaneViewLayoutManager p = new ScrolledSingleVPaneViewLayoutManager();
    p.setResponsiveMatrix(scrolledSingleVPaneViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("ScrolledSingleVPaneViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix scrolledSingleVPaneViewLayoutResponsiveMatrix() {
    List<String> matrix = scrolledSingleVPaneViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("ScrolledSingleVPaneViewLayoutResponsiveMatrixDefinition")
  public List<String> scrolledSingleVPaneViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0.0#hide::hide");
    return matrix;
  }


  @Bean
  @Named("CenteredTabViewLayoutManager")
  public IViewLayoutManager CenteredTabViewLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager p = new FixedLeftRightThreeHPanesViewLayoutManager();
    p.setResponsiveMatrix(centeredTabViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("CenteredTabViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix centeredTabViewLayoutResponsiveMatrix() {
    List<String> matrix = centeredTabViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("CenteredTabViewLayoutResponsiveMatrixDefinition")
  public List<String> centeredTabViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:700#0.05:0.90:0.05#::");
    matrix.add("700:1200#0.50:fixed|650:0.50#::");
    matrix.add("1200:1400#0.5:fixed|750:0.5#::");
    matrix.add("1400#0.50:fixed|750:0.50#::");
    return matrix;
  }


  @Bean
  @Named("CenteredStretchedViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix centeredStretchedViewLayoutResponsiveMatrix() {
    List<String> matrix = centeredStretchedViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("CenteredStretchedViewLayoutResponsiveMatrixDefinition")
  public List<String> centeredStretchedViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:800#0.05:0.90:0.05#::");
    matrix.add("800:1000#0.5:fixed|650:0.5#::");
    matrix.add("1000#0.5:fixed|650:0.5#::");
    return matrix;
  }


  @Bean
  @Named("CenteredStretchedForwardViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix centeredStretchedForwardViewLayoutResponsiveMatrix() {
    List<String> matrix = centeredStretchedForwardViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("CenteredStretchedForwardViewLayoutResponsiveMatrixDefinition")
  public List<String> centeredStretchedForwardViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:800#0.05:0.90:0.05#::");
    matrix.add("800:1000#0.5:fixed|650:0.5#::");
    matrix.add("1000#0.5:fixed|650:0.5#::");
    return matrix;
  }




  @Bean
  @Named("MobileCenteredStretchedForwardViewLayoutManager")
  public IViewLayoutManager MobileCenteredStretchedForwardViewLayoutManager() {
    ViewForwardLayoutManager p = new ViewForwardLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(mobileCenteredStretchedForwardViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("MobileCenteredStretchedForwardViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix mobileCenteredStretchedForwardViewLayoutResponsiveMatrix() {
    List<String> matrix = mobileCenteredStretchedForwardViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("MobileCenteredStretchedForwardViewLayoutResponsiveMatrixDefinition")
  public List<String> mobileCenteredStretchedForwardViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0.:0.98:0.0#hide::hide");
    return matrix;
  }




  @Bean
  @Named("SelfCenteredStretchedForwardViewLayoutManager")
  public IViewLayoutManager SelfCenteredStretchedForwardViewLayoutManager() {
    ViewForwardLayoutManager p = new ViewForwardLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(selfCenteredStretchedForwardViewResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("SelfCenteredStretchedForwardViewResponsiveMatrix")
  public ParentResponsiveMatrix selfCenteredStretchedForwardViewResponsiveMatrix() {
    List<String> matrix = selfCenteredStretchedForwardViewResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("SelfCenteredStretchedForwardViewResponsiveMatrixDefinition")
  public List<String> selfCenteredStretchedForwardViewResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0.:1:0.0#hide::hide");
    return matrix;
  }



  @Bean
  @Named("CenteredMaximizedDetailsViewLayoutManager")
  public IViewLayoutManager CenteredMaximizedDetailsViewLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager p = new FixedLeftRightThreeHPanesViewLayoutManager();
    p.setResponsiveMatrix(CenteredMaximizedDetailsViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("CenteredMaximizedDetailsViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix CenteredMaximizedDetailsViewLayoutResponsiveMatrix() {
    List<String> matrix = CenteredMaximizedDetailsViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("CenteredMaximizedDetailsViewLayoutResponsiveMatrixDefinition")
  public List<String> CenteredMaximizedDetailsViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0.:1:0.0#hide::hide");
    matrix.add("0:800#0.05:0.90:0.05#::");
    matrix.add("000#0.1:0.80:0.1#::");
    matrix.add("1000:1200#0.15:0.70:0.15#::");
    matrix.add("1200:1600#0.20:0.60:0.20#::");
    matrix.add("1600#0.25:0.50:0.25#::");
    return matrix;
  }


  @Bean
  @Named("TableDetailsViewLayoutManager")
  public IViewLayoutManager TableDetailsViewLayoutManager() {
    ListLeftDetailsRightLayoutManager p = new ListLeftDetailsRightLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(TableDetailsViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("TableDetailsViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix TableDetailsViewLayoutResponsiveMatrix() {
    List<String> matrix = TableDetailsViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("TableDetailsViewLayoutResponsiveMatrixDefinition")
  public List<String> TableDetailsViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:900#1:0#:hide");
    matrix.add("900:1000#0.70:0.30#:");
    matrix.add("1000:1200#0.60:0.40#:");
    matrix.add("1200:1400#0.65:0.35#:");
    matrix.add("1400:1600#0.70:0.30#:");
    matrix.add("1600:1800#0.75:0.25#:");
    matrix.add("1800#0.75:0.25#:");
    return matrix;
  }



  @Bean
  @Named("TabsDetailsViewLayoutManager")
  public IViewLayoutManager TabsDetailsViewLayoutManager() {
    ListLeftDetailsRightLayoutManager p = new ListLeftDetailsRightLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(TabsDetailsViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("TabsDetailsViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix TabsDetailsViewLayoutResponsiveMatrix() {
    List<String> matrix = TabsDetailsViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("TabsDetailsViewLayoutResponsiveMatrixDefinition")
  public List<String> TabsDetailsViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:1200#1:0#minimize:hide");
    matrix.add("1200#1:fixed|400#:minimize");
    return matrix;
  }




  @Bean
  @Named("FullPaneLayoutManager")
  public IViewLayoutManager FullPaneLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager p = new FixedLeftRightThreeHPanesViewLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(FullPaneLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("FullPaneLayoutResponsiveMatrix")
  public ParentResponsiveMatrix FullPaneLayoutResponsiveMatrix() {
    List<String> matrix = FullPaneLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FullPaneLayoutResponsiveMatrixDefinition")
  public List<String> FullPaneLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:1500#0.05:0.90:0.05#::");
    matrix.add("1500#0.1:0.80:0.1#::");
    return matrix;
  }



  @Bean
  @Named("AlwaysMinimizedDetailsViewLayoutManager")
  public IViewLayoutManager AlwaysMinimizedDetailsViewLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager p = new FixedLeftRightThreeHPanesViewLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(AlwaysMinimizedDetailsViewLayoutMatrix());
    return p;
  }

  @Bean
  @Named("AlwaysMinimizedDetailsViewLayoutMatrix")
  public ParentResponsiveMatrix AlwaysMinimizedDetailsViewLayoutMatrix() {
    List<String> matrix = AlwaysMinimizedDetailsViewLayoutMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("AlwaysMinimizedDetailsViewLayoutMatrixDefinition")
  public List<String> AlwaysMinimizedDetailsViewLayoutMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0.0#hide:minimize:hide");
    return matrix;
  }


  @Bean
  @Named("FullTableViewLayoutManager")
  public IViewLayoutManager FullTableViewLayoutManager() {
    FullTableStructureContentLayoutManager p = new FullTableStructureContentLayoutManager();
    p.setResponsiveMatrix(FullTableViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("FullTableViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix FullTableViewLayoutResponsiveMatrix() {
    List<String> matrix = FullTableViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FullTableViewLayoutResponsiveMatrixDefinition")
  public List<String> FullTableViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0.0#hide::hide");
    return matrix;
  }




  @Bean
  @Named("FullTableViewFixedPaginationLayoutManager")
  public IViewLayoutManager FullTableViewFixedPaginationLayoutManager() {
    FullTableFixedPaginationLayoutManager p = new FullTableFixedPaginationLayoutManager();
    p.setResponsiveMatrix(FullTableViewFixedPaginationLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("FullTableViewFixedPaginationLayoutResponsiveMatrix")
  public ParentResponsiveMatrix FullTableViewFixedPaginationLayoutResponsiveMatrix() {
    List<String> matrix = FullTableViewFixedPaginationLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FullTableViewFixedPaginationLayoutResponsiveMatrixDefinition")
  public List<String> FullTableViewFixedPaginationLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0.0#hide::hide");
    return matrix;
  }




  @Bean
  @Named("FullTableViewLayoutManagerWithPadding")
  public IViewLayoutManager FullTableViewLayoutManagerWithPadding() {
    FullTableStructureContentLayoutManager p = new FullTableStructureContentLayoutManager();
    p.setResponsiveMatrix(FullTableViewLayoutManagerWithPaddingResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("FullTableViewLayoutManagerWithPaddingResponsiveMatrix")
  public ParentResponsiveMatrix FullTableViewLayoutManagerWithPaddingResponsiveMatrix() {
    List<String> matrix = FullTableViewLayoutManagerWithPaddingResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FullTableViewLayoutManagerWithPaddingResponsiveMatrixDefinition")
  public List<String> FullTableViewLayoutManagerWithPaddingResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0.05:0.90:0.05#::");
    return matrix;
  }



  @Bean
  @Named("CenteredFullTableViewLayoutManager")
  public IViewLayoutManager CenteredFullTableViewLayoutManager() {
    FullTableStructureContentLayoutManager p = new FullTableStructureContentLayoutManager();
    p.setResponsiveMatrix(CenteredFullTableViewLayoutResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("CenteredFullTableViewLayoutResponsiveMatrix")
  public ParentResponsiveMatrix CenteredFullTableViewLayoutResponsiveMatrix() {
    List<String> matrix = CenteredFullTableViewLayoutResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("CenteredFullTableViewLayoutResponsiveMatrixDefinition")
  public List<String> CenteredFullTableViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:800#0.05:0.90:0.05#::");
    matrix.add("800:1000#0.10:0.80:0.10#::");
    matrix.add("1000:1400#0.15:0.70:0.15#::");
    matrix.add("1400:1600#0.20:0.60:0.20#::");
    matrix.add("1600#0.20:0.60:0.20#::");
    return matrix;
  }



  @Bean
  @Named("CenteredFullFlowLayoutManager")
  public IViewLayoutManager CenteredFullFlowLayoutManager() {
    FullFlowContentLayoutManager p = new FullFlowContentLayoutManager();
    p.setResponsiveMatrix(CenteredFullFlowLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("CenteredFullFlowLayoutManagerMatrix")
  public ParentResponsiveMatrix CenteredFullFlowLayoutManagerMatrix() {
    List<String> matrix = CenteredFullFlowLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("CenteredFullFlowLayoutManagerMatrixDefinition")
  public List<String> CenteredFullFlowLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:610#0:1:0#hide:minimize:hide");
    matrix.add("610:800#0:1:0#hide:minimize:hide");
    matrix.add("800:1000#0.5:fixed|610:0.5#::");
    matrix.add("1000#0.5:fixed|650:0.50#::");
    return matrix;
  }



  @Bean
  @Named("FixedSizeFullFlowLayoutManager")
  public IViewLayoutManager FixedSizeFullFlowLayoutManager() {
    FullFlowContentLayoutManager p = new FullFlowContentLayoutManager();
    p.setResponsiveMatrix(FixedSizeFullFlowLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("FixedSizeFullFlowLayoutManagerMatrix")
  public ParentResponsiveMatrix FixedSizeFullFlowLayoutManagerMatrix() {
    List<String> matrix = FixedSizeFullFlowLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FixedSizeFullFlowLayoutManagerMatrixDefinition")
  public List<String> FixedSizeFullFlowLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:1100#0.01:0.95:0.01#::");
    matrix.add("1100:1400#0.5:fixed|900:0.5#::");
    matrix.add("1400#0.5:fixed|1000:0.5#::");
    return matrix;
  }



  @Bean
  @Named("FullFlowLayoutManager")
  public IViewLayoutManager FullFlowLayoutManager() {
    FullFlowContentLayoutManager p = new FullFlowContentLayoutManager();
    p.setResponsiveMatrix(FullFlowLayoutManagerResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("FullFlowLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix FullFlowLayoutManagerResponsiveMatrix() {
    List<String> matrix = FullFlowLayoutManagerResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FullFlowLayoutManagerResponsiveMatrixDefinition")
  public List<String> FullFlowLayoutManagerResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0#hide::hide");
    return matrix;
  }



  @Bean
  @Named("FullFlowFixedPaginationLayoutManager")
  public IViewLayoutManager FullFlowFixedPaginationLayoutManager() {
    FullFlowContentFixedPaginationLayoutManager p = new FullFlowContentFixedPaginationLayoutManager();
    p.setResponsiveMatrix(FullFlowFixedPaginationLayoutManagerResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("FullFlowFixedPaginationLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix FullFlowFixedPaginationLayoutManagerResponsiveMatrix() {
    List<String> matrix = FullFlowFixedPaginationLayoutManagerResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FullFlowFixedPaginationLayoutManagerResponsiveMatrixDefinition")
  public List<String> FullFlowFixedPaginationLayoutManagerResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0#hide::hide");
    return matrix;
  }



  @Bean
  @Named("FullFlowFixedPaginationScrollLessLayoutManager")
  public IViewLayoutManager FullFlowFixedPaginationScrollLessLayoutManager() {
    FullFlowContentFixedPaginationLayoutManager p = new FullFlowContentFixedPaginationLayoutManager();
    p.setScroll(false);
    p.setResponsiveMatrix(FullFlowFixedPaginationScrollLessLayoutManagerResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("FullFlowFixedPaginationScrollLessLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix FullFlowFixedPaginationScrollLessLayoutManagerResponsiveMatrix() {
    List<String> matrix = FullFlowFixedPaginationScrollLessLayoutManagerResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FullFlowFixedPaginationScrollLessLayoutManagerResponsiveMatrixDefinition")
  public List<String> FullFlowFixedPaginationScrollLessLayoutManagerResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0#hide::hide");
    return matrix;
  }




  @Bean
  @Named("SwitchableTwoHPanesWithHeaderActionsViewLayoutManager")
  public IViewLayoutManager SwitchableTwoHPanesWithHeaderActionsViewLayoutManager() {
    SwitchableTwoHPanesWithHeaderActionsViewLayoutManager p = new SwitchableTwoHPanesWithHeaderActionsViewLayoutManager();
    p.setVerticalScroll(false);
    //p.setFloating(false);
    p.setResponsiveMatrix(SwitchableTwoHPanesWithHeaderActionsViewLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("SwitchableTwoHPanesWithHeaderActionsViewLayoutManagerMatrix")
  public ParentResponsiveMatrix SwitchableTwoHPanesWithHeaderActionsViewLayoutManagerMatrix() {
    List<String> matrix = SwitchableTwoHPanesWithHeaderActionsViewLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("SwitchableTwoHPanesWithHeaderActionsViewLayoutManagerMatrixDefinition")
  public List<String> SwitchableTwoHPanesWithHeaderActionsViewLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:1200#0:1#minimize:");
    matrix.add("1200#fixed|500:1#:");
    return matrix;
  }


  @Bean
  @Named("TwoHPanesWithLeftMenuLayoutManager")
  public IViewLayoutManager TwoHPanesWithLeftMenuLayoutManager() {
    TwoHPanesViewLayoutManager p = new TwoHPanesViewLayoutManager();
    p.setResponsiveMatrix(TwoPanesWithMenuRSContentMatrix());
    return p;
  }

  @Bean
  @Named("TwoPanesWithMenuRSContentMatrix")
  public ParentResponsiveMatrix TwoPanesWithMenuRSContentMatrix() {
    List<String> matrix = TwoPanesWithMenuRSContentMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("TwoPanesWithMenuRSContentMatrixDefinition")
  public List<String> TwoPanesWithMenuRSContentMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:900#fixed|82:1#minimize:");
    matrix.add("900#fixed|300:1#:");
    return matrix;
  }



  @Bean
  @Named("FloatingTwoHPanesWithHeaderActionsViewLayoutManager")
  public IViewLayoutManager FloatingTwoHPanesWithHeaderActionsViewLayoutManager() {
    SwitchableTwoHPanesWithHeaderActionsViewLayoutManager p = new SwitchableTwoHPanesWithHeaderActionsViewLayoutManager();
    p.setVerticalScroll(false);
    //p.setFloating(false);
    p.setResponsiveMatrix(FloatingTwoHPanesWithHeaderActionsViewLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("FloatingTwoHPanesWithHeaderActionsViewLayoutManagerMatrix")
  public ParentResponsiveMatrix FloatingTwoHPanesWithHeaderActionsViewLayoutManagerMatrix() {
    List<String> matrix = FloatingTwoHPanesWithHeaderActionsViewLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FloatingTwoHPanesWithHeaderActionsViewLayoutManagerMatrixDefinition")
  public List<String> FloatingTwoHPanesWithHeaderActionsViewLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:900#fixed|82:1#minimize:");
    matrix.add("900:1100#fixed|300:1#:");
    matrix.add("1100#fixed|400:1#:");
    return matrix;
  }




  @Bean
  @Named("FloatingTwoHPanesLayoutManagerMatrix")
  public ParentResponsiveMatrix FloatingTwoHPanesLayoutManagerMatrix() {
    List<String> matrix = FloatingTwoHPanesLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("FloatingTwoHPanesLayoutManagerMatrixDefinition")
  public List<String> FloatingTwoHPanesLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:600#0:1:0#fixed|300::minimize");
    matrix.add("600:800#0:1:0#fixed|300::minimize");
    matrix.add("800:1000#0:1:0#fixed|400::minimize");
    matrix.add("1000:1200#0:1:0#fixed|450::minimize");
    matrix.add("1200#0:1:0#fixed|450::fixed|400");
    return matrix;
  }



  @Bean
  @Named("RightSwitchableTwoHPanesLayoutManager")
  public IViewLayoutManager RightSwitchableTwoHPanesLayoutManager() {
    SwitchableTwoHPanesViewLayoutManager p = new SwitchableTwoHPanesViewLayoutManager();
    p.setVerticalScroll(false);
    p.setOrientation("right");
    p.setResponsiveMatrix(FloatingTwoHPanesLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("LeftSwitchableTwoHPanesLayoutManager")
  public IViewLayoutManager LeftSwitchableTwoHPanesLayoutManager() {
    SwitchableTwoHPanesViewLayoutManager p = new SwitchableTwoHPanesViewLayoutManager();
    p.setVerticalScroll(false);
    p.setOrientation("left");
    p.setResponsiveMatrix(FloatingTwoHPanesLayoutManagerMatrix());
    return p;
  }




  @Bean
  @Named("SwitchableLeftRightTwoHPanesViewLayoutManager")
  public IViewLayoutManager SwitchableLeftRightTwoHPanesViewLayoutManager() {
    SwitchableLeftRightTwoHPanesViewLayoutManager p = new SwitchableLeftRightTwoHPanesViewLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(SwitchableLeftRightTwoHPanesViewLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("SwitchableLeftRightTwoHPanesViewLayoutManagerMatrix")
  public ParentResponsiveMatrix SwitchableLeftRightTwoHPanesViewLayoutManagerMatrix() {
    List<String> matrix = SwitchableLeftRightTwoHPanesViewLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("SwitchableLeftRightTwoHPanesViewLayoutManagerMatrixDefinition")
  public List<String> SwitchableLeftRightTwoHPanesViewLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:1000#0:1:0#minimize::minimize");
    matrix.add("1000:1200#0:1:0#fixed|450::minimize");
    matrix.add("1200#0:1:0#fixed|450::fixed|400");
    return matrix;
  }




  @Bean
  @Named("DefaultTableResponsiveMatrix")
  public TableResponsiveMatrix DefaultTableResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("DefaultTableResponsiveMatrixDefinition");
    TableResponsiveMatrix p = new TableResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("DefaultTableResponsiveMatrixDefinition")
  public List<String> DefaultTableResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:800#-1");
    matrix.add("800:900#-1:0");
    matrix.add("900:1000#-1:0:1");
    matrix.add("1000:1100#-1:0:1:2");
    matrix.add("1100:1300#-1:0:1:2:3");
    matrix.add("1300#-1:0:1:2:3:4");
    return matrix;
  }



  @Bean
  @Named("SearchFiltersToolbarResponsiveMatrix")
  public ParentResponsiveMatrix SearchFiltersToolbarResponsiveMatrix() {
    List<String> matrix = SearchFiltersToolbarResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Named("SearchFiltersToolbarResponsiveMatrixDefinition")
  public List<String> SearchFiltersToolbarResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0.20:0:0.80#:hide:");
    return matrix;
  }




  @Bean
  @Named("MaximizedDetailsViewRootLayoutManager")
  public IViewLayoutManager MaximizedDetailsViewRootLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager p = new FixedLeftRightThreeHPanesViewLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(MaximizedDetailsViewRootLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("MaximizedDetailsViewRootLayoutManagerMatrix")
  public ParentResponsiveMatrix MaximizedDetailsViewRootLayoutManagerMatrix() {
    List<String> matrix = MaximizedDetailsViewRootLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("MaximizedDetailsViewRootLayoutManagerMatrixDefinition")
  public List<String> MaximizedDetailsViewRootLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0:1:0#hide::hide");
    return matrix;
  }



  @Bean
  @Named("MaximizedDetailsViewLayoutManager")
  public IViewLayoutManager MaximizedDetailsViewLayoutManager() {
    MaximizedDetailsViewLayoutManager p = new MaximizedDetailsViewLayoutManager();
    p.setVerticalScroll(true);
    p.setResponsiveMatrix(MaximizedDetailsViewLayoutManagerResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("MaximizedDetailsViewLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix MaximizedDetailsViewLayoutManagerResponsiveMatrix() {
    List<String> matrix = MaximizedDetailsViewLayoutManagerResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("MaximizedDetailsViewLayoutManagerResponsiveMatrixDefinition")
  public List<String> MaximizedDetailsViewLayoutManagerResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:800#0:0:1:0#hide:hide::hide");
    matrix.add("800:900#0.5:0:fixed|640:0.5#:hide::");
    matrix.add("900#0.5:0:fixed|640:0.5#:hide::");
    return matrix;
  }




  @Bean
  @Named("ListDetailsViewLayoutManager")
  public IViewLayoutManager ListDetailsViewLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager p = new FixedLeftRightThreeHPanesViewLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(ListDetailsViewLayoutManagerMatrix());
    return p;
  }

  @Bean
  @Named("ListDetailsViewLayoutManagerMatrix")
  public ParentResponsiveMatrix ListDetailsViewLayoutManagerMatrix() {
    List<String> matrix = ListDetailsViewLayoutManagerMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("ListDetailsViewLayoutManagerMatrixDefinition")
  public List<String> ListDetailsViewLayoutManagerMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:1000#0:1:0#hide::hide");
    matrix.add("1000:1200#0.5:fixed|800:0.5#::");
    matrix.add("1200#0.5:fixed|900:0.5#::");
    return matrix;
  }




  @Bean
  @Named("PrimaryHeaderToolbarResponsiveMatrix")
  public ParentResponsiveMatrix PrimaryHeaderToolbarResponsiveMatrix() {
    List<String> matrix = PrimaryHeaderToolbarResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("PrimaryHeaderToolbarResponsiveMatrixDefinition")
  public List<String> PrimaryHeaderToolbarResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:800#0.5:fixed|500:0.5#::");
    matrix.add("800:1000#0.50:fixed|500:0.50#::");
    matrix.add("1000:1100#0.50:fixed|600:0.50#::");
    matrix.add("1100#0.50:fixed|700:0.50#::");
    return matrix;
  }



  @Bean
  @Named("SearchFormLayoutContentManager")
  public IViewLayoutManager SearchFormLayoutContentManager() {
    SearchFormLayoutContentManager p = new SearchFormLayoutContentManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(SearchFormResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("SearchFormResponsiveMatrix")
  public ParentResponsiveMatrix SearchFormResponsiveMatrix() {
    List<String> matrix = SearchFormResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("SearchFormResponsiveMatrixDefinition")
  public List<String> SearchFormResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:600#0:1:0#hide::hide");
    matrix.add("600:900#0.10:0.80:0.10#::");
    matrix.add("900#0.50:fixed|600:0.50#::");
    return matrix;
  }




  @Bean
  @Named("SearchResultLayoutManager")
  public IViewLayoutManager SearchResultLayoutManager() {
    SearchResultViewLayoutManager p = new SearchResultViewLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(SearchResultLayoutManagerResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("SearchResultLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix SearchResultLayoutManagerResponsiveMatrix() {
    List<String> matrix = SearchResultLayoutManagerResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("SearchResultLayoutManagerResponsiveMatrixDefinition")
  public List<String> SearchResultLayoutManagerResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:700#0.5:fixed|600:0.5#hide::hide");
    matrix.add("700:1000#0.50:fixed|650:0.50#::");
    matrix.add("1000:1200#0.50:fixed|700:0.50#::");
    matrix.add("1200#0.50:fixed|700:0.50#::");
    return matrix;
  }



  @Bean
  @Named("SearchResultTableLayoutManager")
  public IViewLayoutManager SearchResultTableLayoutManager() {
    SearchResultViewLayoutManager p = new SearchResultViewLayoutManager();
    p.setVerticalScroll(false);
    p.setResponsiveMatrix(SearchResultTableLayoutManagerResponsiveMatrix());
    return p;
  }

  @Bean
  @Named("SearchResultTableLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix SearchResultTableLayoutManagerResponsiveMatrix() {
    List<String> matrix = SearchResultTableLayoutManagerResponsiveMatrixDefinition();
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }

  @Bean
  @Singleton
  @Named("SearchResultTableLayoutManagerResponsiveMatrixDefinition")
  public List<String> SearchResultTableLayoutManagerResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0#0.:1:0.#hide::hide");
    return matrix;
  }




  @Bean
  @Named("FixedLeftRightThreeHPanesViewLayoutManager")
  public FixedLeftRightThreeHPanesViewLayoutManager FixedLeftRightThreeHPanesViewLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager lyt = new FixedLeftRightThreeHPanesViewLayoutManager();
    lyt.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("FullTabPaneLayoutManagerResponsiveMatrix"));
    return lyt;
  }

  @Bean
  @Named("FullTabPaneLayoutManager")
  public FixedLeftRightThreeHPanesViewLayoutManager FullTabPaneLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager lyt = new FixedLeftRightThreeHPanesViewLayoutManager();
    lyt.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("FullTabPaneLayoutManagerResponsiveMatrix"));
    return lyt;
  }

  @Bean
  @Named("FullTabPaneLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix FullTabPaneLayoutManagerResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("FullTabPaneLayoutManagerResponsiveMatrixDefinition");
    ParentResponsiveMatrix lyt = new ParentResponsiveMatrix(matrix);
    return lyt;
  }


  @Bean
  @Singleton
  @Named("FullTabPaneLayoutManagerResponsiveMatrixDefinition")
  public List<String> FullTabPaneLayoutManagerResponsiveMatrixDefinition() {
    List<String> data = new ArrayList<>();
    data.add("0#0:1:0#hide::hide");
    return data;
  }



  @Bean
  @Named("CenteredStretchedViewLayoutManager")
  public FixedLeftRightThreeHPanesViewLayoutManager CenteredStretchedViewLayoutManager() {
    FixedLeftRightThreeHPanesViewLayoutManager lyt = new FixedLeftRightThreeHPanesViewLayoutManager();
    lyt.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("CenteredStretchedViewLayoutManagerResponsiveMatrix"));
    return lyt;
  }

  @Bean
  @Named("CenteredStretchedViewLayoutManagerResponsiveMatrix")
  public ParentResponsiveMatrix CenteredStretchedViewLayoutManagerResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("CenteredStretchedViewLayoutManagerResponsiveMatrixDefinition");
    ParentResponsiveMatrix lyt = new ParentResponsiveMatrix(matrix);
    return lyt;
  }

  @Bean
  @Singleton
  @Named("CenteredStretchedViewLayoutManagerResponsiveMatrixDefinition")
  public List<String> CenteredStretchedViewLayoutManagerResponsiveMatrixDefinition() {
    List<String> data = new ArrayList<>();
    data.add("0#0:1:0#hide::hide");
    return data;
  }



  @Bean
  @Named("CenteredStretchedForwardViewLayoutManager")
  public ViewForwardLayoutManager CenteredStretchedForwardViewLayoutManager() {
    ViewForwardLayoutManager lyt = new ViewForwardLayoutManager();
    lyt.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("CenteredStretchedForwardViewLayoutResponsiveMatrix"));
    return lyt;
  }

  @Bean
  @Named("CenteredStretchedMobileForwardViewLayoutManager")
  public ViewForwardLayoutManager CenteredStretchedMobileForwardViewLayoutManager() {
    ViewForwardLayoutManager lyt = new ViewForwardLayoutManager();
    lyt.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("CenteredStretchedMobileForwardViewLayoutResponsiveMatrix"));
    return lyt;
  }

  @Bean
  @Singleton
  @Named("CenteredStretchedForwardViewLayoutResponsiveMatrixDefinition")
  public List<String> CenteredStretchedForwardViewLayoutResponsiveMatrixDefinition() {
    List<String> matrix = new ArrayList<>();
    matrix.add("0:700#0.5:fixed|600:0.5#hide::hide");
    matrix.add("700:1000#0.50:fixed|650:0.50#::");
    matrix.add("1000:1200#0.50:fixed|700:0.50#::");
    matrix.add("1200#0.50:fixed|700:0.50#::");
    return matrix;
  }

  @Bean
  @Named("CenteredStretchedForwardViewLayoutManagerLayoutResponsiveMatrix")
  public IParentResponsiveMatrix CenteredStretchedForwardViewLayoutManagerLayoutResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("CenteredStretchedMobileForwardViewLayoutResponsiveMatrixDefinition");
    ParentResponsiveMatrix lyt = new ParentResponsiveMatrix(matrix);
    return lyt;
  }

  @Bean
  @Named("CenteredStretchedMobileForwardViewLayoutResponsiveMatrix")
  public IParentResponsiveMatrix CenteredStretchedMobileForwardViewLayoutResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("CenteredStretchedMobileForwardViewLayoutResponsiveMatrixDefinition");
    ParentResponsiveMatrix lyt = new ParentResponsiveMatrix(matrix);
    return lyt;
  }

  @Bean
  @Singleton
  @Named("CenteredStretchedMobileForwardViewLayoutResponsiveMatrixDefinition")
  public List<String> CenteredStretchedMobileForwardViewLayoutResponsiveMatrixDefinition() {
    List<String> data = new ArrayList<>();
    data.add("0#0:0.98:0#hide::hide");
    return data;
  }


  @Bean
  @Named("SecondaryHeaderToolbarResponsiveMatrix")
  public IParentResponsiveMatrix SecondaryHeaderToolbarResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("SecondaryHeaderToolbarResponsiveMatrixDefinition");
    ParentResponsiveMatrix lyt = new ParentResponsiveMatrix(matrix);
    return lyt;
  }

  @Bean
  @Singleton
  @Named("SecondaryHeaderToolbarResponsiveMatrixDefinition")
  public List<String> SecondaryHeaderToolbarResponsiveMatrixDefinition() {
    List<String> data = new ArrayList<>();
    data.add("0#0.60:0:0.40#:hide:");
    return data;
  }

}
