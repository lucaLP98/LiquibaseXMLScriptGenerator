package it.alten.tirocinio.api.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.alten.tirocinio.api.DTO.entityDTO.SchemaDTO;
import it.alten.tirocinio.model.SchemaMetadata;

public class SchemaMetadataMapperTest {
	private SchemaMetadataMapper mapper;
	
	private final String SCHEMA_NAME = "demo1";
	
	@BeforeEach
	public void init() {
		mapper = SchemaMetadataMapper.INSTANCE;
	}
	
	@Test
	public void schemaToSchemaDTOTest() {
		SchemaMetadata entity = new SchemaMetadata();
		entity.setSchemaName(SCHEMA_NAME);
		
		SchemaDTO dto = mapper.schemaToSchemaDTO(entity);
		
		assertEquals(SCHEMA_NAME, dto.getSchemaName());
	}
	
	@Test
	public void schemaToSchemaDTOTest_nullParameter() {
		SchemaDTO dto = mapper.schemaToSchemaDTO(null);
		assertNull(dto);
	}
}
