package com.excilys.ebi.spring.dbunit.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;

import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.config.DataSetFormat;
import com.excilys.ebi.spring.dbunit.config.Constants.ConfigurationDefaults;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExpectedDataSet {

	/**
	 * alias for locations
	 */
	String[] value() default {};

	/**
	 * @return Dataset files locations
	 */
	String[] locations() default {};

	/**
	 * Columns to ignore.
	 */
	String[] columnsToIgnore() default {};

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
	 * @see DatabaseConfig#FEATURE_QUALIFIED_TABLE_NAMES
	 * @return default
	 *         {@link ConfigurationDefaults.DEFAULT_QUALIFIED_TABLE_NAMES}
	 */
	boolean qualifiedTableNames() default ConfigurationDefaults.DEFAULT_QUALIFIED_TABLE_NAMES;

	/**
	 * @see DatabaseConfig#PROPERTY_TABLE_TYPE
	 * @return default {@link ConfigurationDefaults.DEFAULT_TABLE_TYPE}
	 */
	String[] tableType() default { "TABLE" };
	
	/**
	 * @return the schema
	 */
	String schema() default "";

}
