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




import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
@XmlRootElement(name = "menuRows")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewRootMenuRowsXML {

	@XmlAttribute(name = "id") private String							id;
	@XmlElement(name = "menuRow") private List<VLViewRootMenuRowXML>	rows;


	/**
	 * Getter of id
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * Setter of id
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * Getter of rows
	 *
	 * @return the rows
	 */
	public List<VLViewRootMenuRowXML> getRows() {
		return rows;
	}


	/**
	 * Setter of rows
	 *
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(List<VLViewRootMenuRowXML> rows) {
		this.rows = rows;
	}


	/**
	 * @{inheritedDoc}
	 */
	@Override public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VLViewRootMenuRowsXML [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (rows != null) {
			builder.append("rows=");
			builder.append(rows);
		}
		builder.append("]");
		return builder.toString();
	}

}
