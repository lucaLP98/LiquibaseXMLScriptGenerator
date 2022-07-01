package it.alten.tirocinio.api.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetDTO;
import it.alten.tirocinio.liquibaseChangeElement.ChangeSet;

/*
 * Mapper for ChangeSet and ChangeSetDTO
 */
@Mapper
public interface ChangeSetMapper {
	ChangeSetMapper INSTANCE = Mappers.getMapper(ChangeSetMapper.class);
	
	@Mapping(source="changeSetId", target="changeSetId")
	ChangeSetDTO changeSetToChangeSetDTO(ChangeSet changeSet);
}
