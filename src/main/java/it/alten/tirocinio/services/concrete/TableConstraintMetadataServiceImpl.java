package it.alten.tirocinio.services.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataListDTO;
import it.alten.tirocinio.api.Mapper.TableConstraintMetadataMapper;
import it.alten.tirocinio.model.TableConstraintMetadata;
import it.alten.tirocinio.repository.TableConstraintMetadataRepository;
import it.alten.tirocinio.services.TableConstraintMetadataService;

@Service
public class TableConstraintMetadataServiceImpl implements TableConstraintMetadataService {
	private TableConstraintMetadataRepository repository;
	
	/*
	 * Constructors
	 */
	public TableConstraintMetadataServiceImpl(TableConstraintMetadataRepository repository) {
		this.repository = repository;
	}
	
	/*
	 * Converter between Set of TableConstraintMetadata and TableConstraintMetadataListDTO
	 */
	private TableConstraintMetadataListDTO TableConstraintMetadataSetToListDTO(Set<TableConstraintMetadata> tableConstraintMetadata) {
		List<TableConstraintMetadataDTO> constraintsDTO = new ArrayList<>();
		
		if(tableConstraintMetadata != null) {
			for(TableConstraintMetadata c : tableConstraintMetadata) {
				constraintsDTO.add(TableConstraintMetadataMapper.INSTANCE.TableConstraintMetadataDTOToTableConstraintMetadata(c));
			}
		}
		
		return new TableConstraintMetadataListDTO(constraintsDTO);
	}
	
	/*
	 * get all DB constraints by table name (schema required)
	 */
	@Override
	public TableConstraintMetadataListDTO getAllConstraints(String tableName, String schemaName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new TableConstraintMetadataListDTO();
			
		return TableConstraintMetadataSetToListDTO(repository.getAllConstraintsByTable(tableName.toUpperCase(), schemaName.toUpperCase()));
	}

	/*
	 * get all DB unique constraints by table name (schema required)
	 */
	@Override
	public TableConstraintMetadataListDTO getAllUniqueConstraints(String tableName, String schemaName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new TableConstraintMetadataListDTO();
		
		return TableConstraintMetadataSetToListDTO(repository.getUniqueConstraintsByTable(tableName.toUpperCase(), schemaName.toUpperCase()));
	}

	/*
	 * get all DB Foreign Key constraints by table name (schema required)
	 */
	@Override
	public TableConstraintMetadataListDTO getAllForeignKeyConstraints(String tableName, String schemaName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new TableConstraintMetadataListDTO();
		
		return TableConstraintMetadataSetToListDTO(repository.getForeignKeyConstraintsByTable(tableName.toUpperCase(), schemaName.toUpperCase()));
	}
}
