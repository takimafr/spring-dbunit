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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.TestContext;
import org.springframework.util.ObjectUtils;

import com.excilys.ebi.spring.dbunit.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.test.conventions.ConfigurationConventions;
import com.excilys.ebi.spring.dbunit.test.conventions.DefaultConfigurationConventions;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class TestConfigurationProcessor {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestConfigurationProcessor.class);

	private final ConfigurationConventions conventions;

	private final ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * A configuration cache used between setup and teardown
	 */
	private final Map<Method, DataSetConfiguration> configurationCache = Collections.synchronizedMap(new IdentityHashMap<Method, DataSetConfiguration>());

	/**
	 * Configure with default conventions
	 */
	public TestConfigurationProcessor() {
		conventions = new DefaultConfigurationConventions();
	}

	/**
	 * Configure with custom conventions
	 * 
	 * @param conventions
	 */
	public TestConfigurationProcessor(ConfigurationConventions conventions) {
		this.conventions = conventions;
	}

	/**
	 * @param testContext
	 *            the context
	 * @return the configuration
	 * @throws IOException
	 *             I/O failureDataSet
	 * @throws DatabaseUnitException
	 *             DBUnit failure
	 */
	public DataSetConfiguration getConfiguration(TestContext testContext) throws IOException, DatabaseUnitException {

		DataSetConfiguration configuration = configurationCache.get(testContext.getTestMethod());

		if (configuration == null) {
			// no cached configuration --> instancing it
			DataSet dataSetAnnotation = findAnnotation(testContext.getTestMethod(), testContext.getTestClass(), DataSet.class);

			if (dataSetAnnotation != null) {
				configuration = buildConfiguration(dataSetAnnotation, testContext);
				configurationCache.put(testContext.getTestMethod(), configuration);

			} else {
				LOGGER.info("DataSetTestExecutionListener was configured but without any DataSet or DataSets! DataSet features are disabled");
			}
		}

		return configuration;
	}

	private DataSetConfiguration buildConfiguration(DataSet dataSetAnnotation, TestContext testContext) throws DataSetException, IOException {
		List<IDataSet> dataSets = buildDataSets(dataSetAnnotation, testContext);
		String dataSourceSpringName = dataSetAnnotation.dataSourceSpringName();
		DatabaseOperation setUpOperation = dataSetAnnotation.setUpOperation().getDatabaseOperation();
		DatabaseOperation tearDownOperation = dataSetAnnotation.tearDownOperation().getDatabaseOperation();
		IDataTypeFactory dataTypeFactory = dataSetAnnotation.dbType().getDataTypeFactory();

		return new DataSetConfiguration(dataSets, dataSourceSpringName, setUpOperation, tearDownOperation, dataTypeFactory);
	}

	/**
	 * Build a DataSet from the informations passed in the annotation
	 * 
	 * @param testContext
	 *            the context
	 * @return the datasets
	 * @throws IOException
	 *             I/O failure
	 * @throws DatabaseUnitException
	 *             DBUnit failure
	 */
	private List<IDataSet> buildDataSets(DataSet annotation, TestContext testContext) throws DataSetException, IOException {

		String[] valueLocations = annotation.value();
		String[] locations = annotation.locations();
		if (!ObjectUtils.isEmpty(valueLocations)) {
			locations = valueLocations;
		}

		List<Resource> dataSetResources = getResourcesByConventions(locations, testContext);
		List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetResources.size());
		for (Resource resource : dataSetResources) {
			dataSets.add(annotation.format().fromInputStream(resource.getInputStream()));
		}

		return dataSets;
	}

	/**
	 * @param locations
	 *            the possibly numerous files
	 * @param testContext
	 *            the context
	 * @return an Inputstream, possibly the concatenation of several files
	 * @throws IOException
	 *             I/O failure
	 */
	private List<Resource> getResourcesByConventions(String[] locations, TestContext testContext) throws IOException {

		List<String> dataSetResourceLocations = conventions.getDataSetResourcesLocations(testContext.getTestClass(), locations);

		List<Resource> dataSetResources = new ArrayList<Resource>(dataSetResourceLocations.size());

		for (String dataSetResourceLocation : dataSetResourceLocations) {
			LOGGER.debug("Loading DataSet file '{}'", dataSetResourceLocation);
			Resource dataSetResource = resourceLoader.getResource(dataSetResourceLocation);
			dataSetResources.add(dataSetResource);
		}

		return dataSetResources;
	}

	/**
	 * @param method
	 *            the test method
	 * @param clazz
	 *            the test class
	 * @return the {@link DataSet} at method level if found, otherwise at class
	 *         level
	 */
	private <A extends Annotation> A findAnnotation(Method method, Class<?> clazz, Class<A> annotationType) {
		A annotation = AnnotationUtils.findAnnotation(method, annotationType);
		return annotation == null ? annotation = AnnotationUtils.findAnnotation(clazz, annotationType) : annotation;
	}
}
