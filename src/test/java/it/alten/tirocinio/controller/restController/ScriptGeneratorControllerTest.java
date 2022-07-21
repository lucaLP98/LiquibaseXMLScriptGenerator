package it.alten.tirocinio.controller.restController;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import it.alten.tirocinio.services.ScriptGeneratorService;

public class ScriptGeneratorControllerTest {
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/api/scriptGenerator/";
		
	@Mock
	private ScriptGeneratorService scriptGeneratorService;

	@InjectMocks
	private ScriptGeneratorController scriptGeneratorController;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(scriptGeneratorController).build();
	}
}	
