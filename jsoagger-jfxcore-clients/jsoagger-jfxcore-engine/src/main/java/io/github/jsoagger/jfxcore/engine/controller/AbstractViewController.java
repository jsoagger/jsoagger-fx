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

package io.github.jsoagger.jfxcore.engine.controller;


import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IComponent;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.api.components.annotation.InjectComponent;
import io.github.jsoagger.jfxcore.api.security.IRootContext;
import io.github.jsoagger.jfxcore.api.security.IViewContext;
import io.github.jsoagger.jfxcore.api.services.LocalComponentsService;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewFilterXML;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaEvaluatorImpl;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriasFactory;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.events.VLEvent;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.LocaleResolver;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractViewController implements IJSoaggerController {

  /*-----------------------------------------------------------------------------
  | FIELDS
   *=============================================================================*/

  /**
   * Identifier of the view
   */
  protected SimpleStringProperty id = new SimpleStringProperty();

  /**
   * List of all generated component layed out on this view
   */
  protected Map<String, InjectableComponent> controls = new HashMap<>();
  /**
   * The model
   */
  protected SimpleObjectProperty<Object> model = new SimpleObjectProperty<>();
  protected IModelProvider modelProvider;

  /**
   * Agregation of components
   */
  protected LocalComponentsService commonComponents;

  /**
   * View
   */
  protected List<String> viewDefinitions = new ArrayList<>();
  protected List<String> csses = new ArrayList<>();
  /**
   * Holds the configurations for view generation
   */
  protected IViewContext viewContext;
  /**
   * Whatever the depth of content in the xml definition, the processed content must be wrapped into a
   * node. This node is passed to the layout managed and displayed. It is the responsability of the
   * processor to handle {@link AbstractViewController}.
   */
  protected Pane processedView;

  /** Internationalisation */
  protected MessageSource messageSource;

  /**
   * When controller is singleton, this flag help us to avoid rebuild a controller. If a controller
   * must be rebuild, it must be done manualy.
   */
  protected SimpleBooleanProperty initialized = new SimpleBooleanProperty();

  /**
   * The platform propeties
   */
  // needs platformProperties
  protected Properties platformProperties;
  private AbstractViewController parent;

  private final List<VLEvent> handledEvents = new ArrayList<>();


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
   *=============================================================================*/
  /**
   * Contructor
   */
  public AbstractViewController() {
    registerListener(CoreEvent.ModelUpdatedEvent);
  }


  /*-----------------------------------------------------------------------------
  | FXML HANDLING
   *=============================================================================*/
  /**
   * Loads the fxml file associated to the view.
   */
  protected void loadFromFXMLFile() {
    final URL fxmlName = getFXMLFileName();
    if (fxmlName != null) {
      final Node content = loadFXML(fxmlName);
      HBox.setHgrow(content, Priority.ALWAYS);
      VBox.setVgrow(content, Priority.ALWAYS);

      processedView = (Pane) content;
    }
  }


  /**
   * Fxml file name must be : name of the view.fxml
   */
  protected URL getFXMLFileName() {
    final String name = StringUtils.substringAfterLast(this.getClass().getName(), ".");
    return getClass().getResource(name.concat(".fxml"));
  }


  /**
   * Load a content from an FXML definition
   */
  protected Node loadFXML(URL fxmlLocation) {
    try {
      if (fxmlLocation != null) {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(fxmlLocation);
        fxmlLoader.setController(this);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        return fxmlLoader.load();
      }
    } catch (final IOException e) {
      e.printStackTrace();
    }

    return null;
  }


  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
   *=============================================================================*/
  /**
   * Add an event to event to listen
   *
   * @param e
   */
  public void registerListener(VLEvent event) {
    handledEvents.add(event);
  }


  /**
   * Check if this controller is aware of given event.
   *
   * @param e
   * @return
   */
  public synchronized <T extends GenericEvent> boolean isAwareOf(T event) {
    for(VLEvent e : handledEvents) {
      if(e.filter().equals(event.getFilter())) {
        return true;
      }
    }

    return false;
  }


  /**
   * Handle event
   *
   * @param e
   */
  public synchronized <T extends GenericEvent> void handle(T e) {
    if (e.isA(CoreEvent.ModelUpdatedEvent)) {
    }
  }


  /**
   * Handles an action result (For message displaying for axample ).
   *
   * @param actionRequest
   * @param actionResult
   */
  @Override
  public void handleActionResult(IActionRequest actionRequest, IActionResult actionResult) {

  }


  /**
   * Build the controller
   */
  public void build() {
    preProcess();
    process();
    postProcess();
  }


  /**
   * Process the controller
   */
  protected void process() {
    doProcessing();
  }


  /**
   * Process the view if configuration has defined default processor
   */
  protected void doProcessing() {
    if (config().getRootComponent() != null) {
      final Node node = ComponentUtils.buildFromProcessor(this, config().getRootComponent());
      if (node != null) {
        NodeHelper.setHVGrow(node);
        processedView(node);
      }
    }
  }


  /**
   * Loads the model if the view has defined {@link IModelProvider}
   */
  protected void loadModel() {}


  /**
   * @param name
   * @return
   */
  public Object getSpringBean(String name) {
    return Services.getBean(name);
  }


  /**
   * Get the root component of this view wizardConfiguration.
   *
   * @return {@link VLViewComponentXML}
   */
  public VLViewComponentXML getRootComponent() {
    return viewContext().getViewConfig().getRootComponent();
  }


  /**
   * Post Process the view
   */
  public void postProcess() {}


  /**
   * Pre Process the view
   */
  public void preProcess() {
    controls = new HashMap<>();

    // load fxml file if one exist
    loadFromFXMLFile();
  }


  /**
   * rootFormProperty.set() do not fire value change if the referenced object does not change. The
   * change is calculated with operator == between new and old value. In some cases we want to fire
   * this event when some attribute inside the object changes.
   *
   * @param newValue
   */
  public void forceFormPropertyValue(ObjectModel newValue) {
    modelProperty().set(null);
    modelProperty().set(newValue);
  }


  /**
   * Loads all mandatory wizardConfiguration or context datas needed.
   */
  public void initViewContext(VLViewConfigXML configXML, IRootContext context) {
    viewContext = (IViewContext) Services.getBean("viewContext");
    viewContext.processFrom(configXML, context);

    // criterias may depends on some attributes on root model, be aware to
    // compute it after setting root form!!! important
    // The build method should be called after updating root model.
    final CriteriaContext criterias = CriteriasFactory.processCriterias(this);
    viewContext.setCriterias(criterias);

    // loca css
    loadCsses();
  }


  public StructureContentController getStructureContent() {
    if (this instanceof StructureContentController) {
      return (StructureContentController) this;
    }
    if (this instanceof StandardViewController) {
      return ((StandardViewController) this).getStructureContentController();
    }
    return null;
  }


  /**
   * @param controllerClazz
   */
  protected void loadCsses() {
    if (csses != null && csses.size() > 0) {
      for (int i = 0; i < csses.size(); i++) {
        final String cssPath = csses.get(i);

        if (StringUtils.isNotBlank(cssPath)) {
          try {
            final String url = getClass().getResource(cssPath).toExternalForm();
            if (url != null) {
              //Services.getStylesheetManager().addStyleSheet(url);
              //System.out.println("Adding style : " + url + ", to controller " + getClass());
            } else {
              //System.out.println("Css file not found : " + cssPath);
            }
          } catch (final NullPointerException e) {
          }
        }
      }
    }
  }


  /**
   * @return the processedContent
   */
  public Node processedView() {
    return processedView;
  }


  /**
   * @param processedContent the processedContent to set
   */
  public void processedView(final Node processedContent) {
    if (processedContent instanceof Pane) {
      processedView = (Pane) processedContent;
    } else {
      final StackPane pane = new StackPane();
      processedView = pane;
      pane.getChildren().add(processedContent);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    destroy();
    modelProperty().unbind();
  }


  /**
   * Destroys controller and associated view
   */
  public void destroy() {
    processedView = null;
    parent = null;
    controls.clear();

    // no more vents
    handledEvents.clear();
  }


  /**
   * Add a Field
   *
   * @param control
   */
  @Deprecated
  public void addComponent(final Object comp) {
    if (comp instanceof InjectableComponent) {
      if ("SearchMasterAttribute".equals(((InjectableComponent) comp).getInternalId())) {
      }
      controls.put(((InjectableComponent) comp).getInternalId(), (InjectableComponent) comp);
      injectNodeInController(getClass(), (InjectableComponent) comp);
    }
  }

  /**
   *
   */
  @Override
  public void addIComponent(final IComponent comp) {
    addComponent(comp);
  }


  /**
   * @param clazz
   * @param button
   */
  private void injectNodeInController(Class clazz, InjectableComponent comp) {

    boolean injected = false;

    // inject the node in the view if needed
    final Field[] fieldList = clazz.getDeclaredFields();
    for (final Field field : fieldList) {

      if (field.isAnnotationPresent(InjectComponent.class)) {
        final InjectComponent annotation = field.getAnnotation(InjectComponent.class);
        final String id = annotation.id();
        final boolean wasAccessible = field.isAccessible();

        if (id.equalsIgnoreCase(comp.getInternalId())) {
          try {
            field.setAccessible(true);
            field.set(this, comp);
            injected = true;
          } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalArgumentException("Error occurs when injecting component : " + comp.getInternalId(), e);
          } finally {
            field.setAccessible(wasAccessible);
          }
        }
      }
    }

    if (!injected && clazz.getSuperclass() != null) {
      injectNodeInController(clazz.getSuperclass(), comp);
    }
  }


  /**
   * Get a component from this view
   *
   * @param id
   * @return VLComponent ro null
   */
  public io.github.jsoagger.jfxcore.api.InjectableComponent getComponent(String id) {
    return controls.get(id);
  }


  /**
   * Get the view wizardConfiguration
   *
   * @return
   */
  @Override
  public VLViewConfigXML config() {
    return viewContext.getViewConfig();
  }


  /**
   * Get a localized value of the key from declared bundles of the view.
   *
   * @param key
   * @return
   */
  @Override
  public String getLocalised(String key) {
    if (StringUtils.isBlank(key) || messageSource == null) {
      return "__I18N__";
    }

    if (key.endsWith("_CORE_MSG")) {
      return getGLocalised(key.replaceAll("_CORE_MSG", ""));
    }

    final Locale locale = LocaleResolver.getLocale();
    return messageSource.getOrDefault(key, "__I18N__", locale);
  }


  /**
   * @param key
   * @param args
   * @return
   */
  public String getLocalised(String key, Object... args) {
    if(messageSource == null) {
      return "__I18N__";
    }

    final Locale locale = LocaleResolver.getLocale();
    return messageSource.getOrDefault(key,"__I18N__" ,locale);
  }


  /**
   * @param key
   * @param config
   * @return
   */
  @Override
  public String getLocalised(String key, VLViewComponentXML config) {
    if(messageSource == null) {
      return "__I18N__";
    }

    String message = messageSource.get(key);
    if (StringUtils.isBlank(message) && config.getId().startsWith("Core")) {
      return getGLocalised(key);
    }
    return message;
  }


  /**
   * Get a localized value of the key from core messages.
   *
   * @param key
   * @return
   */
  @Override
  public String getGLocalised(String key) {
    if(messageSource == null) {
      return "__I18N__";
    }

    if (StringUtils.isBlank(key)) {
      return "__I18N__";
    }
    final Locale locale = LocaleResolver.getLocale();
    if(messageSource.getParentMessageSource() == null) {
      return "__I18N__";
    }
    return messageSource.getParentMessageSource().getOrDefault(key,"__I18N__" ,locale);
  }


  /**
   * Get a localized value of the key from core messages.
   *
   * @param key
   * @param args
   * @return
   */
  public String getGLocalised(String key, Object... args) {
    if(messageSource == null) {
      return "__GI18N__";
    }

    final Locale locale = LocaleResolver.getLocale();
    return messageSource.getParentMessageSource().getOrDefault(key,"__I18N__" ,locale);
  }


  /**
   * Get the user locale
   *
   * @return
   */
  public Locale getUserLocale() {
    return viewContext.getUserLocale();
  }


  /**
   * Reload the XML and data
   */
  public void refreshAll() {
    build();
  }


  /**
   * Reload data only
   */
  public void refreshDatas() {

  }


  /**
   * @param wizardConfiguration
   * @return
   */
  public boolean evaluateFilter(VLViewComponentXML configuration) {
    String criteria = configuration.getCriteria();
    if (StringUtils.isNotBlank(criteria)) {
      final CriteriaEvaluatorImpl evaluatorImpl = new CriteriaEvaluatorImpl();
      final VLViewFilterXML noeud = Services.resolveFilter(this, criteria);

      if (noeud != null) {
        return evaluatorImpl.evaluate(this, noeud, viewContext.getCriterias());
      }
      else {

        if(viewContext.getCriterias() != null) {
          if(viewContext.getCriterias().containsFilter(criteria)){
            return "true".equals(viewContext.getCriterias().filterValue(criteria));
          }
        }
      }

      // a criteria have been declared
      // but developper has not declared an evaluator for it
      // so hide it
      return false;
    }

    return true;
  }


  /**
   * Return the current view context
   *
   * @return
   */
  @Override
  public IViewContext viewContext() {
    return viewContext;
  }


  /**
   * Get the value of the given key from platform properties.
   *
   * @param key
   * @return
   */
  public String getPlatformProperties(String key) {
    return platformProperties.getProperty(key);
  }


  /**
   * @param event
   */
  public void publishEvent(Object event) {
    Services.dispatchEvent(event);
  }


  /**
   * @return
   */
  public AbstractViewController getParent() {
    return parent;
  }


  /**
   * Setter of parent
   *
   * @param parent the parent to set
   */
  public void setParent(AbstractViewController parent) {
    this.parent = parent;
  }


  /**
   * Getter of viewDefinitions
   *
   * @return the viewDefinitions
   */
  @Override
  public List<String> getViewDefinitions() {
    return viewDefinitions;
  }


  /**
   * Setter of viewDefinitions
   *
   * @param viewDefinitions the viewDefinitions to set
   */
  public void setViewDefinitions(List<String> viewDefinitions) {
    this.viewDefinitions = viewDefinitions;
  }


  /**
   * Getter of csses
   *
   * @return the csses
   */
  public List<String> getCsses() {
    return csses;
  }


  /**
   * Setter of csses
   *
   * @param csses the csses to set
   */
  public void setCsses(List<String> csses) {
    this.csses = csses;
  }


  /**
   * @param actionRequest
   * @param result
   */
  public void handleValidationResult(IActionRequest actionRequest, IUIDataValidationResult result) {}


  public final SimpleObjectProperty<Object> modelProperty() {
    return model;
  }


  @Override
  public final Object getModel() {
    return this.modelProperty().get();
  }


  @Override
  public final void setModel(final Object model) {
    this.modelProperty().set(model);
  }


  public final SimpleBooleanProperty initializedProperty() {
    return initialized;
  }


  public final boolean isInitialized() {
    return this.initializedProperty().get();
  }


  public final void setInitialized(final boolean initialized) {
    this.initializedProperty().set(initialized);
  }


  public final SimpleStringProperty idProperty() {
    return id;
  }


  public final java.lang.String getId() {
    return this.idProperty().get();
  }


  public final void setId(final java.lang.String id) {
    this.idProperty().set(id);
  }


  /**
   * Getter of modelProvider
   *
   * @return the modelProvider
   */
  public IModelProvider getModelProvider() {
    return modelProvider;
  }


  /**
   * Setter of modelProvider
   *
   * @param modelProvider the modelProvider to set
   */
  public void setModelProvider(IModelProvider modelProvider) {
    this.modelProvider = modelProvider;
  }


  public String getModelBusinessTypeFullId() {
    final IOperationResult operationResult = (IOperationResult) getModel();
    if (operationResult != null) {
      final OperationData data = (OperationData) operationResult.rootData();
      return (String) data.getBusinessType().get("fullId");
    }

    return null;
  }


  @Override
  public String getModelContainerPath() {
    final IOperationResult operationResult = (IOperationResult) getModel();
    if (operationResult != null) {
      final OperationData data = (OperationData) operationResult.rootData();
      String path = (String) data.getContainer().get("path");
      if (StringUtils.isEmpty(path)) {
        path = getRootStructure().getRootContext().getContainerPath();
      }
      return path;
    }

    return null;
  }


  @Override
  public String getModelContainerFullId() {
    if (this instanceof WizardViewController && getStructureContent() != null) {
      final OperationData data = getStructureContent().getFormModelData();
      if (data != null) {
        final String fullId = (String) data.getContainer().get("fullId");
        return fullId;
      }
    } else {
      final IOperationResult operationResult = (IOperationResult) getModel();
      if (operationResult != null) {
        final OperationData data = (OperationData) operationResult.rootData();
        String fullId = (String) data.getContainer().get("fullId");
        if (StringUtils.isEmpty(fullId)) {
          fullId = getRootStructure().getRootContext().getContainerFullId();
        }
        return fullId;
      }
    }

    return null;
  }


  public String getModelBusinessTypeName() {
    final IOperationResult operationResult = (IOperationResult) getModel();
    if (operationResult != null) {
      final OperationData data = (OperationData) operationResult.rootData();
      return (String) data.getBusinessType().get("name");
    }

    return null;
  }


  public String getModelBusinessTypePath() {
    final IOperationResult operationResult = (IOperationResult) getModel();
    if (operationResult != null) {
      final OperationData data = (OperationData) operationResult.rootData();
      return (String) data.getBusinessType().get("path");
    }

    return null;
  }


  @Override
  public String getModelFullId() {
    if (this instanceof WizardViewController) {
      final OperationData data = getStructureContent().getFormModelData();
      if (data != null) {
        final String fullId = (String) data.getAttributes().get("fullId");
        return fullId;
      }
    } else {
      final IOperationResult operationResult = (IOperationResult) getModel();
      if (operationResult != null) {
        final OperationData data = (OperationData) operationResult.rootData();
        if (data != null) {
          return (String) data.getAttributes().get("fullId");
        }
        return null;
      }
    }

    return null;
  }

  private IFormRowEditor currentForwarEditor;
  public IFormRowEditor currentForwarEditor() {
    return currentForwarEditor;
  }

  /**
   * Called by {@link IFormRowEditor}, never call it from over places
   * <p>
   *
   * @param simpleForwardEditor
   * @return
   */
  public boolean beginForwardEdition(IFormRowEditor simpleForwardEditor) {
    currentForwarEditor = simpleForwardEditor;

    // !!always return false
    return false;
  }


  /**
   * Called by {@link IFormRowEditor}, never call it from over places
   * <p>
   *
   * @return
   */
  public boolean endForwardEdition() {
    if (currentForwarEditor != null) {
      currentForwarEditor = null;
    }

    return false;
  }


  /**
   * Can be called from external action to close the editor if not null.
   */
  public void closeForwardEditor() {
    if (currentForwarEditor != null) {
      currentForwarEditor.closeEditor();
    }
  }


  /**
   * Handle the layout of this view.
   *
   * @param controller The source controller. When the pop is called, the source controller beforePop
   *        method is called in order to tell it to update itself.
   *
   * @param node
   */
  public void pushContent(StandardViewController controller, Node node) {}


  /**
   * Back up the pushed content
   */
  public void popContent() {}


  /**
   * Called before poping the controller. May be you need to update the view.
   */
  public void beforePop() {

  }


  public HashMap<String, String> getAllCriterias() {
    if (viewContext.getCriterias() != null) {
      return viewContext.getCriterias().getAllFilters();
    }

    return new HashMap<>();
  }


  public void setCommonComponents(LocalComponentsService commonComponents) {
    this.commonComponents = commonComponents;
  }


  public LocalComponentsService getCommonComponents() {
    return commonComponents;
  }

  public OperationData getOpData() {
    if (getModel() != null) {
      final Object model = getModel();
      if (model instanceof SingleResult) {
        return ((SingleResult) model).getData();
      }
    }

    return null;
  }

  /**
   * Add a view defnition to this
   * controller.
   *
   * @param path
   */
  public void addViewDefinition(String path) {
    viewDefinitions.add(path);
  }

  /**
   * Dispatch given event. Child implements decides how event should be disptached.
   *
   * @param event The event
   */
  public abstract <T extends GenericEvent> void dispatchEvent(T event);


  /**
   * @return
   */
  @Override
  public abstract RootStructureController getRootStructure();


  /**
   * @return the messageSource
   */
  public MessageSource getMessageSource() {
    return messageSource;
  }


  /**
   * @param messageSource the messageSource to set
   */
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }
}
