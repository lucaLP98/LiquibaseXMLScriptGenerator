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

import it.alten.tirocinio.api.DTO.entityDTO.SchemaListDTO;
import it.alten.tirocinio.model.mysql.SchemaMetadatMySql;
import it.alten.tirocinio.repository.mysql.SchemaRepository;

public class SchemaMetadataServiceMySqlTest {
	@Mock
	private SchemaRepository schemaRepository;
	
	@InjectMocks
	private SchemaMetadataServiceMySql service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getAllDatabaseSchemaTest() {
		SchemaMetadatMySql s1 = new SchemaMetadatMySql();
		s1.setSchemaName("s1");
		SchemaMetadatMySql s2 = new SchemaMetadatMySql();
		s2.setSchemaName("s2");
		SchemaMetadatMySql s3 = new SchemaMetadatMySql();
		s3.setSchemaName("s3");
		Set<SchemaMetadatMySql> set = new HashSet<>();
		set.add(s3);
		set.add(s2);
		set.add(s1);
		
		when(schemaRepository.getAllDBSchema()).thenReturn(set);
		
		SchemaListDTO listDTO = service.getAllDatabaseSchema();

		assertEquals(3, listDTO.getSchemaList().size());
	}
	
	@Test
	public void getAllDatabaseSchemaTest_EmptyResult() { 
		when(schemaRepository.getAllDBSchema()).thenReturn(new HashSet<>());
		
		SchemaListDTO listDTO = service.getAllDatabaseSchema();
		assertEquals(0, listDTO.getSchemaList().size());
	}
}
