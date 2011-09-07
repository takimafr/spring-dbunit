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

import org.dbunit.database.DefaultMetadataHandler;
import org.dbunit.database.IMetadataHandler;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.db2.Db2MetadataHandler;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mckoi.MckoiDataTypeFactory;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.ext.netezza.NetezzaDataTypeFactory;
import org.dbunit.ext.netezza.NetezzaMetadataHandler;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum DBType {

	DB2(Db2DataTypeFactory.class, Db2MetadataHandler.class), /**/
	HSQLDB(HsqldbDataTypeFactory.class, DefaultMetadataHandler.class), /**/
	H2(H2DataTypeFactory.class, DefaultMetadataHandler.class), /**/
	MCKOY(MckoiDataTypeFactory.class, DefaultMetadataHandler.class), /**/
	MSSQL(MsSqlDataTypeFactory.class, DefaultMetadataHandler.class), /**/
	MYSQL(MySqlDataTypeFactory.class, MySqlMetadataHandler.class), /**/
	NETEZZA(NetezzaDataTypeFactory.class, NetezzaMetadataHandler.class), /**/
	ORACLE(OracleDataTypeFactory.class, DefaultMetadataHandler.class), /**/
	ORACLE10(Oracle10DataTypeFactory.class, DefaultMetadataHandler.class), /**/
	POSTGRESQL(PostgresqlDataTypeFactory.class, DefaultMetadataHandler.class);

	private final Class<? extends IDataTypeFactory> dataTypeFactoryClass;
	private final Class<? extends IMetadataHandler> metadataHandlerClass;

	private IDataTypeFactory dataTypeFactory;

	private IMetadataHandler metadataHandler;

	private DBType(Class<? extends IDataTypeFactory> dataTypeFactoryClass, Class<? extends IMetadataHandler> metadataHandlerClass) {
		this.dataTypeFactoryClass = dataTypeFactoryClass;
		this.metadataHandlerClass = metadataHandlerClass;
	}

	IDataTypeFactory getDataTypeFactory() {

		// doesn't really matter if it's not synchronized...
		if (dataTypeFactory == null) {
			dataTypeFactory = getInstance(dataTypeFactoryClass);
		}
		return dataTypeFactory;
	}

	IMetadataHandler getMetadataHandler() {

		// doesn't really matter if it's not synchronized...
		if (metadataHandler == null) {
			metadataHandler = getInstance(metadataHandlerClass);
		}
		return metadataHandler;
	}

	private <T> T getInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
}
