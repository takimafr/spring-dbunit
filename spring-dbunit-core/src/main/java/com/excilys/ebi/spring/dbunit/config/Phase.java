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
package com.excilys.ebi.spring.dbunit.config;

import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum Phase {

	SETUP {
		@Override
		public DatabaseOperation getOperation(DataSetConfiguration configuration) {
			if (configuration.getSetUpOperation().length == 1) {
				return configuration.getSetUpOperation()[0].getDatabaseOperation();
			} else {
				DatabaseOperation[] databaseOperations = new DatabaseOperation[configuration.getSetUpOperation().length];
				for (int i = 0; i < configuration.getSetUpOperation().length; i++) {
					databaseOperations[i] = configuration.getSetUpOperation()[i].getDatabaseOperation();
				}
				return new CompositeOperation(databaseOperations);
			}
		}
	},
	ROLLBACK {
		@Override
		public DatabaseOperation getOperation(DataSetConfiguration configuration) {
			if (configuration.getTearDownOperation().length == 1) {
				DBOperation op = configuration.getTearDownOperation()[0];
				if (op == DBOperation.NONE)
					op = DBOperation.DELETE_ALL;
				return op.getDatabaseOperation();
			} else {
				DatabaseOperation[] databaseOperations = new DatabaseOperation[configuration.getTearDownOperation().length];
				for (int i = 0; i < configuration.getTearDownOperation().length; i++) {
					databaseOperations[i] = configuration.getTearDownOperation()[i].getDatabaseOperation();
				}
				return new CompositeOperation(databaseOperations);
			}
		}
	},
	TEARDOWN {
		@Override
		public DatabaseOperation getOperation(DataSetConfiguration configuration) {
			if (configuration.getTearDownOperation().length == 1) {
				return configuration.getTearDownOperation()[0].getDatabaseOperation();
			} else {
				DatabaseOperation[] databaseOperations = new DatabaseOperation[configuration.getTearDownOperation().length];
				for (int i = 0; i < configuration.getTearDownOperation().length; i++) {
					databaseOperations[i] = configuration.getTearDownOperation()[i].getDatabaseOperation();
				}
				return new CompositeOperation(databaseOperations);
			}
		}
	};

	public abstract DatabaseOperation getOperation(DataSetConfiguration configuration);
}
