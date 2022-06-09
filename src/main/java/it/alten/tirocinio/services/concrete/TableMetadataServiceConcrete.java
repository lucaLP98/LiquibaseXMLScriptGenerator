package it.alten.tirocinio.services.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.TableMetadataDTO;
import it.alten.tirocinio.api.DTO.TableMetadataListDTO;
import it.alten.tirocinio.api.Mapper.TableMetadataMapper;
import it.alten.tirocinio.model.TableMetadata;
import it.alten.tirocinio.repository.TableMetadataRepository;
import it.alten.tirocinio.services.TableMetadataService;

@Service
public class TableMetadataServiceConcrete implements TableMetadataService {
	private final TableMetadataRepository tableMetadataRepository;
	
	public TableMetadataServiceConcrete(TableMetadataRepository tableMetadataRepository) {
		this.tableMetadataRepository = tableMetadataRepository;
	}
	
	private TableMetadataListDTO TableMetadataSetToListDTO(Set<TableMetadata> tablesMetadata) {
		List<TableMetadataDTO> tablesDTO = new ArrayList<>();
		
		for(TableMetadata t: tablesMetadata) {
			tablesDTO.add(TableMetadataMapper.INSTANCE.tableMetadataToTableMetadataDTO(t));
		}
		
		return new TableMetadataListDTO(tablesDTO);
	}

	@Override
	public TableMetadataListDTO getAllTables() {
		return TableMetadataSetToListDTO(tableMetadataRepository.getAllDBTables());
	}

	@Override
	public TableMetadataListDTO getAllTablesBySchema(String schemaName) {
		return TableMetadataSetToListDTO(tableMetadataRepository.getAllDBTablesBySchema(schemaName));
	}

	@Override
	public TableMetadataDTO getTableByNameAndSchema(String schemaName, String tableName) {
		return TableMetadataMapper.INSTANCE.tableMetadataToTableMetadataDTO(tableMetadataRepository.getDBTablesByNameAndSchema(schemaName, tableName));
	}

}
