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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import com.excilys.ebi.spring.dbunit.config.DBOp;
import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.DataSetFormat;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class ServletConfigurationProcessor {

	private String DATASETS_LOCATION_DELIMITERS = ",; \t\n";

	public static final String SPRING_DBUNIT_INIT_PARAM_PREFIX = "spring.dbunit.";

	public static final String DATASETS_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dataSets";

	public static final String SETUP_OPERATION_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "setUpOperation";

	public static final String TEARDOWN_OPERATION_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "tearDownOperation";

	public static final String FORMAT_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "format";

	public static final String DB_TYPE_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dbType";

	public static final String DATASOURCE_SPRING_NAME_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dataSourceSpringName";

	public static final String DEFAULT_DATASET = "classpath:dataSet.xml";

	public static final DataSetFormat DEFAULT_DATASET_FORMAT = DataSetFormat.FLAT;

	public static final DBOp DEFAULT_SETUP_OPERATION = DBOp.CLEAN_INSERT;

	public static final DBOp DEFAULT_TEARDOWN_OPERATION = DBOp.NONE;

	public static final DBType DEFAULT_DB_TYPE = DBType.HSQLDB;

	public static final String DEFAULT_DATASOURCE_SPRING_NAME = null;

	private ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

	public DataSetConfiguration getConfiguration(ServletContext servletContext) throws IOException, DatabaseUnitException {

		DataSetFormat format = buildInitParam(servletContext, FORMAT_INIT_PARAM, DEFAULT_DATASET_FORMAT, new InitFunction<DataSetFormat>() {
			public DataSetFormat apply(String in) {
				return DataSetFormat.valueOf(in);
			}
		});
		DBOp setUpOperation = buildInitParam(servletContext, SETUP_OPERATION_INIT_PARAM, DEFAULT_SETUP_OPERATION, new InitFunction<DBOp>() {
			public DBOp apply(String in) {
				return DBOp.valueOf(in);
			}
		});
		DBOp tearDownOperation = buildInitParam(servletContext, TEARDOWN_OPERATION_INIT_PARAM, DEFAULT_TEARDOWN_OPERATION, new InitFunction<DBOp>() {
			public DBOp apply(String in) {
				return DBOp.valueOf(in);
			}
		});
		DBType dbType = buildInitParam(servletContext, DB_TYPE_INIT_PARAM, DEFAULT_DB_TYPE, new InitFunction<DBType>() {
			public DBType apply(String in) {
				return DBType.valueOf(in);
			}
		});
		String dataSourceSpringName = buildInitParam(servletContext, DATASOURCE_SPRING_NAME_INIT_PARAM, DEFAULT_DATASOURCE_SPRING_NAME, new InitFunction<String>() {
			public String apply(String in) {
				return in;
			}
		});

		String[] dataSetsLocations = getDataSetsLocations(servletContext);

		List<IDataSet> dataSets = loadDataSets(dataSetsLocations, format);

		return new DataSetConfiguration(dataSets, dataSourceSpringName, setUpOperation, tearDownOperation, dbType);
	}

	private interface InitFunction<T> {
		T apply(String in);
	}

	private <T> T buildInitParam(ServletContext servletContext, String name, T defaultValue, InitFunction<T> function) {
		String initParam = servletContext.getInitParameter(name);
		return StringUtils.hasLength(initParam) ? function.apply(initParam.trim()) : defaultValue;
	}

	private String[] getDataSetsLocations(ServletContext servletContext) {
		String dataSetsInitParam = servletContext.getInitParameter(DATASETS_INIT_PARAM);

		if (StringUtils.hasLength(dataSetsInitParam)) {
			return StringUtils.tokenizeToStringArray(dataSetsInitParam, DATASETS_LOCATION_DELIMITERS);

		} else {
			return new String[] { DEFAULT_DATASET };
		}
	}

	private List<IDataSet> loadDataSets(String[] dataSetsLocations, DataSetFormat format) throws DataSetException, IOException {
		List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetsLocations.length);
		for (String location : dataSetsLocations) {
			Resource[] resources = resourceLoader.getResources(location);
			for (Resource resource : resources) {
				dataSets.add(format.fromInputStream(resource.getInputStream()));
			}
		}
		return dataSets;
	}
}
