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

package io.github.jsoagger.jfxcore.platform.components;




import javax.management.Notification;

import io.github.jsoagger.jfxcore.engine.websocket.WSMessageHandler;

/**
 * Unmarshall json message from server to {@link Notification} and push it to
 * {@link EventBusWrapper}.
 *
 * @author Administrator
 *
 */
public class WSNotificationMessageHandler implements WSMessageHandler {

  private static final String CONTENT = "content";
  private static final String SUBJECT2 = "subject";
  private static final String CREATION_DATE = "creationDate";
  private static final String STATUS = "status";
  private static final String OWNER = "owner";
  private static final String DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy hh:mm:ss";


  /**
   * Constructor
   */
  public WSNotificationMessageHandler() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void handleMessage(String message) {
    // final NotificationVO notif = unmarsahll(message);
    // Services.getCtx().publishEvent(new
    // NewNotificationEvent(notif));
  }

  // private NotificationVO unmarsahll(String message) {
  /*
   * final SimpleDateFormat df = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS); final JsonObject json =
   * Json.createReader(new StringReader(message)).readObject();
   *
   * final NotificationVO notification = new NotificationVO();
   *
   * final int status = json.getInt(STATUS);
   * notification.setStatus(NotificationVOStatus.valueOf(String.valueOf( status)));
   *
   * final String date = json.getString(CREATION_DATE); Date creationDate;
   *
   * try { creationDate = df.parse(date);
   * notification.getPersistenceInfo().setCreateDate(creationDate); } catch (final ParseException e) {
   * final Date dd = Date.from(LocalDate.of(1985, 12,
   * 12).atStartOfDay(ZoneId.systemDefault()).toInstant());
   * notification.getPersistenceInfo().setCreateDate(dd); }
   *
   * final String subject = json.getString(SUBJECT2); notification.setSubject(subject);
   *
   * notification.setContent(json.getString(CONTENT)); return notification;
   */
  // return null;
  // }
}
