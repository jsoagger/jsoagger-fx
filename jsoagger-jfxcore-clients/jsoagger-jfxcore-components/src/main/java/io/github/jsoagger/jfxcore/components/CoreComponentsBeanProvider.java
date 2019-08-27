/**
 *
 */
package io.github.jsoagger.jfxcore.components;


import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.i18n.MessageSource;
import io.github.jsoagger.core.ioc.api.BeanFactory;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.core.ioc.api.annotations.View;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.components.actions.AddElementUsageFromSearchAction;
import io.github.jsoagger.jfxcore.components.actions.BuildRSContentEventAction;
import io.github.jsoagger.jfxcore.components.actions.ClearCachedDataAction;
import io.github.jsoagger.jfxcore.components.actions.CloseRowForwardEditionAction;
import io.github.jsoagger.jfxcore.components.actions.CloseSecondaryRSAction;
import io.github.jsoagger.jfxcore.components.actions.CreateObjectLinkFromSearchAction;
import io.github.jsoagger.jfxcore.components.actions.CreateTypedObjectLinkFromSearchAction;
import io.github.jsoagger.jfxcore.components.actions.DeleteElementUsageFromSearchAction;
import io.github.jsoagger.jfxcore.components.actions.DeleteLinkFromSearchAction;
import io.github.jsoagger.jfxcore.components.actions.DoCancelSearchFilteringAction;
import io.github.jsoagger.jfxcore.components.actions.DoChangeDefaultThemeAction;
import io.github.jsoagger.jfxcore.components.actions.DoChangeDefaultThemeFromPreferencesAction;
import io.github.jsoagger.jfxcore.components.actions.DoSearchAction;
import io.github.jsoagger.jfxcore.components.actions.DoSearchAndHideFilteringAction;
import io.github.jsoagger.jfxcore.components.actions.DoSearchSortAction;
import io.github.jsoagger.jfxcore.components.actions.DoSelectFolderFromPreferencesAction;
import io.github.jsoagger.jfxcore.components.actions.DoShowPreferencesInSecondaryRSView;
import io.github.jsoagger.jfxcore.components.actions.HideSecondaryRootStructureAction;
import io.github.jsoagger.jfxcore.components.actions.ModelByMasterOidDoActionAndForwardToViewHandler;
import io.github.jsoagger.jfxcore.components.actions.ModelByMasterOidDoActionAndUpdateCurrentSCMHandler;
import io.github.jsoagger.jfxcore.components.actions.ModelByOidDoActionAndForwardToViewHandler;
import io.github.jsoagger.jfxcore.components.actions.ModelByOidDoActionAndReplaceByViewHandler;
import io.github.jsoagger.jfxcore.components.actions.ModelByOidDoActionAndUpdateCurrentSCMHandler;
import io.github.jsoagger.jfxcore.components.actions.ModelByOidDoActionHandler;
import io.github.jsoagger.jfxcore.components.actions.ModelFromTableRowByMasterOidDoActionAndForwardToViewHandler;
import io.github.jsoagger.jfxcore.components.actions.PopRSContentAction;
import io.github.jsoagger.jfxcore.components.actions.PushToTabContentViewAction;
import io.github.jsoagger.jfxcore.components.actions.PushToViewAction;
import io.github.jsoagger.jfxcore.components.actions.PushToViewActionStaticData;
import io.github.jsoagger.jfxcore.components.actions.PushViewToRSContentAction;
import io.github.jsoagger.jfxcore.components.actions.PushViewToSecondaryRSContentAction;
import io.github.jsoagger.jfxcore.components.actions.SetRootViewEventAction;
import io.github.jsoagger.jfxcore.components.actions.UpdateStructureContentToSelectedFlowModelAction;
import io.github.jsoagger.jfxcore.components.filters.LifecycleManagedFiltersContext;
import io.github.jsoagger.jfxcore.components.filters.RCFiltersContext;
import io.github.jsoagger.jfxcore.components.filters.SearchMasterContextFiltersContext;
import io.github.jsoagger.jfxcore.components.inputView.LifecycleAllStatesInputView;
import io.github.jsoagger.jfxcore.components.inputView.LifecycleInputView;
import io.github.jsoagger.jfxcore.components.modelprovider.ModelRelatedToRootStructureLoader;
import io.github.jsoagger.jfxcore.components.modelprovider.RootStructureModelLoader;
import io.github.jsoagger.jfxcore.components.modelprovider.StructureContentModelLoader;
import io.github.jsoagger.jfxcore.components.modelprovider.StructureContentModelReference;
import io.github.jsoagger.jfxcore.components.search.SimpleSearchForwardEditorFooter;
import io.github.jsoagger.jfxcore.components.search.comps.SearchFiltersBlocTitle;
import io.github.jsoagger.jfxcore.components.search.comps.SearchHeaderComponent;
import io.github.jsoagger.jfxcore.components.search.comps.SearchInputComponent;
import io.github.jsoagger.jfxcore.components.structure.action.GetLatestAndLoadChildrenAndUpdateTableAction;
import io.github.jsoagger.jfxcore.components.structure.action.LoadItemChildrenAndUpdateRSCAction;
import io.github.jsoagger.jfxcore.components.structure.action.LoadItemChildrenAndUpdateTableAction;
import io.github.jsoagger.jfxcore.components.structure.action.LoadPreviousItemChildrenAndUpdateRSCAction;
import io.github.jsoagger.jfxcore.components.structure.action.LoadPreviousItemChildrenAndUpdateTableAction;
import io.github.jsoagger.jfxcore.components.structure.action.LoadRootContainerChildrenFolderAndUpdateTableAction;
import io.github.jsoagger.jfxcore.components.structure.action.LoadRootModelChildrenAndUpdateTableAction;
import io.github.jsoagger.jfxcore.components.toolbar.SearchToolbar;
import io.github.jsoagger.jfxcore.engine.components.listform.dataloader.ApplicationVersionDataLoader;
import io.github.jsoagger.jfxcore.engine.components.listform.dataloader.ByKeySingleListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.components.listform.dataloader.ListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header.EditStructureTableHeaderImpl;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.header.ToolbarController;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreComponentsBeanProvider {

  @Bean
  @Named("ManageObjectInSecondaryRSView")
  @View(locations= {"/io/github/jsoagger/jfxcore/components/manage/ManageObjectInSecondaryRSView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/components/manage/")
  public DoubleHeaderRootStructureController ManageObjectInSecondaryRSView() {
    DoubleHeaderRootStructureController p = new DoubleHeaderRootStructureController();
    p.setMessageSource((MessageSource) Services.getBean("CommonDashboardMessageSource"));
    p.setModelProvider((IModelProvider) BeanFactory.instance().getBean("RootStructureModelLoader"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/components/manage/ManageObjectInSecondaryRSView.json");
    return p;
  }

  @Bean
  @Named("ManageObjectInSecondaryRSContentView")
  @View(locations= {"/io/github/jsoagger/jfxcore/components/manage/ManageObjectInSecondaryRSContentView.xml"},
  outputFilePath="/io/github/jsoagger/jfxcore/components/manage/")
  public RootStructureContentController ManageObjectInSecondaryRSContentView() {
    RootStructureContentController p = new RootStructureContentController();
    p.setMessageSource((MessageSource) Services.getBean("CommonDashboardMessageSource"));
    p.addViewDefinition("/io/github/jsoagger/jfxcore/components/manage/ManageObjectInSecondaryRSContentView.json");
    return p;
  }


  @Bean
  @Named("SearchSecondaryRSHeaderToolbarView")
  @View(locations = {"/io/github/jsoagger/jfxcore/components/header/SearchSecondaryRSHeaderToolbarView.xml",
  "/io/github/jsoagger/jfxcore/engine/controller/HeaderToolbar.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/components/header/")
  public ToolbarController  SearchSecondaryRSHeaderToolbarView() {
    ToolbarController tbc = new ToolbarController();
    tbc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    tbc.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("SecondaryHeaderToolbarResponsiveMatrix"));
    tbc.addViewDefinition("/io/github/jsoagger/jfxcore/components/header/SearchSecondaryRSHeaderToolbarView.json");
    return tbc;
  }

  @Bean
  @Named("SecondaryRSHeaderToolbarView")
  @View(locations = {"/io/github/jsoagger/jfxcore/components/header/SecondaryRSHeaderToolbarView.xml",
  "/io/github/jsoagger/jfxcore/engine/controller/HeaderToolbar.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/components/header/")
  public ToolbarController  SecondaryRSHeaderToolbarView() {
    ToolbarController tbc = new ToolbarController();
    tbc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    tbc.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("SecondaryHeaderToolbarResponsiveMatrix"));
    tbc.addViewDefinition("/io/github/jsoagger/jfxcore/components/header/SecondaryRSHeaderToolbarView.json");
    return tbc;
  }

  @Bean
  @Named("SearchFiltersRSHeaderToolbarView")
  @View(locations = {"/io/github/jsoagger/jfxcore/components/header/SearchFiltersRSHeaderToolbarView.xml",
  "/io/github/jsoagger/jfxcore/engine/controller/HeaderToolbar.xml"},
  outputFilePath = "/io/github/jsoagger/jfxcore/components/header/")
  public ToolbarController  SearchFiltersRSHeaderToolbarView() {
    ToolbarController tbc = new ToolbarController();
    tbc.setMessageSource((MessageSource) Services.getBean("RooStructureMessageSource"));
    tbc.setResponsiveMatrix((IParentResponsiveMatrix) Services.getBean("SearchFiltersToolbarResponsiveMatrix"));
    tbc.addViewDefinition("/io/github/jsoagger/jfxcore/components/header/SearchFiltersRSHeaderToolbarView.json");
    return tbc;
  }

  @Bean
  @Singleton
  @Named("ComponentsMessageSource")
  public MessageSource componentsMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.components.message");
    return messageSource;
  }

  @Bean
  @Singleton
  @Named("CoreComponentsMessageSource")
  public MessageSource CoreComponentsMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setParentMessageSource((MessageSource) Services.getBean("CoreGeneralMessageSource"));
    messageSource.getBasenames().add("io.github.jsoagger.jfxcore.components.message");
    return messageSource;
  }

  @Bean
  @Singleton
  @Named("CoreGeneralMessageSource")
  public MessageSource CoreGeneralMessageSource() {
    MessageSource messageSource = new MessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.getBasenames().add("i18n.generalBundle");
    messageSource.getBasenames().add("i18n.lifecycleBundle");
    messageSource.getBasenames().add("i18n.validationBundle");
    return messageSource;
  }

  @Bean
  @Named("ByKeySingleListFormDataLoader")
  public ByKeySingleListFormDataLoader ByKeySingleListFormDataLoader() {
    ByKeySingleListFormDataLoader p = new ByKeySingleListFormDataLoader();
    p.setGetPreferenceValueOperation((IOperation) Services.getBean("GetPreferenceValueOperation"));
    p.setSetPreferenceValueOperation((IOperation) Services.getBean("SetPreferencesOperation"));
    p.setListValueOperation((IOperation) Services.getBean("ListValuesOperation"));
    return p;
  }

  @Bean
  @Named("ListFormDataLoader")
  public ListFormDataLoader ListFormDataLoader() {
    ListFormDataLoader p = new ListFormDataLoader();
    return p;
  }

  @Bean
  @Named("applicationVersionDataLoader")
  public ApplicationVersionDataLoader ApplicationVersionDataLoader() {
    ApplicationVersionDataLoader p = new ApplicationVersionDataLoader();
    return p;
  }


  @Bean
  @Named("DoChangeDefaultThemeFromPreferencesAction")
  public DoChangeDefaultThemeFromPreferencesAction DoChangeDefaultThemeFromPreferencesAction() {
    DoChangeDefaultThemeFromPreferencesAction p = new DoChangeDefaultThemeFromPreferencesAction();
    p.setGetPreferenceValueOperation((IOperation) Services.getBean("GetPreferenceValueOperation"));
    return p;
  }

  @Bean
  @Named("DoSelectFolderFromPreferencesAction")
  public DoSelectFolderFromPreferencesAction doSelectFolderFromPreferencesAction() {
    DoSelectFolderFromPreferencesAction p = new DoSelectFolderFromPreferencesAction();
    p.setSetPreferenceValueOperation((IOperation) Services.getBean("SetPreferenceValueOperation"));
    return p;
  }

  @Bean
  @Named("HideSecondaryRootStructureAction")
  public HideSecondaryRootStructureAction hideSecondaryRootStructureAction() {
    HideSecondaryRootStructureAction p = new HideSecondaryRootStructureAction();
    return p;
  }


  @Bean
  @Named("DoChangeDefaultThemeAction")
  public DoChangeDefaultThemeAction DoChangeDefaultThemeAction() {
    DoChangeDefaultThemeAction p = new DoChangeDefaultThemeAction();
    return p;
  }

  @Bean
  @Singleton
  @Named("RootStructureModelLoader")
  public IModelProvider rootStructureModelLoader() {
    RootStructureModelLoader loader = new RootStructureModelLoader();
    loader.setLoadContainerByOidOperation(
        (IOperation) Services.getBean("LoadContainerByOidOperation"));
    return loader;
  }

  @Bean
  @Named("StructureContentModelLoader")
  public IModelProvider StructureContentModelLoader() {
    StructureContentModelLoader loader = new StructureContentModelLoader();
    loader.setLoadSimpleModelOperation(
        (IOperation) Services.getBean("PersistableLoadSimpleModelOperation"));
    return loader;
  }

  @Bean
  @Named("ModelRelatedToRootStructureLoader")
  public IModelProvider ModelRelatedToRootStructureLoader() {
    ModelRelatedToRootStructureLoader loader = new ModelRelatedToRootStructureLoader();
    loader.setLoadSimpleModelOperation(
        (IOperation) Services.getBean("PersistableLoadSimpleModelOperation"));
    return loader;
  }

  @Bean
  @Named("PushToViewActionStaticData")
  public PushToViewActionStaticData PushToViewActionStaticData() {
    return new PushToViewActionStaticData();
  }

  @Bean
  @Named("SearchFiltersBlocTitle")
  public SearchFiltersBlocTitle SearchFiltersBlocTitle() {
    return new SearchFiltersBlocTitle();
  }

  @Bean
  @Named("EditStructureTableHeaderImpl")
  public EditStructureTableHeaderImpl EditStructureTableHeaderImpl() {
    return new EditStructureTableHeaderImpl();
  }


  @Bean
  @Named("SimpleSearchForwardEditorFooter")
  public SimpleSearchForwardEditorFooter SimpleSearchForwardEditorFooter() {
    return new SimpleSearchForwardEditorFooter();
  }

  @Bean
  @Named("PushToViewAction")
  public PushToViewAction PushToViewAction() {
    return new PushToViewAction();
  }

  @Bean
  @Named("StructureContentModelReference")
  public IModelProvider StructureContentModelReference() {
    StructureContentModelReference p = new StructureContentModelReference();
    return p;
  }

  @Bean
  @Named("SetRootViewEventAction")
  public SetRootViewEventAction SetRootViewEventAction() {
    SetRootViewEventAction p = new SetRootViewEventAction();
    return p;
  }

  @Bean
  @Named("PushToTabContentViewAction")
  public PushToTabContentViewAction PushToTabContentViewAction() {
    return new io.github.jsoagger.jfxcore.components.actions.PushToTabContentViewAction();
  }

  @Bean
  @Named("PushToTabContentViewAction")
  public PushToViewAction pushToViewAction() {
    return new PushToViewAction();
  }

  @Bean
  @Named("BuildRSContentEventAction")
  public BuildRSContentEventAction BuildRSContentEventAction() {
    return new BuildRSContentEventAction();
  }


  @Bean
  @Named("SearchToolbar")
  public SearchToolbar SearchToolbar() {
    return new SearchToolbar();
  }

  @Bean
  @Named("DoSearchAction")
  public DoSearchAction DoSearchAction() {
    return new DoSearchAction();
  }

  @Bean
  @Named("CloseSecondaryRSAction")
  public CloseSecondaryRSAction CloseSecondaryRSAction() {
    return new CloseSecondaryRSAction();
  }

  @Bean
  @Named("PushViewToRSContentAction")
  public PushViewToRSContentAction PushViewToRSContentAction() {
    return new PushViewToRSContentAction();
  }

  @Bean
  @Named("PushViewToSecondaryRSContentAction")
  public PushViewToSecondaryRSContentAction PushViewToSecondaryRSContentAction() {
    return new PushViewToSecondaryRSContentAction();
  }

  @Bean
  @Named("DoShowPreferencesInSecondaryRSView")
  public DoShowPreferencesInSecondaryRSView DoShowPreferencesInSecondaryRSView() {
    return new DoShowPreferencesInSecondaryRSView();
  }

  @Bean
  @Named("ClearCachedDataAction")
  public ClearCachedDataAction clearCachedDataAction() {
    return new ClearCachedDataAction();
  }

  @Bean
  @Named("ModelByOidDoActionAndForwardToViewHandler")
  public ModelByOidDoActionAndForwardToViewHandler modelByOidDoActionAndForwardToViewHandler() {
    return new ModelByOidDoActionAndForwardToViewHandler();
  }

  @Bean
  @Named("ModelByOidDoActionHandler")
  public ModelByOidDoActionHandler modelByOidDoActionHandler() {
    return new ModelByOidDoActionHandler();
  }

  @Bean
  @Named("ModelByOidDoActionAndReplaceByViewHandler")
  public ModelByOidDoActionAndReplaceByViewHandler modelByOidDoActionAndReplaceByViewHandler() {
    return new ModelByOidDoActionAndReplaceByViewHandler();
  }

  @Bean
  @Named("AddElementUsageFromSearchAction")
  public AddElementUsageFromSearchAction addElementUsageFromSearchAction() {
    return new AddElementUsageFromSearchAction();
  }

  @Bean
  @Named("DeleteElementUsageFromSearchAction")
  public DeleteElementUsageFromSearchAction deleteElementUsageFromSearchAction() {
    return new DeleteElementUsageFromSearchAction();
  }

  @Bean
  @Named("SearchHeaderComponent")
  public SearchHeaderComponent searchHeaderComponent() {
    return new SearchHeaderComponent();
  }

  @Bean
  @Named("UpdateStructureContentToSelectedFlowModelAction")
  public UpdateStructureContentToSelectedFlowModelAction updateStructureContentToSelectedFlowModelAction() {
    return new UpdateStructureContentToSelectedFlowModelAction();
  }

  @Bean
  @Named("ModelByMasterOidDoActionAndForwardToViewHandler")
  public ModelByMasterOidDoActionAndForwardToViewHandler modelByMasterOidDoActionAndForwardToViewHandler() {
    return new ModelByMasterOidDoActionAndForwardToViewHandler();
  }

  @Bean
  @Named("ModelFromTableRowByMasterOidDoActionAndForwardToViewHandler")
  public ModelFromTableRowByMasterOidDoActionAndForwardToViewHandler modelFromTableRowByMasterOidDoActionAndForwardToViewHandler() {
    return new ModelFromTableRowByMasterOidDoActionAndForwardToViewHandler();
  }

  @Bean
  @Named("DoSearchSortAction")
  public DoSearchSortAction doSearchSortAction() {
    return new DoSearchSortAction();
  }

  @Bean
  @Named("CloseRowForwardEditionAction")
  public CloseRowForwardEditionAction closeRowForwardEditionAction() {
    return new CloseRowForwardEditionAction();
  }

  @Bean
  @Named("PopRSContentAction")
  public PopRSContentAction PopRSContentAction() {
    return new PopRSContentAction();
  }

  @Bean
  @Named("DoCancelSearchFilteringAction")
  public DoCancelSearchFilteringAction doCancelSearchFilteringAction() {
    return new DoCancelSearchFilteringAction();
  }

  @Bean
  @Named("DoSearchAndHideFilteringAction")
  public DoSearchAndHideFilteringAction doSearchAndHideFilteringAction() {
    return new DoSearchAndHideFilteringAction();
  }

  @Bean
  @Named("ModelByMasterOidDoActionAndUpdateCurrentSCMHandler")
  public ModelByMasterOidDoActionAndUpdateCurrentSCMHandler modelByMasterOidDoActionAndUpdateCurrentSCMHandler() {
    return new ModelByMasterOidDoActionAndUpdateCurrentSCMHandler();
  }

  @Bean
  @Named("ModelByOidDoActionAndUpdateCurrentSCMHandler")
  public ModelByOidDoActionAndUpdateCurrentSCMHandler modelByOidDoActionAndUpdateCurrentSCMHandler() {
    return new ModelByOidDoActionAndUpdateCurrentSCMHandler();
  }

  @Bean
  @Named("LoadPreviousItemChildrenAndUpdateRSCAction")
  public LoadPreviousItemChildrenAndUpdateRSCAction loadPreviousItemChildrenAndUpdateRSCAction() {
    return new LoadPreviousItemChildrenAndUpdateRSCAction();
  }

  @Bean
  @Named("LoadItemChildrenAndUpdateRSCAction")
  public LoadItemChildrenAndUpdateRSCAction loadItemChildrenAndUpdateRSCAction() {
    return new LoadItemChildrenAndUpdateRSCAction();
  }

  @Bean
  @Named("LoadItemChildrenAndUpdateTableAction")
  public LoadItemChildrenAndUpdateTableAction loadItemChildrenAndUpdateTableAction() {
    return new LoadItemChildrenAndUpdateTableAction();
  }

  @Bean
  @Named("LoadRootModelChildrenAndUpdateTableAction")
  public LoadRootModelChildrenAndUpdateTableAction loadRootModelChildrenAndUpdateTableAction() {
    return new LoadRootModelChildrenAndUpdateTableAction();
  }

  @Bean
  @Named("LoadRootContainerChildrenFolderAndUpdateTableAction")
  public LoadRootContainerChildrenFolderAndUpdateTableAction loadRootContainerChildrenFolderAndUpdateTableAction() {
    return new LoadRootContainerChildrenFolderAndUpdateTableAction();
  }

  @Bean
  @Named("LoadPreviousItemChildrenAndUpdateTableAction")
  public LoadPreviousItemChildrenAndUpdateTableAction loadPreviousItemChildrenAndUpdateTableAction() {
    return new LoadPreviousItemChildrenAndUpdateTableAction();
  }

  @Bean
  @Named("CreateTypedObjectLinkFromSearchAction")
  public CreateTypedObjectLinkFromSearchAction createTypedObjectLinkFromSearchAction() {
    CreateTypedObjectLinkFromSearchAction op = new CreateTypedObjectLinkFromSearchAction();
    op.setCreateLinkOperation((IOperation) Services.getBean("CreateTypedObjectLinkOperation"));
    return op;
  }

  @Bean
  @Named("CreateObjectLinkFromSearchAction")
  public CreateObjectLinkFromSearchAction createObjectLinkFromSearchAction() {
    CreateObjectLinkFromSearchAction op = new CreateObjectLinkFromSearchAction();
    op.setCreateLinkOperation((IOperation) Services.getBean("CreateObjectLinkOperation"));
    return op;
  }

  @Bean
  @Named("DeleteLinkFromSearchAction")
  public DeleteLinkFromSearchAction deleteLinkFromSearchAction() {
    DeleteLinkFromSearchAction op = new DeleteLinkFromSearchAction();
    op.setDeleteLinkOperation((IOperation) Services.getBean("DeleteLinkOperation"));
    return op;
  }

  @Bean
  @Named("GetLatestAndLoadChildrenAndUpdateTableAction")
  public GetLatestAndLoadChildrenAndUpdateTableAction getLatestAndLoadChildrenAndUpdateTableAction() {
    return new GetLatestAndLoadChildrenAndUpdateTableAction();
  }


  @Bean
  @Named("RCFiltersContext")
  public RCFiltersContext rcFiltersContext() {
    return new RCFiltersContext();
  }

  @Bean
  @Named("LifecycleAllStatesInputView")
  public LifecycleAllStatesInputView lifecycleAllStatesInputView() {
    return new LifecycleAllStatesInputView();
  }


  @Bean
  @Named("LifecycleInputView")
  public LifecycleInputView lifecycleInputView() {
    return new LifecycleInputView();
  }


  @Bean
  @Named("SearchFiltersBlocTitle")
  public SearchFiltersBlocTitle searchFiltersBlocTitle() {
    return new SearchFiltersBlocTitle();
  }

  @Bean
  @Named("SearchInputComponent")
  public SearchInputComponent searchInputComponent() {
    return new SearchInputComponent();
  }

  @Bean
  @Named("EditStructureTableHeaderImpl")
  public EditStructureTableHeaderImpl editStructureTableHeaderImpl() {
    return new EditStructureTableHeaderImpl();
  }

  @Bean
  @Named("SimpleSearchForwardEditorFooter")
  public SimpleSearchForwardEditorFooter simpleSearchForwardEditorFooter() {
    return new SimpleSearchForwardEditorFooter();
  }


  @Bean
  @Named("SearchMasterContextFiltersContext")
  public SearchMasterContextFiltersContext searchMasterContextFiltersContext() {
    return new SearchMasterContextFiltersContext();
  }



  @Bean
  @Named("LifecycleManagedFiltersContext")
  public LifecycleManagedFiltersContext lifecycleManagedFiltersContext() {
    return new LifecycleManagedFiltersContext();
  }




  /********************************************************************************************************/

}
