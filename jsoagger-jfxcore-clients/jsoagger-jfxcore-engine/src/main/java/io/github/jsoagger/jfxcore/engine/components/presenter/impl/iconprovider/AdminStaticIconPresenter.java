/**
 *
 */
package io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class AdminStaticIconPresenter implements ModelIconPresenter {

  final StackPane graphic = new StackPane();

  /**
   * Constructor
   */
  public AdminStaticIconPresenter() {
    graphic.minHeightProperty().bind(graphic.prefHeightProperty());
    graphic.minWidthProperty().bind(graphic.prefWidthProperty());

    graphic.maxHeightProperty().bind(graphic.prefHeightProperty());
    graphic.maxWidthProperty().bind(graphic.prefWidthProperty());

    graphic.setPrefWidth(42);
    graphic.setPrefHeight(42);

    Label icon = new Label();
    IconUtils.setFontIcon("fa-gears:30", icon);
    icon.getGraphic().getStyleClass().add("grey-clear-ikonli");

    graphic.setAlignment(Pos.CENTER);
    graphic.getChildren().add(icon);
    graphic.setStyle("-fx-background-color:-grey-color-50;-fx-border-radius:10;"
        + "-fx-background-radius:10;");
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration) {
    return graphic;
  }

  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return graphic;
  }

  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    OperationData d = (OperationData) forModel;
    if(d != null) {
      String ikonli = (String) d.getAttributes().get("ikonli");
      if(StringUtils.isNotBlank(ikonli)) {
        Label icon = new Label();
        IconUtils.setFontIcon(ikonli, icon);
        icon.getGraphic().getStyleClass().add("grey-clear-ikonli");
        graphic.getChildren().clear();
        graphic.getChildren().add(icon);
      }
    }
    return graphic;
  }


  @Override
  public void setCell(IndexedCell cell) {

  }
}
