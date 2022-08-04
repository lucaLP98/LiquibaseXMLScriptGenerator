package it.alten.tirocinio.services.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.SchemaDTO;
import it.alten.tirocinio.api.DTO.entityDTO.SchemaListDTO;
import it.alten.tirocinio.api.Mapper.SchemaMetadataMapper;
import it.alten.tirocinio.model.SchemaMetadata;
import it.alten.tirocinio.repository.SchemaMetadataRepository;
import it.alten.tirocinio.services.SchemaMetadataService;

/*
 * Service implementation of SchemaMetadataService interface
 */
@Service
public class SchemaMetadataServiceImpl implements SchemaMetadataService {
	private final SchemaMetadataRepository schemaMetadataRepository;
	
	/* 
	 * Constructors
	 */
	public SchemaMetadataServiceImpl(SchemaMetadataRepository schemaMetadataRepository) {
		this.schemaMetadataRepository = schemaMetadataRepository;
	}
	
	/*
	 * Converter between set of SchemaMetadata and SchemaListDTO
	 */
	private SchemaListDTO SchemaSetToListDTO(Set<SchemaMetadata> schemaMetadataSet) {
		List<SchemaDTO> schemasDTO = new ArrayList<>();
		
		if(schemaMetadataSet != null) {
			for(SchemaMetadata s : schemaMetadataSet) {
				schemasDTO.add(SchemaMetadataMapper.INSTANCE.schemaToSchemaDTO(s));
			}
		}
		
		return new SchemaListDTO(schemasDTO);
	}

	/*
	 * Get all schema which are present in DB 
	 */
	@Override
	public SchemaListDTO getAllDatabaseSchema() {	
		return SchemaSetToListDTO(schemaMetadataRepository.getAllDBSchema());
	}
}
