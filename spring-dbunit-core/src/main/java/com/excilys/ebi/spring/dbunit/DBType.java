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
package com.excilys.ebi.spring.dbunit;

import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mckoi.MckoiDataTypeFactory;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.netezza.NetezzaDataTypeFactory;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum DBType {

	DB2(Db2DataTypeFactory.class), /**/
	HSQLDB(HsqldbDataTypeFactory.class), /**/
	H2(H2DataTypeFactory.class), /**/
	MCKOY(MckoiDataTypeFactory.class), /**/
	MSSQL(MsSqlDataTypeFactory.class), /**/
	MYSQL(MySqlDataTypeFactory.class), /**/
	NETEZZA(NetezzaDataTypeFactory.class), /**/
	ORACLE(OracleDataTypeFactory.class), /**/
	ORACLE10(Oracle10DataTypeFactory.class), /**/
	POSTGRESQL(PostgresqlDataTypeFactory.class);

	private final Class<? extends IDataTypeFactory> dataTypeFactoryClass;

	private IDataTypeFactory dataTypeFactory;

	private DBType(Class<? extends IDataTypeFactory> dataTypeFactoryClass) {
		this.dataTypeFactoryClass = dataTypeFactoryClass;
	}

	public IDataTypeFactory getDataTypeFactory() {

		// doesn't really matter if it's not synchronized...
		if (dataTypeFactory == null) {
			try {
				dataTypeFactory = dataTypeFactoryClass.newInstance();
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		}

		return dataTypeFactory;
	}
}
