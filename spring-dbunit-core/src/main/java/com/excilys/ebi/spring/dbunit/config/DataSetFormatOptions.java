/*
 * Copyright 2010-2011 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.excilys.ebi.spring.dbunit.config;

import com.excilys.ebi.spring.dbunit.config.Constants.ConfigurationDefaults;

public class DataSetFormatOptions {

	private boolean columnSensing = ConfigurationDefaults.DEFAULT_COLUMN_SENSING;
	private String dtdLocation = ConfigurationDefaults.DEFAULT_DTD_LOCATION;
	private boolean dtdMetadata = ConfigurationDefaults.DEFAULT_DTD_METADATA;
	private boolean caseSensitiveTableNames = ConfigurationDefaults.DEFAULT_CASE_SENSITIVE_TABLE_NAMES;

	public static DataSetFormatOptions.Builder newFormatOptions() {
		return new Builder();
	}

	public static class Builder {

		private DataSetFormatOptions options = new DataSetFormatOptions();

		private Builder() {
		}

		public DataSetFormatOptions.Builder withColumnSensing(boolean columnSensing) {
			options.columnSensing = columnSensing;
			return this;
		}

		public DataSetFormatOptions.Builder withDtdLocation(String dtdLocation) {
			options.dtdLocation = dtdLocation;
			return this;
		}

		public DataSetFormatOptions.Builder withDtdMetadata(boolean dtdMetadata) {
			options.dtdMetadata = dtdMetadata;
			return this;
		}

		public DataSetFormatOptions.Builder withCaseSensitiveTableNames(boolean caseSensitiveTableNames) {
			options.caseSensitiveTableNames = caseSensitiveTableNames;
			return this;
		}

		public DataSetFormatOptions build() {
			return options;
		}
	}

	public boolean isColumnSensing() {
		return columnSensing;
	}

	public String getDtdLocation() {
		return dtdLocation;
	}

	public boolean isDtdMetadata() {
		return dtdMetadata;
	}

	public boolean isCaseSensitiveTableNames() {
		return caseSensitiveTableNames;
	}

	public void setColumnSensing(boolean columnSensing) {
		this.columnSensing = columnSensing;
	}

	public void setDtdLocation(String dtdLocation) {
		this.dtdLocation = dtdLocation;
	}

	public void setDtdMetadata(boolean dtdMetadata) {
		this.dtdMetadata = dtdMetadata;
	}

	public void setCaseSensitiveTableNames(boolean caseSensitiveTableNames) {
		this.caseSensitiveTableNames = caseSensitiveTableNames;
	}
}