package it.alten.tirocinio.services.concrete.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataListDTO;
import it.alten.tirocinio.api.Mapper.TableMetadataMapper;
import it.alten.tirocinio.model.mysql.TableMetadataMySql;
import it.alten.tirocinio.repository.mysql.TableMetadataRepository;
import it.alten.tirocinio.services.TableMetadataService;

/*
 * Service implementation of TableMetadataService interface
 */
@Profile("mysql")
@Service
public class TableMetadataServiceMySql implements TableMetadataService {
	private final TableMetadataRepository tableMetadataRepository;
	
	/*
	 * Constructors
	 */
	public TableMetadataServiceMySql(TableMetadataRepository tableMetadataRepository) {
		this.tableMetadataRepository = tableMetadataRepository;
	}
	
	/*
	 * Converter between set of TableMetadataMySql and TableMetadataListDTO
	 */
	private TableMetadataListDTO TableMetadataSetToListDTO(Set<TableMetadataMySql> tablesMetadata) {
		List<TableMetadataDTO> tablesDTO = new ArrayList<>();
		
		if(tablesMetadata != null) {
			for(TableMetadataMySql t: tablesMetadata) {
				tablesDTO.add(TableMetadataMapper.INSTANCE.tableMetadataToTableMetadataDTO(t));
			}
		}
		
		return new TableMetadataListDTO(tablesDTO);
	}

	/*
	 * Get all tables which are present in database
	 */
	@Override
	public TableMetadataListDTO getAllTables() {
		return TableMetadataSetToListDTO(tableMetadataRepository.getAllDBTables());
	}

	/*
	 * Get all tables which are present in DB by its membership schema
	 */
	@Override
	public TableMetadataListDTO getAllTablesBySchema(String schemaName) {
		if(schemaName == null || schemaName.equals(""))	return new TableMetadataListDTO();
		
		return TableMetadataSetToListDTO(tableMetadataRepository.getAllDBTablesBySchema(schemaName));
	}

	/*
	 * Get only one table by its Name and membership schema
	 */
	@Override
	public TableMetadataDTO getTableByNameAndSchema(String schemaName, String tableName) {
		TableMetadataMySql t = tableMetadataRepository.getDBTablesByNameAndSchema(schemaName, tableName);
		if(t == null) t = new TableMetadataMySql();
		
		return TableMetadataMapper.INSTANCE.tableMetadataToTableMetadataDTO(t);
	}

}
