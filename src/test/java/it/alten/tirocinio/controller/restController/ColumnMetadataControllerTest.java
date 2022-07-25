package it.alten.tirocinio.controller.restController;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.ColumnMetadataListDTO;
import it.alten.tirocinio.services.ColumnMetadataService;

public class ColumnMetadataControllerTest {
	@Mock
	private ColumnMetadataService columnMetadataService;
	
	@InjectMocks
	private ColumnMetadataController columnMetadataController;
	
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/api/columns/";
	
	private ColumnMetadataListDTO list;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(columnMetadataController).build();
		
		ColumnMetadataDTO c1 = new ColumnMetadataDTO();
		ColumnMetadataDTO c2 = new ColumnMetadataDTO();
		ColumnMetadataDTO c3 = new ColumnMetadataDTO();
		list = new ColumnMetadataListDTO(Arrays.asList(c1, c2, c3));
	}
	
	@Test
	public void getAllColumnsTest() throws Exception {
		when(columnMetadataService.getAllColumns()).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "all"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
		
		verify(columnMetadataService, times(1)).getAllColumns();
	}
	
	@Test
	public void gettAllColumnsByTableTest() throws Exception {
		when(columnMetadataService.getAllColumnsByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/byTable/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
		
		verify(columnMetadataService, times(1)).getAllColumnsByTable(anyString(), anyString());
	}
	
	@Test
	public void getColumnsByNameAndTableTest() throws Exception {
		String columnNameTestString = "col1";
		String columnTypeTestString = "INTEGER";
		String columnTableTestString = "tab1";
		String columnSchemaTestString = "demo1";
		
		ColumnMetadataDTO columnMetadati = new ColumnMetadataDTO();
		columnMetadati.setColumnName(columnNameTestString);
		columnMetadati.setColumnType(columnTypeTestString);
		columnMetadati.setTableName(columnTableTestString);
		columnMetadati.setTableSchema(columnSchemaTestString);
		when(columnMetadataService.getColumnByNameAndTable(columnSchemaTestString, columnTableTestString, columnNameTestString)).thenReturn(columnMetadati);
		
		mockMvc.perform(get(BASE_URL + "/byName/"+columnSchemaTestString+"&"+columnTableTestString+"&"+columnNameTestString))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_name", equalTo(columnNameTestString)))
			.andExpect(jsonPath("$.table_name", equalTo(columnTableTestString)))
			.andExpect(jsonPath("$.table_schema", equalTo(columnSchemaTestString)))
			.andExpect(jsonPath("$.column_type", equalTo(columnTypeTestString)));
		
		verify(columnMetadataService, times(1)).getColumnByNameAndTable(columnSchemaTestString, columnTableTestString, columnNameTestString);
	}
	
	@Test
	public void getColumnsNotNullByTableTest() throws Exception {
		when(columnMetadataService.getColumnNotNullByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/NotNull/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
		
		verify(columnMetadataService, times(1)).getColumnNotNullByTable(anyString(), anyString());
	}
	
	@Test
	public void getColumnsNullableByTableTest() throws Exception {
		when(columnMetadataService.getColumnNullableByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/Null/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
		
		verify(columnMetadataService, times(1)).getColumnNullableByTable(anyString(), anyString());
	}
	
	@Test
	public void getIntegerColumnsByTableTest() throws Exception {
		when(columnMetadataService.getIntegerColumnByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/Integer/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
		
		verify(columnMetadataService, times(1)).getIntegerColumnByTable(anyString(), anyString());
	}
	
	@Test
	public void getColumnsWithDefaultByTableTest() throws Exception {
		when(columnMetadataService.getColumnWithDefaultValueByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/WithDefaultValue/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
		
		verify(columnMetadataService, times(1)).getColumnWithDefaultValueByTable(anyString(), anyString());
	}
	
	@Test
	public void getColumnsByDataTypeAndTableTest() throws Exception {
		when(columnMetadataService.getColumnByTypeAndTable(anyString(), anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/byDataType/"+anyString()+"&"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
		
		verify(columnMetadataService, times(1)).getColumnByTypeAndTable(anyString(), anyString(), anyString());
	}
}
