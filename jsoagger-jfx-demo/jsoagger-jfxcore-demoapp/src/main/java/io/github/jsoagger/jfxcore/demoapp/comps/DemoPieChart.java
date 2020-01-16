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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoPieChart implements IBuildable {

  PieChart chart = null;


  @Override
  public Node getDisplay() {
    return chart;
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    // @formatter:off
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Critical", 13), new PieChart.Data("High", 25), new PieChart.Data("Blocking", 10),
        new PieChart.Data("Medium", 22), new PieChart.Data("Low", 30));
    // @formatter:on

    chart = new PieChart(pieChartData);
    chart.setTitle(configuration.getPropertyValue("title").toUpperCase());
  }
}
