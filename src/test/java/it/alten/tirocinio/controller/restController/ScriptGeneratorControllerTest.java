package it.alten.tirocinio.controller.restController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import it.alten.tirocinio.api.DTO.scriptDTO.AddAutoIncrementScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddDefaultValueScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddForeignKeyConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddNotNullConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddUniqueConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateSchemaScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DeleteDataScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropDefaultValueScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropForeignKeyConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropNotNullConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropUniqueConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.InsertDataScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.ModifyColumnDataTypeScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.ScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.UpdateDataScriptDTO;
import it.alten.tirocinio.services.ScriptGeneratorService;

public class ScriptGeneratorControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private ScriptGeneratorService scriptGeneratorService;

	@InjectMocks
	private ScriptGeneratorController scriptGeneratorController;
	
	private final String BASE_URL = "/api/scriptGenerator/";
	private final String SCRIPT_TEST_STRING = "<dbchangelog>...</dbchangelog>";
	private final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
		
	private void initScriptDTOForTesting(ScriptDTO script) {
		script.setAddToChangelog(false);
		script.setAuthor("lp");
		script.setChangeLog(false);
		script.setIdChangeset("test1");
		script.setOnError("HALT");
		script.setOnFail("HALT");
	}
	
	private String jsonToString(Object o) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson= ow.writeValueAsString(o);
	    return requestJson;
	}	
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(scriptGeneratorController).build();
	}
	
	@Test
	public void generateDropTableScriptRequestTest() throws JsonProcessingException, Exception {
		DropTableScriptDTO scriptDTO = new DropTableScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setCascadeConstraint("CASCADE");
		scriptDTO.setTableName("tabTest");
		scriptDTO.setTableSchema("demo");
		
		when(scriptGeneratorService.generateDropTableLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);
				
		MvcResult result = mockMvc.perform(post(BASE_URL + "dropTableScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateDropTableLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateDropColumnScriptRequestTest() throws JsonProcessingException, Exception {
		DropColumnScriptDTO scriptDTO = new DropColumnScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setColumnName("col1");
		scriptDTO.setTableName("tab1");
		scriptDTO.setTableSchema("demo");
		
		when(scriptGeneratorService.generateDropColumnLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "dropColumnScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateDropColumnLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateCreateTableScriptRequestTest() throws JsonProcessingException, Exception {
		CreateTableScriptDTO scriptDTO = new CreateTableScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setTableName("tab1");
		scriptDTO.setTableSchema("demo");
		scriptDTO.setPrimaryKeyName("pk1");
		scriptDTO.setPrimaryKeyType("int");	
		
		when(scriptGeneratorService.generateCreateTableLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "createTableScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateCreateTableLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateCreateSchemaScriptRequestTest() throws JsonProcessingException, Exception {
		CreateSchemaScriptDTO scriptDTO = new CreateSchemaScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		
		when(scriptGeneratorService.generateCreateSchemaLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "createSchemaScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateCreateSchemaLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateAddColumnScriptRequestTest() throws JsonProcessingException, Exception {
		AddColumnScriptDTO scriptDTO = new AddColumnScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setColumnName("col1");
		scriptDTO.setColumnType("int");
		scriptDTO.setIsNullable(false);
		scriptDTO.setTableName("tab");
		scriptDTO.setUnique(false);
		
		when(scriptGeneratorService.generateAddColumnLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "addColumnScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateAddColumnLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateDropNotNullConstraintScriptRequestTest() throws JsonProcessingException, Exception {
		DropNotNullConstraintScriptDTO scriptDTO = new DropNotNullConstraintScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setTableSchema("demo");
		scriptDTO.setColumnName("col1");
		scriptDTO.setTableName("tab");
		scriptDTO.setColumnDataType("int");
		
		when(scriptGeneratorService.generateDropNotNullConstraintLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "dropNotNullConstraintScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateDropNotNullConstraintLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateAddNotNullConstraintScriptRequestTest() throws JsonProcessingException, Exception {
		AddNotNullConstraintScriptDTO scriptDTO = new AddNotNullConstraintScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setTableSchema("demo");
		scriptDTO.setColumnName("col1");
		scriptDTO.setTableName("tab");
		scriptDTO.setColumnDataType("int");
		
		when(scriptGeneratorService.generateAddNotNullConstraintLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "addNotNullConstraintScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateAddNotNullConstraintLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateAddUniqueConstraintScriptRequestTest() throws JsonProcessingException, Exception {
		AddUniqueConstraintScriptDTO scriptDTO = new AddUniqueConstraintScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setTableSchema("demo");
		scriptDTO.setTableName("tab");
		scriptDTO.setConstrainName("con1");
		AddUniqueConstraintScriptDTO.ColumnUnique c1 = new AddUniqueConstraintScriptDTO.ColumnUnique();
		c1.setColumnName("col1");
		List<AddUniqueConstraintScriptDTO.ColumnUnique> list = new ArrayList<>();
		list.add(c1);
		scriptDTO.setColumns(list);
		
		when(scriptGeneratorService.generateAddUniqueConstraintLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "addUniqueConstraintScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateAddUniqueConstraintLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateDropUniqueConstraintScriptRequestTest() throws JsonProcessingException, Exception {
		DropUniqueConstraintScriptDTO scriptDTO = new DropUniqueConstraintScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setTableSchema("demo");
		scriptDTO.setConstrainName("con");
		scriptDTO.setTableName("tab");
		
		when(scriptGeneratorService.generateDropUniqueConstraintLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "dropUniqueConstraintScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateDropUniqueConstraintLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateRenameTableScriptRequestTest() throws JsonProcessingException, Exception {
		RenameTableScriptDTO scriptDTO = new RenameTableScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setNewTableName("newName");
		scriptDTO.setOldTableName("oldName");
		scriptDTO.setSchemaName("demo");
		
		when(scriptGeneratorService.generateRenameTableLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "renameTableScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateRenameTableLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateRenameColumnScriptRequestTest() throws JsonProcessingException, Exception {
		RenameColumnScriptDTO scriptDTO = new RenameColumnScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setOldColumnName("oldName");
		scriptDTO.setTableName("tab");
		scriptDTO.setNewColumnName("newName");
		scriptDTO.setColumnType("int");
		
		when(scriptGeneratorService.generateRenameColumnLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "renameColumnScript/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateRenameColumnLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateModifyColumnDataTypeScriptRequestTest() throws JsonProcessingException, Exception {
		ModifyColumnDataTypeScriptDTO scriptDTO = new ModifyColumnDataTypeScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setColumnName("col1");
		scriptDTO.setTableName("tab");
		scriptDTO.setOldColumnType("int");
		scriptDTO.setNewColumnType("float");
		
		when(scriptGeneratorService.generateModifyColumnDataTypeLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "modifyColumnDataType/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateModifyColumnDataTypeLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateAddAutoIncrementScriptRequestTest() throws JsonProcessingException, Exception {
		AddAutoIncrementScriptDTO scriptDTO = new AddAutoIncrementScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setColumnName("col1");
		scriptDTO.setTableName("tab");
		scriptDTO.setIncrementBy(1);
		scriptDTO.setStartWith(1);
		
		when(scriptGeneratorService.generateAddAutoIncrementLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "addAutoIncrement/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateAddAutoIncrementLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateAddDefaultValueScriptRequestTest() throws JsonProcessingException, Exception {
		AddDefaultValueScriptDTO scriptDTO = new AddDefaultValueScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setColumnName("col1");
		scriptDTO.setTableName("tab");
		scriptDTO.setColumnType("int");
		scriptDTO.setDefaultValue("default");
		
		when(scriptGeneratorService.generateAddDefaultValueLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "addDefaultValue/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateAddDefaultValueLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateDropDefaultValueScriptRequestTest() throws JsonProcessingException, Exception {
		DropDefaultValueScriptDTO scriptDTO = new DropDefaultValueScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setColumnName("col1");
		scriptDTO.setTableName("tab");
		scriptDTO.setColumnType("int");
		scriptDTO.setDefaultValue("default");
		
		when(scriptGeneratorService.generateDropDefaultValueLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "dropDefaultValue/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateDropDefaultValueLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateAddForeignKeyConstraintScriptRequestTest() throws JsonProcessingException, Exception {
		AddForeignKeyConstraintScriptDTO scriptDTO = new AddForeignKeyConstraintScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setBaseColumnName("col1");
		scriptDTO.setBaseSchemaName("demo1");
		scriptDTO.setBaseTableName("tab1");
		scriptDTO.setConstraintName("con1");
		scriptDTO.setOnDelete("NO ACTION");
		scriptDTO.setOnUpdate("NO ACTION");
		scriptDTO.setReferencedColumnName("refCol1");
		scriptDTO.setReferencedSchemaName("demo1");
		scriptDTO.setReferencedTableName("refTab1");
		
		when(scriptGeneratorService.generateAddForeignKeyConstraintLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "addForeignKeyConstraint/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateAddForeignKeyConstraintLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateDropForeignKeyConstraintScriptRequestTest() throws JsonProcessingException, Exception {
		DropForeignKeyConstraintScriptDTO scriptDTO = new DropForeignKeyConstraintScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setBaseSchemaName("demo1");
		scriptDTO.setBaseTableName("tab1");
		scriptDTO.setConstraintName("con1");
		
		when(scriptGeneratorService.generateDropForeignKeyConstraintLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "dropForeignKeyConstraint/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateDropForeignKeyConstraintLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateDeleteScriptRequestTest() throws JsonProcessingException, Exception {
		DeleteDataScriptDTO scriptDTO = new DeleteDataScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setTableName("tab");
		
		when(scriptGeneratorService.generateDeleteDataLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "deleteData/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateDeleteDataLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateUpdateScriptRequestTest() throws JsonProcessingException, Exception {
		UpdateDataScriptDTO scriptDTO = new UpdateDataScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setTableName("tab");
		scriptDTO.setColumns(new HashMap<String, String>());
		
		when(scriptGeneratorService.generateUpdateDataLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "updateData/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateUpdateDataLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
	
	@Test 
	public void generateInsertScriptRequestTest() throws JsonProcessingException, Exception {
		InsertDataScriptDTO scriptDTO = new InsertDataScriptDTO();
		initScriptDTOForTesting(scriptDTO);
		scriptDTO.setSchemaName("demo");
		scriptDTO.setTableName("tab");
		scriptDTO.setColumns(new HashMap<String, String>());
		
		when(scriptGeneratorService.generateInsertDataLiquibaseXMLScript(scriptDTO, true)).thenReturn(SCRIPT_TEST_STRING);

		MvcResult result = mockMvc.perform(post(BASE_URL + "insertData/")
				.contentType(APPLICATION_JSON_UTF8)
				.content(jsonToString(scriptDTO)))
				.andExpect(status().isOk())
				.andReturn();
		
		verify(scriptGeneratorService, times(1)).generateInsertDataLiquibaseXMLScript(scriptDTO, true);
		
		assertEquals(SCRIPT_TEST_STRING, result.getResponse().getContentAsString());
	}
}	
