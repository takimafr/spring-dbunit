/**
 * Copyright 2011-2012 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.spring.dbunit.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.springframework.core.DecoratingClassLoader;

import com.excilys.ebi.spring.dbunit.config.Constants.ConfigurationDefaults;
import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.config.DataSetFormat;
import com.excilys.ebi.spring.dbunit.dataset.DataSetDecorator;

/**
 * Indicates that a test class or a test method has to load and purge the
 * database with a DBUnit dataset
 * 
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataSet {

	/**
	 * alias for locations
	 */
	String[] value() default {};

	/**
	 * @return Dataset files locations
	 */
	String[] locations() default {};

	/**
	 * DBUnit operation on setup
	 * 
	 * @return default : {@link DBOperation#CLEAN_INSERT}
	 */
	DBOperation[] setUpOperation() default DBOperation.CLEAN_INSERT;

	/**
	 * DBUnit operation on teardown
	 * 
	 * @return (default @link DBOperation#NONE}
	 */
	DBOperation[] tearDownOperation() default DBOperation.NONE;

	/**
	 * {@link IDataSet} file format
	 * 
	 * @return default {@link DataSetFormat#FLAT}
	 */
	DataSetFormat format() default DataSetFormat.FLAT;

	/**
	 * database type
	 * 
	 * @return default {@link DBType#HSQLDB}
	 */
	DBType dbType() default DBType.HSQLDB;

	/**
	 * @return DataSource name in the Spring Context. If empty, expect one and
	 *         only one DataSource in the Spring Context.
	 */
	String dataSourceSpringName() default "";

	/**
	 * @see com.excilys.ebi.spring.dbunit.dataset.xml.flyweight.FlyWeightFlatXmlDataSetBuilder#setColumnSensing(boolean)
	 * @return default {@link ConfigurationDefaults.DEFAULT_COLUMN_SENSING}
	 */
	boolean columnSensing() default ConfigurationDefaults.DEFAULT_COLUMN_SENSING;

	/**
	 * @see com.excilys.ebi.spring.dbunit.dataset.xml.flyweight.FlyWeightFlatXmlDataSetBuilder#setMetaDataSet(IDataSet)
	 * @return default empty
	 */
	String dtdLocation() default "";

	/**
	 * @see com.excilys.ebi.spring.dbunit.dataset.xml.flyweight.FlyWeightFlatXmlDataSetBuilder#setDtdMetadata(boolean)
	 * @return default {@link ConfigurationDefaults.DEFAULT_DTD_METADATA}
	 */
	boolean dtdMetadata() default ConfigurationDefaults.DEFAULT_DTD_METADATA;

	/**
	 * @see DatabaseConfig#FEATURE_CASE_SENSITIVE_TABLE_NAMES
	 * @return default
	 *         {@link ConfigurationDefaults.DEFAULT_CASE_SENSITIVE_TABLE_NAMES}
	 */
	boolean caseSensitiveTableNames() default ConfigurationDefaults.DEFAULT_CASE_SENSITIVE_TABLE_NAMES;

	/**
	 * @see DatabaseConfig#PROPERTY_ESCAPE_PATTERN
	 * @return default {@link ConfigurationDefaults.DEFAULT_ESCAPE_PATTERN}
	 */
	String escapePattern() default ConfigurationDefaults.DEFAULT_ESCAPE_PATTERN;

	/**
	 * @see DatabaseConfig#PROPERTY_BATCH_SIZE
	 * @return default {@link ConfigurationDefaults.DEFAULT_BATCH_SIZE}
	 */
	int batchSize() default ConfigurationDefaults.DEFAULT_BATCH_SIZE;

	/**
	 * @see DatabaseConfig#PROPERTY_FETCH_SIZE
	 * @return default {@link ConfigurationDefaults.DEFAULT_FETCH_SIZE}
	 */
	int fetchSize() default ConfigurationDefaults.DEFAULT_FETCH_SIZE;

	/**
	 * @see DatabaseConfig#FEATURE_QUALIFIED_TABLE_NAMES
	 * @return default
	 *         {@link ConfigurationDefaults.DEFAULT_QUALIFIED_TABLE_NAMES}
	 */
	boolean qualifiedTableNames() default ConfigurationDefaults.DEFAULT_QUALIFIED_TABLE_NAMES;

	/**
	 * @see DatabaseConfig#FEATURE_BATCHED_STATEMENTS
	 * @return default {@link ConfigurationDefaults.DEFAULT_BATCHED_STATEMENTS}
	 */
	boolean batchedStatements() default ConfigurationDefaults.DEFAULT_BATCHED_STATEMENTS;

	/**
	 * @see DatabaseConfig#FEATURE_SKIP_ORACLE_RECYCLEBIN_TABLES
	 * @return default
	 *         {@link ConfigurationDefaults.DEFAULT_SKIP_ORACLE_RECYCLEBIN_TABLES}
	 */
	boolean skipOracleRecycleBinTables() default ConfigurationDefaults.DEFAULT_SKIP_ORACLE_RECYCLEBIN_TABLES;

	/**
	 * @see DatabaseConfig#PROPERTY_TABLE_TYPE
	 * @return default {@link ConfigurationDefaults.DEFAULT_TABLE_TYPE}
	 */
	String[] tableType() default { "TABLE" };
	
	/**
	 * @return the schema
	 */
	String schema() default "";

	/**
	 * @return decorators to be applied on the dataset
	 */
	Class<? extends DataSetDecorator>[] decorators() default {};
}
