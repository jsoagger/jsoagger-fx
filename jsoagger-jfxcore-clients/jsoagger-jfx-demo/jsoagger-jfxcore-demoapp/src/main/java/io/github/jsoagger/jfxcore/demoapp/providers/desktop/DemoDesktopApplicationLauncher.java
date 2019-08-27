/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.desktop;

import io.github.jsoagger.cloud.stub.StubOperationsBeansProvider;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.components.ApplicationProviderUtils;
import io.github.jsoagger.jfxcore.demoapp.providers.DemoAppCommonBeansProvider;
import io.github.jsoagger.jfxcore.demoapp.providers.DemoAppPreferencesBean;
import io.github.jsoagger.jfxcore.demoapp.providers.DemoAppSearchBeansProvider;
import io.github.jsoagger.jfxcore.demoapp.providers.DemoDetailsBeansProvider;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;

/**
 * @author Ramilafananana VONJISOA
 */
public class DemoDesktopApplicationLauncher  extends AbstractApplicationRunner  {


  /**
   * @{inheritedDoc}
   */
  @Override
  public void initApplication() {

    // Core beans
    BeanFactory.addProviders(ApplicationProviderUtils.getAllProviders());

    // Demo common beans
    BeanFactory.addProvider(DemoAppCommonBeansProvider.class);
    BeanFactory.addProvider(DemoAppSearchBeansProvider.class);
    BeanFactory.addProvider(DemoAppPreferencesBean.class);
    BeanFactory.addProvider(DemoDesktopTableViewsBeansProvider.class);
    BeanFactory.addProvider(DemoDesktopBeansProvider.class);
    BeanFactory.addProvider(DemoDesktopSearchBeansProvider.class);
    BeanFactory.addProvider(DemoHeaderLessBeansProvider.class);
    BeanFactory.addProvider(DemoDesktopWizardBeansProvider.class);
    BeanFactory.addProvider(DemoDetailsBeansProvider.class);
    BeanFactory.addProvider(DemoDetailsBeansProvider.class);
    BeanFactory.addProvider(StubOperationsBeansProvider.class);

    addDesktopBeansProvider();

    // do load all declared beans
    BeanFactory.loadBeans();

    // build structure
    this.viewStructure = (ViewStructure) Services.getBean("platformViewStructure");
    this.viewStructure.buildStructure();
  }

  private void addDesktopBeansProvider() {
    BeanFactory.addProvider(DemoAppDesktopViewStructureBeansProvider.class);
    BeanFactory.addProvider(DemoAppDesktopViewsProvider.class);
  }
}
