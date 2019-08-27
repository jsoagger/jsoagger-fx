/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers.integration;

import java.text.MessageFormat;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.jfxcore.api.css.IStylesheetManager;
import io.github.jsoagger.jfxcore.api.services.ApplicationContextService;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class JSoaggerFXApplicationContextService implements ApplicationContextService {

  /**
   * Constructor
   */
  public JSoaggerFXApplicationContextService() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public  IStylesheetManager getStylesheetManager() {
    return (IStylesheetManager) getBean("styleSheetManager");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public  void dispatchEvent(Object event) {
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Object getBean(String identifier) {
    if (StringUtils.isEmpty(identifier)) {
      return null;
    }

    try {
      Object bean =  BeanFactory.instance().getBean(identifier);
      if(bean != null) {
        return bean;
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
    }

    throw new IllegalArgumentException(MessageFormat.format("Bean with name {0} not found in application context", identifier));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void publishEvent(Object event) {
  }
}
