package it.alten.tirocinio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	}
	
	@Test
	public void getAllTablesTest() {
		when(tableMetadataRepository.getAllDBTables()).thenReturn(testSet);
		
		TableMetadataListDTO metadataList = service.getAllTables();
		
		assertEquals(3, metadataList.getTablesMetadataList().size());
	}
	
	@Test
	public void getAllTablesTest_EmptyResult() {
		when(tableMetadataRepository.getAllDBTables()).thenReturn(new HashSet<>());
		
		TableMetadataListDTO metadataList = service.getAllTables();
		
		assertEquals(0, metadataList.getTablesMetadataList().size());
	}
}
