package it.alten.tirocinio.api.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import it.alten.tirocinio.api.DTO.entityDTO.SchemaDTO;
import it.alten.tirocinio.model.SchemaMetadata;

/*
 * Mapper for SchemaMetadata and SchemaDTO
 */
@Mapper
public interface SchemaMetadataMapper {
	SchemaMetadataMapper INSTANCE = Mappers.getMapper(SchemaMetadataMapper.class);
	
	@Mapping(source="schemaName", target="schemaName")
	SchemaDTO schemaToSchemaDTO(SchemaMetadata schemaMetadata);
}
