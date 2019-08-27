/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers.integration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.services.GlobalComponentsService;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;

/**
 * @author Ramilafananana  VONJISOA
 */
public class JsonGlobalCompsService implements GlobalComponentsService {

  boolean debug = true;
  boolean loaded = false;
  private List<String> files = new ArrayList<>();

  VLViewConfigXML allLoadedComps= new VLViewConfigXML();

  /**
   * {@inheritDoc}
   */
  @Override
  public VLViewComponentXML getCompFromGlobalConfig(String id) {
    // not supported yet
    if(debug) {
      System.out.println("[Find global component ] " + id);
    }

    if(!loaded) {
      loadFiles();
    }

    Optional<VLViewComponentXML> comp =  allLoadedComps.getComponentById(id);
    if(comp.isPresent()) {
      System.out.println("[Found global component ] " + id);
    }
    else {
      System.out.println("[Not Found global component ] " + id);
    }

    return comp.orElse(null);
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
