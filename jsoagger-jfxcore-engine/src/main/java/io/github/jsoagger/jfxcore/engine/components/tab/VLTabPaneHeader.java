/**
 *
 */
package io.github.jsoagger.jfxcore.engine.components.tab;

import java.util.Iterator;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class VLTabPaneHeader extends StackPane implements IBuildable {


  private HBox titleContainer = new HBox();

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IBuildable#buildFrom(io.github.jsoagger.jfxcore.api.IJSoaggerController, io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML)
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    NodeHelper.styleClassSetAll(this, configuration, "styleClass", "ep-tab-pane-header-wrapper");
    titleContainer.getStyleClass().add("ep-tab-pane-header-content-wrapper");
    getChildren().add(titleContainer);
    structureModelUpdated(FXCollections.observableArrayList());

    if(((AbstractViewController)controller).getStructureContent()!= null) {
      FullTableStructureController c = (FullTableStructureController) ((AbstractViewController)controller).getStructureContent().getCurrentEditingTableStructure();
      AbstractTableStructure ts = (AbstractTableStructure) c.processedElementProperty().get();
      ts.childTree().addListener((ListChangeListener<OperationData>) e -> {
        Platform.runLater(()->structureModelUpdated(ts.childTree()));
      });
    }
    else {
      ((AbstractViewController)controller).getStructureContent()
      .currentEditingTableStructureProperty()
      .addListener(new ChangeListener<AbstractViewController>() {

        @Override
        public void changed(ObservableValue<? extends AbstractViewController> observable, AbstractViewController oldValue, AbstractViewController newValue) {
          FullTableStructureController c = (FullTableViewController) newValue;
          AbstractTableStructure ts = (AbstractTableStructure) c.processedElementProperty().get();
          ts.childTree().addListener((ListChangeListener<OperationData>) e -> {
            Platform.runLater(()->structureModelUpdated(ts.childTree()));
          });
        }
      });
    }
  }

  protected void structureModelUpdated(ObservableList<OperationData> observableList) {
    titleContainer.getChildren().clear();
    Iterator<OperationData> it = observableList.iterator();

    if (it.hasNext()) {
      titleContainer.getChildren().add(NodeHelper.getSep("ep-tab-pane-header-structure-navigation-label-each"));
      while (it.hasNext()) {
        Text l = new Text();

        OperationData opd = it.next();

        l.setText(NodeHelper.getDisplayName(opd));
        l.getStyleClass().add("ep-tab-pane-header-structure-navigation-label-each");
        titleContainer.getChildren().add(l);

        if (it.hasNext()) {
          titleContainer.getChildren().add(NodeHelper.getSep("ep-tab-pane-header-structure-navigation-label-each"));
        }
      }
    }
    else {
      Text l = new Text("Home");
      l.getStyleClass().add("ep-tab-pane-header-structure-navigation-label-each");
      titleContainer.getChildren().addAll(NodeHelper.getSep("ep-tab-pane-header-structure-navigation-label-each"), l);
    }
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IDisplayable#getDisplay()
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
