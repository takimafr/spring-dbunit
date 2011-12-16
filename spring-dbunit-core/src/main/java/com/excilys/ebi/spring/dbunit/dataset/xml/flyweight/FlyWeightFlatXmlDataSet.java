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
package com.excilys.ebi.spring.dbunit.dataset.xml.flyweight;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads and writes flat XML dataset document. Each XML element corresponds to a
 * table row. Each XML element name corresponds to a table name. The XML
 * attributes correspond to table columns.
 * <p>
 * Flat XML dataset document sample:
 * <p>
 * 
 * <pre>
 * &lt;!DOCTYPE dataset SYSTEM "my-dataset.dtd"&gt;
 * &lt;dataset&gt;
 *     &lt;TEST_TABLE COL0="row 0 col 0"
 *         COL1="row 0 col 1"
 *         COL2="row 0 col 2"/&gt;
 *     &lt;TEST_TABLE COL1="row 1 col 1"/&gt;
 *     &lt;SECOND_TABLE COL0="row 0 col 0"
 *           COL1="row 0 col 1" /&gt;
 *     &lt;EMPTY_TABLE/&gt;
 * &lt;/dataset&gt;
 * </pre>
 * <p>
 * To specify null values, omit corresponding attribute. In the above example,
 * missing COL0 and COL2 attributes of TEST_TABLE second row represents null
 * values.
 * <p>
 * Table metadata is deduced from the first row of each table by default.
 * <b>Beware that DbUnit may think a table misses some columns if the first row
 * of that table has one or more null values.</b> You can do one of the
 * following things to avoid this:
 * <ul>
 * <li>Use a DTD. DbUnit will use the columns declared in the DTD as table
 * metadata. DbUnit only supports external system URI. The URI can be absolute
 * or relative.</li>
 * <li>Since DBUnit 2.3.0 there is a functionality called "column sensing" which
 * basically reads in the whole XML into a buffer and dynamically adds new
 * columns as they appear. It can be used as demonstrated in the following
 * example:
 * 
 * <pre>
 * // since dbunit 2.4.7
 * FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
 * builder.setInputSource(new File(&quot;src/xml/flatXmlTableTest.xml&quot;));
 * builder.setColumnSensing(true);
 * IDataSet dataSet = builder.build();
 * 
 * // or dbunit release &lt;= 2.4.6:
 * boolean enableColumnSensing = true;
 * IDataSet dataSet = new FlatXmlDataSet(new File(&quot;src/xml/flatXmlTableTest.xml&quot;), false, enableColumnSensing);
 * </pre>
 * 
 * </li>
 * </ul>
 * </p>
 * 
 * @author Manuel Laflamme
 * @author gommma (gommma AT users.sourceforge.net)
 * @author Last changed by: $Author: gommma $
 * @version $Revision: 1048 $ $Date: 2009-09-26 18:21:40 +0200 (sab, 26 set
 *          2009) $
 * @since 1.0 (Mar 12, 2002)
 */
public class FlyWeightFlatXmlDataSet extends CachedDataSet {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FlyWeightFlatXmlDataSet.class);

	/**
	 * Creates a new {@link FlyWeightFlatXmlDataSet} with the data of the given
	 * producer.
	 * 
	 * @param flatXmlProducer
	 *            The producer that provides the {@link FlyWeightFlatXmlDataSet}
	 *            content
	 * @throws DataSetException
	 * @since 2.4.7
	 */
	public FlyWeightFlatXmlDataSet(FlyWeightFlatXmlProducer flatXmlProducer) throws DataSetException {
		super(flatXmlProducer, flatXmlProducer.isCaseSensitiveTableNames());
	}

	/**
	 * Write the specified dataset to the specified output stream as xml.
	 */
	public static void write(IDataSet dataSet, OutputStream out) throws IOException, DataSetException {
		logger.debug("write(dataSet={}, out={}) - start", dataSet, out);

		FlatXmlWriter datasetWriter = new FlatXmlWriter(out);
		datasetWriter.setIncludeEmptyTable(true);
		datasetWriter.write(dataSet);
	}

	/**
	 * Write the specified dataset to the specified writer as xml.
	 */
	public static void write(IDataSet dataSet, Writer writer) throws IOException, DataSetException {
		logger.debug("write(dataSet={}, writer={}) - start", dataSet, writer);
		write(dataSet, writer, null);
	}

	/**
	 * Write the specified dataset to the specified writer as xml.
	 */
	public static void write(IDataSet dataSet, Writer writer, String encoding) throws IOException, DataSetException {
		if (logger.isDebugEnabled()) {
			logger.debug("write(dataSet={}, writer={}, encoding={}) - start", new Object[] { dataSet, writer, encoding });
		}

		FlatXmlWriter datasetWriter = new FlatXmlWriter(writer, encoding);
		datasetWriter.setIncludeEmptyTable(true);
		datasetWriter.write(dataSet);
	}
}
