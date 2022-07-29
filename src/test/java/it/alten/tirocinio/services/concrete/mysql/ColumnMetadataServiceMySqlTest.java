package it.alten.tirocinio.services.concrete.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataListDTO;
import it.alten.tirocinio.model.mysql.ColumnMetadataMySql;
import it.alten.tirocinio.repository.mysql.ColumnMetadataRepository;

public class ColumnMetadataServiceMySqlTest {
	@Mock
	private ColumnMetadataRepository repository;
	
	@InjectMocks
	private ColumnMetadataServiceMySql service;
	
	private Set<ColumnMetadataMySql> testSet;
	private int TEST_SET_SIZE;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		
		ColumnMetadataMySql c1 = new ColumnMetadataMySql();
		c1.setColumnName("col1");
		c1.setTableName("tab1");
		c1.setTableSchema("demo");
		ColumnMetadataMySql c2 = new ColumnMetadataMySql();
		c2.setColumnName("col2");
		c2.setTableName("tab1");
		c2.setTableSchema("demo");
		ColumnMetadataMySql c3 = new ColumnMetadataMySql();
		c3.setColumnName("col3");
		c3.setTableName("tab1");
		c3.setTableSchema("demo");
		testSet = new HashSet<>();
		testSet.add(c1);
		testSet.add(c2);
		testSet.add(c3);
		TEST_SET_SIZE = testSet.size();
	}
	
	/*
	 * Method getAllColumns
	 */
	@Test
	public void getAllColumnsTest_emptyResult() {
		when(repository.getAllDBColumns()).thenReturn(new HashSet<>());
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumns();
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsTest_notEmptyResult() {
		when(repository.getAllDBColumns()).thenReturn(testSet);
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumns();
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getColumnsMetadataList().size());
	}
	
	/*
	 * Method getAllColumnsByTable
	 */
	@Test
	public void getAllColumnsByTableTest_schemaNameNull_tableNameNull() {
		String schemaName = null;
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameNull_tableNameEmpty() {
		String schemaName = null;
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameNull_tableNameNotEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameEmpty_tableNameNull() {
		String schemaName = "";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameEmpty_tableNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameEmpty_tableNameNotEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameNotEmpty_tableNameNull() {
		String schemaName = "demo";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameNotEmpty_tableNameEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_schemaNameNotEmpty_tableNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBColumnsByTableAndSchema(schemaName, tableName)).thenReturn(testSet);
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getAllColumnsByTableTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBColumnsByTableAndSchema(schemaName, tableName)).thenReturn(new HashSet<>());
		
		ColumnMetadataListDTO metadataListDTO = service.getAllColumnsByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	/*
	 * method getColumnByNameAndTable
	 */
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameNull_columnNameNull() {
		String schemaName = null;
		String tableName = null;
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameNull_columnNameEmpty() {
		String schemaName = null;
		String tableName = null;
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameNull_columnNameNotEmpty() {
		String schemaName = null;
		String tableName = null;
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameEmpty_columnNameNull() {
		String schemaName = null;
		String tableName = "";
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameEmpty_columnNameEmpty() {
		String schemaName = null;
		String tableName = "";
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameEmpty_columnNameNotEmpty() {
		String schemaName = null;
		String tableName = "";
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameNotEmpty_columnNameNull() {
		String schemaName = null;
		String tableName = "tab1";
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameNotEmpty_columnNameEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNull_tableNameNotEmpty_columnNameNotEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameNull_columnNameNull() {
		String schemaName = "";
		String tableName = null;
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameNull_columnNameEmpty() {
		String schemaName = "";
		String tableName = null;
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameNull_columnNameNotEmpty() {
		String schemaName = "";
		String tableName = null;
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameEmpty_columnNameNull() {
		String schemaName = "";
		String tableName = "";
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameEmpty_columnNameEmpty() {
		String schemaName = "";
		String tableName = "";
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameEmpty_columnNameNotEmpty() {
		String schemaName = "";
		String tableName = "";
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameNotEmpty_columnNameNULL() {
		String schemaName = "";
		String tableName = "tab1";
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameNotEmpty_columnNameEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameEmpty_tableNameNotEmpty_columnNameNotEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameNull_columnNameNull() {
		String schemaName = "demo";
		String tableName = null;
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameNull_columnNameEmpty() {
		String schemaName = "demo";
		String tableName = null;
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameNull_columnNameNotEmpty() {
		String schemaName = "demo";
		String tableName = null;
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameEmpty_columnNameNull() {
		String schemaName = "demo";
		String tableName = "";
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameEmpty_columnNameEmpty() {
		String schemaName = "demo";
		String tableName = "";
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameEmpty_columnNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "";
		String columnName = "col1";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameNotEmpty_columnNameNull() {
		String schemaName = "demo";
		String tableName = "tab1";
		String columnName = null;
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameNotEmpty_columnNameEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		String columnName = "";
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_schemaNameNotEmpty_tableNameNotEmpty_columnNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		String columnName = "col1";
		
		ColumnMetadataMySql c = new ColumnMetadataMySql();
		c.setColumnName(columnName);
		c.setTableName(tableName);
		c.setTableSchema(schemaName);
		
		when(repository.getDBColumnByNameAndTableAndSchema(schemaName, tableName, columnName)).thenReturn(c);
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertEquals(schemaName, metadataDTO.getTableSchema());
		assertEquals(tableName, metadataDTO.getTableName());
		assertEquals(columnName, metadataDTO.getColumnName());
	}
	
	@Test
	public void getColumnByNameAndTableTest_resultEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		String columnName = "col1";
		
		when(repository.getDBColumnByNameAndTableAndSchema(schemaName, tableName, columnName)).thenReturn(new ColumnMetadataMySql());
		
		ColumnMetadataDTO metadataDTO = service.getColumnByNameAndTable(schemaName, tableName, columnName);
		
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
		assertNull(metadataDTO.getColumnName());
	}
	
	/*
	 * Method getColumnNotNullByTable
	 */
	@Test
	public void getColumnNotNullByTableTest_schemaNameNull_tableNameNull() {
		String schemaName = null;
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameNull_tableNameEmpty() {
		String schemaName = null;
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameNull_tableNameNotEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameEmpty_tableNameNull() {
		String schemaName = "";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameEmpty_tableNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameEmpty_tableNameNotEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameNotEmpty_tableNameNull() {
		String schemaName = "demo";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameNotEmpty_tableNameEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_schemaNameNotEmpty_tableNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBNotNullColumnsByTableAndSchema(schemaName, tableName)).thenReturn(testSet);
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNotNullByTableTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBNotNullColumnsByTableAndSchema(schemaName, tableName)).thenReturn(new HashSet<>());
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNotNullByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	/*
	 * Method getColumnNullableByTable
	 */
	@Test
	public void getColumnNullableByTableTest_schemaNameNull_tableNameNull() {
		String schemaName = null;
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameNull_tableNameEmpty() {
		String schemaName = null;
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameNull_tableNameNotEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameEmpty_tableNameNull() {
		String schemaName = "";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameEmpty_tableNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameEmpty_tableNameNotEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameNotEmpty_tableNameNull() {
		String schemaName = "demo";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameNotEmpty_tableNameEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_schemaNameNotEmpty_tableNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBNullableColumnsByTableAndSchema(schemaName, tableName)).thenReturn(testSet);
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnNullableByTableTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBNullableColumnsByTableAndSchema(schemaName, tableName)).thenReturn(new HashSet<>());
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnNullableByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	/*
	 * Method getIntegerColumnByTable
	 */
	@Test
	public void getIntegerColumnByTableTest_schemaNameNull_tableNameNull() {
		String schemaName = null;
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameNull_tableNameEmpty() {
		String schemaName = null;
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameNull_tableNameNotEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameEmpty_tableNameNull() {
		String schemaName = "";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameEmpty_tableNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameEmpty_tableNameNotEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameNotEmpty_tableNameNull() {
		String schemaName = "demo";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameNotEmpty_tableNameEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_schemaNameNotEmpty_tableNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBIntegerColumnsByTableAndSchema(schemaName, tableName)).thenReturn(testSet);
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getIntegerColumnByTableTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBIntegerColumnsByTableAndSchema(schemaName, tableName)).thenReturn(new HashSet<>());
		
		ColumnMetadataListDTO metadataListDTO = service.getIntegerColumnByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	/*
	 * Method getColumnWithDefaultValueByTable
	 */
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameNull_tableNameNull() {
		String schemaName = null;
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameNull_tableNameEmpty() {
		String schemaName = null;
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameNull_tableNameNotEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameEmpty_tableNameNull() {
		String schemaName = "";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameEmpty_tableNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameEmpty_tableNameNotEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameNotEmpty_tableNameNull() {
		String schemaName = "demo";
		String tableName = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameNotEmpty_tableNameEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_schemaNameNotEmpty_tableNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBColumnsWithDefaultValueByTableAndSchema(schemaName, tableName)).thenReturn(testSet);
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnWithDefaultValueByTableTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab1";
		
		when(repository.getAllDBColumnsWithDefaultValueByTableAndSchema(schemaName, tableName)).thenReturn(new HashSet<>());
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnWithDefaultValueByTable(schemaName, tableName);
		
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	/*
	 * Method getColumnByTypeAndTable
	 */
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameNull_dataTypeNull() {
		String schemaName = null;
		String tableName = null;
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameNull_dataTypeEmpty() {
		String schemaName = null;
		String tableName = null;
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameNull_dataTypeNotEmpty() {
		String schemaName = null;
		String tableName = null;
		String dataType = "varchar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameEmpty_dataTypeNull() {
		String schemaName = null;
		String tableName = "";
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameEmpty_dataTypeEmpty() {
		String schemaName = null;
		String tableName = "";
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameEmpty_dataTypeNotEmpty() {
		String schemaName = null;
		String tableName = "";
		String dataType = "varhcar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameNotEmpty_dataTypeNull() {
		String schemaName = null;
		String tableName = "tab1";
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameNotEmpty_dataTypeEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNull_tableNameNotEmpty_dataTypeNotEmpty() {
		String schemaName = null;
		String tableName = "tab1";
		String dataType = "varchar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameNull_dataTypeNull() {
		String schemaName = "";
		String tableName = null;
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameNull_dataTypeEmpty() {
		String schemaName = "";
		String tableName = null;
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameNull_dataTypeNotEmpty() {
		String schemaName = "";
		String tableName = null;
		String dataType = "varchar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameEmpty_dataTypeNull() {
		String schemaName = "";
		String tableName = "";
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameEmpty_dataTypeEmpty() {
		String schemaName = "";
		String tableName = "";
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameEmpty_dataTypeNotEmpty() {
		String schemaName = "";
		String tableName = "";
		String dataType = "varchar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameNotEmpty_dataTypeNULL() {
		String schemaName = "";
		String tableName = "tab1";
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameNotEmpty_dataTypeEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameEmpty_tableNameNotEmpty_dataTypeNotEmpty() {
		String schemaName = "";
		String tableName = "tab1";
		String dataType = "varchar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameNull_dataTypeNull() {
		String schemaName = "demo";
		String tableName = null;
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameNull_dataTypeEmpty() {
		String schemaName = "demo";
		String tableName = null;
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameNull_dataTypeNotEmpty() {
		String schemaName = "demo";
		String tableName = null;
		String dataType = "varchar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameEmpty_dataTypeNull() {
		String schemaName = "demo";
		String tableName = "";
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameEmpty_dataTypeEmpty() {
		String schemaName = "demo";
		String tableName = "";
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameEmpty_dataTypeNotEmpty() {
		String schemaName = "demo";
		String tableName = "";
		String dataType = "varchar";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameNotEmpty_dataTypeNull() {
		String schemaName = "demo";
		String tableName = "tab1";
		String dataType = null;
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameNotEmpty_dataTypeEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		String dataType = "";
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_schemaNameNotEmpty_tableNameNotEmpty_dataTypeNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		String dataType = "varchar";
		
		when(repository.getAllDBColumnsByDataTypeAndTable(dataType, schemaName, tableName)).thenReturn(testSet);
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(TEST_SET_SIZE, metadataListDTO.getColumnsMetadataList().size());
	}
	
	@Test
	public void getColumnByTypeAndTableTest_resultEmpty() {
		String schemaName = "demo";
		String tableName = "tab1";
		String dataType = "varchar";
		
		when(repository.getAllDBColumnsByDataTypeAndTable(dataType, schemaName, tableName)).thenReturn(new HashSet<>());
		
		ColumnMetadataListDTO metadataListDTO = service.getColumnByTypeAndTable(dataType, schemaName, tableName);
		
		assertNotNull(metadataListDTO);
		assertEquals(0, metadataListDTO.getColumnsMetadataList().size());
	}
}
