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

package io.github.jsoagger.jfxcore.engine.components.input;



import java.io.File;
import java.util.Optional;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.control9.CustomTextField;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputFilePicker extends AbstractInputComponent {

  /*--------------------------------------------------------------------------
  | Private fields
   *============================================================================*/
  private static String SEPARATOR = ";";
  private FileChooser chooser = new FileChooser();
  private final Hyperlink chooseButton = new Hyperlink();
  private final CustomTextField choosenFullPath = new CustomTextField();
  private String filters = "";


  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/

  /**
   * Constructor
   */
  public InputFilePicker() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    this.filters = configuration.propertyValueOf(XMLConstants.FILTERS).orElse("");
    if (!StringUtils.isEmpty(filters)) {
      final String[] split = filters.split(SEPARATOR);
      if (split.length > 0) {
        for (int i = 0; i < split.length; i++) {
          final String filter = split[i];
          final String text = filter.split(",")[0];
          final String key = filter.split(",")[1];
          final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(text, key);
          chooser.getExtensionFilters().add(extFilter);
        }
      }
    }

    // IconUtils.iconify16Container24(chooseButton, "UPLOAD");
    chooseButton.setFocusTraversable(false);
    chooseButton.setOpacity(0.60);
    chooseButton.setOnAction(e -> {
      fileChoosen();
    });

    // prompt
    final Optional<String> prompt = configuration.propertyValueOf(XMLConstants.PROMPT);
    prompt.ifPresent(e -> {
      final String val = controller.getLocalised(prompt.get());
      choosenFullPath.setPromptText(val);
    });

    choosenFullPath.getStyleClass().remove("custom-text-field");
    choosenFullPath.setRight(chooseButton);
    choosenFullPath.setOnMouseClicked(e -> {
      fileChoosen();
    });

    HBox.setHgrow(choosenFullPath, Priority.ALWAYS);
    Bindings.bindBidirectional(choosenFullPath.textProperty(), owner.currentInternalValueProperty(), owner.getConverter());
  }


  private void fileChoosen() {
    final File chosen = chooser.showOpenDialog(null);
    if (chosen != null) {
      choosenFullPath.setText(chosen.getAbsolutePath());
    }
  }


  @Override
  public void addDisplayBinding(Label label) {
    super.addDisplayBinding(label);
    label.textProperty().bind(choosenFullPath.textProperty());
  }


  /**
   * @return
   */
  public FileChooser getChooser() {
    return chooser;
  }


  /**
   * @param chooser
   */
  public void setChooser(FileChooser chooser) {
    this.chooser = chooser;
  }


  /**
   *
   * @return
   */
  public TextField getChoosenFullPath() {
    return choosenFullPath;
  }


  @Override
  public Node getDisplay() {
    return choosenFullPath;
  }


  @Override
  public Node getComponent() {
    return choosenFullPath;
  }
}
