/**
 *
 */
package io.github.jsoagger.jfxcore.engine.components.layoutproc;

import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.scene.Node;

public class ToolBarProcessor implements IComponentProcessor {

  @Override
  public Node process(IJSoaggerController controller, VLViewComponentXML contentCfg) {
    return NodeHelper.jfxButton("Save modifications");
  }

}
