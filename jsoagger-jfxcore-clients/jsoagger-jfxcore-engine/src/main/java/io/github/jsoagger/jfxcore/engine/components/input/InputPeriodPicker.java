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



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

//import org.controlsfx.control.PopOver;
//import org.controlsfx.control.PopOver.ArrowLocation;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class InputPeriodPicker extends AbstractInputComponent {

  private static final String customRangePattern = "Between %s and %s";
  private static final String CUSTOM_RANGE = "80";

  private ComboBox<IEnumeratedValueModel> comboBox = new ComboBox<>();
  private List<IEnumeratedValueModel> selectableValues = new ArrayList<>();
  private StackPane rootContainer = new StackPane();
  private CustomChoiceActionner customChoiceActionner = null;


  /**
   * Constructor
   */
  public InputPeriodPicker() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    customChoiceActionner = new CustomChoiceActionner();
    if ((owner.getEnumeratedValueModels() != null) && !owner.getEnumeratedValueModels().isEmpty()) {
      comboBox.getItems().setAll(owner.getEnumeratedValueModels());
    }

    final Callback cellFactory = param -> new ListCell<EnumeratedValueModel>() {

      @Override
      protected void updateItem(EnumeratedValueModel item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          if ((item != null) && (item.getSavedValue() == null)) {
            setText(item.getValue());
          } else {
            setText(item.getValue());
          }
        }
      }
    };

    comboBox.setButtonCell(new ListCell<IEnumeratedValueModel>() {

      @Override
      protected void updateItem(IEnumeratedValueModel item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          if ((item != null) && (item.getSavedValue() == null)) {
            // getStyleClass().add("combo-box-prompt");
            setText(item.getValue());
          } else {
            // getStyleClass().add("combo-box-list-value");
            setText(item.getValue());
          }
        }
      }
    });

    comboBox.setCellFactory(cellFactory);
    final ChangeListener valueChangeListener0 = (arg0, arg1, arg2) -> {
      final IEnumeratedValueModel selected = comboBox.getSelectionModel().getSelectedItem();
      final String newValKey = selected.getSavedValue();
      owner.currentInternalValueProperty().set(newValKey);
    };
    comboBox.valueProperty().addListener(valueChangeListener0);

    final ChangeListener valueChangeListener1 = (arg0, arg1, arg2) -> {
      final IEnumeratedValueModel selected = comboBox.getSelectionModel().getSelectedItem();
      if ((selected != null) && selected.getSavedValue().startsWith(CUSTOM_RANGE)) {
        hideCombobox();
      } else if ((selected != null) && !selected.getSavedValue().startsWith(CUSTOM_RANGE)) {
        owner.currentInternalValueProperty().set(selected.getSavedValue());
      } else {
        showCombobox();
      }
    };
    comboBox.valueProperty().addListener(valueChangeListener1);

    comboBox.getSelectionModel().selectFirst();
    comboBox.getItems().addAll(selectableValues);
    comboBox.getSelectionModel().selectFirst();
    showCombobox();

    rootContainer.getChildren().add(comboBox);
    rootContainer.getChildren().add(customChoiceActionner);

    comboBox.getStyleClass().add("ep-period-picker-combo");
    comboBox.prefWidthProperty().bind(rootContainer.widthProperty());
  }


  private void showCombobox() {
    comboBox.setVisible(true);
    customChoiceActionner.setVisible(false);
  }


  private void hideCombobox() {
    comboBox.setVisible(false);
    customChoiceActionner.setVisible(true);
    customChoiceActionner.buildAndShowCustomChoicePopover();
  }


  /**
   * Get the value
   *
   * @return the value
   */
  public SimpleStringProperty valueProperty() {
    return new SimpleStringProperty();
  }

  /**
   * The custom choice hyperlink with the arrow button
   *
   * @author Ramilafananana  VONJISOA
   *
   */
  private class CustomChoiceActionner extends HBox {

    private final DatePicker from = new DatePicker();
    private final DatePicker to = new DatePicker();
    private final Hyperlink customChoice = new Hyperlink();
    private final Hyperlink icon = new Hyperlink();


    /**
     * Constructor
     */
    public CustomChoiceActionner() {
      final Pane spacer = new Pane();
      NodeHelper.setHgrow(spacer);

      // build the icon
      // GlyphsDude.setIcon(icon, FontAwesomeIcon.CARET_DOWN, "16");
      icon.setFocusTraversable(false);

      // build the custom choice
      customChoice.getStyleClass().add("ep-period-picker-custom-choice");

      // build the date selections
      from.requestFocus();
      from.setValue(LocalDate.now().minusDays(7));
      to.setValue(LocalDate.now());

      // when customChoice is clicked show combobox
      // todo improve this to show a menu with all choices
      customChoice.setOnAction(e -> {
        showCombobox();
        comboBox.getSelectionModel().selectFirst();
      });

      getChildren().addAll(customChoice, spacer, icon);
    }


    private void updateHyperlinkText() {
      final String fromString = from.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
      final String toString = to.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
      customChoice.setText(String.format(customRangePattern, fromString, toString));
      // owner.initialValueProperty().set("CUSTOM_RANGE|" + fromString +
      // "|" + toString);
    }


    /**
     * Show the popover
     */
    public void buildAndShowCustomChoicePopover() {
      //final PopOver popOver = new PopOver();
      //popOver.setDetachable(false);
      //popOver.setDetached(false);
      //popOver.show(comboBox);
      //popOver.setArrowLocation(ArrowLocation.TOP_CENTER);

      // from today - 7 days
      final VBox rowContainer1 = new VBox();
      final Label fromLabel = new Label("From");
      rowContainer1.getChildren().addAll(fromLabel, from);

      // when the from date changes, update label of hyperlink
      from.valueProperty().addListener((e, e1, e2) -> {
        if (e2 != null) {
          updateHyperlinkText();
        }
      });

      // to now
      final VBox rowContainer2 = new VBox();
      final Label toLabel = new Label("To");
      rowContainer2.getChildren().addAll(toLabel, to);

      // when the from date changes, update label of hyperlink
      to.valueProperty().addListener((e, e1, e2) -> {
        if (e2 != null) {
          updateHyperlinkText();
        }
      });

      final VBox container = new VBox();
      container.getChildren().addAll(fromLabel, from, toLabel, to);
      NodeHelper.setHVGrow(container);
      container.setStyle("-fx-spacing: 16; -fx-padding: 24");
      updateHyperlinkText();
      // popOver.setContentNode(container);
    }
  }


  /**
   * Get the comboBox
   *
   * @return the comboBox
   */
  public ComboBox<IEnumeratedValueModel> getComboBox() {
    return comboBox;
  }


  @Override
  public Node getDisplay() {
    return rootContainer;
  }


  @Override
  public Node getComponent() {
    return comboBox;
  }
}
