/**
 *
 */
package io.github.jsoagger.jfxcore.engine.components.table.cell;

import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.table.row.EpTableRow;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class TableRowMultipleActionPresenter extends CellPresenterImpl {

  /**
   * Default Constructor
   */
  public TableRowMultipleActionPresenter() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object item) {
    HBox node = new HBox();
    node.setStyle("-fx-alignment:CENTER; -fx-spacing:4");
    if(configuration.hasSubComponent()) {
      for(VLViewComponentXML c: configuration.getSubcomponents()) {
        Node n = _present(controller, c, item);
        node.getChildren().add(n);
      }
    }

    return node;
  }

  public Node _present(IJSoaggerController controller, VLViewComponentXML configuration, Object item) {
    Hyperlink label = new Hyperlink();
    String rowCriteria = configuration.getPropertyValue("rowCriteria");
    boolean show = true;
    if(StringUtils.isNotBlank(rowCriteria)) {
      EpTableCell c = (EpTableCell) cell;
      EpTableRow r = (EpTableRow) c.getTableRow();
      show = r.evaluateCriteria(rowCriteria);
    }

    if(show) {
      IconUtils.setIcon(label, configuration);
      if (label.getGraphic() != null) {
        label.getGraphic().getStyleClass().add("grey-flat-ikonli");
      }

      NodeHelper.setTitle(label, configuration, (AbstractViewController) controller);
      NodeHelper.setStyleClass(label, configuration, "buttonStyleClass", true);
      ComponentToButtonBaseHelper.setOnAction(configuration, label, (AbstractViewController) controller, (OperationData) item);
    } else {
      label.setManaged(false);
      label.setVisible(false);
    }

    return label;
  }
}
