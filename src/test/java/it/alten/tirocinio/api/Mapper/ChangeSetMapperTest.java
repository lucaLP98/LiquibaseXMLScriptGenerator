package it.alten.tirocinio.api.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetDTO;
import it.alten.tirocinio.liquibaseChangeElement.ChangeSet;

public class ChangeSetMapperTest {
	private ChangeSetMapper mapper;
	
	private final String CHANGESET_ID = "cs-1";
	
	@BeforeEach
	public void init() {
		mapper = ChangeSetMapper.INSTANCE;
	}
	
	@Test
	public void changeSetToChangeSetDTOTest() throws ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		ChangeSet entity = new ChangeSet(document, CHANGESET_ID);
		
		ChangeSetDTO dto = mapper.changeSetToChangeSetDTO(entity);
		assertEquals(CHANGESET_ID, dto.getChangeSetId());
	}
	
	@Test
	public void changeSetToChangeSetDTOTest_nullParameter() {
		ChangeSetDTO dto = mapper.changeSetToChangeSetDTO(null);
		assertNull(dto);
	}
}
