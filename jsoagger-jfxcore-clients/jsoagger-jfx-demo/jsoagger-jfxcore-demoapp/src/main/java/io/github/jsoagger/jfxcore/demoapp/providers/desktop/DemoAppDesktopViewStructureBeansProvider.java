/**
 *
 */

package io.github.jsoagger.jfxcore.demoapp.providers.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.jfxcore.api.css.IStylesheetManager;
import io.github.jsoagger.jfxcore.api.services.ApplicationContextService;
import io.github.jsoagger.jfxcore.api.services.CommonComponentsServices;
import io.github.jsoagger.jfxcore.api.services.GlobalComponentsService;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.services.ViewConfigurationService;
import io.github.jsoagger.jfxcore.engine.components.css.StyleSheetsManager;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.TopTabPanedViewStructure;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.ParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.providers.integration.CommonCompsServices;
import io.github.jsoagger.jfxcore.engine.providers.integration.JSoaggerFXApplicationContextService;
import io.github.jsoagger.jfxcore.engine.providers.integration.JSonViewConfigurationService;
import io.github.jsoagger.jfxcore.engine.providers.integration.JsonGlobalCompsService;

/**
 * @author Ramilafananana  VONJISOA
 */
@BeansProvider
public class DemoAppDesktopViewStructureBeansProvider {

  /**
   * Constructor
   */
  public DemoAppDesktopViewStructureBeansProvider() {
  }

  @Bean
  @Singleton
  @Named("platformViewStructure")
  public TopTabPanedViewStructure platformViewStructure() {
    TopTabPanedViewStructure structure = new TopTabPanedViewStructure();
    structure.setScreenProperties(screenProperties());
    structure.setStyleSheetManager(styleSheetManager());
    structure.setPlatformProperties(platformProperties());
    return  structure;
  }


  @Bean
  @Singleton
  @Named("platformProperties")
  public Properties platformProperties() {
    Properties platformProperties = new Properties();
    platformProperties.setProperty("tempFolderPath", "temp");
    platformProperties.setProperty("applicationDataFolderPath", "temp");
    platformProperties.setProperty("localNotificationsFolderPath", "temp");
    platformProperties.setProperty("applicationWindowName", "DEMO APPLICATION");
    platformProperties.setProperty("applicationTitle", "DEMO APPLICATION");
    platformProperties.setProperty("fullScreen", "false");
    platformProperties.setProperty("platformRootStructureId", "DemoDashboardRSView");
    platformProperties.setProperty("loginRootStructureId", "LoginRootStructure");
    platformProperties.setProperty("platformType", "desktop");
    platformProperties.setProperty("applicationViewConfigMode", "offline");
    platformProperties.setProperty("applicationConnexionMode", "allow_anonymous");
    platformProperties.setProperty("full.screen.support", "false");
    return platformProperties;
  }

  @Bean
  @Singleton
  @Named("styleSheetManager")
  public IStylesheetManager styleSheetManager() {
    StyleSheetsManager sheetsManager = new StyleSheetsManager();
    sheetsManager.getStyleSheetsPath().add("classpath:/css/color/accent/orange.css");
    sheetsManager.getStyleSheetsPath().add("classpath:/css/color/primary/light-blue.css");
    sheetsManager.getStyleSheetsPath().add("classpath:/css/content/light/light-desktop.css");
    sheetsManager.getStyleSheetsPath().add("classpath:/css/undecorator/undecorator.css");
    sheetsManager.getStyleSheetsPath().add("classpath:/io/github/jsoagger/jfxcore/controller/login/login.css");
    return sheetsManager;
  }

  @Bean
  @Singleton
  @Named("customStyleSheetsPath")
  public List<String> customStyleSheetsPath(){
    List<String> d = new ArrayList<>();
    d.add("classpath:/css/desktop-override.css");
    return d;
  }

  @Bean
  @Singleton
  @Named("wizardProperties")
  public Properties providesWizardProperties() {
    Properties wizardProperties = new Properties();
    wizardProperties.setProperty("width", "300");
    wizardProperties.setProperty("height", "400");
    return wizardProperties;
  }

