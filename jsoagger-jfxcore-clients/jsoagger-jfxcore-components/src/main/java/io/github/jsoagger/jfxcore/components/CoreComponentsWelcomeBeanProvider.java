/**
 *
 */
package io.github.jsoagger.jfxcore.components;


import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.ConvertViewToJson;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.components.actions.WelcomeButtonAction;
import io.github.jsoagger.jfxcore.components.welcome.WelcomeController;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
@ConvertViewToJson
public class CoreComponentsWelcomeBeanProvider {


  @Bean
  @Named("WelcomeButtonAction")
  public WelcomeButtonAction welcomeButtonAction() {
    return new WelcomeButtonAction();
  }

  @Bean
  @Named("WelcomeMessageSource")
  public MessageSource WelcomeMessageSource() {
    MessageSource m = new MessageSource();
    m.getBasenames().add("io.github.jsoagger.jfxcore.components.welcome.message");
    return m;
  }


  @Bean
  @Singleton
  @Named("WelcomeView")
  @View(locations= {"/io/github/jsoagger/jfxcore/components/welcome/WelcomeView.xml"})
  public WelcomeController welcomeView() {
    WelcomeController controller = new WelcomeController();
    controller.setMessageSource(WelcomeMessageSource());
    controller.addViewDefinition("/io/github/jsoagger/jfxcore/components/welcome/WelcomeView.json");
    return controller;
  }

  @Bean
  @Singleton
  @Named("WelcomeRSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/components/welcome/WelcomeRSContentView.xml"})
  public RootStructureContentController welcomeRSContentView() {
    RootStructureContentController controller = new RootStructureContentController();
    controller.setMessageSource(WelcomeMessageSource());
    controller.addViewDefinition("/io/github/jsoagger/jfxcore/components/welcome/WelcomeRSContentView.json");
    return controller;
  }

  @Bean
  @Singleton
  @Named("WelcomeRSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/components/welcome/WelcomeRSView.xml"})
  public RootStructureController welcomeRSView() {
    DoubleHeaderRootStructureController controller = new DoubleHeaderRootStructureController();
    controller.setMessageSource(WelcomeMessageSource());
    controller.addViewDefinition("/io/github/jsoagger/jfxcore/components/welcome/WelcomeRSView.json");
    return controller;
  }
}
