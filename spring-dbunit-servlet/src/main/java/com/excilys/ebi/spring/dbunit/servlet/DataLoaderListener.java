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
package com.excilys.ebi.spring.dbunit.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.ebi.spring.dbunit.ConfigurationProcessor;
import com.excilys.ebi.spring.dbunit.DataLoader;
import com.excilys.ebi.spring.dbunit.DefaultDataLoader;
import com.excilys.ebi.spring.dbunit.config.ApplicationContextConfigurationProcessor;
import com.excilys.ebi.spring.dbunit.config.DataSetConfiguration;
import com.excilys.ebi.spring.dbunit.config.Phase;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class DataLoaderListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataLoaderListener.class);

	private DataLoader dataLoader = new DefaultDataLoader();
	private ConfigurationProcessor<ApplicationContext> configurationProcessor = new ApplicationContextConfigurationProcessor();

	private ApplicationContext context;
	private DataSetConfiguration configuration;

	public void contextInitialized(ServletContextEvent sce) {

		context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

		try {
			configuration = configurationProcessor.getConfiguration(context);
			dataLoader.execute(context, configuration, Phase.SETUP);

		} catch (Exception e) {
			LOGGER.error("Error while initializing DbUnit data", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {

		try {
			dataLoader.execute(context, configuration, Phase.TEARDOWN);

		} catch (Exception e) {
			LOGGER.error("Error while cleaning up DbUnit data", e);
			throw new RuntimeException(e);
		}
	}
}
