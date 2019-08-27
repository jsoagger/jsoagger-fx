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

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.components.control9.CustomTextField;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputDirectoryPicker extends AbstractInputComponent {

  /*-----------------------------------------------------------------------------
  | Static fields
   *=============================================================================*/
  private final static String BLACK_ICON = "black-icon";

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  private DirectoryChooser chooser = new DirectoryChooser();
  private final Hyperlink chooseButton = new Hyperlink();
  private final CustomTextField choosenFullPath = new CustomTextField();
  private final SimpleStringProperty file = new SimpleStringProperty();


  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  /**
   * Constructor
   */
  public InputDirectoryPicker() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    final Optional<String> prompt = configuration.propertyValueOf(XMLConstants.PROMPT);
    // IconUtils.iconify16Container24(chooseButton, "UPLOAD");

    chooseButton.getStyleClass().add(BLACK_ICON);
    chooseButton.setFocusTraversable(false);
    chooseButton.setOpacity(0.60);
    chooseButton.setOnAction(e -> {
      final File chosen = chooser.showDialog(null);
      if (chosen != null) {
        file.set(chosen.getAbsolutePath());
        choosenFullPath.setText(chosen.getAbsolutePath());
      }
    });

    // prompt
    prompt.ifPresent(e -> {
      final String val = controller.getLocalised(prompt.get());
      choosenFullPath.setPromptText(val);
    });

    choosenFullPath.getStyleClass().remove("custom-text-field");
    choosenFullPath.setRight(chooseButton);
    choosenFullPath.setOnMouseClicked(e -> {
      final File chosen = chooser.showDialog(null);
      if (chosen != null) {
        file.set(chosen.getAbsolutePath());
        choosenFullPath.setText(chosen.getAbsolutePath());
      }
    });

    Bindings.bindBidirectional(choosenFullPath.textProperty(), owner.currentInternalValueProperty(), owner.getConverter());
  }


  /**
   * @return
   */
  public SimpleStringProperty file() {
    return file;
  }


  /**
   * @return
   */
  public DirectoryChooser getChooser() {
    return chooser;
  }


  /**
   * @param chooser
   */
  public void setChooser(DirectoryChooser chooser) {
    this.chooser = chooser;
  }


  /**
   * @return
   */
  public TextField getChoosenFullPath() {
    return choosenFullPath;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return choosenFullPath;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return choosenFullPath;
  }
}
