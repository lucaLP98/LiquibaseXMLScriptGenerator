package it.alten.tirocinio.test.liquibaseChangeElement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;
import it.alten.tirocinio.liquibaseChangeElement.ChangeSet;

public class ChangeLogTest {
	private ChangeLog changeLog;
	private Document document;
	
	@BeforeEach
	public void init() throws ParserConfigurationException {
		changeLog = new ChangeLog();
		
		document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		changeLog.setChangeLogDocument(document);
		changeLog.setCreated(true);
		changeLog.createChangeLog(document.createElement("databaseChangeLog"));
	}
	
	@Test
	public void addChangeSetToChangeLogTest_nullParameter() {
		assertFalse(changeLog.addChangeSetToChangeLog(null));
	}
	
	@Test
	@Transactional
	public void addChangeSetToChangeLogTest_newChangeSet() {
		ChangeSet changeSet = new ChangeSet(document, "set-1");
		changeSet.setChangeSet(document.createElement("changeSet"));
		assertTrue(changeLog.addChangeSetToChangeLog(changeSet));
	}
	
	@Test
	@Transactional
	public void addChangeSetToChangeLogTest_existingChangeSet() {
		ChangeSet cs1 = new ChangeSet(document, "set-1");
		cs1.setChangeSet(document.createElement("changeSet"));
		
		ChangeSet cs2 = new ChangeSet(document, "set-1");
		cs2.setChangeSet(document.createElement("changeSet"));
				
		assertTrue(changeLog.addChangeSetToChangeLog(cs1));
		assertFalse(changeLog.addChangeSetToChangeLog(cs2));
	}
}
