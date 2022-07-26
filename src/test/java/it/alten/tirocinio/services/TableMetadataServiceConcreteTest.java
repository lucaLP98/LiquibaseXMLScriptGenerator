package it.alten.tirocinio.services;

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

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataListDTO;
import it.alten.tirocinio.model.TableMetadata;
import it.alten.tirocinio.repository.TableMetadataRepository;
import it.alten.tirocinio.services.concrete.TableMetadataServiceConcrete;

public class TableMetadataServiceConcreteTest {
	@Mock
	private TableMetadataRepository tableMetadataRepository;
	
	@InjectMocks
	private TableMetadataServiceConcrete service;
	
	private Set<TableMetadata> testSet;
	private int TEST_SET_SIZE;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		
		TableMetadata t1 = new TableMetadata();
		t1.setTableName("t1");
		t1.setTableSchema("demo");
		TableMetadata t2 = new TableMetadata();
		t2.setTableName("t2");
		t2.setTableSchema("demo");
		TableMetadata t3 = new TableMetadata();
		t3.setTableName("t3");
		t3.setTableSchema("demo");
		testSet = new HashSet<>();
		testSet.add(t1);	
		testSet.add(t2);	
		testSet.add(t3);
		TEST_SET_SIZE = testSet.size();
	}
	
	/*
	 * method getAllTables
	 */
	@Test
	public void getAllTablesTest() {
		when(tableMetadataRepository.getAllDBTables()).thenReturn(testSet);
		
		TableMetadataListDTO metadataListDTO = service.getAllTables();
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getTablesMetadataList().size());
	}
	
	@Test
	public void getAllTablesTest_EmptyResult() {
		when(tableMetadataRepository.getAllDBTables()).thenReturn(new HashSet<>());
		
		TableMetadataListDTO metadataListDTO = service.getAllTables();
		
		assertEquals(0, metadataListDTO.getTablesMetadataList().size());
	}
	
	/*
	 * method getAllTablesBySchema
	 */
	@Test 
	public void getAllTablesBySchemaTest() {
		String schemaName = "demo";
		
		when(tableMetadataRepository.getAllDBTablesBySchema(schemaName)).thenReturn(testSet);
		
		TableMetadataListDTO metadataListDTO = service.getAllTablesBySchema(schemaName);
		
		assertEquals(TEST_SET_SIZE, metadataListDTO.getTablesMetadataList().size());
	}
	
	@Test 
	public void getAllTablesBySchemaTest_EmptyResult() {
		String schemaName = "demo";
		
		when(tableMetadataRepository.getAllDBTablesBySchema(schemaName)).thenReturn(new HashSet<>());
		
		TableMetadataListDTO metadataListDTO = service.getAllTablesBySchema(schemaName);
		
		assertEquals(0, metadataListDTO.getTablesMetadataList().size());
	}
	
	@Test 
	public void getAllTablesBySchemaTest_NullParameter() {
		TableMetadataListDTO metadataListDTO = service.getAllTablesBySchema(null);
		
		assertEquals(0, metadataListDTO.getTablesMetadataList().size());
	}
	
	@Test 
	public void getAllTablesBySchemaTest_BlankStringParameter() {
		TableMetadataListDTO metadataListDTO = service.getAllTablesBySchema("");
		
		assertEquals(0, metadataListDTO.getTablesMetadataList().size());
	}
	
	/*
	 * method getTableByNameAndSchema
	 */
	@Test
	public void getTableByNameAndSchema() {
		String schemaName = "demo";
		String tableName = "tab";
		
		TableMetadata t1 = new TableMetadata();
		t1.setTableName(tableName);
		t1.setTableSchema(schemaName);
		
		when(tableMetadataRepository.getDBTablesByNameAndSchema(schemaName, tableName)).thenReturn(t1);
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertEquals(tableName, metadataDTO.getTableName());
		assertEquals(schemaName, metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_EmptyResult() {
		String schemaName = "demo";
		String tableName = "tab";
		
		TableMetadata t1 = new TableMetadata();
		t1.setTableName(tableName);
		t1.setTableSchema(schemaName);
		
		when(tableMetadataRepository.getDBTablesByNameAndSchema(schemaName, tableName)).thenReturn(null);
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameNotEmpty_TableNameNull() {
		String schemaName = "demo";
		String tableName = null;
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameNotEmpty_TableNameEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameNull_TableNameNotEmpty() {
		String schemaName = null;
		String tableName = "tab";
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameNull_TableNameNull() {
		String schemaName = null;
		String tableName = null;
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameNull_TableNameEmpty() {
		String schemaName = null;
		String tableName = "";
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameEmtpy_TableNameNotEmpty() {
		String schemaName = "";
		String tableName = "tab";
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameEmtpy_TableNameNull() {
		String schemaName = "";
		String tableName = null;
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
	
	@Test
	public void getTableByNameAndSchema_SchemaNameEmtpy_TableNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		TableMetadataDTO metadataDTO = service.getTableByNameAndSchema(schemaName, tableName);
		
		assertNotNull(metadataDTO);
		assertNull(metadataDTO.getTableName());
		assertNull(metadataDTO.getTableSchema());
	}
}
