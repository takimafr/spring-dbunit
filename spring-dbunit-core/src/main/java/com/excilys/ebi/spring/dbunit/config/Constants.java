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
