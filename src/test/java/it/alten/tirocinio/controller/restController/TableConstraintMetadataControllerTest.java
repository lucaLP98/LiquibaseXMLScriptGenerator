package it.alten.tirocinio.controller.restController;

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

import static org.hamcrest.Matchers.hasSize;

import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataDTO;
import it.alten.tirocinio.api.DTO.entityDTO.TableConstraintMetadataListDTO;
import it.alten.tirocinio.services.TableConstraintMetadataService;

public class TableConstraintMetadataControllerTest {
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/api/constraint/";
		
	@Mock
	private TableConstraintMetadataService tableConstraintMetadataService;

	@InjectMocks
	private TableConstraintMetadataController tableConstraintMetadataController;
	
	private TableConstraintMetadataListDTO list;
	private String schemaNameTest;
	private String tableNameTest;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tableConstraintMetadataController).build();

		list = new TableConstraintMetadataListDTO(Arrays.asList(
				new TableConstraintMetadataDTO(), 
				new TableConstraintMetadataDTO(), 
				new TableConstraintMetadataDTO()));
	
		schemaNameTest = "demo";
		tableNameTest = "tab1";
	}
	
	@Test
	public void getAllUniqueConstraintsTest() throws Exception {
		when(tableConstraintMetadataService.getAllUniqueConstraints(tableNameTest, schemaNameTest)).thenReturn(list);

		mockMvc.perform(get(BASE_URL + "unique/"+schemaNameTest+"&"+tableNameTest))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.constraint_list", hasSize(3)));
	}

	@Test
	public void getAllForeignKeyConstraintsTest() throws Exception {
		when(tableConstraintMetadataService.getAllForeignKeyConstraints(tableNameTest, schemaNameTest)).thenReturn(list);
	
		mockMvc.perform(get(BASE_URL + "foreignkey/"+schemaNameTest+"&"+tableNameTest))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.constraint_list", hasSize(3)));
	}
	
	@Test
	public void getAllConstraintsTest() throws Exception {
		when(tableConstraintMetadataService.getAllConstraints(tableNameTest, schemaNameTest)).thenReturn(list);
		
		mockMvc.perform(get(BASE_URL + "all/"+schemaNameTest+"&"+tableNameTest))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.constraint_list", hasSize(3)));
	} 
}
