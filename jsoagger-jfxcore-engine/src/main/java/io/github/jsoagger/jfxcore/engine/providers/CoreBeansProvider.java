package io.github.jsoagger.jfxcore.engine.providers;


import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.IAccessRuleResolver;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IListViewPaneHeader;
import io.github.jsoagger.jfxcore.api.RowLayout;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.accessrule.AddLinkBetweenTwoObjectsButtonAccessRuleResolver;
import io.github.jsoagger.jfxcore.engine.accessrule.AddObjectToTableFromSearchAccessRuleResolver;
import io.github.jsoagger.jfxcore.engine.accessrule.DeleteLinksBetweenTwoObjectsButtonAccessRuleResolver;
import io.github.jsoagger.jfxcore.engine.accessrule.DeleteObjectFromTableFromSearchAccessRuleResolver;
import io.github.jsoagger.jfxcore.engine.accessrule.LoginButtonAccessRuleResolver;
import io.github.jsoagger.jfxcore.engine.accessrule.LogoutButtonAccessRuleResolver;
import io.github.jsoagger.jfxcore.engine.action.ExpandPrimaryMenuAction;
import io.github.jsoagger.jfxcore.engine.components.autocomplete.VLAutocomplete;
import io.github.jsoagger.jfxcore.engine.components.browser.BrowserTitlePaneBlocContent;
import io.github.jsoagger.jfxcore.engine.components.converter.EnumeratedValueModelConverter;
import io.github.jsoagger.jfxcore.engine.components.converter.GeneratedValueConverter;
import io.github.jsoagger.jfxcore.engine.components.converter.IdenpotentConverter;
import io.github.jsoagger.jfxcore.engine.components.converter.LocalDateConverter;
import io.github.jsoagger.jfxcore.engine.components.converter.StringBooleanConverter;
import io.github.jsoagger.jfxcore.engine.components.dialog.DialogContent;
import io.github.jsoagger.jfxcore.engine.components.dialog.DialogFooter;
import io.github.jsoagger.jfxcore.engine.components.dialog.DialogHeader;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.DynamicalAttributesFormBlocContent;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormBlocContent;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormBlocFooter;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormBlocTitle;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.InternalTabsFormBlocContent;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.ModelThumbsBlocContent;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.ModelVisualisationBlocContent;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.FormFieldset;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.FormFieldsetContent;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.FormFieldsetHeader;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.menu.FieldsetFlowLayout;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.menu.FieldsetVerticalLayout;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.tabed.FieldsetBottomTabedLayout;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.tabed.FieldsetTopTabedLayout;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.tabed.SimpleFieldsetTopTabedLayout;
import io.github.jsoagger.jfxcore.engine.components.form.row.HorizontalFormRowWrapper;
import io.github.jsoagger.jfxcore.engine.components.form.row.LabelLessFormRowWrapper;
import io.github.jsoagger.jfxcore.engine.components.form.row.VerticalFormRowWrapper;
import io.github.jsoagger.jfxcore.engine.components.header.BasicToolbar;
import io.github.jsoagger.jfxcore.engine.components.header.CurrentLocationViewer;
import io.github.jsoagger.jfxcore.engine.components.header.ListViewPaneHeader;
import io.github.jsoagger.jfxcore.engine.components.header.comps.HeaderCenterToolBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.HeaderLeftToolBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.HeaderLessSingleLocationNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.HeaderRightToolbar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.MobileWithBottomTabNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.NoLocationPrimaryMenuWithNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.PrimaryMenuWithNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.ScrollableContent;
import io.github.jsoagger.jfxcore.engine.components.header.comps.SingleLocationNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.SingleLocationPrimaryMenuWithNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.input.AudienceSelector;
import io.github.jsoagger.jfxcore.engine.components.input.AudienceSelectorCellFactory;
import io.github.jsoagger.jfxcore.engine.components.input.InputCheckbox;
import io.github.jsoagger.jfxcore.engine.components.input.InputCombobox;
import io.github.jsoagger.jfxcore.engine.components.input.InputDataImportProcessingPane;
import io.github.jsoagger.jfxcore.engine.components.input.InputDatePicker;
import io.github.jsoagger.jfxcore.engine.components.input.InputDirectoryPicker;
import io.github.jsoagger.jfxcore.engine.components.input.InputFilePicker;
import io.github.jsoagger.jfxcore.engine.components.input.InputHyperlink;
import io.github.jsoagger.jfxcore.engine.components.input.InputPeriodPicker;
import io.github.jsoagger.jfxcore.engine.components.input.InputSortButton;
import io.github.jsoagger.jfxcore.engine.components.input.InputText;
import io.github.jsoagger.jfxcore.engine.components.input.InputTextarea;
import io.github.jsoagger.jfxcore.engine.components.input.InputTimePicker;
import io.github.jsoagger.jfxcore.engine.components.input.InputWebView;
import io.github.jsoagger.jfxcore.engine.components.input.ProcessingButton;
import io.github.jsoagger.jfxcore.engine.components.input.ProfileSimpleButton;
import io.github.jsoagger.jfxcore.engine.components.input.SimpleButton;
import io.github.jsoagger.jfxcore.engine.components.input.WrappedInput;
import io.github.jsoagger.jfxcore.engine.components.inputview.BasicAttributeInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.BlobTextInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.CheckboxInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.DateInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.IdentifiableInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.MultiValuedInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.NumberInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.PeriodInputView;
import io.github.jsoagger.jfxcore.engine.components.inputview.TextInputView;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.FormBlocProcessor;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.FormFieldsetListProcessor;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.FormFieldsetProcessor;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.SimpleDetailsViewAllFieldsetsProcessor;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.SingleBlobBlocsFieldsetProcessor;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.ToolBarProcessor;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.UserBigChipsListProcessor;
import io.github.jsoagger.jfxcore.engine.components.listform.BooleanListFormCellFactory;
import io.github.jsoagger.jfxcore.engine.components.listform.LegalMentionItemPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.MultiSelectionListFormItemPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.ParentItemPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.SystemUpdateListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.SystemVersionListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.dataloader.ApplicationVersionDataLoader;
import io.github.jsoagger.jfxcore.engine.components.listform.dataloader.ListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.components.menu.Copyright;
import io.github.jsoagger.jfxcore.engine.components.menu.PrimaryMenuUserProfile;
import io.github.jsoagger.jfxcore.engine.components.message.EmptyNoContentPane;
import io.github.jsoagger.jfxcore.engine.components.message.GroupedActionsContentPane;
import io.github.jsoagger.jfxcore.engine.components.message.NoContentPane;
import io.github.jsoagger.jfxcore.engine.components.message.NoContentPaneProcessor;
import io.github.jsoagger.jfxcore.engine.components.pagination.DefaultEntityPaginatedDataLoader;
import io.github.jsoagger.jfxcore.engine.components.pagination.DefaultLinkPaginatedDataLoader;
import io.github.jsoagger.jfxcore.engine.components.pagination.GenericEntityPaginatedDataLoader;
import io.github.jsoagger.jfxcore.engine.components.pagination.LoadMorePaginationBar;
import io.github.jsoagger.jfxcore.engine.components.pagination.SimplePaginationBar;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.DefaultEnumeratedValueTranslater;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.GenericModelAttributetoTablePresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.ModelDateTimePresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.descriptionprovider.ModelDescriptionPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.descriptionprovider.ModelMasterDescriptionPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.AccountThumbPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.ModelMimeTypePresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.ModelThumbPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.PeopleThumbPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.SmallItemInitialIconCircle;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.TableIndicatorColumnPresenter;
import io.github.jsoagger.jfxcore.engine.components.tab.MobileVLTabpane;
import io.github.jsoagger.jfxcore.engine.components.tab.VLTabpane;
import io.github.jsoagger.jfxcore.engine.components.table.cell.BlankSpacerTableCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.table.cell.HEllipsisTableCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.table.cell.TableRowMimeTypePresenter;
import io.github.jsoagger.jfxcore.engine.components.table.cell.TableRowMultipleActionPresenter;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SimpleTableStructureLayoutManager;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.TableStructureProcessor;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.TableStructureWithLayoutProcessor;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.TableStructuresVBoxProcessor;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.flow.FlowContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.flow.FlowTableContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.list.ListViewContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header.BasicTableHeaderImpl;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header.FiltrableTableHeaderImpl;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header.FiltrableTableHeaderImpl2;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header.StructureNavigatorTableHeaderImpl;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.tree.TreeContent;
import io.github.jsoagger.jfxcore.engine.components.toolbar.htoolbar.BasicHToolbar;
import io.github.jsoagger.jfxcore.engine.components.toolbar.htoolbar.ModifiableHToolbar;
import io.github.jsoagger.jfxcore.engine.components.toolbar.inline.FormInlineAction;
import io.github.jsoagger.jfxcore.engine.components.toolbar.inline.FormInlineEditAction;
import io.github.jsoagger.jfxcore.engine.components.toolbar.inline.FormInlineToobar;
import io.github.jsoagger.jfxcore.engine.components.wizard.Wizard;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardContent;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardFooter;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardHeader;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardStep;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardStepContent;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardStepFooter;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardStepHeader;
import io.github.jsoagger.jfxcore.engine.components.wizard.content.WizardContentLayoutSelectorLeft;
import io.github.jsoagger.jfxcore.engine.components.wizard.content.WizardContentLayoutSelectorTop;
import io.github.jsoagger.jfxcore.engine.components.wizard.impl.EmptyStepHeader;
import io.github.jsoagger.jfxcore.engine.components.wizard.impl.EmptyWizardFooter;
import io.github.jsoagger.jfxcore.engine.components.wizard.impl.EmptyWizardHeader;
import io.github.jsoagger.jfxcore.engine.components.wizard.stepper.WizardHorizontalStepper;
import io.github.jsoagger.jfxcore.engine.components.wizard.stepper.WizardVerticalStepper;
import io.github.jsoagger.jfxcore.engine.controller.detailsview.layout.MaximizedDetailsView;
import io.github.jsoagger.jfxcore.engine.controller.login.component.LoginWizardFooter;
import io.github.jsoagger.jfxcore.engine.controller.login.component.LoginWizardFooterToolbar;
import io.github.jsoagger.jfxcore.engine.controller.login.component.LoginWizardStepHeader;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.ScrollLessSinglePaneViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.model.DefaultEnumeratedValueLoader;
import io.github.jsoagger.jfxcore.engine.model.LinkableRoleBsFromCurrentContextLoader;
import io.github.jsoagger.jfxcore.engine.model.LinkableRoleBsLoader;

