package com.excilys.ebi.spring.dbunit.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.lang.reflect.Method;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext.HierarchyMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.excilys.ebi.spring.dbunit.ConfigurationProcessor;
import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.config.DataSetFormat;
import com.excilys.ebi.spring.dbunit.config.ExpectedDataSetConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "applicationContext-test.xml", "applicationContext-test-h2.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(dbType = DBType.H2)
public class ExpectedDataSetTest {

	private static class TestContextMock implements TestContext {

		private static final long serialVersionUID = 1L;

		public void setAttribute(String name, Object value) {				}
		public Object getAttribute(String name) {			return null;	}
		public Object removeAttribute(String name) {		return null;	}
		public boolean hasAttribute(String name) {			return false;	}
		public String[] attributeNames() {					return null;	}
		public ApplicationContext getApplicationContext() {	return null;	}
		public Object getTestInstance() {					return null;	}
		public Throwable getTestException() {				return null;	}
		public void markApplicationContextDirty(HierarchyMode hierarchyMode) {	}
		public void updateState(Object testInstance, Method testMethod,	Throwable testException) {	}

		private Class<?> testClass;
		private Method testMethod;
		
		public TestContextMock(Class<?> testClass, Method testMethod) {
			this.testClass = testClass;
			this.testMethod = testMethod;
		}
		
		public Class<?> getTestClass() {					return testClass;	}
		public Method getTestMethod() {						return testMethod;	}
	}
	
	private static class ClassForTest {
		@ExpectedDataSet
		public void test() {
			
		}
		@ExpectedDataSet(value="ds.xml", schema="sch")
		public void test1() {
			
		}
	}

	@Autowired
	private IMyEntityDao myEntityDao;
	
	private ConfigurationProcessor<TestContext> configurationProcessor = new TestConfigurationProcessor();
	
	@Test
	public void testExpectedDataSetConfiguration() throws NoSuchMethodException, SecurityException, IOException, DatabaseUnitException {
		final Class<?> testClass = ClassForTest.class;
		final Method testMethod = testClass.getMethod("test");
		TestContext context = new TestContextMock(testClass, testMethod);
		final ExpectedDataSetConfiguration configuration = configurationProcessor.getExpectedConfiguration(context);
		assertArrayEquals(new String[] {"classpath:com/excilys/ebi/spring/dbunit/test/expectedDataSet.xml"}, configuration.getDataSetResourceLocations());
		assertNull(configuration.getDataSourceSpringName());
		assertEquals(DBType.HSQLDB, configuration.getDbType());
		assertNull(configuration.getEscapePattern());
		assertEquals(DataSetFormat.FLAT, configuration.getFormat());
		assertNull(configuration.getFormatOptions().getDtdLocation());
		assertFalse(configuration.getFormatOptions().isCaseSensitiveTableNames());
		assertFalse(configuration.getFormatOptions().isColumnSensing());
		assertFalse(configuration.getFormatOptions().isDtdMetadata());
		assertNull(configuration.getSchema());
		assertArrayEquals(new String[] {"TABLE"}, configuration.getTableType());
	}
	
	@Test
	public void testExpectedDataSetConfiguration1() throws NoSuchMethodException, SecurityException, IOException, DatabaseUnitException {
		final Class<?> testClass = ClassForTest.class;
		final Method testMethod = testClass.getMethod("test1");
		TestContext context = new TestContextMock(testClass, testMethod);
		final ExpectedDataSetConfiguration configuration = configurationProcessor.getExpectedConfiguration(context);
		assertArrayEquals(new String[] {"classpath:com/excilys/ebi/spring/dbunit/test/ds.xml"}, configuration.getDataSetResourceLocations());
		assertNull(configuration.getDataSourceSpringName());
		assertEquals(DBType.HSQLDB, configuration.getDbType());
		assertNull(configuration.getEscapePattern());
		assertEquals(DataSetFormat.FLAT, configuration.getFormat());
		assertNull(configuration.getFormatOptions().getDtdLocation());
		assertFalse(configuration.getFormatOptions().isCaseSensitiveTableNames());
		assertFalse(configuration.getFormatOptions().isColumnSensing());
		assertFalse(configuration.getFormatOptions().isDtdMetadata());
		assertEquals("sch", configuration.getSchema());
		assertArrayEquals(new String[] {"TABLE"}, configuration.getTableType());
		
		IDataSet dataset = configuration.getDataSet();
		assertEquals(1, dataset.getTableNames().length);
		assertEquals("MY_ENTITY", dataset.getTableNames()[0]);
		
		ITableMetaData tableMetaData = dataset.getTableMetaData("MY_ENTITY");
		
		assertEquals(2, tableMetaData.getColumns().length);
		assertEquals("ID", tableMetaData.getColumns()[0].getColumnName());
		assertEquals("NAME", tableMetaData.getColumns()[1].getColumnName());
		
		ITable table = dataset.getTable("MY_ENTITY");
		assertEquals(4, table.getRowCount());
		assertEquals("id1", table.getValue(0, "ID"));
		assertEquals("name1", table.getValue(0, "NAME"));
		assertEquals("id2", table.getValue(1, "ID"));
		assertEquals("name2", table.getValue(1, "NAME"));
		assertEquals("id3", table.getValue(2, "ID"));
		assertEquals("name3", table.getValue(2, "NAME"));
		assertEquals("id4", table.getValue(3, "ID"));
		assertEquals("name4", table.getValue(3, "NAME"));
	}

	@Test
	@DataSet
	@ExpectedDataSet
	public void testExpectedDataSet() {
		myEntityDao.deleteById("id1");
	}

	@Test
	@DataSet
	@ExpectedDataSet(value="expectedDataSetWithColumnToIgnore.xml", columnsToIgnore="ID")
	public void testExpectedDataSetWithColumnToIgnore() {
		MyEntity myEntity = new MyEntity();
		myEntity.setId("id6");
		myEntity.setName("name6");
		myEntityDao.saveOrUpdate(myEntity);
		MyEntity myEntity2 = new MyEntity();
		myEntity2.setId("id5");
		myEntity2.setName("name5");
		myEntityDao.saveOrUpdate(myEntity2);
	}
}
