/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;


import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.action.LogoutAction;
import io.github.jsoagger.jfxcore.engine.components.wizard.action.BackWizardAction;
import io.github.jsoagger.jfxcore.engine.components.wizard.action.HideWizardAction;
import io.github.jsoagger.jfxcore.engine.components.wizard.action.NextWizardAction;
import io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler.WizardStepBackActionHandler;
import io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler.WizardStepCancelActionHandler;
import io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler.WizardStepFinishActionHandler;
import io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler.WizardStepNextActionHandler;
import io.github.jsoagger.jfxcore.engine.controller.login.action.LoginAction;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreActionsBeanProvider {

  @Bean
  @Named("LoginActionHandler")
  public WizardStepFinishActionHandler LoginActionHandler() {
    WizardStepFinishActionHandler w = new WizardStepFinishActionHandler();
    w.getActions().add((IAction) Services.getBean("LoginAction"));
    w.getActions().add((IAction)Services.getBean("HideWizardAction"));
    return w;
  }

  @Bean
  @Named("WizardStepNextActionHandler")
  public WizardStepNextActionHandler WizardStepNextActionHandler() {
    WizardStepNextActionHandler w = new WizardStepNextActionHandler();
    w.getActions().add((IAction)Services.getBean("NextWizardAction"));
    return w;
  }


  @Bean
  @Named("WizardStepBackActionHandler")
  public WizardStepBackActionHandler WizardStepBackActionHandler() {
    WizardStepBackActionHandler w = new WizardStepBackActionHandler();
    w.getActions().add((IAction)Services.getBean("BackWizardAction"));
    return w;
  }

  @Bean
  @Named("WizardStepFinishActionHandler")
  public WizardStepFinishActionHandler WizardStepFinishActionHandler() {
    WizardStepFinishActionHandler w = new WizardStepFinishActionHandler();
    w.getActions().add((IAction)Services.getBean("BackWizardAction"));
    return w;
  }


  @Bean
  @Named("WizardStepCancelActionHandler")
  public WizardStepCancelActionHandler WizardStepCancelActionHandler() {
    WizardStepCancelActionHandler w = new WizardStepCancelActionHandler();
    return w;
  }


  @Bean
  @Named("CreateSavedSearchButtonActionHandler")
  public WizardStepFinishActionHandler CreateSavedSearchButtonActionHandler() {
    WizardStepFinishActionHandler w = new WizardStepFinishActionHandler();
    w.getActions().add((IAction)Services.getBean("CreateSavedSearchAction"));
    return w;
  }

  @Bean
  @Named("LoginAction")
  public LoginAction LoginAction() {
    LoginAction w = new LoginAction();
    w.setLoginOperation((IOperation) Services.getBean("LoginOperation"));
    return w;
  }

  @Bean
  @Named("LogoutAction")
  public LogoutAction LogoutAction() {
    LogoutAction w = new LogoutAction();
    w.setLogoutOperation((IOperation) Services.getBean("LogoutOperation"));
    return w;
  }

  @Bean
  @Named("HideWizardAction")
  public HideWizardAction HideWizardAction() {
    HideWizardAction w = new HideWizardAction();
    return w;
  }

  @Bean
  @Named("BackWizardAction")
  public BackWizardAction BackWizardAction() {
    BackWizardAction w = new BackWizardAction();
    return w;
  }

  @Bean
  @Named("NextWizardAction")
  public NextWizardAction NextWizardAction() {
    NextWizardAction w = new NextWizardAction();
    return w;
  }
}
