/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers.integration;

import java.util.List;

import io.github.jsoagger.jfxcore.api.services.LocalComponentsService;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class JsonLocalCompsService   implements LocalComponentsService {

  /**
   * {@inheritDoc}
   */
  @Override
  public VLViewComponentXML getComponent(String identifier) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getConfigurationFiles() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfigurationFiles(List<String> configurationFiles) {
  }
}
