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
package com.excilys.ebi.spring.dbunit.config;

import static com.excilys.ebi.spring.dbunit.utils.ApplicationContextUtils.getOptionalUniqueBeanOfType;

import java.io.IOException;

import org.dbunit.DatabaseUnitException;
import org.springframework.context.ApplicationContext;

import com.excilys.ebi.spring.dbunit.ConfigurationProcessor;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class ApplicationContextConfigurationProcessor implements ConfigurationProcessor<ApplicationContext> {

	private DataSetConfiguration defaultConfiguration = new DataSetConfiguration();

	public DataSetConfiguration getConfiguration(ApplicationContext applicationContext) throws IOException, DatabaseUnitException {
		return getOptionalUniqueBeanOfType(applicationContext, DataSetConfiguration.class, defaultConfiguration);
	}

	public DataSetConfiguration getDefaultConfiguration() {
		return defaultConfiguration;
	}

	public void setDefaultConfiguration(DataSetConfiguration defaultConfiguration) {
		this.defaultConfiguration = defaultConfiguration;
	}
}
