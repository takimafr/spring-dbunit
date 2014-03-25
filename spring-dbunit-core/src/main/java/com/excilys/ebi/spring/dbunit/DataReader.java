package com.excilys.ebi.spring.dbunit;

import org.dbunit.dataset.IDataSet;
import org.springframework.context.ApplicationContext;

import com.excilys.ebi.spring.dbunit.config.ExpectedDataSetConfiguration;

public interface DataReader {

	IDataSet execute(ApplicationContext applicationContext, ExpectedDataSetConfiguration expectedDataSetConfiguration, String tableName) throws Exception;
}
