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

/*
 * Service implementation of ColumnMetadataService interface
 */
@Service
public class ColumnMetadataServiceConcrete implements ColumnMetadataService {
	private ColumnMetadataRepository columnMetadataRepository;

	/* 
	 * Constructors
	 */
	public ColumnMetadataServiceConcrete(ColumnMetadataRepository columnMetadataRepository) {
		this.columnMetadataRepository = columnMetadataRepository;
	}
	
	/*
	 * Converter between Set of ColumnMetadata and ColumnMetadataListDTO
	 */
	private ColumnMetadataListDTO ColumnMetadataSetToListDTO(Set<ColumnMetadata> columnsMetadata) {
		List<ColumnMetadataDTO> columnsDTO = new ArrayList<>();
		
		if(columnsMetadata != null) {
			for(ColumnMetadata c : columnsMetadata) {
				columnsDTO.add(ColumnMetadataMapper.INSTANCE.ColumnMetadataToColumnMetadataDTO(c));
			}
		}
		
		return new ColumnMetadataListDTO(columnsDTO);
	}
	
	/*
	 * Get all columns which are present in database
	 */
	@Override
	public ColumnMetadataListDTO getAllColumns() {		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBColumns());
	}

	/*
	 * Get all column which are present in DB by theirs membership table (schema required)
	 */
	@Override
	public ColumnMetadataListDTO getAllColumnsByTable(String schemaName, String tableName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new ColumnMetadataListDTO();
		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBColumnsByTableAndSchema(schemaName, tableName));
	}

	/*
	 * Get only one column by its Name and membership table (schema required)
	 */
	@Override
	public ColumnMetadataDTO getColumnByNameAndTable(String schemaName, String tableName, String columnName) {
		if(schemaName==null || tableName==null || columnName==null || schemaName.equals("") || tableName.equals("") || columnName.equals(""))
			return new ColumnMetadataDTO();
		
		ColumnMetadata c = columnMetadataRepository.getDBColumnByNameAndTableAndSchema(schemaName, tableName, columnName);
		if(c == null) c = new ColumnMetadata();
		
		return ColumnMetadataMapper.INSTANCE.ColumnMetadataToColumnMetadataDTO(c);
	}

	/*
	 * Get all columns with not null constraint by theirs membership table (schema required)
	 */
	@Override
	public ColumnMetadataListDTO getColumnNotNullByTable(String schemaName, String tableName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new ColumnMetadataListDTO();
		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBNotNullColumnsByTableAndSchema(schemaName, tableName));
	}
	
	/*
	 * Get all columns without not null constraint by theirs membership table (schema required)
	 */
	@Override
	public ColumnMetadataListDTO getColumnNullableByTable(String schemaName, String tableName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new ColumnMetadataListDTO();
		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBNullableColumnsByTableAndSchema(schemaName, tableName));
	}
	
	/*
	 * Get all integer columns by theirs membership table (schema required)
	 */
	@Override
	public ColumnMetadataListDTO getIntegerColumnByTable(String schemaName, String tableName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new ColumnMetadataListDTO();
		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBIntegerColumnsByTableAndSchema(schemaName, tableName));
	}
	
	/*
	 * Get all columns with default value not null by theirs membership table (schema required)
	 */
	@Override
	public ColumnMetadataListDTO getColumnWithDefaultValueByTable(String schemaName, String tableName) {
		if(schemaName==null || tableName==null || schemaName.equals("") || tableName.equals(""))
			return new ColumnMetadataListDTO();
		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBColumnsWithDefaultValueByTableAndSchema(schemaName, tableName));
	}
	
	/*
	 * Get all columns by theirs data type and membership table (schema required)
	 */
	@Override
	public ColumnMetadataListDTO getColumnByTypeAndTable(String dataType, String schemaName, String tableName) {
		if(schemaName==null || tableName==null || dataType==null || schemaName.equals("") || tableName.equals("") || dataType.equals(""))
			return new ColumnMetadataListDTO();
		
		return ColumnMetadataSetToListDTO(columnMetadataRepository.getAllDBColumnsByDataTypeAndTable(dataType, schemaName, tableName));
	}
}
