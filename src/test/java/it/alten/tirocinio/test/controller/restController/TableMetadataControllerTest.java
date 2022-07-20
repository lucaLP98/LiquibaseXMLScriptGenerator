package it.alten.tirocinio.test.controller.restController;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.alten.tirocinio.controller.restController.TableMetadataController;
import it.alten.tirocinio.services.TableMetadataService;

public class TableMetadataControllerTest {
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/api/tables/";
		
	@Mock
	private TableMetadataService tableMetadataService;

	@InjectMocks
	private TableMetadataController tableMetadataController;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tableMetadataController).build();
	}
}
