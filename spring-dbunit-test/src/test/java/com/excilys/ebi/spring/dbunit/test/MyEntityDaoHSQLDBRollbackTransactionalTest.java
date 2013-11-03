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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import com.excilys.ebi.spring.dbunit.config.DataSetFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests with HSQLDB in-memory database.
 * 
 * @author author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "applicationContext-test-transactional.xml", "applicationContext-test-hsqldb.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, RollbackTransactionalDataSetTestExecutionListener.class })
@TransactionConfiguration
@Transactional
@DataSet
public class MyEntityDaoHSQLDBRollbackTransactionalTest {

	@Autowired
	private IMyEntityDao myEntityDao;

	@Test
	public void testInsert() {
		MyEntity myEntity = new MyEntity();
		myEntity.setId("id2000");
		myEntity.setName("name2000");
		myEntityDao.saveOrUpdate(myEntity);
		List<MyEntity> entities = myEntityDao.loadAll();
		assertEquals(5, entities.size());
	}

	@Test
	public void testDeleteById() {
		myEntityDao.deleteById("id1");
		List<MyEntity> entities = myEntityDao.loadAll();
		assertEquals(3, entities.size());
	}

	@Test
	public void testLoadAll() {
		List<MyEntity> entities = myEntityDao.loadAll();
		assertEquals(4, entities.size());
	}

	@Test
	public void testGetById() {
		MyEntity entity = myEntityDao.getById("id1");
		assertNotNull("entity with id=id1 not found", entity);
		assertEquals("name1", entity.getName());
	}

	@Test
	@DataSet("dataSet2.xml")
	public void testGetByIdMethodOverride() {
		MyEntity entity = myEntityDao.getById("id1");
		assertNull("entity with id=id1 should not have been found", entity);

		entity = myEntityDao.getById("foo1");
		assertNotNull("entity with id=foo1 not found", entity);
		assertEquals("bar1", entity.getName());
	}

	@Test
	@DataSet(locations = { "dataSet.xml", "dataSet2.xml" })
	public void testMultipleDataSets() {
		MyEntity entity = myEntityDao.getById("id1");
		assertNotNull("entity with id=id1 not found", entity);
		assertEquals("name1", entity.getName());

		entity = myEntityDao.getById("foo1");
		assertNotNull("entity with id=foo1 not found", entity);
		assertEquals("bar1", entity.getName());
	}

	@Test
	public void testGetByName() {
		MyEntity entity = myEntityDao.getByName("name1");
		assertNotNull("entity with name=name1 not found", entity);
		assertEquals("id1", entity.getId());
	}

	@Test
	@DataSet(format = DataSetFormat.CSV, locations = { "dataSet3" })
	public void testLoadAllCsv() {
		List<MyEntity> entities = myEntityDao.loadAll();
		assertEquals(2, entities.size());
	}

}
