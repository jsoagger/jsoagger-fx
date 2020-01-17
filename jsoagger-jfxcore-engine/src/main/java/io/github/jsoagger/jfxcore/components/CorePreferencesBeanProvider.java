/**
 *
 */
package io.github.jsoagger.jfxcore.components;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CorePreferencesBeanProvider {

  @Bean
  @Named("PreferencesMessageSource")
  public MessageSource PreferencesMessageSource() {
    MessageSource msg = new MessageSource();
    msg.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    msg.getBasenames().add("io.github.jsoagger.jfxcore.components.preferences.message");
    return msg;
  }



  @Bean
  @Named("PreferencesThemingPrimaryTabPaneView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/PreferencesThemingPrimaryTabPaneView.xml")
  public StandardController PreferencesThemingPrimaryTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/PreferencesThemingPrimaryTabPaneView.json");
    return c;
  }

  @Bean
  @Named("PreferencesThemingAccentTabPaneView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/PreferencesThemingAccentTabPaneView.xml")
  public StandardController PreferencesThemingAccentTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/PreferencesThemingAccentTabPaneView.json");
    return c;
  }



  @Bean
  @Named("SystemPreferencesRootGroupListView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesRootGroupListView.xml",
  outputFilePath = "/io/github/jsoagger/jfxcore/components/preferences/system")
  public StandardController SystemPreferencesRootGroupListView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesRootGroupListView.json");
    return c;
  }


  @Bean
  @Named("SelfSystemPreferencesRootGroupListView")
  @View(locations = {"/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesRootGroupListView.xml",
  "/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesRootGroupListView.xml"},
  outputFileName = "SelfSystemPreferencesRootGroupListView", outputFilePath = "/io/github/jsoagger/jfxcore/components/preferences/system/")
  public StandardController SelfSystemPreferencesRootGroupListView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("SelfCenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesRootGroupListView.json");
    return c;
  }



  @Bean
  @Named("SystemPreferencesNotificationsTabPaneView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesNotificationsTabPaneView.xml")
  public StandardController SystemPreferencesNotificationsTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesNotificationsTabPaneView.json");
    return c;
  }


  @Bean
  @Named("SelfSystemPreferencesNotificationsTabPaneView")
  @View(locations = {"/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesNotificationsTabPaneView.xml",
  "/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesNotificationsTabPaneView.xml"},
  outputFileName = "SelfSystemPreferencesNotificationsTabPaneView", outputFilePath = "/io/github/jsoagger/jfxcore/components/preferences/system/")
  public StandardController SelfSystemPreferencesNotificationsTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("SelfCenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesNotificationsTabPaneView.json");
    return c;
  }





  @Bean
  @Named("SystemPreferencesGeneralGroupTabPaneView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesGeneralGroupTabPaneView.xml")
  public StandardController SystemPreferencesGeneralGroupTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesGeneralGroupTabPaneView.json");
    return c;
  }


  @Bean
  @Named("SelfSystemPreferencesGeneralGroupTabPaneView")
  @View(locations = {"/io/github/jsoagger/jfxcore/components/preferences/PreferencesGeneralGroupTabPaneView.xml",
  "/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesGeneralGroupTabPaneView.xml"},
  outputFileName = "SelfSystemPreferencesNotificationsTabPaneView", outputFilePath = "/io/github/jsoagger/jfxcore/components/preferences/system/")
  public StandardController SelfSystemPreferencesGeneralGroupTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("SelfCenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesGeneralGroupTabPaneView.json");
    return c;
  }





  @Bean
  @Named("SystemPreferencesInformationGroupTabPaneView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesInformationGroupTabPaneView.xml")
  public StandardController SystemPreferencesInformationGroupTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SystemPreferencesGeneralGroupTabPaneView.json");
    return c;
  }


  @Bean
  @Named("SelfSystemPreferencesInformationGroupTabPaneView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesInformationGroupTabPaneView.xml")
  public StandardController SelfSystemPreferencesInformationGroupTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesInformationGroupTabPaneView.json");
    return c;
  }


  @Bean
  @Named("SelfSystemLicenseView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/cachedatas/SelfSystemLicenseView.xml")
  public StandardController SelfSystemLicenseView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/cachedatas/SelfSystemLicenseView.json");
    return c;
  }


  @Bean
  @Named("SelfSystemThanksToView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/cachedatas/SelfSystemThanksToView.xml")
  public StandardController SelfSystemThanksToView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/cachedatas/SelfSystemThanksToView.json");
    return c;
  }


  @Bean
  @Named("SystemLicenseView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/cachedatas/SystemLicenseView.xml")
  public StandardController SystemLicenseView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/cachedatas/SystemLicenseView.json");
    return c;
  }

  @Bean
  @Named("SystemThanksToView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/cachedatas/SystemThanksToView.xml")
  public StandardController SystemThanksToView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/cachedatas/SystemThanksToView.json");
    return c;
  }




  @Bean
  @Named("SystemPreferencesRegionalGroupTabPaneView")
  @View(locations = "/io/github/jsoagger/jfxcore/components/preferences/PreferencesRegionalGroupTabPaneView.xml")
  public StandardController SystemPreferencesRegionalGroupTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("CenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/PreferencesRegionalGroupTabPaneView.json");
    return c;
  }


  @Bean
  @Named("SelfSystemPreferencesRegionalGroupTabPaneView")
  @View(locations = {"/io/github/jsoagger/jfxcore/components/preferences/PreferencesRegionalGroupTabPaneView.xml",
  "/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesRegionalGroupTabPaneView.xml"},
  outputFileName = "SelfSystemPreferencesNotificationsTabPaneView", outputFilePath = "/io/github/jsoagger/jfxcore/components/preferences/system/")
  public StandardController SelfSystemPreferencesRegionalGroupTabPaneView() {
    StandardController c = new StandardController();
    c.setMessageSource((MessageSource) Services.getBean("PreferencesMessageSource"));
    c.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    c.setLayoutManager((IViewLayoutManager) Services.getBean("SelfCenteredStretchedForwardViewLayoutManager"));
    c.addViewDefinition("/io/github/jsoagger/jfxcore/components/preferences/system/SelfSystemPreferencesRegionalGroupTabPaneView.json");
    return c;
  }

}
