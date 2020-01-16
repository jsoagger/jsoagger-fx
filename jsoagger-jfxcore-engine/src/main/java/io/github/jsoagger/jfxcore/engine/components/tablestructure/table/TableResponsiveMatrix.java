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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.table;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @formatter:off <value>0:830#-1</value> <value>0:830#-1:0</value>
 * @formatter:on
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TableResponsiveMatrix {

  public static String TOKEN_SEP = "#";
  public static String VALUE_SEP = ":";

  public List<TableResponsiveOrder> resizes = new ArrayList<>();


  /**
   * Constructor
   */
  public TableResponsiveMatrix(List<String> values) {
    for (String token : values) {
      TableResponsiveOrder responsiveAreaSize = new TableResponsiveOrder();
      resizes.add(responsiveAreaSize);

      String fourchette = token.split(TOKEN_SEP)[0];
      String columnsOrder = token.split(TOKEN_SEP)[1];

      String[] fourchettes = fourchette.split(VALUE_SEP, -1);
      String[] columnsOrders = columnsOrder.split(VALUE_SEP, -1);

      if (fourchettes.length == 1) {
        responsiveAreaSize.minValue = Double.valueOf(fourchettes[0]);
      }

      // in this case, has provided min max
      else if (fourchettes.length == 2) {
        responsiveAreaSize.minValue = Double.valueOf(fourchettes[0]);
        responsiveAreaSize.maxValue = Double.valueOf(fourchettes[1]);
      }

      responsiveAreaSize.orders.addAll(Arrays.asList(columnsOrders));
    }
  }


  public TableResponsiveOrder getOrdersOf(double parentWidth) {
    TableResponsiveOrder result = null;
    for (TableResponsiveOrder order : resizes) {
      if ((parentWidth >= order.minValue) && (parentWidth < order.maxValue)) {
        result = order;
        break;
      }
    }

    return result;
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public static class TableResponsiveOrder {

    private double minValue = -1L;
    private double maxValue = Double.MAX_VALUE;

    private List<String> orders = new ArrayList<>();


    /**
     * Constructor
     */
    public TableResponsiveOrder() {}


    /**
     * Getter of minValue
     *
     * @return the minValue
     */
    public double getMinValue() {
      return minValue;
    }


    /**
     * Setter of minValue
     *
     * @param minValue the minValue to set
     */
    public void setMinValue(double minValue) {
      this.minValue = minValue;
    }


    /**
     * Getter of maxValue
     *
     * @return the maxValue
     */
    public double getMaxValue() {
      return maxValue;
    }


    /**
     * Setter of maxValue
     *
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(double maxValue) {
      this.maxValue = maxValue;
    }


    /**
     * Getter of orders
     *
     * @return the orders
     */
    public List<String> getOrders() {
      return orders;
    }


    /**
     * Setter of orders
     *
     * @param orders the orders to set
     */
    public void setOrders(List<String> orders) {
      this.orders = orders;
    }


    /**
     * @{inheritedDoc}
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      long temp;
      temp = Double.doubleToLongBits(maxValue);
      result = (prime * result) + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(minValue);
      result = (prime * result) + (int) (temp ^ (temp >>> 32));
      return result;
    }


    /**
     * @{inheritedDoc}
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      TableResponsiveOrder other = (TableResponsiveOrder) obj;
      if (Double.doubleToLongBits(maxValue) != Double.doubleToLongBits(other.maxValue)) {
        return false;
      }
      if (Double.doubleToLongBits(minValue) != Double.doubleToLongBits(other.minValue)) {
        return false;
      }
      return true;
    }
  }
}
