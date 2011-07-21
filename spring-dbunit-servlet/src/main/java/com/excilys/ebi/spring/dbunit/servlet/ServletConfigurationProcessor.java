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
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import com.excilys.ebi.spring.dbunit.DBOps;
import com.excilys.ebi.spring.dbunit.DBType;
import com.excilys.ebi.spring.dbunit.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.DataSetFormat;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class ServletConfigurationProcessor {

	public static final String SPRING_DBUNIT_INIT_PARAM_PREFIX = "spring.dbunit.";

	public static final String DATASETS_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dataSets";

	public static final String SETUPOPERATION_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "setUpOperation";

	public static final String TEARDOWNOPERATION_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "tearDownOperation";

	public static final String FORMAT_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "format";

	public static final String DB_TYPE_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dbType";

	public static final String DATASOURCE_SPRING_NAME_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dataSourceSpringName";

	public static final String DEFAULT_DATASET = "classpath:dataSet.xml";

	public static final DataSetFormat DEFAULT_DATASET_FORMAT = DataSetFormat.FLAT;

	public static final DBOps DEFAULT_SETUP_OPERATION = DBOps.CLEAN_INSERT;

	public static final DBOps DEFAULT_TEARDOWN_OPERATION = DBOps.NONE;

	public static final DBType DEFAULT_DB_TYPE = DBType.HSQLDB;

	public static final String DEFAULT_DATASOURCE_SPRING_NAME = null;

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	public DataSetConfiguration getConfiguration(ServletContext servletContext) throws IOException, DatabaseUnitException {

		DataSetFormat format = getDataSetFormat(servletContext);
		DBOps setUpOperation = getSetUpOperation(servletContext);
		DBOps tearDownOperation = getTearDownOperationInitParam(servletContext);
		DBType dbType = getDBType(servletContext);
		String dataSourceSpringName = getDataSourceSpringName(servletContext);
		List<String> dataSetsLocations = getDataSetsLocations(servletContext);

		List<IDataSet> dataSets = loadDataSets(dataSetsLocations, format);

		return new DataSetConfiguration(dataSets, dataSourceSpringName, setUpOperation.getDatabaseOperation(), tearDownOperation.getDatabaseOperation(),
				dbType.getDataTypeFactory());
	}

	private DataSetFormat getDataSetFormat(ServletContext servletContext) {
		String formatInitParam = servletContext.getInitParameter(FORMAT_INIT_PARAM);
		return StringUtils.hasLength(formatInitParam) ? DataSetFormat.valueOf(formatInitParam.trim()) : DEFAULT_DATASET_FORMAT;
	}

	private DBOps getSetUpOperation(ServletContext servletContext) {
		String setUpOperationInitParam = servletContext.getInitParameter(SETUPOPERATION_INIT_PARAM);
		return StringUtils.hasLength(setUpOperationInitParam) ? DBOps.valueOf(setUpOperationInitParam.trim()) : DEFAULT_SETUP_OPERATION;
	}

	private DBOps getTearDownOperationInitParam(ServletContext servletContext) {
		String tearDownOperationInitParam = servletContext.getInitParameter(TEARDOWNOPERATION_INIT_PARAM);
		return StringUtils.hasLength(tearDownOperationInitParam) ? DBOps.valueOf(tearDownOperationInitParam.trim()) : DEFAULT_TEARDOWN_OPERATION;
	}

	private DBType getDBType(ServletContext servletContext) {
		String dbTypeInitParam = servletContext.getInitParameter(DB_TYPE_INIT_PARAM);
		return StringUtils.hasLength(dbTypeInitParam) ? DBType.valueOf(dbTypeInitParam.trim()) : DEFAULT_DB_TYPE;
	}

	private String getDataSourceSpringName(ServletContext servletContext) {
		String dataSourceSpringNameInitParam = servletContext.getInitParameter(DATASOURCE_SPRING_NAME_INIT_PARAM);
		return StringUtils.hasText(dataSourceSpringNameInitParam) ? dataSourceSpringNameInitParam.trim() : DEFAULT_DATASOURCE_SPRING_NAME;
	}

	private List<String> getDataSetsLocations(ServletContext servletContext) {
		String dataSetsInitParam = servletContext.getInitParameter(DATASETS_INIT_PARAM);
		List<String> locations = new ArrayList<String>();

		if (StringUtils.hasLength(dataSetsInitParam)) {
			for (String location : dataSetsInitParam.split(";")) {
				locations.add(location.trim());
			}

		} else {
			locations.add(DEFAULT_DATASET);
		}

		return locations;
	}

	private List<IDataSet> loadDataSets(List<String> dataSetsLocations, DataSetFormat format) throws DataSetException, IOException {
		List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetsLocations.size());
		for (String location : dataSetsLocations) {
			Resource resource = resourceLoader.getResource(location);
			dataSets.add(format.fromInputStream(resource.getInputStream()));
		}
		return dataSets;
	}
}
