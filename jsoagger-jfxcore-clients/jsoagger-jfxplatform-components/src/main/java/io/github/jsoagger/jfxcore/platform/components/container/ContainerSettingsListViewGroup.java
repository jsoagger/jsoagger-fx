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

package io.github.jsoagger.jfxcore.platform.components.container;




import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.list.utils.FixedSizeListView;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * {@link ContainerSettingsListViewGroup} is a group that contains a {@link FixedSizeListView} and a
 * title label and description.
 * <p>
 * Each group may have a title.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ContainerSettingsListViewGroup extends VBox implements IBuildable {

  /** The title of the group */
  private Label title = new Label();

  /** The content containing each item */
  private FixedSizeListView listView = new FixedSizeListView();
  private TextFlow description = new TextFlow();

  /** Configuration */
  private AbstractViewController controller;
  private VLViewComponentXML configuration;


  /**
   * Constructor
   */
  public ContainerSettingsListViewGroup() {
    super();
    setSpacing(16);
    getChildren().addAll(title);
    getChildren().addAll(description, listView);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    String listViewStyleClass = configuration.getPropertyValue("listViewStyleClass");
    if (StringUtils.isNotBlank(listViewStyleClass)) {
      listView.getStyleClass().addAll(listViewStyleClass.split(","));
    }

    buildHeader();
    buildListView();
  }


  /**
   * Build the header
   */
  private void buildHeader() {
    NodeHelper.setTitle(title, configuration, controller);
    NodeHelper.styleClassAddAll(title, configuration, "titleStyleClass", "h4");

    title.managedProperty().bind(title.visibleProperty());
    boolean displayTitle = configuration.getBooleanProperty("displayTitle", true);
    title.visibleProperty().set(displayTitle);

    String listViewId = configuration.getPropertyValue("listViewId");
    if (StringUtils.isNotBlank(listViewId)) {
      listView.setId(listViewId);
    }

    // description
    String description = configuration.getPropertyValue("description");
    NodeHelper.setHVGrow(this.description);
    if (StringUtils.isNotBlank(description)) {
      String lb = controller.getLocalised(description);
      Text text = new Text();
      NodeHelper.styleClassAddAll(text, configuration, "descriptionStyleClass", "medium-description");
      text.setText(lb);
      this.description.getChildren().add(text);
    }
  }


  private void buildListView() {
    if (configuration.hasSubComponent()) {

      listView.setCellFactory(param -> {
        // final SimpleListFormCell cell = new SimpleListFormCell();
        // cell.setConfiguration(configuration);
        // cell.setController(controller);
        return null;
      });

      List<VLViewComponentXML> content = configuration.getSubcomponents();
      Iterator<VLViewComponentXML> iterator = content.iterator();

      while (iterator.hasNext()) {

        VLViewComponentXML rowConfig = iterator.next();

        final String presenter = rowConfig.getPropertyValue("presenter");
        final String dataLoader = rowConfig.getPropertyValue("dataLoader");

        final IListFormCellPresenter node = (IListFormCellPresenter) Services.getBean(presenter);
        node.buildFrom((IJSoaggerController) controller, rowConfig);

        if (StringUtils.isNotBlank(dataLoader)) {
          IListFormDataLoader loader = (IListFormDataLoader) Services.getBean(dataLoader);
          node.setDataLoader(loader);
        }

        listView.getItems().add(node);
        if (!iterator.hasNext()) {
          node.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
        }
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
