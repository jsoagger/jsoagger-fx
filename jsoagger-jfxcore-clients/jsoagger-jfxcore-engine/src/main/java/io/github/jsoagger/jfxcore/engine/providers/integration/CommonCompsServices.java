/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers.integration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.services.CommonComponentsServices;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewContextFilterXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewFilterXML;

/**
 * @author Ramilafananana  VONJISOA
 */
public class CommonCompsServices  implements CommonComponentsServices {

  boolean debug = true;
  boolean loaded = false;
  private List<String> files = new ArrayList<>();

  VLViewConfigXML allLoadedComps= new VLViewConfigXML();

  /**
   * {@inheritDoc}
   */
  @Override
  public VLViewFilterXML getFilter(IJSoaggerController controller, String id) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VLViewContextFilterXML getFiltersContext(String id) {
    return null;
  }


  public void loadFiles() {
    if(!loaded) {
      for(String file : files) {
        try(InputStream io = ResourceUtils.getStream(getClass(), file)){
          Gson gson1 = new Gson();
          VLViewConfigXML c  = gson1.fromJson(new InputStreamReader(io), VLViewConfigXML.class);
          allLoadedComps.getComponents().addAll(c.getComponents());
        }catch (Exception e) {
        }
      }

      loaded = true;
    }
  }

  /**
   * @return the files
   */
  public List<String> getFiles() {
    return files;
  }
}
