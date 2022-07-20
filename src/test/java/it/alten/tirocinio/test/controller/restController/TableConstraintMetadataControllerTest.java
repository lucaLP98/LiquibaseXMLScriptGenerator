package it.alten.tirocinio.test.controller.restController;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.alten.tirocinio.controller.restController.TableConstraintMetadataController;
import it.alten.tirocinio.services.TableConstraintMetadataService;

public class TableConstraintMetadataControllerTest {
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/api/constraint/";
		
	@Mock
	private TableConstraintMetadataService tableConstraintMetadataService;

	@InjectMocks
	private TableConstraintMetadataController tableConstraintMetadataController;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tableConstraintMetadataController).build();
	}
}
