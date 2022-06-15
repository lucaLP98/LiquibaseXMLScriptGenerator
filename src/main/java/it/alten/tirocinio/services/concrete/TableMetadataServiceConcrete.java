package it.alten.tirocinio.services.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataListDTO;
import it.alten.tirocinio.api.Mapper.TableMetadataMapper;
import it.alten.tirocinio.model.TableMetadata;
import it.alten.tirocinio.repository.TableMetadataRepository;
import it.alten.tirocinio.services.TableMetadataService;

/*
 * Service implementation of TableMetadataService interface
 */
@Service
public class TableMetadataServiceConcrete implements TableMetadataService {
	private final TableMetadataRepository tableMetadataRepository;
	
	/*
	 * Constructors
	 */
	public TableMetadataServiceConcrete(TableMetadataRepository tableMetadataRepository) {
		this.tableMetadataRepository = tableMetadataRepository;
	}
	
	/*
	 * Converter between set of TableMetadata and TableMetadataListDTO
	 */
	private TableMetadataListDTO TableMetadataSetToListDTO(Set<TableMetadata> tablesMetadata) {
		List<TableMetadataDTO> tablesDTO = new ArrayList<>();
		
		for(TableMetadata t: tablesMetadata) {
			tablesDTO.add(TableMetadataMapper.INSTANCE.tableMetadataToTableMetadataDTO(t));
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
		return TableMetadataSetToListDTO(tableMetadataRepository.getAllDBTablesBySchema(schemaName));
	}

	/*
	 * Get only one table by its Name and membership schema
	 */
	@Override
	public TableMetadataDTO getTableByNameAndSchema(String schemaName, String tableName) {
		return TableMetadataMapper.INSTANCE.tableMetadataToTableMetadataDTO(tableMetadataRepository.getDBTablesByNameAndSchema(schemaName, tableName));
	}

}
