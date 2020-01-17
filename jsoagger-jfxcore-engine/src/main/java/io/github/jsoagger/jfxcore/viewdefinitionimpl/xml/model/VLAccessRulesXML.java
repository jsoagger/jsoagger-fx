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
@XmlRootElement(name = "accessRules")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLAccessRulesXML {

	@XmlElement(name = "ruleResolver")
	private VLAccessRulesResolverXML ruleResolver;


	/**
	 * @return
	 */
	public String getRuleResolverName() {
		if (ruleResolver != null) {
			return ruleResolver.getName();
		}

		return null;
	}


	/**
	 * Getter of ruleResolver
	 *
	 * @return the ruleResolver
	 */
	public VLAccessRulesResolverXML getRuleResolver() {
		return ruleResolver;
	}


	/**
	 * Setter of ruleResolver
	 *
	 * @param ruleResolver
	 *            the ruleResolver to set
	 */
	public void setRuleResolver(VLAccessRulesResolverXML ruleResolver) {
		this.ruleResolver = ruleResolver;
	}
}
