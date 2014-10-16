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

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.excilys.ebi.spring.dbunit.ConfigurationProcessor;
import com.excilys.ebi.spring.dbunit.DataLoader;
import com.excilys.ebi.spring.dbunit.DataReader;
import com.excilys.ebi.spring.dbunit.DefaultDataLoader;
import com.excilys.ebi.spring.dbunit.DefaultDataReader;
import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.ExpectedDataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.Phase;

/**
 * Spring test framework TestExecutionListener for executing DBUnit operations on JUnit tests setup and teardown. A
 * typical use case is to load a DataSet on setUp and revert the changes on teardown.
 *
 * It looks for a {@DataSet} annotation to instanciate the configuration.
 *
 * @see #processLocation for conventions on how the resource is loaded
 *
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DataSetTestExecutionListener extends AbstractTestExecutionListener {

    protected DataLoader dataLoader = new DefaultDataLoader();

    protected DataReader dataReader = new DefaultDataReader();

    protected ConfigurationProcessor<TestContext> configurationProcessor = new TestConfigurationProcessor();

    protected DataSetConfiguration getConfiguration(TestContext testContext) throws Exception {
        return configurationProcessor.getConfiguration(testContext);
    }

    protected ExpectedDataSetConfiguration getExpectedConfiguration(TestContext testContext) {
        return configurationProcessor.getExpectedConfiguration(testContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        dataLoader.execute(testContext.getApplicationContext(), getConfiguration(testContext), Phase.SETUP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        try {
            checkDatas(testContext);
        } finally {
            dataLoader.execute(testContext.getApplicationContext(), getConfiguration(testContext), Phase.TEARDOWN);
        }
    }

    private void checkDatas(TestContext testContext) throws Exception {
        ExpectedDataSetConfiguration expectedConfiguration = getExpectedConfiguration(testContext);
        if (expectedConfiguration != null) {
            IDataSet expectedDataSet = expectedConfiguration.getDataSet();
            for (String tableName : expectedDataSet.getTableNames()) {
                IDataSet dataSet = dataReader.execute(testContext.getApplicationContext(), getExpectedConfiguration(testContext), tableName);
                String[] columnsToIgnore = expectedConfiguration.getColumnsToIgnore();
                if (columnsToIgnore == null || columnsToIgnore.length == 0) {
                    Assertion.assertEquals(expectedDataSet.getTable(tableName), dataSet.getTable(tableName));
                } else {
                    Assertion.assertEqualsIgnoreCols(expectedDataSet.getTable(tableName), dataSet.getTable(tableName), columnsToIgnore);
                }
            }
        }
    }
}
