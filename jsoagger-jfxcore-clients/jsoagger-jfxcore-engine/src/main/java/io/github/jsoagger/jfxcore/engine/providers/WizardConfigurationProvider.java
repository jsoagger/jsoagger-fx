/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.action.CloseWizardAction;
import io.github.jsoagger.jfxcore.engine.action.DeleteIterationFromTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.ExpandPrimaryMenuAction;
import io.github.jsoagger.jfxcore.engine.action.ExpandSecondaryMenuAction;
import io.github.jsoagger.jfxcore.engine.action.ExpandTernaryMenuAction;
import io.github.jsoagger.jfxcore.engine.action.LogoutAction;
import io.github.jsoagger.jfxcore.engine.action.RefreshParentTableAction;
import io.github.jsoagger.jfxcore.engine.action.ShowWizardAction;
import io.github.jsoagger.jfxcore.engine.action.ShowWizardFromTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.WizardWaitingAction;
import io.github.jsoagger.jfxcore.engine.action.table.CopySelectedElementsFromTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.table.DeleteObjectLinkFromRecursiveStructureTableRowAction;
import io.github.jsoagger.jfxcore.engine.action.table.DeleteObjectLinkFromTableStructureRowAction;
import io.github.jsoagger.jfxcore.engine.action.table.DeleteSelectedElementsInTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.table.NotImplementedAction;
import io.github.jsoagger.jfxcore.engine.action.table.PasteElementsToTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.table.PrintSelectedElementsInTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.table.PushViewToTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.table.RefreshTableStructureAction;
import io.github.jsoagger.jfxcore.engine.action.table.SelectAllElementsInTableStructureAction;
import io.github.jsoagger.jfxcore.engine.components.notification.action.DeleteAllNotificationsAction;
import io.github.jsoagger.jfxcore.engine.components.notification.action.MarkAllNotificationsReadenAction;
import io.github.jsoagger.jfxcore.engine.components.pagination.StructureContentRelatedModelPaginatedDataLoader;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.DoActionPresenter;
import io.github.jsoagger.jfxcore.engine.components.table.cell.TableContextualActionPresenter;
import io.github.jsoagger.jfxcore.engine.components.wizard.action.BackWizardAction;
import io.github.jsoagger.jfxcore.engine.components.wizard.action.HideWizardAction;
import io.github.jsoagger.jfxcore.engine.components.wizard.action.NextWizardAction;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.SimpleAttributeForwardEditionHandler;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.SimpleAttributeInlineEditionHandler;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.components.SimpleForwardEditor;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.components.SimpleInLineEditor;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.controller.CoreSimpleAttributeUpdateAction;
import io.github.jsoagger.jfxcore.engine.controller.login.action.LoginAction;
import io.github.jsoagger.jfxcore.engine.delegate.PersistenceServiceDelegate;

/**
 * Utility beans for displaying wizard
 *
 * @author Ramilafananana VONJISOA
 *
 */
public class WizardConfigurationProvider {

  @Bean
  @Named("LoginAction")
  public IAction loginAction() {
    LoginAction la = new LoginAction();
    la.setLoginOperation((IOperation) Services.getBean("LoginOperation"));
    return la;
  }

  @Bean
  @Named("LoginAction")
  public IAction logoutAction() {
    LogoutAction la = new LogoutAction();
    la.setLogoutOperation((IOperation) Services.getBean("LogoutOperation"));
    return la;
  }

  @Bean
  @Named("HideWizardAction")
  public IAction HideWizardAction() {
    HideWizardAction la = new HideWizardAction();
    return la;
  }

  @Bean
  @Named("BackWizardAction")
  public IAction backWizardAction() {
    BackWizardAction la = new BackWizardAction();
    return la;
  }

  @Bean
  @Named("NextWizardAction")
  public IAction nextWizardAction() {
    NextWizardAction la = new NextWizardAction();
    return la;
  }

  @Bean
  @Named("ExpandSecondaryMenuAction")
  public IAction expandSecondaryMenuAction() {
    ExpandSecondaryMenuAction la = new ExpandSecondaryMenuAction();
    return la;
  }

  @Bean
  @Named("ExpandTernaryMenuAction")
  public IAction expandTernaryMenuAction() {
    ExpandTernaryMenuAction la = new ExpandTernaryMenuAction();
    return la;
  }

  @Bean
  @Named("ExpandPrimaryMenuAction")
  public IAction expandPrimaryMenuAction() {
    ExpandPrimaryMenuAction la = new ExpandPrimaryMenuAction();
    return la;
  }

  @Bean
  @Named("CoreSimpleAttributeUpdateAction")
  public IAction coreSimpleAttributeUpdateAction() {
    CoreSimpleAttributeUpdateAction la = new CoreSimpleAttributeUpdateAction();
    return la;
  }


  @Bean
  @Named("SimpleForwardEditor")
  public SimpleForwardEditor simpleForwardEditor() {
    SimpleForwardEditor la = new SimpleForwardEditor();
    return la;
  }

  @Bean
  @Named("UpdateAttributeInLineActionHandler")
  public SimpleAttributeInlineEditionHandler updateAttributeInLineActionHandler() {
    SimpleAttributeInlineEditionHandler la = new SimpleAttributeInlineEditionHandler();
    return la;
  }

  @Bean
  @Named("SimpleAttributeForwardEditionHandler")
  public SimpleAttributeForwardEditionHandler simpleAttributeForwardEditionHandler() {
    SimpleAttributeForwardEditionHandler la = new SimpleAttributeForwardEditionHandler();
    return la;
  }

  @Bean
  @Named("SimpleInLineEditor")
  public SimpleInLineEditor simpleInLineEditor() {
    SimpleInLineEditor la = new SimpleInLineEditor();
    return la;
  }

