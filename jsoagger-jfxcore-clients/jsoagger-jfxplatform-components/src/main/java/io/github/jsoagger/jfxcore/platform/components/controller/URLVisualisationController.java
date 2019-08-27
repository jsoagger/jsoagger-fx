/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.controller;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class URLVisualisationController extends StandardViewController {

  private StackPane layout = new StackPane();

  /**
   * Constructor
   */
  public URLVisualisationController() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();

    try {
      String location = "www.google.com";
      OperationData data = getStructureContent().formModelDataProperty().get();

      javafx.application.Platform.runLater(()->{
        WebView webView = new WebView();
        webView.getEngine().load(location);
        layout.getChildren().add(webView);
      });

    }catch (Exception e) {
      e.printStackTrace();
      unsupported();
    }

    processedView(layout);
  }


  protected void unsupported() {
    Label label = new Label("Unsupported content format");
    IconUtils.setFontIcon("hws-document-error:80", label);
    layout.getChildren().add(label);
  }
}
