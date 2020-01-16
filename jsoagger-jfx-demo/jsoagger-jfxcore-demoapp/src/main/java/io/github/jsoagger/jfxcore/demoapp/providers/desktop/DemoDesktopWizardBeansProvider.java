/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.providers.desktop;

import java.util.Properties;

import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.demoapp.action.CreateDemoElementAction;
import io.github.jsoagger.jfxcore.demoapp.action.CreateDemoElementActionHL;
import io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler.WizardStepFinishActionHandler;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class DemoDesktopWizardBeansProvider {

  @Bean
  @Singleton
  @Named("WizardMessageSource")
  public MessageSource WizardMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.wizard.message");
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.demoapp.desktop.details.message");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    return messageSource;
  }


  @Bean
  @Named("WizardRSContentView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/WizardRSContentView.xml"})
  public RootStructureContentController WizardRSContentView() {
    RootStructureContentController rssc = new RootStructureContentController();
    rssc.setMessageSource((MessageSource) Services.getBean("WizardMessageSource"));
    rssc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/WizardRSContentView.json");
    return rssc;
  }

  @Bean
  @Named("WizardRSView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/WizardRSView.xml"})
  public DoubleHeaderRootStructureController WizardRSView() {
    DoubleHeaderRootStructureController rssc = new DoubleHeaderRootStructureController();
    rssc.setMessageSource((MessageSource) Services.getBean("WizardMessageSource"));
    rssc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rssc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/WizardRSView.json");
    return rssc;
  }

  @Bean
  @Named("WizardContentViewWizard")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/WizardContentView.xml"})
  public WizardViewController WizardContentViewWizard() {
    WizardViewController rssc = new WizardViewController();
    rssc.setMessageSource((MessageSource) Services.getBean("WizardMessageSource"));
    rssc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rssc.setWizardProperties((Properties) Services.getBean("wizardProperties"));
    rssc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/WizardContentView.json");
    return rssc;
  }


  @Bean
  @Named("AnotherWizardView")
  @View(locations = {"/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/AnotherWizardView.xml"})
  public StandardController AnotherWizardView() {
    StandardController rssc = new StandardController();
    rssc.setMessageSource((MessageSource) Services.getBean("WizardMessageSource"));
    rssc.setModelProvider((IModelProvider) Services.getBean("RootStructureModelLoader"));
    rssc.addViewDefinition("/io/github/jsoagger/jfxcore/demoapp/desktop/wizard/AnotherWizardView.json");
    return rssc;
  }


  @Bean
  @Named("CreateDemoElementAction")
  public CreateDemoElementAction createDemoElementAction() {
    CreateDemoElementAction a = new CreateDemoElementAction();
    return a;
  }

  @Bean
  @Named("CreateDemoElementActionHandler")
  public WizardStepFinishActionHandler createDemoElementActionHandler() {
    WizardStepFinishActionHandler a = new WizardStepFinishActionHandler();
    a.getActions().add((IAction) Services.getBean("CreateDemoElementAction"));
    return a;
  }


  @Bean
  @Named("CreateDemoElementActionHL")
  public CreateDemoElementActionHL createDemoElementActionHL() {
    CreateDemoElementActionHL a = new CreateDemoElementActionHL();
    return a;
  }


  @Bean
  @Named("CreateDemoElementActionHandlerHL")
  public WizardStepFinishActionHandler createDemoElementActionHandlerHL() {
    WizardStepFinishActionHandler a = new WizardStepFinishActionHandler();
    a.getActions().add((IAction) Services.getBean("CreateDemoElementActionHL"));
    return a;
  }
}
