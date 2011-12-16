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

import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.Constants;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum DBOperation {

	/** Nothing, @see {@link DatabaseOperation.#NONE} */
	NONE,

	/** @see {@link DatabaseOperation#UPDATE} */
	UPDATE,

	/** @see {@link DatabaseOperation#INSERT} */
	INSERT,

	/** @see {@link DatabaseOperation#REFRESH} */
	REFRESH,

	/** @see {@link DatabaseOperation#DELETE} */
	DELETE,

	/** @see {@link DatabaseOperation#DELETE_ALL} */
	DELETE_ALL,

	/** @see {@link DatabaseOperation#TRUNCATE_TABLE} */
	TRUNCATE_TABLE,

	/** @see {@link DatabaseOperation#CLEAN_INSERT} */
	CLEAN_INSERT;

	/** Internal representation of the {@link DatabaseOperation} constants */
	private final transient Constants operations = new Constants(DatabaseOperation.class);

	/**
	 * Convert this enum into {@link DatabaseOperation operation database}.
	 * 
	 * @return The DatabaseOperation for this enum
	 */
	public DatabaseOperation getDatabaseOperation() {
		return DatabaseOperation.class.cast(operations.asObject(name()));
	}
}
