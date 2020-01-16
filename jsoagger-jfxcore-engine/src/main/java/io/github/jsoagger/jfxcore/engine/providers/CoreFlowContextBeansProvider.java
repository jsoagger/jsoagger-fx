/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;

import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.ConvertViewToJson;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.engine.components.pagination.StaticListEntryLoader;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.descriptionprovider.ModelDescriptionPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.identityprovider.ModelNameIdentityPresenter;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header.FiltrableTableHeaderImpl;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header.FiltrableTableHeaderImpl2;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@BeansProvider
@ConvertViewToJson
public class CoreFlowContextBeansProvider {


  @Bean
  @Named("ModelNameIdentityPresenter")
  public ModelNameIdentityPresenter ModelNameIdentityPresenter() {
    return new ModelNameIdentityPresenter();
  }

  @Bean
  @Named("FiltrableTableHeaderImpl")
  public FiltrableTableHeaderImpl FiltrableTableHeaderImpl() {
    return new FiltrableTableHeaderImpl();
  }

  @Bean
  @Named("FiltrableTableHeaderImpl2")
  public FiltrableTableHeaderImpl2 FiltrableTableHeaderImpl2() {
    return new FiltrableTableHeaderImpl2();
  }


  @Bean
  @Named("StaticListEntryLoader")
  public StaticListEntryLoader StaticListEntryLoader() {
    StaticListEntryLoader d = new StaticListEntryLoader();
    return d;
  }


  @Bean
  @Named("ModelDescriptionPresenter")
  public ModelDescriptionPresenter ModelDescriptionPresenter() {
    ModelDescriptionPresenter p = new ModelDescriptionPresenter();
    return p;
  }
}
