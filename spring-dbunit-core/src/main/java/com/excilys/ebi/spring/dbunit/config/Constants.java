package com.excilys.ebi.spring.dbunit.config;

public interface Constants {

	public static class ConfigurationDefaults {

		public static final DBOperation DEFAULT_SETUP_OPERATION = DBOperation.CLEAN_INSERT;

		public static final DBOperation DEFAULT_TEARDOWN_OPERATION = DBOperation.NONE;

		public static final DBType DEFAULT_DB_TYPE = DBType.HSQLDB;

		public static final DataSetFormat DEFAULT_DB_FORMAT = DataSetFormat.FLAT;

		public static final boolean DEFAULT_COLUMN_SENSING = false;

		public static final String DEFAULT_DTD_LOCATION = null;

		public static final boolean DEFAULT_DTD_METADATA = false;

		public static final boolean DEFAULT_CASE_SENSITIVE_TABLE_NAMES = false;

		public static final String DEFAULT_ESCAPE_PATTERN = "\"?\"";

		public static final int DEFAULT_BATCH_SIZE = 100;

		public static final int DEFAULT_FETCH_SIZE = 100;

		public static final boolean DEFAULT_QUALIFIED_TABLE_NAMES = false;

		public static final boolean DEFAULT_BATCHED_STATEMENTS = false;

		public static final boolean DEFAULT_SKIP_ORACLE_RECYCLEBIN_TABLES = false;

		public static final String[] DEFAULT_TABLE_TYPE = { "TABLE" };
	}
}
