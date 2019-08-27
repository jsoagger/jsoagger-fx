/**
 *
 */
package io.github.jsoagger.jfxcore.components;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.components.actions.FlowItemActionPresenter;
import io.github.jsoagger.jfxcore.components.presenter.ModelMasterAttributePresenter;
import io.github.jsoagger.jfxcore.components.presenter.ModelRevisionPresenter;
import io.github.jsoagger.jfxcore.components.presenter.ModelStatusPresenter;
import io.github.jsoagger.jfxcore.components.presenter.ModelWorkStatusPresenter;
import io.github.jsoagger.jfxcore.components.presenter.PeopleFullIdentityPresenter;
import io.github.jsoagger.jfxcore.components.presenter.RCIteratedFullIdentityHeaderPresenter;
import io.github.jsoagger.jfxcore.components.presenter.RCIteratedFullIdentityPresenter;
import io.github.jsoagger.jfxcore.components.presenter.RCMasterDescriptionPresenter;
import io.github.jsoagger.jfxcore.components.presenter.RCMasterFullIdentityHeaderPresenter;
import io.github.jsoagger.jfxcore.components.presenter.RCMasterFullIdentityPresenter;
import io.github.jsoagger.jfxcore.components.presenter.RCMasterNameIdentityPresenter;
import io.github.jsoagger.jfxcore.components.presenter.RCMasterNamePresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.components.listform.LegalMentionItemPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.ListFormCellFactory;
import io.github.jsoagger.jfxcore.engine.components.listform.MultiSelectionListFormItemPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.ParentItemPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.PreferenceDoActionPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.listform.SystemUpdateListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.SystemVersionListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.YesNoListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.LargeItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.MediumItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.SmallItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.ModelAttributePresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider.AdminStaticIconPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.DoActionPresenter;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreComponentsPresentersBeanProvider {

  @Bean
  @Named("ModelMasterAttributePresenter")
  public ModelMasterAttributePresenter modelMasterAttributePresenter() {
    return new ModelMasterAttributePresenter();
  }

  @Bean
  @Named("RCMasterFullIdentityPresenter")
  public RCMasterFullIdentityPresenter rCMasterFullIdentityPresenter() {
    return new RCMasterFullIdentityPresenter();
  }

  @Bean
  @Named("RCMasterNameIdentityPresenter")
  public RCMasterNameIdentityPresenter rCMasterNameIdentityPresenter() {
    return new RCMasterNameIdentityPresenter();
  }

  @Bean
  @Named("RCMasterDescriptionPresenter")
  public RCMasterDescriptionPresenter RCMasterDescriptionPresenter() {
    return new RCMasterDescriptionPresenter();
  }

  @Bean
  @Named("RCIteratedFullIdentityHeaderPresenter")
  public RCIteratedFullIdentityHeaderPresenter rCIteratedFullIdentityHeaderPresenter() {
    return new RCIteratedFullIdentityHeaderPresenter();
  }

  @Bean
  @Named("PeopleFullIdentityPresenter")
  public PeopleFullIdentityPresenter peopleFullIdentityPresenter() {
    return new PeopleFullIdentityPresenter();
  }

  @Bean
  @Named("PeopleEmailPresenter")
  public ModelAttributePresenter peopleEmailPresenter() {
    ModelAttributePresenter p = new ModelAttributePresenter();
    p.setAttributePath("attributes.email");
    return p;
  }


  @Bean
  @Named("RCMasterFullIdentityHeaderPresenter")
  public RCMasterFullIdentityHeaderPresenter rCMasterFullIdentityHeaderPresenter() {
    return new RCMasterFullIdentityHeaderPresenter();
  }

  @Bean
  @Named("ModelWorkStatusPresenter")
  public ModelWorkStatusPresenter modelWorkStatusPresenter() {
    return new ModelWorkStatusPresenter();
  }

  @Bean
  @Named("ModelRevisionPresenter")
  public ModelRevisionPresenter modelRevisionPresenter() {
    return new ModelRevisionPresenter();
  }

  @Bean
  @Named("RCMasterNamePresenter")
  public RCMasterNamePresenter rCMasterNamePresenter() {
    return new RCMasterNamePresenter();
  }

  @Bean
  @Named("ModelStatusPresenter")
  public ModelStatusPresenter modelStatusPresenter() {
    return new ModelStatusPresenter();
  }

  @Bean
  @Named("RCIteratedFullIdentityPresenter")
  public RCIteratedFullIdentityPresenter rCIteratedFullIdentityPresenter() {
    return new RCIteratedFullIdentityPresenter();
  }

  @Bean
  @Named("HRCIteratedFullIdentityPresenter")
  public RCIteratedFullIdentityPresenter hRCIteratedFullIdentityPresenter() {
    RCIteratedFullIdentityPresenter rcit = new RCIteratedFullIdentityPresenter();
    rcit.setOrientation("horizontal");
    return rcit;
  }


  @Bean
  @Named("applicationVersionValuePresenter")
  public MultiSelectionListFormItemPresenter applicationVersionValuePresenter() {
    MultiSelectionListFormItemPresenter p = new MultiSelectionListFormItemPresenter();
    p.setDataLoader((IListFormDataLoader) Services.getBean("applicationVersionDataLoader"));
    return p;
  }

  @Bean
  @Named("defaultMultiSelectionPreferenceValuePresenter")
  public MultiSelectionListFormItemPresenter defaultMultiSelectionPreferenceValuePresenter() {
    MultiSelectionListFormItemPresenter p = new MultiSelectionListFormItemPresenter();
    p.setDataLoader((IListFormDataLoader) Services.getBean("ListFormDataLoader"));
    return p;
  }

  @Bean
  @Named("legalMentionItemPresenter")
  public LegalMentionItemPresenter LegalMentionItemPresenter() {
    LegalMentionItemPresenter p = new LegalMentionItemPresenter();
    return p;
  }

  @Bean
  @Named("parentItemPresenter")
  public ParentItemPresenter ParentItemPresenter() {
    ParentItemPresenter p = new ParentItemPresenter();
    return p;
  }


  @Bean
  @Named("systemUpdatePresenter")
  public SystemUpdateListFormCellPresenter SystemUpdateListFormCellPresenter() {
    SystemUpdateListFormCellPresenter p = new SystemUpdateListFormCellPresenter();
    return p;
  }

  @Bean
  @Named("systemVersionPresenter")
  public SystemVersionListFormCellPresenter SystemVersionListFormCellPresenter() {
    SystemVersionListFormCellPresenter p = new SystemVersionListFormCellPresenter();
    return p;
  }


  @Bean
  @Named("PreferencesListCellFactory")
  public ListFormCellFactory PreferencesListCellFactory() {
    ListFormCellFactory p = new ListFormCellFactory();
    return p;
  }

  @Bean
  @Named("PreferenceDoActionPresenterFactory")
  public PreferenceDoActionPresenterFactory PreferenceDoActionPresenterFactory() {
    PreferenceDoActionPresenterFactory p = new PreferenceDoActionPresenterFactory();
    return p;
  }

  @Bean
  @Named("DoActionPresenter")
  public DoActionPresenter DoActionPresenter() {
    DoActionPresenter p = new DoActionPresenter();
    return p;
  }


  @Bean
  @Named("AdminStaticIconPresenter")
  public static ModelIconPresenter AdminStaticIconPresenter() {
    AdminStaticIconPresenter p = new AdminStaticIconPresenter();
    return p;
  }

  @Bean
  @Named("FlowItemActionPresenter")
  public FlowItemActionPresenter FlowItemActionPresenter() {
    return new FlowItemActionPresenter();
  }

  @Bean
  @Named("yesNoItemPresenter")
  public YesNoListFormCellPresenter yesNoItemPresenter() {
    YesNoListFormCellPresenter p = new YesNoListFormCellPresenter();
    p.setSetPreferenceValueOperation((IOperation) Services.getBean("SetPreferencesValueOperation"));
    return p;
  }


  @Bean
  @Named("SmallSearchResultItemPresenter")
  public SmallItemPresenterFactory smallSearchResultItemPresenter() {
    SmallItemPresenterFactory pr = new SmallItemPresenterFactory();
    pr.setIconPresenter((ModelIconPresenter) Services.getBean("ModelSoftTypeIconPresenter"));
    pr.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    pr.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return pr;
  }

  @Bean
  @Named("MediumItemPresenterFactory")
  public MediumItemPresenterFactory mediumItemPresenterFactory() {
    MediumItemPresenterFactory pr = new MediumItemPresenterFactory();
    pr.setIconPresenter((ModelIconPresenter) Services.getBean("ModelSoftTypeIconPresenter"));
    pr.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    pr.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return pr;
  }

  @Bean
  @Named("LargerSearchResultItemPresenter")
  public LargeItemPresenterFactory largerSearchResultItemPresenter() {
    LargeItemPresenterFactory pr = new LargeItemPresenterFactory();
    pr.setIconPresenter((ModelIconPresenter) Services.getBean("ModelSoftTypeIconPresenter"));
    pr.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    pr.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return pr;
  }

  @Bean
  @Named("LargerSearchResultItemPresenter2")
  public LargeItemPresenterFactory LargerSearchResultItemPresenter2() {
    LargeItemPresenterFactory pr = new LargeItemPresenterFactory();
    pr.setIconPresenter((ModelIconPresenter) Services.getBean("ModelSoftTypeIconPresenter"));
    pr.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    pr.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelMasterDescriptionPresenter"));
    return pr;
  }

  @Bean
  @Named("SmallFixedSearchResultItemPresenter")
  public SmallItemPresenterFactory SmallItemPresenterFactory() {
    SmallItemPresenterFactory pr = new SmallItemPresenterFactory();
    pr.setIdentityPresenter((ModelIdentityPresenter) Services.getBean("ModelNameIdentityPresenter"));
    pr.setSecondaryLabelPresenter((ModelSecondaryLabelPresenter) Services.getBean("ModelDescriptionPresenter"));
    return pr;
  }
}
