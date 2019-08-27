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

package io.github.jsoagger.jfxcore.engine.components.converter;




import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.github.jsoagger.core.utils.DateUtils;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LocalDateConverter extends StringConverter<LocalDate> {

  /**
   * Constructor
   */
  public LocalDateConverter() {
    super();
  }


  /**
   * Constructor
   *
   * @param displayFormat
   * @param saveFormat
   */
  public LocalDateConverter(String displayFormat, String saveFormat) {
    super();
    this.displayFormat = displayFormat;
    this.saveFormat = saveFormat;
  }


  @Override
  public LocalDate fromString(String string) {
    if (string != null && !string.isEmpty()) {
      try {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(saveFormat);
        LocalDate val = LocalDate.parse(string, format);
        return val;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return null;
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString(LocalDate object) {
    if (object != null) {
      if (object instanceof LocalDate) {
        Date datem = DateUtils.asDate(object);
        return DateUtils.dateString(datem, saveFormat);
      }
    }
    return "-";
  }
}
