package io.github.jsoagger.jfxcore.engine.components.tab;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Tab pane where tab items size are equals.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class MobileVLTabpane extends VLTabpane {

  /**
   */
  public MobileVLTabpane() {
    super();
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    leftTabItemsContainer.setVisible(false);
    rightTabItemsContainer.setVisible(false);
    leftTabItemsContainer.setManaged(false);
    rightTabItemsContainer.setManaged(false);
    centerTabItemsContainer.prefWidthProperty().bind(tabItemsContainer.prefWidthProperty().multiply(0.9));

    // todo handle with max according to running platform
    if(ViewStructure.instance().platformSceneWidth().get() > 100) {
      double w = ViewStructure.instance().platformSceneWidth().get();
      updateTabsSize();
    }

    ViewStructure.instance().platformSceneWidth().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        updateTabsSize();
      }});
  }

  protected void updateTabsSize() {
    double w = ViewStructure.instance().platformSceneWidth().get();
    double d = 0.9 * (w / getTabs().size());
    for(VLTab tab: getTabs()) {
      tab.setMaxWidth(d);
      tab.setMinWidth(d);
    }
  }

  @Override
  protected void addTab(VLTab tab, TabSide side) {
    NodeHelper.setHgrow(tab);
    super.addTab(tab, TabSide.CENTER);
  }

  @Override
  public void selectTab(int i) {
    super.selectTab(i);
  }

  @Override
  public void selectTab(VLTab tab) {
    super.selectTab(tab);
  }

  @Override
  public void selectTab(String id) {
    super.selectTab(id);
  }
}
