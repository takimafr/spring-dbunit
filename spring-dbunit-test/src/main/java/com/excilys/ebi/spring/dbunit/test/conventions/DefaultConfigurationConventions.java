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
package com.excilys.ebi.spring.dbunit.test.conventions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

public class DefaultConfigurationConventions implements ConfigurationConventions {

	/**
	 * Default resource name = "dataSet.xml"
	 */
	private static final String DEFAULT_RESOURCE_NAME = "dataSet.xml";

	/**
	 * If the supplied <code>location</code> is <code>null</code> or
	 * <em>empty</em>, a default location will be
	 * {@link #getDefaultLocationByConventions(Class) generated} for the
	 * specified {@link Class class} and the {@link #getResourceName resource
	 * file name} ; otherwise, the supplied <code>location</code> will be
	 * {@link #modifyLocation modified} if necessary and returned.
	 * 
	 * @param clazz
	 *            the class with which the location is associated: to be used
	 *            when generating a default location
	 * @param locations
	 *            the raw locations to use for loading the DataSet (can be
	 *            <code>null</code> or empty)
	 * @return DataSets locations
	 * @see #generateDefaultLocation
	 * @see #modifyLocation
	 */
	public List<String> getDataSetResourcesLocations(Class<?> clazz, String[] locations) {

		if (ObjectUtils.isEmpty(locations)) {
			locations = new String[] { null };
		}

		List<String> resourceLocations = new ArrayList<String>(locations.length);

		for (String location : locations) {
			String resourceLocation = !StringUtils.hasLength(location) ? getDefaultLocationByConventions(clazz) : getRealLocationByConventions(clazz, location);
			resourceLocations.add(resourceLocation);
		}

		return resourceLocations;
	}

	/**
	 * Generate a modified version of the supplied location and returns it.
	 * <p>
	 * A plain path, e.g. &quot;context.xml&quot;, will be treated as a
	 * classpath resource from the same package in which the specified class is
	 * defined. A path starting with a slash is treated as a fully qualified
	 * class path location, e.g.: &quot;/com/example/whatever/foo.xml&quot;. A
	 * path which references a URL (e.g., a path prefixed with
	 * {@link ResourceUtils#CLASSPATH_URL_PREFIX classpath:},
	 * {@link ResourceUtils#FILE_URL_PREFIX file:}, <code>http:</code>, etc.)
	 * will be added to the results unchanged.
	 * <p>
	 * Subclasses can override this method to implement a different
	 * <em>location modification</em> strategy.
	 * 
	 * @param clazz
	 *            the class with which the locations are associated
	 * @param locations
	 *            the resource location to be modified
	 * @return the modified application context resource location
	 */
	private String getRealLocationByConventions(Class<?> clazz, String location) {
		String modifiedLocation = null;
		if (location.startsWith("/")) {
			modifiedLocation = ResourceUtils.CLASSPATH_URL_PREFIX + location;
		} else if (!ResourcePatternUtils.isUrl(location)) {
			modifiedLocation = ResourceUtils.CLASSPATH_URL_PREFIX + StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(clazz) + "/" + location);
		} else {
			modifiedLocation = StringUtils.cleanPath(location);
		}
		return modifiedLocation;
	}

	/**
	 * Generates the default classpath resource location based on the supplied
	 * class.
	 * <p>
	 * For example, if the supplied class is <code>com.example.MyTest</code>,
	 * the generated location will be a string with a value of
	 * &quot;classpath:/com/example/<code>&lt;suffix&gt;</code>&quot;, where
	 * <code>&lt;suffix&gt;</code> is the value of the
	 * {@link #getResourceName() resource name} string.
	 * <p>
	 * Subclasses can override this method to implement a different
	 * <em>default location generation</em> strategy.
	 * 
	 * @param clazz
	 *            the class for which the default locations are to be generated
	 * @return an array of default application context resource locations
	 * @see #getResourceSuffix()
	 */
	private String getDefaultLocationByConventions(Class<?> clazz) {
		Assert.notNull(clazz, "Class must not be null");
		return ResourceUtils.CLASSPATH_URL_PREFIX + StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(clazz) + "/" + DEFAULT_RESOURCE_NAME);
	}
}
