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

package io.github.jsoagger.jfxcore.demoapp.map;



import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoMapController extends StandardController {

  AnchorPane layout = new AnchorPane();
  WebEngine webEngine;

  /**
   * Constructor
   */
  public DemoMapController() {}

  @Override
  protected void process() {
    super.process();
    mapInitialized();
    processedView(layout);
  }

  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return layout;
    }
    return super.getNodeOnPosition(position);
  }

  /**
   * {@inheritDoc}
   */
  public void mapInitialized() {
    Runnable initWebView = () -> {
      try {
        WebView browser = new WebView();
        webEngine = browser.getEngine();


        AnchorPane.setTopAnchor(browser, 0.0);
        AnchorPane.setLeftAnchor(browser, 0.0);
        AnchorPane.setBottomAnchor(browser, 0.0);
        AnchorPane.setRightAnchor(browser, 0.0);
        layout.getChildren().add(browser);

        browser.widthProperty().addListener(e -> mapResized());
        browser.heightProperty().addListener(e -> mapResized());

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
          @Override
          public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
            if (newState == Worker.State.SUCCEEDED) {
              // initialiseScript();
              // setInitialized(true);
              // fireMapInitializedListeners();

            }
          }
        });
        webEngine.load(getClass().getResource("/io/github/jsoagger/jfxcore/demoapp/desktop/map/googlemap.html").toExternalForm());
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
      } finally {
        // latch.countDown();
      }
    };

    if (Platform.isFxApplicationThread()) {
      initWebView.run();
    } else {
      Platform.runLater(initWebView);
    }
  }

  private void mapResized() {
    // webEngine.executeScript("google.maps.event.trigger(" + map.getVariableName() + ", 'resize')");
  }

}
