package com.excilys.ebi.spring.dbunit.config;

import org.dbunit.database.DatabaseConfig;

public interface DatabaseConnectionConfigurer {

	void configure(DatabaseConfig databaseConfig);
}
