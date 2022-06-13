package it.alten.tirocinio.api.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataDTO;
import it.alten.tirocinio.model.ColumnMetadata;

/*
 * Mapper for ColumnMetadata and ColumnMetadataDTO
 */
@Mapper
public interface ColumnMetadataMapper {
	ColumnMetadataMapper INSTANCE = Mappers.getMapper(ColumnMetadataMapper.class);
	
	@Mapping(source="tableSchema", target="tableSchema")
	@Mapping(source="tableName", target="tableName")
	@Mapping(source="columnType", target="columnType")
	@Mapping(source="isNullable", target="isNullable")
	@Mapping(source="columnName", target="columnName")
	@Mapping(source="columnKey", target="columnKey")
	ColumnMetadataDTO ColumnMetadataToColumnMetadataDTO(ColumnMetadata columnMetadata);
}
