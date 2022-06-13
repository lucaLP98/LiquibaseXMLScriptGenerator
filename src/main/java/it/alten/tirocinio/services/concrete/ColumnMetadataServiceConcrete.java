package it.alten.tirocinio.services.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataListDTO;
import it.alten.tirocinio.api.Mapper.ColumnMetadataMapper;
import it.alten.tirocinio.repository.ColumnMetadataRepository;
import it.alten.tirocinio.services.ColumnMetadataService;
import it.alten.tirocinio.model.ColumnMetadata;

@Service
public class ColumnMetadataServiceConcrete implements ColumnMetadataService {
	private ColumnMetadataRepository columnMetadataRepository;

	public ColumnMetadataServiceConcrete(ColumnMetadataRepository columnMetadataRepository) {
		this.columnMetadataRepository = columnMetadataRepository;
	}
	
	private ColumnMetadataListDTO ColumnMetadataSetToListDTO(Set<ColumnMetadata> columnsMetadata) {
		List<ColumnMetadataDTO> columnsDTO = new ArrayList<>();
		
		for(ColumnMetadata c : columnsMetadata) {
			columnsDTO.add(ColumnMetadataMapper.INSTANCE.ColumnMetadataToColumnMetadataDTO(c));
		}
		
		return new ColumnMetadataListDTO(columnsDTO);
	}
	
	@Override
	public ColumnMetadataListDTO getAllColumns() {		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBColumns());
	}

	@Override
	public ColumnMetadataListDTO getAllColumnsByTable(String schemaName, String tableName) {
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBColumnsByTableAndSchema(schemaName, tableName));
	}

	@Override
	public ColumnMetadataDTO getColumnByNameAndTable(String schemaName, String tableName, String columnName) {
		return ColumnMetadataMapper.INSTANCE.ColumnMetadataToColumnMetadataDTO(columnMetadataRepository.getDBColumnByNameAndTableAndSchema(schemaName, tableName, columnName));
	}

}
