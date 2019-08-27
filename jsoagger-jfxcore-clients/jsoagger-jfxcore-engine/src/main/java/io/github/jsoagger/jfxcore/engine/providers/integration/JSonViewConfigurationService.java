/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers.integration;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.services.ViewConfigurationService;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class JSonViewConfigurationService  implements ViewConfigurationService {

  private boolean debug = false;

  /**
   * Merge views definitions corresponding to this view. View must define at least one
   * wizardConfiguration.
   */
  @Override
  public VLViewConfigXML getConfigurationFile(final IJSoaggerController controller) {
    VLViewConfigXML finalResult = null;

    if(debug) {
      System.out.println("Searching for wiew : " + controller.getViewDefinitions().get(0));
    }

    if(controller.getViewDefinitions().size() > 0) {
      String file = controller.getViewDefinitions().get(0);
      if(file.endsWith(".xml")) {
        throw new RuntimeException("Only Json configurration file is supported, given" + file);
      }

      if(debug) {
        System.out.println("### Searching for wiew : " + file);
      }

      Gson gson = new Gson();
      InputStream io = ResourceUtils.getStream(controller.getClass(), file);
      finalResult = gson.fromJson(new InputStreamReader(io), VLViewConfigXML.class);
    }
    else {
      finalResult = new VLViewConfigXML();
      System.out.println("[ERROR] Not found configuration for file : " + controller.getViewDefinitions());
    }

    return finalResult;
  }
}
