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
package com.excilys.ebi.spring.dbunit.servlet;

import static com.excilys.ebi.spring.dbunit.config.DataSetConfiguration.newDataSetConfiguration;
import static com.excilys.ebi.spring.dbunit.config.DataSetFormatOptions.newFormatOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.dbunit.DatabaseUnitException;
import org.springframework.util.StringUtils;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.DataSetFormat;
import com.excilys.ebi.spring.dbunit.servlet.ServletConfigurationConstants.Defaults;
import com.excilys.ebi.spring.dbunit.servlet.ServletConfigurationConstants.InitParams;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class ServletConfigurationProcessor {

	private String DATASETS_LOCATION_DELIMITERS = ",; \t\n";

	public DataSetConfiguration getConfiguration(final ServletContext servletContext) throws IOException, DatabaseUnitException {

		DataSetFormat format = buildInitParam(servletContext, InitParams.FORMAT_INIT_PARAM, Defaults.DEFAULT_DATASET_FORMAT, new InitFunction<DataSetFormat>() {
			public DataSetFormat apply(String in) {
				return DataSetFormat.valueOf(in);
			}
		});
		DBOperation setUpOperation = buildInitParam(servletContext, InitParams.SETUP_OPERATION_INIT_PARAM, Defaults.DEFAULT_SETUP_OPERATION, new InitFunction<DBOperation>() {
			public DBOperation apply(String in) {
				return DBOperation.valueOf(in);
			}
		});
		DBOperation tearDownOperation = buildInitParam(servletContext, InitParams.TEARDOWN_OPERATION_INIT_PARAM, Defaults.DEFAULT_TEARDOWN_OPERATION,
				new InitFunction<DBOperation>() {
					public DBOperation apply(String in) {
						return DBOperation.valueOf(in);
					}
				});
		DBType dbType = buildInitParam(servletContext, InitParams.DB_TYPE_INIT_PARAM, Defaults.DEFAULT_DB_TYPE, new InitFunction<DBType>() {
			public DBType apply(String in) {
				return DBType.valueOf(in);
			}
		});
		String dataSourceSpringName = buildInitParam(servletContext, InitParams.DATASOURCE_SPRING_NAME_INIT_PARAM, Defaults.DEFAULT_DATASOURCE_SPRING_NAME,
				new InitFunction<String>() {
					public String apply(String in) {
						return in;
					}
				});
		List<String> dataSetsLocations = buildInitParam(servletContext, InitParams.DATASETS_INIT_PARAM, Defaults.DEFAULT_DATASET, new InitFunction<List<String>>() {
			public List<String> apply(String in) {
				return Arrays.asList(StringUtils.tokenizeToStringArray(in, DATASETS_LOCATION_DELIMITERS));
			}
		});
		boolean columnSensing = buildInitParam(servletContext, InitParams.COLUMN_SENSING_INIT_PARAM, Defaults.DEFAULT_COLUMN_SENSING, new InitFunction<Boolean>() {
			public Boolean apply(String in) {
				return Boolean.valueOf(in);
			}
		});
		boolean dtdMetadata = buildInitParam(servletContext, InitParams.DTD_METADATA_INIT_PARAM, Defaults.DEFAULT_DTD_METADATA, new InitFunction<Boolean>() {
			public Boolean apply(String in) {
				return Boolean.valueOf(in);
			}
		});
		boolean caseSensitiveTableNames = buildInitParam(servletContext, InitParams.CASE_SENSITIVE_TABLE_NAMES_INIT_PARAM, Defaults.DEFAULT_CASE_SENSITIVE_TABLE_NAMES,
				new InitFunction<Boolean>() {
					public Boolean apply(String in) {
						return Boolean.valueOf(in);
					}
				});
		String dtdLocation = buildInitParam(servletContext, InitParams.DTD_LOCATION_INIT_PARAM, Defaults.DEFAULT_DTD_LOCATION, new InitFunction<String>() {
			public String apply(String in) {
				return in;
			}
		});

		return newDataSetConfiguration().withFormat(format)/**/
		.withFormatOptions(newFormatOptions()/**/
		.withColumnSensing(columnSensing).withDtdMetadata(dtdMetadata).withDtdLocation(dtdLocation).withCaseSensitiveTableNames(caseSensitiveTableNames).build())/**/
		.withSetUpOp(setUpOperation)/**/
		.withTearDownOp(tearDownOperation)/**/
		.withDbType(dbType)/**/
		.withDataSourceSpringName(dataSourceSpringName)/**/
		.withDataSetResourceLocations(dataSetsLocations)/**/
		.build();
	}

	private interface InitFunction<T> {
		T apply(String in);
	}

	private <T> T buildInitParam(ServletContext servletContext, String name, T defaultValue, InitFunction<T> function) {
		String initParam = servletContext.getInitParameter(name);
		return StringUtils.hasLength(initParam) ? function.apply(initParam.trim()) : defaultValue;
	}
}
