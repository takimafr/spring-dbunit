package com.excilys.ebi.spring.dbunit;

import static com.excilys.ebi.spring.dbunit.utils.DbUnitUtils.getDatabaseConnection;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.springframework.context.ApplicationContext;

import com.excilys.ebi.spring.dbunit.config.ExpectedDataSetConfiguration;

public class DefaultDataReader implements DataReader {

	public IDataSet execute(ApplicationContext applicationContext, ExpectedDataSetConfiguration expectedDataSetConfiguration, String tableName) throws Exception {
		if (expectedDataSetConfiguration != null) {
			DatabaseConnection databaseConnection = getDatabaseConnection(applicationContext, expectedDataSetConfiguration);
			return databaseConnection.createDataSet(new String[]{tableName});
		}
		return null;
	}

}
