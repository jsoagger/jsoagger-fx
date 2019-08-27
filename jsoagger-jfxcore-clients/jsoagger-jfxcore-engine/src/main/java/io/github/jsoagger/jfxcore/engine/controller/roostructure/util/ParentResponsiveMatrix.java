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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.util;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;

/**
 * @formatter:off <value>0:830#0:1:0#:minimize:hide</value>
 *                <value>800:900#0.05:0.90:0.05#:show:hide</value>
 *                <value>900:1000#0.10:0.80:0.10#:minimize:hide</value>
 *                <value>1000:1200#0.15:0.70:0.15#:minimize:hide</value>
 *                <value>1000:1200#0.15:0.70:0.15#:minimize:hide</value>
 *                <value>1200:1600#0.20:0.60:0.20#:show:minimize</value>
 *
 *                <value>1600#0.50:fixed|200:0.50#:minimize:show</value> -> Fixed size of center
 *                area [200] -> right: 50% of parent - 200 -> left: 50% of parent - 200
 * @formatter:on
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ParentResponsiveMatrix implements IParentResponsiveMatrix {

  public static String TOKEN_SEP = "#";
  public static String VALUE_SEP = ":";
  public static String MINIMIZE_ = "minimize";
  public static String HIDE = "hide";
  public static String SHOW = "show";

  public List<IResponsiveAreaSize> resizes = new ArrayList<>();


  /**
   * Constructor
   */
  public ParentResponsiveMatrix(List<String> values) {
    for (String token : values) {
      IResponsiveAreaSize responsiveAreaSize = new ResponsiveAreaSize();
      resizes.add(responsiveAreaSize);

      String fourchette = token.split(TOKEN_SEP)[0];
      String sizesPercent = token.split(TOKEN_SEP)[1];
      String visibility = token.split(TOKEN_SEP)[2];

      String[] fourchettes = fourchette.split(VALUE_SEP, -1);
      String[] sizesPercents = sizesPercent.split(VALUE_SEP, -1);
      String[] visibilities = visibility.split(VALUE_SEP, -1);

      // length of these parts should be the same
      if (sizesPercents.length == visibilities.length) {

        // in this case, no max value, only min provided
        if (fourchettes.length == 1) {
          responsiveAreaSize.setMinValue(Double.valueOf(fourchettes[0]));
        }

        // in this case, has provided min max
        else if (fourchettes.length == 2) {
          responsiveAreaSize.setMinValue(Double.valueOf(fourchettes[0]));
          responsiveAreaSize.setMaxValue(Double.valueOf(fourchettes[1]));
        }

        int colNbr = sizesPercents.length;
        for (int i = 0; i < colNbr; i++) {
          IResponsiveSizing sizing = new ResponsiveSizing();

          // Fixed size format is "fixed|200"
          if (sizesPercents[i].startsWith("fixed|")) {
            sizing.setWidthPercent(0);
            sizing.setFixedWidth(Double.valueOf(StringUtils.substringAfter(sizesPercents[i], "fixed|")));
            responsiveAreaSize.setFixedAreaSize(sizing.getFixedWidth());
          } else {
            sizing.setFixedWidth(0);
            // responsiveAreaSize.fixedAreaSize = 0;
            sizing.setWidthPercent(Double.valueOf(sizesPercents[i]));
          }

          sizing.setHide(Boolean.valueOf(visibilities[i].equalsIgnoreCase(HIDE)));
          sizing.setMinimize(!sizing.isHide() && Boolean.valueOf(visibilities[i].equalsIgnoreCase(MINIMIZE_)));

          responsiveAreaSize.getSizes().add(sizing);
        }
      }

      else {
        throw new RuntimeException("Invalid Matrix provided");
      }
    }
  }


  /**
   * @param sizesPercents
   * @return
   */
  private boolean hasFixWidth(String[] sizesPercents) {
    return Stream.of(sizesPercents).anyMatch(size -> size.startsWith("fixed"));
  }


  @Override
  public IResponsiveAreaSize getSizeOf(double parentWidth) {
    IResponsiveAreaSize result = null;
    for (IResponsiveAreaSize areaSize : resizes) {
      if ((parentWidth >= areaSize.getMinValue()) && (parentWidth < areaSize.getMaxValue())) {
        result = areaSize;
        result.of(parentWidth - areaSize.getFixedAreaSize());
        break;
      }
    }

    return result;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ParentResponsiveMatrix [");
    if (resizes != null) {
      builder.append("resizes=");
      builder.append(resizes);
    }
    builder.append("]");
    return builder.toString();
  }
}
