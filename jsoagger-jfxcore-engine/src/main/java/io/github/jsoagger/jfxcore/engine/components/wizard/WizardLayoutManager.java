/**
 *
 */
package io.github.jsoagger.jfxcore.engine.components.wizard;

import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Use layout manager when need to forward edit row inside wizard. In simple case,
 * wizard do not need layout manager.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class WizardLayoutManager implements IViewLayoutManager {

  private StackPane layout = new StackPane();
  private Node center = null;

  /**
   * Constructor
   */
  public WizardLayoutManager() {
    layout.getStyleClass().add("ep-wizard-layout-manager");
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.view.IViewLayoutManager#layout(io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable)
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    center = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    layout.getChildren().add(center);
  }

  /*
   * (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.view.IViewLayoutManager#pushContent(javafx.scene.Node)
   */
  @Override
  public void pushContent(Node node) {
    IViewLayoutManager.super.pushContent(node);
    layout.getChildren().clear();
    layout.getChildren().add(node);
  }

  /*
   * (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.view.IViewLayoutManager#popContent()
   */
  @Override
  public void popContent() {
    IViewLayoutManager.super.popContent();
    layout.getChildren().clear();
    layout.getChildren().add(center);
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.view.IViewLayoutManager#getDisplay()
   */
  @Override
  public Node getDisplay() {
    return center;
  }
}
