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

package io.github.jsoagger.jfxcore.engine.components.browser;


import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.list.utils.FixedSizeListView;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class BrowserTitlePaneBlocContent extends StackPane implements IFormBlocContent {

  protected VLViewComponentXML blocConfig;
  protected AbstractViewController controller;

  /** The content containing each item */
  private FixedSizeListView listView = new FixedSizeListView();


  /**
   * Constructor
   */
  public BrowserTitlePaneBlocContent() {
    managedProperty().bindBidirectional(visibleProperty());
  }


  @Override
  public void build(VLViewComponentXML blocConfig, IJSoaggerController controller) {
    this.blocConfig = blocConfig;
    this.controller = (AbstractViewController) controller;

    final String styleClass = blocConfig.getPropertyValue("contentStyleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      getStyleClass().setAll(styleClass.split(","));
    }

    String listViewId = blocConfig.getPropertyValue("listViewId");
    if (StringUtils.isNotBlank(listViewId)) {
      listView.setId(listViewId);
    } else {
      listView.setId("browser-listview");
    }

    listView.setCellFactory(param -> {
      // final SimpleListFormCell cell = new SimpleListFormCell();
      // cell.setConfiguration(blocConfig);
      // cell.setController(controller);
      return null;
    });

    List<VLViewComponentXML> content = blocConfig.getSubcomponents();
    Iterator<VLViewComponentXML> iterator = content.iterator();

    while (iterator.hasNext()) {
      VLViewComponentXML rowConfig = iterator.next();

      final String presenter = rowConfig.getPropertyValue("presenter");
      final String dataLoader = rowConfig.getPropertyValue("dataLoader");

      final IListFormCellPresenter node = (IListFormCellPresenter) Services.getBean(presenter);
      node.buildFrom(controller, rowConfig);

      if (StringUtils.isNotBlank(dataLoader)) {
        IListFormDataLoader loader = (IListFormDataLoader) Services.getBean(dataLoader);
        node.setDataLoader(loader);
      }
      // else {
      // loader = (IListFormDataLoader)
      // Services.getBean("ListFormDataLoader");
      // }

      listView.getItems().add(node);

      if (!iterator.hasNext()) {
        node.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
      }
    }

    getChildren().add(listView);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IFormFieldsetRow> getRows() {
    return null;
  }
}
