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

package io.github.jsoagger.jfxcore.engine.components.notification;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.INotification;
import io.github.jsoagger.jfxcore.api.NotificationLevel;
import io.github.jsoagger.jfxcore.api.NotificationStatus;
import io.github.jsoagger.jfxcore.api.NotificationType;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class Notification implements Serializable, INotification {

  private static final long serialVersionUID = -7465865760727116777L;

  /** If remote, have data associated, avalaible only on remote */
  private OperationData operationData;
  private Object subject;

  private String uuid;
  private String title;
  private String message;
  private String titleKey;
  private String messageKey;
  private Date creationDate = new Date();
  private String subjectFullId;
  private String contentType;

  private NotificationType type = NotificationType.LOCAL;
  private NotificationLevel level = NotificationLevel.INFO;
  private NotificationStatus status = NotificationStatus.NEW;


  /**
   * Constructor
   */
  public Notification() {}


  /**
   * Getter of subject
   *
   * @return the subject
   */
  public Object getSubject() {
    return subject;
  }


  /**
   * Setter of subject
   *
   * @param subject the subject to set
   */
  public void setSubject(Object subject) {
    this.subject = subject;
  }


  /**
   * Getter of title
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }


  /**
   * Setter of title
   *
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   * Getter of message
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }


  /**
   * Setter of message
   *
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }


  /**
   * Getter of type
   *
   * @return the type
   */
  @Override
  public NotificationType getType() {
    return type;
  }


  /**
   * Setter of type
   *
   * @param type the type to set
   */
  public void setType(NotificationType type) {
    this.type = type;
  }


  /**
   * Getter of level
   *
   * @return the level
   */
  public NotificationLevel getLevel() {
    return level;
  }


  /**
   * Setter of level
   *
   * @param level the level to set
   */
  public void setLevel(NotificationLevel level) {
    this.level = level;
  }


  /**
   * Getter of status
   *
   * @return the status
   */
  public NotificationStatus getStatus() {
    return status;
  }


  /**
   * Setter of status
   *
   * @param status the status to set
   */
  public void setStatus(NotificationStatus status) {
    this.status = status;
  }


  /**
   * Getter of uuid
   *
   * @return the uuid
   */
  @Override
  public String getUuid() {
    return uuid;
  }


  /**
   * Setter of uuid
   *
   * @param uuid the uuid to set
   */
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public static class Builder {

    private OperationData data;
    private Object subject;
    private String subjectFullId;
    private String uuid = "";
    private String title = "";
    private String message = "";
    private String titleKey = "";
    private String messageKey = "";
    private String contentType = "";
    private Date creationDate = new Date();
    private NotificationType type = NotificationType.LOCAL;
    private NotificationLevel level = NotificationLevel.INFO;
    private NotificationStatus status = NotificationStatus.NEW;


    public Builder operationData(OperationData data) {
      this.data = data;

      Map attributes = data.getAttributes();

      uuid = (String) attributes.get("uuid");
      title = (String) attributes.get("subject");

      ArrayList t = (ArrayList) attributes.get("content");
      byte[] bytes = new byte[t.size()];
      for (int i = 0; i < t.size(); i++) {
        bytes[i] = ((Integer) t.get(i)).byteValue();
      }

      message = new String(bytes);
      titleKey = (String) attributes.get("titleKey");
      titleKey = (String) attributes.get("titleKey");
      subjectFullId = (String) attributes.get("fullId");
      contentType = (String) attributes.get("fullId");
      try {
        creationDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse((String) attributes.get("lastModifiedDate"));
      } catch (ParseException e) {
        e.printStackTrace();
      }

      type = NotificationType.REMOTE;
      level = NotificationLevel.fromString((String) attributes.get("level"));
      status = NotificationStatus.fromString((String) attributes.get("status"));

      return this;
    }


    public Builder subject(Object subject) {
      this.subject = subject;
      return this;
    }


    public Builder uuid(String uuid) {
      this.uuid = uuid;
      return this;
    }


    public Builder title(String title) {
      this.title = title;
      return this;
    }


    public Builder message(String message) {
      this.message = message;
      return this;
    }


    public Builder titleKey(String titleKey) {
      this.titleKey = titleKey;
      return this;
    }


    public Builder messageKey(String messageKey) {
      this.messageKey = messageKey;
      return this;
    }


    public Builder creationDate(Date creationDate) {
      this.creationDate = creationDate;
      return this;
    }


    public Builder type(NotificationType type) {
      this.type = type;
      return this;
    }


    public Builder level(NotificationLevel level) {
      this.level = level;
      return this;
    }


    public Builder status(NotificationStatus status) {
      this.status = status;
      return this;
    }


    public Notification build() {
      return new Notification(this);
    }


    /**
     * @return the contentType
     */
    public String getContentType() {
      return contentType;
    }


    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
      this.contentType = contentType;
    }
  }


  private Notification(Builder builder) {
    operationData = builder.data;
    subject = builder.subject;
    uuid = builder.uuid;
    title = builder.title;
    message = builder.message;
    titleKey = builder.titleKey;
    messageKey = builder.messageKey;
    creationDate = builder.creationDate;
    type = builder.type;
    level = builder.level;
    status = builder.status;
    subjectFullId = builder.subjectFullId;
    contentType = builder.contentType;
  }


  /**
   * Getter of creationDate
   *
   * @return the creationDate
   */
  @Override
  public Date getCreationDate() {
    return creationDate;
  }


  /**
   * Setter of creationDate
   *
   * @param creationDate the creationDate to set
   */
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }


  /**
   * Getter of subjectFullId
   *
   * @return the subjectFullId
   */
  @Override
  public String getSubjectFullId() {
    return subjectFullId;
  }


  /**
   * Setter of subjectFullId
   *
   * @param subjectFullId the subjectFullId to set
   */
  public void setSubjectFullId(String subjectFullId) {
    this.subjectFullId = subjectFullId;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (creationDate == null ? 0 : creationDate.hashCode());
    result = prime * result + (uuid == null ? 0 : uuid.hashCode());
    return result;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Notification other = (Notification) obj;
    if (creationDate == null) {
      if (other.creationDate != null)
        return false;
    } else if (!creationDate.equals(other.creationDate))
      return false;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    return true;
  }


  /**
   * Getter of operationData
   *
   * @return the operationData
   */
  public OperationData getOperationData() {
    return operationData;
  }


  /**
   * Setter of operationData
   *
   * @param operationData the operationData to set
   */
  public void setOperationData(OperationData operationData) {
    this.operationData = operationData;
  }


  /**
   * Getter of titleKey
   *
   * @return the titleKey
   */
  public String getTitleKey() {
    return titleKey;
  }


  /**
   * Setter of titleKey
   *
   * @param titleKey the titleKey to set
   */
  public void setTitleKey(String titleKey) {
    this.titleKey = titleKey;
  }


  /**
   * Getter of messageKey
   *
   * @return the messageKey
   */
  public String getMessageKey() {
    return messageKey;
  }


  /**
   * Setter of messageKey
   *
   * @param messageKey the messageKey to set
   */
  public void setMessageKey(String messageKey) {
    this.messageKey = messageKey;
  }


  /**
   * @return the contentType
   */
  public String getContentType() {
    return contentType;
  }


  /**
   * @param contentType the contentType to set
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }
}
