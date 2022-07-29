package it.alten.tirocinio.api.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataDTO;
import it.alten.tirocinio.model.mysql.ColumnMetadataMySql;

public class ColumnMetadataMapperTest {
	private ColumnMetadataMapper mapper;
	
	private final String TABLE_SCHEMA = "demo1";
	private final String TABLE_NAME = "tab1";
	private final String COLUMN_TYPE = "integer";
	private final String IS_NULLABLE = "false";
	private final String COLUMN_NAME = "col1";
	private final String COLUMN_KEY = "PRI";
	private final String COLUMN_DEFAULT = "";
	
	@BeforeEach
	public void init() {
		mapper = ColumnMetadataMapper.INSTANCE;
	}
	
	@Test
	public void ColumnMetadataToColumnMetadataDTOTest() {
		ColumnMetadataMySql entity = new ColumnMetadataMySql();
		entity.setColumnName(COLUMN_NAME);
		entity.setTableSchema(TABLE_SCHEMA);
		entity.setTableName(TABLE_NAME);
		entity.setIsNullable(IS_NULLABLE);
		entity.setColumnType(COLUMN_TYPE);
		entity.setColumnKey(COLUMN_KEY);
		entity.setColumnDefault(COLUMN_DEFAULT);
		
		ColumnMetadataDTO dto = mapper.ColumnMetadataToColumnMetadataDTO(entity);
		
		assertEquals(TABLE_SCHEMA, dto.getTableSchema());
		assertEquals(TABLE_NAME, dto.getTableName());
		assertEquals(COLUMN_TYPE, dto.getColumnType());
		assertEquals(IS_NULLABLE, dto.getIsNullable());
		assertEquals(COLUMN_NAME, dto.getColumnName());
		assertEquals(COLUMN_KEY, dto.getColumnKey());
		assertEquals(COLUMN_DEFAULT, dto.getColumnDefault());
	}
	
	@Test
	public void ColumnMetadataToColumnMetadataDTOTest_nullParameter() {
		ColumnMetadataDTO dto = mapper.ColumnMetadataToColumnMetadataDTO(null);
		assertNull(dto);
	}
}
