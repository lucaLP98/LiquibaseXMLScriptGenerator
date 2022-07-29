package it.alten.tirocinio.services.concrete.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataListDTO;
import it.alten.tirocinio.model.mysql.TableConstraintMetadataMySql;
import it.alten.tirocinio.repository.mysql.TableConstraintMetadataRepository;

public class TableConstraintMetadataServiceMySqlTest {
	@Mock
	private TableConstraintMetadataRepository repository;
	
	@InjectMocks
	public TableConstraintMetadataServiceMySql service;
	
	private Set<TableConstraintMetadataMySql> testSet;
	private int TEST_SET_SIZE;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		
		TableConstraintMetadataMySql t1 = new TableConstraintMetadataMySql();
		t1.setConstraintName("tc1");
		t1.setTableName("tab");
		t1.setTableSchema("demo");
		TableConstraintMetadataMySql t2 = new TableConstraintMetadataMySql();
		t2.setConstraintName("tc2");
		t2.setTableName("tab");
		t2.setTableSchema("demo");
		TableConstraintMetadataMySql t3 = new TableConstraintMetadataMySql();
		t3.setConstraintName("tc3");
		t3.setTableName("tab");
		t3.setTableSchema("demo");
		testSet = new HashSet<>();
		testSet.add(t1);
		testSet.add(t2);
		testSet.add(t3);
		TEST_SET_SIZE = testSet.size();
	}
	
	/*
	 * method getAllConstraints
	 */
	@Test
	public void getAllConstraintsTest_tableNameNull_schemaNameNull() {
		TableConstraintMetadataListDTO ret = service.getAllConstraints(null, null);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameNull_schemaNameEmpty() {
		String schemaName = "";
		String tableName = null;
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameNull_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = null;
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameEmpty_schemaNameNull() {
		String schemaName = null;
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameEmpty_schemaNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameEmpty_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameNotEmpty_schemaNameNull() {
		String schemaName = null;
		String tableName = "tab";
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameNotEmpty_schemaNameEmpty() {
		String schemaName = "";
		String tableName = "tab";
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_tableNameNotEmpty_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab";
		
		when(repository.getAllConstraintsByTable(tableName, schemaName)).thenReturn(testSet);
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(TEST_SET_SIZE, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllConstraintsTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab";
		
		when(repository.getAllConstraintsByTable(tableName, schemaName)).thenReturn(new HashSet<>());
		
		TableConstraintMetadataListDTO ret = service.getAllConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	/*
	 * method getAllUniqueConstraints
	 */
	@Test
	public void getAllUniqueConstraintsTest_tableNameNull_schemaNameNull() {
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(null, null);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameNull_schemaNameEmpty() {
		String schemaName = "";
		String tableName = null;
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameNull_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = null;
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameEmpty_schemaNameNull() {
		String schemaName = null;
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameEmpty_schemaNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameEmpty_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameNotEmpty_schemaNameNull() {
		String schemaName = null;
		String tableName = "tab";
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameNotEmpty_schemaNameEmpty() {
		String schemaName = "";
		String tableName = "tab";
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_tableNameNotEmpty_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab";
		
		when(repository.getUniqueConstraintsByTable(tableName, schemaName)).thenReturn(testSet);
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(TEST_SET_SIZE, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllUniqueConstraintsTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab";
		
		when(repository.getUniqueConstraintsByTable(tableName, schemaName)).thenReturn(new HashSet<>());
		
		TableConstraintMetadataListDTO ret = service.getAllUniqueConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	/*
	 * method getAllForeignKeyConstraints
	 */
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameNull_schemaNameNull() {
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(null, null);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameNull_schemaNameEmpty() {
		String schemaName = "";
		String tableName = null;
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameNull_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = null;
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameEmpty_schemaNameNull() {
		String schemaName = null;
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameEmpty_schemaNameEmpty() {
		String schemaName = "";
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameEmpty_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "";
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraints() {
		String schemaName = null;
		String tableName = "tab";
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameNotEmpty_schemaNameEmpty() {
		String schemaName = "";
		String tableName = "tab";
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_tableNameNotEmpty_schemaNameNotEmpty() {
		String schemaName = "demo";
		String tableName = "tab";
		
		when(repository.getForeignKeyConstraintsByTable(tableName, schemaName)).thenReturn(testSet);
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(TEST_SET_SIZE, ret.getConstraintsMetadata().size());
	}
	
	@Test
	public void getAllForeignKeyConstraintsTest_emptyResult() {
		String schemaName = "demo";
		String tableName = "tab";
		
		when(repository.getForeignKeyConstraintsByTable(tableName, schemaName)).thenReturn(new HashSet<>());
		
		TableConstraintMetadataListDTO ret = service.getAllForeignKeyConstraints(tableName, schemaName);
		assertEquals(0, ret.getConstraintsMetadata().size());
	}
}
