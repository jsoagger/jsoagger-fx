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

package io.github.jsoagger.jfxcore.demoapp.comps;



import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoBarChart implements IBuildable {

  BarChart<String, Number> bc = null;


  @Override
  public Node getDisplay() {
    return bc;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {

    String austria = "Austria";
    String brazil = "Brazil";
    String france = "France";
    String italy = "Italy";
    String usa = "USA";

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();

    bc = new BarChart<>(xAxis, yAxis);
    bc.setTitle("Company world performance".toUpperCase());
    xAxis.setLabel("Country");
    yAxis.setLabel("Value");

    XYChart.Series series1 = new XYChart.Series();
    series1.setName("2016");
    series1.getData().add(new XYChart.Data(austria, 25601.34));
    series1.getData().add(new XYChart.Data(brazil, 20148.82));
    series1.getData().add(new XYChart.Data(france, 10000));
    series1.getData().add(new XYChart.Data(italy, 35407.15));
    series1.getData().add(new XYChart.Data(usa, 12000));

    XYChart.Series series2 = new XYChart.Series();
    series2.setName("2017");
    series2.getData().add(new XYChart.Data(austria, 57401.85));
    series2.getData().add(new XYChart.Data(brazil, 41941.19));
    series2.getData().add(new XYChart.Data(france, 45263.37));
    series2.getData().add(new XYChart.Data(italy, 117320.16));
    series2.getData().add(new XYChart.Data(usa, 14845.27));

    XYChart.Series series3 = new XYChart.Series();
    series3.setName("2018");
    series3.getData().add(new XYChart.Data(austria, 45000.65));
    series3.getData().add(new XYChart.Data(brazil, 44835.76));
    series3.getData().add(new XYChart.Data(france, 18722.18));
    series3.getData().add(new XYChart.Data(italy, 17557.31));
    series3.getData().add(new XYChart.Data(usa, 92633.68));

    bc.getData().addAll(series1, series2, series3);
  }
}
