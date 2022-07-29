package it.alten.tirocinio.controller.restController;

import static org.hamcrest.Matchers.hasSize;
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

import it.alten.tirocinio.api.DTO.entityDTO.SchemaDTO;
import it.alten.tirocinio.api.DTO.entityDTO.SchemaListDTO;
import it.alten.tirocinio.services.SchemaMetadataService;

public class SchemaControllerTest {
	@Mock
	private SchemaMetadataService schemaMetadataService;
	
	@InjectMocks
	private SchemaController schemaController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(schemaController).build();
	}
	
	@Test 
	public void getAllDatabaseSchemaTest() throws Exception {
		SchemaDTO s1 = new SchemaDTO();
		SchemaDTO s2 = new SchemaDTO();
		SchemaDTO s3 = new SchemaDTO();
		
		SchemaListDTO schemaList = new SchemaListDTO(Arrays.asList(s1, s2, s3));
		
		when(schemaMetadataService.getAllDatabaseSchema()).thenReturn(schemaList);
		
		mockMvc.perform(get("/api/schema/"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.schema_list", hasSize(3)));
		
		verify(schemaMetadataService, times(1)).getAllDatabaseSchema();
	}
}
