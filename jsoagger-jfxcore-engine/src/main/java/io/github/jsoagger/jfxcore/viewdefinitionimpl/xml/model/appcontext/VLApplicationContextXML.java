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

package io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model.appcontext;



import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model.VLViewContextFilterGroupXML;
import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model.VLViewContextFilterXML;
import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model.VLViewFilterXML;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "context")
public class VLApplicationContextXML {

	@XmlElement(name = "import-context")
	private List<VLImportXML> imports;

	@XmlElement(name = "context-filter-group")
	private List<VLViewContextFilterGroupXML> contextFilterGroups;

	@XmlElement(name = "context-filter")
	private List<VLViewContextFilterXML> contextFilters;

	@XmlElement(name = "filter")
	private List<VLViewFilterXML> filters;

	@XmlElement(name = "component")
	private List<VLViewComponentXML> components;


	public List<VLViewComponentXML> getComponents() {
		return components;
	}


	public void setComponents(List<VLViewComponentXML> components) {
		this.components = components;
	}


	/**
	 * @return the imports
	 */
	public List<VLImportXML> getImports() {
		return imports;
	}


	/**
	 * @param imports
	 *            the imports to set
	 */
	public void setImports(List<VLImportXML> imports) {
		this.imports = imports;
	}


	/**
	 * @return the contextFilterGroups
	 */
	public List<VLViewContextFilterGroupXML> getContextFilterGroups() {
		return contextFilterGroups;
	}


	/**
	 * @param contextFilterGroups
	 *            the contextFilterGroups to set
	 */
	public void setContextFilterGroups(List<VLViewContextFilterGroupXML> contextFilterGroups) {
		this.contextFilterGroups = contextFilterGroups;
	}


	/**
	 * @return the contextFilters
	 */
	public List<VLViewContextFilterXML> getContextFilters() {
		return contextFilters;
	}


	/**
	 * @param contextFilters
	 *            the contextFilters to set
	 */
	public void setContextFilters(List<VLViewContextFilterXML> contextFilters) {
		this.contextFilters = contextFilters;
	}


	/**
	 * @return the filters
	 */
	public List<VLViewFilterXML> getFilters() {
		return filters;
	}


	/**
	 * @param filters
	 *            the filters to set
	 */
	public void setFilters(List<VLViewFilterXML> filters) {
		this.filters = filters;
	}
}
