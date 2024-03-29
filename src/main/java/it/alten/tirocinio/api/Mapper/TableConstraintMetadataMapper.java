package it.alten.tirocinio.api.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataDTO;
import it.alten.tirocinio.model.TableConstraintMetadata;

/*
 * Mapper for TableConstraintMetadata and TableConstraintMetadataDTO
 */
@Mapper
public interface TableConstraintMetadataMapper {
	TableConstraintMetadataMapper INSTANCE = Mappers.getMapper(TableConstraintMetadataMapper.class);

	@Mapping(source="constraintName", target="constraintName")
	@Mapping(source="tableSchema", target="tableSchema")
	@Mapping(source="tableName", target="tableName")
	@Mapping(source="constraintType", target="constraintType")
	TableConstraintMetadataDTO TableConstraintMetadataDTOToTableConstraintMetadata(TableConstraintMetadata tableConstraintMetadata);
}
