/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.controller;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FlowWithTableContentViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullFlowViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.events.LinkCreatedEvent;
import io.github.jsoagger.jfxcore.engine.events.LinkDeletedEvent;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StructureManagementController extends FlowWithTableContentViewController {

  private SimpleObjectProperty<OperationData> rootModel = new SimpleObjectProperty<>();

  /**
   * Constructor
   */
  public StructureManagementController() {
    super();
    // action from center table structure
    registerListener(CoreEvent.DeleteObjectFromStructureEvent);

    // actions from search pane
    registerListener(CoreEvent.LinkCreatedEvent);
    registerListener(CoreEvent.LinkDeletedEvent);
  }

  @Override
  protected void buildPanes() {
    doBuildLeftPane();
    doBuildCenterPane();
    doBuildRightPane();
  }

  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if(e.isA(CoreEvent.DeleteObjectFromStructureEvent)) {
      OperationData data = (OperationData) e.getModel();

      // remove it from left and center pane is not done yet!
      if(leftPaneController instanceof FullTableViewController) {
        AbstractTableStructure ts =(AbstractTableStructure) ((FullTableViewController)leftPaneController).processedElement();
        ts.refreshDatas();
      }

      // remove it from left and center pane is not done yet!
      if(leftPaneController instanceof FullFlowViewController) {
        AbstractTableStructure ts =(AbstractTableStructure) ((FullFlowViewController)leftPaneController).processedElement();
        ts.getItems().remove(data);
      }

      if(centerViewController instanceof FullTableViewController) {
        AbstractTableStructure ts =(AbstractTableStructure) ((FullTableViewController)centerViewController).processedElement();
        ts.getItems().remove(data);
      }
    }
    else if(e.isA(CoreEvent.LinkCreatedEvent)) {
      OperationData roleB = (OperationData) e.getModel();
      OperationData roleA = ((LinkCreatedEvent)e).getRoleA();

      if(roleA.fullIdEquals(getLastChildTree())) {
        if(centerViewController instanceof FullTableViewController) {
          AbstractTableStructure ts =(AbstractTableStructure) ((FullTableViewController)centerViewController).processedElement();
          ts.getItems().add(roleB);
        }

        if(leftPaneController instanceof FullTableViewController) {
          AbstractTableStructure ts =(AbstractTableStructure) ((FullTableViewController)leftPaneController).processedElement();
          ts.getItems().add(roleB);
        }

        if(leftPaneController instanceof FullFlowViewController) {
          AbstractTableStructure ts =(AbstractTableStructure) ((FullFlowViewController)leftPaneController).processedElement();
          ts.getItems().add(roleB);
        }
      }
    }
    else if(e.isA(CoreEvent.LinkDeletedEvent)) {
      OperationData roleB = (OperationData) e.getModel();
      OperationData roleA = ((LinkDeletedEvent)e).getRoleA();

      if(leftPaneController.getOpData().fullIdEquals(roleA)) {
        FullTableViewController tc = getTableController();
        AbstractTableStructure ts =(AbstractTableStructure) tc.processedElement();
        ts.getItems().remove(roleB);
      }
    }
  }

  @Override
  public Node getDisplayIdentity() {
    if(identityPresenter != null) {
      Node node = identityPresenter.provideIdentityOf(this, getRootComponent());
      return node;
    }

    OperationData data = getOpData();
    Label identity = new Label("Structure of " + data.getMasterAttributes().get("name"));
    identity.setStyle("-fx-text-fill:white;-fx-font-size:12px;");
    return identity;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void doBuildLeftPane() {
    super.doBuildLeftPane();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doBuildRightPane() {
    super.doBuildRightPane();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void doBuildCenterPane() {
    super.doBuildCenterPane();
    AbstractTableStructure ts = (AbstractTableStructure) leftPaneController.processedElement();
    AbstractTableStructure ts2 = (AbstractTableStructure) getTableController().processedElement();

    leftPaneController.selectedElementProperty().addListener(new ChangeListener<OperationData>() {

      @Override
      public void changed(ObservableValue<? extends OperationData> observable, OperationData oldValue, OperationData newValue) {

        if(newValue != null) {
          ts2.childTree().setAll(ts.childTree());
          SingleResult sr = new SingleResult();
          sr.setData(newValue);
          getTableController().setModel(sr);
          getTableController().refreshDatas();
        }
        else {
          getTableController().getTableStructure().setNoContent();
        }
      }});

    getStructureContent().setCurrentEditingTableStructure(getTableController());
  }

  /**
   * Get last item in the child tree. Return null is child tree is empty, else return the last item.
   * Last item is the parent of current displayed datas.
   *
   * @return
   */
  public OperationData getLastChildTree() {
    AbstractTableStructure ts2 = (AbstractTableStructure) leftPaneController.processedElement();
    if( ts2.childTree().size() > 0) return  ts2.childTree().get( ts2.childTree().size() -1);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void leftPaneSelectedElementChange(OperationData selectedModel) {

  }

  /**
   * In this configuration the table is inside the tabpane,
   * so we must go inside it to get the table
   * @return
   */
  private FullTableViewController getTableController() {
    if(centerViewController instanceof StandardTabPaneController) {
      StandardTabPaneController tpc =(StandardTabPaneController) centerViewController;
      for(AbstractViewController c: tpc.getBuildedTabsController()) {
        if(c instanceof FullTableViewController) {
          return (FullTableViewController) c;
        }
      }
    }
    else {
      return (FullTableViewController) centerViewController;
    }

    return null;
  }
}

