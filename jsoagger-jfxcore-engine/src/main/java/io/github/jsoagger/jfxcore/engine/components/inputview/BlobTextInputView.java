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

package io.github.jsoagger.jfxcore.engine.components.inputview;




import java.io.File;
import java.io.IOException;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAttachmentsListLoader;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

/**
 * If the root form have attachments, use this viewer to list these attachments.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class BlobTextInputView extends AbstractViewInputComponent {

  private VBox container = new VBox();
  private VLViewComponentXML configuration;
  private IAttachmentsListLoader loader;
  private AbstractViewController controller;


  /**
   * {@inheritDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    container.setStyle("-fx-alignment:CENTER_LEFT;");

    final String attachmentLoader = configuration.getPropertyValue(XMLConstants.ATTACHMENT_LOADER);
    if (attachmentLoader != null) {
      loader = (IAttachmentsListLoader) Services.getBean(attachmentLoader);
      final List<String> attachmentsName = loader.getAttachmentsName(controller.getModel());
      for (final String attachment : attachmentsName) {
        final AttachmentViewer attachmentViewer = new AttachmentViewer(controller, attachment, loader);
        container.getChildren().add(attachmentViewer);
        NodeHelper.setHgrow(attachmentViewer);
      }
    }

    NodeHelper.setHVGrow(container);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return container;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }

  private static class AttachmentViewer extends VBox {

    private final HBox firstRow = new HBox();
    private final Hyperlink view = new Hyperlink();
    private final Hyperlink download = new Hyperlink();
    private final IAttachmentsListLoader iAttachmentsListLoader;
    private final String attachmentName;
    private final AbstractViewController controller;


    /**
     * Constructor
     */
    public AttachmentViewer(AbstractViewController controller, String attachmentName, IAttachmentsListLoader iAttachmentsListLoader) {

      this.controller = controller;
      this.iAttachmentsListLoader = iAttachmentsListLoader;
      this.attachmentName = attachmentName;

      getChildren().add(firstRow);
      NodeHelper.setHgrow(firstRow);
      final Label label = new Label(attachmentName);

      // IconUtils.iconify16Container24(download,
      // FontAwesomeIcon.DOWNLOAD.toString());
      download.setOnAction(e -> {
        downloadAttachment();
      });

      // IconUtils.iconify16Container24(view,
      // FontAwesomeIcon.EYE.toString());
      view.setOnAction(e -> {
      });

      firstRow.getChildren().addAll(label, NodeHelper.horizontalSpacer(), download, view);
      firstRow.setStyle("-fx-alignment:CENTER_LEFT;" + "-fx-spacing: 8;");

      setStyle("-fx-spacing: 16;");
    }


    private void downloadAttachment() {

      try {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File selectedDirectory = directoryChooser.showDialog(download.getScene().getWindow());

        if (selectedDirectory != null) {
          final String content = iAttachmentsListLoader.getAttachment(controller.getModel(), attachmentName);
          final String fileName = selectedDirectory + File.separator + attachmentName;
          final String suffix = io.github.jsoagger.core.utils.StringUtils.substringBeforeLast(fileName, ".");
          final String prefix = StringUtils.substringAfterLast(fileName, ".");
          final String format = suffix + "_%d." + prefix;

          File file = new File(fileName);
          if (file.exists()) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
              file = new File(String.format(format, i));
              if (!file.exists()) {
                break;
              }
            }
          }

          // FileUtils.writeByteArrayToFile(file,
          // content.getBytes("UTF-8"));
          // final LocalNotification notification = new
          // LocalNotification()
          // ._setTitle(controller.getLocalised("CONTENT_DOWNLOADED_LABEL"))
          // ._setMessage(controller.getLocalised("CONTENT_DOWNLOADED_MESSAGE",
          // file.getCanonicalPath()))
          // ._setType(LocalLevel.INFO);
          // final NewNotificationEvent e = new
          // NewNotificationEvent(notification);
          // controller.publishEvent(e);
        }
      } catch (final IOException e1) {
        // final LocalNotification notification = new
        // LocalNotification()
        // ._setTitle(controller.getLocalised("CONTENT_DOWNLOADED_ERROR_LABEL"))
        // ._setMessage(controller.getLocalised("CONTENT_DOWNLOADED_ERROR_MESSAGE"))
        // ._setType(LocalLevel.INFO);
        // final NewNotificationEvent e = new
        // NewNotificationEvent(notification);
        // controller.publishEvent(e);

        e1.printStackTrace();
      }
    }
  }
}
