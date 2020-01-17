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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "view-filter")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewFilterXML implements Serializable {

	private static final long serialVersionUID = -6364354880489942068L;

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "param")
	private VLViewFilterParamXML param;

	@XmlElement(name = "and")
	private VLViewFilterAndOperatorXML and;

	@XmlElement(name = "or")
	private VLViewFilterOrOperatorXML or;

	@XmlElement(name = "not")
	private VLViewFilterNotOperatorXML not;


	public boolean andEmpty() {
		return and == null;
	}


	public boolean notEmpty() {
		return not == null;
	}


	public boolean orEmpty() {
		return or == null;
	}


	public VLViewFilterAndOperatorXML and() {
		if (and == null) {
			return new VLViewFilterAndOperatorXML();
		}

		return and;
	}


	public VLViewFilterOrOperatorXML or() {
		if (or == null) {
			return new VLViewFilterOrOperatorXML();
		}

		return or;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the param
	 */
	public VLViewFilterParamXML getParam() {
		return param;
	}


	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(VLViewFilterParamXML param) {
		this.param = param;
	}


	/**
	 * @return the and
	 */
	public VLViewFilterAndOperatorXML getAnd() {
		return and;
	}


	/**
	 * @param and
	 *            the and to set
	 */
	public void setAnd(VLViewFilterAndOperatorXML and) {
		this.and = and;
	}


	/**
	 * @return the or
	 */
	public VLViewFilterOrOperatorXML getOr() {
		return or;
	}


	/**
	 * @param or
	 *            the or to set
	 */
	public void setOr(VLViewFilterOrOperatorXML or) {
		this.or = or;
	}


	/**
	 * @return the not
	 */
	public VLViewFilterNotOperatorXML getNot() {
		return not;
	}


	/**
	 * @param not
	 *            the not to set
	 */
	public void setNot(VLViewFilterNotOperatorXML not) {
		this.not = not;
	}
}
