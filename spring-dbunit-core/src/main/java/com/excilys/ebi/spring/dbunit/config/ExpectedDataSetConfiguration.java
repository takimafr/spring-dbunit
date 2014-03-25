/**
 * Copyright 2011-2012 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.spring.dbunit.config;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.io.IOException;
import java.util.List;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import com.excilys.ebi.spring.dbunit.config.Constants.ConfigurationDefaults;

public class ExpectedDataSetConfiguration implements DatabaseConnectionConfigurer {

	private boolean disabled;
	private String dataSourceSpringName;
	private DBType dbType = ConfigurationDefaults.DEFAULT_DB_TYPE;
	private String[] dataSetResourceLocations = new String[] { "classpath:expectedDataSet.xml" };
	private String[] columnsToIgnore = new String[]{};
	private DataSetFormat format = ConfigurationDefaults.DEFAULT_DB_FORMAT;
	private DataSetFormatOptions formatOptions = new DataSetFormatOptions();
	private String escapePattern = ConfigurationDefaults.DEFAULT_ESCAPE_PATTERN;
	private boolean qualifiedTableNames = ConfigurationDefaults.DEFAULT_QUALIFIED_TABLE_NAMES;
	private String[] tableType = ConfigurationDefaults.DEFAULT_TABLE_TYPE;
	private String schema = ConfigurationDefaults.DEFAULT_SCHEMA;

	public IDataSet getDataSet() throws DataSetException, IOException {
		List<IDataSet> dataSets = format.loadMultiple(formatOptions, dataSetResourceLocations);
		return dataSets.size() == 1 ? dataSets.get(0) : new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
	}

	public void configure(DatabaseConfig databaseConfig) {

		Assert.notNull(dbType, "dbType is required");

		databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dbType.getDataTypeFactory());
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, dbType.getMetadataHandler());
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, escapePattern);
		databaseConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, formatOptions.isCaseSensitiveTableNames());
		databaseConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, qualifiedTableNames);
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_TABLE_TYPE, tableType);
	}

	public static Builder newExpectedDataSetConfiguration() {
		return new Builder();
	}

	public static class Builder {

		private ExpectedDataSetConfiguration expectedDataSetConfiguration = new ExpectedDataSetConfiguration();

		private Builder() {
		}

		public Builder withDisabled(boolean disabled) {
			expectedDataSetConfiguration.disabled = disabled;
			return this;
		}

		public Builder withDataSourceSpringName(String dataSourceSpringName) {
			expectedDataSetConfiguration.dataSourceSpringName = dataSourceSpringName;
			return this;
		}

		public Builder withColumnsToIgnore(String[] columnsToIgnore) {
			expectedDataSetConfiguration.columnsToIgnore = columnsToIgnore;
			return this;
		}

		public Builder withDbType(DBType dbType) {
			expectedDataSetConfiguration.dbType = dbType;
			return this;
		}

		public Builder withDataSetResourceLocations(String[] dataSetResourceLocations) {
			expectedDataSetConfiguration.dataSetResourceLocations = dataSetResourceLocations;
			return this;
		}

		public Builder withFormat(DataSetFormat format) {
			expectedDataSetConfiguration.format = format;
			return this;
		}

		public Builder withFormatOptions(DataSetFormatOptions formatOptions) {
			expectedDataSetConfiguration.formatOptions = formatOptions;
			return this;
		}

		public Builder withEscapePattern(String escapePattern) {
			escapePattern = escapePattern.trim();
			expectedDataSetConfiguration.escapePattern = escapePattern.isEmpty() ? null : escapePattern;
			return this;
		}

		public Builder withQualifiedTableNames(boolean qualifiedTableNames) {
			expectedDataSetConfiguration.qualifiedTableNames = qualifiedTableNames;
			return this;
		}

		public Builder withTableType(String[] tableType) {
			if (tableType != null)
				expectedDataSetConfiguration.tableType = tableType;
			return this;
		}

		public Builder withSchema(String schema) {
			schema = schema.trim();
			if (!schema.isEmpty())
				expectedDataSetConfiguration.schema = schema;
			return this;
		}

		public ExpectedDataSetConfiguration build() throws DataSetException, IOException {

			Assert.notNull(expectedDataSetConfiguration.dataSetResourceLocations, "dataSetResourceLocations is required");
			Assert.notNull(expectedDataSetConfiguration.dbType, "dbType is required");
			Assert.notNull(expectedDataSetConfiguration.format, "format is required");
			Assert.notNull(expectedDataSetConfiguration.formatOptions, "formatOptions are required");

			return expectedDataSetConfiguration;
		}
	}

	public String getDataSetResourceLocation() {
		throw new UnsupportedOperationException();
	}

	public void setDataSetResourceLocation(String dataSetResourceLocation) {
		this.dataSetResourceLocations = tokenizeToStringArray(dataSetResourceLocation, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
	}

	public boolean isDisabled() {
		return disabled;
	}

	public String getDataSourceSpringName() {
		return dataSourceSpringName;
	}

	public DBType getDbType() {
		return dbType;
	}

	public String[] getDataSetResourceLocations() {
		return dataSetResourceLocations;
	}

	public DataSetFormat getFormat() {
		return format;
	}

	public DataSetFormatOptions getFormatOptions() {
		return formatOptions;
	}

	public String getEscapePattern() {
		return escapePattern;
	}

	public boolean isQualifiedTableNames() {
		return qualifiedTableNames;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setDataSourceSpringName(String dataSourceSpringName) {
		this.dataSourceSpringName = dataSourceSpringName;
	}

	public void setDbType(DBType dbType) {
		this.dbType = dbType;
	}

	public void setDataSetResourceLocations(String[] dataSetResourceLocations) {
		this.dataSetResourceLocations = dataSetResourceLocations;
	}

	public void setFormat(DataSetFormat format) {
		this.format = format;
	}

	public void setFormatOptions(DataSetFormatOptions formatOptions) {
		this.formatOptions = formatOptions;
	}

	public void setEscapePattern(String escapePattern) {
		this.escapePattern = escapePattern;
	}

	public void setQualifiedTableNames(boolean qualifiedTableNames) {
		this.qualifiedTableNames = qualifiedTableNames;
	}

	public String[] getTableType() {
		return tableType;
	}

	public void setTableType(String[] tableType) {
		this.tableType = tableType;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String[] getColumnsToIgnore() {
		return columnsToIgnore;
	}

	public void setColumnsToIgnore(String[] columnsToIgnore) {
		this.columnsToIgnore = columnsToIgnore;
	}
}
