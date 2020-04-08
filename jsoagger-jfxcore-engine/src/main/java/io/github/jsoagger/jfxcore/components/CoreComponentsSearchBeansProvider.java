/**
 *
 */
package io.github.jsoagger.jfxcore.components;

import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.IAttributeForwardEditionHandler;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.components.search.DefaultSearchPaginatedDataLoader;
import io.github.jsoagger.jfxcore.components.search.SearchAttributeForwardEditor;
import io.github.jsoagger.jfxcore.components.search.SearchFormLayoutContentManager;
import io.github.jsoagger.jfxcore.components.search.SimpleSearchForwardEditionHandler;
import io.github.jsoagger.jfxcore.components.search.comps.SearchFiltersBlocTitle;
import io.github.jsoagger.jfxcore.components.search.comps.SearchHeaderComponent;
import io.github.jsoagger.jfxcore.engine.components.list.impl.ModelSearchResultListCell;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.ParentResponsiveMatrix;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreComponentsSearchBeansProvider {

  public CoreComponentsSearchBeansProvider() {}

  @Bean
  @Named("DefaultSearchPaginatedDataLoader")
  public DefaultSearchPaginatedDataLoader DefaultSearchPaginatedDataLoader() {
    return new DefaultSearchPaginatedDataLoader();
  }


  @Bean
  @Named("SearchHeaderComponent")
  public SearchHeaderComponent searchHeaderComponent() {
    SearchHeaderComponent p = new SearchHeaderComponent();
    p.setMode("mobile");
    return p;
  }


  @Bean
  @Named("SearchFiltersBlocTitle")
  public SearchFiltersBlocTitle searchFiltersBlocTitle() {
    SearchFiltersBlocTitle p = new SearchFiltersBlocTitle();
    return p;
  }


  @Bean
  @Named("SearchFormForwardEditionHandler")
  public IAttributeForwardEditionHandler SimpleSearchForwardEditionHandler() {
    return new SimpleSearchForwardEditionHandler();
  }

  @Bean
  @Named("SearchAttributeForwardEditor")
  public SearchAttributeForwardEditor SearchAttributeForwardEditor() {
    return new SearchAttributeForwardEditor();
  }

  @Bean
  @Named("ModelSearchResultListCell")
  public ModelSearchResultListCell ModelSearchResultListCell() {
    return new ModelSearchResultListCell();
  }


  @Bean
  @Named("SearchFormLayoutContentManager")
  public SearchFormLayoutContentManager searchFormLayoutContentManager() {
    SearchFormLayoutContentManager p = new SearchFormLayoutContentManager();
    p.setResponsiveMatrix(
        (IParentResponsiveMatrix) BeanFactory.instance().getBean("SearchFormResponsiveMatrix"));
    return p;
  }

  @Bean
  @Named("SearchFormResponsiveMatrix")
  public ParentResponsiveMatrix searchFormResponsiveMatrix() {
    List<String> d =
        (List<String>) BeanFactory.instance().getBean("SearchFormResponsiveMatrixDefinition");
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(d);
    return p;
  }

  @Bean
  @Named("SearchFormResponsiveMatrixDefinition")
  public List<String> searchFormResponsiveMatrixDefinition() {
    List<String> l = new ArrayList<>();
    l.add("0#0:0.98:0#hide::hide");
    return l;
  }
}
