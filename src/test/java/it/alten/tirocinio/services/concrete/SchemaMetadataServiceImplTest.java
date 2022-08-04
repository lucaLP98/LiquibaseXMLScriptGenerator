package it.alten.tirocinio.services.concrete;

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
import it.alten.tirocinio.model.SchemaMetadata;
import it.alten.tirocinio.repository.mysql.SchemaMetadataRepositoryMySql;

public class SchemaMetadataServiceImplTest {
	@Mock
	private SchemaMetadataRepositoryMySql schemaMetadataRepositoryMySql;
	
	@InjectMocks
	private SchemaMetadataServiceImpl service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getAllDatabaseSchemaTest() {
		SchemaMetadata s1 = new SchemaMetadata();
		s1.setSchemaName("s1");
		SchemaMetadata s2 = new SchemaMetadata();
		s2.setSchemaName("s2");
		SchemaMetadata s3 = new SchemaMetadata();
		s3.setSchemaName("s3");
		Set<SchemaMetadata> set = new HashSet<>();
		set.add(s3);
		set.add(s2);
		set.add(s1);
		
		when(schemaMetadataRepositoryMySql.getAllDBSchema()).thenReturn(set);
		
		SchemaListDTO listDTO = service.getAllDatabaseSchema();

		assertEquals(3, listDTO.getSchemaList().size());
	}
	
	@Test
	public void getAllDatabaseSchemaTest_EmptyResult() { 
		when(schemaMetadataRepositoryMySql.getAllDBSchema()).thenReturn(new HashSet<>());
		
		SchemaListDTO listDTO = service.getAllDatabaseSchema();
		assertEquals(0, listDTO.getSchemaList().size());
	}
}
