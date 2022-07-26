package it.alten.tirocinio.liquibaseChangeElement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

public class ChangeLogTest {
	private ChangeLog createTestChangeLog() throws ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		ChangeLog changeLog = new ChangeLog();
		changeLog.setChangeLogDocument(document);
		changeLog.setCreated(true);
		changeLog.createChangeLog(document.createElement("databaseChangeLog"));
		
		return changeLog;
	}
	
	/*
	 * Testing for method addChangeSetToChangeLog
	 */
	@Test
	public void addChangeSetToChangeLogTest_nullParameter() throws ParserConfigurationException {
		ChangeLog changeLog = createTestChangeLog();
		assertFalse(changeLog.addChangeSetToChangeLog(null));
	}
	
	@Test
	public void addChangeSetToChangeLogTest_newChangeSet() throws ParserConfigurationException {
		ChangeLog changeLog = createTestChangeLog();
		Document document = changeLog.getChangeLogDocument();
		
		ChangeSet changeSet = new ChangeSet(document, "set-1");
		changeSet.setChangeSet(document.createElement("changeSet"));
		
		assertTrue(changeLog.addChangeSetToChangeLog(changeSet));
	}
	
	@Test
	public void addChangeSetToChangeLogTest_existingChangeSet()  throws ParserConfigurationException {
		ChangeLog changeLog = createTestChangeLog();
		Document document = changeLog.getChangeLogDocument();
		
		ChangeSet cs1 = new ChangeSet(document, "set-1");
		cs1.setChangeSet(document.createElement("changeSet"));
		
		ChangeSet cs2 = new ChangeSet(document, "set-1");
		cs2.setChangeSet(document.createElement("changeSet"));
		
		changeLog.addChangeSetToChangeLog(cs1);
				
		assertFalse(changeLog.addChangeSetToChangeLog(cs2));
	}
	
	/*
	 * Testing for method deleteChangeSetFromChangeLog
	 */
	@Test
	public void deleteChangeSetFromChangeLogTest_nullParameter() throws ParserConfigurationException {
		ChangeLog changeLog = createTestChangeLog();
		assertFalse(changeLog.deleteChangeSetFromChangeLog(null));
	}
	
	@Test
	public void deleteChangeSetFromChangeLogTest_BLankStringParameter() throws ParserConfigurationException {
		ChangeLog changeLog = createTestChangeLog();
		assertFalse(changeLog.deleteChangeSetFromChangeLog(""));
	}
	
	@Test
	public void deleteChangeSetFromChangeLogTest_changesetNotPresent() throws ParserConfigurationException {
		ChangeLog changeLog = createTestChangeLog();
		Document document = changeLog.getChangeLogDocument();
		
		ChangeSet cs1 = new ChangeSet(document, "set-1");
		cs1.setChangeSet(document.createElement("changeSet"));
		
		ChangeSet cs2 = new ChangeSet(document, "set-2");
		cs2.setChangeSet(document.createElement("changeSet"));
		
		changeLog.addChangeSetToChangeLog(cs1);
		changeLog.addChangeSetToChangeLog(cs2);
		
		assertFalse(changeLog.deleteChangeSetFromChangeLog("set-3"));
	}
	
	@Test
	public void deleteChangeSetFromChangeLogTest_existingChangeset() throws ParserConfigurationException {
		ChangeLog changeLog = createTestChangeLog();
		Document document = changeLog.getChangeLogDocument();
		
		ChangeSet cs1 = new ChangeSet(document, "set-1");
		cs1.setChangeSet(document.createElement("changeSet"));
		
		ChangeSet cs2 = new ChangeSet(document, "set-2");
		cs2.setChangeSet(document.createElement("changeSet"));
		
		changeLog.addChangeSetToChangeLog(cs1);
		changeLog.addChangeSetToChangeLog(cs2);
		
		assertTrue(changeLog.deleteChangeSetFromChangeLog("set-1"));
	}
}
