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

import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DataSetConfiguration {

	private final List<IDataSet> dataSets;

	private final String dataSourceSpringName;

	private final DBOp setUpOp;

	private final DBOp tearDownOp;

	private final DBType dbType;

	public DataSetConfiguration(List<IDataSet> dataSets, String dataSourceSpringName, DBOp setUpOp, DBOp tearDownOp, DBType dbType) {
		this.dataSets = dataSets;
		this.dataSourceSpringName = dataSourceSpringName;
		this.setUpOp = setUpOp;
		this.tearDownOp = tearDownOp;
		this.dbType = dbType;
	}

	public List<IDataSet> getDataSets() {
		return dataSets;
	}

	public String getDataSourceSpringName() {
		return dataSourceSpringName;
	}

	public IDataTypeFactory getDataTypeFactory() {
		return dbType.getDataTypeFactory();
	}

	public DatabaseOperation getSetUpOperation() {
		return setUpOp.getDatabaseOperation();
	}

	public DatabaseOperation getTearDownOperation() {
		return tearDownOp.getDatabaseOperation();
	}
}
