/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.ConvertViewToJson;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.components.listform.controller.SelectMultiValueController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
@ConvertViewToJson
public class DemoAppPreferencesBean {

  @Bean
  @Named("DemoMobilePreferencesRootGroupListView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesRootGroupListView.xml",
  "/io/github/jsoagger/jfxcore/demoapp/mobile/preferences/DemoMobilePreferencesRootGroupListView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/demoapp/mobile/preferences", outputFileName="DemoMobilePreferencesRootGroupListView")
  public StandardController DemoPreferencesRootGroupListView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/mobile/preferences/DemoMobilePreferencesRootGroupListView.json");
    return p;
  }

  @Bean
  @Named("DemoPreferencesInformationGroupTabPaneView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesInformationGroupTabPaneView.xml"})
  public StandardController DemoPreferencesInformationGroupTabPaneView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesInformationGroupTabPaneView.json");
    return p;
  }

  @Bean
  @Named("LicenceView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/LicenceView.xml"})
  public StandardController LicenceView() {
    StandardController p = new StandardController();
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/LicenceView.json");
    return p;
  }

  @Bean
  @Named("ThanksToView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/ThanksToView.xml"})
  public StandardController ThanksToView() {
    StandardController p = new StandardController();
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/ThanksToView.json");
    return p;
  }


  @Bean
  @Named("DemoPreferencesGeneralGroupTabPaneView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesGeneralGroupTabPaneView.xml"})
  public StandardController DemoPreferencesGeneralGroupTabPaneView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesGeneralGroupTabPaneView.json");
    return p;
  }

  @Bean
  @Named("DemoPreferencesRegionalGroupTabPaneView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesRegionalGroupTabPaneView.xml"})
  public StandardController DemoPreferencesRegionalGroupTabPaneView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesRegionalGroupTabPaneView.json");
    return p;
  }


  @Bean
  @Named("DemoPreferencesUserNotificationsTabPaneView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesUserNotificationsTabPaneView.xml"})
  public StandardController DemoPreferencesUserNotificationsTabPaneView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesUserNotificationsTabPaneView.json");
    return p;
  }

  @Bean
  @Named("DemoPreferencesUserThemingTabPaneView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesUserThemingTabPaneView.xml"})
  public StandardController DemoPreferencesUserThemingTabPaneView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesUserThemingTabPaneView.json");
    return p;
  }

  @Bean
  @Named("SelectPreferenceValueView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/SelectPreferenceValueView.xml"})
  public StandardController SelectPreferenceValueView() {
    SelectMultiValueController p = new SelectMultiValueController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/SelectPreferenceValueView.json");
    return p;
  }

  @Bean
  @Named("DemoPreferencesThemingPrimaryTabPaneView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesThemingPrimaryTabPaneView.xml"})
  public StandardController DemoPreferencesThemingPrimaryTabPaneView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesThemingPrimaryTabPaneView.json");
    return p;
  }

  @Bean
  @Named("DemoPreferencesThemingAccentTabPaneView")
  @View(locations= {"/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesThemingAccentTabPaneView.xml"})
  public StandardController DemoPreferencesThemingAccentTabPaneView() {
    StandardController p = new StandardController();
    p.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    p.setModelProvider((IModelProvider) Services.getBean("StructureContentModelReference"));
    p.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedMobileForwardViewLayoutManager"));
    p.getViewDefinitions().add("/io/github/jsoagger/jfxcore/demoapp/common/preferences/DemoPreferencesThemingAccentTabPaneView.json");
    return p;
  }
}
