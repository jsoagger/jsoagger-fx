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

package io.github.jsoagger.jfxcore.engine.components.input;




import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputWebView extends AbstractInputComponent {

  private Browser browser = null;

  /**
   * Constructor
   */
  public InputWebView() {
    super();
    browser = new Browser();
    NodeHelper.setHgrow(browser);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    String url = configuration.getPropertyValue("url");
    Platform.runLater(() ->
    {
      browser.webEngine.load(controller.getClass().getResource(url).toExternalForm());
      browser.webEngine.documentProperty().addListener((prop, oldDoc, newDoc) -> {
        String heightText = browser.webEngine.executeScript(
            "window.getComputedStyle(document.body, null).getPropertyValue('height')"
            )
            .toString();

        System.out.println("heighttext: " + heightText);
        Double height = Double.parseDouble(heightText.replace("px", "")) + 10 ;  // <- Why are this 15.0 required??
        //browser.browser.setMinHeight(height);
      });
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return browser;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return browser;
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public class Browser extends Region {

    WebView browser;
    WebEngine webEngine;


    public Browser() {
      super();
      Platform.runLater(() -> {
        browser = new WebView();
        webEngine = browser.getEngine();
        webEngine.setJavaScriptEnabled(true);
        getChildren().add(browser);


        // Update the stage title when a new web page title is available
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
        {
          @Override
          public void changed(ObservableValue<? extends State> ov, State oldState, State newState)
          {
            if (newState == State.SUCCEEDED)
            {
            }
          }
        });
      });
    }
  }
}