  @Bean
  @Singleton
  @Named("screenProperties")
  public Properties screenProperties() {
    Properties screenProperties = new Properties();
    screenProperties.setProperty("width", "900");
    screenProperties.setProperty("height", "1000");
    screenProperties.setProperty("minWidth", "690");
    screenProperties.setProperty("minHeight", "500");
    return screenProperties;
  }

  @Bean
  @Singleton
  @Named("ApplicationContextService")
  public ApplicationContextService appContextService() {
    JSoaggerFXApplicationContextService mobile = new JSoaggerFXApplicationContextService();
    return mobile;
  }


  @Bean
  @Singleton
  @Named("ViewConfigurationService")
  public ViewConfigurationService viewConfigurationService() {
    JSonViewConfigurationService viewConfigurationService = new JSonViewConfigurationService();
    return viewConfigurationService;
  }

  @Bean
  @Singleton
  @Named("GlobalComponentsService")
  public GlobalComponentsService jsonGlobalCompsService() {
    JsonGlobalCompsService jsonGlobalComps = new JsonGlobalCompsService();
    jsonGlobalComps.getFiles().add("/io/github/jsoagger/jfxcore/demoapp/common/CoreActions.json");
    jsonGlobalComps.getFiles().add("/io/github/jsoagger/jfxcore/demoapp/common/CoreActionsModel.json");
    jsonGlobalComps.getFiles().add("/io/github/jsoagger/jfxcore/demoapp/common/CoreAttributes.json");
    jsonGlobalComps.getFiles().add("/io/github/jsoagger/jfxcore/demoapp/common/CoreComponents.json");

    jsonGlobalComps.getFiles().add("/io/github/jsoagger/jfxcore/demoapp/common/common-components.json");
    jsonGlobalComps.getFiles().add("/io/github/jsoagger/jfxcore/demoapp/common/element-components.json");
    jsonGlobalComps.getFiles().add("/io/github/jsoagger/jfxcore/demoapp/common/search-components.json");
    jsonGlobalComps.loadFiles();
    return jsonGlobalComps;
  }

  @Bean
  @Singleton
  @Named("CommonCompsServices")
  public CommonCompsServices commonCompsServices() {
    CommonCompsServices commonCompsServices = new CommonCompsServices();
    return commonCompsServices;
  }

  @Bean
  @Singleton
  @Named("IntegrationService")
  public Services integrationService() {
    Services services = Services.instance();
    services.setAppContextService((ApplicationContextService) BeanFactory.instance().getBean("ApplicationContextService"));
    services.setCommonComponentsServices((CommonComponentsServices) BeanFactory.instance().getBean("CommonCompsServices"));
    services.setGlobalConfigService((GlobalComponentsService) BeanFactory.instance().getBean("GlobalComponentsService"));
    services.setViewConfigurationService((ViewConfigurationService) BeanFactory.instance().getBean("ViewConfigurationService"));
    return services;
  }

  @Bean
  @Singleton
  @Named("SearchablePrimaryHeaderToolbarResponsiveMatrix")
  public ParentResponsiveMatrix SearchablePrimaryHeaderToolbarResponsiveMatrix() {
    List<String> matrix = (List<String>) Services.getBean("SearchablePrimaryHeaderToolbarResponsiveMatrixDefinition");
    ParentResponsiveMatrix p = new ParentResponsiveMatrix(matrix);
    return p;
  }


  @Bean
  @Singleton
  @Named("SearchablePrimaryHeaderToolbarResponsiveMatrixDefinition")
  public List<String> SearchablePrimaryHeaderToolbarResponsiveMatrixDefinition(){
    List<String> matrix = new ArrayList<>();
    matrix.add("0:800#0.5:fixed|500:0.5#::");
    matrix.add("800:1000#0.50:fixed|500:0.50#::");
    matrix.add("1000:1100#0.50:fixed|600:0.50#::");
    matrix.add("1100#0.50:fixed|700:0.50#::");
    return matrix;
  }
}
