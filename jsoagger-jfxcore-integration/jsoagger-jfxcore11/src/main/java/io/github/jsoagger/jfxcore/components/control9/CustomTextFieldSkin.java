/*-
 * ========================LICENSE_START=================================
 * JSoagger 
 * %%
 * Copyright (C) 2019 JSOAGGER
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package io.github.jsoagger.jfxcore.components.control9;


import javafx.beans.property.ObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.text.HitInfo;

public abstract class CustomTextFieldSkin extends TextFieldSkin {

  private static final PseudoClass HAS_NO_SIDE_NODE = PseudoClass.getPseudoClass("no-side-nodes"); //$NON-NLS-1$
  private static final PseudoClass HAS_LEFT_NODE = PseudoClass.getPseudoClass("left-node-visible"); //$NON-NLS-1$
  private static final PseudoClass HAS_RIGHT_NODE = PseudoClass.getPseudoClass("right-node-visible"); //$NON-NLS-1$

  private Node left;
  private StackPane leftPane;
  private Node right;
  private StackPane rightPane;

  private final TextField control;

  public CustomTextFieldSkin(final TextField control) {
    super(control);

    this.control = control;
    updateChildren();

    registerChangeListener(leftProperty(), e -> updateChildren());
    registerChangeListener(rightProperty(), e -> updateChildren());
  }

  public abstract ObjectProperty<Node> leftProperty();
  public abstract ObjectProperty<Node> rightProperty();

  private void updateChildren() {
    Node newLeft = leftProperty().get();
    if (newLeft != null) {
      getChildren().remove(leftPane);
      leftPane = new StackPane(newLeft);
      leftPane.setAlignment(Pos.CENTER_LEFT);
      leftPane.getStyleClass().add("left-pane"); //$NON-NLS-1$
      getChildren().add(leftPane);
      left = newLeft;
    }

    Node newRight = rightProperty().get();
    if (newRight != null) {
      getChildren().remove(rightPane);
      rightPane = new StackPane(newRight);
      rightPane.setAlignment(Pos.CENTER_RIGHT);
      rightPane.getStyleClass().add("right-pane"); //$NON-NLS-1$
      getChildren().add(rightPane);
      right = newRight;
    }

    control.pseudoClassStateChanged(HAS_LEFT_NODE, left != null);
    control.pseudoClassStateChanged(HAS_RIGHT_NODE, right != null);
    control.pseudoClassStateChanged(HAS_NO_SIDE_NODE, left == null && right == null);
  }

  @Override protected void layoutChildren(double x, double y, double w, double h) {
    final double fullHeight = h + snappedTopInset() + snappedBottomInset();

    final double leftWidth = leftPane == null ? 0.0 : snapSize(leftPane.prefWidth(fullHeight));
    final double rightWidth = rightPane == null ? 0.0 : snapSize(rightPane.prefWidth(fullHeight));

    final double textFieldStartX = snapPosition(x) + snapSize(leftWidth);
    final double textFieldWidth = w - snapSize(leftWidth) - snapSize(rightWidth);

    super.layoutChildren(textFieldStartX, 0, textFieldWidth, fullHeight);

    if (leftPane != null) {
      final double leftStartX = 0;
      leftPane.resizeRelocate(leftStartX, 0, leftWidth, fullHeight);
    }

    if (rightPane != null) {
      final double rightStartX = rightPane == null ? 0.0 : w - rightWidth + snappedLeftInset();
      rightPane.resizeRelocate(rightStartX, 0, rightWidth, fullHeight);
    }
  }

  @Override
  public HitInfo getIndex(double x, double y) {
    /**
     * This resolves https://bitbucket.org/controlsfx/controlsfx/issue/476
     * when we have a left Node and the click point is badly returned
     * because we weren't considering the shift induced by the leftPane.
     */
    final double leftWidth = leftPane == null ? 0.0 : snapSize(leftPane.prefWidth(getSkinnable().getHeight()));
    return super.getIndex(x - leftWidth, y);
  }

  @Override
  protected double computePrefWidth(double h, double topInset, double rightInset, double bottomInset, double leftInset) {
    final double pw = super.computePrefWidth(h, topInset, rightInset, bottomInset, leftInset);
    final double leftWidth = leftPane == null ? 0.0 : snapSize(leftPane.prefWidth(h));
    final double rightWidth = rightPane == null ? 0.0 : snapSize(rightPane.prefWidth(h));

    return pw + leftWidth + rightWidth;
  }

  @Override
  protected double computePrefHeight(double w, double topInset, double rightInset, double bottomInset, double leftInset) {
    final double ph = super.computePrefHeight(w, topInset, rightInset, bottomInset, leftInset);
    final double leftHeight = leftPane == null ? 0.0 : snapSize(leftPane.prefHeight(-1));
    final double rightHeight = rightPane == null ? 0.0 : snapSize(rightPane.prefHeight(-1));

    return Math.max(ph, Math.max(leftHeight, rightHeight));
  }
  //
  //    @Override
  //    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
  //        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
  //}
}
