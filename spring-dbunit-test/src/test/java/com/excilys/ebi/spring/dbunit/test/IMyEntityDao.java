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

import java.util.List;

/**
 * Interafec of the test DAO
 * 
 * @author author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public interface IMyEntityDao {

	List<MyEntity> loadAll();

	MyEntity getById(String id);

	MyEntity getByName(final String name);

	void saveOrUpdate(MyEntity entity);

	void delete(MyEntity entity);

	void deleteById(final String id);
}