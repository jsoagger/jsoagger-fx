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




import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ruleResolver")
public class VLAccessRulesResolverXML {

	@XmlAttribute(name = "name")
	private String name;


	/**
	 * Getter of name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Setter of name
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @{inheritedDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VLAccessRulesResolverXML [");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
		}
		builder.append("]");
		return builder.toString();
	}
}
