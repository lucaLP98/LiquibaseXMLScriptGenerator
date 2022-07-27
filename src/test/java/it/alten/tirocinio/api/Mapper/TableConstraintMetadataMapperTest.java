package it.alten.tirocinio.api.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataDTO;
import it.alten.tirocinio.model.TableConstraintMetadata;

public class TableConstraintMetadataMapperTest {
	private final String SCHEMA_NAME = "demo1";
	private final String TABLE_NAME = "testTab";
	private final String CONSTRAINT_NAME = "constr1";
	private final String CONSTRAINT_TYPE = "CHECK";
	
	private TableConstraintMetadataMapper mapper;
	
	@BeforeEach
	public void init() {
		mapper = TableConstraintMetadataMapper.INSTANCE;
	}
	
	@Test
	public void TableConstraintMetadataDTOToTableConstraintMetadataTest() {
		TableConstraintMetadata entity = new TableConstraintMetadata();
		entity.setConstraintName(CONSTRAINT_NAME);
		entity.setConstraintType(CONSTRAINT_TYPE);
		entity.setTableName(TABLE_NAME);
		entity.setTableSchema(SCHEMA_NAME);
		
		TableConstraintMetadataDTO dto = mapper.TableConstraintMetadataDTOToTableConstraintMetadata(entity);
		
		assertEquals(CONSTRAINT_NAME, dto.getConstraintName());
		assertEquals(CONSTRAINT_TYPE, dto.getConstraintType());
		assertEquals(TABLE_NAME, dto.getTableName());
		assertEquals(SCHEMA_NAME, dto.getTableSchema());
	}
	
	@Test
	public void TableConstraintMetadataDTOToTableConstraintMetadataTest_nullParameter() {
		TableConstraintMetadataDTO dto = mapper.TableConstraintMetadataDTOToTableConstraintMetadata(null);
		assertNull(dto);
	}
}
