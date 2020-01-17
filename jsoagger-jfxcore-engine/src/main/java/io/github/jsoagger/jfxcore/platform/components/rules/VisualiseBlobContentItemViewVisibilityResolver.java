package io.github.jsoagger.jfxcore.platform.components.rules;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.accessrule.AbstractRuleResolver;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class VisualiseBlobContentItemViewVisibilityResolver extends AbstractRuleResolver {

  /**
   * Constructur
   */
  public VisualiseBlobContentItemViewVisibilityResolver() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UIAccessRule isAccessible(IJSoaggerController controller, VLViewComponentXML compConfig) {
    OperationData data = (OperationData) get("forModel");
    String type = (String) data.getAttributes().get("contentLocationType");
    if("blob".equalsIgnoreCase(type)) {
      return UIAccessRule.SHOW;
    }

    return UIAccessRule.HIDDEN;
  }
}
