/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

import com.google.gson.Gson;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.services.ViewConfigurationService;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;

/**
 * @author Ramilafananana VONJISOA
 *
 */
public class JSonViewConfigurationService implements ViewConfigurationService {

  private boolean debug = false;

  public JSonViewConfigurationService() {}

  /**
   * Merge views definitions corresponding to this view. View must define at least one
   * wizardConfiguration.
   */
  @Override
  public VLViewConfigXML getConfigurationFile(final IJSoaggerController controller) {
    VLViewConfigXML finalResult = null;
    BufferedReader bins = null;

    try {

      if (debug) {
        System.out.println("Searching for wiew : " + controller.getClass().getCanonicalName());
        System.out.println("Searching for wiew : " + controller.getViewDefinitions().get(0));
      }

      if (controller.getViewDefinitions().size() > 0) {
        String file = controller.getViewDefinitions().get(0);
        if (file.endsWith(".xml")) {
          throw new RuntimeException("Only Json configurration file is supported, given" + file);
        }

        Gson gson = new Gson();
        URL fileurl = ResourceUtils.getURL(file);

        bins = new BufferedReader(new java.io.InputStreamReader(fileurl.openStream()));
        finalResult = gson.fromJson(bins, VLViewConfigXML.class);

        if (debug) {
          System.out.println("### finalResult getComponents: " + finalResult.getComponents());
          System.out.println("### finalResult : " + finalResult);
        }
      } else {
        finalResult = new VLViewConfigXML();
        System.out.println(
            "[ERROR] Not found configuration for file : " + controller.getViewDefinitions());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bins != null) {
        try {
          bins.close();
        } catch (IOException e) {
        }
      }
    }

    return finalResult;
  }
}
