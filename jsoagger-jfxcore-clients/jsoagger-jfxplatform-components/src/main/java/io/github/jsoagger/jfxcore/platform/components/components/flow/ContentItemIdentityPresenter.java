/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.components.flow;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ContentItemIdentityPresenter implements ModelIdentityPresenter {

  Label identity = new Label();

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter#provideIdentityOf(io.github.jsoagger.jfxcore.api.IJSoaggerController, io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML)
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component) {
    identity.setText(identityOf(controller, component));
    return identity;
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter#identityOf(io.github.jsoagger.jfxcore.api.IJSoaggerController, io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML)
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML component) {
    OperationData d = ((AbstractViewController) controller).getOpData();
    String role = (String) d.getAttributes().get("role");

    // url content
    String contentLocationType = (String) d.getAttributes().get("contentLocationType");
    if("HTTP_URL".equalsIgnoreCase(contentLocationType) || "URL".equalsIgnoreCase(contentLocationType)) {
      return (String) d.getAttributes().get("externalLocation");
    }

    // file
    if("PRIMARY".equalsIgnoreCase(role)) {
      return (String) d.getAttributes().get("name");
    }

    return (String) d.getAttributes().get("name");
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter#identityOf(io.github.jsoagger.jfxcore.api.IJSoaggerController, io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML, java.lang.Object)
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    OperationData d = (OperationData) forModel;
    String role = (String) d.getAttributes().get("role");

    // url content
    String contentLocationType = (String) d.getAttributes().get("contentLocationType");
    if("HTTP_URL".equalsIgnoreCase(contentLocationType) || "URL".equalsIgnoreCase(contentLocationType)) {
      return (String) d.getAttributes().get("externalLocation");
    }

    // file
    if("PRIMARY".equalsIgnoreCase(role)) {
      return (String) d.getAttributes().get("name");
    }

    return (String) d.getAttributes().get("name");
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter#provideIdentityOf(io.github.jsoagger.jfxcore.api.IJSoaggerController, io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML, java.lang.Object)
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    identity.setText(identityOf(controller, configuration, forModel));
    return identity;
  }

}
