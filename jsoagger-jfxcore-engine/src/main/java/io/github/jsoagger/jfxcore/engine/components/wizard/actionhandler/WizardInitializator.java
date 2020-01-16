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

package io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler;



import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;

/**
 * Caller before the wizard is show. Can be used to init some components in wizard.
 * <p>
 * As in the the first page, before step is not called, there is no way to inialize components.
 * Thats why you can use this class.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 26 févr. 2018
 */
public interface WizardInitializator {

  void initialize(WizardViewController wizardViewController);
}
