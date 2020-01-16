/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;


import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.security.ILoginSessionHolder;
import io.github.jsoagger.jfxcore.api.security.IRootContext;
import io.github.jsoagger.jfxcore.api.security.IUserContext;
import io.github.jsoagger.jfxcore.engine.components.security.LoginSessionHolder;
import io.github.jsoagger.jfxcore.engine.components.security.RootContext;
import io.github.jsoagger.jfxcore.engine.components.security.UIContext;
import io.github.jsoagger.jfxcore.engine.components.security.UserContext;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@BeansProvider
public class CoreAuthentificationBeansProvider {

  @Bean
  @Singleton
  @Named("LoginSessionHolder")
  public ILoginSessionHolder loginSessionHolder() {
    return new LoginSessionHolder();
  }

  @Bean
  @Singleton
  @Named("RootContext")
  public IRootContext rootContext() {
    IRootContext context = new RootContext();
    context.setLoginSessionHolder((ILoginSessionHolder) BeanFactory.instance().getBean("LoginSessionHolder"));
    context.init();
    return context;
  }

  @Bean
  @Singleton
  @Named("ResourceUtils")
  public ResourceUtils resourceUtils() {
    return new ResourceUtils();
  }

  @Bean
  @Named("viewContext")
  public UIContext viewContext() {
    UIContext context = new UIContext();
    context.setRootContext((IRootContext) BeanFactory.instance().getBean("RootContext"));
    context.setUserContext((IUserContext) BeanFactory.instance().getBean("UserContext"));
    return context;
  }

  @Bean
  @Named("UserContext")
  public UserContext userContext() {
    UserContext userContext = new UserContext();
    return userContext;
  }

  @Bean
  @Named("StructureContentController")
  public StructureContentController structureContentController() {
    StructureContentController structureContentController = new StructureContentController();
    return structureContentController;
  }
}