/**
 *
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreBeansProvider {

  public CoreBeansProvider() {}

  @Bean
  @Named("InputCheckbox")
  public InputCheckbox InputCheckbox() {
    return new InputCheckbox();
  }

  @Bean
  @Named("InputCombobox")
  public InputCombobox InputCombobox() {
    return new InputCombobox();
  }

  @Bean
  @Named("ToolBarProcessor")
  public ToolBarProcessor ToolBarProcessor() {
    return new ToolBarProcessor();
  }

  @Bean
  @Named("InputDataImportProcessingPane")
  public InputDataImportProcessingPane InputDataImportProcessingPane() {
    return new InputDataImportProcessingPane();
  }


  @Bean
  @Named("InputDatePicker")
  public InputDatePicker InputDatePicker() {
    return new InputDatePicker();
  }

  @Bean
  @Named("InputTimePicker")
  public InputTimePicker InputTimePicker() {
    return new InputTimePicker();
  }

  @Bean
  @Named("InputDirectoryPicker")
  public InputDirectoryPicker InputDirectoryPicker() {
    return new InputDirectoryPicker();
  }

  @Bean
  @Named("InputFilePicker")
  public InputFilePicker InputFilePicker() {
    return new InputFilePicker();
  }

  @Bean
  @Named("InputPeriodPicker")
  public InputPeriodPicker InputPeriodPicker() {
    return new InputPeriodPicker();
  }


  @Bean
  @Named("InputText")
  public InputText InputText() {
    return new InputText();
  }

  @Bean
  @Named("InputTextarea")
  public InputTextarea InputTextarea() {
    return new InputTextarea();
  }

  @Bean
  @Named("AutoComplete")
  public VLAutocomplete AutoComplete() {
    return new VLAutocomplete();
  }

  @Bean
  @Named("InputSortButton")
  public InputSortButton InputSortButton() {
    return new InputSortButton();
  }


  @Bean
  @Named("InputHyperlink")
  public InputHyperlink InputHyperlink() {
    return new InputHyperlink();
  }

  @Bean
  @Named("InputWebView")
  public InputWebView InputWebView() {
    return new InputWebView();
  }

  @Bean
  @Named("AudienceSelector")
  public AudienceSelector AudienceSelector() {
    return new AudienceSelector();
  }

  @Bean
  @Named("MultiValuedInputView")
  public MultiValuedInputView MultiValuedInputView() {
    return new MultiValuedInputView();
  }

  @Bean
  @Named("VerticalFormRowLayout")
  public RowLayout VerticalFormRowLayout() {
    return new VerticalFormRowWrapper();
  }

  @Bean
  @Named("HorizontalFormRowLayout")
  public RowLayout HorizontalFormRowLayout() {
    return new HorizontalFormRowWrapper();
  }

  @Bean
  @Named("LabelLessFormRowWrapper")
  public LabelLessFormRowWrapper LabelLessFormRowWrapper() {
    return new LabelLessFormRowWrapper();
  }

  @Bean
  @Named("WrappedInput")
  public WrappedInput WrappedInput() {
    return new WrappedInput();
  }

  /****************************************************************************************************************************************************************
   * INPUTS IEW
   *********************************************************************************************************************************************************************/


  @Bean
  @Named("BasicAttributeInputView")
  public BasicAttributeInputView BasicAttributeInputView() {
    return new BasicAttributeInputView();
  }

  @Bean
  @Named("BlobTextInputView")
  public BlobTextInputView BlobTextInputView() {
    return new BlobTextInputView();
  }

  @Bean
  @Named("TextInputView")
  public TextInputView TextInputView() {
    return new TextInputView();
  }

  @Bean
  @Named("DateInputView")
  public DateInputView DateInputView() {
    return new DateInputView();
  }



  @Bean
  @Named("NumberInputView")
  public NumberInputView NumberInputView() {
    return new NumberInputView();
  }



  @Bean
  @Named("PeriodInputView")
  public PeriodInputView PeriodInputView() {
    return new PeriodInputView();
  }



  @Bean
  @Named("IdentifiableInputView")
  public IdentifiableInputView IdentifiableInputView() {
    return new IdentifiableInputView();
  }

  @Bean
  @Named("SimpleButton")
  public SimpleButton SimpleButton() {
    return new SimpleButton();
  }

  @Bean
  @Named("ProfileSimpleButton")
  public ProfileSimpleButton ProfileSimpleButton() {
    return new ProfileSimpleButton();
  }

  @Bean
  @Named("ProcessingButton")
  public ProcessingButton ProcessingButton() {
    return new ProcessingButton();
  }

  @Bean
  @Named("CurrentLocationViewer")
  public CurrentLocationViewer CurrentLocationViewer() {
    return new CurrentLocationViewer();
  }

  @Bean
  @Named("BasicToolbar")
  public BasicToolbar BasicToolbar() {
    return new BasicToolbar();
  }

  @Bean
  @Named("UserProfile")
  public PrimaryMenuUserProfile UserProfile() {
    return new PrimaryMenuUserProfile();
  }

  @Bean
  @Named("Copyright")
  public Copyright Copyright() {
    return new Copyright();
  }

  @Bean
  @Named("CheckboxInputView")
  public CheckboxInputView CheckboxInputView() {
    return new CheckboxInputView();
  }

  @Bean
  @Named("FormInlineEditAction")
  public FormInlineEditAction FormInlineEditAction() {
    return new FormInlineEditAction();
  }

  @Bean
  @Named("FormInlineAction")
  public FormInlineAction FormInlineAction() {
    return new FormInlineAction();
  }

  @Bean
  @Named("FormBlocFooter")
  public FormBlocFooter FormBlocFooter() {
    return new FormBlocFooter();
  }

  @Bean
  @Named("ModelMasterDescriptionPresenter")
  public ModelMasterDescriptionPresenter ModelMasterDescriptionPresenter() {
    return new ModelMasterDescriptionPresenter();
  }

  @Bean
  @Named("ScrollableContent")
  public ScrollableContent ScrollableContent() {
    return new ScrollableContent();
  }

  @Bean
  @Named("TableIndicatorColumnPresenter")
  public TableIndicatorColumnPresenter TableIndicatorColumnPresenter() {
    return new TableIndicatorColumnPresenter();
  }

  @Bean
  @Named("FormFieldsetProcessor")
  public FormFieldsetProcessor FormFieldsetProcessor() {
    return new FormFieldsetProcessor();
  }

  @Bean
  @Named("FormFieldsetListProcessor")
  public FormFieldsetListProcessor FormFieldsetListProcessor() {
    return new FormFieldsetListProcessor();
  }


  @Bean
  @Named("SimpleDetailsViewAllFieldsetsProcessor")
  public SimpleDetailsViewAllFieldsetsProcessor SimpleDetailsViewAllFieldsetsProcessor() {
    return new SimpleDetailsViewAllFieldsetsProcessor();
  }


  @Bean
  @Named("NoContentPaneProcessor")
  public NoContentPaneProcessor NoContentPaneProcessor() {
    return new NoContentPaneProcessor();
  }


  @Bean
  @Named("SingleBlobBlocsFieldsetProcessor")
  public SingleBlobBlocsFieldsetProcessor SingleBlobBlocsFieldsetProcessor() {
    return new SingleBlobBlocsFieldsetProcessor();
  }


  @Bean
  @Named("UserBigChipsListProcessor")
  public UserBigChipsListProcessor UserBigChipsListProcessor() {
    return new UserBigChipsListProcessor();
  }


  @Bean
  @Named("FormBlocProcessor")
  public FormBlocProcessor FormBlocProcessor() {
    return new FormBlocProcessor();
  }


  @Bean
  @Named("WizardContentLayoutSelectorLeft")
  public WizardContentLayoutSelectorLeft WizardContentLayoutSelectorLeft() {
    return new WizardContentLayoutSelectorLeft();
  }


  @Bean
  @Named("WizardContentLayoutSelectorTop")
  public WizardContentLayoutSelectorTop WizardContentLayoutSelectorTop() {
    return new WizardContentLayoutSelectorTop();
  }


  @Bean
  @Named("WizardHorizontalStepper")
  public WizardHorizontalStepper WizardHorizontalStepper() {
    return new WizardHorizontalStepper();
  }


  @Bean
  @Named("WizardVerticalStepper")
  public WizardVerticalStepper WizardVerticalStepper() {
    return new WizardVerticalStepper();
  }


  @Bean
  @Named("EmptyWizardFooter")
  public EmptyWizardFooter EmptyWizardFooter() {
    return new EmptyWizardFooter();
  }


  @Bean
  @Named("EmptyWizardHeader")
  public EmptyWizardHeader EmptyWizardHeader() {
    return new EmptyWizardHeader();
  }


  @Bean
  @Named("EmptyStepHeader")
  public EmptyStepHeader EmptyStepHeader() {
    return new EmptyStepHeader();
  }


  @Bean
  @Named("FlowContentHeader")
  public ListViewPaneHeader FlowContentHeader() {
    return new ListViewPaneHeader();
  }


  @Bean
  @Named("ListViewPaneHeader")
  public ListViewPaneHeader ListViewPaneHeader() {
    return new ListViewPaneHeader();
  }

  @Bean
  @Named("SimplePaginationBar")
  public SimplePaginationBar SimplePaginationBar() {
    return new SimplePaginationBar();
  }

  @Bean
  @Named("LoadMorePaginationBar")
  public LoadMorePaginationBar LoadMorePaginationBar() {
    return new LoadMorePaginationBar();
  }

  @Bean
  @Named("AudienceSelectorCellFactory")
  public AudienceSelectorCellFactory AudienceSelectorCellFactory() {
    return new AudienceSelectorCellFactory();
  }

  @Bean
  @Named("NoContentPane")
  public NoContentPane NoContentPane() {
    return new NoContentPane();
  }

  @Bean
  @Named("EmptyNoContentPane")
  public EmptyNoContentPane EmptyNoContentPane() {
    return new EmptyNoContentPane();
  }

  @Bean
  @Named("GroupedActionsContentPane")
  public GroupedActionsContentPane GroupedActionsContentPane() {
    return new GroupedActionsContentPane();
  }

  @Bean
  @Named("FormInlineToobar")
  public FormInlineToobar FormInlineToobar() {
    return new FormInlineToobar();
  }

  @Bean
  @Named("BrowserTitlePaneBlocContent")
  public BrowserTitlePaneBlocContent BrowserTitlePaneBlocContent() {
    return new BrowserTitlePaneBlocContent();
  }

  @Bean
  @Named("SingleLocationNavigationBar")
  public SingleLocationNavigationBar SingleLocationNavigationBar() {
    return new SingleLocationNavigationBar();
  }

  @Bean
  @Named("HeaderLessSingleLocationNavigationBar")
  public HeaderLessSingleLocationNavigationBar HeaderLessSingleLocationNavigationBar() {
    return new HeaderLessSingleLocationNavigationBar();
  }

  @Bean
  @Named("MobileWithBottomTabNavigationBar")
  public MobileWithBottomTabNavigationBar MobileWithBottomTabNavigationBar() {
    return new MobileWithBottomTabNavigationBar();
  }

  @Bean
  @Named("SingleLocationPrimaryMenuWithNavigationBar")
  public SingleLocationPrimaryMenuWithNavigationBar SingleLocationPrimaryMenuWithNavigationBar() {
    return new SingleLocationPrimaryMenuWithNavigationBar();
  }

  @Bean
  @Named("NoLocationPrimaryMenuWithNavigationBar")
  public NoLocationPrimaryMenuWithNavigationBar NoLocationPrimaryMenuWithNavigationBar() {
    return new NoLocationPrimaryMenuWithNavigationBar();
  }

  @Bean
  @Named("PrimaryMenuWithNavigationBar")
  public PrimaryMenuWithNavigationBar PrimaryMenuWithNavigationBar() {
    return new PrimaryMenuWithNavigationBar();
  }


  @Bean
  @Named("HeaderRightToolbar")
  public HeaderRightToolbar HeaderRightToolbar() {
    return new HeaderRightToolbar();
  }


  @Bean
  @Named("HeaderCenterToolBar")
  public HeaderCenterToolBar HeaderCenterToolBar() {
    return new HeaderCenterToolBar();
  }

  @Bean
  @Named("HeaderLeftToolBar")
  public HeaderLeftToolBar HeaderLeftToolBar() {
    return new HeaderLeftToolBar();
  }


  @Bean
  @Named("VLTabPane")
  public VLTabpane VLTabPane() {
    return new MobileVLTabpane();
  }

  @Bean
  @Named("MobileVLTabpane")
  public VLTabpane MobileVLTabpane() {
    return new MobileVLTabpane();
  }


  @Bean
  @Named("ScrollLessSinglePaneViewLayoutManager")
  public ScrollLessSinglePaneViewLayoutManager ScrollLessSinglePaneViewLayoutManager() {
    return new ScrollLessSinglePaneViewLayoutManager();
  }



  @Bean
  @Named("TreeContent")
  public TreeContent TreeContent() {
    return new TreeContent();
  }



  @Bean
  @Named("ApplicationVersionValuePresenter")
  public MultiSelectionListFormItemPresenter ApplicationVersionValuePresenter() {
    return new MultiSelectionListFormItemPresenter();
  }


  @Bean
  @Named("DefaultMultiSelectionPreferenceValuePresenter")
  public MultiSelectionListFormItemPresenter defaultMultiSelectionPreferenceValuePresenter() {
    return new MultiSelectionListFormItemPresenter();
  }


  @Bean
  @Named("HEllipsisTableCellPresenter")
  public HEllipsisTableCellPresenter HEllipsisTableCellPresenter() {
    return new HEllipsisTableCellPresenter();
  }


  @Bean
  @Named("ForwardToStructureCellPresenter")
  public io.github.jsoagger.jfxcore.engine.components.table.cell.TableContextualActionPresenter ForwardToStructureCellPresenter() {
    return new io.github.jsoagger.jfxcore.engine.components.table.cell.TableContextualActionPresenter();
  }


  @Bean
  @Named("DeleteElementTableRowActionPresenter")
  public io.github.jsoagger.jfxcore.engine.components.table.cell.TableRowActionPresenter DeleteElementTableRowActionPresenter() {
    return new io.github.jsoagger.jfxcore.engine.components.table.cell.TableRowActionPresenter();
  }


  @Bean
  @Named("TableRowActionPresenter")
  public io.github.jsoagger.jfxcore.engine.components.table.cell.TableRowActionPresenter TableRowActionPresenter() {
    return new io.github.jsoagger.jfxcore.engine.components.table.cell.TableRowActionPresenter();
  }

  @Bean
  @Named("TableRowMultipleActionPresenter")
  public TableRowMultipleActionPresenter TableRowMultipleActionPresenter() {
    return new TableRowMultipleActionPresenter();
  }


  @Bean
  @Named("ListFormDataLoader")
  public ListFormDataLoader ListFormDataLoader() {
    return new ListFormDataLoader();
  }


  @Bean
  @Named("legalMentionItemPresenter")
  public LegalMentionItemPresenter legalMentionItemPresenter() {
    return new LegalMentionItemPresenter();
  }


  @Bean
  @Named("parentItemPresenter")
  public ParentItemPresenter TreeTableContent() {
    return new ParentItemPresenter();
  }


  @Bean
  @Named("systemUpdatePresenter")
  public SystemUpdateListFormCellPresenter systemUpdatePresenter() {
    return new SystemUpdateListFormCellPresenter();
  }


  @Bean
  @Named("systemVersionPresenter")
  public SystemVersionListFormCellPresenter systemVersionPresenter() {
    return new SystemVersionListFormCellPresenter();
  }


  @Bean
  @Named("applicationVersionDataLoader")
  public ApplicationVersionDataLoader applicationVersionDataLoader() {
    return new ApplicationVersionDataLoader();
  }


  @Bean
  @Named("FormFieldsetHeader")
  public FormFieldsetHeader FormFieldsetHeader() {
    return new FormFieldsetHeader();
  }


  @Bean
  @Named("FormFieldsetContent")
  public FormFieldsetContent FormFieldsetContent() {
    return new FormFieldsetContent();
  }


  @Bean
  @Named("FormFieldset")
  public FormFieldset FormFieldset() {
    return new FormFieldset();
  }



  @Bean
  @Named("DynamicalAttributesFormBlocContent")
  public DynamicalAttributesFormBlocContent DynamicalAttributesFormBlocContent() {
    return new DynamicalAttributesFormBlocContent();
  }



  @Bean
  @Named("FormBlocContent")
  public FormBlocContent FormBlocContent() {
    return new FormBlocContent();
  }



  @Bean
  @Named("FormBlocTitle")
  public FormBlocTitle FormBlocTitle() {
    return new FormBlocTitle();
  }



  @Bean
  @Named("ModelThumbsBlocContent")
  public ModelThumbsBlocContent ModelThumbsBlocContent() {
    return new ModelThumbsBlocContent();
  }



  @Bean
  @Named("ModelVisualisationBlocContent")
  public ModelVisualisationBlocContent ModelVisualisationBlocContent() {
    ModelVisualisationBlocContent c = new ModelVisualisationBlocContent();
    c.afterPropertiesSet();
    c.setWidth(300);
    c.setHeight(300);
    return c;
  }



  @Bean
  @Named("InternalTabsFormBlocContent")
  public InternalTabsFormBlocContent InternalTabsFormBlocContent() {
    return new InternalTabsFormBlocContent();
  }



  @Bean
  @Named("PeopleThumbPresenter")
  public PeopleThumbPresenter PeopleThumbPresenter() {
    return new PeopleThumbPresenter();
  }



  @Bean
  @Named("DialogHeader")
  public DialogHeader DialogHeader() {
    return new DialogHeader();
  }



  @Bean
  @Named("DialogContent")
  public DialogContent DialogContent() {
    return new DialogContent();
  }



  @Bean
  @Named("DialogFooter")
  public DialogFooter DialogFooter() {
    return new DialogFooter();
  }



  @Bean
  @Named("Wizard")
  public Wizard Wizard() {
    return new Wizard();
  }



  @Bean
  @Named("WizardFooter")
  public WizardFooter WizardFooter() {
    return new WizardFooter();
  }



  @Bean
  @Named("LoginWizardFooter")
  public LoginWizardFooter LoginWizardFooter() {
    return new LoginWizardFooter();
  }



  @Bean
  @Named("LoginWizardStepHeader")
  public LoginWizardStepHeader LoginWizardStepHeader() {
    return new LoginWizardStepHeader();
  }



  @Bean
  @Named("WizardHeader")
  public WizardHeader WizardHeader() {
    return new WizardHeader();
  }



  @Bean
  @Named("WizardStep")
  public WizardStep WizardStep() {
    return new WizardStep();
  }



  @Bean
  @Named("WizardStepHeader")
  public WizardStepHeader WizardStepHeader() {
    return new WizardStepHeader();
  }



  @Bean
  @Named("WizardStepFooter")
  public WizardStepFooter WizardStepFooter() {
    return new WizardStepFooter();
  }


  @Bean
  @Named("WizardStepContent")
  public WizardStepContent WizardStepContent() {
    return new WizardStepContent();
  }


  @Bean
  @Named("WizardContent")
  public WizardContent WizardContent() {
    return new WizardContent();
  }


  @Bean
  @Named("LoginWizardFooterToolbar")
  public LoginWizardFooterToolbar LoginWizardFooterToolbar() {
    return new LoginWizardFooterToolbar();
  }


  @Bean
  @Named("FieldsetBottomTabedLayout")
  public FieldsetBottomTabedLayout FieldsetBottomTabedLayout() {
    return new FieldsetBottomTabedLayout();
  }


  @Bean
  @Named("FieldsetTopTabedLayout")
  public FieldsetTopTabedLayout FieldsetTopTabedLayout() {
    return new FieldsetTopTabedLayout();
  }


  @Bean
  @Named("FieldsetVerticalLayout")
  public FieldsetVerticalLayout FieldsetVerticalLayout() {
    return new FieldsetVerticalLayout();
  }



  @Bean
  @Named("FieldsetFlowLayout")
  public FieldsetFlowLayout FieldsetFlowLayout() {
    return new FieldsetFlowLayout();
  }


  @Bean
  @Named("SimpleFieldsetTopTabedLayout")
  public SimpleFieldsetTopTabedLayout SimpleFieldsetTopTabedLayout() {
    return new SimpleFieldsetTopTabedLayout();
  }


  @Bean
  @Named("BasicHToolbar")
  public BasicHToolbar BasicHToolbar() {
    return new BasicHToolbar();
  }


  @Bean
  @Named("ModifiableHToolbar")
  public ModifiableHToolbar ModifiableHToolbar() {
    return new ModifiableHToolbar();
  }


  @Bean
  @Named("DefaultEnumeratedValueLoader")
  public DefaultEnumeratedValueLoader DefaultEnumeratedValueLoader() {
    DefaultEnumeratedValueLoader e = new DefaultEnumeratedValueLoader();
    e.setListvaluesOperation((IOperation) Services.getBean("ListvaluesOperation"));
    return e;
  }

  @Bean
  @Named("LinkableRoleBsLoader")
  public LinkableRoleBsLoader LinkableRoleBsLoader() {
    return new LinkableRoleBsLoader();
  }

  @Bean
  @Named("LinkableRoleBsFromCurrentContextLoader")
  public LinkableRoleBsFromCurrentContextLoader LinkableRoleBsFromCurrentContextLoader() {
    return new LinkableRoleBsFromCurrentContextLoader();
  }

  @Bean
  @Named("MaximizedDetailsView")
  public MaximizedDetailsView MaximizedDetailsView() {
    return new MaximizedDetailsView();
  }

  @Bean
  @Named("LocalDateConverter")
  public LocalDateConverter LocalDateConverter() {
    return new LocalDateConverter();
  }

  @Bean
  @Named("GeneratedValueConverter")
  public GeneratedValueConverter GeneratedValueConverter() {
    return new GeneratedValueConverter();
  }

  @Bean
  @Named("IdenpotentConverter")
  public IdenpotentConverter IdenpotentConverter() {
    return new IdenpotentConverter();
  }

  @Bean
  @Named("EnumeratedValueModelConverter")
  public EnumeratedValueModelConverter EnumeratedValueModelConverter() {
    return new EnumeratedValueModelConverter();
  }

  @Bean
  @Named("StringBooleanConverter")
  public StringBooleanConverter StringBooleanConverter() {
    return new StringBooleanConverter();
  }

  @Bean
  @Named("DefaultEnumeratedValueTranslater")
  public DefaultEnumeratedValueTranslater DefaultEnumeratedValueTranslater() {
    return new DefaultEnumeratedValueTranslater();
  }

  @Bean
  @Named("TableContent")
  public TableContent TableContent() {
    return new TableContent();
  }

  @Bean
  @Named("FlowContent")
  public FlowContent FlowContent() {
    return new FlowContent();
  }

  @Bean
  @Named("FlowTableContent")
  public FlowTableContent FlowTableContent() {
    return new FlowTableContent();
  }

  @Bean
  @Named("AdvancedTableStructureLayoutManager")
  public io.github.jsoagger.jfxcore.engine.components.tablestructure.AdvancedTableStructureLayoutManager AdvancedTableStructureLayoutManager() {
    return new io.github.jsoagger.jfxcore.engine.components.tablestructure.AdvancedTableStructureLayoutManager();
  }

  @Bean
  @Named("SimpleTableStructureLayoutManager")
  public SimpleTableStructureLayoutManager SimpleTableStructureLayoutManager() {
    return new SimpleTableStructureLayoutManager();
  }

  @Bean
  @Named("TableStructureProcessor")
  public TableStructureProcessor TableStructureProcessor() {
    return new TableStructureProcessor();
  }

  @Bean
  @Named("TableStructuresVBoxProcessor")
  public TableStructuresVBoxProcessor TableStructuresVBoxProcessor() {
    return new TableStructuresVBoxProcessor();
  }

  @Bean
  @Named("TableStructureWithLayoutProcessor")
  public TableStructureWithLayoutProcessor TableStructureWithLayoutProcessor() {
    return new TableStructureWithLayoutProcessor();
  }

  @Bean
  @Named("AccountThumbPresenter")
  public AccountThumbPresenter AccountThumbPresenter() {
    return new AccountThumbPresenter();
  }

  @Bean
  @Named("ModelThumbPresenter")
  public ModelThumbPresenter ModelThumbPresenter() {
    return new ModelThumbPresenter();
  }

  @Bean
  @Named("ModelMimeTypePresenter")
  public ModelMimeTypePresenter ModelMimeTypePresenter() {
    return new ModelMimeTypePresenter();
  }

  @Bean
  @Named("TableRowMimeTypePresenter")
  public TableRowMimeTypePresenter TableRowMimeTypePresenter() {
    return new TableRowMimeTypePresenter();
  }

  @Bean
  @Named("BlankSpacerTableCellPresenter")
  public BlankSpacerTableCellPresenter BlankSpacerTableCellPresenter() {
    return new BlankSpacerTableCellPresenter();
  }

  @Bean
  @Named("SmallItemInitialIconCircle")
  public SmallItemInitialIconCircle SmallItemInitialIconCircle() {
    return new SmallItemInitialIconCircle();
  }

  @Bean
  @Named("ModelAttributePresenter")
  public io.github.jsoagger.jfxcore.engine.components.presenter.impl.ModelAttributePresenter ModelAttributePresenter() {
    return new io.github.jsoagger.jfxcore.engine.components.presenter.impl.ModelAttributePresenter();
  }

  @Bean
  @Named("ModelDateTimePresenter")
  public ModelDateTimePresenter ModelDateTimePresenter() {
    return new ModelDateTimePresenter();
  }

  @Bean
  @Named("GenericModelAttributetoTablePresenter")
  public GenericModelAttributetoTablePresenter GenericModelAttributetoTablePresenter() {
    return new GenericModelAttributetoTablePresenter();
  }

  @Bean
  @Named("SmallMasterNameItemInitialIconCircle")
  public SmallItemInitialIconCircle SmallMasterNameItemInitialIconCircle() {
    SmallItemInitialIconCircle p = new SmallItemInitialIconCircle();
    p.setIdentityPresenter(
        (ModelIdentityPresenter) Services.getBean("RCMasterNameIdentityPresenter"));
    return p;
  }

  @Bean
  @Named("BasicTableHeaderImpl")
  public IListViewPaneHeader BasicTableHeaderImpl() {
    return new BasicTableHeaderImpl();
  }

  @Bean
  @Named("FiltrableTableHeaderImpl")
  public IListViewPaneHeader FiltrableTableHeaderImpl() {
    return new FiltrableTableHeaderImpl();
  }

  @Bean
  @Named("FiltrableTableHeaderImpl2")
  public IListViewPaneHeader FiltrableTableHeaderImpl2() {
    return new FiltrableTableHeaderImpl2();
  }

  @Bean
  @Named("FolderNavigatorTableHeader")
  public IListViewPaneHeader FolderNavigatorTableHeader() {
    return new StructureNavigatorTableHeaderImpl();
  }


  @Bean
  @Named("GenericEntityPaginatedDataLoader")
  public GenericEntityPaginatedDataLoader GenericEntityPaginatedDataLoader() {
    return new GenericEntityPaginatedDataLoader();
  }

  @Bean
  @Named("DefaultEntityPaginatedDataLoader")
  public DefaultEntityPaginatedDataLoader DefaultEntityPaginatedDataLoader() {
    return new DefaultEntityPaginatedDataLoader();
  }

  @Bean
  @Named("DefaultLinkPaginatedDataLoader")
  public DefaultLinkPaginatedDataLoader DefaultLinkPaginatedDataLoader() {
    return new DefaultLinkPaginatedDataLoader();
  }

  @Bean
  @Named("ListViewContent")
  public ListViewContent ListViewContent() {
    return new ListViewContent();
  }


  @Bean
  @Named("BooleanListFormCellFactory")
  public BooleanListFormCellFactory BooleanListFormCellFactory() {
    BooleanListFormCellFactory p = new BooleanListFormCellFactory();
    return p;
  }

  @Bean
  @Named("ExpandPrimaryMenuAction")
  public IAction ExpandPrimaryMenuAction() {
    return new ExpandPrimaryMenuAction();
  }

  @Bean
  @Named("ModelDescriptionPresenter")
  public ModelDescriptionPresenter ModelDescriptionPresenter() {
    ModelDescriptionPresenter p = new ModelDescriptionPresenter();
    return p;
  }

  @Bean
  @Named("loginButtonAccessRuleResolver")
  public IAccessRuleResolver loginButtonAccessRuleResolver() {
    LoginButtonAccessRuleResolver p = new LoginButtonAccessRuleResolver();
    return p;
  }

  @Bean
  @Named("logoutButtonAccessRuleResolver")
  public IAccessRuleResolver logoutButtonAccessRuleResolver() {
    LogoutButtonAccessRuleResolver p = new LogoutButtonAccessRuleResolver();
    return p;
  }

  @Bean
  @Named("DeleteLinksBetweenTwoObjectsButtonAccessRuleResolver")
  public IAccessRuleResolver deleteLinksBetweenTwoObjectsButtonAccessRuleResolver() {
    DeleteLinksBetweenTwoObjectsButtonAccessRuleResolver p =
        new DeleteLinksBetweenTwoObjectsButtonAccessRuleResolver();
    return p;
  }


  @Bean
  @Named("AddLinkBetweenTwoObjectsButtonAccessRuleResolver")
  public IAccessRuleResolver addLinkBetweenTwoObjectsButtonAccessRuleResolver() {
    AddLinkBetweenTwoObjectsButtonAccessRuleResolver p =
        new AddLinkBetweenTwoObjectsButtonAccessRuleResolver();
    return p;
  }

  @Bean
  @Named("AddObjectToTableFromSearchAccessRuleResolver")
  public IAccessRuleResolver addObjectToTableFromSearchAccessRuleResolver() {
    AddObjectToTableFromSearchAccessRuleResolver p =
        new AddObjectToTableFromSearchAccessRuleResolver();
    return p;
  }

  @Bean
  @Named("DeleteObjectFromTableFromSearchAccessRuleResolver")
  public IAccessRuleResolver deleteObjectFromTableFromSearchAccessRuleResolver() {
    DeleteObjectFromTableFromSearchAccessRuleResolver p =
        new DeleteObjectFromTableFromSearchAccessRuleResolver();
    return p;
  }
}
