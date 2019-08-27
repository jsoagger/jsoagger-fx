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



/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class MimeTyPeUtils {

  public static String getFontIcon(String mimeType, String iconSize) {
    if (mimeType.startsWith("video")) {
      return "hws-document-file-mov:" + iconSize;
    } else if (mimeType.startsWith("image")) {
      return "hws-document-file-" + mimeType.split("/")[1] + ":" + iconSize;
    } else if (mimeType.equals("text/plain")) {
      return "hws-document-file-txt:" + iconSize;
    } else if (mimeType.equals("text/html")) {
      return "hws-document-file-html:" + iconSize;
    } else if (mimeType.equals("text/xml")) {
      return "hws-document-file-xml:" + iconSize;
    } else if (mimeType.equals("audio/basic")) {
      return "hws-document-file-wav:" + iconSize;
    } else if (mimeType.equals("audio/mid")) {
      return "hws-document-file-mid:" + iconSize;
    } else if (mimeType.equals("audio/wav")) {
      return "hws-document-file-wav:" + iconSize;
    } else if (mimeType.startsWith("application")) {
      if (mimeType.contains("word")) {
        return "hws-document-file-docx:" + iconSize;
      } else if (mimeType.contains("excel")) {
        return "hws-document-file-xls:" + iconSize;
      } else if (mimeType.contains("ppt")) {
        return "hws-document-file-ppt:" + iconSize;
      } else if (mimeType.contains("zip")) {
        return "hws-document-file-zip:" + iconSize;
      } else {
        return "hws-document-file-" + mimeType.split("/")[1] + ":" + iconSize;
      }
    }

    return "hws-document:" + iconSize;
  }


  public static String getFontIcon(String mimeType) {
    return getFontIcon(mimeType, "32");
  }
}
