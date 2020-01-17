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
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.search.SearchFormLayoutContentManager;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormBlocTitlePane;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A search form. Layout manager should be {@link SearchFormLayoutContentManager}.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class SearchFormController extends StandardViewController {

  private JsonObject form;
  private final VBox layout = new VBox();
  private final List<IFormBlocContent> contents = new ArrayList<>();
  private SearchRootFormController rootFormController;

  /**
   * Default Constructor
   */
  public SearchFormController() {
    super();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
    final VLViewComponentXML searchFormConfig = getRootComponent().getComponentById("SearchForm").orElse(null);
    if (searchFormConfig != null && searchFormConfig.hasSubComponent()) {

      NodeHelper.styleClassAddAll(layout, searchFormConfig, "styleClass", "ep-search-form-child-layout");

      final Iterator<VLViewComponentXML> iterator = searchFormConfig.getSubcomponents().iterator();
      while (iterator.hasNext()) {
        final VLViewComponentXML next = iterator.next();
        final FormBlocTitlePane blocContent = new FormBlocTitlePane(next, this);
        NodeHelper.setHgrow(blocContent);

        layout.getChildren().add(blocContent);
        contents.add(blocContent.getBlocContent());

        if (!iterator.hasNext()) {
          blocContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
        }
      }
    }

    NodeHelper.setVgrow(layout);
    processedView(layout);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean beginForwardEdition(IFormRowEditor simpleForwardEditor) {
    super.beginForwardEdition(simpleForwardEditor);
    rootFormController.getLayoutManager().pushContent(simpleForwardEditor.getDisplay());
    return true;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean endForwardEdition() {
    super.endForwardEdition();
    rootFormController.getLayoutManager().popContent();
    return true;
  }

  public void doSearch() {
    if (rootFormController != null) {
      rootFormController.doSearch();
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return layout;
    }

    return super.getNodeOnPosition(position);
  }

  public void commitForm() {
    form = new JsonObject();
    for (final IFormBlocContent blocContent : contents) {
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
    for (final IFormBlocContent blocContent : contents) {
      for(IFormFieldsetRow row: blocContent.getRows()) {
        for(IInputComponentWrapper e: row.getEntries()) {
          e.cancelModification();
        }
      }
    }
  }

  public void setRootFormController(SearchRootFormController rootFormController) {
    this.rootFormController = rootFormController;
  }

  public SearchRootFormController getSearchRootFormController() {
    return rootFormController;
  }
}
