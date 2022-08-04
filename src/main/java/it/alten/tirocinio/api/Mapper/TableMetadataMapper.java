package it.alten.tirocinio.api.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.model.TableMetadata;

/*
 * Mapper for TableMetadata and TableMetadataDTO
 */
@Mapper
public interface TableMetadataMapper {
	TableMetadataMapper INSTANCE = Mappers.getMapper(TableMetadataMapper.class);
	
	@Mapping(source="tableSchema", target="tableSchema")
	@Mapping(source="tableName", target="tableName")
	@Mapping(source="tableRows", target="tableRows")
	TableMetadataDTO tableMetadataToTableMetadataDTO(TableMetadata tableMetadata);
}
