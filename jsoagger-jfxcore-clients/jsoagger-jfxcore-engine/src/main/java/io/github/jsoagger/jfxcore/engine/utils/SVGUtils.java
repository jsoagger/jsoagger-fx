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

package io.github.jsoagger.jfxcore.engine.utils;



import java.net.URL;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 22 janv. 2018
 */
public class SVGUtils {

  Callback<ImageView, Void> callback;


  public static SVGUtils newInstance(Callback<ImageView, Void> callback) {
    return new SVGUtils(callback);
  }


  private SVGUtils(Callback<ImageView, Void> callback) {
    this.callback = callback;
  }


  /**
   * This method will attempt to load the given svgImage URL into an ImageView node that will be
   * provided asynchronously via the provided {@link Callback}, and it will be sized to the given
   * prefWidth / prefHeight.
   *
   * <p>
   * Note that it is valid to pass in -1 to prefWidth and / or prefHeight as an indicator to the SVG
   * loader. If both values are -1, the default width of the SVG will be used. If one of the values is
   * -1, then the SVG will be sized to ensure that it remains proportional.
   *
   * @param svgImage The image to load.
   * @param prefWidth The preferred width of the image when loaded, or -1 if there is no preferred
   *        width.
   * @param prefHeight The preferred height of the image when loaded, or -1 if there is no preferred
   *        height.
   * @param callback The {@link Callback} that will be called when the SVG image is loaded, where the
   *        {@link ImageView} containing the rendered image will be available.
   */
  public void loadSVGImage(final URL svgImage, final double prefWidth, final double prefHeight) {
    loadSVGImage(svgImage, prefWidth, prefHeight, callback, null);
  }


  /**
   * This method will attempt to load the given svgImage URL into the provided {@link WritableImage},
   * with the SVG scaled to fit the size of the WritableImage.
   *
   * @param svgImage The image to load.
   * @param outputImage The location to write the loaded image once it has been rendered (it will not
   *        happen synchronously).
   * @throws NullPointerException The outputImage argument must be non-null.
   */
  public void loadSVGImage(final URL svgImage, final WritableImage outputImage) {
    if (outputImage == null) {
      throw new NullPointerException("outputImage can not be null"); //$NON-NLS-1$
    }
    final double w = outputImage.getWidth();
    final double h = outputImage.getHeight();
    loadSVGImage(svgImage, w, h, null, outputImage);
  }


  public void loadSVGImage(final URL svgImage, final double prefWidth, final double prefHeight, final Callback<ImageView, Void> callback, final WritableImage outputImage) {

   /* Platform.runLater(() -> {
      final WebView view = new WebView();
      final WebEngine eng = view.getEngine();

      // using non-public API to ensure background transparency
      // final WebPage webPage = Accessor.getPageFor(eng);
      // webPage.setBackgroundColor(webPage.getMainFrame(), 0xffffff00);
      // webPage.setOpaque(webPage.getMainFrame(), false);
      // end of non-public API

      // temporary scene / stage
      final Scene scene = new Scene(view);
      final Stage stage = new Stage();
      stage.setScene(scene);
      stage.setWidth(0);
      stage.setHeight(0);
      stage.setOpacity(0);
      stage.show();

      // String svgString = readFile(svgImage);

      final String content = "<html>" + //$NON-NLS-1$
      "<body style=\"margin-top: 0px; margin-bottom: 30px; margin-left: 0px; margin-right: 0px; padding: 0;\">" //$NON-NLS-1$
          +
      // "<div style=\"width: " + prefWidth + "; height: " +
      // prefHeight + ";\">" +
      "<img id=\"svgImage\" style=\"display: block;float: top;\" width=\"" + prefWidth + "\" height=\"" //$NON-NLS-1$ //$NON-NLS-2$
          + prefHeight + "\" src=\"" + svgImage.toExternalForm() + "\" />" + //$NON-NLS-1$ //$NON-NLS-2$
      // svgString +
      // "</div>" +
      "</body>" + //$NON-NLS-1$
      "</head>"; //$NON-NLS-1$

      eng.loadContent(content);

      eng.getLoadWorker().stateProperty().addListener((ChangeListener<State>) (o, oldValue, newValue) -> {
        if (newValue == State.SUCCEEDED) {

          // HTMLImageElement svgImageElement = (HTMLImageElement)
          // getSvgDom(eng);
          // System.out.println(svgImageElement.getAttributes());

          final double svgWidth = prefWidth >= 0 ? prefWidth : getSvgWidth(eng);
          final double svgHeight = prefHeight >= 0 ? prefWidth : getSvgHeight(eng);

          final SnapshotParameters params = new SnapshotParameters();
          params.setFill(Color.TRANSPARENT);
          params.setViewport(new Rectangle2D(0, 0, svgWidth, svgHeight));

          view.snapshot(param -> {
            final WritableImage snapshot = param.getImage();
            final ImageView image = new ImageView(snapshot);

            if (callback != null) {
              callback.call(image);
            }

            stage.hide();
            return null;
          }, params, outputImage);
        }
      });
    });*/
  }


  // private String readFile(URL url) {
  // try {
  // FileInputStream stream = new FileInputStream(new File(url.toURI()));
  // try {
  // FileChannel fc = stream.getChannel();
  // MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
  // fc.size());
  // return Charset.defaultCharset().decode(bb).toString();
  // }
  // finally {
  // stream.close();
  // }
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // return null;
  // }

  private double getSvgWidth(WebEngine webEngine) {
    final Object result = getSvgDomProperty(webEngine, "offsetWidth"); //$NON-NLS-1$
    if (result instanceof Integer) {
      return (Integer) result;
    }
    return -1;
  }


  private double getSvgHeight(WebEngine webEngine) {
    final Object result = getSvgDomProperty(webEngine, "offsetHeight"); //$NON-NLS-1$
    if (result instanceof Integer) {
      return (Integer) result;
    }
    return -1;
  }


  private Object getSvgDomProperty(final WebEngine webEngine, final String property) {
    return webEngine.executeScript("document.getElementById('svgImage')." + property); //$NON-NLS-1$
  }
}
