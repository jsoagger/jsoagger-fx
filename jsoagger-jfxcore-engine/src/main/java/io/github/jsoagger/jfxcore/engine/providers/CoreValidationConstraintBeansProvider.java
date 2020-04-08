/**
 *
 */
package io.github.jsoagger.jfxcore.engine.providers;

import io.github.jsoagger.core.ioc.api.annotations.Bean;
import io.github.jsoagger.core.ioc.api.annotations.BeansProvider;
import io.github.jsoagger.core.ioc.api.annotations.Named;
import io.github.jsoagger.jfxcore.api.IVLConstraint;
import io.github.jsoagger.jfxcore.engine.components.validation.VLAlphaNumericConstraint;
import io.github.jsoagger.jfxcore.engine.components.validation.VLConstraintMaxLength;
import io.github.jsoagger.jfxcore.engine.components.validation.VLConstraintMinLength;
import io.github.jsoagger.jfxcore.engine.components.validation.VLConstraintNotBlank;
import io.github.jsoagger.jfxcore.engine.components.validation.VLConstraintRequired;
import io.github.jsoagger.jfxcore.engine.components.validation.VLEmailConstraint;
import io.github.jsoagger.jfxcore.engine.components.validation.VLPhoneNumberConstraints;

/**
 * @author Ramilafananana VONJISOA
 *
 */
@BeansProvider
public class CoreValidationConstraintBeansProvider {

  public CoreValidationConstraintBeansProvider() {}

  @Bean
  @Named("VLAlphaNumericConstraint")
  public IVLConstraint VLAlphaNumericConstraint() {
    IVLConstraint c = new VLAlphaNumericConstraint();
    c.setErrorMessageKey("ERROR_ALPHANUMERIC_VALUE");
    return c;
  }

  @Bean
  @Named("VLConstraintRequired")
  public IVLConstraint VLConstraintRequired() {
    IVLConstraint c = new VLConstraintRequired();
    c.setErrorMessageKey("ERROR_MANDATORY_FIELD");
    return c;
  }

  @Bean
  @Named("VLConstraintMaxLength")
  public IVLConstraint VLConstraintMaxLength() {
    IVLConstraint c = new VLConstraintMaxLength();
    c.setErrorMessageKey("ERROR_VALIDATION_MAX_LENGTH");
    return c;
  }

  @Bean
  @Named("VLConstraintMinLength")
  public IVLConstraint VLConstraintMinLength() {
    IVLConstraint c = new VLConstraintMinLength();
    c.setErrorMessageKey("ERROR_VALIDATION_MIN_LENGTH");
    return c;
  }

  @Bean
  @Named("VLConstraintNotBlank")
  public IVLConstraint VLConstraintNotBlank() {
    IVLConstraint c = new VLConstraintNotBlank();
    c.setErrorMessageKey("ERROR_NOT_BLANK_FIELD");
    return c;
  }

  @Bean
  @Named("VLEmailConstraint")
  public IVLConstraint VLEmailConstraint() {
    IVLConstraint c = new VLEmailConstraint();
    c.setErrorMessageKey("ERROR_MAIL_NOT_VALID");
    return c;
  }

  @Bean
  @Named("VLPhoneNumberConstraints")
  public IVLConstraint VLPhoneNumberConstraints() {
    IVLConstraint c = new VLPhoneNumberConstraints();
    c.setErrorMessageKey("ERROR_PHONE_NOT_VALID");
    return c;
  }
}
