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
package com.excilys.ebi.spring.dbunit.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.stream.StreamingDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.dataset.xml.XmlProducer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;

import com.excilys.ebi.spring.dbunit.dataset.xml.LinkedHashMapFlatDtdProducer;
import com.excilys.ebi.spring.dbunit.dataset.xml.flyweight.FlyWeightFlatXmlDataSetBuilder;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum DataSetFormat {

	/**
	 * @see {@link FlatXmlDataSet}.
	 */
	FLYWEIGHT_FLAT {

		/**
		 * {@inheritDoc}
		 */
		protected IDataSet fromResource(Resource resource, DataSetFormatOptions options) throws DataSetException, IOException {
			FlyWeightFlatXmlDataSetBuilder builder = new FlyWeightFlatXmlDataSetBuilder();
			builder.setColumnSensing(options.isColumnSensing());
			builder.setDtdMetadata(options.isDtdMetadata());
			builder.setCaseSensitiveTableNames(options.isCaseSensitiveTableNames());
			if (StringUtils.hasText(options.getDtdLocation())) {
				IDataSet metaDataSet = FLAT_DTD.loadUnique(null, options.getDtdLocation());
				builder.setMetaDataSet(metaDataSet);
			}
			return builder.build(resource.getInputStream());
		}
	},

	/**
	 * @see {@link FlatXmlDataSet}.
	 */
	FLAT {

		/**
		 * {@inheritDoc}
		 */
		protected IDataSet fromResource(Resource resource, DataSetFormatOptions options) throws DataSetException, IOException {
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(options.isColumnSensing());
			builder.setDtdMetadata(options.isDtdMetadata());
			builder.setCaseSensitiveTableNames(options.isCaseSensitiveTableNames());
			if (StringUtils.hasText(options.getDtdLocation())) {
				IDataSet metaDataSet = FLAT_DTD.loadUnique(null, options.getDtdLocation());
				builder.setMetaDataSet(metaDataSet);
			}
			return builder.build(resource.getInputStream());
		}
	},
	/**
	 * @see {@link XmlDataSet}.
	 */
	XML {

		/**
		 * {@inheritDoc}
		 */
		protected IDataSet fromResource(Resource resource, DataSetFormatOptions options) throws DataSetException, IOException {
			return new XmlDataSet(resource.getInputStream());
		}
	},
	/**
	 * @see {@link StreamingXmlDataSet}.
	 */
	STREAMING {

		/**
		 * {@inheritDoc}
		 */
		protected IDataSet fromResource(Resource resource, DataSetFormatOptions options) throws DataSetException, IOException {
			return new StreamingDataSet(new XmlProducer(new InputSource(resource.getInputStream())));
		}
	},

	FLAT_DTD {

		@Override
		protected IDataSet fromResource(Resource resource, DataSetFormatOptions options) throws DataSetException, IOException {
			return new FlatDtdDataSet(new LinkedHashMapFlatDtdProducer(new InputSource(resource.getInputStream())));
		}
	}, CSV{
        @Override
        protected IDataSet fromResource(Resource resource, DataSetFormatOptions options) throws DataSetException, IOException {
            return new CsvDataSet(resource.getFile());
        }
    };

	private static final ResourcePatternResolver RESOURCE_LOADER = new PathMatchingResourcePatternResolver();

	/**
	 * Returns a {@link IDataSet dataset} with the format for this enum
	 * 
	 * @param the
	 *            data file {@link InputStream}
	 * @param options
	 *            ths options
	 * @return a {@link IDataSet dataset}
	 * @throws DataSetException
	 *             DBUnit failure
	 * @throws IOException
	 *             I/O failure
	 */
	protected abstract IDataSet fromResource(Resource resource, DataSetFormatOptions options) throws DataSetException, IOException;

	public IDataSet loadUnique(DataSetFormatOptions options, String location) throws DataSetException, IOException {
		Resource resource = RESOURCE_LOADER.getResource(location);
		return fromResource(resource, options);
	}

	public List<IDataSet> loadMultiple(DataSetFormatOptions options, String[] locations) throws DataSetException, IOException {

		List<IDataSet> dataSets = new ArrayList<IDataSet>(locations.length);
		for (String location : locations) {
			Resource[] resources = RESOURCE_LOADER.getResources(location);
			for (Resource resource : resources) {
				dataSets.add(fromResource(resource, options));
			}
		}
		return dataSets;
	}
}
