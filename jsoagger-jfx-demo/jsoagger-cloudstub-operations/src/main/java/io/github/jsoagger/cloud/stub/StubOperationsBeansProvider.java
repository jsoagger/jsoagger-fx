/**
 *
 */
package io.github.jsoagger.cloud.stub;


import io.github.jsoagger.cloud.stub.operations.StubEmptyMultipleResultOperation;
import io.github.jsoagger.cloud.stub.operations.StubGetCurrentUserOperation;
import io.github.jsoagger.cloud.stub.operations.StubGetIllustrationOperation;
import io.github.jsoagger.cloud.stub.operations.StubGetInstanciableSoftTypesOperation;
import io.github.jsoagger.cloud.stub.operations.StubGetPreferenceValueOperation;
import io.github.jsoagger.cloud.stub.operations.StubGetThumbOperation;
import io.github.jsoagger.cloud.stub.operations.StubGetTypeByOidOperation;
import io.github.jsoagger.cloud.stub.operations.StubGetTypeByPathOperation;
import io.github.jsoagger.cloud.stub.operations.StubListvaluesOperation;
import io.github.jsoagger.cloud.stub.operations.StubLoadContainerByOidOperation;
import io.github.jsoagger.cloud.stub.operations.StubLoadContainerByPathOperation;
import io.github.jsoagger.cloud.stub.operations.StubLoginOperation;
import io.github.jsoagger.cloud.stub.operations.StubPaginatedTableDataOperation;
import io.github.jsoagger.cloud.stub.operations.StubPersistableLoadSimpleModelOperation;
import io.github.jsoagger.cloud.stub.operations.StubSetPreferenceValueOperation;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.core.ioc.api.annotations.Singleton;
import io.github.jsoagger.jfxcore.api.services.Services;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@BeansProvider
public class StubOperationsBeansProvider {

  @Bean
  @Singleton
  @Named("LoginOperation")
  public IOperation loginOperation() {
    return new StubLoginOperation();
  }

  @Bean
  @Named("StubPaginatedTableDataOperation")
  public IOperation stubPaginatedTableDataOperation() {
    return new StubPaginatedTableDataOperation();
  }

  @Bean
  @Singleton
  @Named("SetPreferencesOperation")
  public IOperation setPreferencesValueOperation() {
    return new StubSetPreferenceValueOperation();
  }

  @Bean
  @Singleton
  @Named("SetPreferencesValueOperation")
  public IOperation setPreferenceValueOperation() {
    return new StubSetPreferenceValueOperation();
  }

  @Bean
  @Singleton
  @Named("SetPreferenceValueOperation")
  public IOperation SetPreferenceValueOperation() {
    return new StubSetPreferenceValueOperation();
  }

  @Bean
  @Singleton
  @Named("PreferencesOperation")
  public IOperation preferencesOperation() {
    return new StubPersistableLoadSimpleModelOperation();
  }

  @Bean
  @Singleton
  @Named("LoadAllCurrentUserPreferencesValueOperation")
  public IOperation preferencesOperation2() {
    return new StubPersistableLoadSimpleModelOperation();
  }


  @Bean
  @Singleton
  @Named("PersistableLoadSimpleModelOperation")
  public IOperation persistableLoadSimpleModelOperation() {
    StubPersistableLoadSimpleModelOperation op = new StubPersistableLoadSimpleModelOperation();
    op.setOperation((IOperation) Services.getBean("GetTypeByOidOperation"));
    return op;
  }


  @Bean
  @Named("PaginatedTableDataOperation")
  public IOperation paginatedTableDataOperation() {
    return new StubPaginatedTableDataOperation();
  }


  @Bean
  @Named("LoadContainerByOidOperation")
  public IOperation loadContainerByOidOperation() {
    return new StubLoadContainerByOidOperation();
  }

  @Bean
  @Singleton
  @Named("ListvaluesOperation")
  public IOperation listvaluesOperation() {
    return new StubListvaluesOperation();
  }

  @Bean
  @Singleton
  @Named("ListValuesOperation")
  public IOperation ListeValuesOperation() {
    return new StubListvaluesOperation();
  }

  @Bean
  @Singleton
  @Named("GetTypeByPathOperation")
  public IOperation getTypeByPathOperation() {
    return new StubGetTypeByPathOperation();
  }


  @Bean
  @Singleton
  @Named("GetTypeByOidOperation")
  public IOperation getTypeByOidOperation() {
    return new StubGetTypeByOidOperation();
  }

  @Bean
  @Named("GetThumbOperation")
  public IOperation getThumbOperation() {
    return new StubGetThumbOperation();
  }

  @Bean
  @Singleton
  @Named("GetPreferenceValueOperation")
  public IOperation getPreferenceValueOperation() {
    return new StubGetPreferenceValueOperation();
  }

  @Bean
  @Named("GetIllustrationOperation")
  public IOperation getIllustrationOperation() {
    return new StubGetIllustrationOperation();
  }

  @Bean
  @Singleton
  @Named("GetCurrentUserOperation")
  public IOperation getCurrentUserOperation() {
    return new StubGetCurrentUserOperation();
  }

  @Bean
  @Named("EmptyMultipleResultOperation")
  public IOperation emptyMultipleResultOperation() {
    return new StubEmptyMultipleResultOperation();
  }

  @Bean
  @Named("LoadContainerByPathOperation")
  public IOperation loadContainerByPathOperation() {
    return new StubLoadContainerByPathOperation();
  }

  @Bean
  @Named("GetInstanciableSoftTypesOperation")
  public IOperation GetInstanciableSoftTypesOperation() {
    StubGetInstanciableSoftTypesOperation op = new StubGetInstanciableSoftTypesOperation();
    op.setOperation((IOperation) Services.getBean("GetTypeByPathOperation"));
    return op;
  }

  @Bean
  @Named("StubGetInstanciableSoftTypesOperation")
  public IOperation getInstanciableSoftTypesOperation() {
    StubGetInstanciableSoftTypesOperation op = new StubGetInstanciableSoftTypesOperation();
    op.setOperation((IOperation) Services.getBean("GetTypeByPathOperation"));
    return op;
  }
}
