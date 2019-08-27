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

package io.github.jsoagger.jfxcore.engine.events;




import io.github.jsoagger.jfxcore.engine.components.dialog.HideDialogEvent;
import io.github.jsoagger.jfxcore.engine.components.dialog.ShowDialogEvent;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarBackButtonClicked;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetCustomRightActions;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetStandardRightActions;
import io.github.jsoagger.jfxcore.engine.components.menu.event.SelectMenuItemEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.action.AllNotificationsClearedEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.action.DeleteAllNotificationsEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.action.MarkAllNotificationsReadenEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NewNotificationEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NotificationDeletedEvent;
import io.github.jsoagger.jfxcore.engine.components.tab.PopTabContentEvent;
import io.github.jsoagger.jfxcore.engine.components.tab.PushTabContentEvent;
import io.github.jsoagger.jfxcore.engine.components.tab.ReinitHeaderNavigationEvent;
import io.github.jsoagger.jfxcore.engine.components.tree.event.TreeFolderCreatedEvent;
import io.github.jsoagger.jfxcore.engine.components.tree.event.TreePopulatedFromTemplateEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PopStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.BuildRSContentEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public enum CoreEvent implements VLEvent {

  // @formatter:off
  CloseMenuEvent(CloseMenuEvent.class),
  DisplayMenuEvent(DisplayMenuEvent.class),
  DisplaySearchTabEvent(DisplaySearchTabEvent.class),
  LoginSuccessEvent(LoginSuccessEvent.class),
  LogoutSuccessEvent(LogoutSuccessEvent.class),
  MaximizeMenuEvent(MaximizeMenuEvent.class),
  MinimizeMenuEvent(MinimizeMenuEvent.class),
  PinSecondaryMenuRightEvent(PinSecondaryMenuRightEvent.class),
  PopRootViewEvent(PopRootViewEvent.class),
  UnpinSecondaryMenuRightEvent(UnpinSecondaryMenuRightEvent.class),
  HideDialogEvent(HideDialogEvent.class),
  HeaderSearchLostFocusedEvent(HeaderSearchLostFocusedEvent.class),
  HeaderSearchFocusedEvent(HeaderSearchFocusedEvent.class),
  ShowDialogEvent(ShowDialogEvent.class),
  SetRootViewEvent(SetRootViewEvent.class),
  RefreshTableCurrentPageEvent(RefreshTableCurrentPageEvent.class),
  LinkDeletedEvent(LinkDeletedEvent.class),
  LinkCreatedEvent(LinkCreatedEvent.class),
  PartCreatedEvent(PartCreatedEvent.class),
  FolderedCreatedEvent(FolderedCreatedEvent.class),
  TreeFolderCreatedEvent(TreeFolderCreatedEvent.class),
  TreePopulatedFromTemplateEvent(TreePopulatedFromTemplateEvent.class),
  UpdateCurrentLocationEvent(UpdateCurrentLocationEvent.class),
  PopStructureContentEvent(PopStructureContentEvent.class),
  PushStructureContentEvent(PushStructureContentEvent.class),
  BuildRSContentEvent(BuildRSContentEvent.class),
  SelectMenuItemEvent(SelectMenuItemEvent.class),
  ModelUpdatedEvent(ModelUpdatedEvent.class),
  ElementRemovedFromTableEvent(ElementRemovedFromTableEvent.class),
  SetCurrentLocationEvent(SetCurrentLocationEvent.class),
  HeaderNavbarFireBackButton(HeaderNavbarFireBackButton.class),
  HeaderNavbarBackButtonClicked(HeaderNavbarBackButtonClicked.class),
  HeaderNavbarSetCustomRightActions(HeaderNavbarSetCustomRightActions.class),
  HeaderNavbarSetStandardRightActions(HeaderNavbarSetStandardRightActions.class),
  AllNotificationsClearedEvent(AllNotificationsClearedEvent.class),
  DeleteAllNotificationsEvent(DeleteAllNotificationsEvent.class),
  MarkAllNotificationsReadenEvent(MarkAllNotificationsReadenEvent.class),
  NotificationEvent(NewNotificationEvent.class),
  NotificationDeletedEvent(NotificationDeletedEvent.class),
  ReinitHeaderNavigationEvent(ReinitHeaderNavigationEvent.class),
  PopTabContentEvent(PopTabContentEvent.class),
  DeleteObjectFromStructureEvent(DeleteObjectFromStructureEvent.class),
  PushTabContentEvent(PushTabContentEvent.class);
  // @formatter:on

  private Class clazz;


  /**
   * Constructor
   *
   * @param s
   */
  CoreEvent(Class<? extends GenericEvent> clazz) {
    this.clazz = clazz;
  }


  /**
   * @return
   */
  @Override
  public <T extends GenericEvent> Class<T> filter() {
    return clazz;
  }
}
