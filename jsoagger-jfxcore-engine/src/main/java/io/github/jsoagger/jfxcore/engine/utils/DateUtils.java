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

package io.github.jsoagger.jfxcore.engine.utils;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Ramilafananana  VONJISOA
 * 
 * @version $Revision: 1.0 $
 */
public class DateUtils {

  public final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss z";


  /**
   * Format a date to given format, in UTC timezone (GMT +0)
   * 
   * @param date
   * @param format
   * @return String
   */
  public static String UTCDateString(Date date, String format) {

    if (date == null) {
      return "-";
    }

    final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(date);
  }


  /**
   * Format a date to given format according to default configured timezone of JVM or User PC.
   * 
   * @param date
   * @param format
   * @return String
   */
  public static String dateString(Date date, String format) {

    if (date == null) {
      return "-";
    }

    final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setTimeZone(TimeZone.getDefault());
    return dateFormat.format(date);
  }


  /**
   * Format a date to given format, in UTC timezone (GMT +0). Using format : dd/MM/yyyy HH:mm:ss Z
   * 
   * @param date
   * @param format
   * @return String
   */
  public static String UTCDateString(Date date) {

    if (date == null) {
      return "-";
    }

    final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    return dateFormat.format(date);
  }


  /**
   * Format a date to given format according to default configured timezone of JVM or User PC. Using
   * format : dd/MM/yyyy HH:mm:ss Z
   * 
   * @param date
   * @param format
   * @return String
   */
  public static String dateString(Date date) {

    if (date == null) {
      return "-";
    }

    final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    dateFormat.setTimeZone(TimeZone.getDefault());
    return dateFormat.format(date);
  }
}
