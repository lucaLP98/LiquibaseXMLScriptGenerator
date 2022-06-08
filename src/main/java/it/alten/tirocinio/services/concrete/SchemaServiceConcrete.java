package it.alten.tirocinio.services.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.SchemaDTO;
import it.alten.tirocinio.api.DTO.SchemaListDTO;
import it.alten.tirocinio.api.Mapper.SchemaMapper;
import it.alten.tirocinio.model.Schema;
import it.alten.tirocinio.repository.SchemaRepository;
import it.alten.tirocinio.services.SchemaService;

@Service
public class SchemaServiceConcrete implements SchemaService {
	private final SchemaRepository schemaRepository;
	
	public SchemaServiceConcrete(SchemaRepository schemaRepository) {
		this.schemaRepository = schemaRepository;
	}

	@Override
	public SchemaListDTO getAllDatabaseSchema() {			
		List<SchemaDTO> schemasDTO = new ArrayList<>();
		Set<Schema> schemas = schemaRepository.getAllDBSchema();
		
		for(Schema s : schemas) {
			schemasDTO.add(SchemaMapper.INSTANCE.schemaToSchemaDTO(s));
		}
		SchemaListDTO schemaListDTO = new SchemaListDTO(schemasDTO);
		
		return schemaListDTO;
	}

}
