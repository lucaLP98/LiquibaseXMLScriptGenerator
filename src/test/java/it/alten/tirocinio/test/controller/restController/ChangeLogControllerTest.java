package it.alten.tirocinio.test.controller.restController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import static org.hamcrest.Matchers.hasSize;

import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetDTO;
import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetListDTO;
import it.alten.tirocinio.controller.restController.ChangeLogController;
import it.alten.tirocinio.services.ChangeLogService;

public class ChangeLogControllerTest {
	@Mock
	private ChangeLogService changeLogService;
	
	@InjectMocks
	private ChangeLogController changeLogController;
	
	private MockMvc mockMvc;
	
	private final String BASE_URL = "/changeLog/";
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(changeLogController).build();
	}
	
	private String jsonToString(Object o) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson= ow.writeValueAsString(o);
	    return requestJson;
	}
	
	@Test
	public void viewChangeLogTest() throws Exception {
		when(changeLogService.printChangeLog()).thenReturn("changeLogString");
		
		MvcResult result = mockMvc.perform(get(BASE_URL + "viewChangeLog"))
				.andExpect(status().isOk())
				.andReturn();
		
		assertEquals(result.getResponse().getContentAsString(), "changeLogString");
	}
	
	@Test
	public void getChangeSetListTest() throws Exception {
		ChangeSetDTO changeset1 = new ChangeSetDTO();
		changeset1.setChangeSetId("1-test");
		
		ChangeSetDTO changeset2 = new ChangeSetDTO();
		changeset2.setChangeSetId("2-test");
		
		ChangeSetListDTO changeSetList = new ChangeSetListDTO(Arrays.asList(changeset1, changeset2));
		
		when(changeLogService.getAllChangeSet()).thenReturn(changeSetList);
		
		mockMvc.perform(get(BASE_URL + "getChangeSetList"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.changeset_list", hasSize(2)));
	}
	
	@Test
	public void createChangeLogTest() throws Exception {
		when(changeLogService.createNewChangeLog()).thenReturn(true);
		
		MvcResult result = mockMvc.perform(post(BASE_URL + "createChangeLog"))
				.andExpect(status().isCreated())
				.andReturn();
		
		assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}
	
	@Test
	public void removeChangeSetTest_NullParameter(){
		assertFalse(changeLogController.removeChangeSet(null));
	}
	
	@Test
	public void removeChangeSetTest() throws Exception{
		when(changeLogService.removeChangeSet(anyString())).thenReturn(true);
		
		MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
		
		ChangeSetDTO changeSet = new ChangeSetDTO();
		changeSet.setChangeSetId("set-1");
		
		MvcResult result = mockMvc.perform(delete(BASE_URL + "removeChangeSet/")
					.contentType(APPLICATION_JSON_UTF8)
					.content(jsonToString(changeSet)))
					.andExpect(status().isOk())
				.andReturn();
		
		assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
	}
	
	@Test
	public void closeChangeLogTest() throws Exception{
		mockMvc.perform(delete(BASE_URL + "closeChangeLog")).andExpect(status().isOk());
	}
}
