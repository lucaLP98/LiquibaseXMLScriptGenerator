package it.alten.tirocinio.test.controller.restController;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
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
import it.alten.tirocinio.controller.restController.ColumnMetadataController;
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
	}
	
	@Test
	public void gettAllColumnsByTableTest() throws Exception {
		when(columnMetadataService.getAllColumnsByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/byTable/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
	}
	
	@Test
	public void getColumnsByNameAndTableTest() throws Exception {
		when(columnMetadataService.getColumnByNameAndTable(anyString(), anyString(), anyString())).thenReturn(new ColumnMetadataDTO());
		
		mockMvc.perform(get(BASE_URL + "/byName/"+anyString()+"&"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getColumnsNotNullByTableTest() throws Exception {
		when(columnMetadataService.getColumnNotNullByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/NotNull/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
	}
	
	@Test
	public void getColumnsNullableByTableTest() throws Exception {
		when(columnMetadataService.getColumnNullableByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/Null/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
	}
	
	@Test
	public void getIntegerColumnsByTableTest() throws Exception {
		when(columnMetadataService.getIntegerColumnByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/Integer/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
	}
	
	@Test
	public void getColumnsWithDefaultByTableTest() throws Exception {
		when(columnMetadataService.getColumnWithDefaultValueByTable(anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/WithDefaultValue/"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
	}
	
	@Test
	public void getColumnsByDataTypeAndTableTest() throws Exception {
		when(columnMetadataService.getColumnByTypeAndTable(anyString(), anyString(), anyString())).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "/byDataType/"+anyString()+"&"+anyString()+"&"+anyString()))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.column_list", hasSize(3)));
	}
}
