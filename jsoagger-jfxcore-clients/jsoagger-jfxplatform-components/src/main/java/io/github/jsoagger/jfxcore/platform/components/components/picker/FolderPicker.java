/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.components.picker;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.input.AbstractInputComponent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class FolderPicker extends AbstractInputComponent{

  private Node display;

  /**
   * Constructore
   */
  public FolderPicker() {
    super();
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    String view = "FolderPickerFlowView";
    StandardViewController c = StandardViewUtils.forId((RootStructureController) controller.getRootStructure(), view);
    display = c.processedView();
    c.setParent((AbstractViewController) controller);

    c.selectedElementProperty().addListener(new ChangeListener<OperationData>() {

      @Override
      public void changed(ObservableValue<? extends OperationData> observable, OperationData oldValue, OperationData newValue) {
        if(newValue == null) {

        }
        else {
          String folderOid = (String) newValue.getAttributes().get("fullId");
          String folderPath = (String) newValue.getAttributes().get("path");
          owner.initialInternalValueProperty().set(folderPath);
          owner.currentInternalValueProperty().set(folderPath);
        }
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return display;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getComponent() {
    return display;
  }
}
