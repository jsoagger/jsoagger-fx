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




import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.api.IMultipleEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputCombobox extends AbstractInputComponent {

  protected final ComboBox<IEnumeratedValueModel> combobox = new JFXComboBox<>();
  protected ObservableList<IEnumeratedValueModel> items = FXCollections.observableArrayList();

  /** Operation to load values to be displayed */
  protected Type displayType = Type.COMBOBOX;
  protected Pane radioLayout = null;

  /*** Radio button attributes */
  protected ToggleGroup group;

  /** By default selection mode is single */
  protected SelectionMode selectionMode = SelectionMode.SINGLE;
  protected Map<String, List<CheckBox>> checkBoxes = new HashMap<>();


  /**
   * Constructor
   */
  public InputCombobox() {
    super();
    combobox.getStyleClass().add("audience-selector-combobox");
    combobox.setItems(items);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    // the type of value to display
    final String displayType = configuration.getPropertyValue("displayType");
    if (StringUtils.isNotEmpty(displayType)) {
      this.displayType = Type.valueOf(displayType.toUpperCase());
    }

    String radioLayout = configuration.getPropertyValue("radioLayout");
    if(StringUtils.isEmpty(radioLayout)) {
      this.radioLayout = new VBox();
    }
    else {
      this.radioLayout = new TilePane();
    }
    this.radioLayout.getStyleClass().add("radio-buttons-wrapper");

    if (this.displayType == Type.COMBOBOX) {
      buildCombobox();
    } else if (this.displayType == Type.RADIO) {
      buildRadio();
    } else if (this.displayType == Type.CHECKBOX) {
      buildCheckbox();
      selectionMode = SelectionMode.MULTIPLE;
    }
  }


  /***************************************************************************
   * Display as Checkbox
   **************************************************************************/
  protected void buildCheckbox() {

    // if owner value load is multiple value loaded
    // build it differently
    final IEnumeratedValuesLoader evl = owner.getEnumeratedValuesLoader();
    if (evl instanceof IMultipleEnumeratedValuesLoader) {
      final Map<String, List<IEnumeratedValueModel>> vals = ((IMultipleEnumeratedValuesLoader) evl).getValues();

      for (final String k : vals.keySet()) {
        final Label label = new Label(k);
        label.getStyleClass().add("ep-as-search-form-bloc-header");

        final List<CheckBox> checkBoxs = buildCheckboxed(k, vals.get(k));
        final VBox internalL = new VBox();
        internalL.getStyleClass().add("ep-internal-checkbox-buttons-wrapper");
        internalL.getChildren().add(label);

        Iterator<CheckBox> it = checkBoxs.iterator();
        while(it.hasNext()) {
          CheckBox box = it.next();
          Node n = NodeHelper.wrapInGrowingHbox(box);
          n.getStyleClass().addAll("ep-internal-checkbox-buttons-wrapper-each", "hand-hover");
          internalL.getChildren().add(n);

          // when the row is clicked the checkbox is selected
          n.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(e.getClickCount() == 1) {
              box.fire();
            }
          });

          if(!it.hasNext()) {
            n.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
          }
        }
        radioLayout.getChildren().add(internalL);
      }

      initMultiValued();
    } else {
      if (!owner.getEnumeratedValueModels().isEmpty()) {
        final List<CheckBox> checkBoxs = buildCheckboxed("Lonely", owner.getEnumeratedValueModels());
        Iterator<CheckBox> it = checkBoxs.iterator();
        while(it.hasNext()) {
          CheckBox box = it.next();
          Node n = NodeHelper.wrapInGrowingHbox(box);
          n.getStyleClass().addAll("ep-internal-checkbox-buttons-wrapper-each", "hand-hover");
          // when the row is clicked the checkbox is selected
          n.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(e.getClickCount() == 1) {
              box.fire();
            }
          });

          if(!it.hasNext()) {
            n.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
          }
          radioLayout.getChildren().addAll(n);
        }

        NodeHelper.setHgrow(radioLayout);
        initMultiValued();
      }
    }
  }


  private List<CheckBox> buildCheckboxed(String group, List<IEnumeratedValueModel> vals) {
    final Iterator<IEnumeratedValueModel> iterator = vals.iterator();
    final List<CheckBox> checkBoxs = new ArrayList<>();

    checkBoxes.put(group, checkBoxs);

    while (iterator.hasNext()) {
      final IEnumeratedValueModel model = iterator.next();

      final CheckBox checkBox = new JFXCheckBox();
      checkBoxs.add(checkBox);

      checkBox.setUserData(model);
      checkBox.setText(model.getValue());
      // internalLayout.getStyleClass().add("radio-buttons-internal-wrapper");
      if (!iterator.hasNext()) {
        // internalLayout.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"),
        // true);
      }

      checkBox.selectedProperty().addListener(getCheckboxSelectListener(group, checkBox));
    }

    return checkBoxs;
  }


  private void updateValuetoGroup(String groupname, boolean newValue) {
    // remove all listners on group different from groupname
    // and make the unselected
    for(String key: checkBoxes.keySet()) {
      if (!groupname.equals(key)) {
        final List<CheckBox> chBoxs = checkBoxes.get(key);
        for(CheckBox ch: chBoxs) {
          ch.selectedProperty().removeListener(getCheckboxSelectListener(key, ch));
        }
        for(CheckBox ch: chBoxs) {
          ch.selectedProperty().set(false);
        }
        for(CheckBox ch: chBoxs) {
          ch.selectedProperty().addListener(getCheckboxSelectListener(key, ch));
        }
      }
      // update current value to current group
      else {
        final List<CheckBox> chBoxs = checkBoxes.get(key);
        final StringBuffer buf = new StringBuffer();
        for(CheckBox ch: chBoxs) {
          if (ch.isSelected()) {
            final EnumeratedValueModel m = (EnumeratedValueModel) ch.getUserData();
            buf.append(m.getSavedValue()).append("#");
          }
        }
        owner.currentInternalValueProperty().set(null);
        owner.currentInternalValueProperty().set(buf.toString());
      }
    }
  }

  private final Map<CheckBox, ChangeListener<Boolean>> listeners = new HashMap<>();


  private ChangeListener<Boolean> getCheckboxSelectListener(String group, CheckBox checkBox) {
    if (!listeners.containsKey(checkBox)) {
      final ChangeListener<Boolean> listener = (ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
        updateValuetoGroup(group, newValue);
      };
      listeners.put(checkBox, listener);
    }

    return listeners.get(checkBox);
  }


  private void initMultiValued() {
    final String val = owner.getInitialInternalValue();
    if (StringUtils.isNotBlank(val)) {
      final List vals = Arrays.asList(val.split(owner.getEscapedMultivaluedSeparator()));
      for (final List<CheckBox> checkboxs : checkBoxes.values()) {
        for (final CheckBox checkbox : checkboxs) {
          final String checkboxval = ((EnumeratedValueModel) checkbox.getUserData()).getSavedValue();
          checkbox.setSelected(vals.contains(checkboxval));
          checkbox.selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            updateMultiValuedCheckBox();
          });
        }
      }
    }
    // select initial value
    else {
      if (checkBoxes.size() > 0) {
        final Iterator<List<CheckBox>> iterator = checkBoxes.values().iterator();
        if (iterator.hasNext()) {
          final List<CheckBox> next = iterator.next();
          if (next.size() > 0)
            next.get(0).setSelected(true);
        }
      }
    }
  }


  private void updateMultiValuedCheckBox() {
    String vals = "";
    for (final List<CheckBox> checkboxs : checkBoxes.values()) {
      for (final CheckBox checkbox : checkboxs) {
        if (checkbox.isSelected()) {
          final String val = ((EnumeratedValueModel) checkbox.getUserData()).getSavedValue();
          if (vals != "") {
            vals = vals + owner.getMultivaluedSeparator();
          }

          vals = vals + val;
        }
      }
    }

    owner.currentInternalValueProperty().set(vals);
  }


  /***************************************************************************
   * Display as RADIO Button
   **************************************************************************/
  protected void buildRadio() {
    group = new ToggleGroup();
    owner.commitModification();

    if (!owner.getEnumeratedValueModels().isEmpty()) {

      // data change listener
      group.selectedToggleProperty().addListener((ChangeListener<Toggle>) (observable, oldValue, newValue) -> {
        owner.currentInternalValueProperty().set(((EnumeratedValueModel) newValue.getUserData()).getSavedValue());
      });

      // if owner value load is multiple value loaded
      // build it differently
      final IEnumeratedValuesLoader evl = owner.getEnumeratedValuesLoader();
      if (evl instanceof IMultipleEnumeratedValuesLoader) {
        final Map<String, List<IEnumeratedValueModel>> vals = ((IMultipleEnumeratedValuesLoader) evl).getValues();

        for (final String k : vals.keySet()) {
          final Label label = new Label(k);
          label.getStyleClass().add("ep-as-search-form-bloc-header");

          Node content = buildRadioButtons(vals.get(k));
          final VBox internalL = new VBox();
          internalL.getChildren().addAll(label, content);
          radioLayout.getChildren().add(internalL);
        }
      }
      else {
        if (!owner.getEnumeratedValueModels().isEmpty()) {
          Node content = buildRadioButtons(owner.getEnumeratedValueModels());
          radioLayout.getChildren().add(content);
        }
      }
    }
  }

  private Node buildRadioButtons(List<IEnumeratedValueModel> vals) {
    final VBox radiolayouts = new VBox();
    final Iterator<IEnumeratedValueModel> iterator = vals.iterator();
    while (iterator.hasNext()) {
      final IEnumeratedValueModel model = iterator.next();
      final HBox internalLayout = new HBox();

      final RadioButton button = new JFXRadioButton();
      button.setToggleGroup(group);
      button.setText(model.getValue());
      button.setUserData(model);
      internalLayout.getChildren().add(button);
      internalLayout.getStyleClass().addAll("radio-buttons-internal-wrapper", "hand-hover");
      if (!iterator.hasNext()) {
        internalLayout.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
      }

      internalLayout.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
        if(e.getClickCount() == 1) {
          button.fire();
          controller.currentForwarEditor().commitModification();
        }
      });

      if (StringUtils.isNotBlank(model.getDescription())) {
        final Label description = new Label();
        description.setWrapText(true);
        //description.textProperty().bind(model.descriptionProperty());
        description.getStyleClass().add("input-form-description");
        description.managedProperty().bind(description.visibleProperty());
        description.visibleProperty().bind(Bindings.isNotEmpty(description.textProperty()));
        internalLayout.getChildren().add(description);
      }

      if (model.getSavedValue() != null && model.getSavedValue().toString().equalsIgnoreCase(owner.getInitialInternalValue())) {
        button.setSelected(true);
      }

      radiolayouts.getChildren().add(internalLayout);
    }

    return radiolayouts;
  }


  /***************************************************************************
   *
   * Display Combobox
   *
   **************************************************************************/
  protected void buildCombobox() {
    combobox.setId(configuration.getId());
    combobox.setId(id);

    if (owner.getEnumeratedValueModels() != null && !owner.getEnumeratedValueModels().isEmpty()) {
      items.setAll(owner.getEnumeratedValueModels());
    }

    if (!ComponentUtils.isAttributeMandatory(configuration)) {
      final IEnumeratedValueModel promptValue = EnumeratedValueModel.empty();
      items.add(0, promptValue);

      if (prompt.isPresent() && StringUtils.isNotBlank(prompt.get())) {
        final String valueTranslated = controller.getLocalised(prompt.get(), configuration);
        promptValue.setValue(valueTranslated);
      }
    }
    else {
      // init first item
      combobox.getSelectionModel().selectFirst();

      final IEnumeratedValueModel selected = combobox.getSelectionModel().getSelectedItem();

      if(selected != null) {
        final String newValKey = selected.getSavedValue();
        owner.currentInternalValueProperty().set(newValKey);
        commitModification();
      }
    }

    final Callback cellFactory = param -> new ListCell<EnumeratedValueModel>() {

      @Override
      protected void updateItem(EnumeratedValueModel item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          if (item != null && item.getSavedValue() == null) {
            // getStyleClass().add("combo-box-prompt");
            setText(item.getValue());
          } else {
            // getStyleClass().add("combo-box-list-value");
            setText(item.getValue());
          }
        }
      }
    };


    combobox.setButtonCell((ListCell<IEnumeratedValueModel>) cellFactory.call(null));
    combobox.setCellFactory(cellFactory);


    // update wizardConfiguration value when the selection has changed
    final ChangeListener valueChangeListener = (arg0, arg1, arg2) -> {
      final IEnumeratedValueModel selected = combobox.getSelectionModel().getSelectedItem();

      if(selected != null) {
        final String newValKey = selected.getSavedValue();
        owner.currentInternalValueProperty().set(newValKey);
      }
      else {
        owner.currentInternalValueProperty().set(null);
      }
    };

    addValueChangeListner(valueChangeListener);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addValueChangeListner(ChangeListener changeListener) {
    super.addValueChangeListner(changeListener);
    if (displayType == Type.COMBOBOX) {
      combobox.getSelectionModel().selectedItemProperty().addListener(changeListener);
    } else {
      // data change listener
      group.selectedToggleProperty().addListener(changeListener);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addDisplayBinding(Label label) {
    super.addDisplayBinding(label);

    // update wizardConfiguration value when the selection has changed
    combobox.getSelectionModel().selectedItemProperty().addListener((ChangeListener) (arg0, arg1, arg2) -> {
      if (arg2 != null) {
        final String newValKey = ((IComboboxValue) arg2).getValue();
        label.textProperty().set(newValKey);
      } else {
        label.textProperty().set("");
      }
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    if (displayType == Type.RADIO || displayType == Type.CHECKBOX) {
      return radioLayout;
    }

    if (combodisplay == null) {
      combodisplay = new StackPane();
      combodisplay.setAlignment(Pos.CENTER_LEFT);
      combodisplay.getStyleClass().add("jsoagger-control");
      combodisplay.pseudoClassStateChanged(PseudoClass.getPseudoClass("borderless"), true);
      combodisplay.getChildren().add(combobox);
    }

    return combodisplay;
  }

  StackPane combodisplay = null;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setInErrorState() {
    super.setInErrorState();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setInValidState() {
    super.setInValidState();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    if (displayType == Type.RADIO) {
    }

    return combobox;
  }

  /***************************************************************************
   *
   * Support classes
   *
   **************************************************************************/
  public static enum Type {
    RADIO, CHECKBOX, COMBOBOX;
  }
}
