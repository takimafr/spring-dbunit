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

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.excilys.ebi.spring.dbunit.DataLoader;
import com.excilys.ebi.spring.dbunit.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.DefaultDataLoader;
import com.excilys.ebi.spring.dbunit.Phase;

/**
 * Spring test framework TestExecutionListener for executing DBUnit operations
 * on JUnit tests setup and teardown. A typical use case is to load a DataSet on
 * setUp and revert the changes on teardown.
 * 
 * It looks for a {@DataSet} annotation to instanciate the
 * configuration.
 * 
 * @see #processLocation for conventions on how the resource is loaded
 * 
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DataSetTestExecutionListener extends AbstractTestExecutionListener {

	private TestConfigurationProcessor configurationProcessor = new TestConfigurationProcessor();

	private DataLoader dataLoader = new DefaultDataLoader();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		doWithDataSet(testContext, Phase.SETUP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		doWithDataSet(testContext, Phase.TEARDOWN);
	}

	private void doWithDataSet(TestContext testContext, Phase phase) throws Exception {
		DataSetConfiguration configuration = configurationProcessor.getConfiguration(testContext);
		dataLoader.doWithDataSet(testContext.getApplicationContext(), configuration, phase);
	}
}
