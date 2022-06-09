package it.alten.tirocinio.api.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import it.alten.tirocinio.api.DTO.SchemaDTO;
import it.alten.tirocinio.model.Schema;

/*
 * Mapper for Schema and SchemaDTO
 */
@Mapper
public interface SchemaMapper {
	SchemaMapper INSTANCE = Mappers.getMapper(SchemaMapper.class);
	
	@Mapping(source="schemaName", target="schemaName")
	SchemaDTO schemaToSchemaDTO(Schema schema);
}
