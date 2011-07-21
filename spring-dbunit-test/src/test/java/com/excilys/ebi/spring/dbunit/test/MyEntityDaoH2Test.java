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
package com.excilys.ebi.spring.dbunit.test;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.excilys.ebi.spring.dbunit.DBType;

/**
 * Tests with H2 in-memory database.
 * 
 * @author author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "applicationContext-test.xml", "applicationContext-test-h2.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(dbType = DBType.H2)
public class MyEntityDaoH2Test {

	@Autowired
	private IMyEntityDao myEntityDao;

	@Test
	public void testLoadAll() {
		List<MyEntity> entities = myEntityDao.loadAll();
		assertEquals(4, entities.size());
	}
}
