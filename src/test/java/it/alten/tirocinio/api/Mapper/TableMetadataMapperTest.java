package it.alten.tirocinio.api.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.model.mysql.TableMetadataMySql;

public class TableMetadataMapperTest {
	private final String SCHEMA_NAME = "demo1";
	private final String TABLE_NAME = "testTab";
	private final String TABLE_TYPE = "test";
	private final int TABLE_ROWS = 5;
	
	private TableMetadataMapper mapper;
	
	@BeforeEach
	public void init() {
		mapper = TableMetadataMapper.INSTANCE;
	}
	
	@Test
	public void tableMetadataToTableMetadataDTOTest() {
		TableMetadataMySql tableMetadataMySql = new TableMetadataMySql();
		tableMetadataMySql.setTableName(TABLE_NAME);
		tableMetadataMySql.setTableRows(TABLE_ROWS);
		tableMetadataMySql.setTableSchema(SCHEMA_NAME);
		tableMetadataMySql.setTableType(TABLE_TYPE);
		
		TableMetadataDTO dto = mapper.tableMetadataToTableMetadataDTO(tableMetadataMySql);
		
		assertEquals(SCHEMA_NAME, dto.getTableSchema());
		assertEquals(TABLE_NAME, dto.getTableName());
		assertEquals(TABLE_TYPE, dto.getTableType());
		assertEquals(TABLE_ROWS, dto.getTableRows());
	}
	
	@Test
	public void tableMetadataToTableMetadataDTOTest_nullParameter() {
		TableMetadataDTO dto = mapper.tableMetadataToTableMetadataDTO(null);
		assertNull(dto);
	}
}
