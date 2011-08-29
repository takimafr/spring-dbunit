package com.excilys.ebi.spring.dbunit.servlet;

import java.util.ArrayList;
import java.util.List;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.config.DataSetFormat;

public abstract class ServletConfigurationConstants {

	private ServletConfigurationConstants() {
		throw new UnsupportedOperationException();
	}

	public abstract static class InitParams {

		public static final String SPRING_DBUNIT_INIT_PARAM_PREFIX = "spring.dbunit.";

		public static final String DATASETS_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dataSets";

		public static final String SETUP_OPERATION_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "setUpOperation";

		public static final String TEARDOWN_OPERATION_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "tearDownOperation";

		public static final String FORMAT_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "format";

		public static final String DB_TYPE_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dbType";

		public static final String DATASOURCE_SPRING_NAME_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dataSourceSpringName";

		public static final String COLUMN_SENSING_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "columnSensing";

		public static final String DTD_METADATA_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dtdMetadata";

		public static final String CASE_SENSITIVE_TABLE_NAMES_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "caseSensitiveTableNames";

		public static final String DTD_LOCATION_INIT_PARAM = SPRING_DBUNIT_INIT_PARAM_PREFIX + "dtd";

		private InitParams() {
			throw new UnsupportedOperationException();
		}
	}

	public abstract static class Defaults {

		public static final List<String> DEFAULT_DATASET;

		public static final DataSetFormat DEFAULT_DATASET_FORMAT = DataSetFormat.FLAT;

		public static final DBOperation DEFAULT_SETUP_OPERATION = DBOperation.CLEAN_INSERT;

		public static final DBOperation DEFAULT_TEARDOWN_OPERATION = DBOperation.NONE;

		public static final DBType DEFAULT_DB_TYPE = DBType.HSQLDB;

		public static final String DEFAULT_DATASOURCE_SPRING_NAME = null;

		public static final Boolean DEFAULT_COLUMN_SENSING = Boolean.FALSE;

		public static final Boolean DEFAULT_DTD_METADATA = Boolean.FALSE;

		public static final Boolean DEFAULT_CASE_SENSITIVE_TABLE_NAMES = Boolean.FALSE;

		public static final String DEFAULT_DTD_LOCATION = null;

		static {
			DEFAULT_DATASET = new ArrayList<String>(1);
			DEFAULT_DATASET.add("classpath:dataSet.xml");
		}

		private Defaults() {
			throw new UnsupportedOperationException();
		}
	}
}
