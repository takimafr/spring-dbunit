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

import java.io.IOException;
import java.io.InputStream;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.stream.StreamingDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.dataset.xml.XmlProducer;
import org.xml.sax.InputSource;

/**
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public enum DataSetFormat {

	/**
	 * @see {@link FlatXmlDataSet}.
	 */
	FLAT {

		/**
		 * {@inheritDoc}
		 */
		public IDataSet fromInputStream(final InputStream in) throws DataSetException, IOException {
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(true);
			return builder.build(in);
		}
	},
	/**
	 * @see {@link XmlDataSet}.
	 */
	XML {

		/**
		 * {@inheritDoc}
		 */
		public IDataSet fromInputStream(final InputStream in) throws DataSetException, IOException {
			return new XmlDataSet(in);
		}
	},
	/**
	 * @see {@link StreamingXmlDataSet}.
	 */
	STREAMING {

		/**
		 * {@inheritDoc}
		 */
		public IDataSet fromInputStream(final InputStream in) throws DataSetException, IOException {
			return new StreamingDataSet(new XmlProducer(new InputSource(in)));
		}
	};

	/**
	 * Returns a {@link IDataSet dataset} with the format for this enum
	 * 
	 * @param the
	 *            data file {@link InputStream}
	 * @return a {@link IDataSet dataset}
	 * @throws DataSetException
	 *             DBUnit failure
	 * @throws IOException
	 *             I/O failure
	 */
	public abstract IDataSet fromInputStream(InputStream in) throws DataSetException, IOException;
}
