package it.alten.tirocinio.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HomepageControllerTest {
	private HomepageController homepageController;
	
	@BeforeEach
	public void init() {
		homepageController = new HomepageController();
	}	
	
	@Test
    public void getHomepageTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homepageController).build();

        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("HomePage"));
    } 
}