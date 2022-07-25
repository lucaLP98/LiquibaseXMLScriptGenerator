package it.alten.tirocinio.controller.restController;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableMetadataListDTO;
import it.alten.tirocinio.services.TableMetadataService;

public class TableMetadataControllerTest {
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/api/tables/";
		
	@Mock
	private TableMetadataService tableMetadataService;

	@InjectMocks
	private TableMetadataController tableMetadataController;
	
	private TableMetadataListDTO list;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tableMetadataController).build();
		
		TableMetadataDTO t1 = new TableMetadataDTO();
		TableMetadataDTO t2 = new TableMetadataDTO();
		TableMetadataDTO t3 = new TableMetadataDTO();
		
		list = new TableMetadataListDTO(Arrays.asList(t1, t2, t3));
	}
	
	@Test
	public void getAllTablesTest() throws Exception {
		when(tableMetadataService.getAllTables()).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "all/"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.table_list", hasSize(3)));
		
		verify(tableMetadataService, times(1)).getAllTables();
	}
	
	@Test
	public void getAllTablesBySchemaTest() throws Exception {
		String testString = "demo";
		when(tableMetadataService.getAllTablesBySchema(testString)).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "bySchema/"+testString))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.table_list", hasSize(3)));
		
		verify(tableMetadataService, times(1)).getAllTablesBySchema(testString);
	}
	
	@Test
	public void getAllTablesByNameAndSchemaTest() throws Exception {
		String schemaTestString = "tab1";
		String tableTestString = "demo";
		
		TableMetadataDTO tableMetadata = new TableMetadataDTO();
		tableMetadata.setTableName(tableTestString);
		tableMetadata.setTableSchema(schemaTestString);
		
		when(tableMetadataService.getTableByNameAndSchema(schemaTestString, tableTestString)).thenReturn(tableMetadata);
		
		mockMvc.perform(get(BASE_URL + "byName/"+schemaTestString+"."+tableTestString))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.table_schema", equalTo(schemaTestString)))
			.andExpect(jsonPath("$.table_name", equalTo(tableTestString)));
		
		verify(tableMetadataService, times(1)).getTableByNameAndSchema(schemaTestString, tableTestString);
	}
}
