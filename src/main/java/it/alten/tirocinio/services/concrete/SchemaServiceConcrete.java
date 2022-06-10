package it.alten.tirocinio.services.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.SchemaDTO;
import it.alten.tirocinio.api.DTO.entityDTO.SchemaListDTO;
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
	
	private SchemaListDTO SchemaSetToListDTO(Set<Schema> schemas) {
		List<SchemaDTO> schemasDTO = new ArrayList<>();
		
		for(Schema s : schemas) {
			schemasDTO.add(SchemaMapper.INSTANCE.schemaToSchemaDTO(s));
		}
		
		return new SchemaListDTO(schemasDTO);
	}

	@Override
	public SchemaListDTO getAllDatabaseSchema() {				
		return SchemaSetToListDTO(schemaRepository.getAllDBSchema());
	}

}
