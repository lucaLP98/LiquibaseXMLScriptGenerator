package it.alten.tirocinio.api.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.model.TableMetadata;

public class TableMetadataMapperTest {
	private final String SCHEMA_NAME = "demo1";
	private final String TABLE_NAME = "testTab";
	private final int TABLE_ROWS = 5;
	
	private TableMetadataMapper mapper;
	
	@BeforeEach
	public void init() {
		mapper = TableMetadataMapper.INSTANCE;
	}
	
	@Test
	public void tableMetadataToTableMetadataDTOTest() {
		TableMetadata tableMetadata = new TableMetadata();
		tableMetadata.setTableName(TABLE_NAME);
		tableMetadata.setTableRows(TABLE_ROWS);
		tableMetadata.setTableSchema(SCHEMA_NAME);
		
		TableMetadataDTO dto = mapper.tableMetadataToTableMetadataDTO(tableMetadata);
		
		assertEquals(SCHEMA_NAME, dto.getTableSchema());
		assertEquals(TABLE_NAME, dto.getTableName());
		assertEquals(TABLE_ROWS, dto.getTableRows());
	}
	
	@Test
	public void tableMetadataToTableMetadataDTOTest_nullParameter() {
		TableMetadataDTO dto = mapper.tableMetadataToTableMetadataDTO(null);
		assertNull(dto);
	}
}
