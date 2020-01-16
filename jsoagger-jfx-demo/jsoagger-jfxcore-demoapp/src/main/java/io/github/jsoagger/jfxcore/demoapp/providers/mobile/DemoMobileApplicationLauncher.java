/*-
 * ========================LICENSE_START=================================
 * JSoagger 
 * %%
 * Copyright (C) 2019 JSOAGGER
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package io.github.jsoagger.jfxcore.demoapp.providers.mobile;

import io.github.jsoagger.cloud.stub.StubOperationsBeansProvider;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.components.ApplicationProviderUtils;
import io.github.jsoagger.jfxcore.demoapp.providers.DemoAppCommonBeansProvider;
import io.github.jsoagger.jfxcore.demoapp.providers.DemoAppPreferencesBean;
import io.github.jsoagger.jfxcore.demoapp.providers.DemoAppSearchBeansProvider;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.preloader.desktop.SharedScene;

import javafx.scene.Parent;

/**
 * @author Ramilafananana  VONJISOA
 */
public class DemoMobileApplicationLauncher extends AbstractApplicationRunner implements SharedScene {

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
    BeanFactory.addProvider(DemoMobileSearchBeansProvider.class);
    BeanFactory.addProvider(StubOperationsBeansProvider.class);

    addMobileBeansProvider();
    BeanFactory.loadBeans();

    // build structure
    this.viewStructure = (ViewStructure) Services.getBean("platformViewStructure");
    this.viewStructure.buildStructure();
  }


  /**
   * Add mobile beans to context
   */
  private void addMobileBeansProvider() {
    BeanFactory.addProvider(DemoAppMobileViewStructureBeansProvider.class);
    BeanFactory.addProvider(DemoAppMobileViewsProvider.class);
  }

  /**
   * Used only for mobile mode or embedded.
   */
  @Override
  public Parent getParentNode() {
    return this.viewStructure.getParentNode();
  }
}
