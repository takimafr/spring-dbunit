package com.excilys.ebi.spring.dbunit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.datasource.init.DatabasePopulator;

import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.DatabaseConnectionConfigurer;
import com.excilys.ebi.spring.dbunit.config.Phase;

public class DbUnitDatabasePopulator implements DatabasePopulator {

	private DataSetConfiguration dataSetConfiguration;

	private Phase phase;

	public void populate(Connection connection) throws SQLException {
		DatabaseOperation operation = phase.getOperation(dataSetConfiguration);
		try {
			IDataSet dataSet = dataSetConfiguration.getDataSet();
			DatabaseConnection databaseConnection = getDatabaseConnection(connection, dataSetConfiguration);
			operation.execute(databaseConnection, dataSet);

		} catch (DatabaseUnitException e) {
			throw new DbUnitException(e);
		} catch (IOException e) {
			throw new DbUnitException(e);
		}
	}

	private DatabaseConnection getDatabaseConnection(Connection connection, DatabaseConnectionConfigurer databaseConnectionConfigurer) throws DatabaseUnitException {

		DatabaseConnection databaseConnection = new DatabaseConnection(connection);
		DatabaseConfig databaseConfig = databaseConnection.getConfig();
		databaseConnectionConfigurer.configure(databaseConfig);

		return databaseConnection;
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
