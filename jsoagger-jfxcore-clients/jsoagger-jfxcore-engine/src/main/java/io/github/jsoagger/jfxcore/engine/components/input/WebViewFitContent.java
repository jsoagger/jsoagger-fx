/**
 *
 */
package io.github.jsoagger.jfxcore.engine.components.input;

import java.util.Set;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Ramilafananana VONJISOA
 *
 */
public class WebViewFitContent extends Region {

  WebView webview = null;
  WebEngine webEngine = null;

  public WebViewFitContent(String content) {

    Platform.runLater(() ->{

      webview = new WebView();
      webEngine = webview.getEngine();

      webview.setPrefHeight(5000);

      widthProperty().addListener(new ChangeListener<Object>() {
        @Override
        public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
          Double width = (Double)newValue;
          webview.setPrefWidth(width);
          adjustHeight();
        }
      });



      webview.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
        @Override
        public void changed(ObservableValue<? extends State> arg0, State oldState, State newState)         {
          if (newState == State.SUCCEEDED) {
            adjustHeight();
          }
        }
      });


      webview.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
        @Override
        public void onChanged(Change<? extends Node> change) {
          Set<Node> scrolls = webview.lookupAll(".scroll-bar");
          for (Node scroll : scrolls) {
            scroll.setVisible(false);
          }
        }
      });

      setContent(content);
      getChildren().add(webview);
    });
  }

  public void setContent(final String url) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        webEngine.load(url);
        Platform.runLater(new Runnable(){
          @Override
          public void run() {
            adjustHeight();
          }
        });
      }
    });
  }


  @Override
  protected void layoutChildren() {
    double w = getWidth();
    double h = getHeight();
    layoutInArea(webview,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
  }

  private void adjustHeight() {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          Object result = webEngine.executeScript("document.getElementById('mydiv').offsetHeight");
          result = 2000;
          if (result instanceof Integer) {
            Integer i = (Integer) result;
            double height = new Double(i);
            height = height + 20;
            webview.setPrefHeight(height);
            webview.getPrefHeight();
          }
        } catch (Exception e) { }
      }
    });
  }
}
