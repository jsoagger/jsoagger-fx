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

package io.github.jsoagger.jfxcore.engine.components.form;



import java.io.IOException;
import java.util.Iterator;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IAttachmentsListLoader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.bloc.FormFieldsetBloc;
import io.github.jsoagger.jfxcore.engine.components.input.InputMode;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Administrator
 *
 */
public class FormTextBlobBloc extends FormFieldsetBloc {

  /**
   * Constructor
   */
  public FormTextBlobBloc() {}


  /**
   * @param leftBlocCfg
   * @param controller
   */
  @Override
  public void buildFrom(VLViewComponentXML blocConfig, AbstractViewController controller) {
    if (blocConfig.propertyValueOf(XMLConstants.TITLE).isPresent()) {
      final Node titleBloc = buildBloctitle(blocConfig, controller);
      container.getChildren().add(titleBloc);
    }

    final VBox rowsWrapper = new VBox();
    container.getChildren().add(rowsWrapper);
    NodeHelper.setHVGrow(rowsWrapper);
    rowsWrapper.setStyle("-fx-padding:16 0 0 16;" + "-fx-spacing: 4;");

    Iterator<VLViewComponentXML> iterator = blocConfig.getSubcomponents().iterator();
    while (iterator.hasNext()) {
      VLViewComponentXML rowConfig = iterator.next();

      final String modeString = blocConfig.getPropertyValue(XMLConstants.MODE);
      final FormFieldsetRow row = new FormFieldsetRow(controller, blocConfig, rowConfig, InputMode.from(modeString), !iterator.hasNext());
      rowsWrapper.getChildren().add(row);
      rows.add(row);

      NodeHelper.setHgrow(row);
    }

    final VLViewComponentXML attributeConfig = rows.get(0).getEntries().get(0).configuration();

    // The text blob container
    final StackPane blobContainer = new StackPane();
    blobContainer.setStyle("-fx-border-width: 1 1 1 4;" + "-fx-padding: 16;" + "-fx-alignment:CENTER_LEFT;" + "-fx-border-color: -grey-color-200 -grey-color-200 -grey-color-200 -accent-color;"
        + "-fx-background-color:#f1f1f1;");
    container.getChildren().add(blobContainer);

    final String attachmentLoaderClass = attributeConfig.getPropertyValue(XMLConstants.ATTACHMENT_LOADER);
    if (attachmentLoaderClass != null) {
      try {
        final IAttachmentsListLoader loader = (IAttachmentsListLoader) ReflectionUIUtils.newInstance(attachmentLoaderClass);
        final String content = loader.getPrimaryAttachment(controller.getModel());
        final Label text = new Label(content);
        blobContainer.getChildren().add(text);
      } catch (final IOException e) {
        e.printStackTrace();
      }
    }

    setCenter(container);
  }
}
