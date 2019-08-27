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




import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.javafx.StackedFontIcon;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CheckboxInputView extends AbstractViewInputComponent {

  /***
   * The description is displayed along with the checkbox on its right. Its is different from label
   */
  private Hyperlink descriptionLabel = new Hyperlink();
  private Hyperlink theCheckbox = new Hyperlink();
  private FontIcon unselectedIcon = new FontIcon();
  private FontIcon selectedIcon = new FontIcon();

  private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
  private final StackPane content = new StackPane();


  /**
   * Constructor
   */
  public CheckboxInputView() {
    super();

    StackedFontIcon fontIcon = new StackedFontIcon();
    unselectedIcon = new FontIcon("fa-toggle-off:26");
    selectedIcon = new FontIcon("fa-toggle-on:26");
    fontIcon.getChildren().addAll(unselectedIcon, selectedIcon);
    theCheckbox.setGraphic(fontIcon);

    unselectedIcon.visibleProperty().bind(Bindings.not(selected));
    selectedIcon.visibleProperty().bind(selected);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    content.setAlignment(Pos.CENTER_RIGHT);
    content.getStyleClass().add("ep-input-checkbox-view-wrapper");

    NodeHelper.styleClassSetAll(selectedIcon, getConfiguration(), "iconStyleClass", "grey-ikonli");
    NodeHelper.styleClassSetAll(unselectedIcon, getConfiguration(), "iconStyleClass", "grey-ikonli");

    final String desc = configuration.getPropertyValue("description");
    if (StringUtils.isNotBlank(desc)) {
      descriptionLabel.setText(controller.getLocalised(desc));
      content.getChildren().add(descriptionLabel);
    }

    content.getChildren().addAll(NodeHelper.horizontalSpacer(), theCheckbox);
    theCheckbox.addEventHandler(ActionEvent.ACTION, e -> selected.set(!selected.get()));
    theCheckbox.setContentDisplay(ContentDisplay.LEFT);

    // updatable property
    boolean updatable = getConfiguration().getBooleanProperty("updatable", false);
    theCheckbox.setDisable(!updatable);

    selected.set(Boolean.valueOf(inputComponentWrapper.getCurrentInternalValue()));
    // as there is no inline edition of checkbox
    // commit directly any change done on it!
    if (updatable) {
      selected.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
        inputComponentWrapper.setCurrentValue(newValue);
        inputComponentWrapper.commitModification();
      });
    }

    theCheckbox.getStyleClass().add("transparent-focus");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return content;
  }
}
