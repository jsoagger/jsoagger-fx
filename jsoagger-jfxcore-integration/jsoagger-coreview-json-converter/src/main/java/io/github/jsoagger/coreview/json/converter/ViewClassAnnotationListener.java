/**
 *
 */
package io.github.jsoagger.coreview.json.converter;

import java.util.HashSet;
import java.util.Set;

import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;
import io.github.jsoagger.core.ioc.api.annotations.View;

/**
 * Utility for filtering classes to be scan for custom annotations discover.
 *
 * @author Administrator
 *
 */
public class ViewClassAnnotationListener implements ClassAnnotationDiscoveryListener {

  private final Set<Class<?>> clazzes = new HashSet<>();

  /**
   * Constructor
   */
  public ViewClassAnnotationListener() {
  }

  /**
   * @return
   */
  public Set<Class<?>> getClasses() {
    return clazzes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void discovered(String clazz, String annotation) {
    try {
      clazzes.add(Class.forName(clazz));
    } catch (final ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] supportedAnnotations() {
    return new String[] { View.class.getName()};
    //GlobalComponentsToJson.class.getName(),
    //CopyResources.class.getName()};
  }
}

