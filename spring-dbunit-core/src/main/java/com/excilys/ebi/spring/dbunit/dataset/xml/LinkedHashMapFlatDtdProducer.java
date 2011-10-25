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
package com.excilys.ebi.spring.dbunit.dataset.xml;

import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.dbunit.dataset.xml.FlatDtdProducer;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

/**
 * A FlatDtdProducer that use a LinkedHashMap instead of an HashMap to store
 * tables, so that declared order is respected.
 * 
 * @author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
public class LinkedHashMapFlatDtdProducer extends FlatDtdProducer implements IDataSetProducer, EntityResolver, DeclHandler, LexicalHandler {

	public LinkedHashMapFlatDtdProducer() {
		useLinkedHashMap();
	}

	public LinkedHashMapFlatDtdProducer(InputSource inputSource) {
		super(inputSource);
		useLinkedHashMap();
	}

	private void useLinkedHashMap() {
		Field _columnListMapField = findField(FlatDtdProducer.class, "_columnListMap");
		makeAccessible(_columnListMapField);
		setField(_columnListMapField, this, new LinkedHashMap<String, List<Column>>());
	}
}
