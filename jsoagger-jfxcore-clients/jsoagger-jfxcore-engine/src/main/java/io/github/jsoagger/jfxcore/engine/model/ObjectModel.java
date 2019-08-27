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

package io.github.jsoagger.jfxcore.engine.model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.github.jsoagger.core.bridge.operation.JsonUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ObjectModel implements Serializable {

  private static final long serialVersionUID = -2806127423399538572L;
  protected static final String TRANSFERTDATEFORMAT = "yyyy-MM-dd hh:mm:ss";

  protected final DateTimeFormatter thisYearformatter = DateTimeFormatter.ofPattern("dd MMM");
  protected final DateTimeFormatter otherYearsformatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

  protected ObjectProperty<Object> entry = new SimpleObjectProperty<>();
  protected BooleanProperty selected = new SimpleBooleanProperty(false);
  protected SimpleStringProperty name = new SimpleStringProperty();
  protected SimpleStringProperty description = new SimpleStringProperty();

  protected ObjectProperty<LocalDateTime> lastModificationDate = new SimpleObjectProperty<>();
  protected ObjectProperty<LocalDateTime> creationDate = new SimpleObjectProperty<>();
  protected SimpleStringProperty lastModification = new SimpleStringProperty();
  protected SimpleStringProperty creation = new SimpleStringProperty();
  protected SimpleStringProperty modifiedBy = new SimpleStringProperty();
  protected SimpleStringProperty createdBy = new SimpleStringProperty();


  /**
   * Constructor
   */
  public ObjectModel() {
    addSetDataListener();
    Bindings.bindBidirectional(creation, creationDate, new LocalDateTimeStringConverter());
    Bindings.bindBidirectional(lastModification, lastModificationDate, new LocalDateTimeStringConverter());
  }


  /**
   */
  protected void addSetDataListener() {
    entry.addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
      if (newValue == null) {
        clearData();
      } else {
        setData(newValue);
      }
    });
  }


  /**
   * @param newValue
   */
  protected void setData(Object newValue) {
    ReflectionUIUtils.setPropertyValue(newValue, this, "attributes.name", "name");
    ReflectionUIUtils.setPropertyValue(newValue, this, "attributes.description", "description");
    ReflectionUIUtils.setDateFromStringPropertyValue(newValue, this, "attributes.createDate", "creationDate", TRANSFERTDATEFORMAT);
    ReflectionUIUtils.setDateFromStringPropertyValue(newValue, this, "attributes.lastModifiedDate", "lastModificationDate", TRANSFERTDATEFORMAT);
    ReflectionUIUtils.setPropertyValue(newValue, this, "attributes.lastModifiedBy", "modifiedBy");
    ReflectionUIUtils.setPropertyValue(newValue, this, "attributes.createdBy", "createdBy");
  }


  /**
   * Get the value of attribute "type" corresponding to "full identifier" from entry.
   *
   * @return
   */
  public String getEntryFullId() {
    Object val = null;
    if (entry.get() != null) {
      val = ReflectionUIUtils.invokeGetterOn(entry.get(), "type");
    }

    return val == null ? null : val.toString();
  }


  /**
   * Add a {@link ChangeListener} to a property.
   *
   * @param changerListener
   * @param propertyName
   */
  public void addChangeListener(String propertyName, ChangeListener<? super Object> changerListener) {
    try {
      ReflectionUIUtils.addPropertyChangeListener(this, propertyName, changerListener);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *
   */
  public void clearData() {}


  /**
   * @return the thisYearformatter
   */
  public DateTimeFormatter getThisYearformatter() {
    return thisYearformatter;
  }


  /**
   * @return the otherYearsformatter
   */
  public DateTimeFormatter getOtherYearsformatter() {
    return otherYearsformatter;
  }


  /**
   * @return
   */
  public BooleanProperty selectedProperty() {
    return this.selected;
  }


  public boolean isSelected() {
    return this.selectedProperty().get();
  }


  public void setSelected(final boolean selected) {
    this.selectedProperty().set(selected);
  }


  public SimpleStringProperty nameProperty() {
    return this.name;
  }


  public java.lang.String getName() {
    return this.nameProperty().get();
  }


  public void setName(final java.lang.String name) {
    this.nameProperty().set(name);
  }


  public SimpleStringProperty descriptionProperty() {
    return this.description;
  }


  public java.lang.String getDescription() {
    return this.descriptionProperty().get();
  }


  public void setDescription(final java.lang.String description) {
    this.descriptionProperty().set(description);
  }


  public SimpleStringProperty lastModificationProperty() {
    return this.lastModification;
  }


  public java.lang.String getLastModification() {
    return this.lastModificationProperty().get();
  }


  public void setLastModification(final java.lang.String lastModification) {
    this.lastModificationProperty().set(lastModification);
  }


  public SimpleStringProperty modifiedByProperty() {
    return this.modifiedBy;
  }


  public java.lang.String getModifiedBy() {
    return this.modifiedByProperty().get();
  }


  public void setModifiedBy(final java.lang.String modifiedBy) {
    this.modifiedByProperty().set(modifiedBy);
  }


  public SimpleStringProperty createdByProperty() {
    return this.createdBy;
  }


  public java.lang.String getCreatedBy() {
    return this.createdByProperty().get();
  }


  public void setCreatedBy(final java.lang.String createdBy) {
    this.createdByProperty().set(createdBy);
  }


  public final ObjectProperty<Object> entryProperty() {
    return this.entry;
  }


  public final java.lang.Object getEntry() {
    return this.entryProperty().get();
  }


  public final void setEntry(final java.lang.Object entry) {
    this.entryProperty().set(entry);
  }


  public final ObjectProperty<LocalDateTime> lastModificationDateProperty() {
    return this.lastModificationDate;
  }


  public final java.time.LocalDateTime getLastModificationDate() {
    return this.lastModificationDateProperty().get();
  }


  public final void setLastModificationDate(final java.time.LocalDateTime lastModificationDate) {
    this.lastModificationDateProperty().set(lastModificationDate);
  }


  public final void setModificationDate(final Date modificationDate) {
    this.creationDateProperty().set(modificationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
  }


  public final ObjectProperty<LocalDateTime> creationDateProperty() {
    return this.creationDate;
  }


  public final java.time.LocalDateTime getCreationDate() {
    return this.creationDateProperty().get();
  }


  public final void setCreationDate(final java.time.LocalDateTime creationDate) {
    this.creationDateProperty().set(creationDate);
  }


  public final void setCreationDate(final Date creationDate) {
    this.creationDateProperty().set(creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
  }


  public final SimpleStringProperty creationProperty() {
    return this.creation;
  }


  public final java.lang.String getCreation() {
    return this.creationProperty().get();
  }


  public final void setCreation(final java.lang.String creation) {
    this.creationProperty().set(creation);
  }


  /**
   * @return
   */
  public String toJson() {
    if (entry == null) {
      return null;
    }

    return JsonUtils.toString(entry.get());
  }
}
