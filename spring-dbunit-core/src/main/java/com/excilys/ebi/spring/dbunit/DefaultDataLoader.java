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
package com.excilys.ebi.spring.dbunit;

import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DefaultDataLoader implements DataLoader {

	public void doWithDataSet(ApplicationContext applicationContext, DataSetConfiguration configuration, Phase phase) throws Exception {

		if (configuration != null) {
			DataSource dataSource = lookUpDataSource(configuration, applicationContext);
			executeOperation(phase.getOperation(configuration), configuration, dataSource, phase);
		}
	}

	/**
	 * @param testContext
	 *            the testContext
	 * @return the DataSource used for loading the DataSet. If a name is
	 *         specified in the configuration, use it, otherwise, expect one and
	 *         only one DataSource in the ApplicationContext
	 */
	private DataSource lookUpDataSource(DataSetConfiguration configuration, ApplicationContext applicationContext) {

		DataSource dataSource = null;

		if (StringUtils.hasLength(configuration.getDataSourceSpringName())) {
			dataSource = applicationContext.getBean(configuration.getDataSourceSpringName(), DataSource.class);

		} else {
			dataSource = applicationContext.getBean(DataSource.class);
		}

		Assert.notNull(dataSource, "Unable to find a DataSource");
		return dataSource;
	}

	/**
	 * Execute a DBUbit operation
	 */
	private void executeOperation(DatabaseOperation operation, DataSetConfiguration configuration, DataSource dataSource, Phase phase) throws Exception {

		Connection connection = null;

		try {
			connection = DataSourceUtils.getConnection(dataSource);
			DatabaseConnection databaseConnection = new DatabaseConnection(connection);
			databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "\"?\"");
			databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, configuration.getDataTypeFactory());

			// if operation is CLEAN INSERT, change it into INSERT after first
			// DataSet so data is not deleted
			boolean first = true;
			for (IDataSet dataSet : phase.getDataSets(configuration)) {
				if (!first && operation.equals(DatabaseOperation.CLEAN_INSERT)) {
					operation = DatabaseOperation.INSERT;
				}
				operation.execute(databaseConnection, dataSet);
				first = false;
			}

		} finally {
			if (connection != null && !DataSourceUtils.isConnectionTransactional(connection, dataSource)) {
				// if the connection is transactional, closing it. Otherwise,
				// expects that the framework will do it
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
		}
	}
}
