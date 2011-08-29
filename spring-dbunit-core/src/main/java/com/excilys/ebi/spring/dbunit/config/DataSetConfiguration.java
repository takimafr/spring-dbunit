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

import java.io.IOException;
import java.util.List;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DataSetConfiguration {

	private final IDataSet dataSet;

	private final String dataSourceSpringName;

	private final DBOperation setUpOp;

	private final DBOperation tearDownOp;

	private final DBType dbType;

	private DataSetConfiguration(IDataSet dataSet, String dataSourceSpringName, DBOperation setUpOp, DBOperation tearDownOp, DBType dbType) throws DataSetException {

		this.dataSet = dataSet;
		this.dataSourceSpringName = dataSourceSpringName;
		this.setUpOp = setUpOp;
		this.tearDownOp = tearDownOp;
		this.dbType = dbType;
	}

	public IDataSet getDataSet() {
		return dataSet;
	}

	public String getDataSourceSpringName() {
		return dataSourceSpringName;
	}

	public IDataTypeFactory getDataTypeFactory() {
		return dbType.getDataTypeFactory();
	}

	public DatabaseOperation getSetUpOperation() {
		return setUpOp.getDatabaseOperation();
	}

	public DatabaseOperation getTearDownOperation() {
		return tearDownOp.getDatabaseOperation();
	}

	public static Builder newDataSetConfiguration() {
		return new Builder();
	}

	public static class Builder {

		private String dataSourceSpringName;
		private DBOperation setUpOperation;
		private DBOperation tearDownOperation;
		private DBType dbType;
		private List<String> dataSetResourceLocations;
		private DataSetFormat format;
		private DataSetFormatOptions formatOptions;

		private Builder() {
		}

		public Builder withDataSourceSpringName(String dataSourceSpringName) {
			this.dataSourceSpringName = dataSourceSpringName;
			return this;
		}

		public Builder withSetUpOp(DBOperation setUpOp) {
			this.setUpOperation = setUpOp;
			return this;
		}

		public Builder withTearDownOp(DBOperation tearDownOp) {
			this.tearDownOperation = tearDownOp;
			return this;
		}

		public Builder withDbType(DBType dbType) {
			this.dbType = dbType;
			return this;
		}

		public Builder withDataSetResourceLocations(List<String> dataSetResourceLocations) {
			this.dataSetResourceLocations = dataSetResourceLocations;
			return this;
		}

		public Builder withFormat(DataSetFormat format) {
			this.format = format;
			return this;
		}

		public Builder withFormatOptions(DataSetFormatOptions formatOptions) {
			this.formatOptions = formatOptions;
			return this;
		}

		public DataSetConfiguration build() throws DataSetException, IOException {

			Assert.notNull(setUpOperation, "setUpOperation is required");
			Assert.notNull(tearDownOperation, "tearDownOperation is required");
			Assert.notNull(dbType, "dbType is required");
			Assert.notNull(format, "format is required");

			List<IDataSet> dataSets = format.loadMultiple(formatOptions, dataSetResourceLocations);
			Assert.notEmpty(dataSets, "dataSets are required");

			IDataSet singleDataSet = dataSets.size() == 1 ? dataSets.get(0) : new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));

			return new DataSetConfiguration(singleDataSet, dataSourceSpringName, setUpOperation, tearDownOperation, dbType);
		}
	}
}
