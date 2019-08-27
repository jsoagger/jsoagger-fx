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


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.ViewInputComponent;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.components.input.InputMode;
import io.github.jsoagger.jfxcore.engine.components.toolbar.inline.FormInlineToobar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;

import javafx.animation.FadeTransition;
import javafx.css.PseudoClass;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * A row inside a fieldset. A row can displayed multiple input. Input are layed out inside
 * {@link HBox}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormFieldsetRow extends HBox implements IFormFieldsetRow{

  protected IFieldset fieldset;
  protected IFormBlocContent owner;

  protected final List<IInputComponentWrapper> entries = new ArrayList<>();
  protected AbstractViewController controller;
  protected VLViewComponentXML rowConfig;

  /**
   * When the row is inline editing (different from editing in wizard mode), all content of current
   * row [layout pane] is removed and replaced by the editor pane. Inline editing is the state from
   * view to edit mode. When creating wizard the state is directly EDIT, not inline eidting.
   */
  protected Pane layout = null;

  /**
   * Contextual menu of the row and inline actions
   */
  protected ContextMenu contextMenu = new ContextMenu();


  /**
   * Constructor
   */
  public FormFieldsetRow() {
    super();
  }


  @Override
  public void setLast() {
    for(IInputComponentWrapper e: entries) {
      e.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
    }
    layout.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
  }


  /**
   * Constructor
   */
  public FormFieldsetRow(AbstractViewController controller, VLViewComponentXML blocConfig, VLViewComponentXML rowConfig, InputMode mode, boolean lastRow) {
    this();
    this.controller = controller;
    this.rowConfig = rowConfig;

    if (rowConfig.getPropertyValue("styleClass") != "") {
      rowConfig.getPropertyValue("styleClass");
    }

    String orientation = "horizontal";
    if (StringUtils.isNotBlank(rowConfig.getPropertyValue("orientation"))) {
      orientation = rowConfig.getPropertyValue("orientation");
    }

    // MULTIPLE INPUT IN SAME ROW, HORIZONTALY OR VERTICALLY
    // ALGO
    // 1. if declared horizontal -> HBOX
    // 2. if declared flow -> FlowPane
    // 3. else if has multiple rows, ->flow
    // or else vbox
    if(mode == InputMode.EDIT) {
      if(hasMultipleElementsOnRow(rowConfig)) {
        layout = new FlowPane();
        layout.getStyleClass().add("ep-form-hrow");
        NodeHelper.setHgrow(layout);
      }
      else if ("horizontal".equalsIgnoreCase(orientation)) {
        layout = new HBox();
        layout.getStyleClass().add("ep-form-hrow");
        NodeHelper.setHgrow(layout);
      } else if ("flow".equalsIgnoreCase(orientation)) {
        layout = new FlowPane();
        layout.getStyleClass().add("ep-form-hrow");
        NodeHelper.setHgrow(layout);
      }
      else {
        if(hasMultipleElementsOnRow(rowConfig)) {
          layout = new FlowPane();
          layout.getStyleClass().add("ep-form-hrow");
        }
        else {
          layout = new VBox();
          layout.getStyleClass().add("ep-form-vrow");
        }
        NodeHelper.setHVGrow(layout);
      }
    }
    else {
      if ("horizontal".equalsIgnoreCase(orientation)) {
        layout = new HBox();
        layout.getStyleClass().add("ep-form-hrow");
        NodeHelper.setHgrow(layout);
      } else {
        layout = new VBox();
        layout.getStyleClass().add("ep-form-vrow");
        NodeHelper.setHVGrow(layout);
      }
    }


    getChildren().addAll(layout);
    // NodeHelper.setHVGrow(this);

    NodeHelper.styleClassAddAll(this, rowConfig, "styleClass", "form-fieldset-row-layout");
    pseudoClassStateChanged(PseudoClass.getPseudoClass("editing"), mode == InputMode.EDIT);

    // some rows common styleclass may have been factorized
    // in bloc config
    String rowsStyleClass = blocConfig.getPropertyValue("rowsStyleClass");
    if (StringUtils.isNotBlank(rowsStyleClass)) {
      getStyleClass().addAll(rowsStyleClass.split(","));
    }

    if (lastRow) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
    }

    // process and add each entry in the hbox
    if (rowConfig != null && rowConfig.hasSubComponent()) {

      // this index is used in inline button action.
      // why, because we need to know which component has fired the
      // forward action to display only data associated to this component
      // in the worward editor.
      int rowInternalIndex = 0;

      VLViewComponentXML rowsConfig = rowConfig.getComponentById("Rows").orElse(null);
      if (rowsConfig != null && rowsConfig.hasSubComponent()) {

        Iterator<VLViewComponentXML> iterator = rowsConfig.getSubcomponents().iterator();
        while (iterator.hasNext()) {

          VLViewComponentXML referencedAtt = iterator.next();

          final String attrID = referencedAtt.getReference();
          if (StringUtils.isBlank(attrID)) {
            System.out.println("##### [WARNING] Attribute no displayed because reference is null : " + rowsConfig);
            continue;
          }

          final VLViewComponentXML attributeCfg = ComponentUtils.resolveDefinition(controller, attrID).orElse(null);

          // attribute not found in XML
          if (attributeCfg == null) {
            throw new RuntimeException(MessageFormat.format("Attribute with id {0} has not been resolved in view", attrID));
          }

          attributeCfg.setVisibleIf(referencedAtt.getVisibleIf());
          final InputComponentWrapper componentWrapper = new InputComponentWrapper();
          componentWrapper.buildFrom(attributeCfg, controller, mode, lastRow);
          componentWrapper.setRow(this);

          FormInlineToobar inlineAction = null;
          // PROCESS ACTIONS ON EACH ATTRIBUTE
          VLViewComponentXML actionsCfg = attributeCfg.getComponentById("Actions").orElse(null);
          if (actionsCfg != null) {
            VLViewComponentXML inlineActionsCfg = actionsCfg.getComponentById("InLine").orElse(null);
            if (inlineActionsCfg != null) {
              inlineAction = ToolbarUtils.formInlineToolbar(controller, inlineActionsCfg, this, rowInternalIndex);
            }
          }

          // IF VERTICAL LAYOUT AND THE ROW HAS AN ACTION
          // WRAP THEM IN HBOX
          if (!"horizontal".equalsIgnoreCase(orientation)) {
            if (inlineAction != null) {
              HBox rowWrapper = new HBox();
              rowWrapper.getStyleClass().add("ep-form-row-each");
              if (!iterator.hasNext()) {
                rowWrapper.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
              }

              NodeHelper.setHgrow(componentWrapper.getDisplay(), rowWrapper);
              rowWrapper.getChildren().addAll(componentWrapper.getDisplay(), inlineAction);
              layout.getChildren().add(rowWrapper);

              if(inlineAction.getDefaultInlineAction() != null) {
                rowWrapper.getStyleClass().add("hand-hover");
                final FormInlineToobar ic = inlineAction;

                if(AbstractApplicationRunner.isMobile()) {
                  rowWrapper.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
                    if(!AbstractApplicationRunner.isApplicationScrolling() && e.getTouchCount() == 1) {
                      ButtonBase buttonBase = ic.getDefaultInlineAction().getAction();
                      buttonBase.fire();
                    }
                  });
                }
                else {
                  rowWrapper.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if(e.getClickCount() == 2) {
                      ButtonBase buttonBase = ic.getDefaultInlineAction().getAction();
                      buttonBase.fire();
                    }
                  });
                }
              }
            } else {
              layout.getChildren().addAll(componentWrapper.getDisplay());
            }
          } else {

            NodeHelper.setHgrow(componentWrapper.getDisplay());

            if (inlineAction != null) {
              layout.getChildren().addAll(componentWrapper.getDisplay(), inlineAction);

              if(inlineAction.getDefaultInlineAction() != null) {
                layout.getStyleClass().add("hand-hover");
                final FormInlineToobar ic = inlineAction;
                if(AbstractApplicationRunner.isMobile()) {
                  layout.addEventFilter(TouchEvent.TOUCH_RELEASED, e -> {
                    if(!AbstractApplicationRunner.isApplicationScrolling() && e.getTouchCount() == 1) {
                      ButtonBase buttonBase = ic.getDefaultInlineAction().getAction();
                      buttonBase.fire();
                    }
                  });
                }
                else {
                  layout.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if(e.getClickCount() == 2) {
                      ButtonBase buttonBase = ic.getDefaultInlineAction().getAction();
                      buttonBase.fire();
                    }
                  });
                }
              }
            } else {
              layout.getChildren().addAll(componentWrapper.getDisplay());
            }
          }

          entries.add(componentWrapper);
          rowInternalIndex++;
        }
      }
    }
  }


  private boolean hasMultipleElementsOnRow(VLViewComponentXML rowConfig) {
    VLViewComponentXML rowsConfig = rowConfig.getComponentById("Rows").orElse(null);
    return rowsConfig != null && rowsConfig.hasSubComponent() && rowsConfig.getSubcomponents().size() > 1;
  }


  /**
   * Inline Edit a row inside a simple editor pane. All node in the row are wrapped in an editor which
   * holds the action buttons.
   *
   * @param inlineEditor
   */
  @Override
  public void beginInlineEdit(IFormRowEditor inlineEditor) {
    beginEdition();

    getChildren().clear();
    getChildren().add(inlineEditor.getDisplay());

    minHeightProperty().unbind();
    minHeightProperty().bind(((Pane) inlineEditor.getDisplay()).heightProperty());
    NodeHelper.setHgrow(inlineEditor.getDisplay());

    FadeTransition tt = NodeHelper.fadeIn(inlineEditor.getDisplay(), Duration.millis(100));
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_CIRC);
    tt.setInterpolator(ei);
    tt.play();

    requestLayout();
    requestParentLayout();
  }


  @Override
  public void endInlineEdit() {
    endEdition();
    getChildren().clear();
    minHeightProperty().unbind();
    minHeightProperty().set(prefHeightProperty().get());
    // TODO TEST ME
    // getChildren().addAll(layout, actionsLayout);
    getChildren().addAll(layout);
  }


  public void endEdition() {
    for(IInputComponentWrapper row: entries) {
      row.switchToInfoView();
    }
  }


  @Override
  public void beginEdition() {
    for(IInputComponentWrapper row: entries) {
      row.switchToEditView();
    }
  }


  @Override
  public List<ViewInputComponent> getViewEntries() {
    List<ViewInputComponent> v = new ArrayList<>();
    for(IInputComponentWrapper in : entries) {
      if(in.viewInputComponent() != null) {
        v.add(in.viewInputComponent());
      }
    }

    return v;
  }


  /**
   * Get the rowConfig
   *
   * @return the rowConfig
   */
  public VLViewComponentXML getConfiguration() {
    return rowConfig;
  }


  /**
   * Get the entries
   *
   * @return the entries
   */
  @Override
  public List<IInputComponentWrapper> getEntries() {
    return entries;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   *
   */
  public void setOwner(IFormBlocContent owner) {
    this.owner = owner;
  }


  /**
   *
   */
  @Override
  public void setFieldset(IFieldset fieldset) {
    this.fieldset = fieldset;
  }


  /**
   *
   */
  @Override
  public IFieldset getFieldset() {
    return fieldset;
  }


  /**
   * Getter of owner
   *
   * @return the owner
   */
  public IFormBlocContent getOwner() {
    return owner;
  }
}
