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
/**
 * 
 */
package com.excilys.ebi.spring.dbunit.test;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.excilys.ebi.spring.dbunit.ConfigurationProcessor;
import com.excilys.ebi.spring.dbunit.DataLoader;
import com.excilys.ebi.spring.dbunit.DefaultDataLoader;
import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.Phase;

/**
 * Listener that can load data at class level (for general data test) and
 * method level (for specific data test).
 * 
 * <p>
 * 	 <i>
 *		 <b>Notice:</b>
 *		 this listener has to be used for tests in transactional context with
 *		 a rollback strategy after test method.
 *	 </i>					
 * </p>
 * 
 * @author <a href="mailto:pcavezzan@gmail.com">Patrice CAVEZZAN</a>
 */
public class RollbackTransactionalDataSetTestExecutionListener extends AbstractTestExecutionListener {
	private final DataLoader dataLoader = new DefaultDataLoader();
	private final ConfigurationProcessor<TestContext> configurationProcessor = new RollbackTransactionalTestConfigurationProcessor();
	private DataSetConfiguration configuration;

	/* (non-Javadoc)
	 * @see com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener#beforeTestMethod(org.springframework.test.context.TestContext)
	 */
	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		configuration = configurationProcessor.getConfiguration(testContext);
		dataLoader.execute(testContext.getApplicationContext(), configuration, Phase.SETUP);
	}

	/* (non-Javadoc)
	 * @see com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener#afterTestMethod(org.springframework.test.context.TestContext)
	 */
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		configuration = configurationProcessor.getConfiguration(testContext);
		dataLoader.execute(testContext.getApplicationContext(), configuration, Phase.TEARDOWN);
	}

	/* (non-Javadoc)
	 * @see org.springframework.test.context.support.AbstractTestExecutionListener#beforeTestClass(org.springframework.test.context.TestContext)
	 */
	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		configuration = configurationProcessor.getConfiguration(testContext);
		dataLoader.execute(testContext.getApplicationContext(), configuration, Phase.SETUP);
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		configuration = configurationProcessor.getConfiguration(testContext);
		dataLoader.execute(testContext.getApplicationContext(), configuration, Phase.TEARDOWN);
	}
	
}
