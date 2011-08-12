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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum Phase {

	SETUP {
		@Override
		public DatabaseOperation getOperation(DataSetConfiguration configuration) {
			return configuration.getSetUpOperation();
		}

		@Override
		public List<IDataSet> getDataSets(DataSetConfiguration configuration) {
			return configuration.getDataSets();
		}
	}

	,
	TEARDOWN {
		@Override
		public DatabaseOperation getOperation(DataSetConfiguration configuration) {
			return configuration.getTearDownOperation();
		}

		@Override
		public List<IDataSet> getDataSets(DataSetConfiguration configuration) {

			List<IDataSet> dataSets = new ArrayList<IDataSet>(configuration.getDataSets());
			Collections.reverse(dataSets);
			return dataSets;
		}
	};

	public abstract DatabaseOperation getOperation(DataSetConfiguration configuration);

	public abstract List<IDataSet> getDataSets(DataSetConfiguration configuration);
}
