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

package io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "properties")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewPropertiesXML implements Serializable {

	private static final long serialVersionUID = 7096769958086704706L;

	@XmlElement(name = "property")
	private List<VLViewPropertyXML> properties = new ArrayList<>();


	/**
	 * Constructor
	 */
	public VLViewPropertiesXML() {
	}


	/**
	 * @param name
	 * @return
	 */
	public VLViewPropertyXML getPropertyByName(String name) {
		if (properties != null) {
			for (VLViewPropertyXML propertyXML : properties) {
				if (propertyXML.getName().equalsIgnoreCase(name)) {
					return propertyXML;
				}
			}
		}
		return null;
	}


	/**
	 * @param name
	 * @return
	 */
	public String getPropertyValueByName(String name) {
		if (properties != null) {
			for (VLViewPropertyXML propertyXML : properties) {
				if (propertyXML.getName().equalsIgnoreCase(name)) {
					return propertyXML.getValue();
				}
			}
		}

		return null;
	}


	/**
	 * @return the properties
	 */
	public List<VLViewPropertyXML> getProperties() {
		if (properties == null) {
			properties = new ArrayList<>();
		}
		return properties;
	}
	
	
	public void addProperty(String name, String value) {
		VLViewPropertyXML prop = new VLViewPropertyXML();
		prop.setName(name);
		prop.setValue(value);
		addProperty(prop);
	}
	
	public void addProperty(VLViewPropertyXML prop) {
		if(this.properties  == null) {
			this.properties  = new ArrayList<VLViewPropertyXML>(); 
		}
		this.properties.add(prop);
	}


	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(List<VLViewPropertyXML> properties) {
		this.properties = properties;
	}


	/**
	 * @{inheritedDoc}
	 */
	@Override
	public String toString() {
		return "VLViewPropertiesXML [properties=" + properties + "]";
	}

	public static class Builder {

		private List<VLViewPropertyXML> properties;


		public Builder properties(List<VLViewPropertyXML> properties) {
			this.properties = properties;
			return this;
		}


		public VLViewPropertiesXML build() {
			return new VLViewPropertiesXML(this);
		}
	}


	private VLViewPropertiesXML(Builder builder) {
		properties = builder.properties;
	}
}
