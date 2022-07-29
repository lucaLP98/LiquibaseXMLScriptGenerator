package it.alten.tirocinio.services.concrete.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataListDTO;
import it.alten.tirocinio.api.Mapper.TableConstraintMetadataMapper;
import it.alten.tirocinio.model.mysql.TableConstraintMetadataMySql;
import it.alten.tirocinio.repository.mysql.TableConstraintMetadataRepository;
import it.alten.tirocinio.services.TableConstraintMetadataService;

@Profile("mysql")
@Service
public class TableConstraintMetadataServiceMySql implements TableConstraintMetadataService {
	private TableConstraintMetadataRepository repository;
	
	/*
	 * Constructors
	 */
	public TableConstraintMetadataServiceMySql(TableConstraintMetadataRepository repository) {
		this.repository = repository;
	}
	
	/*
	 * Converter between Set of TableConstraintMetadataMySql and TableConstraintMetadataListDTO
	 */
	private TableConstraintMetadataListDTO TableConstraintMetadataSetToListDTO(Set<TableConstraintMetadataMySql> tableConstraintMetadataMySql) {
		List<TableConstraintMetadataDTO> constraintsDTO = new ArrayList<>();
		
		if(tableConstraintMetadataMySql != null) {
			for(TableConstraintMetadataMySql c : tableConstraintMetadataMySql) {
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
			
		return TableConstraintMetadataSetToListDTO(repository.getAllConstraintsByTable(tableName, schemaName));
	}

	/*
	 * get all DB unique constraints by table name (schema required)
	 */
	@Override
	public TableConstraintMetadataListDTO getAllUniqueConstraints(String tableName, String schemaName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new TableConstraintMetadataListDTO();
		
		return TableConstraintMetadataSetToListDTO(repository.getUniqueConstraintsByTable(tableName, schemaName));
	}

	/*
	 * get all DB Foreign Key constraints by table name (schema required)
	 */
	@Override
	public TableConstraintMetadataListDTO getAllForeignKeyConstraints(String tableName, String schemaName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new TableConstraintMetadataListDTO();
		
		return TableConstraintMetadataSetToListDTO(repository.getForeignKeyConstraintsByTable(tableName, schemaName));
	}
}
