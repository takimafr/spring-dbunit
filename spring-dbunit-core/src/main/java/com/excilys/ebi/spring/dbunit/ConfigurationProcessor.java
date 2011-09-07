package com.excilys.ebi.spring.dbunit;

import java.io.IOException;

import org.dbunit.DatabaseUnitException;

import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;

public interface ConfigurationProcessor<T> {

	DataSetConfiguration getConfiguration(T context) throws IOException, DatabaseUnitException;
}
