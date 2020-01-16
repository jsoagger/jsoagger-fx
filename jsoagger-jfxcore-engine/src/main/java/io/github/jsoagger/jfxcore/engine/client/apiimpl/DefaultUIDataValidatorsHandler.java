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

package io.github.jsoagger.jfxcore.engine.client.apiimpl;


import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.IUIDataValidator;
import io.github.jsoagger.jfxcore.api.IUIDataValidatorHandler;

/**
 * Call validate of listed {@link IUIDataValidator}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DefaultUIDataValidatorsHandler implements IUIDataValidatorHandler {

  //private static final Logger logR = LoggerFactory.getLogger(DefaultUIDataValidatorsHandler.class);

  private List<IUIDataValidator> validators = new ArrayList<>();
  private IUIDataValidationResult validationResult = UIDataValidationResult.success();


  /**
   * Constructor
   */
  public DefaultUIDataValidatorsHandler() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public IUIDataValidationResult validate(IActionRequest actionRequest) {
    for (IUIDataValidator dataValidator : validators) {
      validationResult = dataValidator.validate(actionRequest, validationResult);
    }

    return validationResult;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IUIDataValidator> getValidators() {
    return validators;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setValidators(List<IUIDataValidator> validators) {
    this.validators = validators;
  }


  /**
   * Getter of validationResult
   *
   * @return the validationResult
   */
  public IUIDataValidationResult getValidationResult() {
    return validationResult;
  }


  /**
   * Setter of validationResult
   *
   * @param validationResult the validationResult to set
   */
  public void setValidationResult(IUIDataValidationResult validationResult) {
    this.validationResult = validationResult;
  }
}
