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
package com.excilys.ebi.spring.dbunit.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.ebi.spring.dbunit.DataLoader;
import com.excilys.ebi.spring.dbunit.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.DataSetFunction;
import com.excilys.ebi.spring.dbunit.DefaultDataLoader;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DataLoaderListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataLoaderListener.class);

	private DataLoader dataLoader = new DefaultDataLoader();
	private ServletConfigurationProcessor configurationProcessor = new ServletConfigurationProcessor();
	private DataSetConfiguration configuration = null;

	public void contextInitialized(ServletContextEvent sce) {

		ServletContext sc = sce.getServletContext();

		try {
			configuration = configurationProcessor.getConfiguration(sc);
			doWithDataSet(sc, new DataSetFunction() {
				public DatabaseOperation getOperation(DataSetConfiguration configuration) {
					return configuration.getSetUpOperation();
				}
			});

		} catch (Exception e) {
			LOGGER.error("Error while initializing DBUnit data", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {

		ServletContext sc = sce.getServletContext();

		try {
			doWithDataSet(sc, new DataSetFunction() {
				public DatabaseOperation getOperation(DataSetConfiguration configuration) {
					return configuration.getTearDownOperation();
				}
			});

		} catch (Exception e) {
			LOGGER.error("Error while cleaning up DBUnit data", e);
			throw new RuntimeException(e);
		}
	}

	private void doWithDataSet(ServletContext sc, DataSetFunction function) throws Exception {
		dataLoader.doWithDataSet(WebApplicationContextUtils.getWebApplicationContext(sc), configuration, function);
	}
}