  @Bean
  @Named("PersistenceServiceDelegate")
  public PersistenceServiceDelegate persistenceServiceDelegate() {
    PersistenceServiceDelegate la = new PersistenceServiceDelegate();
    return la;
  }


  @Bean
  @Named("DeleteAllNotificationsAction")
  public DeleteAllNotificationsAction deleteAllNotificationsAction() {
    DeleteAllNotificationsAction la = new DeleteAllNotificationsAction();
    return la;
  }


  @Bean
  @Named("MarkAllNotificationsReadenAction")
  public MarkAllNotificationsReadenAction markAllNotificationsReadenAction() {
    MarkAllNotificationsReadenAction la = new MarkAllNotificationsReadenAction();
    return la;
  }


  @Bean
  @Named("PushViewToTableStructureAction")
  public PushViewToTableStructureAction pushViewToTableStructureAction() {
    PushViewToTableStructureAction la = new PushViewToTableStructureAction();
    return la;
  }


  @Bean
  @Named("ShowWizardAction")
  public ShowWizardAction showWizardAction() {
    ShowWizardAction la = new ShowWizardAction();
    return la;
  }


  @Bean
  @Named("CloseWizardAction")
  public CloseWizardAction closeWizardAction() {
    CloseWizardAction la = new CloseWizardAction();
    return la;
  }


  @Bean
  @Named("RefreshParentTableAction")
  public RefreshParentTableAction refreshParentTableAction() {
    RefreshParentTableAction la = new RefreshParentTableAction();
    return la;
  }

  @Bean
  @Named("TableContextualActionPresenter")
  public TableContextualActionPresenter tableContextualActionPresenter() {
    TableContextualActionPresenter la = new TableContextualActionPresenter();
    return la;
  }

  @Bean
  @Named("DoActionPresenter")
  public DoActionPresenter doActionPresenter() {
    DoActionPresenter la = new DoActionPresenter();
    return la;
  }

  @Bean
  @Named("DeleteObjectLinkFromTableRowAction")
  public DeleteObjectLinkFromTableStructureRowAction deleteObjectLinkFromTableRowAction() {
    DeleteObjectLinkFromTableStructureRowAction la = new DeleteObjectLinkFromTableStructureRowAction();
    return la;
  }

  @Bean
  @Named("DeleteObjectLinkFromRecursiveStructureTableRowAction")
  public DeleteObjectLinkFromRecursiveStructureTableRowAction deleteObjectLinkFromRecursiveStructureTableRowAction() {
    DeleteObjectLinkFromRecursiveStructureTableRowAction la = new DeleteObjectLinkFromRecursiveStructureTableRowAction();
    return la;
  }

  @Bean
  @Named("CopySelectedElementsFromTableStructureAction")
  public CopySelectedElementsFromTableStructureAction copySelectedElementsFromTableStructureAction() {
    CopySelectedElementsFromTableStructureAction la = new CopySelectedElementsFromTableStructureAction();
    return la;
  }

  @Bean
  @Named("DeleteSelectedElementsInTableStructureAction")
  public DeleteSelectedElementsInTableStructureAction deleteSelectedElementsInTableStructureAction() {
    DeleteSelectedElementsInTableStructureAction la = new DeleteSelectedElementsInTableStructureAction();
    return la;
  }

  @Bean
  @Named("RefreshTableStructureAction")
  public RefreshTableStructureAction refreshTableStructureAction() {
    RefreshTableStructureAction la = new RefreshTableStructureAction();
    return la;
  }

  @Bean
  @Named("PrintSelectedElementsInTableStructureAction")
  public PrintSelectedElementsInTableStructureAction printSelectedElementsInTableStructureAction() {
    PrintSelectedElementsInTableStructureAction la = new PrintSelectedElementsInTableStructureAction();
    return la;
  }

  @Bean
  @Named("PasteElementsToTableStructureAction")
  public PasteElementsToTableStructureAction pasteElementsToTableStructureAction() {
    PasteElementsToTableStructureAction la = new PasteElementsToTableStructureAction();
    return la;
  }

  @Bean
  @Named("SelectAllElementsInTableStructureAction")
  public SelectAllElementsInTableStructureAction selectAllElementsInTableStructureAction() {
    SelectAllElementsInTableStructureAction la = new SelectAllElementsInTableStructureAction();
    return la;
  }

  @Bean
  @Named("ShowWizardFromTableStructureAction")
  public ShowWizardFromTableStructureAction showWizardFromTableStructureAction() {
    ShowWizardFromTableStructureAction la = new ShowWizardFromTableStructureAction();
    return la;
  }

  @Bean
  @Named("DeleteIterationFromTableStructureAction")
  public DeleteIterationFromTableStructureAction deleteIterationFromTableStructureAction() {
    DeleteIterationFromTableStructureAction la = new DeleteIterationFromTableStructureAction();
    la.setDeleteOperation((IOperation) Services.getBean("DeleteIterationOperation"));
    return la;
  }

  @Bean
  @Named("StructureContentRelatedModelPaginatedDataLoader")
  public StructureContentRelatedModelPaginatedDataLoader structureContentRelatedModelPaginatedDataLoader() {
    StructureContentRelatedModelPaginatedDataLoader la = new StructureContentRelatedModelPaginatedDataLoader();
    return la;
  }

  @Bean
  @Named("NotImplementedAction")
  public NotImplementedAction notImplementedAction() {
    NotImplementedAction la = new NotImplementedAction();
    return la;
  }

  @Bean
  @Named("WizardWaitingAction")
  public WizardWaitingAction wizardWaitingAction() {
    WizardWaitingAction la = new WizardWaitingAction();
    return la;
  }
}
