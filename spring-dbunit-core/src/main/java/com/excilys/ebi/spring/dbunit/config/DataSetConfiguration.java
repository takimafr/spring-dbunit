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

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DataSetConfiguration implements DatabaseConnectionConfigurer {

	private boolean disabled;
	private String dataSourceSpringName;
	private DBOperation setUpOperation[] = new DBOperation[] { ConfigurationDefaults.DEFAULT_SETUP_OPERATION };
	private DBOperation tearDownOperation[] = new DBOperation[] { ConfigurationDefaults.DEFAULT_TEARDOWN_OPERATION };
	private DBType dbType = ConfigurationDefaults.DEFAULT_DB_TYPE;
	private String[] dataSetResourceLocations = new String[] { "classpath:dataSet.xml" };
	private DataSetFormat format = ConfigurationDefaults.DEFAULT_DB_FORMAT;
	private DataSetFormatOptions formatOptions = new DataSetFormatOptions();
	private String escapePattern = ConfigurationDefaults.DEFAULT_ESCAPE_PATTERN;
	private int batchSize = ConfigurationDefaults.DEFAULT_BATCH_SIZE;
	private int fetchSize = ConfigurationDefaults.DEFAULT_FETCH_SIZE;
	private boolean qualifiedTableNames = ConfigurationDefaults.DEFAULT_QUALIFIED_TABLE_NAMES;
	private boolean batchedStatements = ConfigurationDefaults.DEFAULT_BATCHED_STATEMENTS;
	private boolean skipOracleRecycleBinTables = ConfigurationDefaults.DEFAULT_SKIP_ORACLE_RECYCLEBIN_TABLES;
	private String[] tableType = ConfigurationDefaults.DEFAULT_TABLE_TYPE;

	public IDataSet getDataSet() throws DataSetException, IOException {

		List<IDataSet> dataSets = format.loadMultiple(formatOptions, dataSetResourceLocations);
		return dataSets.size() == 1 ? dataSets.get(0) : new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
	}

	public void configure(DatabaseConfig databaseConfig) {

		Assert.notNull(dbType, "dbType is required");

		databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dbType.getDataTypeFactory());
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, dbType.getMetadataHandler());
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, escapePattern);
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, batchSize);
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_FETCH_SIZE, fetchSize);
		databaseConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, formatOptions.isCaseSensitiveTableNames());
		databaseConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, qualifiedTableNames);
		databaseConfig.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, batchedStatements);
		databaseConfig.setProperty(DatabaseConfig.FEATURE_SKIP_ORACLE_RECYCLEBIN_TABLES, skipOracleRecycleBinTables);
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_TABLE_TYPE, tableType);
	}

	public static Builder newDataSetConfiguration() {
		return new Builder();
	}

	public static class Builder {

		private DataSetConfiguration dataSetConfiguration = new DataSetConfiguration();

		private Builder() {
		}

		public Builder withDisabled(boolean disabled) {
			dataSetConfiguration.disabled = disabled;
			return this;
		}

		public Builder withDataSourceSpringName(String dataSourceSpringName) {
			dataSetConfiguration.dataSourceSpringName = dataSourceSpringName;
			return this;
		}

		public Builder withSetUpOp(DBOperation[] setUpOp) {
			dataSetConfiguration.setUpOperation = setUpOp;
			return this;
		}

		public Builder withTearDownOp(DBOperation[] tearDownOp) {
			dataSetConfiguration.tearDownOperation = tearDownOp;
			return this;
		}

		public Builder withDbType(DBType dbType) {
			dataSetConfiguration.dbType = dbType;
			return this;
		}

		public Builder withDataSetResourceLocations(String[] dataSetResourceLocations) {
			dataSetConfiguration.dataSetResourceLocations = dataSetResourceLocations;
			return this;
		}

		public Builder withFormat(DataSetFormat format) {
			dataSetConfiguration.format = format;
			return this;
		}

		public Builder withFormatOptions(DataSetFormatOptions formatOptions) {
			dataSetConfiguration.formatOptions = formatOptions;
			return this;
		}

		public Builder withEscapePattern(String escapePattern) {
			dataSetConfiguration.escapePattern = escapePattern;
			return this;
		}

		public Builder withBatchSize(int batchSize) {
			dataSetConfiguration.batchSize = batchSize;
			return this;
		}

		public Builder withFetchSize(int fetchSize) {
			dataSetConfiguration.fetchSize = fetchSize;
			return this;
		}

		public Builder withQualifiedTableNames(boolean qualifiedTableNames) {
			dataSetConfiguration.qualifiedTableNames = qualifiedTableNames;
			return this;
		}

		public Builder withBatchedStatements(boolean batchedStatements) {
			dataSetConfiguration.batchedStatements = batchedStatements;
			return this;
		}

		public Builder withSkipOracleRecycleBinTables(boolean skipOracleRecycleBinTables) {
			dataSetConfiguration.skipOracleRecycleBinTables = skipOracleRecycleBinTables;
			return this;
		}

		public Builder withTableType(String[] tableType) {
			dataSetConfiguration.tableType = tableType;
			return this;
		}

		public DataSetConfiguration build() throws DataSetException, IOException {

			Assert.notNull(dataSetConfiguration.dataSetResourceLocations, "dataSetResourceLocations is required");
			Assert.notNull(dataSetConfiguration.setUpOperation, "setUpOperation is required");
			Assert.notNull(dataSetConfiguration.tearDownOperation, "tearDownOperation is required");
			Assert.notNull(dataSetConfiguration.dbType, "dbType is required");
			Assert.notNull(dataSetConfiguration.format, "format is required");
			Assert.notNull(dataSetConfiguration.formatOptions, "formatOptions are required");

			return dataSetConfiguration;
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

	public DBOperation[] getSetUpOperation() {
		return setUpOperation;
	}

	public DBOperation[] getTearDownOperation() {
		return tearDownOperation;
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

	public int getBatchSize() {
		return batchSize;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public boolean isQualifiedTableNames() {
		return qualifiedTableNames;
	}

	public boolean isBatchedStatements() {
		return batchedStatements;
	}

	public boolean isSkipOracleRecycleBinTables() {
		return skipOracleRecycleBinTables;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setDataSourceSpringName(String dataSourceSpringName) {
		this.dataSourceSpringName = dataSourceSpringName;
	}

	public void setSetUpOperation(DBOperation[] setUpOperation) {
		this.setUpOperation = setUpOperation;
	}

	public void setTearDownOperation(DBOperation[] tearDownOperation) {
		this.tearDownOperation = tearDownOperation;
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

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public void setQualifiedTableNames(boolean qualifiedTableNames) {
		this.qualifiedTableNames = qualifiedTableNames;
	}

	public void setBatchedStatements(boolean batchedStatements) {
		this.batchedStatements = batchedStatements;
	}

	public void setSkipOracleRecycleBinTables(boolean skipOracleRecycleBinTables) {
		this.skipOracleRecycleBinTables = skipOracleRecycleBinTables;
	}

	public String[] getTableType() {
		return tableType;
	}

	public void setTableType(String[] tableType) {
		this.tableType = tableType;
	}
}
