package it.alten.tirocinio.services.concrete.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.SchemaDTO;
import it.alten.tirocinio.api.DTO.entityDTO.SchemaListDTO;
import it.alten.tirocinio.api.Mapper.SchemaMapper;
import it.alten.tirocinio.model.mysql.SchemaMetadatMySql;
import it.alten.tirocinio.repository.mysql.SchemaRepository;
import it.alten.tirocinio.services.SchemaMetadataService;

/*
 * Service implementation of SchemaMetadataService interface
 */
@Profile("mysql")
@Service
public class SchemaMetadataServiceMySql implements SchemaMetadataService {
	private final SchemaRepository schemaRepository;
	
	/* 
	 * Constructors
	 */
	public SchemaMetadataServiceMySql(SchemaRepository schemaRepository) {
		this.schemaRepository = schemaRepository;
	}
	
	/*
	 * Converter between set of SchemaMetadatMySql and SchemaListDTO
	 */
	private SchemaListDTO SchemaSetToListDTO(Set<SchemaMetadatMySql> schemaMetadatMySqls) {
		List<SchemaDTO> schemasDTO = new ArrayList<>();
		
		if(schemaMetadatMySqls != null) {
			for(SchemaMetadatMySql s : schemaMetadatMySqls) {
				schemasDTO.add(SchemaMapper.INSTANCE.schemaToSchemaDTO(s));
			}
		}
		
		return new SchemaListDTO(schemasDTO);
	}

	/*
	 * Get all schema which are present in DB 
	 */
	@Override
	public SchemaListDTO getAllDatabaseSchema() {	
		return SchemaSetToListDTO(schemaRepository.getAllDBSchema());
	}
}
