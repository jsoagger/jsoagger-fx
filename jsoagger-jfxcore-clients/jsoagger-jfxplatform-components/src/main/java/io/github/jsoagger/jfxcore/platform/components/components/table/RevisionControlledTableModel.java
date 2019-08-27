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

package io.github.jsoagger.jfxcore.platform.components.components.table;



import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.platform.components.model.PersistableObjectModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * RevisionControlledEntry
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class RevisionControlledTableModel extends PersistableObjectModel {

  private static final long serialVersionUID = 3862767164275329602L;

  protected SimpleStringProperty version = new SimpleStringProperty();
  protected SimpleStringProperty iteration = new SimpleStringProperty();
  protected SimpleStringProperty workingCopy = new SimpleStringProperty();
  protected SimpleStringProperty lockedSince = new SimpleStringProperty();
  protected SimpleStringProperty lockedBy = new SimpleStringProperty();
  protected SimpleStringProperty status = new SimpleStringProperty();
  protected SimpleStringProperty identificationNumber;


  /**
   * Constructor
   */
  public RevisionControlledTableModel() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void setData(Object newValue) {
    super.setData(newValue);

    // final RevisionControlledVO obj = (RevisionControlledVO) entry.get();
    // setIdentificationNumber("");
    // setName(obj.getName());
    // setVersion(obj.getVersionInfo().getVersionId());
    // setIteration(String.valueOf(obj.getIterationInfo().getIterationNumber()));
    // setLockedBy(obj.getWorkInfo().getLockedBy());
    // setLockedSince(df.format(obj.getWorkInfo().getLockedSince()));
    // setWorkingCopy("");
  }


  public StringProperty iterationProperty() {
    return iteration;
  }


  public StringProperty workingCopyProperty() {
    return workingCopy;
  }


  public StringProperty lockedSinceProperty() {
    return lockedSince;
  }


  public StringProperty lockedByProperty() {
    return lockedBy;
  }


  public StringProperty identificationNumberProperty() {
    return identificationNumber;
  }


  // --------------------------------------------------------------------------------------------//
  // ****************************** VERSION COLUMN
  // **********************************************//
  // --------------------------------------------------------------------------------------------//
  public StringProperty versionProperty() {
    return version;
  }


  /**
   * @return the version
   */
  public String getVersion() {
    return version.get();
  }


  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version.set(version);
  }


  // --------------------------------------------------------------------------------------------//
  // ****************************** NAME COLUMN
  // **********************************************//
  // --------------------------------------------------------------------------------------------//

  /**
   * @return the iteration
   */
  public String getIteration() {
    return iteration.get();
  }


  /**
   * @param iteration the iteration to set
   */
  public void setIteration(String iteration) {
    if (!StringUtils.isEmpty(iteration)) {
      this.iteration.set(iteration);
    }
  }


  /**
   * @return the workingCopy
   */
  public String getWorkingCopy() {
    return workingCopy.get();
  }


  /**
   * @param workingCopy the workingCopy to set
   */
  public void setWorkingCopy(String workingCopy) {
    if (!StringUtils.isEmpty(workingCopy)) {
      this.workingCopy.set(workingCopy);
    }
  }


  /**
   * @return the lockedSince
   */
  public String getLockedSince() {
    return lockedSince.get();
  }


  /**
   * @param lockedSince the lockedSince to set
   */
  public void setLockedSince(String lockedSince) {
    if (!StringUtils.isEmpty(lockedSince)) {
      this.lockedSince.set(lockedSince);
    }
  }


  /**
   * @return the lockedBy
   */
  public String getLockedBy() {
    return lockedBy.get();
  }


  /**
   * @param lockedBy the lockedBy to set
   */
  public void setLockedBy(String lockedBy) {
    if (!StringUtils.isEmpty(lockedBy)) {
      this.lockedBy.set(lockedBy);
    }
  }


  /**
   * @return the identificationNumber
   */
  public String getIdentificationNumber() {
    return identificationNumber.get();
  }


  /**
   * @param identificationNumber the identificationNumber to set
   */
  public void setIdentificationNumber(String identificationNumber) {
    if (!StringUtils.isEmpty(identificationNumber)) {
      this.identificationNumber.set(identificationNumber);
    }
  }
}
