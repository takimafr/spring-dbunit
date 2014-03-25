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
package com.excilys.ebi.spring.dbunit;

import static com.excilys.ebi.spring.dbunit.utils.DbUnitUtils.getDatabaseConnection;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.util.StopWatch;

import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.Phase;

public class DbUnitDatabasePopulator implements DatabasePopulator {

	private static final Logger LOGGER = getLogger(DbUnitDatabasePopulator.class);

	private DataSetConfiguration dataSetConfiguration;

	private Phase phase;

	public void populate(Connection connection) throws SQLException {

		LOGGER.debug("populating");

		StopWatch sw = new StopWatch("DbUnitDatabasePopulator");

		DatabaseOperation operation = phase.getOperation(dataSetConfiguration);
		try {
			IDataSet dataSet = dataSetConfiguration.getDataSet();
			String schema = dataSetConfiguration.getSchema();
			DatabaseConnection databaseConnection = getDatabaseConnection(connection, schema, dataSetConfiguration);
			sw.start("populating");
			operation.execute(databaseConnection, dataSet);
			sw.stop();
			LOGGER.debug(sw.prettyPrint());

		} catch (BatchUpdateException e) {
			LOGGER.error("BatchUpdateException while loading dataset", e);
			LOGGER.error("Caused by : ", e.getNextException());
			throw e;

		} catch (DatabaseUnitException e) {
			throw new DbUnitException(e);
		} catch (IOException e) {
			throw new DbUnitException(e);
		}
	}

	public DataSetConfiguration getDataSetConfiguration() {
		return dataSetConfiguration;
	}

	public Phase getPhase() {
		return phase;
	}

	@Required
	public void setDataSetConfiguration(DataSetConfiguration dataSetConfiguration) {
		this.dataSetConfiguration = dataSetConfiguration;
	}

	@Required
	public void setPhase(Phase phase) {
		this.phase = phase;
	}

}
