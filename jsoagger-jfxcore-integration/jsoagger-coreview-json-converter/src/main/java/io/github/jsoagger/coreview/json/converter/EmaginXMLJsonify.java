/**
 *
 */
package io.github.jsoagger.coreview.json.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.atteo.xmlcombiner.XmlCombiner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.viewdefinition.xml.model.VLViewConfigXML;

/**
 * Take source folder and target folder in argument.
 *
 * Will find all class annotated with convertToJson in classpath and generates associated bean and json files.
 * Will also copy resources to destination folde.
 *
 * Resulting in result project independent from spring framework as this last is not mobile and embedded aware.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class EmaginXMLJsonify {

  private static boolean debug = false;
  protected static final String CONF_FILE_SUFFIX = ".xml";
  public static JAXBContext JC;

  private static boolean cleanDestination = true;

  static String SRC = null;

  static {
    try {
      JC = JAXBContext.newInstance(VLViewConfigXML.class);
      JC.createUnmarshaller();
    } catch (final JAXBException e) {
      e.printStackTrace();
    }
  }

  private static String extractFromArgs(String key, String[]args) {
    if(StringUtils.isEmpty(key)) {
      return null;
    }

    Iterator<String> it = Arrays.asList(args).iterator();
    while(it.hasNext()) {
      String token = it.next();
      if(token.equals(key) && it.hasNext()) {
        return it.next();
      }
    }

    return null;
  }

  private static void printUsage() {
  }

  @SuppressWarnings("resource")
  public static void main(String[] args) throws IOException, ClassNotFoundException {

    // String DST_FLD = StringUtils.removeEnd(extractFromArgs("-projectPath", args), File.separator);
    // String[] CLAZZ = StringUtils.removeEnd(extractFromArgs("-classes", args), File.separator).split(",");
    //String DST_FLD = "/PATH TO RESURCES/jsoagger-jfx-demo/jsoagger-jfxcore-demoapp/src/main/resources";
    //String DST_FLD = "/PATH TO RESURCES/jsoagger-jfxcore-clients/jsoagger-jfxcore-components/src/main/resources";
    String DST_FLD = "/PATH TO RESURCES/tutoriels/myproject/src/main/resources";

    if(StringUtils.isNotBlank(SRC)) {
      System.out.println("Process generation with following arguments?");
      System.out.println("Source folder : " + SRC);
      System.out.print("y/n :");
      System.out.flush();
      String val = new Scanner(System.in).next();

      if("y".equalsIgnoreCase(val)) {
        System.out.println("Processing ...");
      }
      else {
        System.out.println("ABORTED");
        System.exit(-1);
      }
    }

    //    if(CLAZZ.length == 0) {
    //      System.out.println("ABORTED : classes to be processed is mandatory");
    //      System.exit(-1);
    //    }

    //    Class[] classes = {DemoAppDesktopViewsProvider.class,
    //        DemoAppMobileViewsProvider.class, DemoAppSearchBeansProvider.class,
    //        DemoAppCommonBeansProvider.class, DemoAppPreferencesBean.class};
    //Class[] classes = { DemoDetailsBeansProvider.class};
    //Class[] classes = { CorePreferencesBeanProvider.class};
    //    Class[] classes = { DemoAppDesktopViewsProvider.class,
    //        DemoDesktopTableViewsBeansProvider.class,DemoAppMobileViewsProvider.class,
    //        DemoAppDesktopViewsProvider.class, DemoAppCommonBeansProvider.class};
    //Class[] classes = {MobileAppViewsConfiguration.class};
    Class[] classes = {};

    for (final Class c : classes) {
      for (final Method method : c.getDeclaredMethods()) {
        if (method.isAnnotationPresent(View.class)) {
          View named = method.getAnnotation(View.class);

          // name of file
          String filename = named.outputFileName();
          if(StringUtils.isEmpty(filename)) {
            String uniqueLocation = named.locations()[0];
            String fileName = org.apache.commons.lang.StringUtils.substringAfterLast(uniqueLocation, "/");
            filename = fileName.split("\\.")[0] + ".json";
          }

          if(!filename.endsWith(".json")) {
            filename += ".json";
          }

          Gson gson = new Gson();
          File finalFile = null;
          if(StringUtils.isEmpty(named.outputFilePath())) {
            finalFile = new File(DST_FLD  + File.separator +
                org.apache.commons.lang.StringUtils.substringBeforeLast(named.locations()[0], "/") +
                File.separator + filename);
          }
          else {
            finalFile = new File(DST_FLD +
                File.separator +
                named.outputFilePath()  +
                File.separator +
                File.separator + filename);
          }

          VLViewConfigXML config = getConfigurationFile(c, Arrays.asList(named.locations()));
          try {
            String json = gson.toJson(config);
            System.out.println(json);
            com.google.common.io.Files.write(json.getBytes(), finalFile);
          } catch (JsonIOException | IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    //globalCompsToJson(DST_FLD);
  }


  private static String getFilenameFrom(String path) {
    return org.apache.commons.lang.StringUtils.substringAfterLast(path, "/").split("\\.")[0];
  }


  private static void globalCompsToJson(String DEST) {
    String[] sources = {"classpath:/comp/CoreActions.xml",
        "classpath:/comp/CoreActionsModel.xml",
        "classpath:/comp/CoreAttributes.xml",
        "classpath:/comp/CoreComponents.xml",
        "classpath:/io/github/jsoagger/jfxcore/demoapp/common/comps/common-components.xml",
        "classpath:/io/github/jsoagger/jfxcore/demoapp/common/comps/element-components.xml",
        "classpath:/io/github/jsoagger/jfxcore/demoapp/common/comps/search-components.xml",
    };
    String destination="/io/github/jsoagger/jfxcore/demoapp/common";

    for(String s: sources) {
      if(s.startsWith("classpath:")) {
        try(InputStream is = EmaginDSpringify.class.getResourceAsStream(StringUtils.substringAfter(s, "classpath:"))){
          VLViewConfigXML finalResult = (VLViewConfigXML) JC.createUnmarshaller().unmarshal(is);

          String filename = getFilenameFrom(StringUtils.substringAfter(s, "classpath:")) + ".json";

          String finalFolder = DEST + "/" + destination ;
          File folder = new File(finalFolder);
          if(!folder.exists()) {
            folder.mkdirs();
          }

          try {
            Gson gson = new Gson();
            String json = gson.toJson(finalResult);
            System.out.println(json);
            com.google.common.io.Files.write(json.getBytes(), new File(finalFolder + File.separator + filename));
          } catch (JsonIOException | IOException e) {
            System.out.println("ERROR " + finalFolder);
            e.printStackTrace();
          }
        } catch (JAXBException e) {
          e.printStackTrace();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }


  public static VLViewConfigXML getConfigurationFile(Class c, List<String> confiFiles) {

    VLViewConfigXML finalResult = null;

    try {

      final XmlCombiner combiner = new XmlCombiner();
      final Map<String, String> env = new HashMap<>();
      env.put("create", "true");

      final boolean combine = confiFiles.size() > 0;
      for (final String uri : confiFiles) {
        System.out.println("Loading file : " + uri);
        InputStream is = ResourceUtils.getStream(c, uri);
        if (is == null) {
          System.out.println("FATAL ERROR");
        }
        combiner.combine(is);
      }

      if (combine) {
        // LOG.debug(MessageFormat.format("Unmarshalling final view definition : {0}", controller.getId()));
        final Document result = combiner.buildDocument();
        finalResult = (VLViewConfigXML) JC.createUnmarshaller().unmarshal(result);

        if(debug) {
          try {
            final DOMSource domSource = new DOMSource(result);
            final StringWriter writer = new StringWriter();
            final StreamResult resultA = new StreamResult(writer);
            final TransformerFactory tf = TransformerFactory.newInstance();
            final Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, resultA);
            System.out.println("XML IN String format is: \n" + writer.toString());
          } catch (final Exception e) {
            e.printStackTrace();
          }
        }
      } else {
        finalResult = new VLViewConfigXML();
      }
      // @formatter: off
    } catch (Exception e) {
      e.printStackTrace();
      // LOG.error(e);
    }
    // @formatter: on
    return finalResult;
  }
}
