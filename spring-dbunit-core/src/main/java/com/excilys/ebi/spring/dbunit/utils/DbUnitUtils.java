package com.excilys.ebi.spring.dbunit.utils;

import static org.springframework.jdbc.datasource.DataSourceUtils.getConnection;

import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.DatabaseConnectionConfigurer;
import com.excilys.ebi.spring.dbunit.config.ExpectedDataSetConfiguration;

public class DbUnitUtils {

	/**
	 * @param testContext
	 *            the testContext
	 * @return the DataSource used for loading the DataSet. If a name is
	 *         specified in the configuration, use it, otherwise, expect one and
	 *         only one DataSource in the ApplicationContext
	 */
	public static DataSource lookUpDataSource(ApplicationContext applicationContext, DataSetConfiguration configuration) {
		return lookUpDataSource(applicationContext, configuration.getDataSourceSpringName());
	}

	public static DataSource lookUpDataSource(ApplicationContext applicationContext, String dataSourceSpringName) {
		return dataSourceSpringName != null ? 
				applicationContext.getBean(dataSourceSpringName, DataSource.class) : 
				applicationContext.getBean(DataSource.class);
	}

	public static DatabaseConnection getDatabaseConnection(Connection connection, String schema, DatabaseConnectionConfigurer databaseConnectionConfigurer) throws DatabaseUnitException {

		DatabaseConnection databaseConnection = StringUtils.hasLength(schema) ? new DatabaseConnection(connection, schema) : new DatabaseConnection(connection);
		DatabaseConfig databaseConfig = databaseConnection.getConfig();
		databaseConnectionConfigurer.configure(databaseConfig);

		return databaseConnection;
	}

	public static DatabaseConnection getDatabaseConnection(ApplicationContext applicationContext, DatabaseConnectionConfigurer configuration) throws DatabaseUnitException {
		DataSource dataSource = null;
		String schema = null;
		if (configuration instanceof DataSetConfiguration) {
			dataSource = lookUpDataSource(applicationContext, ((DataSetConfiguration) configuration).getDataSourceSpringName());
			schema = ((DataSetConfiguration) configuration).getSchema();
		}
		else if (configuration instanceof ExpectedDataSetConfiguration) {
			dataSource = lookUpDataSource(applicationContext, ((ExpectedDataSetConfiguration) configuration).getDataSourceSpringName());
			schema = ((ExpectedDataSetConfiguration) configuration).getSchema();
		}
		Connection connection = getConnection(dataSource);
		return getDatabaseConnection(connection, schema, configuration);
	}
}
