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

package io.github.jsoagger.jfxcore.components.search.controller;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.components.annotation.InjectComponent;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.search.SearchFormLayoutContentManager;
import io.github.jsoagger.jfxcore.components.search.comps.SearchInputComponent;
import io.github.jsoagger.jfxcore.components.search.controller.SearchController.SearchControllerWrapper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormBlocTitlePane;
import io.github.jsoagger.jfxcore.engine.components.input.InputCombobox;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A search form. Layout manger should be {@link SearchFormLayoutContentManager}.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class SearchRootFormController extends StandardViewController {

  private JsonObject form;
  private final VBox layout = new VBox();

  protected VLViewComponentXML searchFormConfig;

  protected List<IFormBlocContent> blocContents = new ArrayList<>();
  protected List<FormBlocTitlePane> rootBlocTitlePanes = new ArrayList<>();
  protected List<IBuildable> actions;

  @InjectComponent(id = "SearchMasterAttribute")
  protected SearchInputComponent searchMasterAttribute;

  @InjectComponent(id = "SearchTypeAttribute")
  protected InputCombobox searchTypeAttribute;

  private final SimpleObjectProperty<SearchController> searchController = new SimpleObjectProperty<>();

  /**
   * Default Constructor
   */
  public SearchRootFormController() {
    super();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    searchFormConfig = getRootComponent().getComponentById("SearchForm").orElse(null);

    if (searchFormConfig != null && searchFormConfig.hasSubComponent()) {
      layout.getStyleClass().add("ep-search-form-root-layout");
      NodeHelper.styleClassAddAll(layout, searchFormConfig, "rootLayoutStyleClass", "ep-search-form-root-layout");

      final Iterator<VLViewComponentXML> iterator = searchFormConfig.getSubcomponents().iterator();
      while (iterator.hasNext()) {
        final VLViewComponentXML next = iterator.next();
        final FormBlocTitlePane blocContent = new FormBlocTitlePane(next, this);
        blocContents.add(blocContent.getBlocContent());
        rootBlocTitlePanes.add(blocContent);
        NodeHelper.setHgrow(blocContent);

        layout.getChildren().add(blocContent);
        if (!iterator.hasNext()) {
          blocContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
        }
      }
    }

    buildActions();
    //processedView(new StackPane());
  }

  protected void buildActions() {
    final VLViewComponentXML searchActionsConfig = getRootComponent().getComponentById("SearchFormActions").orElse(null);
    if (searchActionsConfig != null && searchActionsConfig.hasSubComponent()) {
      actions = ComponentUtils.resolveAndGenerate(this, searchActionsConfig.getSubcomponents());
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean beginForwardEdition(IFormRowEditor simpleForwardEditor) {
    super.beginForwardEdition(simpleForwardEditor);
    layoutManager.pushContent(simpleForwardEditor.getDisplay());
    return true;
  }

  @Override
  public boolean endForwardEdition() {
    super.endForwardEdition();
    layoutManager.popContent();
    return true;
  }

  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return layout;
    }

    return super.getNodeOnPosition(position);
  }

  protected void commitForm() {
    form = new JsonObject();
    for (final IFormBlocContent blocContent : blocContents) {
      for(IFormFieldsetRow row: blocContent.getRows()) {
        for(IInputComponentWrapper e: row.getEntries()) {
          final String attr = e.getAttributeName();
          final String attrval = e.getCurrentInternalValue();
          if (StringUtils.isNotBlank(attrval)) {
            form.addProperty(attr, attrval);
          }
        }
      }
    }
  }

  public JsonObject getForm(boolean commit) {
    if (commit) {
      commitForm();
    }
    return form;
  }

  public void resetForm() {
    form = new JsonObject();
    for (final IFormBlocContent blocContent : blocContents) {
      for(IFormFieldsetRow row: blocContent.getRows()) {
        for(IInputComponentWrapper e: row.getEntries()) {
          e.cancelModification();
        }
      }
    }
  }

  /**
   * Lays out the content of the form from this wrapper to current root form.
   *
   * @param current
   */
  public void setAdditionalForm(SearchControllerWrapper current) {
    Platform.runLater(()-> {
      layout.getChildren().clear();
      for(FormBlocTitlePane e : rootBlocTitlePanes) {
        layout.getChildren().add(e);
      }
      layout.getChildren().add(current.getSearchFormController().processedView());
    });
  }

  public List<IBuildable> getActions() {
    return actions;
  }

  public SearchInputComponent getSearchMasterAttribute() {
    return searchMasterAttribute;
  }

  public InputCombobox getSearchTypeAttribute() {
    return searchTypeAttribute;
  }

  public SearchController getSearchController() {
    return searchController.get();
  }

  public void setSearchController(SearchController searchController) {
    this.searchController.set(searchController);
  }

  public SimpleObjectProperty<SearchController> searchControllerProperty() {
    return searchController;
  }

  /**
   * Transfert dosearch to {@link SearchController}
   */
  public void doSearch() {
    if (getSearchController() != null) {
      getSearchController().doSearch();
    }
  }
}
