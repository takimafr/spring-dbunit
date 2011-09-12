package com.excilys.ebi.spring.dbunit.dataset.xml;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.dbunit.dataset.xml.FlatDtdProducer;
import org.springframework.util.ReflectionUtils;
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
		Field _columnListMapField = ReflectionUtils.findField(FlatDtdProducer.class, "_columnListMap");
		ReflectionUtils.setField(_columnListMapField, this, new LinkedHashMap<String, List<Column>>());
	}
}
